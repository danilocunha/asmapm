package com.familiaborges.danilo.apm.webui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import asmapm.model.CallStackTrace;
import asmapm.model.MethodCall;

import com.familiaborges.danilo.apm.dto.Execution;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ExecutionDetailsWindow extends Window {

	final TreeTable ttable = new TreeTable("CallStack");
	
	ArrayList<Integer> callPath;

	public ExecutionDetailsWindow(Execution e) {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);

		setCaption("Detalhes da Execução");
		setContent(l);
		center();
		setCloseShortcut(KeyCode.ESCAPE, null);
		setResizable(false);
		setClosable(false);

		addStyleName("no-vertical-drag-hints");
		addStyleName("no-horizontal-drag-hints");

		HorizontalLayout details = new HorizontalLayout();
		details.setSpacing(true);
		details.setMargin(true);
		l.addComponent(details);

		FormLayout fields = new FormLayout();
		fields.setWidth("90em");
		fields.setHeight("35em");
		fields.setSpacing(true);
		fields.setMargin(true);
		details.addComponent(fields);

		Label label;

		label = new Label(String.valueOf(e.getIdExecution()));
		label.setSizeUndefined();
		label.setCaption("Id da Execução");
		fields.addComponent(label);

		/*
		 * label = new
		 * Label(e.getCallStackTrace().getOnStringFormat(),ContentMode
		 * .PREFORMATTED); label.setSizeUndefined();
		 * label.setCaption("CallStack"); fields.addComponent(label);
		 */

		montaCallStackTreeTable(e.getCallStackTrace());
		fields.addComponent(ttable);

		HorizontalLayout footer = new HorizontalLayout();
		footer.addStyleName("footer");
		footer.setWidth("100%");
		footer.setMargin(true);

		Button ok = new Button("Close");
		ok.addStyleName("wide");
		ok.addStyleName("default");
		ok.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		footer.addComponent(ok);
		footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
		l.addComponent(footer);

	}

	public void montaCallStackTreeTable(CallStackTrace cst) {

		ttable.addContainerProperty("Name", String.class, null);
		ttable.addContainerProperty("Duração", Long.class, null);
		ttable.addContainerProperty("SQL", String.class, null);
		ttable.setWidth("80em");
		ttable.setSizeFull();
		//ttable.setColumnHeaderMode(ColumnHeaderMode.);
		
		callPath = new ArrayList<Integer>();
		
		List<MethodCall> l = cst.getMethodCalls();
		ListIterator<MethodCall> lite = l.listIterator(l.size());
		
		MethodCall m;
		
		int index = l.size();
		int reverseIndex = 0;
		while (lite.hasPrevious()) {
			
			m = lite.previous();
			//System.out.println(m.toString()+m.getExecutionTime());
			if(m.getExecutionTime()>10) {
			
			//System.out.println(m.toString()+m.getExecutionTime());
			}
			reverseIndex = Math.abs(index-l.size());//Get index of last to first
			if (m.getLevel() == 0) {//Add root node
				ttable.addItem(
						new Object[] { m.toString(), m.getExecutionTime(),
								m.getSql() }, reverseIndex);
				callPath.add(reverseIndex);
				
			}
			
			if ((m.getExecutionTime() > 10) && !callPath.contains(reverseIndex)) {//If above threshold add to Call Tree. Verify if already added
				int dad = setCaller(cst, reverseIndex);//Get dad node and add dad nodes if needed
				ttable.addItem(
						new Object[] { m.toString(), m.getExecutionTime(),
								m.getSql() }, reverseIndex);
				//setCaller(cst, reverseIndex);
				ttable.setParent(reverseIndex, dad);
			}
			
			if ((m.getSql() != null) && !callPath.contains(reverseIndex)) {//If have a SQL Call add to Call Tree. Verify if already added
				int dad = setCaller(cst, reverseIndex);//Get dad node and add dad nodes if needed
				ttable.addItem(
						new Object[] { m.toString(), m.getExecutionTime(),
								m.getSql() }, reverseIndex);
				//setCaller(cst, reverseIndex);
				ttable.setParent(reverseIndex, dad);

			}

			index--;
		}

	}

	public int setCaller(CallStackTrace cst, int indexForward) {
		
		MethodCall m;

		int parentIndex = callPath.get(callPath.size()-1);//Dad node is the last added of Call Path
		
		List<MethodCall> l = cst.getMethodCalls();
		
		ArrayList<Integer> localCallPath = new ArrayList<Integer>();//local Call Path to be added to Call Path
		
		int index = (l.size()-1) - indexForward;
		int nextLevelOfCaller = l.get(index).getLevel() - 1;
		
		
		while (index<l.size()) {
			m = l.get(index);
			
			if (m.getLevel() == nextLevelOfCaller) {
				
				indexForward = Math.abs(index-(l.size()-1));//Calcula o indice do filho
				if((!callPath.contains(indexForward))) {//Se o filho já nao foi adicionado a tabela o faz
					
					localCallPath.add(indexForward);//Atualiza que o filho é para ser adicionado na tabela
					
				}
				//ttable.setParent(indexForward, indexOfCaller);//Seta a huierarquia entre pai e filho
				nextLevelOfCaller--;
			}
			index++;
		}
		
		//Reverse order because Call Tree is mounted from last to first
		Collections.reverse(localCallPath);
		//System.out.println("After Reverse Order, ArrayList Contains : " + localCallPath);
		
		//Add items to table and add to Call Tree on correct order
		Iterator<Integer> ite = localCallPath.iterator();
		while(ite.hasNext()) {
			int i = ite.next();
			indexForward = Math.abs(i-l.size());
			m = l.get(indexForward);
			ttable.addItem(
					new Object[] { m.toString(), m.getExecutionTime(),
							m.getSql() }, i);
			callPath.add(i);
		}
		
		//Set parent of the nodes
		ite = localCallPath.iterator();
		while(ite.hasNext()) {
			int i = ite.next();
			ttable.setParent(i, parentIndex);
			parentIndex = i;
		}
		//The last added node is parent
		if(localCallPath.size()>0) {
		return parentIndex;
		} else {
			return indexForward;
		}
		

	}
}

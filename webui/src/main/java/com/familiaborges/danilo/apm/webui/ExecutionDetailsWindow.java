package com.familiaborges.danilo.apm.webui;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import asmapm.ApmType;
import asmapm.model.CallStackTrace;
import asmapm.model.MethodCall;

import com.familiaborges.danilo.apm.dto.Execution;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ExecutionDetailsWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		fields.setWidth("80em");
		fields.setHeight("35em");
		fields.setSpacing(true);
		fields.setMargin(true);
		details.addComponent(fields);

		Label label;

		label = new Label(String.valueOf(e.getIdExecution()));
		label.setSizeUndefined();
		label.setCaption("Id da Execução");
		fields.addComponent(label);

		if (e.getCallStackTrace().getExtraData().get("URI") != null) {
			label = new Label(e.getCallStackTrace().getExtraData().get("URI")
					.toString());
			label.setSizeUndefined();
			label.setCaption("URI");
			fields.addComponent(label);
		}

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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		ttable.addContainerProperty("Type", CssLayout.class, null);
		ttable.setWidth("50em");
		ttable.setSizeFull();
		// ttable.setColumnHeaderMode(ColumnHeaderMode.);

		callPath = new ArrayList<Integer>();

		List<MethodCall> l = cst.getMethodDadCallsWithIndexesOfDadCalls()
				.getMethodCalls();
		ListIterator<MethodCall> lite = l.listIterator();

		MethodCall m;

		int index = 0;
		while (lite.hasNext()) {

			m = lite.next();
			// System.out.println(m.toString()+m.getExecutionTime());
			if ((m.getExecutionTime() > 10)) {
				m.setVisibleOnCallTree(true);
			}
			if (m.getSql() != null) {
				m.setVisibleOnCallTree(true);
				int dadIndex = m.getIndexOfDadMethodCall();
				while (dadIndex != 0) {
					l.get(dadIndex).setVisibleOnCallTree(true);
					dadIndex = l.get(dadIndex).getIndexOfDadMethodCall();
				}
			}
			if (m.getType() == ApmType.HTTP_CLIENT) {
				m.setVisibleOnCallTree(true);
				int dadIndex = m.getIndexOfDadMethodCall();
				while (dadIndex != 0) {
					l.get(dadIndex).setVisibleOnCallTree(true);
					dadIndex = l.get(dadIndex).getIndexOfDadMethodCall();
				}
			}
			index++;

		}

		lite = l.listIterator();
		index = 0;
		while (lite.hasNext()) {

			m = lite.next();
			// System.out.println(m.toString()+m.getExecutionTime());
			if ((m.isVisibleOnCallTree())) {

				ttable.addItem(
						new Object[] { m.toString(), m.getExecutionTime(),
								getTypeLinkOfCall(m) }, index);
				if (m.getIndexOfDadMethodCall() != null) {
					ttable.setParent(index, m.getIndexOfDadMethodCall());
				}
			}
			index++;

		}

	}

	public Object getTypeLinkOfCall(final MethodCall m) {
		CssLayout layout = new CssLayout();
		if (m.getSql() != null) {			
			Button button = new Button ("SQL",
			new Button.ClickListener() {
			   
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
			    	Window w = new SqlDetailsWindow(m);
			        UI.getCurrent().addWindow(w);
			        w.focus();	
			    }
			});
			button.setStyleName(Reindeer.BUTTON_LINK);
			layout.addComponent(button);			
		}
		
		if (m.getType() == ApmType.HTTP_CLIENT) {			
			Button button = new Button ("HTTP",
			new Button.ClickListener() {
			   
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
			    	Window w = new HttpDetailsWindow(m);
			        UI.getCurrent().addWindow(w);
			        w.focus();	
			    }
			});
			button.setStyleName(Reindeer.BUTTON_LINK);
			layout.addComponent(button);			
		}
		
		return layout;
	}

}

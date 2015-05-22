package com.familiaborges.danilo.apm.webui;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import asmapm.model.CallStackTrace;
import asmapm.model.MethodCall;

import com.familiaborges.danilo.apm.dto.Execution;
import com.vaadin.event.ShortcutAction.KeyCode;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import com.vaadin.ui.TextArea;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class RawDetailsWindow extends Window {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextArea area = new TextArea();
	TextArea reverseArea = new TextArea();
	
	ArrayList<Integer> callPath;

	public RawDetailsWindow(Execution e) {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);

		setCaption("Objeto RAW da Execução");
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
		
		label = new Label(e.getCallStackTrace().getExtraData().get("serverUrl").toString());
		label.setSizeUndefined();
		label.setCaption("URI");
		fields.addComponent(label);

		/*
		 * label = new
		 * Label(e.getCallStackTrace().getOnStringFormat(),ContentMode
		 * .PREFORMATTED); label.setSizeUndefined();
		 * label.setCaption("CallStack"); fields.addComponent(label);
		 */

		CallStackTrace c = e.getCallStackTrace().getMethodDadCallsWithIndexesOfDadCalls();
		montaCallStackTextAreaForward(c);
		montaCallStackTextAreaReverse(c);
		fields.addComponent(area);
		fields.addComponent(reverseArea);

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

	public void montaCallStackTextAreaForward(CallStackTrace cst) {

		StringBuffer sb = new StringBuffer();
		area.setCaption("MethodCalls Forward");
		area.setWidth("80em");
		area.setSizeFull();
		//ttable.setColumnHeaderMode(ColumnHeaderMode.);
		
		callPath = new ArrayList<Integer>();
		
		List<MethodCall> l = cst.getMethodCalls();
		Iterator<MethodCall> lite = l.iterator();
		
		MethodCall m;
				
		while (lite.hasNext()) {
			
			m = lite.next();
			sb.append(m.toString() + "\n");
						
		}
		area.setValue(sb.toString());

	}
	
	public void montaCallStackTextAreaReverse(CallStackTrace cst) {

		StringBuffer sb = new StringBuffer();
		reverseArea.setCaption("MethodCalls Reverse");
		reverseArea.setWidth("80em");
		reverseArea.setSizeFull();
		//ttable.setColumnHeaderMode(ColumnHeaderMode.);
		
		callPath = new ArrayList<Integer>();
		
		List<MethodCall> l = cst.getMethodCalls();
		ListIterator<MethodCall> lite = l.listIterator(l.size());
		
		MethodCall m;
		
		while (lite.hasPrevious()) {
			
			m = lite.previous();
			sb.append(m.toString() + "\n");
						
		}
		reverseArea.setValue(sb.toString());

	}

	
}

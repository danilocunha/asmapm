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

public class SqlDetailsWindow extends Window {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextArea area = new TextArea();
	TextArea reverseArea = new TextArea();
	
	ArrayList<Integer> callPath;

	public SqlDetailsWindow(MethodCall m) {
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);

		setCaption("Detalhes da Execução - SQL");
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
		fields.setWidth("50em");
		
		fields.setSpacing(true);
		fields.setMargin(true);
		details.addComponent(fields);		

		TextArea sql = new TextArea("SQL");
		sql.setWordwrap(true);
		sql.setValue(m.getSql());
		sql.setWidth("40em");
		fields.addComponent(sql);			

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
	
}

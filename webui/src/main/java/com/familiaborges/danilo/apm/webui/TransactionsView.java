package com.familiaborges.danilo.apm.webui;

import com.familiaborges.danilo.apm.dao.ExecutionDAO;
import com.vaadin.data.Item;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TransactionsView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Table t;
	
	final ExecutionDAO dao = new ExecutionDAO();
	public TransactionsView() {
		setSizeFull();
        addStyleName("dashboard-view");

        
        dao.getJPAContainer();
        
        t = new Table(null, dao.getJPAContainer());
        t.setSizeFull();
        t.addStyleName("borderless");
        t.setSelectable(true);
        t.setColumnCollapsingAllowed(true);
        t.setColumnReorderingAllowed(true);
        
        t.setVisibleColumns(new Object[]{"dataHora","serverUrl", "duration"});
        
        t.setColumnHeader("dataHora", "Data/Hora");
        t.setColumnHeader("serverUrl", "URL");
        t.setColumnHeader("duration", "Duração");
        //addContainerProperty("startTimeMillis", Long.class, null);
        
        t.addItemClickListener(new ItemClickListener() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                	 showExecutionDetailsWindows(event.getItem());
            }
                // do something in here
            }
        });
        
        t.addActionHandler(new Handler() {

            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private Action report = new Action("Create Report");

            private Action discard = new Action("Discard");

            private Action callstack = new Action("Callstack");
            
            private Action rawView = new Action("RAW View");

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                if (action == report) {
                	Notification.show("Not implemented in this demo");
                } else if (action == discard) {
                    Notification.show("Not implemented in this demo");
                } else if (action == callstack) {
                    Item item = ((Table) sender).getItem(target);
                    if (item != null) {
                        
                    	showExecutionDetailsWindows(item);
                    }
                } else if (action == rawView) {
                    Item item = ((Table) sender).getItem(target);
                    if (item != null) {
                        
                    	showRawDetailsWindows(item);
                    }
                }
            }

            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[] { callstack, report, discard, rawView };
            }
        });
        
        
        
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        top.addStyleName("toolbar");
        addComponent(top);
        final Label title = new Label("Transactions");
        title.setSizeUndefined();
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);
        
        addComponent(t);
        setExpandRatio(t, 1);
	}

	public void showExecutionDetailsWindows(Item item) {
		Window w = new ExecutionDetailsWindow(dao
                .findById(item.getItemProperty("idExecution")
                        .getValue()));
        UI.getCurrent().addWindow(w);
        w.focus();	
	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	public void showRawDetailsWindows(Item item) {
		Window w = new RawDetailsWindow(dao
                .findById(item.getItemProperty("idExecution")
                        .getValue()));
        UI.getCurrent().addWindow(w);
        w.focus();	
	}
	

}

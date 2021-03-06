package local.teste.pages;

import java.util.Iterator;

import local.teste.dao.EmployeeDAO;
import local.teste.entity.Employee;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class EmployeeListPage extends WebPage {
	
	public EmployeeListPage(PageParameters parameters) {
		EmployeeDAO dao = new EmployeeDAO();

		Iterator<Employee> employees;
		StringValue sendtotaskmanager = parameters.get("sendtotaskmanager");
		if(sendtotaskmanager.toString().equals("1")) {
		  employees = dao.listaTeste().iterator();
		  //System.out.println("Send to Task Manager");
		} else {
		  employees = dao.findAll().iterator();
		  //System.out.println("NOT Send to Task Manager");
		}
		
		

		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		int index = 0;
		while (employees.hasNext())
        {
            AbstractItem item = new AbstractItem(repeating.newChildId());

            repeating.add(item);
            Employee employee = employees.next();

            //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item.add(new Label("emp_no", String.valueOf(employee.getEmpNo())));
            item.add(new Label("first_name", employee.getFirstName()));
            item.add(new Label("last_name", employee.getLastName()));
            

            final int idx = index;
            item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public String getObject()
                {
                    return (idx % 2 == 1) ? "even" : "odd";
                }
            }));

            index++;
            if(index==10) {
            	break;
            }
        }
	}
}

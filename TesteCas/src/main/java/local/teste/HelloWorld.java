package local.teste;

import local.teste.pages.EmployeeListPage;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class HelloWorld extends WebPage{
	 public HelloWorld() {
	        add(new Label("message", "Hello World!"));
	        add(new Link("linkEmployee"){
				@Override
				public void onClick() {			   
	                         //we redirect browser to another page.
	                         setResponsePage(EmployeeListPage.class);
				}			
			});
	    }

}

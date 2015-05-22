package br.leg.camara.cenin.testewicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class SleepPage extends WebPage {
	public SleepPage(PageParameters parameters) {
		super(parameters);
		
		int segundos = 1;
		try {
			segundos = Integer.parseInt(parameters.get("segundos").toString());
		} catch(Exception e) {
			
		}
		
		
		
		try {
			Thread.sleep(segundos*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(new Label("segundos", segundos));
	}
}

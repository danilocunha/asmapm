package br.leg.camara.cenin.testewicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.WebPage;

import br.leg.camara.cenin.testewicket.pages.SleepPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		// add(new Label("version",
		// getApplication().getFrameworkSettings().getVersion()));
		add(new BookmarkablePageLink("linkSleep", SleepPage.class, parameters) {

		});

		// TODO Add your page's components here

	}
}

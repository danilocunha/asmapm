package br.leg.camara.cenin.testewicket.comum;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

public abstract class PaginaBasica extends WebPage {

	public PaginaBasica() {
		super();
		System.out.println("Olha eu aqui na pagina basica");
	}
	
	
	@Override
	protected void configureResponse(WebResponse webResponse) {
		// TODO Auto-generated method stub
		super.configureResponse(webResponse);
				
		webResponse.setHeader("Teste", "testando 1 2 3");
	}
	
}

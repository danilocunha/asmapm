package br.gov.camara.codis.entidade;

import java.util.EnumSet;

public enum WebServiceEnum { 
	ULTIMAS(1, "As dez notícias mais recentes de todos os veículos"),
	CAMARA_VOCE(2, "As dez notícias mais recentes dos temas selecionados"), 
	IMPERDIVEL(3, "As dez notícias destaques mais recentes");
	
	private final int ideServico;
	private final String descricao;
	
	
	private WebServiceEnum(int ideServico, String descricao) {
		this.ideServico = ideServico;
		this.descricao = descricao;
	}

	public int getId() {
		return ideServico;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
}

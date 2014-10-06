package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class ClassificacaoImagem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ideClassificacao;
	private String desClassificacao;

	public String getIdeClassificacao() {
		return ideClassificacao;
	}

	public void setIdeClassificacao(String ideClassificacao) {
		this.ideClassificacao = ideClassificacao;
	}

	public String getDesClassificacao() {
		return desClassificacao;
	}

	public void setDesClassificacao(String desClassificacao) {
		this.desClassificacao = desClassificacao;
	}
}

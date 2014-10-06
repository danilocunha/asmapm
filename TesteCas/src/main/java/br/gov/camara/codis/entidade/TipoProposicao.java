package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class TipoProposicao implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String descricao;
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}

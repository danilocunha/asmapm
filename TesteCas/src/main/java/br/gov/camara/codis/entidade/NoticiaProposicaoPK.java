package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NoticiaProposicaoPK implements Serializable {
	@Column(name="IDENOTICIA", insertable=false, updatable=false)
	private Integer ideNoticia;

	private Integer ideProposicao;

	private static final long serialVersionUID = 1L;

	public NoticiaProposicaoPK() {
		super();
	}

	public Integer getIdeNoticia() {
		return this.ideNoticia;
	}

	public void setIdeNoticia(Integer idenoticia) {
		this.ideNoticia = idenoticia;
	}

	public int getIdeProposicao() {
		return this.ideProposicao;
	}

	public void setIdeProposicao(Integer ideProposicao) {
		this.ideProposicao = ideProposicao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ideNoticia == null) ? 0 : ideNoticia.hashCode());
		result = prime * result
				+ ((ideProposicao == null) ? 0 : ideProposicao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NoticiaProposicaoPK other = (NoticiaProposicaoPK) obj;
		if (ideNoticia == null) {
			if (other.ideNoticia != null)
				return false;
		} else if (!ideNoticia.equals(other.ideNoticia))
			return false;
		if (ideProposicao == null) {
			if (other.ideProposicao != null)
				return false;
		} else if (!ideProposicao.equals(other.ideProposicao))
			return false;
		return true;
	}

}

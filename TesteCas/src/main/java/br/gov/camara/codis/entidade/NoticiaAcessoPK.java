package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NoticiaAcessoPK implements Serializable {
	@Column(name="IDENOTICIA", insertable=false, updatable=false)
	private Integer ideNoticia;

	private Date datAcesso;

	private static final long serialVersionUID = 1L;

	public NoticiaAcessoPK() {
		super();
	}

	public Integer getIdeNoticia() {
		return this.ideNoticia;
	}

	public void setIdeNoticia(Integer idenoticia) {
		this.ideNoticia = idenoticia;
	}

	public Date getDatAcesso() {
		return this.datAcesso;
	}

	public void setDatAcesso(Date datacesso) {
		this.datAcesso = datacesso;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ( ! (o instanceof NoticiaAcessoPK)) {
			return false;
		}
		NoticiaAcessoPK other = (NoticiaAcessoPK) o;
		return (this.ideNoticia == other.ideNoticia)
			&& this.datAcesso.equals(other.datAcesso);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ideNoticia;
		hash = hash * prime + this.datAcesso.hashCode();
		return hash;
	}

}

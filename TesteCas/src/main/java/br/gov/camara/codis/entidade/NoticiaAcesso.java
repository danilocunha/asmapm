package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NoticiaAcesso implements Serializable {
	@EmbeddedId
	private NoticiaAcessoPK pk;
	
	@ManyToOne
	@JoinColumn(name="IDENOTICIA", insertable=false, updatable=false)
	private Noticia noticia;

	private Integer numAcesso;

	private static final long serialVersionUID = 1L;

	public NoticiaAcesso() {
		super();
	}

	public NoticiaAcessoPK getPk() {
		return this.pk;
	}

	public void setPk(NoticiaAcessoPK pk) {
		this.pk = pk;
	}

	public Integer getNumAcesso() {
		return this.numAcesso;
	}

	public void setNumAcesso(Integer numacesso) {
		this.numAcesso = numacesso;
	}
	
	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
}

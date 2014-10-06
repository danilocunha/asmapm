package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class NoticiaProposicao implements Serializable {
	@EmbeddedId
	private NoticiaProposicaoPK pk;

	private static final long serialVersionUID = 1L;

	public NoticiaProposicao() {
		super();
	}

	public NoticiaProposicaoPK getPk() {
		return this.pk;
	}

	public void setPk(NoticiaProposicaoPK pk) {
		this.pk = pk;
	}
}

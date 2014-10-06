package br.gov.camara.codis.entidade;

import java.io.Serializable;

/**
 * Bean que auxilia a montagem da nuvem de tags. Contém a Tag e o número de
 * acessos de notícias com essa Tag.
 */
public class TagAcesso implements Serializable {
	private static final long serialVersionUID = 1L;

	private Tag tag;
	private Long acessos;

	public TagAcesso(Tag tag, Long acessos) {
		this.tag = tag;
		this.acessos = acessos;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Long getAcessos() {
		return acessos;
	}

	public void setAcessos(Long acessos) {
		this.acessos = acessos;
	}
}

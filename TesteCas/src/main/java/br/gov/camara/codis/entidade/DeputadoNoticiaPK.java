package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeputadoNoticiaPK implements Serializable {
	@Column(name="IDENOTICIA", nullable=false, updatable=false)
	private Integer ideNoticia;

	@Column(name="IDEDEPUTADO", nullable=false, updatable=false)
	private Integer ideDeputado;

	private static final long serialVersionUID = 1L;
	
	public Integer getIdeNoticia() {
		return this.ideNoticia;
	}

	public void setIdeNoticia(Integer idenoticia) {
		this.ideNoticia = idenoticia;
	}

	public int getIdeDeputado() {
		return this.ideDeputado;
	}

	public void setIdeDeputado(Integer idedeputado) {
		this.ideDeputado = idedeputado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ideDeputado == null) ? 0 : ideDeputado.hashCode());
		result = prime * result
				+ ((ideNoticia == null) ? 0 : ideNoticia.hashCode());
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
		DeputadoNoticiaPK other = (DeputadoNoticiaPK) obj;
		if (ideDeputado == null) {
			if (other.ideDeputado != null)
				return false;
		} else if (!ideDeputado.equals(other.ideDeputado))
			return false;
		if (ideNoticia == null) {
			if (other.ideNoticia != null)
				return false;
		} else if (!ideNoticia.equals(other.ideNoticia))
			return false;
		return true;
	}
}

package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Midia implements Serializable {
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="idemidia")
	private Integer id;

	private String texDescricao;

	private static final long serialVersionUID = 1L;

	public Midia() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idemidia) {
		this.id = idemidia;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Midia other = (Midia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

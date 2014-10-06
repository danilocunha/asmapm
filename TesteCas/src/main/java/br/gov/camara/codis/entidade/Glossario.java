package br.gov.camara.codis.entidade;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.Hibernate;

@Entity
public class Glossario implements DomainObject<Integer> {
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="ideglossario")
	private Integer id;

	private String texTitulo;

	private String texDescricao;

	private Timestamp datUltimaAlteracao;

	private String texPontoUsuario;

	private static final long serialVersionUID = 1L;

	public Glossario() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexTitulo() {
		return texTitulo;
	}

	public void setTexTitulo(String texTitulo) {
		this.texTitulo = texTitulo;
	}

	public String getTexDescricao() {
		return texDescricao;
	}

	public void setTexDescricao(String texDescricao) {
		this.texDescricao = texDescricao;
	}

	public Timestamp getDatUltimaAlteracao() {
		return datUltimaAlteracao;
	}

	public void setDatUltimaAlteracao(Timestamp datUltimaAlteracao) {
		this.datUltimaAlteracao = datUltimaAlteracao;
	}

	public String getTexPontoUsuario() {
		return texPontoUsuario;
	}

	public void setTexPontoUsuario(String texPontoUsuario) {
		this.texPontoUsuario = texPontoUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getTexTitulo() == null) ? 0 : getTexTitulo().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!Hibernate.getClass(this).equals(Hibernate.getClass(obj)))
			return false;
		Glossario other = (Glossario) obj;
		if (getTexTitulo() == null) {
			if (other.getTexTitulo() != null)
				return false;
		} else if (!getTexTitulo().equalsIgnoreCase(other.getTexTitulo()))
			return false;
		return true;
	}
	
	
	
}

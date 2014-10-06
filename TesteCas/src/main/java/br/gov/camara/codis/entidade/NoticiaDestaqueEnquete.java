package br.gov.camara.codis.entidade;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.Hibernate;

@Entity
public class NoticiaDestaqueEnquete implements DomainObject<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ideNoticiaDestaqueEnquete")
	private Integer id;

	private String texTitulo;

	private String texUrlNoticia;

	public NoticiaDestaqueEnquete() {
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
	
	public String getTexUrlNoticia() {
		return texUrlNoticia;
	}

	public void setTexUrlNoticia(String texUrlNoticia) {
		this.texUrlNoticia = texUrlNoticia;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : id.hashCode());
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
		Retranca other = (Retranca) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}


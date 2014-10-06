package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;

@Entity
public class NoticiaBoletim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideNoticiaBoletim")
	private Integer id;

	@ManyToOne
	@JoinColumn(name="ideBoletim")
	private Boletim boletim;

	@ManyToOne
	@JoinColumn(name="ideNoticia")
	private Noticia noticia; 
	
	@ManyToOne
	@JoinColumn(name="ideRetranca")
	private Retranca retranca;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public Retranca getRetranca() {
		return retranca;
	}

	public void setRetranca(Retranca retranca) {
		this.retranca = retranca;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (! Hibernate.getClass(this).equals(Hibernate.getClass(obj)))
			return false;
		NoticiaBoletim other = (NoticiaBoletim) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}

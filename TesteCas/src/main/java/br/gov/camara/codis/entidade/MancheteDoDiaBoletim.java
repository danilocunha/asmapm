package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MancheteDoDiaBoletim implements Serializable, DomainObject<Integer> 
{	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideMancheteDoDiaBoletim") 
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="ideBoletim")
	private Boletim boletim;   
	
	@ManyToOne 
	@JoinColumn(name="ideNoticia") 
	private Noticia noticia;
	
	@Column(name="numSequencia", nullable=false) 
	private Integer numSequencia; 
	
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

	public Integer getNumSequencia() {
		return numSequencia;
	}

	public void setNumSequencia(Integer numSequencia) {
		this.numSequencia = numSequencia;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		if (getClass() != obj.getClass())
			return false;
		MancheteDoDiaBoletim other = (MancheteDoDiaBoletim) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Boletim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideboletim")
	private Integer id;

	private Integer ideMovimentoSispush;
	
	private String texPontoUsuario;

	private Date datDoEnvio;

	public Boletim() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdeMovimentoSispush() {
		return ideMovimentoSispush;
	}

	public void setIdeMovimentoSispush(Integer ideMovimentoSispush) {
		this.ideMovimentoSispush = ideMovimentoSispush;
	}

	public String getTexPontoUsuario() {
		return texPontoUsuario; 
	}

	public void setTexPontoUsuario(String texPontoUsuario) {
		this.texPontoUsuario = texPontoUsuario;
	}

	public Date getDatDoEnvio() {
		return datDoEnvio; 
	}

	public void setDatDoEnvio(Date datDoEnvio) {
		this.datDoEnvio = datDoEnvio; 
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
		Boletim other = (Boletim) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

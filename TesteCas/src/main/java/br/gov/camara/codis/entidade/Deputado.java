package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class Deputado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Deputado () {	
		super();
	}
	
	public Deputado (Integer id, String nome) {
		setId(id);
		setNome(nome);
	}
	
	public Deputado (Integer id, String nome, String email) {
		setId(id);
		setNome(nome);
		setEmail(email);
	}
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		Deputado other = (Deputado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

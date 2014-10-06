package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class Resposta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Resposta () {
		super();
	}
	
	public Resposta (String id, String ideEnquete, String resposta) {
		setId(id);
		setIdeEnquete(ideEnquete);
		setResposta(resposta);
	}
	
	public Resposta (String id, String ideEnquete, String resposta, int numVotos) {
		setId(id);
		setIdeEnquete(ideEnquete);
		setResposta(resposta);
		setNumVotos(numVotos);
	}
	
	private String id;  	
	private String ideEnquete;
	private String resposta;
	private int numVotos;
	

	public int getNumVotos() {
		return numVotos;
	}

	public void setNumVotos(int votos) {
		this.numVotos = votos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdeEnquete() {
		return ideEnquete;
	}

	public void setIdeEnquete(String ideEnquete) {
		this.ideEnquete = ideEnquete;
	}
	
	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
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
		Resposta other = (Resposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

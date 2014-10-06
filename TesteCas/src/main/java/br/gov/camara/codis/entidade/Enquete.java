package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class Enquete implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public Enquete () {
		super();
	}
	
	public Enquete (String id, String tituloEnquete, String pergunta, String resposta, String idPergunta) {
		setId(id);
		setTituloEnquete(tituloEnquete);
		setPergunta(pergunta);
		setResposta(resposta);		
		setDescricaoResposta(descricaoResposta);
		setTotalRespostasPergunta(totalRespostasPergunta);				
		setIdPergunta(idPergunta);
	}
	
	public Enquete (String id, String tituloEnquete, String pergunta, String resposta, String idPergunta, String totalVotos) {
		setId(id);
		setTituloEnquete(tituloEnquete);
		setPergunta(pergunta);
		setResposta(resposta);
		setDescricaoResposta(descricaoResposta);				
		setTotalRespostasPergunta(totalRespostasPergunta);
		setIdPergunta(idPergunta);
		setTotalVotos(totalVotos);
	}
	
	private String id; 	
	private String tituloEnquete;	
	private String pergunta;	
	private String resposta;
	private String descricaoResposta;	
	private String totalRespostasPergunta;	
	private String idPergunta;
	private String totalVotos;
	private String termoPesquisa;
	private String indSituacaoenquete;
	

	public String getIndSituacaoenquete() {
		return indSituacaoenquete;
	}

	public void setIndSituacaoenquete(String indSituacaoenquete) {
		this.indSituacaoenquete = indSituacaoenquete;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTituloEnquete() {
		return tituloEnquete;
	}

	public void setTituloEnquete(String tituloEnquete) {
		this.tituloEnquete = tituloEnquete;
	}
	
	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getDescricaoResposta() {
		return descricaoResposta;
	}

	public void setDescricaoResposta(String descricaoResposta) {
		this.descricaoResposta = descricaoResposta;
	}	
	
	public String getTotalRespostasPergunta() {
		return totalRespostasPergunta;
	}

	public void setTotalRespostasPergunta(String totalRespostasPergunta) {
		this.totalRespostasPergunta = totalRespostasPergunta;
	}	
	
	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}	
	
	public String getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(String idPergunta) {
		this.idPergunta = idPergunta;
	}
	
	public String getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(String totalVotos) {
		this.totalVotos = totalVotos;
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
		Enquete other = (Enquete) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
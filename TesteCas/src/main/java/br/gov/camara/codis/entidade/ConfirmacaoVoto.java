package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class ConfirmacaoVoto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public ConfirmacaoVoto () {
		super();
	}
	


	public ConfirmacaoVoto (String idConfirmacao, String ideEnquete, String texEmail, String ideOpcao, String datHoraConfirmacao, String tituloEnquete, String texOpcaoDeResposta, String ipOrigem) {
		setIdConfirmacao(idConfirmacao);
		setIdeEnquete(ideEnquete);
		setTexEmail(texEmail);
		setIdeOpcao(ideOpcao);
		setDatHoraConfirmacao(datHoraConfirmacao);
		setTituloEnquete(tituloEnquete);
		setTexOpcaoDeResposta(texOpcaoDeResposta);
		setIpOrigem(ipOrigem);
		
	}
	
	public String getIpOrigem() {
		return ipOrigem;
	}

	public void setIpOrigem(String ipOrigem) {
		this.ipOrigem = ipOrigem;
	}
	
	public String getTituloEnquete() {
		return tituloEnquete;
	}

	public void setTituloEnquete(String tituloEnquete) {
		this.tituloEnquete = tituloEnquete;
	}

	public String getTexOpcaoDeResposta() {
		return texOpcaoDeResposta;
	}

	public void setTexOpcaoDeResposta(String texOpcaoDeResposta) {
		this.texOpcaoDeResposta = texOpcaoDeResposta;
	}

	public String getIdConfirmacao() {
		return idConfirmacao;
	}

	public void setIdConfirmacao(String idConfirmacao) {
		this.idConfirmacao = idConfirmacao;
	}

	public String getIdeEnquete() {
		return ideEnquete;
	}

	public void setIdeEnquete(String ideEnquete) {
		this.ideEnquete = ideEnquete;
	}

	public String getTexEmail() {
		return texEmail;
	}

	public void setTexEmail(String texEmail) {
		this.texEmail = texEmail;
	}

	public String getIdeOpcao() {
		return ideOpcao;
	}

	public void setIdeOpcao(String ideOpcao) {
		this.ideOpcao = ideOpcao;
	}

	public String getDatHoraConfirmacao() {
		return datHoraConfirmacao;
	}

	public void setDatHoraConfirmacao(String datHoraConfirmacao) {
		this.datHoraConfirmacao = datHoraConfirmacao;
	}

	private String idConfirmacao; 	
	private String ideEnquete;	
	private String texEmail;	
	private String ideOpcao;
	private String datHoraConfirmacao;	
	private String tituloEnquete;
	private String texOpcaoDeResposta;
	private String ipOrigem;
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idConfirmacao == null) ? 0 : idConfirmacao.hashCode());
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
		ConfirmacaoVoto other = (ConfirmacaoVoto) obj;
		if (idConfirmacao == null) {
			if (other.idConfirmacao != null)
				return false;
		} else if (!idConfirmacao.equals(other.idConfirmacao))
			return false;
		return true;
	}
}
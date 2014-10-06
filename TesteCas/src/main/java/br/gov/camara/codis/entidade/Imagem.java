package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

public class Imagem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ideImagem;
	private String desTema;
	private String desImagem;
	private String nomImagemP;
	private String nomImagemM;
	private String nomImagemG;
	private Date datTirada;
	private String nomAutor;

	public String getIdeImagem() {
		return ideImagem;
	}

	public void setIdeImagem(String ideImagem) {
		this.ideImagem = ideImagem;
	}

	public String getDesTema() {
		return desTema;
	}

	public void setDesTema(String desTema) {
		this.desTema = desTema;
	}

	public String getDesImagem() {
		if (desImagem != null) return desImagem.trim();
		return desImagem;
	}

	public void setDesImagem(String desImagem) {
		this.desImagem = desImagem;
	}

	public String getNomImagemP() {
		return nomImagemP;
	}

	public void setNomImagemP(String nomImagemP) {
		this.nomImagemP = nomImagemP;
	}

	public String getNomImagemM() {
		return nomImagemM;
	}

	public void setNomImagemM(String nomImagemM) {
		this.nomImagemM = nomImagemM;
	}

	public Date getDatTirada() {
		return datTirada;
	}

	public void setDatTirada(Date datTirada) {
		this.datTirada = datTirada;
	}

	public String getNomAutor() {
		return nomAutor;
	}

	public void setNomAutor(String nomAutor) {
		this.nomAutor = nomAutor;
	}

	public String getNomImagemG() {
		return nomImagemG;
	}

	public void setNomImagemG(String nomImagemG) {
		this.nomImagemG = nomImagemG;
	}
	
	public String getUrlImagemP() {
		return getNomImagemP().trim().replaceAll(" ", "%20");
	}

	public String getUrlImagemM() {
		return getNomImagemM().trim().replaceAll(" ", "%20");
	}
	
	public String getUrlImagemG() {
		return getNomImagemG().trim().replaceAll(" ", "%20");
	}
	
	public String getUrlImagem (TamanhoImagemEnum tamanho) {
		String url=null;
		switch (tamanho) {
		case GRANDE:
			url = getUrlImagemG();
			break;
		case MEDIA:
			url = getUrlImagemM();
			break;
		case PEQUENA:
			url = getUrlImagemP();
			break;
		default:
			break;
		}
		return url;
	}

}

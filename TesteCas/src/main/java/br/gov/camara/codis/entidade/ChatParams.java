package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

/**
 * Contém os parâmetros do chat da agência.
 */
public class ChatParams implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String texBannerDivulgacaoChat;
	private Date datAberturaSala;
	
	public ChatParams () {	
		super();
	}

	public String getTexBannerDivulgacaoChat() {
		return texBannerDivulgacaoChat;
	}

	public void setTexBannerDivulgacaoChat(String texBannerDivulgacaoChat) {
		this.texBannerDivulgacaoChat = texBannerDivulgacaoChat;
	}

	public Date getDatAberturaSala() {
		return datAberturaSala;
	}

	public void setDatAberturaSala(Date datAberturaSala) {
		this.datAberturaSala = datAberturaSala;
	}
}

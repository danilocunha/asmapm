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
public class HomeCamara implements Serializable, DomainObject<Integer> 
{	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideHomeCamara") 
	private Integer id; 
		
	@ManyToOne 
	@JoinColumn(name="ideNoticia") 
	private Noticia noticia;
	
	@Column(name="texTitulo", nullable=false) 
	private String texTitulo; 
	
	@Column(name="texLed", nullable=false) 
	private String texLed;  
	
	@Column(name="linkImagem") 
	private String linkImagem;
	
	@Column(name="linkImagemExtranet") 
	private String linkImagemExtranet;
	
	@Column(name="retrancaExtranet") 
	private String retrancaExtranet;
	
	@Column(name="retranca") 
	private String retranca;
	
	@Column(name="texLegenda") 
	private String texLegenda; 
	
	@Column(name="qtdUltimasNoticias", nullable=false) 
	private String qtdUltimasNoticias;
	
	public String getQtdUltimasNoticias() { 
		return qtdUltimasNoticias; 
	}

	public void setQtdUltimasNoticias(String qtdUltimasNoticias) {
		this.qtdUltimasNoticias = qtdUltimasNoticias; 
	}
	
	@ManyToOne 
	@JoinColumn(name="IdeNoticiaImg") 
	private Noticia noticiaImg; 
	
	
	public Noticia getNoticiaImg() {  
		return noticiaImg;
	}

	public void setNoticiaImg(Noticia noticiaImg) {
		this.noticiaImg = noticiaImg; 
	}
	
	public Noticia getNoticia() { 
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia; 
	}

	public String getTexTitulo() {
		return texTitulo;
	} 

	public void setTexTitulo(String texTitulo) {
		this.texTitulo = texTitulo;
	}

	public String getTexLegenda() {
		return texLegenda;
	} 

	public void setTexLegenda(String texLegenda) {
		this.texLegenda = texLegenda;
	}
	
	public String getTexLed() { 
		return texLed;
	} 

	public void setTexLed(String texLed) {
		this.texLed = texLed; 
	}
	

	public String getLinkImagemExtranet() {
		return linkImagemExtranet;
	} 

	public void setLinkImagemExtranet(String linkImagemExtranet) {
		this.linkImagemExtranet = linkImagemExtranet;
	}
	
	
	public String getRetrancaExtranet() {
		return retrancaExtranet;
	} 

	public void setRetrancaExtranet(String retrancaExtranet) {
		this.retrancaExtranet = retrancaExtranet;
	}
	
	
	public String getRetranca() {
		return retranca;
	} 

	public void setRetranca(String retranca) {
		this.retranca = retranca;
	}
	
	
	public String getLinkImagem() {
		return linkImagem;
	} 

	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
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
		HomeCamara other = (HomeCamara) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

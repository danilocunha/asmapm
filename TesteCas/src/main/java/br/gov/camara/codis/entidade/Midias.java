package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Midias implements Serializable {
	
    @Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="idemidias")
	private Integer id;

	@OneToOne
	@JoinColumn(name="IDENOTICIA")
	private Noticia noticia;

    private String texLegenda;
    
    private String texDuracao;
    
    private String texEtiqueta;
    
    private String texUrlImagem;
    
    private String texUrlMidiaQAlta;
    
    private String texUrlMidiaQPadrao;
    
    private String texUrlVideo;

	private String texUrlAudio;
    
    private String texCreditosVideo;
    
    private String texCreditosAudio;


    public String getTexUrlVideo() {
		return texUrlVideo;
	}

	public void setTexUrlVideo(String texUrlVideo) {
		this.texUrlVideo = texUrlVideo;
	}

	public String getTexUrlAudio() {
		return texUrlAudio;
	}

	public void setTexUrlAudio(String texUrlAudio) {
		this.texUrlAudio = texUrlAudio;
	}

	public String getTexCreditosVideo() {
		return texCreditosVideo;
	}

	public void setTexCreditosVideo(String creditos) {
		this.texCreditosVideo = creditos;
	}
	
    public String getTexCreditosAudio() {
		return texCreditosAudio;
	}

	public void setTexCreditosAudio(String texCreditosAudio) {
		this.texCreditosAudio = texCreditosAudio;
	}
    

	private static final long serialVersionUID = 1L;

	public Midias() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idemidia) {
		this.id = idemidia;
	}

	public Noticia getNoticia(){
		return this.noticia;
	}
	
	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
	public String getTexLegenda() {
		return this.texLegenda;
	}

	public void setTexLegenda(String texLegenda) {
		this.texLegenda = texLegenda;
	}

	public String getTexDuracao() {
		return this.texDuracao;
	}

	public void setTexDuracao(String texDuracao) {
		this.texDuracao = texDuracao;
	}	
	
	public String getTexEtiqueta() {
		return this.texEtiqueta;
	}

	public void setTexEtiqueta(String texEtiqueta) {
		this.texEtiqueta = texEtiqueta;
	}

	public String getTexUrlImagem() {
		return this.texUrlImagem;
	}

	public void setTexUrlImagem(String texUrlImagem) {
		this.texUrlImagem = texUrlImagem;
	}

	public String getTexUrlMidiaQAlta() {
		return this.texUrlMidiaQAlta;
	}

	public void setTexUrlMidiaQAlta(String texUrlMidiaQAlta) {
		this.texUrlMidiaQAlta = texUrlMidiaQAlta;
	}
	
	public String getTexUrlMidiaQPadrao() {
		return this.texUrlMidiaQPadrao;
	}

	public void setTexUrlMidiaQPadrao(String texUrlMidiaQPadrao) {
		this.texUrlMidiaQPadrao = texUrlMidiaQPadrao;
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
		Midias other = (Midias) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TipoBloco implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idetipobloco")
	private Integer id;

	private String texNome;

	private Boolean indImagem;

	private Boolean indVideo;

	private Boolean indAudio;

	private Boolean indRetranca;

	private Boolean indTitulo;

	private Boolean indResumo;

	@ManyToOne
	@JoinColumn(name="IDETIPOAREA")
	private TipoArea tipoArea;

	@OneToMany(mappedBy="tipoBloco")
	private Set<Bloco> blocoCollection;

	private static final long serialVersionUID = 1L;

	public TipoBloco() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idetipobloco) {
		this.id = idetipobloco;
	}

	public String getTexNome() {
		return this.texNome;
	}

	public void setTexNome(String texnome) {
		this.texNome = texnome;
	}

	public Boolean isIndImagem() {
		return this.indImagem;
	}

	public void setIndImagem(Boolean indimagem) {
		this.indImagem = indimagem;
	}

	public Boolean isIndVideo() {
		return this.indVideo;
	}

	public void setIndVideo(Boolean indvideo) {
		this.indVideo = indvideo;
	}

	public Boolean isIndAudio() {
		return this.indAudio;
	}

	public void setIndAudio(Boolean indaudio) {
		this.indAudio = indaudio;
	}

	public Boolean isIndRetranca() {
		return this.indRetranca;
	}

	public void setIndRetranca(Boolean indretranca) {
		this.indRetranca = indretranca;
	}

	public Boolean isIndTitulo() {
		return this.indTitulo;
	}

	public void setIndTitulo(Boolean indtitulo) {
		this.indTitulo = indtitulo;
	}

	public Boolean isIndResumo() {
		return this.indResumo;
	}

	public void setIndResumo(Boolean indresumo) {
		this.indResumo = indresumo;
	}

	public TipoArea getTipoArea() {
		return this.tipoArea;
	}

	public void setTipoArea(TipoArea tipoArea) {
		this.tipoArea = tipoArea;
	}

	public Set<Bloco> getBlocoCollection() {
		return this.blocoCollection;
	}

	public void setBlocoCollection(Set<Bloco> blocoCollection) {
		this.blocoCollection = blocoCollection;
	}

}

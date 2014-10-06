package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class NoticiaBloco implements Serializable, DomainObject<Integer>, Cloneable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idenoticiabloco")
	private Integer id;
	
	private String texTitulo;

	private String texResumo;
	
	private String texMateria;

	private String urlImagem;
	
	private String urlImagem2;

	@Enumerated(EnumType.ORDINAL)
	private PosicaoImagemEnum indPosicaoImagem;

	private String texLegendaImagem;

	private String texRetranca;

	private Integer numPosicao;
	
	private Boolean indPrincipal;

	private Timestamp datUltimaAlteracao;

	private String texPontoUsuario;
	
	private String texURL;
	
	private String texTamanhoFonte;
	
	private String texPosicaoTitulo;
	
	private String texImgALT;
	
	private String texTituloAlternativo;

	@ManyToOne
	@JoinColumn(name="IDENOTICIA")
	private Noticia noticia;
	
	@ManyToOne
	@JoinColumn(name="IDEBLOCO")
	private Bloco bloco;

	private static final long serialVersionUID = 1L;

	public NoticiaBloco() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idenoticiabloco) {
		this.id = idenoticiabloco;
	}

	public Bloco getBloco() {
		return this.bloco;
	}

	public void setBloco(Bloco bloco) {
		this.bloco = bloco;
	}

	public String getTexTitulo() {
		return this.texTitulo;
	}

	public void setTexTitulo(String textitulo) {
		this.texTitulo = textitulo;
	}

	public String getTexResumo() {
		return this.texResumo;
	}

	public void setTexResumo(String texresumo) {
		this.texResumo = texresumo;
	}

	public String getUrlImagem() {
		return this.urlImagem;
	}

	public void setUrlImagem(String urlimagem) {
		this.urlImagem = urlimagem;
	}
	
	public String getUrlImagem2() {
		return this.urlImagem2;
	}

	public void setUrlImagem2(String urlimagem2) {
		this.urlImagem2 = urlimagem2;
	}

	public PosicaoImagemEnum getIndPosicaoImagem() {
		return this.indPosicaoImagem;
	}

	public void setIndPosicaoImagem(PosicaoImagemEnum indposicaoimagem) {
		this.indPosicaoImagem = indposicaoimagem;
	}

	public String getTexLegendaImagem() {
		return this.texLegendaImagem;
	}

	public void setTexLegendaImagem(String texlegendaimagem) {
		this.texLegendaImagem = texlegendaimagem;
	}

	public String getTexRetranca() {
		return this.texRetranca;
	}

	public void setTexRetranca(String texretranca) {
		this.texRetranca = texretranca;
	}

	public Integer getNumPosicao() {
		return this.numPosicao;
	}

	public void setNumPosicao(Integer numposicao) {
		this.numPosicao = numposicao;
	}

	public Timestamp getDatUltimaAlteracao() {
		return this.datUltimaAlteracao;
	}

	public void setDatUltimaAlteracao(Timestamp datultimaalteracao) {
		this.datUltimaAlteracao = datultimaalteracao;
	}

	public String getTexPontoUsuario() {
		return this.texPontoUsuario;
	}

	public void setTexPontoUsuario(String texpontousuario) {
		this.texPontoUsuario = texpontousuario;
	}
	
	public String getTexURL() {
		return this.texURL;
	}

	public void setTexURL(String texURL) {
		this.texURL = texURL;
	}
	
	public String getTexTamanhoFonte() {
		return this.texTamanhoFonte;
	}

	public void setTexTamanhoFonte(String texTamanhoFonte) {
		this.texTamanhoFonte = texTamanhoFonte;
	}
	
	public String getTexPosicaoTitulo() {
		return this.texPosicaoTitulo;
	}

	public void setTexPosicaoTitulo(String texPosicaoTitulo) {
		this.texPosicaoTitulo = texPosicaoTitulo;
	}

	
	public Noticia getNoticia() {
		return this.noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public void setIndPrincipal(Boolean indPrincipal) {
		this.indPrincipal = indPrincipal;
	}

	public Boolean getIndPrincipal() {
		return indPrincipal;
	}
	
	@Override
	public NoticiaBloco clone() throws CloneNotSupportedException {
		NoticiaBloco clone = new NoticiaBloco();
		clone.setDatUltimaAlteracao(getDatUltimaAlteracao());
		clone.setIndPosicaoImagem(getIndPosicaoImagem());
		clone.setIndPrincipal(getIndPrincipal());
		clone.setNoticia(getNoticia());
		clone.setNumPosicao(getNumPosicao());
		clone.setTexLegendaImagem(getTexLegendaImagem());
		clone.setTexPontoUsuario(getTexPontoUsuario());
		clone.setTexResumo(getTexResumo());
		clone.setTexRetranca(getTexRetranca());
		clone.setTexMateria(getTexMateria());
		clone.setTexTitulo(getTexTitulo());
		clone.setUrlImagem(getUrlImagem());
		clone.setUrlImagem2(getUrlImagem2());
		clone.setTexURL(getTexURL());
		clone.setTexTituloAlternetivo(getTexTituloAlternativo());
		clone.setTexImgALT(getTexImgALT());
		clone.setTexPosicaoTitulo(getTexPosicaoTitulo());
		clone.setTexTamanhoFonte(getTexTamanhoFonte());
		return clone;
	}

	public String getTexMateria() {
		return texMateria;
	}

	public void setTexMateria(String texMateria) {
		this.texMateria = texMateria;
	}
	
	public String getTexImgALT() {
		return texImgALT;
	}

	public void setTexImgALT(String texImgALT) {
		this.texImgALT = texImgALT;
	}
	
	public String getTexTituloAlternativo() {
		return texTituloAlternativo;
	}

	public void setTexTituloAlternetivo(String texTextoAlternativo) {
		this.texTituloAlternativo = texTextoAlternativo;
	}
}

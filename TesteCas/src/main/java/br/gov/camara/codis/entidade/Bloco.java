package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Bloco implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idebloco")
	private Integer id;

	private int numPosicao;

	private int indBordaSuperior;

	private int indBordaInferior;

	private int indBordaDireita;

	private int indBordaEsquerda;
	
	private Integer indCorFundo;
	
	private Integer ideCanal;
	
	private String texLegenda;

	private Timestamp datUltimaAlteracao;

	private String texPontoUsuario;

	private String texTitulo;
	
	private String texURL;
	
	private String texImgURL;
	
	private String texTamanhoFonte;

	@ManyToOne
	@JoinColumn(name = "IDEAREA")
	private Area area;

	@ManyToOne
	@JoinColumn(name = "IDETIPOBLOCO")
	private TipoBloco tipoBloco;

	@ManyToOne
	@JoinColumn(name = "IDEBLOCOPAI")
	private Bloco blocoPai;

	@ManyToOne
	@JoinColumn(name = "IDERETRANCA")
	private Retranca retranca;
	
	@ManyToOne
	@JoinColumn(name = "IDETEMADODIA")
	private TemaDoDia temaDoDia;

	@OneToMany(mappedBy = "bloco", cascade = CascadeType.ALL)
	@OrderBy("numPosicao")
	private List<NoticiaBloco> noticiaBlocoCollection;
	
	@OneToMany(mappedBy = "blocoPai", cascade = CascadeType.ALL)
	@OrderBy("id")
	private List<Bloco> blocoFilhoCollection;

	private static final long serialVersionUID = 1L;

	public Bloco() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumPosicao() {
		return numPosicao;
	}

	public void setNumPosicao(int numPosicao) {
		this.numPosicao = numPosicao;
	}

	public int getIndBordaSuperior() {
		return indBordaSuperior;
	}

	public void setIndBordaSuperior(int indBordaSuperior) {
		this.indBordaSuperior = indBordaSuperior;
	}

	public int getIndBordaInferior() {
		return indBordaInferior;
	}

	public void setIndBordaInferior(int indBordaInferior) {
		this.indBordaInferior = indBordaInferior;
	}

	public int getIndBordaDireita() {
		return indBordaDireita;
	}

	public void setIndBordaDireita(int indBordaDireita) {
		this.indBordaDireita = indBordaDireita;
	}

	public int getIndBordaEsquerda() {
		return indBordaEsquerda;
	}

	public void setIndBordaEsquerda(int indBordaEsquerda) {
		this.indBordaEsquerda = indBordaEsquerda;
	}

	public Timestamp getDatUltimaAlteracao() {
		return datUltimaAlteracao;
	}

	public void setDatUltimaAlteracao(Timestamp datUltimaAlteracao) {
		this.datUltimaAlteracao = datUltimaAlteracao;
	}

	public String getTexPontoUsuario() {
		return texPontoUsuario;
	}

	public void setTexPontoUsuario(String texPontoUsuario) {
		this.texPontoUsuario = texPontoUsuario;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public TipoBloco getTipoBloco() {
		return tipoBloco;
	}

	public void setTipoBloco(TipoBloco tipoBloco) {
		this.tipoBloco = tipoBloco;
	}

	public void setNoticiaBlocoCollection(
			List<NoticiaBloco> noticiaBlocoCollection) {
		this.noticiaBlocoCollection = noticiaBlocoCollection;
	}

	public List<NoticiaBloco> getNoticiaBlocoCollection() {
		if (noticiaBlocoCollection == null) {
			noticiaBlocoCollection = new ArrayList<NoticiaBloco>();
		}
		return noticiaBlocoCollection;
	}

	public String getTexTitulo() {
		return texTitulo;
	}

	public void setTexTitulo(String texTitulo) {
		this.texTitulo = texTitulo;
	}
	
	public String getTexImgURL() {
		return texImgURL;
	}

	public void setTexURL(String texURL) {
		this.texURL = texURL;
	}
	
	public String getTexURL() {
		return texURL;
	}

	public void setTexImgURL(String texImgURL) {
		this.texImgURL = texImgURL;
	}
	
	//setTexImgURL
	
	public Bloco getBlocoPai() {
		return blocoPai;
	}

	public void setBlocoPai(Bloco blocoPai) {
		this.blocoPai = blocoPai;
	}

	public Retranca getRetranca() {
		return retranca;
	}

	public void setRetranca(Retranca retranca) {
		this.retranca = retranca;
	}

	public List<Bloco> getBlocoFilhoCollection() {
		if (blocoFilhoCollection == null) {
			blocoFilhoCollection = new ArrayList<Bloco>();
		}
		return blocoFilhoCollection;
	}

	public void setBlocoFilhoCollection(List<Bloco> blocoFilhoCollection) {
		this.blocoFilhoCollection = blocoFilhoCollection;
	}

	public Bloco clone(Area areaClone) throws CloneNotSupportedException {
		/**
		 * TODO: usar reflection para tornar a manutenção do código mais simples.
		 */
		Bloco blocoClone = new Bloco();
		areaClone.getBlocoCollection().add(blocoClone);
		blocoClone.setArea(areaClone);
		blocoClone.setDatUltimaAlteracao(getDatUltimaAlteracao());
		blocoClone.setIndBordaDireita(getIndBordaDireita());
		blocoClone.setIndBordaEsquerda(getIndBordaEsquerda());
		blocoClone.setIndBordaInferior(getIndBordaInferior());
		blocoClone.setIndBordaSuperior(getIndBordaSuperior());
		blocoClone.setNumPosicao(getNumPosicao());
		blocoClone.setRetranca(getRetranca());
		blocoClone.setTexPontoUsuario(getTexPontoUsuario());
		blocoClone.setTexTitulo(getTexTitulo());
		blocoClone.setTipoBloco(getTipoBloco());
		blocoClone.setTemaDoDia(getTemaDoDia());
		blocoClone.setIdeCanal(getIdeCanal());
		blocoClone.setIndCorFundo(getIndCorFundo());
		blocoClone.setTexLegenda(getTexLegenda());
		blocoClone.setTexURL(getTexURL());
		blocoClone.setTexImgURL(getTexImgURL());
		blocoClone.setTexTamanhoFonte(getTexTamanhoFonte());

		
		for (Bloco blocoFilho : getBlocoFilhoCollection()) {
			Bloco blocoFilhoClone = blocoFilho.clone(areaClone);
			blocoFilhoClone.setBlocoPai(blocoClone);
			blocoClone.getBlocoFilhoCollection().add(blocoFilhoClone);
		}

		for (NoticiaBloco noticiaBloco : getNoticiaBlocoCollection()) {
			NoticiaBloco noticiaBlocoClone = noticiaBloco.clone();
			noticiaBlocoClone.setBloco(blocoClone);
			blocoClone.getNoticiaBlocoCollection().add(
					noticiaBlocoClone);
		}
		
		return blocoClone;
	}

	public Integer getIndCorFundo() {
		return indCorFundo;
	}

	public void setIndCorFundo(Integer indCorFundo) {
		this.indCorFundo = indCorFundo;
	}

	public TemaDoDia getTemaDoDia() {
		return temaDoDia;
	}

	public void setTemaDoDia(TemaDoDia temaDoDia) {
		this.temaDoDia = temaDoDia;
	}

	public Integer getIdeCanal() {
		return ideCanal;
	}

	public void setIdeCanal(Integer ideCanal) {
		this.ideCanal = ideCanal;
	}

	public String getTexLegenda() {
		return texLegenda;
	}

	public void setTexLegenda(String texLegenda) {
		this.texLegenda = texLegenda;
	}
	
	public String getTexTamanhoFonte() {
		return texTamanhoFonte;
	}

	public void setTexTamanhoFonte(String tamanho) {
		this.texTamanhoFonte = tamanho;
	}
}

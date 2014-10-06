package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.Hibernate;

@Entity
public class Noticia implements Serializable, DomainObject<Integer> 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idenoticia")
	private Integer id;

	@OneToOne(mappedBy="noticia", cascade=CascadeType.ALL)
	private EnqueteAutomatica enqueteAutomatica;

	@OneToOne(mappedBy="noticia", cascade=CascadeType.ALL)
	private Midias midias;
	
	private String texTitulo; 

	private String texResumo;

	private String texMateria;

	private Date datMateria;

	private Boolean indVisivelBoletim;
	
	private Boolean indComentario;

	private Boolean indVisivelAgencia;

	private String texPesquisa;
	
	private String texRodape;

	private Date datUltimaAlteracao;
	
	private Date datEstreia;
	
	private Date datPublicacao;
	
	private Date datAtualizacao;

	private String texPontoUsuario;
	
	private Integer numBloco;
	
	private String numEnquete;
	
	private Date datControleEdicao;
	
	private String texUsuarioEdicao;
	
	private Boolean indVisivelCongresso;
	
	@Transient
	private Boolean indMancheteDoDia = false;
	 
	@ManyToOne
	@JoinColumn(name="IDESTATUSNOTICIA")
	private StatusNoticia statusNoticia;
	
	@ManyToOne
	@JoinColumn(name="IDESERVICO")
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name="IDETIPONOTICIA")
	private TipoNoticia tipoNoticia;
	
	@ManyToOne
	@JoinColumn(name="IDEMIDIA")
	private Midia midia;
	
	@ManyToOne
	@JoinColumn(name="IDETEMADODIA")
	private TemaDoDia temaDoDia;
	
	@ManyToOne
	@JoinColumn(name="IDERETRANCAPRINCIPAL")
	private Retranca retrancaPrincipal;

	@OneToMany(mappedBy="pk.ideNoticia", cascade=CascadeType.ALL)
	private Set<DeputadoNoticia> deputadoNoticiaCollection;

	@OneToMany(mappedBy="pk.ideNoticia", cascade=CascadeType.ALL)
	private Set<NoticiaAcesso> noticiaAcessoCollection;

	@OneToMany(mappedBy="noticia", cascade=CascadeType.ALL)
	private Set<NoticiaBloco> noticiaBlocoCollection;
	
	@OneToMany(mappedBy="pk.ideNoticia", cascade=CascadeType.ALL)
	private List<NoticiaProposicao> noticiaProposicaoCollection;
	
	@OneToMany(mappedBy="noticia", cascade=CascadeType.ALL)
	private Set<AssuntoBoletim> assuntoBoletimCollection;
	
	@OneToMany(mappedBy="noticia", cascade=CascadeType.ALL)
	private Set<MancheteDoDiaBoletim> mancheteDoDiaBoletimCollection;
	
	@OneToMany(mappedBy="noticia", cascade=CascadeType.ALL)
	private Set<NoticiaBoletim> noticiaBoletimCollection;
	
	@OneToMany(mappedBy="noticia", cascade=CascadeType.ALL)
	private Set<NoticiaArquivoAnexo> noticiaArquivoAnexoCollection; 

	@ManyToMany
	@JoinTable(name="DESTAQUEHOMETEMATICA",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDERETRANCA"))
	private Set<Retranca> destaqueHomeTematicaCollection;

	@ManyToMany
	@JoinTable(name="DESTAQUEHOMEPROGRAMA",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDEPROGRAMA"))
	private Set<Programa> destaqueHomeProgramaCollection;
	
	@ManyToMany
	@JoinTable(name="NOTICIACONTINUACAO",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDENOTICIACONTINUACAO"))
	private List<Noticia> noticiaContinuacaoCollection;
	
	@ManyToMany(mappedBy="noticiaContinuacaoCollection")
	private List<Noticia> noticiaContinuacaoPaiCollection;

	@ManyToMany
	@JoinTable(name="NOTICIARELACIONADA",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDENOTICIARELACIONADA"))
	private List<Noticia> noticiaRelacionadaCollection;
	
	@ManyToMany(mappedBy="noticiaRelacionadaCollection")
	private List<Noticia> noticiaRelacionadaPaiCollection;

	@ManyToMany
	@JoinTable(name="NOTICIARETRANCA",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDERETRANCA"))
	@OrderBy("texDescricao asc")
	private Set<Retranca> retrancaCollection;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="NOTICIAPROGRAMA",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDEPROGRAMA"))
	//@OrderBy("nomPrograma asc") // Mudanca #13113
	private Set<Programa> programaCollection;
	
	@ManyToMany
	@JoinTable(name="NOTICIATAG",
		joinColumns=@JoinColumn(name="IDENOTICIA"),
		inverseJoinColumns=@JoinColumn(name="IDETAG"))
	@OrderBy("texDescricao asc")
	private Set<Tag> tagCollection;


	private static final long serialVersionUID = 1L;

	public Noticia() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idenoticia) {
		this.id = idenoticia;
	}
	
	public EnqueteAutomatica getEnqueteAutomatica() {
		return enqueteAutomatica;
	}

	public void setEnqueteAutomatica(EnqueteAutomatica enqueteAutomatica) {
		this.enqueteAutomatica = enqueteAutomatica;
	}

	public Midias getMidias() {
		return this.midias;
	}

	public void setMidias(Midias midias) {
		this.midias = midias;
	}
	
	public String getTexTitulo() {

		return this.texTitulo;
	}
	
	public String getTituloEServico() {

		return this.texTitulo +" - "+ this.servico.getTexNomeVeiculo();
	}
	
	public String getSimboloServico() {

		String simbolo = "";
		if(this.servico.getId() == 1) simbolo = "ag";
		if(this.servico.getId() == 2) simbolo = "tv";
		if(this.servico.getId() == 3) simbolo = "ra";
		
		return " ("+ simbolo +") ";
	}

	public String getTituloEBloco() {
		
	    String ret = this.texTitulo;
	    
	    if( this.numBloco != null)
	    	ret = ret + " - Bloco " + this.numBloco;
		return ret;
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

	public String getTexMateria() {
		return this.texMateria;
	}

	public void setTexMateria(String texmateria) {
		this.texMateria = texmateria;
	}

	public Date getDatMateria() {
		return this.datMateria;
	}

	public void setDatMateria(Date datmateria) {
		this.datMateria = datmateria;
	}

	public Date getDatEstreia() {
		return this.datEstreia;
	}

	public void setDatEstreia(Date datestreia) {
		this.datEstreia = datestreia;
	}
	
	public Date getDatPublicacao() {
		return this.datPublicacao;
	}

	public void setDatPublicacao(Date datpublicacao) {
		this.datPublicacao = datpublicacao;
	}	
	
	public Date getDatAtualizacao() {
		return this.datAtualizacao;
	}

	public void setDatAtualizacao(Date datatualizacao) {
		this.datAtualizacao = datatualizacao;
	}
	public Boolean getIndVisivelBoletim() {
		return this.indVisivelBoletim;
	}

	public void setIndVisivelBoletim(Boolean indvisivelboletim) {
		this.indVisivelBoletim = indvisivelboletim;
	}
	
	public Boolean getIndMancheteDoDia() {
		return this.indMancheteDoDia;
	} 

	public void setIndMancheteDoDia(Boolean indMancheteDoDia) {
		this.indMancheteDoDia = indMancheteDoDia;  
	}
	
	public Boolean getIndComentario() {
		return this.indComentario;
	}

	public void setIndComentario(Boolean indComentario) {
		this.indComentario = indComentario;
	}
	
	public Boolean getIndVisivelAgencia() {
		return this.indVisivelAgencia;
	}

	public void setIndVisivelAgencia(Boolean indvisivelagencia) {
		this.indVisivelAgencia = indvisivelagencia;
	}
	
	public String getTexPesquisa() {
		return this.texPesquisa;
	}

	public void setTexPesquisa(String texpesquisa) {
		this.texPesquisa = texpesquisa;
	}
	
	public String getTexRodape() {
		return this.texRodape;
	}
	
	public void setTexRodape(String texRodape) {
		this.texRodape = texRodape; 
	}

	public Date getDatUltimaAlteracao() {
		return this.datUltimaAlteracao;
	}

	public void setDatUltimaAlteracao(Date datultimaalteracao) {
		this.datUltimaAlteracao = datultimaalteracao;
	}

	public String getTexPontoUsuario() {
		return this.texPontoUsuario;
	}

	public void setTexPontoUsuario(String texpontousuario) {
		this.texPontoUsuario = texpontousuario;
	}
	
	public String getNumEnquete() {
		return this.numEnquete;
	}

	public void setNumEnquete(String numEnquete) {
		this.numEnquete = numEnquete;
	}

	public StatusNoticia getStatusNoticia() {
		return this.statusNoticia;
	}

	public void setStatusNoticia(StatusNoticia statusnoticia) {
		this.statusNoticia = statusnoticia;
	}
	
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public TipoNoticia getTipoNoticia() {
		return this.tipoNoticia;
	}

	public void setTipoNoticia(TipoNoticia tiponoticia) {
		this.tipoNoticia = tiponoticia;
	}

	public Set<NoticiaBoletim> getNoticiaBoletimCollection() {
		return this.noticiaBoletimCollection;
	}

	public void setNoticiaBoletimCollection(Set<NoticiaBoletim> noticiaBoletimCollection) {
		this.noticiaBoletimCollection = noticiaBoletimCollection;
	}
	
	public Set<NoticiaArquivoAnexo> getNoticiaArquivoAnexoCollection() {
		if (this.noticiaArquivoAnexoCollection==null) {
			this.noticiaArquivoAnexoCollection = new HashSet<NoticiaArquivoAnexo>();
		}		
		return this.noticiaArquivoAnexoCollection;
	}

	public void setNoticiaArquivoAnexoCollection(Set<NoticiaArquivoAnexo> noticiaArquivoAnexoCollection) {
		this.noticiaArquivoAnexoCollection = noticiaArquivoAnexoCollection;
	}
	
	public Set<DeputadoNoticia> getDeputadoNoticiaCollection() {
		if (this.deputadoNoticiaCollection==null) {
			this.deputadoNoticiaCollection = new HashSet<DeputadoNoticia>();
		}
		return this.deputadoNoticiaCollection;
	}

	public void setDeputadoNoticiaCollection(Set<DeputadoNoticia> deputadoNoticiaCollection) {
		this.deputadoNoticiaCollection = deputadoNoticiaCollection;

	}

	public Set<NoticiaAcesso> getNoticiaAcessoCollection() {
		return this.noticiaAcessoCollection;
	}

	public void setNoticiaAcessoCollection(Set<NoticiaAcesso> noticiaacessoCollection) {
		this.noticiaAcessoCollection = noticiaacessoCollection;
	}

	public Set<NoticiaBloco> getNoticiaBlocoCollection() {
		return this.noticiaBlocoCollection;
	}

	public void setNoticiaBlocoCollection(Set<NoticiaBloco> noticiablocoCollection) {
		this.noticiaBlocoCollection = noticiablocoCollection;
	}

	public Set<Programa> getDestaqueHomeProgramaCollection() {
		if (destaqueHomeProgramaCollection == null) {
			destaqueHomeProgramaCollection = new HashSet<Programa>();
		}
		return this.destaqueHomeProgramaCollection;
	}

	public void setDestaqueHomeProgramaCollection(Set<Programa> programaCollection) {
		this.destaqueHomeProgramaCollection = programaCollection;
	}

	public Set<Retranca> getDestaqueHomeTematicaCollection() {
		if (destaqueHomeTematicaCollection == null) {
			destaqueHomeTematicaCollection = new HashSet<Retranca>();
		}
		return this.destaqueHomeTematicaCollection;
	}

	public void setDestaqueHomeTematicaCollection(Set<Retranca> retrancaCollection) {
		this.destaqueHomeTematicaCollection = retrancaCollection;
	}
	
	
	public Set<Retranca> getRetrancaCollection() {
		if (retrancaCollection == null) {
			retrancaCollection = new HashSet<Retranca>();
		}
		return this.retrancaCollection;
	}

	public void setRetrancaCollection(Set<Retranca> retrancaCollection2) {
		this.retrancaCollection = retrancaCollection2;
	}

	
	public Set<Programa> getProgramaCollection() {
		if (programaCollection == null) {
			programaCollection = new HashSet<Programa>();
		}
		return this.programaCollection;
	}

	public void setProgramaCollection(Set<Programa> programaCollection) {
		this.programaCollection = programaCollection;
	}	
	
	public Set<Tag> getTagCollection() {
		if (tagCollection==null) {
			tagCollection = new HashSet<Tag>();
		}
		return this.tagCollection;
	}

	public void setTagCollection(Set<Tag> tagCollection) {
		this.tagCollection = tagCollection;
	}

	public List<Noticia> getNoticiaContinuacaoCollection() {
		if (noticiaContinuacaoCollection == null) {
			noticiaContinuacaoCollection = new ArrayList<Noticia>();
		}
		return this.noticiaContinuacaoCollection;
	}

	public void setNoticiaContinuacaoCollection(List<Noticia> noticiaCollection3) {
		this.noticiaContinuacaoCollection = noticiaCollection3;
	}

	public List<Noticia> getNoticiaRelacionadaCollection() {
		if (noticiaRelacionadaCollection == null) {
			noticiaRelacionadaCollection = new ArrayList<Noticia>();
		}
		return this.noticiaRelacionadaCollection;
	}

	public void setNoticiaRelacionadaCollection(List<Noticia> noticiaCollection) {
		this.noticiaRelacionadaCollection = noticiaCollection;
	}
	
	public List<Noticia> getNoticiaRelacionadaPaiCollection() {
		if (noticiaRelacionadaPaiCollection == null) {
			noticiaRelacionadaPaiCollection = new ArrayList<Noticia>();
		}
		return this.noticiaRelacionadaPaiCollection;
	}

	public void setNoticiaRelacionadaPaiCollection(List<Noticia> noticiaCollection) {
		this.noticiaRelacionadaPaiCollection = noticiaCollection;
	}

	public List<NoticiaProposicao> getNoticiaProposicaoCollection() {
		if (noticiaProposicaoCollection == null) {
			noticiaProposicaoCollection = new ArrayList<NoticiaProposicao>();
		}
		return noticiaProposicaoCollection;
	}

	public void setNoticiaProposicaoCollection(
			List<NoticiaProposicao> noticiaProposicaoCollection) {
		this.noticiaProposicaoCollection = noticiaProposicaoCollection;
	}

	public Set<AssuntoBoletim> getAssuntoBoletimCollection() {
		return assuntoBoletimCollection;
	}

	public void setAssuntoBoletimCollection(
			Set<AssuntoBoletim> assuntoBoletimCollection) {
		this.assuntoBoletimCollection = assuntoBoletimCollection;
	}

	public Set<MancheteDoDiaBoletim> getMancheteDoDiaBoletimCollection() {
		return mancheteDoDiaBoletimCollection;
	}

	public void setMancheteDoDiaBoletimCollection(
			Set<MancheteDoDiaBoletim> mancheteDoDiaBoletimCollection) {
		this.mancheteDoDiaBoletimCollection = mancheteDoDiaBoletimCollection;
	}

	public Retranca getRetrancaPrincipal() {
		return retrancaPrincipal;
	}

	public void setRetrancaPrincipal(Retranca retrancaPrincipal) {
		this.retrancaPrincipal = retrancaPrincipal;
	}

	public TemaDoDia getTemaDoDia() {
		return temaDoDia;
	}

	public void setTemaDoDia(TemaDoDia temaDoDia) {
		this.temaDoDia = temaDoDia;
	}
	
	public Integer getNumBloco() {
		return numBloco;
	}

	public void setNumBloco(Integer numBloco) {
		this.numBloco = numBloco;
	}
	
	public Date getDatControleEdicao() {
		return this.datControleEdicao;
	}

	public void setDatControleEdicao(Date datEdicao) {
		this.datControleEdicao = datEdicao;
	}
	
	public String getTexUsuarioEdicao() {
		return this.texUsuarioEdicao;
	}

	public void setTexUsuarioEdicao(String texUsuario) {
		this.texUsuarioEdicao = texUsuario;
	}
	
	public Boolean getIndVisivelCongresso() {
		return this.indVisivelCongresso;
	}

	public void setIndVisivelCongresso(Boolean indVisivelCongresso) {
		this.indVisivelCongresso = indVisivelCongresso;
	}
	
	
	/**
	 * Método que monta o nome do arquivo html, de acordo com regras para indexação
	 * em sistemas de busca.
	 */ 
	public String getNomeArquivoHtml() {
		String textoRetranca = null;
		
		// Se Agência Câmara, mostra Retranca Principal
		if( getServico().getId().intValue() == 1) {
			if (getRetrancaPrincipal() != null) {
				textoRetranca = getRetrancaPrincipal().getTexDescricao();
				
			}
		
		// Se Tv ou Rádio, mostra nome do programa
		} else { 
		  Iterator<Programa> it = getProgramaCollection().iterator();
		  	if( it.hasNext()) {
		  		textoRetranca = it.next().getNomPrograma();
		  		
		  	} else {
		  		textoRetranca = "";  
		  		
		  	}
		}
	return null;
	}
	
	public String getNomeDiretorioNoticia() {
		String diretorio = null;
		//Agencia
		if( getServico().getId().intValue() == 1) {
			diretorio = "/noticias";
		} else { 
			diretorio = "/materias";
		}
		return diretorio;
	}
	
	public List<Noticia> getNoticiaContinuacaoPaiCollection() {
		if (noticiaContinuacaoPaiCollection == null) {
			noticiaContinuacaoPaiCollection = new ArrayList<Noticia>();
		}
		return noticiaContinuacaoPaiCollection;
	}

	public void setNoticiaContinuacaoPaiCollection(
			List<Noticia> noticiaContinuacaoPaiCollection) {
		this.noticiaContinuacaoPaiCollection = noticiaContinuacaoPaiCollection;
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
		if (! Hibernate.getClass(this).equals(Hibernate.getClass(obj)))
			return false;
		Noticia other = (Noticia) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	} 

	public Midia getMidia() {
		return midia;
	}
	
	public Programa getPrograma(){
        Iterator<Programa> it = this.programaCollection.iterator();
        if( !it.hasNext())
        	return null;
        
        Programa programa =  it.next();
        
		return programa;
	}

	public String getEndArquivo(Date date) {
		GregorianCalendar calendar = new GregorianCalendar(); 
		calendar.setTime(date);
		//int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH); 
		int mes = calendar.get(GregorianCalendar.MONTH); 
		int ano = calendar.get(GregorianCalendar.YEAR);

		String strAno = String.valueOf(ano);
		//String strDia = String.valueOf(dia);
		String strMes = null;
		
		if(mes < 10 ){
			strMes = String.valueOf(mes);
			strMes = "0" + strMes;
		}else{
			strMes = String.valueOf(mes);			
		};

		String endereco = "http://imagem.camara.gov.br/internet/midias/tv/" + strAno + "/" + strMes  + "/";
		
		return endereco;
	}
	
	public String getURLCompleta()
	{
		String diretorio =  this.getNomeDiretorioNoticia();
		String arquivo = this.getNomeArquivoHtml();
		
		return diretorio.replace("/", "") +"/"+ arquivo; 
	}
	
	private String getTitulo() {
		
		String titulo = "";
		titulo = this.texTitulo;
		
		if((this.tipoNoticia.getId() == TipoNoticiaEnum.BLOCO_PROGRAMA_RADIO.getIdeTipoNoticia() || this.tipoNoticia.getId() == TipoNoticiaEnum.BLOCO_PROGRAMA_TV.getIdeTipoNoticia() ) && this.numBloco != null && this.numBloco > 0 ) {
			   titulo = titulo + "-Bloco-" + this.numBloco;
	
		}
		
		return titulo;
	}
	

}

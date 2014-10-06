package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Programa implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idePrograma")

	private Integer id;

	private String nomPrograma;
	
	private String texDescricao;
	
	private String texDescricaoResumida;	
	
	private String texUrlImagem;
	
	private String texHorario;
	
	private Boolean indAcervo;
	
	private Boolean indDownload;
	
	private Boolean indJornalistico;
	
	private Integer ideCategoria;
	
	private Integer numDestaques;

	@ManyToOne
	@JoinColumn(name="IDESERVICO")
	private Servico servico;

	@ManyToMany(mappedBy="programaCollection")
	private Set<Noticia> noticiaCollection;

	@ManyToMany(mappedBy="destaqueHomeProgramaCollection")
	private Set<Noticia> noticiaDestaqueCollection;
	
	private static final long serialVersionUID = 1L;

	public Programa() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idePrograma) {
		this.id = idePrograma;
	}
	
	public String getNomPrograma() {
		return this.nomPrograma;
	}

	public void setNomPrograma(String nomPrograma) {
		this.nomPrograma = nomPrograma;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}
	
	public String getTexDescricaoResumida() {
		return this.texDescricaoResumida;
	}	

	public void setTexDescricaoResumida(String texDescricaoResumida) {
		this.texDescricaoResumida = texDescricaoResumida;
	}

	public String getTexUrlImagem() {
		return this.texUrlImagem;
	}

	public void setTexUrlImagem(String texUrlImagem) {
		this.texUrlImagem = texUrlImagem;
	}	
	
	public String getTexHorario() {
		return this.texHorario;
	}

	public void setTexHorario(String texHorario) {
		this.texHorario = texHorario;
	}
	
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico ideservico) {
		this.servico = ideservico;
	}
	
	public Integer getIdeCategoria() {
		return this.ideCategoria;
	}
	
	public void setIdeCategoria(Integer ideCategoria) {
		this.ideCategoria = ideCategoria;
	}
	
	public Set<Noticia> getNoticiaCollection() {
		return this.noticiaCollection;
	}

	public void setNoticiaCollection(Set<Noticia> noticiaCollection) {
		this.noticiaCollection = noticiaCollection;
	}

	public Set<Noticia> getNoticiaDestaqueCollection() {
		return this.noticiaDestaqueCollection;
	}

	public void setNoticiaDestaqueCollection(Set<Noticia> noticiaCollection2) {
		this.noticiaDestaqueCollection = noticiaCollection2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : id.hashCode());
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
		Programa other = (Programa) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public Boolean getIndAcervo() {
		return indAcervo;
	}

	public void setIndAcervo(Boolean indAcervo) {
		this.indAcervo = indAcervo;
	}

	public Boolean getIndDownload() {
		return indDownload;
	}

	public void setIndDownload(Boolean indDownload) {
		this.indDownload= indDownload;
	}
	
	public Boolean getIndJornalistico() {
		return indJornalistico;
	}

	public void setIndJornalistico(Boolean indJornalistico) {
		this.indJornalistico= indJornalistico;
	}
	
	public Integer getNumDestaques() {
		return this.numDestaques;
	}
	
	public void setNumDestaques(Integer num) {
		this.numDestaques = num;
	}
	
}

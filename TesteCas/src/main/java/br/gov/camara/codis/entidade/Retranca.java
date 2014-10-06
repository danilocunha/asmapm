package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Retranca implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideretranca")
	private Integer id;

	private String texDescricao;
	
	private Boolean indHomeTematica;
	
	private Boolean indBoletim;

	@ManyToOne
	@JoinColumn(name="IDESERVICO")
	private Servico servico;

	@ManyToMany(mappedBy="retrancaCollection")
	private Set<Noticia> noticiaCollection;

	@ManyToMany(mappedBy="destaqueHomeTematicaCollection")
	private Set<Noticia> noticiaDestaqueCollection;
	
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="retrancaCollection")
	private Set<Tag> tagCollection;	

	public Set<Tag> getTagCollection() {
		if (tagCollection == null) {
			tagCollection = new HashSet<Tag>();
		}
		return tagCollection;
	}

	public void setTagCollection(Set<Tag> tagCollection) {
		this.tagCollection = tagCollection;
	}

	private static final long serialVersionUID = 1L;

	public Retranca() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer ideretranca) {
		this.id = ideretranca;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}

	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico ideservico) {
		this.servico = ideservico;
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
		Retranca other = (Retranca) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	public Boolean getIndHomeTematica() {
		return indHomeTematica;
	}

	public void setIndHomeTematica(Boolean indHomeTematica) {
		this.indHomeTematica = indHomeTematica;
	}

	public Boolean getIndBoletim() {
		return indBoletim;
	}

	public void setIndBoletim(Boolean indBoletim) {
		this.indBoletim = indBoletim;
	}
	
	
}

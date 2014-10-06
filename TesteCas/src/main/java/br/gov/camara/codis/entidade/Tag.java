package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;

@Entity
public class Tag implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idetag")
	private Integer id;

	private String texDescricao;

	private Timestamp datUltimaAlteracao;

	private String texPontoUsuario;

	@ManyToOne
	@JoinColumn(name = "IDESERVICO")
	private Servico servico;

	@ManyToMany(mappedBy = "tagCollection")
	private Set<Noticia> noticiaCollection;
	
	@ManyToMany
	@JoinTable(name="RETRANCATAG",
		joinColumns=@JoinColumn(name="IDETAG"),
		inverseJoinColumns=@JoinColumn(name="IDERETRANCA"))
	private Set<Retranca> retrancaCollection;

	
	private static final long serialVersionUID = 1L;

	public Tag() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idetag) {
		this.id = idetag;
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
	
	
	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((texDescricao == null) ? 0 : texDescricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!Hibernate.getClass(this).equals(Hibernate.getClass(obj)))
			return false;
		Tag other = (Tag) obj;
		if (getTexDescricao() == null) {
			if (other.getTexDescricao() != null)
				return false;
		} else if (!getTexDescricao().equalsIgnoreCase(other.getTexDescricao()))
			return false;
		return true;
	}



}

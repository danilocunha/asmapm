package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TemaDoDia implements Serializable, DomainObject<Integer> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idetemadodia")
	private Integer id;

	@Column(nullable=false)
	private String texDescricao;
	
	@Column(nullable=false)
	private Date datTema;

	@OneToMany(mappedBy="temaDoDia")
	private Set<Noticia> noticiaCollection;

	@OneToMany(mappedBy="temaDoDia")
	private Set<Bloco> blocoCollection;	

	public TemaDoDia() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idetiponoticia) {
		this.id = idetiponoticia;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}

	public Set<Noticia> getNoticiaCollection() {
		return this.noticiaCollection;
	}

	public void setNoticiaCollection(Set<Noticia> noticiaCollection) {
		this.noticiaCollection = noticiaCollection;
	}

	public Date getDatTema() {
		return datTema;
	}

	public void setDatTema(Date datTema) {
		this.datTema = datTema;
	}

	public Set<Bloco> getBlocoCollection() {
		return blocoCollection;
	}

	public void setBlocoCollection(Set<Bloco> blocoCollection) {
		this.blocoCollection = blocoCollection;
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
		if (getClass() != obj.getClass())
			return false;
		TemaDoDia other = (TemaDoDia) obj;
		if (texDescricao == null) {
			if (other.texDescricao != null)
				return false;
		} else if (!texDescricao.equals(other.texDescricao))
			return false;
		return true;
	}

}

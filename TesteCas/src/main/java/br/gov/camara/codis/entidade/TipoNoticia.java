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
public class TipoNoticia implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idetiponoticia")
	private Integer id;

	@Column(nullable=false)
	private String texDescricao;

	@ManyToOne
	@JoinColumn(name="IDESERVICO")
	private Servico servico;

	@OneToMany(mappedBy="tipoNoticia")
	private Set<Noticia> noticiaCollection;

	private static final long serialVersionUID = 1L;

	public TipoNoticia() {
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
		TipoNoticia other = (TipoNoticia) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}

package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class StatusNoticia implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idestatusnoticia")
	private Integer id;

	private String texDescricao;

	@OneToMany(mappedBy="statusNoticia")
	private Set<Noticia> noticiaCollection;

	private static final long serialVersionUID = 1L;

	public StatusNoticia() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idestatusnoticia) {
		this.id = idestatusnoticia;
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

}

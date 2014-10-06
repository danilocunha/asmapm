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
public class Servico implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideservico")
	private Integer id;

	private String texDescricao;

	private String texNomeVeiculo;

	private String texGrupoRedeCamara;

	@OneToMany(mappedBy="servico")
	private Set<TipoNoticia> tipoNoticiaCollection;

	@OneToMany(mappedBy="servico")
	private Set<Retranca> retrancaCollection;

	@OneToMany(mappedBy="servico")
	private Set<Home> homeCollection;

	@OneToMany(mappedBy="servico")
	private Set<Tag> tagCollection;

	private static final long serialVersionUID = 1L;

	public Servico() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer ideservico) {
		this.id = ideservico;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}

	public String getTexNomeVeiculo() {
		return this.texNomeVeiculo;
	}

	public void setTexNomeVeiculo(String texnomeveiculo) {
		this.texNomeVeiculo = texnomeveiculo;
	}

	public String getTexGrupoRedeCamara() {
		return this.texGrupoRedeCamara;
	}

	public void setTexGrupoRedeCamara(String texgruporedecamara) {
		this.texGrupoRedeCamara = texgruporedecamara;
	}

	public Set<TipoNoticia> getTipoNoticiaCollection() {
		return this.tipoNoticiaCollection;
	}

	public void setTipoNoticiaCollection(Set<TipoNoticia> tiponoticiaCollection) {
		this.tipoNoticiaCollection = tiponoticiaCollection;
	}

	public Set<Retranca> getRetrancaCollection() {
		return this.retrancaCollection;
	}

	public void setRetrancaCollection(Set<Retranca> retrancaCollection) {
		this.retrancaCollection = retrancaCollection;
	}

	public Set<Home> getHomeCollection() {
		return this.homeCollection;
	}

	public void setHomeCollection(Set<Home> homeCollection) {
		this.homeCollection = homeCollection;
	}

	public Set<Tag> getTagCollection() {
		return this.tagCollection;
	}

	public void setTagCollection(Set<Tag> tagCollection) {
		this.tagCollection = tagCollection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Servico other = (Servico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

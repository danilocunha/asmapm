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
public class TipoArea implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idetipoarea")
	private Integer id;

	private String texDescricao;

	@OneToMany(mappedBy="tipoArea")
	private Set<Area> areaCollection;

	@OneToMany(mappedBy="tipoArea")
	private Set<TipoBloco> tipoBlocoCollection;

	private static final long serialVersionUID = 1L;

	public TipoArea() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idetipoarea) {
		this.id = idetipoarea;
	}

	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}

	public Set<Area> getAreaCollection() {
		return this.areaCollection;
	}

	public void setAreaCollection(Set<Area> areaCollection) {
		this.areaCollection = areaCollection;
	}

	public Set<TipoBloco> getTipoBlocoCollection() {
		return this.tipoBlocoCollection;
	}

	public void setTipoBlocoCollection(Set<TipoBloco> tipoblocoCollection) {
		this.tipoBlocoCollection = tipoblocoCollection;
	}

}

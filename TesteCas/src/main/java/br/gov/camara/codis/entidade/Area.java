package br.gov.camara.codis.entidade;

import java.io.Serializable;
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
public class Area implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idearea")
	private Integer id;

	private int numPosicao;
	
	private String texCorArea;
	
	private String texCorBorda;

	@ManyToOne
	@JoinColumn(name = "IDEHOME")
	private Home home;

	@ManyToOne
	@JoinColumn(name = "IDETIPOAREA")
	private TipoArea tipoArea;

	@OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
	@OrderBy("numPosicao")
	private List<Bloco> blocoCollection;

	public Area() {
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

	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

	public TipoArea getTipoArea() {
		return tipoArea;
	}

	public void setTipoArea(TipoArea tipoArea) {
		this.tipoArea = tipoArea;
	}

	public List<Bloco> getBlocoCollection() {
		if (blocoCollection == null) {
			blocoCollection = new ArrayList<Bloco>();
		}
		return blocoCollection;
	}

	public void setBlocoCollection(List<Bloco> blocoCollection) {
		this.blocoCollection = blocoCollection;
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
		Area other = (Area) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Area clone() throws CloneNotSupportedException {
		Area areaClone = new Area();
		areaClone.setNumPosicao(getNumPosicao());
		areaClone.setTipoArea(getTipoArea());
        areaClone.setTexCorArea(getTexCorArea());
        areaClone.setTexCorBorda(getTexCorBorda());

		for (Bloco bloco : getBlocoCollection()) {
			if (bloco.getBlocoPai() != null)
				continue;
			areaClone.getBlocoCollection().add(bloco.clone(areaClone));
		}

		return areaClone;
	}
	
	public String getTexCorArea() {
		return texCorArea;
	}

	public void setTexCorArea(String corArea) {
		this.texCorArea = corArea;
	}
	
	public String getTexCorBorda() {
		return texCorBorda;
	}

	public void setTexCorBorda(String corBorda) {
		this.texCorBorda = corBorda;
	}
	
}

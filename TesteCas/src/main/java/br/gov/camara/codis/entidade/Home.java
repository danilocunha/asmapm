package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.Hibernate;

@Entity
public class Home implements DomainObject<Integer>, Serializable {
	@Id @GeneratedValue (strategy=GenerationType.AUTO)
	@Column(name="idehome")
	private Integer id;	

	private String texDescricao;
	
	private Date datAgendamento; 
	
	private String texUsuarioAgendamento;
	
	private Integer ideHomeOrigem;

	private String texUsuarioPublicacao;
	
	private Date datPublicacao; 
	
	@Enumerated(EnumType.ORDINAL)
	private StatusHomeEnum indStatus;

	@ManyToOne
	@JoinColumn(name="IDESERVICO")
	private Servico servico;

	@OneToMany(mappedBy="home", cascade=CascadeType.ALL)
	@OrderBy("numPosicao")
	private List<Area> areaCollection;

	private static final long serialVersionUID = 1L;

	public Home() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdeHomeOrigem() {
		return this.ideHomeOrigem;
	}

	public void setIdeHomeOrigem(Integer id) {
		this.ideHomeOrigem = id;
	}
	
	public String getTexDescricao() {
		return this.texDescricao;
	}

	public void setTexDescricao(String texdescricao) {
		this.texDescricao = texdescricao;
	}
	
	public Date getDatAgendamento() {
		return this.datAgendamento;
	}

	public void setDatAgendamento(Date datpublicacao) {
		this.datAgendamento = datpublicacao;
	}
	
	public String getTexUsuarioAgendamento() {
		return this.texUsuarioAgendamento;
	}

	public void setTexUsuarioAgendamento(String pontoUsuario) {
		this.texUsuarioAgendamento = pontoUsuario;
	}
	
	public Date getDatPublicacao() {
		return this.datPublicacao;
	}

	public void setDatPublicacao(Date datpublicacao) {
		this.datPublicacao = datpublicacao;
	}
	
	public String getTexUsuarioPublicacao() {
		return this.texUsuarioPublicacao;
	}

	public void setTexUsuarioPublicacao(String pontoUsuario) {
		this.texUsuarioPublicacao = pontoUsuario;
	}

	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Area> getAreaCollection() {
		if (areaCollection == null) {
			areaCollection = new ArrayList<Area>();
		}
		return this.areaCollection;
	}

	public void setAreaCollection(List<Area> areaCollection) {
		this.areaCollection = areaCollection;
	}

	public StatusHomeEnum getIndStatus() {
		return indStatus;
	}

	public void setIndStatus(StatusHomeEnum indStatus) {
		this.indStatus = indStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getTexDescricao() == null) ? 0 : getTexDescricao().hashCode());
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
		Home other = (Home) obj;
		if (getTexDescricao() == null) {
			if (other.getTexDescricao() != null)
				return false;
		} else if (!getTexDescricao().equalsIgnoreCase(other.getTexDescricao()))
			return false;
		return true;
	}

	@Override
	public Home clone() throws CloneNotSupportedException {
		Home homeClone = new Home();
		homeClone.setTexDescricao(getTexDescricao());
		homeClone.setServico(getServico());
		homeClone.setIndStatus(getIndStatus());
		
		for (Area area : this.getAreaCollection()) {
			Area areaClone = area.clone();
			areaClone.setHome(homeClone);
			homeClone.getAreaCollection().add(areaClone);
		}
		
		return homeClone;
	}	
	
}

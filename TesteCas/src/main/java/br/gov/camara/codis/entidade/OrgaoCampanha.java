package br.gov.camara.codis.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrgaoCampanha implements Serializable, DomainObject<Integer> {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideorgaocampanha")
	private Integer id;

	private String texNomeOrgaoCampanha;

	private String texUrlOrgaoCampanha;

	private static final long serialVersionUID = 1L;

	public OrgaoCampanha() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer ideorgaocampanha) {
		this.id = ideorgaocampanha;
	}

	public String getTexNomeOrgaoCampanha() {
		return this.texNomeOrgaoCampanha;
	}

	public void setTexNomeOrgaoCampanha(String texnomeorgaocampanha) {
		this.texNomeOrgaoCampanha = texnomeorgaocampanha;
	}

	public String getTexUrlOrgaoCampanha() {
		return this.texUrlOrgaoCampanha;
	}

	public void setTexUrlOrgaoCampanha(String texurlorgaocampanha) {
		this.texUrlOrgaoCampanha = texurlorgaocampanha;
	}

}

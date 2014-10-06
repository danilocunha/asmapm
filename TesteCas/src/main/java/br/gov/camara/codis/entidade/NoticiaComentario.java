package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Hibernate;

@Entity
public class NoticiaComentario implements Serializable, DomainObject<Integer> 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideNoticiaComentario")
	private Integer id; 
	
	@ManyToOne
	@JoinColumn(name="IDENOTICIA")
	private Noticia noticia;
	
	private Date datComentario;
	
	private String texNome;
	
	private String texEmail;
	
	private String texComentario;
	
	private String texPontoUsuario;
	
	private int indStatus;
	
	private String texCidade;
		
	private String texUF;
	
	@ManyToOne
	@JoinColumn(name="IDESTATUSCOMENTARIO")
	private StatusComentario statusComentario;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}
	
	public StatusComentario getStatusComentario() {
		return statusComentario;
	}

	public void setStatusComentario(StatusComentario statusComentario) {
		this.statusComentario = statusComentario;
	}

	public Date getDatComentario() {
		return datComentario;
	}

	public void setDatComentario(Date datComentario) {
		this.datComentario = datComentario;
	}

	public String getTexNome() {
		return texNome;
	}

	public void setTexNome(String texNome) {
		this.texNome = texNome;
	}

	public String getTexEmail() {
		return texEmail;
	}

	public void setTexEmail(String texEmail) {
		this.texEmail = texEmail;
	}

	public String getTexComentario() {
		return texComentario; 
	}

	public void setTexComentario(String texComentario) {
		this.texComentario = texComentario;
	}

	public String getTexPontoUsuario() { 
		return texPontoUsuario;
	}

	public void setTexPontoUsuario(String texPontoUsuario) {
		this.texPontoUsuario = texPontoUsuario;
	}
		
	public int getIndStatus() {
		return indStatus;
	}

	public void setIndStatus(int indStatus) {
		this.indStatus = indStatus;
	}
	
	public String getTexUF() {
		return texUF;
	}

	public void setTexUF(String texUF) {
		this.texUF = texUF;
	}
	
	public String getTexCidade() {
		return texCidade;
	}

	public void setTexCidade(String texCidade) {
		this.texCidade = texCidade;
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
		if (! Hibernate.getClass(this).equals(Hibernate.getClass(obj)))
			return false;
		NoticiaComentario other = (NoticiaComentario) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

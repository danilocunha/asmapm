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
public class NoticiaArquivoAnexo implements Serializable, DomainObject<Integer> 
{
	private static final long serialVersionUID = 1L; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideNoticiaArquivoAnexo")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name="ideNoticia")
	private Noticia noticia; 
	
		
	private String texDescricao;
	
	
	private String texUrlArquivoAnexo;
		

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
	
	public String getTexDescricao() {
		return texDescricao;
	}

	public void setTexDescricao(String texDescricao) {
		this.texDescricao = texDescricao;
	}

	public String getTexUrlArquivoAnexo() {
		return texUrlArquivoAnexo;
	}

	public void setTexUrlArquivoAnexo(String texUrlArquivoAnexo) {
		this.texUrlArquivoAnexo = texUrlArquivoAnexo;
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
		NoticiaArquivoAnexo other = (NoticiaArquivoAnexo) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}

package br.gov.camara.codis.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class EnqueteAutomatica implements Serializable 
{
	private static final long serialVersionUID = 1L; 

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ideEnquete")
	private Integer id; 
	
	@OneToOne
	@JoinColumn(name="IDENOTICIA")
	private Noticia noticia;
	
	private Date datEnquete;
	
	private String opcao1;
	
	private String opcao2;
	
	private String opcao3;
	
	private String descPergunta;
	 
	private int cont1;
	 
	private int cont2;
	
	private int cont3;
	
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

	public Date getDatEnquete() {
		return datEnquete;
	}

	public void setDatEnquete(Date datEnquete) {
		this.datEnquete = datEnquete;
	}

	public String getOpcao1() {
		return opcao1;
	}

	public void setOpcao1(String opcao1) {
		this.opcao1 = opcao1;
	}

	public String getOpcao2() {
		return opcao2;
	}

	public void setOpcao2(String opcao2) {
		this.opcao2 = opcao2;
	}

	public String getOpcao3() {
		return opcao3;
	}

	public void setOpcao3(String opcao3) {
		this.opcao3 = opcao3;
	}

	public String getDescPergunta() {
		return descPergunta;
	}

	public void setDescPergunta(String descPergunta) {
		this.descPergunta = descPergunta;
	}

	public int getCont1() {
		return cont1;
	}

	public void setCont1(int cont1) {
		this.cont1 = cont1;
	}

	public int getCont2() {
		return cont2;
	}

	public void setCont2(int cont2) {
		this.cont2 = cont2;
	}

	public int getCont3() {
		return cont3;
	}

	public void setCont3(int cont3) {
		this.cont3 = cont3;
	}
}

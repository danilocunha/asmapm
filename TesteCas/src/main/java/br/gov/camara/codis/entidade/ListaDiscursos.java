package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class ListaDiscursos implements Serializable {
	private static final long serialVersionUID = 1L;
    private String codMateria;  
	private String data;  
    private String hora;  
    private String manchete;  
    private String descricao;  
    private String ideimagem;
    private String txtComentario;

   public ListaDiscursos(
            String codMateria,   
            String data,   
            String hora,  
            String manchete,
            String descricao,
            String ideimagem,
    		String txtComentario)
    {  
        this.codMateria = codMateria;  
        this.data = data;  
        this.hora = hora;  
        this.manchete = manchete;  
        this.descricao = descricao;  
        this.ideimagem = ideimagem;  
        this.txtComentario = txtComentario;  

    }  
  
    public String getCodMateria() {  
        return codMateria;  
    }  
    
    public String getData() {  
        return data;  
    }  
  
    public String getHora() {  
        return hora;  
    }  
  
    public String getManchete() {  
        return manchete;  
    }  
  
    public String getDescricao() {  
        return descricao;  
    }  

    public String getIdeImagem() {  
        return ideimagem;  
    }  

    public String getTxtComentario() {  
        return txtComentario;  
    }  
    
    public void setCodMateria(String codMateria) {  
        codMateria = codMateria;  
    }  
  
    public void setData(String data) {  
        this.data = data;  
    }  
  
    public void setHora(String hora) {  
        this.hora = hora;  
    }  
  
    public void setManchete(String manchete) {  
        manchete = manchete;  
    }  

    public void setDescricao(String descricao) {  
        this.descricao = descricao;  
    }     

    public void setIdeImagem(String ideimagem) {  
        this.ideimagem = ideimagem;  
    }        

    public void setTxtComentario(String txtComentario) {  
        this.txtComentario = txtComentario;  
    }

   public String toString() {
	   return codMateria; 
   }
}

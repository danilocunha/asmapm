package br.gov.camara.codis.entidade;

import java.io.Serializable;

public class ListaDiscursosMateria implements Serializable {
	private static final long serialVersionUID = 1L;
    private String dataManchete;  
	private String retrancaManchete;  
    private String horaManchete;  
    private String descricaoManchete;  
    private String tituloManchete;  
    private String textoManchete;
    private String nomeImagemM;
    private String legendaImagem;
    private String nomAutorImagem;
    private String imagemComente;
    
   public ListaDiscursosMateria(
            String dataManchete,   
            String retrancaManchete,   
            String horaManchete,  
            String descricaoManchete,            
            String manchete,
            String tituloManchete,
            String textoManchete,
    		String nomeImagemM,
    		String legendaImagem,
    		String nomAutorImagem,
    		String imagemComente)
    {  
        this.dataManchete = dataManchete;  
        this.retrancaManchete = retrancaManchete;  
        this.horaManchete = horaManchete;  
        this.descricaoManchete = descricaoManchete;  
        this.tituloManchete = tituloManchete;  
        this.textoManchete = textoManchete;  
        this.nomeImagemM = nomeImagemM;
        this.legendaImagem = legendaImagem;
        this.nomAutorImagem = nomAutorImagem;
        this.imagemComente = imagemComente;

    }  
  
    public String getDataManchete() {  
        return dataManchete;  
    }  
    
    public String getRetrancaManchete() {  
        return retrancaManchete;  
    }  
  
    public String getHoraManchete() {  
        return horaManchete;  
    }  
  
    public String getDescricaoManchete() {  
        return descricaoManchete;  
    }  
  
    public String getTituloManchete() {  
        return tituloManchete;  
    }  

    public String getTextoManchete() {  
        return textoManchete;  
    }  

    public String getNomeImagemM() {  
        return nomeImagemM;  
    }  

    public String getLegendaImagem() {  
        return legendaImagem;  
    }  
    
    public String getNomAutorImagem(){
    	return nomAutorImagem;
    }
    
    public String getImagemComente(){
    	return imagemComente;
    }
    
    public void setDataManchete(String dataManchete) {  
    	this.dataManchete = dataManchete;  
    }  
  
    public void setRetrancaManchete(String retrancaManchete) {  
        this.retrancaManchete = retrancaManchete;  
    }  
  
    public void setHoraManchete(String horaManchete) {  
        this.horaManchete = horaManchete;  
    }  
  
    public void setDescricaoManchete(String descricaoManchete) {  
    	this.descricaoManchete = descricaoManchete;  
    }  

    public void setTituloManchete(String tituloManchete) {  
        this.tituloManchete = tituloManchete;  
    }     

    public void setTextoManchete(String textoManchete) {  
        this.textoManchete = textoManchete;  
    }        

    public void setNomeImagemM(String nomeImagemM) {  
        this.nomeImagemM = nomeImagemM;  
    }

    public void setLegendaImagem(String legendaImagem) {  
        this.legendaImagem = legendaImagem;  
    }    
    
    public void setNomAutorImagem(String nomAutorImagem){
    	this.nomAutorImagem = nomAutorImagem;
    }
    
    public void setImagemComente(String imagemComente){
    	this.imagemComente = imagemComente;
    }
    
   public String toString() {
	   return dataManchete; 
   }
}

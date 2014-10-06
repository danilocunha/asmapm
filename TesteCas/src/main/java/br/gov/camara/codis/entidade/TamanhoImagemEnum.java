package br.gov.camara.codis.entidade;

public enum TamanhoImagemEnum {
	GRANDE(10, "Grande"),
	MEDIA(5, "Média"), 
	PEQUENA(1, "Pequena");
	
	private final int codTamanho;
	private final String descricao;
	
	private TamanhoImagemEnum (int codTamanho, String descricao) {
		this.codTamanho = codTamanho;
		this.descricao = descricao;
	}

	public int getCodTamanho() {
		return codTamanho;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
}


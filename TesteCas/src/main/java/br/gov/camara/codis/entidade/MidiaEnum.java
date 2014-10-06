package br.gov.camara.codis.entidade;

public enum MidiaEnum {
	TEXTO(1),
	VIDEO(2),
	AUDIO(3),
	INFOGRAFICO(4);
	
	private final int ideMidia;
	
	private MidiaEnum(int ideMidia) {
		this.ideMidia = ideMidia;
	}

	public int getIdeMidia() {
		return ideMidia;
	}
}

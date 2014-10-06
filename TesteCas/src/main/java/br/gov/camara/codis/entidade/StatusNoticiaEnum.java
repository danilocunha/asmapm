package br.gov.camara.codis.entidade;

public enum StatusNoticiaEnum {
	GRAVADA(1),
	PUBLICADA(2),
	MODIFICADA(3);
	
	private final int ideStatusNoticia;
	
	private StatusNoticiaEnum(int ideStatusNoticia) {
		this.ideStatusNoticia = ideStatusNoticia;
	}

	public int getIdeStatusNoticia() {
		return ideStatusNoticia;
	}
}

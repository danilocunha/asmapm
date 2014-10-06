package br.gov.camara.codis.entidade;

public enum ComentarioStatusEnum {
	APROVAR(0),
	APROVADO(1),
	REPROVAR(2);
	
	private final int ideComentarioStatusEnum;
	
	private ComentarioStatusEnum(int ideComentarioStatusEnum) {
		this.ideComentarioStatusEnum = ideComentarioStatusEnum;
	}

	public int getIdeComentarioStatusEnum() {
		return ideComentarioStatusEnum;
	}
}

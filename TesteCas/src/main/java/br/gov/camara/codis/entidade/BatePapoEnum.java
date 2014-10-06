package br.gov.camara.codis.entidade;


public enum BatePapoEnum {
	AGENCIA(8),
	TV(0);
	
	private final int ideBatePapoEnum;
	
	private BatePapoEnum(int ideBatePapoEnum) {
		this.ideBatePapoEnum = ideBatePapoEnum;
	}

	// retorna o número da sala
	public int getBatePapoEnum() {
		return ideBatePapoEnum;
	}
}
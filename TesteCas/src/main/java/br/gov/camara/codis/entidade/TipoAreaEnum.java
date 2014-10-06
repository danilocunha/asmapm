package br.gov.camara.codis.entidade;

public enum TipoAreaEnum {
	AREA_100PORCENTO(1, "�?rea de 100%"),
	AREA_50PORCENTO(2, "�?rea de 50%");
	
	private final int ideTipoArea;
	private final String label;
	
	private TipoAreaEnum(int ideTipoArea, String label) {
		this.ideTipoArea = ideTipoArea;
		this.label = label;
	}

	public int getIdeTipoArea() {
		return ideTipoArea;
	}

	public String getLabel() {
		return label;
	}
}

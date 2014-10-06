package br.gov.camara.codis.entidade;

public enum TipoNoticiaEnum {
	CONSOLIDADA(1),
	PAUTA(2),
	PROJETO(3),
	AGENDA(4),
	TEMPO_REAL(5),
	NOTICIA_TV(6),
	BLOCO_PROGRAMA_TV(7),
	TITULO_TV(8),
	EPISODIO_TV(9),
	INTEGRA_TV(10),
	COLETANEA_TV(11),
	NOTICIA_RADIO(12),
	BLOCO_PROGRAMA_RADIO(13),
	TITULO_RADIO(14),
	INTEGRA_RADIO(15);
	
	private final int ideTipoNoticia;
	
	private TipoNoticiaEnum(int ideTipoNoticia) {
		this.ideTipoNoticia = ideTipoNoticia;
	}

	public int getIdeTipoNoticia() {
		return ideTipoNoticia;
	}
}

package br.gov.camara.codis.entidade;

public enum TipoBlocoEnum {
	MANCHETE_PRINCIPAL(1, "Manchete Principal"),
	RETRANCA_VERTICAL(2, "Retranca Vertical"),
	RETRANCA_HORIZONTAL(3, "Retranca Horizontal"),
	PAR_DE_CAIXAS(4, "Par de Caixas"),
	TEMPO_REAL(5, "Tempo Real"),
	MAIS_LIDAS(6, "Mais Lidas"),
	MAIS_VISTOS(6, "Mais Vistos"),
	TEMATICO(7, "Temático"),
	IMAGEM(8, "Imagem"),
	ENQUETE(9, "Enquete"),	
	BATE_PAPO(10, "Bate Papo"),
	RETRANCA_DINAMICA(11, "Retranca Dinâmica"),
	GENERICO(12, "Genérico"),
	MANCHETE_SEM_IMAGEM(13, "Manchete Sem Imagem"),
	DIVISORIA(14, "Divisória"),
	AO_VIVO(15,"Ao Vivo"), 
	ESPACAMENTO(16, "Espaçamento"),
	CAIXA_DE_TEXTO_COM_LINK(17, "Caixa de texto com link"),
	CAIXA_DE_TEXTO_SEM_LINK(18, "Caixa de texto sem link"),
	VIDEO(19, "Vídeo"),
	IMAGEMAREA100(20, "Imagem área 100%"),
	BANNERAREA100(21, "Banner área 100%"),
	BANNERBATEPAPOAREA100(22, "Banner com link área 100%"),
	AUDIO(23, "Audio"),
	MP4(24, "Vídeo MP4"),
	MANCHETE_PRINCIPAL_AREA50(25, "Manchete Principal 50%"),
	BANNER_IMAGEM_AREA50(26, "Banner Imagem 50%"),
	IMAGEM_THUMBNAILS(27, "Imagem com Thumbnails"),
	AO_VIVO_RADIO(28, "Rádio ao vivo"),
	TITULO_AREA100(29, "Título 100%"),
	RETRANCA_HORIZONTAL_AREA100(30, "Retranca Horizontal 100%");
	
	
	private final int ideTipoBloco;
	private final String label;
	
	private TipoBlocoEnum(int ideTipoBloco, String label) {
		this.ideTipoBloco = ideTipoBloco;
		this.label = label;
	}

	public int getIdeTipoBloco() {
		return ideTipoBloco;
	}

	public String getLabel() {
		return label;
	}
}

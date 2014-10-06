package br.gov.camara.codis.entidade;

import java.util.EnumSet;

public enum ServicoEnum { 
	AGENCIA(1, "Agencia"),
	TV(2, "TV"), 
	RADIO(3, "Radio");
	
	private final int ideServico;
	private final String descricao;
	
	public static final EnumSet<ServicoEnum> TV_E_RADIO = EnumSet.of(TV, RADIO);
	public static final EnumSet<ServicoEnum> TODOS = EnumSet.allOf(ServicoEnum.class);
	public static final EnumSet<ServicoEnum> AGENCIA_E_RADIO = EnumSet.of(AGENCIA, RADIO);
	public static final EnumSet<ServicoEnum> AGENCIA_E_TV = EnumSet.of(AGENCIA, TV);
	
	private ServicoEnum(int ideServico, String descricao) {
		this.ideServico = ideServico;
		this.descricao = descricao;
	}

	public int getIdeServico() {
		return ideServico;
	}
	
	public String getDescricao () {
		return descricao;
	}
	
	public static String getDescricaoServico(int ideServico) {
		
		String strDescricaoServico = "";
		
		if(ideServico == ServicoEnum.TV.getIdeServico()){ strDescricaoServico = ServicoEnum.TV.getDescricao().toString(); } 
		if(ideServico == ServicoEnum.AGENCIA.getIdeServico()){ strDescricaoServico = ServicoEnum.AGENCIA.getDescricao().toString(); }
		if(ideServico == ServicoEnum.RADIO.getIdeServico()){ strDescricaoServico = ServicoEnum.RADIO.getDescricao().toString(); }
		
		return strDescricaoServico;
	}
}

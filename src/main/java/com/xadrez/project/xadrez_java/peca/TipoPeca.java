package com.xadrez.project.xadrez_java.peca;

public enum TipoPeca {
	PEAO('P'),
	TORRE('T'),
	CAVALO('C'),
	BISPO('B'),
	RAINHA('Q'),
	REI('K');
	private final char simbolo;
	
	TipoPeca(char simbolo) {
		this.simbolo = simbolo;
	}
	
	public char getSimbolo(int cor) {
		if (cor == 1) return this.simbolo;
		return Character.toLowerCase(this.simbolo);
	}
}

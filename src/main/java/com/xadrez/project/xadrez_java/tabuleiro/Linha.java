package com.xadrez.project.xadrez_java.tabuleiro;

public enum Linha {
	//{'8','7','6','5','4','3','2','1'};
	L8(0, '8'), L7(1, '7'), L6(2, '6'), L5(3, '5'), L4(4, '4'), L3(5, '3'), L2(6, '2'), L1(7, '1');
	private final int indice;
	private final char simbolo;
	
	Linha (int indice, char simbolo) {
		this.indice = indice;
		this.simbolo = simbolo;
	}
	
	public static Linha deIndice (int indice) {
		for(Linha l : values()) {
			if (l.indice == indice) return l;
		}
		throw new IllegalArgumentException("Índice de linha inválido: " + indice);
	}
	
	public int getIndice() {
		return this.indice;
	}
	
	public char simbolo() {
		return this.simbolo;
	}
}

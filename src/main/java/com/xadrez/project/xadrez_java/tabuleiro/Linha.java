package com.xadrez.project.xadrez_java.tabuleiro;

public enum Linha {
	//{'8','7','6','5','4','3','2','1'};
	L8(0), L7(1), L6(2), L5(3), L4(4), L3(5), L2(6), L1(7);
	private final int indice;
	
	Linha (int indice) {
		this.indice = indice;
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
}

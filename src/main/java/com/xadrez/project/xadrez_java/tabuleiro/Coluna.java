package com.xadrez.project.xadrez_java.tabuleiro;

public enum Coluna {
	//{'A','B','C','D','E','F','G','H'};
	A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);
	private final int indice;
	
	Coluna (int indice) {
		this.indice = indice;
	}
	
	public static Coluna deIndice (int indice) {
		for(Coluna c: values()) {
			if (c.indice == indice) return c;
		}
		throw new IllegalArgumentException("Índice de coluna inválido: " + indice);
	}
	
	public int getIndice() {
		return this.indice;
	}
}

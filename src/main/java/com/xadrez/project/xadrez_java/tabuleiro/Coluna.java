package com.xadrez.project.xadrez_java.tabuleiro;

public enum Coluna {
	//{'A','B','C','D','E','F','G','H'};
	A(0, 'A'), B(1, 'B'), C(2, 'C'), D(3, 'D'), E(4, 'E'), F(5, 'F'), G(6, 'G'), H(7, 'H');
	private final int indice;
	private final char simbolo;
	
	Coluna (int indice, char simbolo) {
		this.indice = indice;
		this.simbolo = simbolo;
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
	
	public char simbolo() {
		return this.simbolo;
	}
}

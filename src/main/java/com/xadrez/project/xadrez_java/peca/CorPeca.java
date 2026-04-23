package com.xadrez.project.xadrez_java.peca;

public enum CorPeca {
	BRANCA(1), PRETA(0);
	private final int cor;
	
	CorPeca(int cor) {
		this.cor = cor;
	}
	
	public int getCor() {
		return this.cor;
	}
}

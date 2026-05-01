package com.xadrez.project.xadrez_java.tabuleiro;

public record Posicao(Coluna c, Linha l) {
	public static Posicao converterStringEmPos (String s) {
		//Tratamento de dado inválido caso uma string não seja de tamanho 2
		if (s == null || s.length() != 2) {
			throw new IllegalArgumentException("Formato inválido, use algo como 'A1'");
		}
		
		//Repartição da string para pesquisar linha e coluna
		char charColuna = Character.toUpperCase(s.charAt(0));
		char charLinha = s.charAt(1);
		
		//Obtem o valor correspondente de cada coluna
		Coluna coluna = Coluna.valueOf(String.valueOf(charColuna));
		Linha linha = Linha.valueOf("L" + charLinha);
		
		//Retorna o record com a posição feita
		return new Posicao(coluna,linha);
	}
	
	//Método para retornar a posição como um vetor (col,lin)
	public int[] getCoord() {
		return new int[] {c.getIndice(), l.getIndice()};
	}
	
	public String posicao() {
		String pos = String.format("%c%c", c.simbolo(), l.simbolo());
		return pos;
	}
	
	public int x() {
		return c.getIndice();
	}
	
	public int y() {
		return l.getIndice();
	}
}

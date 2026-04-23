package com.xadrez.project.xadrez_java.jogador;

import java.util.ArrayList;
import java.util.Scanner;

import com.xadrez.project.xadrez_java.computador.Computador;
import com.xadrez.project.xadrez_java.peca.CorPeca;
import com.xadrez.project.xadrez_java.peca.Peca;
import com.xadrez.project.xadrez_java.peca.TipoPeca;
import com.xadrez.project.xadrez_java.peca.bispo.Bispo;
import com.xadrez.project.xadrez_java.peca.cavalo.Cavalo;
import com.xadrez.project.xadrez_java.peca.peao.Peao;
import com.xadrez.project.xadrez_java.peca.rainha.Rainha;
import com.xadrez.project.xadrez_java.peca.rei.Rei;
import com.xadrez.project.xadrez_java.peca.torre.Torre;
import com.xadrez.project.xadrez_java.tabuleiro.Coluna;
import com.xadrez.project.xadrez_java.tabuleiro.Linha;
import com.xadrez.project.xadrez_java.tabuleiro.Posicao;
import com.xadrez.project.xadrez_java.tabuleiro.Tabuleiro;

public class Jogador {
	//Define as peças de cada cor, diferenciadas pelo fato de serem
	//Maíusculas ou não
	private char[] pecasBrancas = {'P','T','C','B','K','Q'};
	private char[] pecasPretas = {'p','t','c','b','k','q'};
	
	//Variável que define qual jogador é
	private int jogador;
	
	//Variável condicional que diz se o jogador está em xeque
	private boolean xeque = false;
	
	//ArrayList que traz as direções que estão ameaçando o rei do jogador
	//private ArrayList<int[]> dirAmeacadas = new ArrayList<>();
	
	//ArrayList que compila todas as peças que o jogador possui
	private ArrayList<Peca> pecasAtuais = new ArrayList<>();
	
	//ArrayList que compila todas as peças capturadas do adversário;
	private ArrayList<Peca> pecasCapturadas = new ArrayList<>();
	
	public Jogador(int jogador) {
		this.jogador = jogador;
	}
	
	//Construtor de cópia de objeto
	public Jogador(Jogador outro) {
		this.jogador = outro.jogador;
		this.pecasAtuais = new ArrayList<>();
		this.pecasCapturadas = new ArrayList<>();
		this.xeque = outro.xeque;
	}
	
	//Método para inserir peças na posição inicial do tabuleiro (versão clean do
	//método acima
	public void posInicialPecas(Tabuleiro tabuleiro) {
		//Variáveis que irão ser alteradas conforme o jogador que as chama
		int linhaPeoes = this.jogador == 0 ? 6 : 1;
		int linhaPecas = this.jogador == 0 ? 7 : 0;
		CorPeca corPecasJogador = this.jogador == 0 ? CorPeca.BRANCA : CorPeca.PRETA;
		
		//Variável que abriga os objetos criados das peças
		Peca peca = null;
		
		//Inserindo peões nas suas respectivas linhas
		for (int col = 0; col < 8; col++) {
			Posicao pos = new Posicao(Coluna.deIndice(col), Linha.deIndice(linhaPeoes));
			peca = new Peao(corPecasJogador, pos, this);
			pecasAtuais.add(peca);
			tabuleiro.inserirPeca(peca);
		}
		
		//Inserindo as demais peças
		for (int col = 0; col < 8; col++) {
			Posicao pos = new Posicao(Coluna.deIndice(col), Linha.deIndice(linhaPecas));
			peca = switch(col) {
				case 0, 7 -> new Torre(corPecasJogador, pos, this);
				case 1, 6 -> new Cavalo(corPecasJogador, pos, this);
				case 2, 5 -> new Bispo(corPecasJogador, pos, this);
				case 3 -> new Rei(corPecasJogador, pos, this);
				default -> new Rainha(corPecasJogador, pos, this);
			};
			pecasAtuais.add(peca);
			tabuleiro.inserirPeca(peca);
		}
	}

	//Método que checa a jogada e sendo ela válida fará ela acontecer
	public void realizarJogada(Tabuleiro tabuleiro, Scanner leitor) {
		//Atributos para obtenção das informações da jogada
		Posicao posAntiga, posNova;
		Peca pecaSelecionada;
		
		do {
			System.out.print("Digite a posição referente a peça que você quer selecionar: ");
			posAntiga = Posicao.converterStringEmPos(leitor.next());
			
			if (!this.checarOutOfBounds(posAntiga, tabuleiro)) continue;
			pecaSelecionada = tabuleiro.getPeca(posAntiga);
			
			if(!this.checarPecaValida(pecaSelecionada)) continue;
			
			System.out.println();
			System.out.print("Digite a posição na qual você quer mover a peça: ");

			posNova = Posicao.converterStringEmPos(leitor.next());
			
			if(!pecaSelecionada.validarMovimento(posNova, tabuleiro)) continue;
			if(!this.validarJogada(posAntiga, posNova, tabuleiro)) continue;
			break;
		} while (true);
		
		tabuleiro.executarMovimento(pecaSelecionada, posAntiga, posNova);
		/*if(pecaSelecionada.getTipo().equals(TipoPeca.PEAO)) {
			if(coordNova[1] == 7 || coordNova[1] == 0) {
				this.promoverPeao(pecaSelecionada, tabuleiro);
			}
		}*/
	}
	
	private boolean checarOutOfBounds(Posicao pos, Tabuleiro tabuleiro) {
		Peca pecaSelecionada;
		try {
			pecaSelecionada = tabuleiro.getPeca(pos);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Posição inválida! Tente novamente");
			return false;
		}
		return true;
	}
	
	private boolean checarPecaValida(Peca peca) {

		if(peca == null) {
			System.out.println("Casa vazia! Tente novamente");
			return false;
		}
		if(!this.getPecasAtuais().contains(peca)) {
			System.out.println("Essa não é a sua peça! Tente novamente");
			return false;
		}
		return true;
	}
	
	//Método sobrecarregado para caso haja um controle do computador
	public void realizarJogada(Tabuleiro tabuleiro, Computador computador) {
		//Atributos para obtenção das informações da jogada
		Posicao posAntiga, posNova;
		Peca pecaSelecionada;
		
		//Loop que irá ser realizado enquanto o rei permanecer em xeque (caso esteja)
		do {
			pecaSelecionada = computador.selecionarPeca(this.getPecasAtuais(), tabuleiro);
			posAntiga = pecaSelecionada.getPosicaoAtual();
			posNova = computador.moverPeca(pecaSelecionada,tabuleiro);
		} while (!this.validarJogada(posAntiga, posNova, tabuleiro));
		tabuleiro.executarMovimento(pecaSelecionada, posAntiga, posNova);
		/*if(pecaSelecionada.getTipo().equals(TipoPeca.PEAO)) {
			if(coordNova[1] == 7 || coordNova[1] == 0) {
				this.promoverPeao(pecaSelecionada, tabuleiro, computador);
			}
		}*/
	}
	
	//Método feito para ensaiar uma jogada e validar a fim de ver se ela deixa o nosso
	//rei vulnerável
	private boolean validarJogada(Posicao posAntiga, Posicao posNova, Tabuleiro tabuleiro) {
		Tabuleiro tabVirtual = new Tabuleiro(tabuleiro);
		Jogador jgVirtual = this.getJogador() == 0 ? tabVirtual.getJogadores()[0] : tabVirtual.getJogadores()[1];
		Peca pecaJogada = tabVirtual.getPeca(posAntiga);
		System.out.println(pecaJogada);
		tabVirtual.executarMovimento(pecaJogada, posAntiga, posNova);
		
		if (tabVirtual.checarXequeMate(jgVirtual)) {
			System.out.println("Posição te deixará/manterá em xeque, selecione outra");
			return false;
		}
		return true;
	}
	
	//Método para promoção de peões
	private void promoverPeao(Peca peao, Tabuleiro tabuleiro) {
		//Objeto scanner para adquirir o input do usuário (posteriormente será removido)
		Scanner leitor = new Scanner(System.in);
		
		//String agrupando todas as possibilidades de opções
		String opcoesPromo = "TtCcBbQq";
		
		//Variável que irá guardar a opção escolhida
		char op;
		System.out.print("Escolha uma das peças para promover o peão: T,C,B,Q ");
		
		//Loop que irá averiguar que se o jogador colocou a opção correta
		do {
			op = leitor.next().charAt(0);
			if(opcoesPromo.indexOf(op) != -1) {
				break;
			}
			System.out.println("Escolha inválida, tente novamente!");
		} while (true);
		
		//Pesquisa responsável para verificar e achar a peça dentre as peças que o jogador
		//possui
		int indice = peao.getJogadorResp().getPecasAtuais().indexOf(peao);
		if (indice != -1) {
			Peca novaPeca = switch (op) {
				case 'T','t' -> new Torre(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case 'C','c' -> new Cavalo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				case 'B','b' -> new Bispo(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
				default -> new Rainha(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
			};
			
			//Alterar a peça e remover o peão
			peao.getJogadorResp().getPecasAtuais().set(indice, novaPeca);
			tabuleiro.colocarPeca(novaPeca, novaPeca.getPosicao());
		}
	}
	
	private void promoverPeao (Peca peao, Tabuleiro tabuleiro, Computador computador) {
		char op = peao.getJogadorResp().getJogador() == 0 ? 'Q' : 'q';
		int indice = peao.getJogadorResp().getPecasAtuais().indexOf(peao);
		Peca novaPeca = new Rainha(peao.getCor(), peao.getPosicaoAtual(), peao.getJogadorResp());
		peao.getJogadorResp().getPecasAtuais().set(indice, novaPeca);
		tabuleiro.colocarPeca(novaPeca, novaPeca.getPosicao());
	}
	
	public int getJogador() {
		return jogador;
	}

	public void setJogador(int jogador) {
		this.jogador = jogador;
	}

	public ArrayList<Peca> getPecasAtuais() {
		return pecasAtuais;
	}

	public void setPecasAtuais(ArrayList<Peca> pecasAtuais) {
		this.pecasAtuais = pecasAtuais;
	}
	
	public ArrayList<Peca> getPecasCapturadas() {
		return pecasCapturadas;
	}
	
	public boolean isXeque() {
		return xeque;
	}
	
	public void setXeque(boolean xeque) {
		this.xeque = xeque;
	}
}

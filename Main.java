import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {
	// Constantes
	private static final int MAX_SEQUENCIA = 21;
	private static final int MIN_JOGADORES = 2;
	private static final int MAX_JOGADORES = 4;
	private static final int BONUS_SEQUENCIA_COMPLETA = 20;
	private static final int CODIGO_VOLTAR = 9;
	private static final int CODIGO_REINICIAR = 3;
	
	// Formatação
	private static final String NEGRITO = "\u001b[1m";
	private static final String VERMELHO = "\u001B[91m";
	private static final String RESET = "\u001B[0m";
	
	// Entrada de dados
	private static final Scanner scanner = new Scanner(System.in);
	
	// Estado do jogo
	private static boolean reiniciar = false;
	private static String[] sequencia = new String[MAX_SEQUENCIA];
	private static int contadorSequencia = -1;
	private static int rodada = 1;
	
	// Modelo de jogadores
	private static class Jogador {
		String nome;
		int pontos;
		boolean ativo;
		String ultimaResposta;
		
		Jogador(String nome) {
			this.nome = nome;
			this.pontos = 0;
			this.ativo = true;
		}
		
		void reiniciar() {
			this.pontos = 0;
			this.ativo = true;
		}
	}
	
	private static List<Jogador> jogadores = new ArrayList<>();

	public static void main(String[] args) {
		System.out.println("MEMORIX");
		menuInicial();
	}
	
	// ===== MENU E NAVEGAÇÃO =====
	
	private static void menuInicial() {
		limparTela();
		System.out.println("\nESCOLHA UMA OPÇÃO\n\n[1] REGRAS\n[2] JOGAR\n[3] SAIR\n");
		System.out.print(VERMELHO + "Opção: " + RESET);
		
		int opcao = lerOpcao(1, 3);
		switch (opcao) {
			case 1: regras(); break;
			case 2: jogar(); break;
			case 3: 
				System.out.println("\nObrigado por jogar o Memorix!");
				System.exit(0);
		}
	}
	
	private static void regras() {
		System.out.println("\n** REGRAS DO JOGO **");
		System.out.println(NEGRITO + "\nObjetivo:" + RESET);
		System.out.println("O objetivo do jogo é testar sua memória enquanto você tenta repetir sequências.");
		
		System.out.println(NEGRITO + "\nComo jogar:" + RESET);
		System.out.println("Assim que o jogo iniciar, o primeiro jogador precisa iniciar uma sequência com apenas uma palavra, \n"
				+ "sem restrição de tema. Em seguida, a tela será limpa e o próximo jogador deve repetir a palavra que o jogador \n"
				+ "anterior escreveu e adicionar outra palavra à sequência. A tela será limpa novamente e o próximo jogador deve \n"
				+ "repetir a primeira palavra, a segunda palavra e adicionar uma terceira palavra; "
				+ "\ne assim por diante, de forma sucessiva.\n\n"
				+ "Pontuação \n"
				+ "Acertar itens da sequência: +1 ponto por item\n"
				+ "Herança: +1 ponto (caso o jogador perca a rodada, um ponto cai na conta do próximo jogador)\n"
				+ "Chegar a 21 itens: +20 pontos");
		
		redirecionarParaMenu();
	}
	
	private static void redirecionarParaMenu() {
		System.out.println(VERMELHO + "\nDigite 9 para voltar ao menu" + RESET);
		System.out.print(VERMELHO + "Opção: " + RESET);
		
		int opcao = lerOpcao(CODIGO_VOLTAR, CODIGO_VOLTAR);
		if (opcao == CODIGO_VOLTAR) {
			menuInicial();
		}
	}
	
	private static void fimDeJogo() {
		System.out.println(VERMELHO + "\nDigite 9 para voltar ao menu ou 3 para reiniciar a partida com os mesmos jogadores" + RESET);
		
		int opcao = lerOpcao(Arrays.asList(CODIGO_VOLTAR, CODIGO_REINICIAR));
		if (opcao == CODIGO_VOLTAR) {
			reiniciar = false;
			limparTela();
			menuInicial();
		} else if (opcao == CODIGO_REINICIAR) {
			reiniciar = true;
			jogar();
		}
	}
	
	// ===== CONFIGURAÇÃO DO JOGO =====
	
	private static void inicializarJogo() {
		contadorSequencia = -1;
		rodada = 1;
		
		for (Jogador jogador : jogadores) {
			jogador.ativo = true;
		}
	}
	
	private static void configJogadores() {
		jogadores.clear();
		
		System.out.println("Digite o número de jogadores que terão na rodada (mín. " + 
						  MIN_JOGADORES + ", máx. " + MAX_JOGADORES + ")");
		
		int numJogadores = lerOpcao(MIN_JOGADORES, MAX_JOGADORES);
		
		for (int i = 1; i <= numJogadores; i++) {
			System.out.println(NEGRITO + "\nInforme o nome do Jogador nº" + i + RESET);
			jogadores.add(new Jogador(scanner.next()));
		}
	}
	
	// ===== PRINCIPAL LÓGICA DO JOGO =====
	
	private static void jogar() {
		if (!reiniciar) {
			configJogadores();
		} else {
			for (Jogador jogador : jogadores) {
				jogador.reiniciar();
			}
		}
		
		inicializarJogo();
		System.out.println("\n** JOGAR **\n" + RESET);
		
		while (contadorSequencia < MAX_SEQUENCIA - 1 && jogadoresAtivos() >= 2) {
			limparTela();
			System.out.printf("\n## RODADA %d ##%n", rodada);
			
			if (rodada == 1) {
				System.out.println(VERMELHO + "\nDica: " + RESET + 
								 "Digite um número ou palavra para dar inicío a sequência (sem espaços)");
			}
			
			System.out.println("\nPlacar");
			exibirPlacar(false);
			contadorSequencia++;
			
			// Jogada de cada jogador
			for (int i = 0; i < jogadores.size(); i++) {
				Jogador jogador = jogadores.get(i);
				if (jogador.ativo) {
					processarJogada(jogador, i);
				}
			}
			
			// Verificar se completou a sequência
			if (contadorSequencia == MAX_SEQUENCIA - 1) {
				System.out.println("\nOs jogadores fizeram uma sequência de " + MAX_SEQUENCIA + 
								 " itens! Parabéns!\nJogo encerrado");
				
				for (Jogador jogador : jogadores) {
					if (jogador.ativo) {
						jogador.pontos += BONUS_SEQUENCIA_COMPLETA;
					}
				}
				
				System.out.println("\nPlacar final");
				exibirPlacar(true);
				determinarVencedor();
				break;
			}
			
			rodada++;
		}
		
		// Se não completou a sequência
		if (contadorSequencia < MAX_SEQUENCIA - 1) {
			System.out.println("\nPlacar final");
			exibirPlacar(true);
			determinarVencedor();
		}
		
		fimDeJogo();
	}
	
	private static void processarJogada(Jogador jogador, int indiceJogador) {
		if (contadorSequencia == 0 && rodada == 1) {
			// Primeira jogada do jogo
			System.out.printf("%nVez de %s%n", jogador.nome);
			jogador.ultimaResposta = scanner.next();
			sequencia[0] = jogador.ultimaResposta;
			return;
		}
		
		// Jogadas subsequentes
		limparTela();
		
		if (rodada == 1 && indiceJogador > 0) {
			System.out.println(VERMELHO + "\nDica: " + RESET + 
							 "Repita a sequência iniciada pelo jogador anterior e adicione mais um item");
			System.out.println(VERMELHO + "Separe os itens por Enter!!!" + RESET);
		} else {
			System.out.printf("\n## RODADA %d ##%n", rodada);
		}
		
		System.out.println("\nPlacar");
		exibirPlacar(false);
		System.out.printf("%nVez de %s%n", jogador.nome);
		
		// Loop para verificar cada palavra da sequência
		String resposta = scanner.next();
		for (int i = 0; i < contadorSequencia; i++) {
			if (!resposta.equalsIgnoreCase(sequencia[i])) {
				// Errou a sequência
				Jogador proximoJogador = obterProximoJogadorAtivo(indiceJogador);
				proximoJogador.pontos++;
				
				System.out.printf("%n%s, você perdeu. 1 ponto vai para o próximo jogador: %s%n", 
								jogador.nome, proximoJogador.nome);
				System.out.println(VERMELHO + "Dê Enter para continuar" + RESET);
				scanner.nextLine();
				scanner.nextLine();
				
				jogador.ativo = false;
				return;
			}
			
			// Acertou, acrescenta ponto e pega próxima resposta
			jogador.pontos++;
			resposta = scanner.next();
			
			// Se chegou no final da sequência existente, adiciona nova palavra
			if (i == contadorSequencia - 1) {
				sequencia[contadorSequencia] = resposta;
			}
		}
	}
	
	// ===== UTILITÁRIOS =====
	
	private static int jogadoresAtivos() {
		int ativos = 0;
		for (Jogador jogador : jogadores) {
			if (jogador.ativo) ativos++;
		}
		return ativos;
	}
	
	private static Jogador obterProximoJogadorAtivo(int indiceAtual) {
		int proximo = indiceAtual;
		do {
			proximo = (proximo + 1) % jogadores.size();
		} while (!jogadores.get(proximo).ativo && proximo != indiceAtual);
		
		return jogadores.get(proximo);
	}
	
	private static void exibirPlacar(boolean final_) {
		if (final_) {
			for (Jogador jogador : jogadores) {
				System.out.printf("%s: %d%n", jogador.nome, jogador.pontos);
			}
		} else {
			for (Jogador jogador : jogadores) {
				if (jogador.ativo) {
					System.out.printf("%s: %d%n", jogador.nome, jogador.pontos);
				}
			}
		}
	}
	
	private static void determinarVencedor() {
		if (verificarEmpate()) {
			System.out.println("\nHouve um empate!");
			return;
		}
		
		Jogador vencedor = jogadores.get(0);
		for (Jogador jogador : jogadores) {
			if (jogador.pontos > vencedor.pontos) {
				vencedor = jogador;
			}
		}
		
		System.out.printf("%n%s é o vencedor da partida!%n", vencedor.nome);
	}
	
	private static boolean verificarEmpate() {
		for (int i = 0; i < jogadores.size() - 1; i++) {
			for (int j = i + 1; j < jogadores.size(); j++) {
				if (jogadores.get(i).pontos == jogadores.get(j).pontos) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void limparTela() {
		for (int i = 0; i < 130; i++) {
			System.out.println();
		}
	}
	
	private static int lerOpcao(int min, int max) {
		while (true) {
			try {
				int opcao = scanner.nextInt();
				if (opcao >= min && opcao <= max) {
					return opcao;
				} else {
					System.out.println(VERMELHO + "\nOpção inválida!\nTente novamente" + RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(VERMELHO + "\nEntrada inválida! Digite um número de " + min + " a " + max + RESET);
				scanner.nextLine();
			}
		}
	}
	
	private static int lerOpcao(List<Integer> opcoesValidas) {
		while (true) {
			try {
				int opcao = scanner.nextInt();
				if (opcoesValidas.contains(opcao)) {
					return opcao;
				} else {
					System.out.println(VERMELHO + "\nOpção inválida!\nTente novamente" + RESET);
				}
			} catch (InputMismatchException e) {
				System.out.println(VERMELHO + "\nEntrada inválida! Digite uma das opções válidas" + RESET);
				scanner.nextLine();
			}
		}
	}
}

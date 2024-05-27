import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	static Scanner ler = new Scanner(System.in);

	// CORES DOS TEXTOS
	static String NEGRITO = "\u001b[1m";
	static String VERMELHO = "\u001B[91m";
	static String RESET = "\u001B[0m";

	// UTILITÁRIOS
	static boolean valido;
	static boolean reiniciar = false;
	static int opcao;

	// VARIAVEIS DOS JOGADORES
	static String nomeJogador1, nomeJogador2, nomeJogador3, nomeJogador4, respostaJogador1, respostaJogador2, respostaJogador3, respostaJogador4; // estáticos, para poder modificar na função
	static int pontosJogador1 = 0, pontosJogador2 = 0, pontosJogador3 = 0, pontosJogador4 = 0;
	static boolean jogador1Continua = true;
	static boolean jogador2Continua = true;
	static boolean jogador3Continua = true;
	static boolean jogador4Continua = true;
	static String passarPontos = "";
	static String enter = "";

	// INFOS DA RODADA
	static String respostas[] = new String[21]; // array que armazena os itens da sequência a ser realizada
	static int qtdplayers = 0; 
	static int contResp = -1; // informa a quantidade de itens que existem até então para serem validados
	static int rodada = 1;

	public static void main(String[] args) {

		// MENU
		System.out.println("MEMORIX");
		menuInicial();

	}

	// FUNÇÕES

	// FUNÇÃO QUE EXIBE MENU INICIAL E PEDE UMA OPÇAO

	public static void menuInicial() {
		valido = false;

		System.out.println("\nESCOLHA UMA OPÇÃO\n\n[1] REGRAS\n[2] JOGAR\n[3] SAIR\n");
		System.out.print(VERMELHO + "Opção: " + RESET);

		do {
			try {
				opcao = ler.nextInt();
				if (opcao == 1) {
					valido = true;
					regras();
				} else if (opcao == 2) {
					valido = true;
					jogar();
				} else if (opcao == 3) {
					valido = true;
					System.out.println("\nObrigado por jogar o Memorix!");
					System.exit(0);
				} else {
					System.out.println(VERMELHO + "\nOpção inválida!\nTente novamente" + RESET);
				}
			} catch(InputMismatchException e) { 
				System.out.println(VERMELHO + "\nEntrada inválida! Digite um número de 1 a 3" + RESET);
				ler.nextLine();
			}
		} while(!valido);
	}

	// FUNÇÃO DAS REGRAS

	public static void regras() {
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

		redirecionamento();
	}

	// FUNÇÃO DO REDIRECIONAMENTO PARA VOLTAR AO MENU

	public static void redirecionamento() {
		valido = false;

		System.out.println(VERMELHO + "\nDigite 9 para voltar ao menu" + RESET);
		System.out.print(VERMELHO + "Opção: " + RESET);

		do {
			try {
				opcao = ler.nextInt();
				if (opcao == 9) {
					valido = true;
					menuInicial();
				} else {
					System.out.println(VERMELHO + "\nOpção inválida!\nTente novamente" + RESET);
				}
			} catch(InputMismatchException e) { 
				System.out.println(VERMELHO + "\nEntrada inválida! Digite '9' para voltar ao menu" + RESET);
				ler.nextLine();
			}
		} while(!valido);
	}

	// FUNÇÃO PARA JOGAR

	public static void jogar() {
		jogador1Continua = true;
		jogador2Continua = true;
		jogador3Continua = true;
		jogador4Continua = true;

		contResp = -1;
		System.out.println("\n** JOGAR **\n" + RESET);

		if(!reiniciar) {
			pontosJogador1 = 0; 
			pontosJogador2 = 0;
			pontosJogador3 = 0;
			pontosJogador4 = 0;
			qtdPlayers();
		}

		// JOGABILIDADE EM 2

		if(qtdplayers == 2) { 
			for(rodada = 1; contResp < 20; rodada++) { 
				jogador3Continua = false;
				jogador4Continua = false;
				if(jogador1Continua && jogador2Continua) {
					telaEmBranco();
					System.out.printf("\n## RODADA %d ##%n", rodada);
					if(rodada == 1) {
						System.out.println(VERMELHO + "\nDica: " + RESET + "Digite um número ou palavra para dar inicío a sequência (sem espaços)");
					}
					System.out.println("\nPlacar");
					placar();
					contResp++;

					if(jogador2Continua) {
						vezJog1();
					}
					if(jogador1Continua) {
						vezJog2();
					}

					if (contResp == 20) {
						System.out.println("\nOs jogadores fizeram uma sequência de 21 itens! Parabéns!\nJogo encerrado");
						pontosJogador1 += 20;
						pontosJogador2 += 20;
						jogador1Continua = false; 
						jogador2Continua = false;
						System.out.println("\nPlacar final");
						placarFinal();
						determinarVencedor();
						fimDeJogo();
						break;
					}

				} else {
					jogador1Continua = true;
					jogador2Continua = true;
					jogador3Continua = true;
					jogador4Continua = true;
					System.out.println("\nPlacar final");
					placar();
					determinarVencedor();
					fimDeJogo();
					break;
				}
			} 

			// JOGABILIDADE EM 3

		} else if(qtdplayers == 3) { 
			for(rodada = 1; contResp < 20; rodada++) {
				jogador4Continua = false;
				if((jogador1Continua && jogador2Continua && jogador3Continua) || 
						(jogador1Continua && jogador2Continua) || 
						(jogador1Continua && jogador3Continua) || 
						(jogador2Continua && jogador3Continua)) {
					telaEmBranco();
					System.out.printf("\n## RODADA %d ##%n", rodada);
					if(rodada == 1) {
						System.out.println(VERMELHO + "\nDica: " + RESET + "Digite um número ou palavra para dar inicío a sequência (sem espaços)");
					}
					System.out.println("\nPlacar");
					placar();
					contResp++;

					if((jogador1Continua && jogador2Continua) || (jogador1Continua && jogador3Continua)) {
						vezJog1();
					}
					if((jogador2Continua && jogador1Continua) || (jogador2Continua && jogador3Continua)) {
						vezJog2();
					} 
					if((jogador3Continua && jogador1Continua) || (jogador3Continua && jogador2Continua)) {
						vezJog3();
					}

					if (contResp == 20) {
						System.out.println("\nOs jogadores fizeram uma sequência de 21 itens! Parabéns!\nJogo encerrado");
						if(jogador1Continua) {
							pontosJogador1 += 20;
						}
						if(jogador2Continua) {
							pontosJogador2 += 20;
						}
						if(jogador3Continua) {
							pontosJogador3 += 20;
						}
						jogador1Continua = false; 
						jogador2Continua = false;
						jogador3Continua = false;
						System.out.println("\nPlacar final");
						placarFinal();
						determinarVencedor();
						fimDeJogo();
						break;
					}

				} else {
					jogador1Continua = true;
					jogador2Continua = true;
					jogador3Continua = true;
					jogador4Continua = true;
					System.out.println("\nPlacar final");
					placar();
					determinarVencedor();
					fimDeJogo();
					break;
				}

			}

			// JOGABILIDADE EM 4

		} else if(qtdplayers == 4) {
			for(rodada = 1; contResp < 20; rodada++) {
				if((jogador1Continua && jogador2Continua && jogador3Continua && jogador4Continua) || 
						(jogador1Continua && jogador2Continua && jogador3Continua) || 
						(jogador1Continua && jogador3Continua && jogador4Continua) || 
						(jogador2Continua && jogador3Continua && jogador4Continua) || 
						(jogador1Continua && jogador2Continua && jogador4Continua) || 
						(jogador1Continua && jogador2Continua) || 
						(jogador1Continua && jogador3Continua) || 
						(jogador2Continua && jogador3Continua) || 
						(jogador1Continua && jogador4Continua) || 
						(jogador2Continua && jogador4Continua) || 
						(jogador3Continua && jogador4Continua)) {
					telaEmBranco();
					System.out.printf("\n## RODADA %d ##%n", rodada);
					if(rodada == 1) {
						System.out.println(VERMELHO + "\nDica: " + RESET + "Digite um número ou palavra para dar inicío a sequência (sem espaços)");
					}
					System.out.println("\nPlacar");
					placar();
					contResp++;

					if((jogador1Continua && jogador2Continua && jogador3Continua) || (jogador1Continua && jogador3Continua && jogador4Continua) || (jogador1Continua && jogador2Continua && jogador4Continua) || (jogador1Continua && jogador2Continua) || (jogador1Continua && jogador3Continua) || (jogador1Continua && jogador4Continua)) {
						vezJog1();
					}
					if((jogador1Continua && jogador2Continua && jogador3Continua) || (jogador2Continua && jogador3Continua && jogador4Continua) || (jogador1Continua && jogador2Continua && jogador4Continua) || (jogador1Continua && jogador2Continua) || (jogador2Continua && jogador3Continua) || (jogador2Continua && jogador4Continua)) {
						vezJog2();
					} 
					if((jogador1Continua && jogador2Continua && jogador3Continua) || (jogador1Continua && jogador3Continua && jogador4Continua) || (jogador2Continua && jogador3Continua && jogador4Continua) || (jogador1Continua && jogador3Continua) || (jogador2Continua && jogador3Continua) || (jogador3Continua && jogador4Continua)) {
						vezJog3();
					}
					if((jogador1Continua && jogador3Continua && jogador4Continua) || (jogador2Continua && jogador3Continua && jogador4Continua) || (jogador1Continua && jogador2Continua && jogador4Continua) || (jogador1Continua && jogador4Continua) || (jogador2Continua && jogador4Continua) || (jogador3Continua && jogador4Continua)){
						vezJog4();
					}

					if (contResp == 20) {
						System.out.println("Os jogadores fizeram uma sequência de 21 itens! Parabéns!\nJogo encerrado");
						if(jogador1Continua) {
							pontosJogador1 += 20;
						}
						if(jogador2Continua) {
							pontosJogador2 += 20;
						}
						if(jogador3Continua) {
							pontosJogador3 += 20;
						}
						if(jogador4Continua) {
							pontosJogador4 += 20;
						}
						jogador1Continua = false; 
						jogador2Continua = false;
						jogador3Continua = false;
						jogador4Continua = false;
						System.out.println("\nPlacar final");
						placarFinal();
						determinarVencedor();
						fimDeJogo();
					}

				} else {
					jogador1Continua = true;
					jogador2Continua = true;
					jogador3Continua = true;
					jogador4Continua = true;
					System.out.println("\nPlacar final");
					placar();
					determinarVencedor();
					fimDeJogo();
					break;
				}

			}

		}


	}

	// FUNÇÃO DA QUANTIDADE DE PLAYER E NOMEAMENTO DESTES

	public static void qtdPlayers() {
		valido = false;

		System.out.println("Digite o número de jogadores que terão na rodada (mín. 2, máx. 4)");

		do {
			try {
				int qtd = ler.nextInt();
				if(qtd == 2) {
					qtdplayers = 2;
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº1" + RESET);
					nomeJogador1 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº2" + RESET);
					nomeJogador2 = ler.next();
					jogador3Continua = false;
					jogador4Continua = false;
					valido = true;
				} else if (qtd == 3) {
					qtdplayers = 3;
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº1" + RESET);
					nomeJogador1 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº2" + RESET);
					nomeJogador2 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº3" + RESET);
					nomeJogador3 = ler.next();
					jogador4Continua = false;
					valido = true;
				} else if (qtd == 4) {
					qtdplayers = 4;
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº1" + RESET);
					nomeJogador1 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº2" + RESET);
					nomeJogador2 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº3" + RESET);
					nomeJogador3 = ler.next();
					System.out.println(NEGRITO + "\nInforme o nome do Jogador nº4" + RESET);
					nomeJogador4 = ler.next();
					valido = true;
				} else if(qtd == 9) {
					menuInicial();
				} else {
					System.out.println(VERMELHO + "\nNúmero de jogadores inválido!\nTente novamente ou digite '9' para voltar ao menu" + RESET);
				}
			} catch(InputMismatchException e) { 
				System.out.println(VERMELHO + "\nEntrada inválida! Digite um número de 2 a 4 ou '9' para voltar ao menu" + RESET);
				ler.nextLine();
			}
		} while(!valido);

	}

	// FUNÇÃO DE REDIRECIONAMENTO PARA QUANDO ACABA A RODADA

	public static void fimDeJogo() {
		valido = false;
		System.out.println(VERMELHO + "\nDigite 9 para voltar ao menu ou 3 para reiniciar a partida com os mesmos jogadores" + RESET);

		do {
			try {
				opcao = ler.nextInt();
				if (opcao == 9) {
					valido = true;
					reiniciar = false;
					telaEmBranco();
					menuInicial();
				} else if (opcao == 3) {
					valido = true;
					reiniciar = true;
					jogar();
				} else {
					System.out.println(VERMELHO + "\nOpção inválida!\nTente novamente" + RESET);
				}
			} catch(InputMismatchException e) {
				System.out.println(VERMELHO + "\nEntrada inválida! Digite 9 para voltar ao menu ou 3 para reiniciar a partida com os mesmos jogadores" + RESET);
				ler.nextLine();
			}
		} while(!valido);
	}

	// FUNÇÃO QUE PULA A LINHA 130 VEZES

	public static void telaEmBranco() {
		for(int i = 0; i < 130; i++) {
			System.out.println();
		}
	}

	// JOGADAS DOS JOGADORES

	public static void vezJog1() {
		// COMEÇO JOGADOR 1

		if(contResp == 0) { // primeira rodada
			System.out.printf("%nVez de %s%n", nomeJogador1);
			respostaJogador1 = ler.next();
			respostas[0] = respostaJogador1; // primeira resposta será guardada na array de respostas
		}
		else { // demais rodadas
			System.out.printf("%nVez de %s%n", nomeJogador1);
			respostaJogador1 = ler.next();
			for(int i = 0; i < contResp; i++) {
				if(!respostaJogador1.equalsIgnoreCase(respostas[i])) { // se errar
					if(qtdplayers == 2) { // herança de pontos 2 jogadores
						pontosJogador2++;
						passarPontos = nomeJogador2;
					} else if(qtdplayers == 3) { // herança de pontos 3 jogadores
						if(jogador2Continua) {
							pontosJogador2++;
							passarPontos = nomeJogador2;
						} else {
							pontosJogador3++;
							passarPontos = nomeJogador3;
						}
					} else if(qtdplayers == 4) { // herança de pontos 4 jogadores
						if(jogador2Continua) {
							pontosJogador2++;
							passarPontos = nomeJogador2;
						} else if(jogador3Continua) {
							pontosJogador3++;
							passarPontos = nomeJogador3;
						} else {
							pontosJogador4++;
							passarPontos = nomeJogador4;
						}
					}
					System.out.printf("%n%s, você perdeu. 1 ponto vai para o próximo jogador: %s%n", nomeJogador1, passarPontos);
					System.out.println(VERMELHO + "Dê Enter para continuar" + RESET);
					enter = ler.nextLine();
					enter = ler.nextLine();
					jogador1Continua = false;
					break;
				} else { // se acertar
					pontosJogador1++;
					respostaJogador1 = ler.next();
					if(i == contResp-1) {
						respostas[contResp] = respostaJogador1;
					}
				}
			}
		}

		// FIM JOGADOR 1
	}

	public static void vezJog2() {
		// COMEÇO JOGADOR 2

		if(contResp < 20) { // exceção para a última rodada. o contador de itens vai parar em 20 itens. se nao parar aqui, vai dar erro
			contResp++;
		}

		if(!jogador1Continua) {
			contResp--;
		}

		telaEmBranco();

		if(rodada == 1) {
			System.out.println(VERMELHO + "\nDica: " + RESET + "Repita a sequência iniciada pelo jogador anterior e adicione mais um item");
			System.out.println(VERMELHO + "Separe os itens por Enter!!!" + RESET);
		} else {
			System.out.printf("\n## RODADA %d ##%n", rodada);
		}

		System.out.println("\nPlacar");
		placar();
		System.out.printf("%nVez de %s%n", nomeJogador2);
		respostaJogador2 = ler.next();
		for(int i = 0; i < contResp; i++) {
			if(!respostaJogador2.equalsIgnoreCase(respostas[i])) { // se errar
				if(qtdplayers == 3) {
					contResp--;
				} else if(qtdplayers == 4) {
					contResp--;
					contResp++;
				}
				if(qtdplayers == 2) { // herança de pontos
					pontosJogador1++;
					passarPontos = nomeJogador1;
				} else if(qtdplayers == 3) {
					if(jogador3Continua) {
						pontosJogador3++;
						passarPontos = nomeJogador3;
					} else {
						pontosJogador1++;
						passarPontos = nomeJogador1;
					}
				} else if(qtdplayers == 4) {
					if(jogador3Continua) {
						pontosJogador3++;
						passarPontos = nomeJogador3;
					} else if(jogador4Continua) {
						pontosJogador4++;
						passarPontos = nomeJogador4;
					} else {
						pontosJogador1++;
						passarPontos = nomeJogador1;
					}
				}
				System.out.printf("%n%s, você perdeu. 1 ponto vai para o próximo jogador: %s%n", nomeJogador2, passarPontos);
				System.out.println(VERMELHO + "Dê Enter para continuar" + RESET);
				enter = ler.nextLine();
				enter = ler.nextLine();
				jogador2Continua = false;
				break;
			} else { // se acertar
				pontosJogador2++;
				respostaJogador2 = ler.next();
				if(i == contResp-1) {
					respostas[contResp] = respostaJogador2;
				}
			}

		}



		// FIM JOGADOR 2
	}

	public static void vezJog3() {
		// COMEÇO JOGADOR 3

		if(contResp < 20) { // exceção para a última rodada. o contador de itens vai parar em 20 itens. se nao parar aqui, vai dar erro
			contResp++;
		}

		if(!jogador1Continua && !jogador2Continua) {
			contResp--;
		}
		telaEmBranco();
		System.out.printf("\n## RODADA %d ##%n", rodada);

		System.out.println("\nPlacar");
		placar();
		System.out.printf("%nVez de %s%n", nomeJogador3);
		respostaJogador3 = ler.next();
		for(int i = 0; i < contResp; i++) {
			if(!respostaJogador3.equalsIgnoreCase(respostas[i])) {
				contResp--;
				if(qtdplayers == 3) { // herança de pontos
					if(jogador1Continua) {
						pontosJogador1++;
						passarPontos = nomeJogador1;
					} else {
						pontosJogador2++;
						passarPontos = nomeJogador2;
					}
				} else if(qtdplayers == 4) {
					if(jogador4Continua) {
						pontosJogador4++;
						passarPontos = nomeJogador4;
					} else if(jogador1Continua) {
						pontosJogador1++;
						passarPontos = nomeJogador1;
					} else {
						pontosJogador2++;
						passarPontos = nomeJogador2;
					}
				}
				System.out.printf("%n%s, você perdeu. 1 ponto vai para o próximo jogador: %s%n", nomeJogador3, passarPontos);
				System.out.println(VERMELHO + "Dê Enter para continuar" + RESET);
				enter = ler.nextLine();
				enter = ler.nextLine();
				jogador3Continua = false;
				break;
			} else {
				pontosJogador3++;
				respostaJogador3 = ler.next();
				if(i == contResp-1) {
					respostas[contResp] = respostaJogador3;
				}
			}
		}

		// FIM JOGADOR 3
	}

	public static void vezJog4() {
		// COMEÇO JOGADOR 4

		if(contResp < 20) { // exceção para a última rodada. o contador de itens vai parar em 20 itens. se nao parar aqui, vai dar erro
			contResp++;
		}
		telaEmBranco();
		System.out.printf("\n## RODADA %d ##%n", rodada);

		System.out.println("\nPlacar");
		placar();
		System.out.printf("%nVez de %s%n", nomeJogador4);
		respostaJogador4 = ler.next();
		for(int i = 0; i < contResp; i++) {
			if(!respostaJogador4.equalsIgnoreCase(respostas[i])) {
				contResp--;
				if(qtdplayers == 4) { // herança de pontos
					if(jogador1Continua) {
						pontosJogador1++;
						passarPontos = nomeJogador1;
					} else if(jogador2Continua) {
						pontosJogador2++;
						passarPontos = nomeJogador2;
					} else if(jogador3Continua) {
						pontosJogador3++;
						passarPontos = nomeJogador3;
					}
				}
				System.out.printf("%n%s, você perdeu. Um ponto vai para o próximo jogador: %s%n", nomeJogador4, passarPontos);
				System.out.println(VERMELHO + "Dê Enter para continuar" + RESET);
				enter = ler.nextLine();
				enter = ler.nextLine();
				jogador4Continua = false;
				break;
			} else {
				pontosJogador4++;
				respostaJogador4 = ler.next();
				if(i == contResp-1) {
					respostas[contResp] = respostaJogador4;
				}
			}
		}

		// FIM JOGADOR 4
	}

	public static void placar() {
		if(jogador1Continua) {
			System.out.printf("%s: %d%n", nomeJogador1, pontosJogador1);
		}
		if(jogador2Continua) {
			System.out.printf("%s: %d%n", nomeJogador2, pontosJogador2);
		}
		if(qtdplayers == 3) {
			if(jogador3Continua) {
				System.out.printf("%s: %d%n", nomeJogador3, pontosJogador3);
			}
		} else if(qtdplayers == 4) {
			if(jogador3Continua) {
				System.out.printf("%s: %d%n", nomeJogador3, pontosJogador3);
			}
			if(jogador4Continua) {
				System.out.printf("%s: %d%n", nomeJogador4, pontosJogador4);
			}
		}

	}

	public static void placarFinal() {

		System.out.printf("%s: %d%n", nomeJogador1, pontosJogador1);
		System.out.printf("%s: %d%n", nomeJogador2, pontosJogador2);
		if(qtdplayers == 3) {
			System.out.printf("%s: %d%n", nomeJogador3, pontosJogador3);
		} else if(qtdplayers == 4) {
			System.out.printf("%s: %d%n", nomeJogador3, pontosJogador3);
			System.out.printf("%s: %d%n", nomeJogador4, pontosJogador4);
		}
	}

	// FUNÇÃO CALCULA O VENCEDOR DA PARTIDA

	public static boolean verificarEmpate(int pontos1, int pontos2, int pontos3, int pontos4) {
		boolean empate = false;

		if(qtdplayers == 2) {
			if(pontosJogador1 == pontosJogador2) {
				empate = true;
			}
		} else if(qtdplayers == 3) {
			if(pontosJogador1 == pontosJogador2 ||
					pontosJogador1 == pontosJogador3 || 
					pontosJogador2 == pontosJogador3) {
				empate = true;
			}
		} else {
			if(pontosJogador1 == pontosJogador2 ||
					pontosJogador1 == pontosJogador3 || 
					pontosJogador1 == pontosJogador4 ||
					pontosJogador2 == pontosJogador3 ||
					pontosJogador2 == pontosJogador4 ||
					pontosJogador3 == pontosJogador4)
				empate = true;
		}

		return empate;
	}	

	public static void determinarVencedor() {
		boolean empate = verificarEmpate(pontosJogador1, pontosJogador2, pontosJogador3, pontosJogador4);
			
			if (qtdplayers == 2) {
				if (pontosJogador1 > pontosJogador2) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador1);
				} else if (pontosJogador2 > pontosJogador1) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador2);
				} else if (empate) {
					System.out.println("\nHouve um empate!");
				}
			} else if (qtdplayers == 3) {
				if (pontosJogador1 > pontosJogador2 && pontosJogador1 > pontosJogador3) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador1);
				} else if (pontosJogador2 > pontosJogador1 && pontosJogador2 > pontosJogador3) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador2);
				} else if (pontosJogador3 > pontosJogador1 && pontosJogador3 > pontosJogador2) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador3);
				} else if (empate) {
					System.out.println("\nHouve um empate!");
				}
			} else if (qtdplayers == 4) {
				if (pontosJogador1 > pontosJogador2 && pontosJogador1 > pontosJogador3 && pontosJogador1 > pontosJogador4) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador1);
				} else if (pontosJogador2 > pontosJogador1 && pontosJogador2 > pontosJogador3 && pontosJogador2 > pontosJogador4) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador2);
				} else if (pontosJogador3 > pontosJogador1 && pontosJogador3 > pontosJogador2 && pontosJogador3 > pontosJogador4) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador3);
				} else if (pontosJogador4 > pontosJogador1 && pontosJogador4 > pontosJogador2 && pontosJogador4 > pontosJogador3) {
					System.out.printf("%n%s é o vencedor da partida!%n", nomeJogador4);
				} else if (empate){
					System.out.println("\nHouve um empate!");
				}
			}
		
		
	}

}

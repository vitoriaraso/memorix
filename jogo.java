import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Jogo {
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
        exibirLogotipo();
        menuInicial();
    }
    
    private static void exibirLogotipo() {
        System.out.println(NEGRITO + "\n" + 
                "  ███╗   ███╗███████╗███╗   ███╗ ██████╗ ██████╗ ██╗██╗  ██╗\n" +
                "  ████╗ ████║██╔════╝████╗ ████║██╔═══██╗██╔══██╗██║╚██╗██╔╝\n" +
                "  ██╔████╔██║█████╗  ██╔████╔██║██║   ██║██████╔╝██║ ╚███╔╝ \n" +
                "  ██║╚██╔╝██║██╔══╝  ██║╚██╔╝██║██║   ██║██╔══██╗██║ ██╔██╗ \n" +
                "  ██║ ╚═╝ ██║███████╗██║ ╚═╝ ██║╚██████╔╝██║  ██║██║██╔╝ ██╗\n" +
                "  ╚═╝     ╚═╝╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝\n" + RESET);
        
        System.out.println(VERMELHO + "        O DESAFIO DEFINITIVO PARA SUA MEMÓRIA" + RESET);
        System.out.println(NEGRITO + "\n        Versão 1.0 | Desenvolvido em Java" + RESET);
        System.out.println("\n═════════════════════════════════════════════════════════════");
    }
    
    // ===== MENU E NAVEGAÇÃO =====
    
    private static void menuInicial() {
        System.out.println("\n" + NEGRITO + "MENU PRINCIPAL" + RESET);
        System.out.println("\nEscolha uma opção:");
        System.out.println(NEGRITO + " [1]" + RESET + " Regras do jogo");
        System.out.println(NEGRITO + " [2]" + RESET + " Iniciar partida");
        System.out.println(NEGRITO + " [3]" + RESET + " Sair");
        System.out.print(VERMELHO + "\nOpção: " + RESET);
        
        int opcao = lerOpcao(1, 3);
        switch (opcao) {
            case 1: regras(); break;
            case 2: jogar(); break;
            case 3: 
                System.out.println("\nObrigado por jogar o Memorix! Até a próxima.");
                System.exit(0);
        }
    }
    
    private static void regras() {
        System.out.println("\n" + NEGRITO + "** REGRAS DO MEMORIX **" + RESET);
        
        System.out.println(NEGRITO + "\nObjetivo do Jogo:" + RESET);
        System.out.println("O Memorix é um jogo que desafia sua capacidade de memorização em uma dinâmica progressiva e competitiva.");
        
        System.out.println(NEGRITO + "\nMecânica de Jogo:" + RESET);
        System.out.println("• Rodadas: O jogo avança em rodadas sequenciais onde cada jogador terá sua vez.");
        System.out.println("• Primeira Rodada: O primeiro jogador inicia com uma única palavra ou número de sua escolha.");
        System.out.println("• Segunda Rodada: O segundo jogador deve repetir a palavra do primeiro jogador e adicionar uma nova.");
        System.out.println("• Terceira Rodada: O próximo jogador deve repetir as duas palavras anteriores e adicionar mais uma.");
        System.out.println("• Rodadas Subsequentes: A sequência vai ficando cada vez mais longa e o desafio maior!");
        System.out.println("• Exemplo de sequência:");
        System.out.println("   - Jogador 1: \"Carro\"");
        System.out.println("   - Jogador 2: \"Carro, Casa\"");
        System.out.println("   - Jogador 3: \"Carro, Casa, Montanha\"");
        System.out.println("   - E assim por diante...");
        System.out.println("• A tela é limpa entre as jogadas para aumentar o desafio de memorização");
        System.out.println("• Separação: Os itens da sequência devem ser inseridos um por vez, separados por Enter");
        
        System.out.println(NEGRITO + "\nSistema de Pontuação:" + RESET);
        System.out.println("• +1 ponto para cada item da sequência repetido corretamente");
        System.out.println("• +1 ponto de bônus (herança) quando um jogador erra, beneficiando o próximo jogador");
        System.out.println("• +20 pontos de bônus para todos os jogadores ativos que completarem uma sequência de 21 itens");
        
        System.out.println(NEGRITO + "\nEliminação:" + RESET);
        System.out.println("• Um jogador é eliminado da rodada quando erra qualquer item da sequência");
        System.out.println("• O jogo continua enquanto houver pelo menos 2 jogadores ativos");
        System.out.println("• A partida termina quando apenas um jogador permanece ou a sequência atinge 21 itens");
        
        System.out.println(NEGRITO + "\nVitória:" + RESET);
        System.out.println("• Vencedor: O jogador com mais pontos ao final da partida");
        System.out.println("• Empate: Caso dois ou mais jogadores terminem com a mesma pontuação");
        
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
        System.out.println(NEGRITO + "\n═════════════════════════════════════════════" + RESET);
        System.out.println(VERMELHO + "Digite 9 para voltar ao menu ou 3 para jogar novamente com os mesmos jogadores" + RESET);
        
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
        System.out.println("\n" + NEGRITO + "** NOVO JOGO INICIADO **" + RESET);
        System.out.println("\n" + NEGRITO + "MECÂNICA DO JOGO:" + RESET);
        System.out.println("1. Primeiro jogador informa uma palavra ou número");
        System.out.println("2. Próximos jogadores repetem a sequência anterior + um novo item");
        System.out.println("3. A cada rodada, a sequência fica mais longa e o desafio maior");
        System.out.println("4. Quem errar qualquer item da sequência é eliminado");
        System.out.println("5. Jogue até restar apenas 1 jogador ou completar 21 itens");
        
        // Fase de inicialização: apenas o primeiro jogador define a primeira palavra
        limparTela();
        System.out.println(NEGRITO + "\n## FASE INICIAL ##" + RESET);
        System.out.println(VERMELHO + "\nInstruções: " + RESET + 
                         "O primeiro jogador deve informar uma palavra ou número para iniciar a sequência");
        System.out.println("\n" + NEGRITO + "PLACAR INICIAL" + RESET);
        exibirPlacar(false);
        
        // Primeira palavra definida apenas pelo jogador 1
        Jogador primeiroJogador = jogadores.get(0);
        System.out.printf("%n" + NEGRITO + "Vez de %s" + RESET + "%n", primeiroJogador.nome);
        System.out.println("Digite uma palavra ou número para iniciar a sequência (sem espaços):");
        String primeiraPalavra = scanner.next();
        sequencia[0] = primeiraPalavra;
        contadorSequencia = 0;
        
        System.out.println("✓ Sequência iniciada com: " + primeiraPalavra);
        System.out.println(VERMELHO + "\nPressione ENTER para continuar com o próximo jogador" + RESET);
        scanner.nextLine();
        scanner.nextLine();
        
        // Começa o jogo a partir do segundo jogador
        int jogadorAtual = 1;  // Começa pelo jogador 2 (índice 1)
        rodada = 1;            // Primeira rodada real
        
        while (contadorSequencia < MAX_SEQUENCIA - 1 && jogadoresAtivos() >= 2) {
            limparTela();
            System.out.printf("\n" + NEGRITO + "## RODADA %d ##" + RESET + "%n", rodada);
            
            System.out.println(VERMELHO + "\nLembrando: " + RESET + 
                             "A sequência atual possui " + (contadorSequencia + 1) + " item(s)");
            System.out.println("Cada jogador deve repetir TODOS esses itens na ordem correta e adicionar um novo.");
            
            System.out.println("\n" + NEGRITO + "PLACAR ATUAL" + RESET);
            exibirPlacar(false);
            
            // Joga um jogador por vez, na ordem, começando do segundo jogador
            int jogadasNaRodada = 0;
            while (jogadasNaRodada < jogadores.size() && jogadoresAtivos() >= 2) {
                Jogador jogador = jogadores.get(jogadorAtual);
                if (jogador.ativo) {
                    processarJogada(jogador, jogadorAtual);
                    jogadasNaRodada++;
                }
                
                // Avança para o próximo jogador, voltando ao início quando necessário
                jogadorAtual = (jogadorAtual + 1) % jogadores.size();
            }
            
            // Verificar se completou a sequência
            if (contadorSequencia == MAX_SEQUENCIA - 1) {
                System.out.println(NEGRITO + "\n╔═════════════════════════════════╗");
                System.out.println("║    SEQUÊNCIA COMPLETA!         ║");
                System.out.println("╚═════════════════════════════════╝" + RESET);
                System.out.println("Os jogadores completaram uma sequência de " + MAX_SEQUENCIA + 
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
            if (jogadoresAtivos() < 2) {
                System.out.println(NEGRITO + "\n╔═════════════════════════════════╗");
                System.out.println("║    FIM DE JOGO!                ║");
                System.out.println("╚═════════════════════════════════╝" + RESET);
                System.out.println("Apenas um jogador restou na partida.");
            }
            System.out.println("\nPlacar final");
            exibirPlacar(true);
            determinarVencedor();
        }
        
        fimDeJogo();
    }
    
    private static void processarJogada(Jogador jogador, int indiceJogador) {
        limparTela();
        
        if (rodada == 1 && indiceJogador == 1) {
            // Primeiro jogador da primeira rodada (segundo jogador do jogo)
            System.out.println(NEGRITO + "\n## RODADA " + rodada + " ##" + RESET);
            System.out.println(VERMELHO + "\nInstruções: " + RESET + 
                             "Você deve repetir a palavra do primeiro jogador e adicionar uma nova palavra");
            System.out.println(VERMELHO + "Importante: " + RESET + "Digite um item por vez, pressionando ENTER após cada um");
        } else {
            System.out.println(NEGRITO + "\n## RODADA " + rodada + " ##" + RESET);
            System.out.println(VERMELHO + "\nInstruções: " + RESET + 
                            "Você deve repetir TODAS as " + (contadorSequencia + 1) + 
                            " palavras anteriores na ordem correta e adicionar uma nova");
            System.out.println(VERMELHO + "Importante: " + RESET + "Digite um item por vez, pressionando ENTER após cada um");
        }
        
        System.out.println("\n" + NEGRITO + "PLACAR ATUAL" + RESET);
        exibirPlacar(false);
        System.out.printf("%n" + NEGRITO + "Vez de %s" + RESET + "%n", jogador.nome);
        
        // Exibe a contagem de itens a serem repetidos
        System.out.println("Você precisa repetir " + (contadorSequencia + 1) + 
                         " item(s) e adicionar 1 novo item no final da sequência.");
        
        System.out.println("Digite cada item da sequência, pressionando ENTER após cada um:");
        
        // Loop para verificar cada palavra da sequência
        String resposta = scanner.next();
        
        // Verificação das palavras da sequência existente
        for (int i = 0; i <= contadorSequencia; i++) {
            if (!resposta.equalsIgnoreCase(sequencia[i])) {
                // Errou a sequência
                Jogador proximoJogador = obterProximoJogadorAtivo(indiceJogador);
                proximoJogador.pontos++;
                
                System.out.println(VERMELHO + "\n✖ ERRO NA SEQUÊNCIA!" + RESET);
                System.out.printf("%s, você errou no item %d. O termo correto era: %s%n", 
                                jogador.nome, i+1, sequencia[i]);
                System.out.printf("1 ponto de bônus foi transferido para %s%n", 
                                proximoJogador.nome);
                System.out.println(VERMELHO + "\nPressione ENTER para continuar" + RESET);
                scanner.nextLine();
                scanner.nextLine();
                
                jogador.ativo = false;
                return;
            }
            
            // Acertou, acrescenta ponto
            jogador.pontos++;
            
            // Se não for o último item da sequência, mostra feedback positivo e pede o próximo
            if (i < contadorSequencia) {
                System.out.println("✓ Correto! Continue com o próximo item da sequência...");
                resposta = scanner.next();
            }
        }
        
        // Se chegou aqui, repetiu toda a sequência corretamente, agora adiciona nova palavra
        System.out.println("✓ Sequência completa! Agora adicione um novo item ao final:");
        resposta = scanner.next();
        contadorSequencia++;
        sequencia[contadorSequencia] = resposta;
        System.out.println("Item adicionado com sucesso! Sequência atualizada para " + (contadorSequencia + 1) + " itens.");
        
        // Pausa para o jogador ver o resultado
        System.out.println(VERMELHO + "\nPressione ENTER para continuar" + RESET);
        scanner.nextLine();
        scanner.nextLine();
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
            System.out.println(NEGRITO + "\n╔══════════════════════════╗");
            System.out.println("║     PLACAR FINAL          ║");
            System.out.println("╚══════════════════════════╝" + RESET);
            
            for (Jogador jogador : jogadores) {
                System.out.printf("  %s: %d pontos%n", jogador.nome, jogador.pontos);
            }
        } else {
            for (Jogador jogador : jogadores) {
                if (jogador.ativo) {
                    System.out.printf("  %s: %d pontos%n", jogador.nome, jogador.pontos);
                }
            }
        }
    }
    
    private static void determinarVencedor() {
        if (verificarEmpate()) {
            System.out.println(NEGRITO + "\n╔══════════════════════════╗");
            System.out.println("║        EMPATE!           ║");
            System.out.println("╚══════════════════════════╝" + RESET);
            System.out.println("Vários jogadores alcançaram a mesma pontuação!");
            return;
        }
        
        Jogador vencedor = jogadores.get(0);
        for (Jogador jogador : jogadores) {
            if (jogador.pontos > vencedor.pontos) {
                vencedor = jogador;
            }
        }
        
        System.out.println(NEGRITO + "\n╔══════════════════════════╗");
        System.out.println("║      VENCEDOR(A)!         ║");
        System.out.println("╚══════════════════════════╝" + RESET);
        System.out.printf("  %s é o grande vencedor com %d pontos!%n", vencedor.nome, vencedor.pontos);
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

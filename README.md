![cover](https://i.imgur.com/1jzBaA0.png "Cover")
# Memorix

**Memorix** é um jogo de memória em Java limitado à execução no terminal que desafia sua capacidade de lembrar e expandir sequências de palavras.

## Funcionalidades

- Menu interativo com as opções de regras, jogar e sair.
- Suporte para 2 a 4 jogadores com personalização dos nomes.
- Sistema de pontuação baseado em respostas corretas e bônus.
- Exibição de placar e determinação automática do vencedor.
- Dinâmica de jogo com dificuldade progressiva.
- Reinício de partidas com os mesmos jogadores.

## Como Jogar

1. Escolha entre ler as regras, iniciar o jogo ou sair.
2. Insira os nomes dos jogadores.
3. O primeiro jogador inicia a sequência com uma palavra.
4. O próximo jogador repete a sequência anterior e adiciona uma nova palavra.
5. Continue até que alguém erre ou complete uma sequência de 21 palavras.

## Regras

- Cada jogador deve repetir corretamente a sequência e adicionar uma nova palavra.
- Caso um jogador erre, ele sai da rodada, e pontos de herança vão para o próximo jogador.
- O jogo termina quando todos erram ou a sequência atinge 21 itens.

### Pontuação

- +1 ponto por palavra correta.
- +1 ponto de bônus por herança.
- +20 pontos ao completar 21 itens na sequência.

## Requisitos

- Java Development Kit (JDK) 8 ou superior.

## Como Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/vitoriaraso/memorix.git

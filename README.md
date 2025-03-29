![cover](https://i.imgur.com/1jzBaA0.png "Cover")

# Memorix

**Memorix** é um jogo de memorização sequencial desenvolvido em Java que desafia os jogadores a lembrarem e expandirem sequências de palavras ou números em um ambiente competitivo.

## 🎮 Visão Geral

Memorix é um jogo de memória executado no terminal que testa a capacidade dos jogadores de memorizar sequências cada vez mais longas. O jogo combina elementos de desafio cognitivo com estratégia competitiva, oferecendo uma experiência divertida para grupos de 2 a 4 jogadores.

## ✨ Funcionalidades

- **Menu Interativo**: Navegue facilmente entre as opções de regras, jogo e saída
- **Suporte Multijogador**: Jogue com 2 a 4 jogadores, cada um com seu nome personalizado
- **Sistema de Pontuação Dinâmico**: Ganhe pontos por itens memorizados corretamente e bônus especiais
- **Placar em Tempo Real**: Acompanhe o desempenho de cada jogador durante a partida
- **Desafio Progressivo**: A dificuldade aumenta naturalmente a cada rodada
- **Reinício Rápido**: Reinicie partidas com os mesmos jogadores para revanche imediata

## 📋 Regras Detalhadas

### Objetivo do Jogo
O objetivo é acumular o maior número de pontos através da memorização e repetição correta de sequências.

### Mecânica Principal
1. **Primeira Rodada**: O primeiro jogador inicia com uma única palavra ou número.
2. **Rodadas Subsequentes**: Cada jogador deve:
   - Repetir toda a sequência anterior na ordem exata
   - Adicionar um novo item ao final da sequência

### Sistema de Pontuação
- **Itens Corretos**: +1 ponto para cada item da sequência repetido corretamente
- **Bônus de Herança**: +1 ponto quando um jogador erra, beneficiando o próximo jogador ativo
- **Bônus de Conclusão**: +20 pontos para todos os jogadores ativos que completarem uma sequência de 21 itens

### Eliminação
- Um jogador é eliminado quando erra qualquer item da sequência
- O jogo continua enquanto houver pelo menos 2 jogadores ativos
- A partida termina quando a sequência atinge 21 itens ou quando apenas um jogador permanece

### Vitória
O jogador com mais pontos ao final da partida é declarado vencedor. Empates são possíveis quando dois ou mais jogadores terminam com a mesma pontuação.

## 🚀 Como Jogar

1. Execute o jogo e selecione a opção "JOGAR" no menu inicial
2. Insira o número de jogadores (2-4) e seus respectivos nomes
3. O primeiro jogador digita uma palavra ou número para iniciar a sequência
4. Os jogadores se alternam, repetindo a sequência e adicionando novos itens
5. A tela é limpa entre as jogadas para aumentar o desafio de memorização
6. O jogo continua até que reste apenas um jogador ou a sequência atinja 21 itens
7. Ao final, o jogador com mais pontos é declarado vencedor

## 💻 Requisitos Técnicos

- Java Development Kit (JDK) 8 ou superior
- Terminal com suporte para códigos ANSI (para formatação de texto colorido)
- Espaço em disco mínimo: < 1 MB

## 🛠️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/vitoriaraso/memorix.git
   ```

2. Navegue até o diretório do projeto:
   ```bash
   cd memorix
   ```

3. Compile o código-fonte:
   ```bash
   javac jogo.java
   ```

4. Execute o jogo:
   ```bash
   java Main
   ```

## 📝 Dicas para uma Melhor Experiência

- **Estratégia de Memorização**: Tente criar associações entre os itens da sequência
- **Escolha Itens Simples**: Palavras curtas ou números simples são mais fáceis de lembrar
- **Comunique com Clareza**: Diga suas respostas em voz alta e clara
- **Mantenha o Foco**: Evite distrações durante a partida para maximizar seu desempenho

## 👥 Contribuições

Contribuições são bem-vindas! Se você tem ideias para melhorar o Memorix, sinta-se à vontade para:

1. Abrir issues com sugestões ou relatórios de bugs
2. Enviar pull requests com novos recursos ou correções
3. Melhorar a documentação

---

Desenvolvido como projeto educacional para praticar conceitos de programação em Java.

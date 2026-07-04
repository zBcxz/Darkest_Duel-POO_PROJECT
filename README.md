# The Darkest Duel

Jogo de duelo em turnos desenvolvido em Java para a disciplina de Programação Orientada a Objetos.

O sistema simula uma batalha entre dois jogadores em uma arena linear, onde cada jogador escolhe uma classe de personagem e executa ações estratégicas utilizando pontos de ação, chamados de AC. O jogo possui movimentação, ataques, efeitos de status, cooldowns, dano crítico, áreas em chamas e tratamento customizado de exceções.

O objetivo do projeto é demonstrar os principais conceitos de POO, como classes, objetos, encapsulamento, herança, polimorfismo, abstração, exceptions personalizadas, testes unitários e documentação com JavaDoc.

## Funcionalidades do Sistema

### Sistema de Classes de Personagem

O jogo possui diferentes classes jogáveis, cada uma com atributos e ações próprias:

- Warrior
- Archer
- Assassin
- Spear Master

Cada classe possui características específicas, como HP máximo, dano, quantidade de AC por turno, limite de movimento, chance de crítico e ações disponíveis.

### Sistema de Combate em Turnos

A partida ocorre em turnos alternados entre os jogadores. A cada turno, o jogador recebe AC e pode executar ações enquanto tiver pontos suficientes.

As ações disponíveis incluem:

- Ataques corpo a corpo
- Ataques à distância
- Movimentação na arena
- Defesa
- Evasão
- Contra-ataque
- Regeneração
- Bomba incendiária
- Esperar para preservar AC

### Arena e Movimentação

O jogo utiliza uma arena linear, onde os jogadores ocupam posições específicas. A movimentação permite aproximação ou afastamento do adversário, respeitando os limites da arena e evitando ocupar a mesma casa do oponente.

### Efeitos de Status

O sistema possui efeitos temporários que alteram o comportamento do jogador durante a partida, como:

- Defesa, que reduz o próximo dano recebido
- Evasão, que pode anular um ataque
- Contra-ataque, que concede AC ao jogador atacado
- Regeneração, que recupera HP no início dos turnos

### Perigos de Ambiente

A ação Bomba Incendiária cria zonas de fogo na arena. Jogadores que estiverem em casas em chamas recebem dano no início do turno.

### Tratamento Customizado de Erros

O projeto possui exceções personalizadas para validar regras importantes do jogo, como:

- Arena com tamanho inválido
- Movimento inválido
- AC negativo
- AC insuficiente
- Efeito de status inválido
- Multiplicador de dano inválido
- Ação com custo inválido

## Conceitos de POO Utilizados

- Classes e objetos
- Atributos e métodos
- Encapsulamento
- Herança
- Polimorfismo
- Abstração
- Exceptions personalizadas
- Testes unitários
- JavaDoc

## Tecnologias Utilizadas

- Java
- Maven
- JUnit 5
- JavaDoc
- Git e GitHub

## Organização das Pastas e Arquivos

```text
TheDarkestDuel/
├── docs/                         # Imagens e documentação do projeto
│   └── diagrama-classes.png      # Diagrama de classes
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── darkestduel/
│   │           ├── app/          # Classe principal Main
│   │           ├── actions/      # Ações executáveis pelos jogadores
│   │           ├── classes/      # Classes de personagem
│   │           ├── effects/      # Efeitos de status
│   │           ├── exceptions/   # Exceções personalizadas
│   │           ├── game/         # Arena, partida, turnos e controlador
│   │           ├── model/        # Classe Player
│   │           └── util/         # Classes auxiliares
│   │
│   └── test/
│       └── java/
│           └── darkestduel/
│               ├── actions/     # Testes das ações
│               ├── classes/     # Testes das classes de personagem
│               ├── effects/     # Testes dos efeitos
│               ├── game/        # Testes da arena
│               └── model/       # Testes do jogador
│
├── target/                       # Arquivos gerados pelo Maven
├── .gitignore                    # Arquivos ignorados pelo Git
├── pom.xml                       # Configuração do Maven
└── README.md                     # Apresentação do projeto
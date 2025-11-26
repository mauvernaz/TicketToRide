# Ticket to Ride (Versão Normal)

Implementação digital do  jogo de tabuleiro **Ticket to Ride (Mapa USA)**, desenvolvida utilizando JavaFX, para matéria de Projeto de Software.

##### Conceitos GRASP explorados
- **Information Expert**: Focamos em criar classes especialistas, algumas possuem o comentário "Information Expert" como exemplo
- **High Cohesion**: Nos esforçamos em evitar God Classes, no histórico de commits é possível ver a dissolução de uma GodClass chamada `JogoController` e sua divisão de responsabilidades.
- **Controller**: A classe `JogoUIController` atua como controller no nosso projeto, gerenciando o caso de uso principal, atribuindo a responsabilidade lógica para `JogoUIService` e outras classes.

##### Conceitos GoF explorados
- **Singleton**: As classes `Jogadores`, `Tabuleiro`, e as herdadas de `DeckCartas` fizeram uso de singleton para simplificar...
- **Builder**: A classe `UiService`, por possuir múltiplos atributos, foi desenvolvida fazendo uso de Builder.


## 🛠️ Tecnologias Utilizadas
* [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
* **JavaFX** (Interface Gráfica)
* **Maven** (Gerenciamento de dependências e build)


## 🚀 Como Rodar o Projeto

Siga os passos abaixo para executar a aplicação em seu ambiente local:

1. **Clone o repositório**
2. Instale as tecnologias usadas
3. Execute o comando maven `mvn javafx:run`
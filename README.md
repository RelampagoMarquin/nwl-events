## ℹ️ Sobre

Sistema de inscrições em eventos, onde os usuários podem se inscrever, gerar links de indicação e acompanhar um ranking de indicações.

## 🚀 Tecnologias

O projeto foi desenvolvido com as seguintes tecnologias:

- **Java 17**
- **Spring Boot 3.4.2**
- **Maven**

### Dependências utilizadas:

- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- Lombok
- MySQL Driver

## 📂 Estrutura do Projeto

O projeto segue a organização tradicional do Spring Boot, adotando a separação de responsabilidades.

**Padrão em camadas**: Model, Controller, Service, Repository, Padrão DTO.

## ✨ Funcionalidades

- 📌 **Inscrição**: Usuários podem se inscrever em eventos informando nome e e-mail.
- 🔗 **Geração de Link de Indicação**: Cada inscrito pode gerar um link de indicação único.
- 📊 **Ranking de Indicações**: Exibe um ranking baseado no número de indicações bem-sucedidas.
- 👥 **Visualização de Indicações**: Cada usuário pode ver quantas pessoas se inscreveram usando seu link.

## 🌐 Rotas da API
Principais rotas disponíveis na aplicação:

```bash
localhost:8080/ #base url local host
```

### 🎟️ Eventos
```bash
(GET) events/{prettyName} #busca pelo nome
```
```bash
(GET) /events #busca todos os eventos
```
POST:
```bash
(POST) /events #add um no evento

# json de exemplo
{
  "title": "SUPPER SLAM",
  "location": "Online",
  "price": 0.0,
  "startDate": "2027-03-16",
  "endDate": "2027-03-18",
  "startTime": "19:00:00",
  "endTime": "21:00:00"
}
```

### 📝 Inscrição

- **Realizar inscrição no evento**  
```bash
  (POST) `/subscription/{prettyName}`  

  # json de exemplo
  {
    "name":"John cena", 
    "email":"neverGiveUp@cena.com"
  }
```

- **Cadastrar usuário com id de indicação**  
```bash
  (POST) `/subscription/{prettyName}/ranking/{userId}`

  # json de exemplo
  {
    "name":"CM PUNK", 
    "email":"bestInTheWorld@cmpunk.com"
  }  
```

- **Visualizar ranking de indicações de um evento**  
```bash
  (GET) `/subscription/{prettyName}/ranking`  
```

- **Visualizar ranking de indicações de um usuário específico**  
```bash
  (GET) `/subscription/{prettyName}/ranking/{userId}` 
```

## ▶️ Como Rodar o Projeto

Para executar o projeto localmente, siga os passos abaixo:

1. Clone o repositório:

    ```bash
    git clone https://github.com/RelampagoMarquin/nwl-events.git
    ```

2. Importe o projeto na sua IDE de escolha (Eclipse, IntelliJ, STS):

    - Selecione **Import > Import Maven Projects**

3. Configure o banco de dados:

    - Edite o arquivo `application.properties` e adapte o valor de `spring.datasource.password` (linha 3) conforme sua configuração local.
    - No MySQL, crie o schema do banco de dados:

      ```sql
      CREATE SCHEMA db_events;
      ```
    - caso não tenha o mysql e nem queira instalar, tem um composer de exemplo para subir um servidor mysql no docker basta usar o seguinte comando:
    ```bash
      docker-compose up --build
    ```
4. Inicie o servidor:

    - Execute a aplicação dentro da sua IDE ou via terminal com:

      ```bash
      mvn spring-boot:run
      ```
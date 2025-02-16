# JavaSpringExpert-FifthChallenge

# Sobre o projeto

FifthChallenge é uma aplicação backend construída como atividade avaliativa do curso Java Spring Expert, ministrado pelo professor ![Nelio Alves](https://www.linkedin.com/in/nelio-alves/).

A aplicação consiste em testes de API'S utilizando RestAssured para o projeto ![JavaSpringExpert-FourthChallange](https://github.com/Bruno-Basques/JavaSpringExpert-FourthChallange), em que se testa os seguintes cenários:

MovieContollerRA:
- findAllShouldReturnOkWhenMovieNoArgumentsGiven
- findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty
- findByIdShouldReturnMovieWhenIdExists
- findByIdShouldReturnNotFoundWhenIdDoesNotExist
- insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle
- insertShouldReturnForbiddenWhenClientLogged
- insertShouldReturnUnauthorizedWhenInvalidToken

ScoreContollerRA:
- saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist
- saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId
- saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- Maven
- RestAssured

# Como executar o projeto

## Back end
Pré-requisitos: Java 21

```bash
# clonar repositório
git clone [https://github.com/Bruno-Basques/JavaSpringExpert-FourthChallange.git]
git clone [https://github.com/Bruno-Basques/JavaSpringExpert-FifthChallange.git]

# entrar na pasta do projeto FourthChallange
cd JavaSpringExpert-FourthChallange

# executar o projeto
mvnw spring-boot:run

# acessar o projeto JavaSpringExpert-FifthChallange com a sua IDE de preferência e executar os testes
```

# Autor

Bruno A. B. R. C. Rodrigues

https://www.linkedin.com/in/bruno-basques/

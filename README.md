[![NPM](https://img.shields.io/npm/l/react)](https://github.com/josimarrenepont/supera-task_management/blob/main/LICENSE)
# Sistema de Gerenciamento de Tarefas

## Visão Geral do Projeto
A aplicação permite a criação e gerenciamento de listas de tarefas, adição, edição, remoção e organização de itens, além de oferecer opções de filtragem e destaque de itens prioritários.

## Requisitos

### Requisitos Funcionais
1. **Criação de Listas**: O usuário pode criar e gerenciar listas de tarefas.
2. **Gerenciamento de Itens**: Adicionar, editar, remover e alterar o estado dos itens dentro de listas.
3. **Visualização e Filtragem**: Visualizar e organizar listas e itens, com opções de filtragem.
4. **Prioridade de Itens**: Destacar itens para indicar prioridade.

### Regras de Negócio
1. **Validação de Dados**: Itens devem seguir critérios básicos de validação.
2. **Estado dos Itens**: Cada item deve ter um estado alterável.
3. **Ordenação e Destaque**: Itens destacados devem aparecer com prioridade.

### Requisitos Não Funcionais
1. **Persistência de Dados**: Dados armazenados de forma persistente.
2. **Exposição de API**: API disponível para operações principais.
3. **Testes Automatizados**: Inclusão de testes automatizados para principais funcionalidades.

## Tecnologias utilizadas

### Back End

* JDK 21
* Java <version>21.0.3</version>
* Spring Boot <version>3.3.2</version>
* JPA / Hibernate
* Maven
* Mockito
  
### Implantação em produção

* Banco de dados: PostgreSQL

## Endpoints da API
- **TaskListController**:
  - `GET /taskList`
  - `GET /taskList/{id}`
  - `POST /taskList`
  - `PUT /taskList/{id}`
  - `DELETE /taskList/{id}`
- **ItemController**:
  - `GET /items`
  - `GET /items/{id}`
  - `POST /items`
  - `PUT /items/{id}`
  - `DELETE /items/{id}`
  - `GET /items/findByTitle`

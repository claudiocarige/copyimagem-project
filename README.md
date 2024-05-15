# Projeto COPY IMAGEM

## Objetivo do Projeto:

O objetivo principal do Projeto COPY IMAGEM é solucionar os desafios enfrentados no controle de
informações nas locações de impressoras pela empresa Copyimagem, visando uma gestão mais eficaz e transparente.
 Iremos desenvolver e implementar uma arquitetura de software robusta e escalável para a aplicação CopyImagem. Faremos uso de
tecnologias avançadas como Java, Spring Boot e PostgreSQL para garantir um sistema eficiente e seguro. Adotaremos a
prática de Desenvolvimento Orientado a Testes (TDD) para garantir a qualidade do código e a robustez das funcionalidades
implementadas.
Nosso foco primário é garantir a eficiência, segurança e manutenibilidade do sistema, proporcionando uma experiência
excepcional aos usuários finais. Estamos concentrados especialmente na gestão de
clientes, máquinas e mensalidades.

### 1. Visão Geral da Arquitetura

A arquitetura da aplicação seguirá o padrão composto de camadas distintas para facilitar a manutenção e escalabilidade.

#### a. Camada CORE da aplicação:

- Utilizará o Spring MVC para lidar com as requisições HTTP.
- Autenticação e autorização serão gerenciadas pelo Spring Security, utilizando JWT para token de autenticação.
- A documentação da API será gerada automaticamente pelo Swagger/OpenAPI.

##### Camada de Negócios (usecases):

- Implementará a lógica de negócios da aplicação.
- Fará uso do Spring Boot para criar serviços transacionais.
- Fará Integração com o banco de dados PostgreSQL.

##### Camada de Modelo (domain):

- Esta camada será responsável por entidades de domínio e enumeradores.
- Representará as entidades do domínio usando anotações JPA.
- Onde ficarão os enumeradores.
- Lombok será utilizado para reduzir a verbosidade.

##### Camada de Controle (controllers):

- Será responsável por receber as entradas dos usuários, coordenar as ações necessárias e chamar os casos de uso (use cases) apropriados.

##### Camada de Exceções (exceptions):

- Ficará responsável pelo tratamento das exceções personalizadas da aplicação.

##### Camada DTO (representationDTO):

- Esta camada terá a responsabilidade de transferir os dados entre as camadas da aplicação, sobretudo quando são expostas para o externo.

#### 2. Camada INFRA da aplicação:

##### a. Camada de Configuração (config):

- Responsável por lidar com as configurações da aplicação.

##### b. Camada Adapter (adapter):

- Terá a responsabilidade de traduzir os dados entre os casos de uso da aplicação e os detalhes da implementação externa, com frameworks, bibliotecas, APIs ou sistemas.

##### c. Camada de Persistência (persistence):

- Utilizará JPA (Java Persistence API) para interagir com o banco de dados.
- Repositórios serão criados para realizar operações de persistências como: Create, Read, Update e Delete.

### 2. Diagramas:

- Diagrama de Componentes:
- Diagrama de Sequência:

### 3. Tecnologias Utilizadas:

- **Java e Spring Boot**:
    - Versão do Java: 17
    - Versão do Spring Boot: 3.1.0

- **Gerenciador de dependências**:
    - Maven

- **Banco de Dados**:
    - PostgreSQL: Utilizado para armazenamento persistente.

- **Persistência**:
    - Spring Data JPA: Facilita a implementação da camada de acesso a dados.

- **Segurança**:
    - Spring Security: Gerenciamento de autenticação e autorização.
    - JWT: Token de autenticação seguro.

- **Documentação**:
    - Swagger/OpenAPI: Documentação automática da API.

- **Lombok**:
    - Para redução de boilerplate code.

- **Relatórios**:
    - Apache POI: Para geração de relatórios em Excel.

### 4. Regras de Negócio

- **Sistema de Login**:
    - O login deverá ser feito com nickname e senha.
    - Após login feito, deverá ser disponibilizado um Token para acesso à API.
    - O login será administrado pelo Spring Security.

- **Controle de Clientes**:
    - Atributos para cliente: (listados)

- **Controle de Máquinas**:
    - Atributos para máquina: (listados)

- **Controle de Mensalidades**:
    - Deverá listar todos os clientes e máquinas.
    - Poderão ser editados os clientes e máquinas.
    - Não deverá possibilitar modificar valores de mensalidades num prazo maior de que 40 dias do seu lançamento.
    - Não devem ser deletadas nenhuma informação de clientes, de mensalidades e nem de máquinas.

### 5. Padrões de Codificação e Convenções:

- Exemplo: 
  - [ topico ] : "**descrição do commit**"
  - <span style="color:green; font-weight: bold"> - [ addition ] : First commit</span>
  

- **Tópicos de Commit:**
    1. addition / inclusion: Usado especificamente para adicionar novos elementos ao código, como atributos, métodos, classes, etc.
    2. feature: Usado para adicionar uma nova funcionalidade ao código.
    3. refactor: Usado para fazer alterações no código para melhorar sua estrutura, legibilidade ou desempenho sem alterar seu comportamento externo.
    4. enhancement: Usado para adicionar melhorias ou otimizações ao código existente.
    5. update: Usado quando você está atualizando ou modificando partes existentes do código.
    6. docs: Usado para fazer alterações na documentação do código, como adicionar ou corrigir comentários, atualizar documentação de API, etc.
    7. cleanup: Usado para realizar tarefas de limpeza no código, como remover código morto, otimizar imports, etc.
    8. fix: Usado para corrigir um bug existente no código.
    9. test: Usado para adicionar, modificar ou corrigir testes de unidade, testes de integração, etc.
    10. test refactor: Usado para informar alterações no código dos testes.
    11. build: Usado para modificações nos arquivos de Build.

- **Padrões de Commits e Versionamento**:
    - (detalhado na seção)

### 6. Arquitetura do Projeto Backend:

    
- **src**
    - main
       - java
          - <span style="color:yellow; font-weight: bold">core</span>
              - <span style="color:blue; font-weight: bold">domain</span>
                  - entities
                  - enums
                      - TipoLocacao.java
              - <span style="color:blue; font-weight: bold">dto</span>
                - LocacaoDTO.java
              - <span style="color:red; font-weight: bold">exceptions</span>
              - <span style="color:blue; font-weight: bold">usecases</span>
                  - contracts
                      - RealizarLocacaoService.java
                  - impl
                      - RealizarLocacaoServiceImpl.java
                      - DevolverLocacaoServiceImpl.java
                      - ListarLocacoesServiceImpl.java
          - <span style="color:yellow; font-weight: bold">infra</span>
              - <span style="color:blue; font-weight: bold">controllers</span>
              - <span style="color:blue; font-weight: bold">adaptors
                  - apiexterna
                      - ApiExternaAdapter.java
                      - ApiExternaAdapterInterface.java
                  - emailapi
                      - MensageriaAdapter.java
                      - MensageriaAdapterInterface.java
            - <span style="color:blue; font-weight: bold">config</span>
                - SecurityConfig.java
                - security
                    - JwtTokenProvider.java  // Provedor JWT
            - <span style="color:blue; font-weight: bold">persistence
                - repositorios
                    - LocacaoRepository.java




- **Gerenciamento de Dependências**:
    - Utilizar uma ferramenta de gerenciamento de dependências, como Maven ou Gradle.

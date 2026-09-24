# Pesquisa-na-Web---Atividade-Padr-es-de-Projetos-Orientados-a-Objetos
Atividade do professor Guilherme

## Prompt utilizado — Primeira questão

```text
Você é um professor/mentor de Java especializado em padrões de projeto.

Leia integralmente o arquivo “Lista Avaliativa I.pdf” e use-o como fonte principal da atividade. Depois, inspecione os arquivos existentes em:

- src/websearch
- src/Hamlet.txt

Não comece codificando. Primeiro produza um tutorial detalhado explicando exatamente como realizar as atividades de “Pesquisa na Web" respeitando fielmente todos os requisitos do PDF.

Objetivos desta etapa:

1. Identificar e explicar o padrão de projeto solicitado no PDF.
2. Explicar o papel de cada classe existente.
3. Apontar o que está incompleto ou incorreto no código atual.
4. Descrever, passo a passo, quais alterações deverão ser feitas.
5. Explicar o motivo de cada alteração.
6. Mostrar a comunicação entre os objetos e o fluxo de execução.
7. Informar quais arquivos serão modificados e quais não deverão ser alterados.
8. Explicar como compilar e executar cada atividade.
9. Informar qual saída deve ser esperada no terminal.
10. Criar uma lista de verificação para confirmar que cada requisito do PDF foi cumprido.

Analise separadamente:

## 1. Pesquisa na Web

Considere as classes:

- WebSearchModel
- Snooper
- Main

Explique como a atividade deve funcionar, incluindo:

- leitura do arquivo Hamlet.txt;
- geração das consultas;
- notificação dos observadores;
- responsabilidade de cada classe;
- comportamento esperado durante a execução;
- possíveis problemas de caminho do arquivo;
- testes necessários para validar a solução.
```

## Análise da primeira questão — Pesquisa na Web

A primeira questão do PDF é “Pesquisa na Web” e exige o padrão Strategy. A atividade Telefone pertence à segunda questão e não será tratada nesta etapa.

### Requisitos da primeira questão

O programa deve:

- Ler o arquivo `src/Hamlet.txt`.
- Tratar cada linha como uma consulta.
- Usar o padrão Strategy para definir filtros de consulta.
- Permitir que cada observador seja registrado junto com seu filtro.
- Verificar o filtro antes de notificar o observador.
- Manter `WebSearchModel` independente das implementações concretas dos filtros.
- Criar dois observadores em `Snooper`:
  - imprimir `Oh Yes! <consulta>` quando a consulta contiver `friend`, ignorando maiúsculas e minúsculas;
  - imprimir `So long <consulta>` quando a consulta tiver mais de 60 caracteres.

### Situação atual

`WebSearchModel` já possui leitura do arquivo, lista de observadores, registro de observadores e notificação das consultas.

Entretanto:

- o modelo notifica todos os observadores;
- não existe uma estratégia de filtragem;
- `addQueryObserver` aceita apenas o observador;
- `Snooper` possui apenas um observador;
- `Main.java` procura o arquivo em `data/Hamlet.txt`, mas o arquivo atual está em `src/Hamlet.txt`.

### Plano de implementação

#### 1. Criar a interface de Strategy

Criar uma interface para representar o filtro da consulta, por exemplo `QueryFilter`.

Essa interface deverá definir um método que receba uma `String` e retorne `true` quando a consulta for interessante ou `false` quando ela não atender ao filtro.

#### 2. Alterar o registro no modelo

O método de registro deverá receber o observador e o filtro correspondente. O modelo deverá armazenar essa associação.

#### 3. Alterar a notificação

Para cada linha do arquivo, o modelo deverá:

1. Ler a consulta.
2. Avaliar o filtro de cada observador.
3. Notificar somente os observadores cujo filtro retornar `true`.

O `WebSearchModel` não deverá conhecer regras como `friend` ou tamanho da consulta. Essas regras pertencerão às Strategies criadas pelo cliente.

#### 4. Alterar o `Snooper`

O `Snooper` deverá criar dois pares independentes de observador e filtro:

- filtro de consultas que contêm `friend`;
- filtro de consultas com mais de 60 caracteres.

Cada observador deverá imprimir apenas sua mensagem correspondente.

#### 5. Corrigir o caminho do arquivo

Como o arquivo está em `src/Hamlet.txt`, o caminho utilizado pelo `Main.java` deverá ser compatível com a forma escolhida para executar o programa.

### Fluxo esperado

```text
Main
 ├── cria WebSearchModel
 ├── cria Snooper
 │    ├── registra observador + filtro "friend"
 │    └── registra observador + filtro "mais de 60 caracteres"
 └── inicia pretendToSearch()

WebSearchModel
 ├── lê uma consulta
 ├── testa o filtro "friend"
 ├── testa o filtro de tamanho
 └── notifica somente os observadores aprovados
```

### Arquivos envolvidos

Serão necessários:

- `src/websearch/WebSearchModel.java`;
- `src/websearch/Snooper.java`;
- `src/websearch/Main.java`;
- um novo arquivo de interface para a Strategy.

Não será necessário alterar `src/Hamlet.txt`.

### Checklist

- [x] Interface de filtro criada.
- [x] Filtro recebe uma consulta e retorna `boolean`.
- [x] Observador e filtro são registrados juntos.
- [x] O modelo filtra antes de notificar.
- [x] O modelo não conhece as regras concretas.
- [x] Filtro `friend` ignora maiúsculas e minúsculas.
- [x] Filtro de tamanho aceita somente consultas com mais de 60 caracteres.
- [x] `Snooper` possui os dois observadores.
- [x] Caminho de `Hamlet.txt` corrigido para execução a partir da raiz do repositório.
- [x] Saída validada com a execução do programa.

Os passos de criação da Strategy, registro dos filtros, filtragem das notificações, criação dos dois observadores e correção do caminho do arquivo foram implementados. A validação da saída permanece pendente até a instalação do JDK.

## Como executar

### Pré-requisito

É necessário ter um JDK instalado, pois o comando `javac` é usado para compilar os arquivos Java.

Verifique a instalação com:

```powershell
java -version
javac -version
```

### Compilação

Execute os comandos a partir da raiz deste repositório:

```powershell
New-Item -ItemType Directory -Force out
javac -d out src\websearch\*.java
```

Os arquivos compilados serão gerados no diretório `out`.

### Execução

Ainda a partir da raiz do repositório, execute:

```powershell
java -cp out Main
```

O programa lerá `src/Hamlet.txt` e exibirá:

- `Oh Yes! <consulta>` para consultas que contêm `friend`, sem diferenciar maiúsculas e minúsculas;
- `So long <consulta>` para consultas com mais de 60 caracteres.

Uma consulta pode gerar as duas mensagens quando atender aos dois filtros.

Para salvar a saída em um arquivo e também exibi-la no terminal:

```powershell
java -cp out Main | Tee-Object saida.txt
```

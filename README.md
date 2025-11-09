🚀 TaskFlow: Sistema de Gerenciamento de Tarefas Ágil

Este repositório contém o projeto prático de Engenharia de Software, simulando o desenvolvimento de um sistema de gerenciamento de tarefas (CRUD) para a TechFlow Solutions, cliente do setor de logística. O projeto foca na aplicação rigorosa de metodologias ágeis (Kanban) e controle de qualidade através de Integração Contínua (CI).

Tecnologia Principal: Java 17 com Maven e JUnit 5.

🎯 Escopo e Funcionalidades Essenciais

O objetivo principal do TaskFlow é fornecer uma ferramenta para acompanhar o fluxo de trabalho e priorizar demandas.

Requisitos Implementados (CRUD)

O sistema implementa a lógica de negócio para gerenciar tarefas:

Criação (Create): Adicionar novas tarefas com título, descrição e prioridade.

Leitura (Read): Listar todas as tarefas ativas, ordenadas por prioridade.

Atualização (Update): Alterar o status da tarefa (TO_DO, IN_PROGRESS, DONE).

Exclusão (Delete): Remover tarefas pelo ID.

🛣️ Metodologia Ágil Adotada: Kanban

Adotamos a metodologia Kanban para gerenciar o fluxo de trabalho. O uso do GitHub Projects permite visualizar o estado de cada funcionalidade em tempo real, garantindo o foco na entrega contínua.

Quadro Kanban (GitHub Projects)

O quadro está organizado nas seguintes colunas:

Coluna

Descrição

A Fazer (To Do)

Ideias e requisitos priorizados para desenvolvimento futuro.

Em Progresso (In Progress)

Tarefas ativas sendo codificadas e testadas pelo desenvolvedor.

Concluído (Done)

Funcionalidades implementadas, testadas e prontas para deploy.

Acompanhe o quadro aqui (substitua o link).

🛠️ Controle de Qualidade e CI/CD

Para garantir a qualidade e a estabilidade do código, utilizamos GitHub Actions para Integração Contínua (CI).

Testes Automatizados (JUnit 5)

Os testes unitários cobrem a lógica de negócio na classe TaskService, verificando:

Criação e integridade dos dados da tarefa.

Ordenação correta das tarefas por prioridade.

Validações de regras de negócio (ex: títulos vazios e prioridades inválidas).

Pipeline de CI

O workflow está configurado no arquivo .github/workflows/ci.yml e é acionado a cada push ou pull_request na branch main. Se qualquer teste unitário falhar, o build falha, bloqueando a integração de código defeituoso.

🔄 Gestão de Mudança de Escopo

Durante o desenvolvimento ágil, o cliente de logística identificou a necessidade crítica de monitorar prazos para melhor coordenação.

Mudança de Escopo: Inclusão do campo deliveryDate (Data de Entrega) na entidade Task.

Justificativa: A priorização baseada apenas no nível de prioridade (1-5) não é suficiente para tarefas logísticas com prazos rígidos. A inclusão da data de entrega permite que a equipe:

Visualize e ordene tarefas baseadas em deadlines.

Mensure o risco de atraso de forma mais eficaz.

Essa funcionalidade foi implementada para demonstrar a adaptabilidade do processo ágil às necessidades emergentes do cliente.

⚙️ Como Executar o Projeto Localmente

Para compilar e rodar a demonstração do sistema:

Pré-requisitos

Java Development Kit (JDK) 17 ou superior.

Apache Maven.

Passos

Clone o repositório:

git clone [https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git](https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git)
cd SEU_REPOSITORIO



Compile o projeto e rode os testes:

mvn clean install



Este comando compila o código e executa todos os testes unitários.

Execute a aplicação de demonstração:

mvn exec:java -Dexec.mainClass="com.techflow.main.Application"



Isso rodará a classe principal, demonstrando as operações CRUD.

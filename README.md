# Desafio Iniflex - Gestão de Funcionários 🚀

Este projeto é uma solução Full Stack desenvolvida para o desafio técnico da Projedata. A aplicação consiste em um sistema de gerenciamento de funcionários, focado em manipulação de dados, cálculos financeiros e organização de coleções seguindo requisitos de negócio rigorosos.

## 🛠️ Tecnologias Utilizadas

- **Java 21 (LTS):** Utilização da versão mais atual para aproveitar recursos modernos de performance.
- **Spring Boot 3.2+:** Base para a API REST e injeção de dependências.
- **Project Loom (Virtual Threads):** Habilitado para permitir alta escalabilidade com baixo consumo de memória.
- **PostgreSQL:** Banco de dados relacional robusto para persistência dos dados.
- **Docker & Docker Compose:** Containerização de todo o ecossistema (Back, Front e Banco).
- **JUnit 5 & Mockito:** Suíte de testes automatizados para garantir a integridade da lógica.
- **Nginx:** Servidor leve para o Frontend estático.

---

## 🏗️ Como Rodar a Aplicação

A aplicação foi totalmente containerizada para garantir que rode em qualquer ambiente sem necessidade de instalar Java ou Postgres localmente.

1. Navegue até a pasta raiz `Projedata`.
2. Execute o comando:
   bash
   docker-compose up --build
   
3. Nota Importante: O banco de dados PostgreSQL leva alguns segundos para inicializar os serviços internos. O backend possui um healthcheck para aguardar o banco,
   mas se o frontend carregar antes dos dados serem processados, basta atualizar a página (F5).
   
   Frontend: http://localhost:8081
   
   Backend (API): http://localhost:8080

🧠 Explicação dos Métodos (Lógica de Negócio)
Abaixo, os métodos implementados no FuncionarioService, que cobrem todos os requisitos do teste:

1. Inserção e Remoção
salvarTodos: Realiza a persistência em lote da lista inicial de funcionários.

removerPorNome: Localiza e remove um funcionário específico (ex: "João") da base de dados.

2. Formatação e Exibição (Requisito 3.3 e 3.4)
listar: Retorna os funcionários. Se o parâmetro ordenado for verdadeiro, utiliza Sort.by("nome") para entrega alfabética. A formatação de datas (dd/MM/yyyy) e números (padrão brasileiro) é tratada na camada de saída/Doria.

3. Alterações Salariais (Requisito 3.7)
atualizarSalarios: Aplica um aumento de 10% sobre o salário atual de todos os funcionários utilizando BigDecimal para garantir precisão decimal (essencial em sistemas financeiros).

4. Agrupamento e Filtros (Requisito 3.5, 3.6 e 3.8)
agruparPorFuncao: Utiliza Collectors.groupingBy para criar um Mapa onde a chave é a Profissão e o valor é a lista de funcionários daquela área.

aniversariantesMes: Filtra funcionários que fazem aniversário nos meses selecionados (ex: 10 e 12) através da extração do MonthValue da data de nascimento.

5. Cálculos e Estatísticas (Requisito 3.9, 3.11 e 3.12)
buscarMaisVelho: Compara as datas de nascimento para encontrar a menor data (a mais antiga), identificando o funcionário com maior idade.

totalSalarios: Soma todos os salários do banco utilizando .reduce(BigDecimal.ZERO, BigDecimal::add).

imprimirSalariosMinimos: Calcula quantos salários mínimos (R$ 1.212,00) cada funcionário recebe individualmente.
````
🧪 Qualidade e Testes
Foram implementados testes unitários utilizando Mockito para simular o repositório. Cada aspecto do teste foi coberto:

Validação de cálculos.

Verificação de fluxos de deleção e salvamento.

Testes de agrupamento e filtros de data.

💡 Diferenciais Implementados
Virtual Threads: A aplicação está preparada para o futuro do Java, processando requisições com threads leves, ideal para serviços que escalam em nuvem.

Resiliência no Docker: Configuração de rede e dependência entre containers para garantir que o sistema suba de forma organizada.

Clean Code: Métodos curtos, nomes legíveis e responsabilidade única.
````
   <img width="1920" height="1080" alt="p3" src="https://github.com/user-attachments/assets/4d1681a3-dfac-4408-bed7-afc7e5e6476d" />
   <img width="1920" height="1080" alt="p2" src="https://github.com/user-attachments/assets/c497af48-8f20-4be7-aa80-c78379fd5272" />
   <img width="1920" height="1080" alt="p1" src="https://github.com/user-attachments/assets/d8a92e0b-c988-482b-9eab-43215601065e" />

# Dell'asse

O **Dell'Asse** é um software criado para facilitar o processo de agendamento e organização de eventos. A aplicação permite que clientes encontrem, selecionem e contratem diferentes tipos de festas em um único lugar, centralizando as informações de eventos, fornecedores e datas disponíveis de forma simples e organizada.

---

## Objetivo do Projeto

Nosso objetivo é:

- Centralizar o fluxo de pesquisa, seleção e contratação de eventos e serviços;
- Facilitar o agendamento de datas e gerenciamento de disponibilidade;
- Organizar informações de clientes, espaços e pacotes de eventos;
- Reduzir retrabalho e falhas de comunicação entre cliente e organizador.

---

## Requisitos Funcionais

- **RF01 – Edição da escala de avaliação**  
  O sistema deve permitir configurar e editar a escala de avaliação (por exemplo, notas de 1 a 5), podendo ajustar os valores conforme a média ou necessidade do negócio.

- **RF02 – Cadastro de aniversários e leads**  
  O sistema deve permitir cadastrar aniversários para futuros contatos (leads), registrando pelo menos: nome do aniversariante, data de aniversário e responsável. Esses cadastros poderão ser utilizados como base de clientes recorrentes.

- **RF03 – Informações da festa e responsáveis**  
  O sistema deve permitir registrar informações da festa, como tipo de evento, local/casa responsável e demais pessoas vinculadas ao evento (ex.: responsáveis adicionais ou convidados recorrentes).

- **RF04 – QR Code para página de apresentação**  
  O sistema deve gerar um QR Code que direcione para uma página de apresentação do espaço/“castelo”, contendo informações gerais do local e serviços oferecidos.

- **RF05 – Controle de convites com restrição de data**  
  O sistema deve permitir o envio/marcação de convites para convidados, porém com a restrição de que novos convites só possam ser registrados até 4 dias antes da data do evento.

- **RF06 – Definição de preço e condições de pagamento da festa**  
  O sistema deve permitir definir o valor da festa e as condições de pagamento (por exemplo, parcelamento), armazenando essas informações vinculadas ao evento.

- **RF07 – Controle de itens de decoração do evento**  
  O sistema deve disponibilizar um controle para adicionar e remover itens de decoração de cada festa, bem como registrar entradas e saídas relacionadas a esses itens.

- **RF08 – Área de acompanhamento do usuário**  
  O sistema deve oferecer uma área para acompanhar o “histórico” do usuário, permitindo visualizar: fotos adquiridas, festas realizadas ou canceladas e documentos associados (como o PDF do contrato, sujeito à aprovação do administrador).

- **RF09 – Registro de movimentações e geração de relatórios**  
  O sistema deve registrar movimentações relacionadas à festa (como valores pagos, gastos, serviços contratados e gastos fixos) e permitir a geração de relatórios com essas informações.

- **RF10 – Contrato padrão de evento**  
  O sistema deve disponibilizar um modelo de contrato padrão para as festas, que possa ser preenchido e associado a cada evento.

- **RF11 – Registro e consulta de orçamentos por cliente**  
  O sistema deve permitir salvar orçamentos por cliente, detalhados por data e tipo de evento, possibilitando consultar facilmente os orçamentos do mês e apoiar a comunicação com o cliente (inclusive via WhatsApp).

- **RF12 – Emissão de faturas padronizadas**  
  O sistema deve emitir faturas em um formato padronizado, mantendo o layout parecido entre elas para facilitar leitura e conferência.

## Requisitos Não Funcionais / Regras de Negócio

- **RNF01 – Galeria de fotos de decorações**  
  O sistema deve disponibilizar uma galeria de fotos das decorações, armazenadas em Blobs ou por meio de API de armazenamento (ex.: Drive), exibidas em formato de carrossel. Cada decoração deve possuir um título que possa ser utilizado para pesquisa (busca parcial).

- **RNF02 – Botão de interesse com integração ao WhatsApp**  
  Cada item da galeria deve possuir um botão de interesse que, ao ser clicado, redireciona o usuário para o WhatsApp, enviando automaticamente a mensagem:  
  “Olá, tenho interesse na decoração {{nome_da_decoração}} #{{código_da_decoração}}”.

- **RNF03 – Cadastro e autenticação de usuários**  
  O registro e login dos usuários devem ser armazenados em banco de dados, não sendo permitidos usuários duplicados. O cadastro deve conter obrigatoriamente: CPF, número de celular, data de nascimento, endereço, foto de perfil, login e senha. A senha deve ser armazenada criptografada em SHA-256 e seguir a política de senha forte, contendo no mínimo 8 caracteres, com ao menos uma letra maiúscula, uma letra minúscula e um caractere especial.

- **RNF04 – Avaliação de imagens**  
  Usuários autenticados (clientes logados) devem poder avaliar as imagens das decorações, sendo essas avaliações persistidas em banco de dados.

- **RNF05 – Funcionalidades disponíveis ao usuário autenticado na galeria**  
  Após autenticação, o usuário deve poder ser redirecionado para a página da galeria de fotos, visualizar a descrição da decoração, solicitar orçamento, realizar avaliações, registrar comentários e visualizar a agenda de datas disponíveis.

- **RNF06 – Gestão de itens pelo funcionário**  
  Funcionários devem poder criar novos itens (decorações) no sistema, indicar um código para o item e garantir que cada item receba um código identificador.

- **RNF07 – Administração do sistema (ADM)**  
  O administrador deve poder gerenciar o sistema, incluindo deletar itens, editar itens, atribuir cargos/perfis a usuários, inativar usuários e apagar avaliações registradas.

## Tecnologias Utilizadas

- **Linguagem:** Java  
- **Framework:** Spring Boot   
- **Banco de Dados:** PostgreSQL  
- **Ferramentas:** Maven, Eclipse

# Configuração do Ambiente

## Arquivo .env

### 1. Criar o arquivo .env

Na raiz do projeto, crie um arquivo chamado `.env`:

```bash
# Copie o arquivo de exemplo
cp .env.example .env
```

### 2. Configurar as variáveis

Edite o arquivo `.env` com suas configurações:

```env
# Configurações do Banco de Dados
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
```

### 3. Verificar a dependência

**Maven** (`pom.xml`):

```xml
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>3.0.0</version>
</dependency>
```

### 4. Usar no código

```java
import io.github.cdimascio.dotenv.Dotenv;

public class Application {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String dbUser = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        // Usar as variáveis na aplicação
    }
}
```

### ⚠️ Importante

- **Nunca commite** o arquivo `.env` no repositório
- O arquivo `.env` já está no `.gitignore`
- Use o arquivo `.env.example` como referência

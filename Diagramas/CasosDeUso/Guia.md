# Casos de Uso – Dell’Asse

## Caso 1 – Contratar uma festa (Cliente logado)

**Ator principal:** Cliente (logado)

Diagrama de Caso de Uso: [Contratar Festa](./ContratarFesta.vpp)

**Fluxo principal (caso perfeito):**
1. Usuário acessa o site.
2. Acessa a aba de **galeria**.
3. Seleciona a **temática** desejada.
4. Demonstra interesse na festa selecionada.
5. É redirecionado para o **carrinho**, na aba da festa, onde pode editar os itens da festa.
6. Envia a **solicitação**.
7. A solicitação fica pendente para o gestor, com status possível de: **aprovado**, **pendente**, **negado**, **cancelado**, etc.
8. A festa é **aprovada**.
9. O cliente é **contatado pelo WhatsApp**.
10. O status da festa é atualizado no carrinho para **Aprovado**.
11. O gestor **gera o contrato** para o cliente.
12. O cliente **assina o contrato**.
13. A **festa é realizada**.

---

## Caso 2 – Criar conta

**Ator principal:** Visitante

Diagrama de Caso de Uso: [Criar Conta](./Cadastro.vpp)

**Fluxo principal (caso perfeito):**
1. O Visitante acessa o site.
2. Clica em **“Cadastrar”** ou **“Criar conta”**.
3. Informa os dados obrigatórios (nome, e-mail, senha, telefone, etc.).
4. Confirma o cadastro.
5. O sistema valida os dados e cria o **perfil de Cliente**.
6. O sistema redireciona para a **página de login** ou já entra logado, conforme regra de negócio.

---

## Caso 3 – Cancelar solicitação de festa (Cliente)

**Ator principal:** Cliente (logado)

Diagrama de Caso de Uso: [Cancelar Festa](./CancelarFesta.vpp)

**Fluxo principal (caso perfeito):**
1. O Cliente acessa a área **“Carrinho”**.
2. Seleciona uma festa com status **“Em andamento”** (ou equivalente).
3. Clica em **“Cancelar”**.
4. O sistema solicita **confirmação** do cancelamento.
5. O Cliente confirma.
6. O sistema altera o status da festa para **Cancelado**.

---

## Caso 4 – Cadastrar festas

**Ator principal:** Gestor

Diagrama de Caso de Uso: [Cadastrar Festas](./CadastrarFesta.vpp)

**Fluxo principal (caso perfeito):**
1. O Gestor acessa o **Painel de Admin**.
2. O Gestor navega até a área **“Adicionar Festa”**.
3. Preenche os campos obrigatórios: **título**, **orçamento base**, **descrição**, **URL da imagem**.
4. O sistema valida os dados e registra a nova festa no catálogo.

---

## Caso 5 – Efetuar login

**Atores principais:** Cliente, Gestor

Diagrama de Caso de Uso: [Efetuar Login](./EfetuarLogin.vpp)

**Fluxo principal (caso perfeito):**
1. O Usuário acessa a **página inicial** do sistema e seleciona a opção **“Login / Entrar”**.
2. O sistema exibe a **tela de login** com campos de e-mail/usuário e senha.
3. O Usuário preenche as credenciais.
4. O Usuário confirma o login clicando em **“Entrar”**.
5. O sistema valida as credenciais:
   - verifica se usuário e senha são válidos;
   - identifica se o usuário é **Admin** ou **Cliente**.
6. O sistema finaliza o processo de autenticação, redirecionando para:
   - o **painel de Admin**, quando se tratar de administrador; ou  
   - a **tela inicial do cliente**, quando se tratar de cliente.
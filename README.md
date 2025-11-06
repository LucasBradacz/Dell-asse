# Dell'asse

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

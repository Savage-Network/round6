# 📋 Documentação do Comando /acc - Round 6 do Rafa

## Visão Geral

O comando `/acc` (ou `/account`, `/conta`) é um comando completo para gerenciar sua conta no servidor Round 6. Inspirado no sistema da 1.8, mas adaptado para o contexto do Round 6.

**Localização:** `auth/src/main/java/rip/rafa/event/auth/command/ChangePasswordCommand.java`

**Permissão:** Nenhuma (todos os jogadores autenticados)

**Plataforma:** Bukkit (Auth Server)

**Aliases:** `/account`, `/conta`

---

## 🎯 Funcionalidades

### 1. Ver Informações da Conta

```
/acc
/acc info
```

**Mostra:**
- Nome do usuário
- UUID
- Tipo de conta (Original/Pirata)
- Data de registro
- Último login
- Último IP
- Email (se cadastrado)

**Exemplo de saída:**

```
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

 §7▪ §fᴜꜱᴜᴀ́ʀɪᴏ: §czrwfaxd
 §7▪ §fᴜᴜɪᴅ: §c12345678-1234-1234-1234-123456789012
 §7▪ §fᴛɪᴘᴏ: §cOriginal

 §7▪ §fʀᴇɢɪꜱᴛʀᴏ: §c10/04/2026 15:30
 §7▪ §fᴜ́ʟᴛɪᴍᴏ ʟᴏɢɪɴ: §c13/04/2026 18:45
 §7▪ §fᴜ́ʟᴛɪᴍᴏ ɪᴘ: §c192.168.1.1
 §7▪ §fᴇᴍᴀɪʟ: §czrwfaxd@example.com

▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
```

---

### 2. Trocar Senha

```
/acc password <senha_atual> <nova_senha>
/acc senha <senha_atual> <nova_senha>
/acc changepassword <senha_atual> <nova_senha>
```

**Validações:**
- ✅ Jogador deve estar autenticado
- ✅ Senha atual deve estar correta
- ✅ Nova senha deve ter entre 4 e 32 caracteres
- ✅ Nova senha não pode ser igual à antiga
- ✅ Senha é criptografada com SHA-256

**Exemplos:**

```bash
# Trocar senha
/acc password minhasenha novasenha123

# Usando alias
/acc senha minhasenha novasenha123
```

**Mensagens:**

✅ **Sucesso:**
```
[Auth] ꜱᴇɴʜᴀ ᴀʟᴛᴇʀᴀᴅᴀ ᴄᴏᴍ ꜱᴜᴄᴇꜱꜱᴏ!
§7ᴜꜱᴇ ꜱᴜᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ɴᴏ ᴘʀᴏ́xɪᴍᴏ ʟᴏɢɪɴ.
```

❌ **Erros:**
```
[Auth] ꜱᴇɴʜᴀ ᴀᴛᴜᴀʟ ɪɴᴄᴏʀʀᴇᴛᴀ!
[Auth] ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍɪ́ɴɪᴍᴏ 4 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!
[Auth] ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ᴅᴇᴠᴇ ᴛᴇʀ ɴᴏ ᴍᴀ́xɪᴍᴏ 32 ᴄᴀʀᴀᴄᴛᴇʀᴇꜱ!
[Auth] ᴀ ɴᴏᴠᴀ ꜱᴇɴʜᴀ ɴᴀ̃ᴏ ᴘᴏᴅᴇ ꜱᴇʀ ɪɢᴜᴀʟ ᴀ̀ ᴀɴᴛɪɢᴀ!
```

---

### 3. Gerenciar Email

#### Ver Email Atual

```
/acc email
```

**Mostra:**
- Email cadastrado (se houver)
- Mensagem para cadastrar (se não houver)

#### Definir Email

```
/acc email set <email>
```

**Validações:**
- ✅ Formato de email válido
- ✅ Deve conter @ e domínio válido

**Exemplos:**

```bash
# Definir email
/acc email set zrwfaxd@example.com

# Ver email atual
/acc email
```

**Mensagens:**

✅ **Sucesso:**
```
[Auth] ᴇᴍᴀɪʟ ᴅᴇꜰɪɴɪᴅᴏ ᴄᴏᴍ ꜱᴜᴄᴇꜱꜱᴏ!
```

❌ **Erros:**
```
[Auth] ᴇᴍᴀɪʟ ɪɴᴠᴀ́ʟɪᴅᴏ!
[Auth] ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴘᴏꜱꜱᴜɪ ᴇᴍᴀɪʟ ᴄᴀᴅᴀꜱᴛʀᴀᴅᴏ.
```

---

### 4. Ajuda

```
/acc help
/acc ?
```

**Mostra:**
- Lista de todos os subcomandos
- Descrição de cada comando
- Exemplos de uso

**Exemplo de saída:**

```
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                    ᴄᴏᴍᴀɴᴅᴏꜱ ᴅᴇ ᴄᴏɴᴛᴀ
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

 §7▪ §c/acc §7- §fᴠᴇʀ ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ꜱᴜᴀ ᴄᴏɴᴛᴀ
 §7▪ §c/acc password <atual> <nova> §7- §fᴛʀᴏᴄᴀʀ ꜱᴇɴʜᴀ
 §7▪ §c/acc email §7- §fᴠᴇʀ ꜱᴇᴜ ᴇᴍᴀɪʟ
 §7▪ §c/acc email set <email> §7- §fᴅᴇꜰɪɴɪʀ ᴇᴍᴀɪʟ

▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
```

---

## 🔒 Segurança

### 1. Autenticação Obrigatória
```java
if (!plugin.getAuthManager().isAuthenticated(player)) {
    player.sendMessage("ᴠᴏᴄᴇ̂ ᴘʀᴇᴄɪꜱᴀ ᴇꜱᴛᴀʀ ʟᴏɢᴀᴅᴏ!");
    return;
}
```

Jogador deve estar logado para usar qualquer subcomando.

### 2. Criptografia SHA-256
```java
private String hashPassword(String password) {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(password.getBytes("UTF-8"));
    // Converte para hexadecimal
    return hexString.toString();
}
```

Todas as senhas são criptografadas antes de serem salvas.

### 3. Validação de Senha
- Mínimo: 4 caracteres
- Máximo: 32 caracteres
- Não pode ser igual à senha antiga
- Senha atual deve estar correta

### 4. Validação de Email
```java
private boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    return email.matches(emailRegex);
}
```

Valida formato de email antes de salvar.

### 5. Logs de Segurança
```java
plugin.getLogger().info("[SECURITY] Jogador " + player.getName() + " alterou sua senha.");
plugin.getLogger().info("[SECURITY] Jogador " + player.getName() + " definiu email: " + email);
```

Todas as ações importantes são registradas.

---

## 🗄️ Banco de Dados

### Tabela: round6_users

```sql
CREATE TABLE round6_users (
    uuid VARCHAR(36) PRIMARY KEY,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    last_ip VARCHAR(45),
    INDEX idx_username (username)
);
```

### Métodos do DatabaseManager

#### checkPassword()
```java
public boolean checkPassword(UUID uuid, String password)
```
Verifica se a senha fornecida está correta (compara hashes).

#### updatePassword()
```java
public boolean updatePassword(UUID uuid, String newPassword)
```
Atualiza a senha do jogador (salva hash SHA-256).

#### getEmail()
```java
public String getEmail(UUID uuid)
```
Retorna o email cadastrado do jogador.

#### updateEmail()
```java
public boolean updateEmail(UUID uuid, String email)
```
Atualiza o email do jogador.

#### getAccountInfo()
```java
public String[] getAccountInfo(UUID uuid)
```
Retorna array com: [username, registeredAt, lastLogin, lastIp, email]

---

## 🎨 Interface Visual

### Cores Hex Customizadas

O comando usa cores hex para uma aparência profissional:

```java
&#FF1744 - Vermelho (destaque)
&#FFFFFF - Branco (texto principal)
§7 - Cinza (texto secundário)
```

### Formatação

- Linhas divisórias com ▬
- Bullets com §7▪
- Títulos centralizados
- Espaçamento adequado

---

## 📊 Fluxo de Execução

### 1. Validação Inicial
```java
if (!(sender instanceof Player)) {
    sender.sendMessage("§cApenas jogadores podem usar este comando!");
    return true;
}
```

### 2. Verificação de Autenticação
```java
if (!plugin.getAuthManager().isAuthenticated(player)) {
    player.sendMessage("ᴠᴏᴄᴇ̂ ᴘʀᴇᴄɪꜱᴀ ᴇꜱᴛᴀʀ ʟᴏɢᴀᴅᴏ!");
    return true;
}
```

### 3. Roteamento de Subcomando
```java
switch (subCommand) {
    case "changepassword":
    case "password":
    case "senha":
        handlePasswordChange(player, args);
        break;
    case "info":
        showAccountInfo(player);
        break;
    case "email":
        handleEmail(player, args);
        break;
    default:
        sendHelp(player);
        break;
}
```

---

## 🔧 Exemplos de Uso Completo

### Cenário 1: Jogador Novo

```bash
# Registrar
/register senha123 senha123

# Fazer login
/login senha123

# Ver informações da conta
/acc

# Definir email
/acc email set meu@email.com

# Trocar senha
/acc password senha123 novasenha456
```

### Cenário 2: Recuperação de Conta

```bash
# Login
/login minhasenha

# Ver informações
/acc

# Verificar email cadastrado
/acc email

# Trocar senha por segurança
/acc password minhasenha senhasegura789
```

### Cenário 3: Atualização de Dados

```bash
# Ver dados atuais
/acc info

# Atualizar email
/acc email set novoemail@example.com

# Confirmar mudança
/acc
```

---

## 🆚 Diferenças do Sistema 1.8

### Removido (não aplicável ao Round 6):
- ❌ Gerenciamento de ranks
- ❌ Sistema de tags
- ❌ Sistema de medalhas
- ❌ Sistema de permissões
- ❌ Sistema de ouros (moeda)
- ❌ Sistema de clantags
- ❌ Histórico de punições
- ❌ Localização geográfica

### Mantido (essencial):
- ✅ Ver informações da conta
- ✅ Trocar senha
- ✅ Gerenciar email
- ✅ Sistema de segurança
- ✅ Logs de auditoria
- ✅ Validações robustas

### Adicionado (específico do Round 6):
- ✅ Detecção de tipo de conta (Original/Pirata)
- ✅ Interface visual com cores hex
- ✅ Mensagens em português estilizado
- ✅ Integração com sistema de autenticação

---

## 🐛 Troubleshooting

### Comando não funciona
- ✅ Verifique se está no servidor Auth
- ✅ Verifique se está autenticado (/login)
- ✅ Verifique se o plugin Auth está carregado

### Não consigo trocar senha
- ✅ Senha atual deve estar correta
- ✅ Nova senha deve ter 4-32 caracteres
- ✅ Nova senha não pode ser igual à antiga

### Email não é aceito
- ✅ Deve ter formato válido (usuario@dominio.com)
- ✅ Deve conter @ e domínio válido
- ✅ Não pode ter espaços

### Informações não aparecem
- ✅ Banco de dados deve estar conectado
- ✅ Jogador deve ter feito login pelo menos uma vez
- ✅ Verifique logs do servidor para erros SQL

---

## 📝 Notas Técnicas

1. **Compatibilidade:** Bukkit 1.8.8+
2. **Dependências:** MySQL via HikariCP
3. **Thread-Safe:** Operações de banco são síncronas
4. **Encoding:** UTF-8 para suportar caracteres especiais
5. **Performance:** Queries otimizadas com prepared statements

---

## 🎯 Roadmap Futuro

### Versão 2.0 (Planejado)
- [ ] Recuperação de senha via email
- [ ] Autenticação de dois fatores (2FA)
- [ ] Histórico de logins
- [ ] Sessões ativas
- [ ] Bloqueio de conta após tentativas falhas

### Versão 3.0 (Futuro)
- [ ] Integração com Discord
- [ ] Notificações de login suspeito
- [ ] Backup de conta
- [ ] Migração de conta
- [ ] API para desenvolvedores

---

**Desenvolvido por: Rafa**
**Baseado em: Sistema da 1.8**
**Adaptado para: Round 6 do Rafa**
**Versão: 1.0.0**
**Data: 13/04/2026**

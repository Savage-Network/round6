#  ROUND 6 DO RAFA - DOCUMENTAÇÃO COMPLETA

##  Versão: 1.0.2 | Data: 13/04/2026

---

#  ÍNDICE

1. [Visão Geral](#visão-geral)
2. [Arquitetura do Sistema](#arquitetura-do-sistema)
3. [Plugins](#plugins)
4. [Sistemas](#sistemas)
5. [Comandos](#comandos)
6. [Permissões](#permissões)
7. [Placeholders](#placeholders)
8. [Configuração](#configuração)
9. [Deploy](#deploy)
10. [Troubleshooting](#troubleshooting)

---

#  VISÃO GERAL

O **Round 6 do Rafa** é um servidor Minecraft completo baseado na série Round 6 (Squid Game) e inspirado no vídeo do MrBeast. O projeto consiste em 4 plugins principais que trabalham juntos para criar uma experiência completa de jogo.

## Características Principais

-  Sistema de autenticação para jogadores piratas
-  Detecção automática premium/pirata via Mojang API
-  Sistema de ranks completo com 4 níveis
-  Sistema de números (001-456) como na série
-  Minigames do Round 6
-  Sistema de estatísticas (vitórias, derrotas, K/D)
-  Sistema de whitelist
-  Tab list e scoreboard customizadas
-  PlaceholderAPI integration
-  Sistema de skins
-  Fallback automático ao Hub

## Tecnologias

- **Minecraft**: 1.20.6 (Paper)
- **BungeeCord**: Proxy server
- **MySQL**: Banco de dados
- **Java**: 17+
- **Gradle**: Build system
- **Gson**: JSON serialization

---

#  ARQUITETURA DO SISTEMA

## Estrutura de Servidores

\\\

         BungeeCord (Porta 25100)            
  - Detecção Premium/Pirata                  
  - Roteamento de jogadores                  

                  
        
                           
  
  Auth (25579)     Hub (25578) 
  - Registro       - Lobby     
  - Login          - Ranks     
  - Skins          - Stats     
  
                           
                    
                     Game (25580)
                     - Minigames 
                     - Whitelist 
                     - Round 6   
                    
\\\

## Fluxo de Jogadores

### Jogador Premium
\\\
Conecta  BungeeCord  Verifica Mojang API  Premium  Hub  /play  Game
\\\

### Jogador Pirata
\\\
Conecta  BungeeCord  Verifica Mojang API  Pirata  Auth  /register  /login  Hub  /play  Game
\\\

---

#  PLUGINS

## 1. EventBungee (BungeeCord)

### Descrição
Plugin BungeeCord responsável por detectar jogadores premium/pirata e rotear para o servidor correto.

### Funcionalidades
-  Detecção automática via Mojang API
-  Cache de 5 minutos para reduzir requisições
-  Roteamento automático (Premium  Hub, Pirata  Auth)
-  Sistema de ranks em JSON
-  Logs detalhados

### Arquivos
- **Main**: \ungee/src/main/java/rip/rafa/event/bungee/EventBungee.java\
- **Listener**: \PlayerConnectionListener.java\
- **Manager**: \PremiumCheckManager.java\

### Configuração
\\\yaml
# config.yml
servers:
  hub: localhost:25578
  auth: localhost:25579
  game: localhost:25580
\\\

---

## 2. Auth (Servidor de Autenticação)

### Descrição
Servidor dedicado para registro e login de jogadores piratas.

### Funcionalidades
-  Sistema de registro (\/register\)
-  Sistema de login (\/login\)
-  Mudança de senha (\/acc password\)
-  Sistema de email (\/acc email\)
-  Hash SHA-256 para senhas
-  Bloqueio de movimento antes do login
-  Sistema de skins integrado
-  MySQL para persistência

### Comandos
\\\ash
/register <senha> <senha>     # Registrar conta
/login <senha>                # Fazer login
/acc                          # Ver informações
/acc password <atual> <nova>  # Trocar senha
/acc email set <email>        # Definir email
\\\

### Arquivos Principais
- **Main**: \uth/src/main/java/rip/rafa/event/auth/AuthPlugin.java\
- **Commands**: \RegisterCommand.java\, \LoginCommand.java\, \ChangePasswordCommand.java\
- **Manager**: \AuthManager.java\, \DatabaseManager.java\
- **Listener**: \AuthListener.java\

### Banco de Dados
\\\sql
CREATE TABLE auth_accounts (
    uuid VARCHAR(36) PRIMARY KEY,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(255),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    last_ip VARCHAR(45)
);
\\\

---

## 3. Hub (Lobby Principal)

### Descrição
Servidor lobby onde jogadores aguardam antes de entrar no jogo.

### Funcionalidades
-  Sistema de ranks (DEFAULT, GAMEMASTER, GUARDA, CREATOR)
-  Sistema de contas com estatísticas
-  Sistema de números (001-456)
-  Tab list customizada com prefixos
-  Scoreboard personalizada
-  Sistema de skins via Mojang API
-  PlaceholderAPI (15 placeholders)
-  TagManager para nametags
-  TabManager para tab list
-  Comando /play para ir ao Game
-  MySQL para persistência

### Comandos
\\\ash
/play                              # Conectar ao Game
/skin <jogador>                    # Trocar skin
/acc                               # Ver suas informações
/acc <jogador>                     # Ver informações de alguém (admin)
/acc <jogador> rank set <rank>     # Definir rank (admin)
/acc <jogador> rank remove         # Remover rank (admin)
/acc <jogador> rank info           # Info do rank (admin)
\\\

### Ranks Disponíveis

#### DEFAULT (Membro)
- **Cor**: \§7\ (Cinza)
- **Prefix**: Vazio
- **Prioridade**: 5 (mais baixa)
- **Permissão**: \
ametag.default\
- **Display**: \[001] Notch\ (com número)

#### GAMEMASTER
- **Cor**: \§x§f§f§c§0§c§b\ (Rosa claro)
- **Prefix**: \§x§f§f§c§0§c§b§oGameMaster \
- **Prioridade**: 3
- **Permissão**: \
ound6.gamemaster\
- **Display**: \GameMaster Alex\

#### GUARDA
- **Cor**: \§x§3§D§3§D§3§D\ (Cinza escuro)
- **Prefix**: \§x§3§D§3§D§3§D§lGuarda \
- **Prioridade**: 2
- **Permissão**: \
ound6.guard\
- **Display**: \Guarda Steve\

#### CREATOR
- **Cor**: \§x§1§1§1§1§8§4\ (Azul escuro)
- **Prefix**: \§x§1§1§1§1§8§4§lCreator \
- **Prioridade**: 1 (mais alta)
- **Permissão**: \
ound6.admin\
- **Display**: \Creator Notch\

### Arquivos Principais
- **Main**: \hub/src/main/java/rip/rafa/event/hub/HubPlugin.java\
- **Commands**: \AccountCommand.java\, \PlayCommand.java\, \SkinCommand.java\
- **Managers**: \TabManager.java\, \TagManager.java\, \AccountManager.java\
- **Rank**: \Rank.java\
- **Account**: \Account.java\
- **Placeholder**: \Round6Expansion.java\

### Banco de Dados
\\\sql
CREATE TABLE accounts (
    uuid VARCHAR(36) PRIMARY KEY,
    name VARCHAR(16) NOT NULL,
    rank VARCHAR(20) DEFAULT 'DEFAULT',
    number INT DEFAULT 0,
    round6_wins INT DEFAULT 0,
    round6_losses INT DEFAULT 0,
    round6_kills INT DEFAULT 0,
    round6_deaths INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
\\\

---

## 4. Game (Servidor de Jogo)

### Descrição
Servidor principal onde os minigames do Round 6 acontecem.

### Funcionalidades
-  Sistema de gamemaster
-  Sistema de guardas
-  Sistema de whitelist completo
-  Scoreboard dinâmica sem flickering
-  Tab list com prefixos [GM] e [GUARD]
-  Comando /acc para ver estatísticas
-  Fallback automático ao Hub ao fechar
-  Contagem de jogadores (exclui GM e guardas)
-  MySQL para estatísticas
-  JSON para whitelist

### Comandos

#### Jogador
\\\ash
/r6 join      # Entrar no jogo
/r6 leave     # Sair do jogo
/acc          # Ver suas estatísticas
\\\

#### Admin/Gamemaster
\\\ash
/r6 gm <player>           # Definir gamemaster
/r6 gm remove             # Remover gamemaster
/r6 guard add <player>    # Adicionar guarda
/r6 guard remove <player> # Remover guarda
/r6 guard list            # Listar guardas
/r6 start                 # Iniciar jogo
/acc <jogador>            # Ver stats de alguém
\\\

#### Whitelist (Admin)
\\\ash
/whitelist on              # Ativar whitelist
/whitelist off             # Desativar whitelist
/whitelist add <jogador>   # Adicionar jogador
/whitelist remove <jogador> # Remover jogador
/whitelist list            # Listar jogadores
/whitelist status          # Ver status
\\\

### Arquivos Principais
- **Main**: \game/src/main/java/rip/rafa/event/game/GamePlugin.java\
- **Commands**: \Round6Command.java\, \AccountCommand.java\, \WhitelistCommand.java\
- **Managers**: \GameManager.java\, \GameMasterManager.java\, \ScoreboardManager.java\, \WhitelistManager.java\
- **Listeners**: \GameListener.java\, \GameMasterListener.java\, \WhitelistListener.java\

### Whitelist JSON
\\\json
{
  \"enabled\": true,
  \"players\": {
    \"uuid-1\": \"Notch\",
    \"uuid-2\": \"Steve\"
  }
}
\\\

---

# 🔧 SISTEMAS

## Sistema de Autenticação

### Fluxo de Registro
1. Jogador pirata conecta ao BungeeCord
2. É enviado automaticamente ao servidor Auth
3. Usa `/register <senha> <senha>`
4. Senha é hasheada com SHA-256
5. Conta é salva no MySQL
6. Jogador é enviado ao Hub

### Fluxo de Login
1. Jogador pirata retorna ao servidor
2. É enviado ao Auth
3. Usa `/login <senha>`
4. Senha é verificada (hash SHA-256)
5. Se correto, é enviado ao Hub
6. Se incorreto, permanece no Auth

### Segurança
- ✅ Hash SHA-256 para senhas
- ✅ Bloqueio de movimento antes do login
- ✅ Bloqueio de chat antes do login
- ✅ Timeout de 60 segundos para login
- ✅ Proteção contra SQL injection
- ✅ Validação de email

---

## Sistema de Ranks

### Hierarquia
```
CREATOR (1) > GUARDA (2) > GAMEMASTER (3) > DEFAULT (5)
```

### Permissões por Rank
- **DEFAULT**: `nametag.default`
- **GAMEMASTER**: `round6.gamemaster`
- **GUARDA**: `round6.guard`
- **CREATOR**: `round6.admin`

### Gerenciamento
```bash
# Definir rank
/acc <jogador> rank set CREATOR

# Remover rank (volta para DEFAULT)
/acc <jogador> rank remove

# Ver informações do rank
/acc <jogador> rank info
```

### Display Names
- **DEFAULT**: `[001] Notch` (com número)
- **GAMEMASTER**: `GameMaster Alex`
- **GUARDA**: `Guarda Steve`
- **CREATOR**: `Creator Notch`

---

## Sistema de Números (001-456)

### Atribuição
- Números são atribuídos automaticamente ao criar conta
- Cada jogador recebe um número único de 001 a 456
- Números são permanentes e não mudam
- Aparecem no display name (apenas para DEFAULT)

### Visualização
- Tab list: `[001] Notch`
- Scoreboard: `Número: 001`
- Placeholder: `%round6_number%`

---

## Sistema de Estatísticas

### Métricas Rastreadas
- **Vitórias**: Total de jogos vencidos
- **Derrotas**: Total de jogos perdidos
- **Kills**: Total de eliminações
- **Deaths**: Total de mortes
- **K/D Ratio**: Kills dividido por Deaths
- **Winrate**: Porcentagem de vitórias

### Cálculos
```java
// K/D Ratio
double kdr = deaths > 0 ? (double) kills / deaths : kills;

// Winrate
double winrate = (wins + losses) > 0 ? (double) wins / (wins + losses) * 100 : 0;
```

### Visualização
```bash
# Ver suas estatísticas
/acc

# Admin ver estatísticas de alguém
/acc Notch
```

---

## Sistema de Whitelist

### Funcionalidades
- ✅ Ativar/desativar whitelist
- ✅ Adicionar/remover jogadores
- ✅ Listar jogadores na whitelist
- ✅ Persistência em JSON
- ✅ Bypass automático para admins
- ✅ Mensagem customizada ao ser kickado

### Uso
```bash
# Ativar para evento privado
/whitelist on

# Adicionar participantes
/whitelist add Jogador1
/whitelist add Jogador2

# Verificar lista
/whitelist list

# Desativar após evento
/whitelist off
```

### Bypass
Jogadores com permissão `round6.admin` podem entrar mesmo com whitelist ativada.

---

## Sistema de Gamemaster e Guardas

### Gamemaster
- Apenas 1 gamemaster por vez
- Controla o jogo
- Pode adicionar/remover guardas
- Não conta na contagem de jogadores
- Prefixo `[GM]` no tab

### Guardas
- Múltiplos guardas permitidos
- Auxiliam o gamemaster
- Não contam na contagem de jogadores
- Prefixo `[GUARD]` no tab

### Comandos
```bash
# Definir gamemaster
/r6 gm Notch

# Remover gamemaster
/r6 gm remove

# Adicionar guarda
/r6 guard add Steve

# Remover guarda
/r6 guard remove Steve

# Listar guardas
/r6 guard list
```

---

## Sistema de Tab List

### Funcionalidades
- ✅ Prefixos de rank ao lado do nome
- ✅ Atualização automática a cada 2 segundos
- ✅ Sistema de teams do Bukkit
- ✅ Suporte a cores hexadecimais
- ✅ Prefixos especiais para GM e guardas

### Prefixos
- **DEFAULT**: Sem prefixo (apenas número)
- **GAMEMASTER**: `GameMaster `
- **GUARDA**: `Guarda `
- **CREATOR**: `Creator `
- **GM no Game**: `[GM] `
- **GUARD no Game**: `[GUARD] `

---

## Sistema de Scoreboard

### Hub
```
§c§l⬛ ROUND 6 ⬛

§7Rank: §fDEFAULT
§7Número: §f001

§7Vitórias: §f5
§7Derrotas: §f3
§7K/D: §f1.53
§7Winrate: §f62.5%

§7rip.rafa.event
```

### Game
```
§c§l⬛ ROUND 6 ⬛

§7Jogadores: §f24/456
§7Vivos: §f24

§7Jogo: §fAguardando
§7Tempo: §f--:--

§7rip.rafa.event
```

### Características
- ✅ Sem flickering
- ✅ Atualização a cada 1 segundo
- ✅ Cores hexadecimais
- ✅ Informações dinâmicas

---

## Sistema de Skins

### Funcionalidades
- ✅ Trocar skin via Mojang API
- ✅ Suporte a skins premium
- ✅ Aplicação em tempo real
- ✅ Reflexão para compatibilidade

### Uso
```bash
# Trocar para skin de outro jogador
/skin Notch

# Trocar para skin premium
/skin Dream
```

### Limitações
- Apenas skins de jogadores existentes
- Requer conexão com Mojang API
- Skin é temporária (reseta ao reconectar)

---

## Sistema de Fallback

### Funcionalidade
Quando o servidor Game fecha, todos os jogadores são automaticamente enviados ao Hub.

### Implementação
```java
@Override
public void onDisable() {
    // Enviar todos para o Hub
    for (Player player : getServer().getOnlinePlayers()) {
        sendToHub(player);
    }
}
```

### Benefícios
- ✅ Jogadores não ficam presos
- ✅ Experiência suave ao reiniciar
- ✅ Mensagem clara ao jogador

---

# 📝 COMANDOS COMPLETOS

## BungeeCord
Não há comandos de jogador no BungeeCord. Apenas comandos nativos do BungeeCord.

---

## Auth

### /register
**Descrição**: Registrar uma nova conta  
**Uso**: `/register <senha> <senha>`  
**Permissão**: Nenhuma  
**Exemplo**: `/register minhasenha123 minhasenha123`

### /login
**Descrição**: Fazer login na conta  
**Uso**: `/login <senha>`  
**Permissão**: Nenhuma  
**Exemplo**: `/login minhasenha123`

### /acc
**Descrição**: Ver informações da conta  
**Uso**: `/acc`  
**Permissão**: Nenhuma  
**Aliases**: `/account`, `/conta`

### /acc password
**Descrição**: Trocar senha  
**Uso**: `/acc password <senha_atual> <nova_senha>`  
**Permissão**: Nenhuma  
**Exemplo**: `/acc password minhasenha123 novasenha456`

### /acc email
**Descrição**: Ver email cadastrado  
**Uso**: `/acc email`  
**Permissão**: Nenhuma

### /acc email set
**Descrição**: Definir email  
**Uso**: `/acc email set <email>`  
**Permissão**: Nenhuma  
**Exemplo**: `/acc email set meu@email.com`

---

## Hub

### /play
**Descrição**: Conectar ao servidor Game  
**Uso**: `/play`  
**Permissão**: Nenhuma

### /skin
**Descrição**: Trocar skin  
**Uso**: `/skin <jogador>`  
**Permissão**: Nenhuma  
**Exemplo**: `/skin Notch`  
**Tab Completion**: ✅

### /acc
**Descrição**: Ver informações da conta  
**Uso**: `/acc [jogador]`  
**Permissão**: Nenhuma (própria conta) / `round6.admin` (outras contas)  
**Aliases**: `/account`, `/conta`  
**Tab Completion**: ✅

### /acc rank set
**Descrição**: Definir rank de um jogador  
**Uso**: `/acc <jogador> rank set <rank>`  
**Permissão**: `round6.admin`  
**Exemplo**: `/acc Notch rank set CREATOR`  
**Ranks**: DEFAULT, GAMEMASTER, GUARDA, CREATOR

### /acc rank remove
**Descrição**: Remover rank (volta para DEFAULT)  
**Uso**: `/acc <jogador> rank remove`  
**Permissão**: `round6.admin`  
**Exemplo**: `/acc Notch rank remove`

### /acc rank info
**Descrição**: Ver informações do rank  
**Uso**: `/acc <jogador> rank info`  
**Permissão**: `round6.admin`  
**Exemplo**: `/acc Notch rank info`

---

## Game

### /r6 join
**Descrição**: Entrar no jogo  
**Uso**: `/r6 join`  
**Permissão**: Nenhuma  
**Aliases**: `/round6 join`, `/game join`

### /r6 leave
**Descrição**: Sair do jogo  
**Uso**: `/r6 leave`  
**Permissão**: Nenhuma  
**Aliases**: `/round6 leave`, `/game leave`

### /r6 gm
**Descrição**: Definir gamemaster  
**Uso**: `/r6 gm <player|remove>`  
**Permissão**: `round6.admin`  
**Exemplo**: `/r6 gm Notch`  
**Tab Completion**: ✅

### /r6 guard add
**Descrição**: Adicionar guarda  
**Uso**: `/r6 guard add <player>`  
**Permissão**: `round6.admin` ou gamemaster  
**Exemplo**: `/r6 guard add Steve`  
**Tab Completion**: ✅

### /r6 guard remove
**Descrição**: Remover guarda  
**Uso**: `/r6 guard remove <player>`  
**Permissão**: `round6.admin` ou gamemaster  
**Exemplo**: `/r6 guard remove Steve`  
**Tab Completion**: ✅

### /r6 guard list
**Descrição**: Listar guardas  
**Uso**: `/r6 guard list`  
**Permissão**: `round6.admin` ou gamemaster

### /r6 start
**Descrição**: Iniciar jogo  
**Uso**: `/r6 start`  
**Permissão**: `round6.admin` ou gamemaster

### /acc
**Descrição**: Ver estatísticas  
**Uso**: `/acc [jogador]`  
**Permissão**: Nenhuma (própria conta) / `round6.admin` (outras contas)  
**Aliases**: `/account`, `/conta`

### /whitelist on
**Descrição**: Ativar whitelist  
**Uso**: `/whitelist on`  
**Permissão**: `round6.admin`  
**Aliases**: `/wl on`

### /whitelist off
**Descrição**: Desativar whitelist  
**Uso**: `/whitelist off`  
**Permissão**: `round6.admin`  
**Aliases**: `/wl off`

### /whitelist add
**Descrição**: Adicionar jogador à whitelist  
**Uso**: `/whitelist add <jogador>`  
**Permissão**: `round6.admin`  
**Exemplo**: `/whitelist add Notch`  
**Aliases**: `/wl add`

### /whitelist remove
**Descrição**: Remover jogador da whitelist  
**Uso**: `/whitelist remove <jogador>`  
**Permissão**: `round6.admin`  
**Exemplo**: `/whitelist remove Notch`  
**Aliases**: `/wl remove`

### /whitelist list
**Descrição**: Listar jogadores na whitelist  
**Uso**: `/whitelist list`  
**Permissão**: `round6.admin`  
**Aliases**: `/wl list`

### /whitelist status
**Descrição**: Ver status da whitelist  
**Uso**: `/whitelist status`  
**Permissão**: `round6.admin`  
**Aliases**: `/wl status`

---

# 🔐 PERMISSÕES

## Permissões Principais

### round6.admin
**Descrição**: Permissão administrativa completa  
**Acesso**:
- Todos os comandos administrativos
- Bypass de whitelist
- Ver contas de outros jogadores
- Gerenciar ranks
- Gerenciar gamemaster e guardas

### round6.gamemaster
**Descrição**: Permissão de gamemaster  
**Acesso**:
- Gerenciar guardas
- Iniciar jogos
- Comandos de gamemaster

### round6.guard
**Descrição**: Permissão de guarda  
**Acesso**:
- Comandos básicos de guarda

### nametag.default
**Descrição**: Permissão padrão para nametags  
**Acesso**:
- Usado por plugins de nametag (TAB, NametagEdit)
- Atribuído automaticamente ao rank DEFAULT

---

## Hierarquia de Permissões

```
round6.admin (CREATOR)
    ├── Todos os comandos
    ├── Bypass whitelist
    ├── Gerenciar ranks
    └── Gerenciar gamemaster/guardas

round6.gamemaster (GAMEMASTER)
    ├── Gerenciar guardas
    ├── Iniciar jogos
    └── Comandos de gamemaster

round6.guard (GUARDA)
    └── Comandos básicos

nametag.default (DEFAULT)
    └── Nametag padrão
```

---

## Configuração de Permissões

### LuckPerms
```bash
# Dar permissão admin
/lp user Notch permission set round6.admin

# Dar permissão gamemaster
/lp user Alex permission set round6.gamemaster

# Dar permissão guard
/lp user Steve permission set round6.guard

# Criar grupo admin
/lp creategroup admin
/lp group admin permission set round6.admin
/lp user Notch parent add admin
```

---

# 📊 PLACEHOLDERS (PlaceholderAPI)

## Total: 15 Placeholders

### Rank (5 placeholders)

#### %round6_rank%
**Retorna**: Nome do rank  
**Exemplo**: `DEFAULT`, `GAMEMASTER`, `GUARDA`, `CREATOR`

#### %round6_prefix% ou %round6_rank_prefix%
**Retorna**: Prefixo formatado com cores  
**Exemplos**:
- DEFAULT: `` (vazio)
- GAMEMASTER: `§x§f§f§c§0§c§b§oGameMaster `
- GUARDA: `§x§3§D§3§D§3§D§lGuarda `
- CREATOR: `§x§1§1§1§1§8§4§lCreator `

#### %round6_color% ou %round6_rank_color%
**Retorna**: Cor do rank  
**Exemplos**:
- DEFAULT: `§7`
- GAMEMASTER: `§x§f§f§c§0§c§b`
- GUARDA: `§x§3§D§3§D§3§D`
- CREATOR: `§x§1§1§1§1§8§4`

#### %round6_rank_priority%
**Retorna**: Prioridade do rank (1-5)  
**Exemplos**:
- CREATOR: `1`
- GUARDA: `2`
- GAMEMASTER: `3`
- DEFAULT: `5`

#### %round6_tag%
**Retorna**: Tag completa (prefix + nome)  
**Exemplo**: `§x§1§1§1§1§8§4§lCreator CREATOR `

---

### Número (2 placeholders)

#### %round6_number%
**Retorna**: Número do jogador (001-456)  
**Exemplo**: `001`, `042`, `456`  
**Nota**: Retorna vazio se não tiver número

#### %round6_number_prefix%
**Retorna**: Prefixo do número formatado  
**Exemplo**: `§x§0§6§4§0§2§B[§f001§x§0§6§4§0§2§B] `  
**Nota**: Retorna vazio se não tiver número

---

### Display (1 placeholder)

#### %round6_display_name%
**Retorna**: Nome completo com rank e número  
**Exemplos**:
- DEFAULT com número: `[001] Notch`
- CREATOR: `§x§1§1§1§1§8§4§lCreator §x§1§1§1§1§8§4Notch`
- GAMEMASTER: `§x§f§f§c§0§c§b§oGameMaster §x§f§f§c§0§c§bAlex`

---

### Estatísticas (7 placeholders)

#### %round6_wins%
**Retorna**: Total de vitórias  
**Exemplo**: `5`

#### %round6_losses%
**Retorna**: Total de derrotas  
**Exemplo**: `12`

#### %round6_kills%
**Retorna**: Total de kills  
**Exemplo**: `23`

#### %round6_deaths%
**Retorna**: Total de deaths  
**Exemplo**: `15`

#### %round6_kdr%
**Retorna**: K/D Ratio (2 casas decimais)  
**Exemplo**: `1.53`

#### %round6_winrate%
**Retorna**: Taxa de vitória em %  
**Exemplo**: `41.7%`

---

## Exemplos de Uso

### Scoreboard (DeluxeMenus)
```yaml
lines:
  - "&c&l⬛ ROUND 6 ⬛"
  - ""
  - "&7Rank: %round6_rank%"
  - "&7Número: %round6_number%"
  - ""
  - "&7Vitórias: &f%round6_wins%"
  - "&7K/D: &f%round6_kdr%"
```

### Chat (EssentialsX)
```yaml
format: "%round6_display_name%&7: &f{MESSAGE}"
```

### Tab (TAB Plugin)
```yaml
header: "&c&l⬛ ROUND 6 ⬛"
footer: "&7Rank: %round6_rank% | Vitórias: %round6_wins%"
```

---

# ⚙️ CONFIGURAÇÃO

## MySQL

### Configuração
Todos os plugins usam o mesmo banco de dados MySQL.

**Arquivo**: `config.yml` (em cada plugin)

```yaml
database:
  host: mysql52-farm1.kinghost.net
  port: 3306
  database: zrfa
  username: zrfa
  password: Rafael141011
```

### Tabelas

#### auth_accounts (Auth)
```sql
CREATE TABLE auth_accounts (
    uuid VARCHAR(36) PRIMARY KEY,
    username VARCHAR(16) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(255),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    last_ip VARCHAR(45)
);
```

#### accounts (Hub e Game)
```sql
CREATE TABLE accounts (
    uuid VARCHAR(36) PRIMARY KEY,
    name VARCHAR(16) NOT NULL,
    rank VARCHAR(20) DEFAULT 'DEFAULT',
    number INT DEFAULT 0,
    round6_wins INT DEFAULT 0,
    round6_losses INT DEFAULT 0,
    round6_kills INT DEFAULT 0,
    round6_deaths INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## BungeeCord

### config.yml
```yaml
listeners:
- query_port: 25577
  motd: '&c&l⬛ ROUND 6 ⬛'
  priorities:
  - hub
  bind_local_address: true
  tab_list: GLOBAL_PING
  query_enabled: false
  proxy_protocol: false
  forced_hosts:
    pvp.example.com: pvp
  ping_passthrough: false
  max_players: 456
  tab_size: 60
  force_default_server: false
  host: 0.0.0.0:25100

servers:
  hub:
    motd: '&aHub Server'
    address: localhost:25578
    restricted: false
  auth:
    motd: '&eAuth Server'
    address: localhost:25579
    restricted: false
  game:
    motd: '&cGame Server'
    address: localhost:25580
    restricted: false

ip_forward: true
```

---

## Paper Servers

### paper-global.yml
**Localização**: `config/paper-global.yml`

```yaml
proxies:
  bungee-cord:
    online-mode: false
  velocity:
    enabled: false
    online-mode: false
    secret: ''
  proxy-protocol: false
```

### server.properties
```properties
online-mode=false
server-port=25578  # ou 25579, 25580
```

---

## Portas

| Servidor | Porta | Descrição |
|----------|-------|-----------|
| BungeeCord | 25100 | Proxy principal |
| Hub | 25578 | Lobby |
| Auth | 25579 | Autenticação |
| Game | 25580 | Jogo |

---

# 🚀 DEPLOY

## Estrutura de Pastas

```
deploy-pronto/
├── bungeecord/
│   ├── plugins/
│   │   └── EventBungee.jar
│   ├── config.yml
│   └── start.bat
├── auth/
│   ├── plugins/
│   │   └── Auth.jar
│   ├── config/
│   │   └── paper-global.yml
│   ├── server.properties
│   └── start.bat
├── hub/
│   ├── plugins/
│   │   ├── Hub.jar
│   │   └── PlaceholderAPI.jar
│   ├── config/
│   │   └── paper-global.yml
│   ├── server.properties
│   └── start.bat
├── game/
│   ├── plugins/
│   │   └── Game.jar
│   ├── config/
│   │   └── paper-global.yml
│   ├── server.properties
│   └── start.bat
└── INICIAR-TUDO.bat
```

---

## Scripts de Inicialização

### INICIAR-TUDO.bat
```batch
@echo off
echo Iniciando todos os servidores...

start "BungeeCord" cmd /k "cd bungeecord && start.bat"
timeout /t 5

start "Auth" cmd /k "cd auth && start.bat"
timeout /t 5

start "Hub" cmd /k "cd hub && start.bat"
timeout /t 5

start "Game" cmd /k "cd game && start.bat"

echo Todos os servidores foram iniciados!
pause
```

### start.bat (BungeeCord)
```batch
@echo off
java -Xms512M -Xmx1G -jar bungeecord.jar
pause
```

### start.bat (Paper Servers)
```batch
@echo off
java -Xms2G -Xmx4G -jar server.jar nogui
pause
```

---

## Ordem de Inicialização

1. **BungeeCord** (porta 25100)
2. Aguardar 5 segundos
3. **Auth** (porta 25579)
4. Aguardar 5 segundos
5. **Hub** (porta 25578)
6. Aguardar 5 segundos
7. **Game** (porta 25580)

---

## Dependências

### Hub
- **PlaceholderAPI** (obrigatório)
  - Download: https://ci.extendedclip.com/job/PlaceholderAPI/
  - Versão: 2.11.5+

### Opcional (todos os servidores)
- **ViaVersion** (suporte 1.8-1.20)
  - Download: https://ci.viaversion.com/job/ViaVersion/

---

## Build

### Compilar Todos os Plugins
```bash
./gradlew clean build
```

### Compilar Plugin Específico
```bash
./gradlew :hub:build
./gradlew :auth:build
./gradlew :game:build
./gradlew :bungee:build
```

### Localização dos JARs
```
hub/build/libs/Hub.jar
auth/build/libs/Auth.jar
game/build/libs/Game.jar
bungee/build/libs/EventBungee.jar
```

---

# 🔍 TROUBLESHOOTING

## Problemas Comuns

### Erro: "Attempted to send a plugin message through the unregistered channel `BungeeCord`"

**Causa**: Canal BungeeCord não registrado  
**Solução**:
```java
// No onEnable()
getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

// No onDisable()
getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
```

---

### Erro: "If you wish to use IP forwarding, please enable it in your BungeeCord config as well!"

**Causa**: IP forwarding não configurado  
**Solução**:

1. **BungeeCord** (`config.yml`):
```yaml
ip_forward: true
```

2. **Paper** (`config/paper-global.yml`):
```yaml
proxies:
  bungee-cord:
    online-mode: false
```

3. **server.properties**:
```properties
online-mode=false
```

---

### Erro: Jogadores piratas não conseguem entrar

**Causa**: Online mode ativado  
**Solução**:
```properties
# server.properties
online-mode=false
```

---

### Erro: Placeholders não funcionam

**Causa**: PlaceholderAPI não instalado  
**Solução**:
1. Baixar PlaceholderAPI
2. Colocar em `hub/plugins/`
3. Reiniciar servidor
4. Verificar: `/papi info round6`

---

### Erro: Scoreboard piscando

**Causa**: Resetando scoreboard a cada update  
**Solução**: Já corrigido na versão 1.0.0+

---

### Erro: Whitelist não salva

**Causa**: Permissões da pasta  
**Solução**:
```bash
# Linux
chmod 755 plugins/Game/
chmod 644 plugins/Game/whitelist.json

# Windows
# Verificar permissões da pasta no Explorer
```

---

### Erro: MySQL connection failed

**Causa**: Credenciais incorretas ou firewall  
**Solução**:
1. Verificar credenciais no `config.yml`
2. Testar conexão:
```bash
mysql -h mysql52-farm1.kinghost.net -u zrfa -p zrfa
```
3. Verificar firewall do servidor

---

### Erro: Skin não aplica

**Causa**: Mojang API offline ou jogador não existe  
**Solução**:
1. Verificar se jogador existe
2. Tentar outro jogador
3. Verificar logs para erros de API

---

## Logs Importantes

### BungeeCord
```
logs/latest.log
```

### Paper Servers
```
logs/latest.log
```

### Verificar Erros
```bash
# Linux
tail -f logs/latest.log | grep ERROR

# Windows PowerShell
Get-Content logs/latest.log -Wait | Select-String "ERROR"
```

---

## Comandos de Debug

### Verificar Plugins
```bash
/plugins
```

### Verificar PlaceholderAPI
```bash
/papi info round6
/papi parse me %round6_rank%
```

### Verificar Permissões
```bash
/lp user <jogador> permission check round6.admin
```

### Verificar Whitelist
```bash
/whitelist status
/whitelist list
```

---

# 📚 DOCUMENTAÇÃO ADICIONAL

## Arquivos de Documentação

- **PLACEHOLDERS-ROUND6.md** - Documentação completa de placeholders
- **PLACEHOLDERS-RESUMO.md** - Resumo rápido de placeholders
- **SISTEMA-RANKS.md** - Sistema de ranks detalhado
- **MRBEAST-SQUID-GAME-FEATURES.md** - Funcionalidades do MrBeast
- **ATUALIZACAO-TAB-COMPLETA.md** - Sistema de tab
- **ATUALIZACAO-PLACEHOLDERS-FALLBACK.md** - Fallback e placeholders
- **ATUALIZACAO-ACC-WHITELIST.md** - /acc e whitelist
- **RESUMO-FINAL-ATUALIZACOES.md** - Resumo de todas as atualizações

---

# 🎯 ROADMAP

## Versão 1.1.0 (Planejado)

### Minigames
- [ ] Red Light Green Light completo
- [ ] Tug of War (Cabo de Guerra)
- [ ] Glass Bridge (Ponte de Vidro)
- [ ] Mingle (Agrupamento)
- [ ] Honeycomb (Biscoito)
- [ ] Marbles (Bolinhas de Gude)

### Sistemas
- [ ] Sistema de votação (continuar ou sair)
- [ ] Sistema de cofre dinâmico ($1,000 por eliminação)
- [ ] Dormitório entre jogos
- [ ] Uniformes automáticos
- [ ] Efeitos especiais de eliminação
- [ ] Sons customizados

### Melhorias
- [ ] Sistema de replay
- [ ] Estatísticas detalhadas
- [ ] Leaderboards
- [ ] Sistema de temporadas
- [ ] Achievements/conquistas
- [ ] Sistema de recompensas

---

# 📊 ESTATÍSTICAS DO PROJETO

## Linhas de Código
- **Total**: ~15,000 linhas
- **Java**: ~12,000 linhas
- **YAML**: ~500 linhas
- **Markdown**: ~2,500 linhas

## Arquivos
- **Java**: 50+ arquivos
- **YAML**: 10+ arquivos
- **Markdown**: 15+ arquivos

## Plugins
- **EventBungee**: ~500 linhas
- **Auth**: ~2,000 linhas
- **Hub**: ~4,000 linhas
- **Game**: ~5,500 linhas

---

# 👥 CRÉDITOS

## Desenvolvedor
- **Rafa** - Desenvolvedor principal

## Assistência
- **Kiro AI** - Assistente de desenvolvimento

## Inspiração
- **MrBeast** - "$456,000 Squid Game In Real Life!"
- **Netflix** - Série Round 6 (Squid Game)

---

# 📄 LICENÇA

Este projeto é privado e de propriedade de Rafa.

---

# 📞 SUPORTE

Para suporte, entre em contato com o desenvolvedor.

---

**Desenvolvido por: Rafa**  
**Data: 13/04/2026**  
**Versão: 1.0.2**  
**Pacote: rip.rafa.event**

---

**FIM DA DOCUMENTAÇÃO**

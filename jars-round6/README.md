# 🎮 ROUND 6 DO RAFA - JARS COMPILADOS

## 📦 Plugins Disponíveis

### EventBungee.jar
**Versão:** 1.0.0
**Descrição:** Plugin BungeeCord com sistema de ranks
**Funcionalidades:**
- Detecção automática premium/pirata via Mojang API
- Premium → Hub | Pirata → Auth
- Sistema de ranks completo
- Comando `/rank` com hierarquia
- Armazenamento em JSON
- Cache de 5 minutos

### Auth.jar
**Versão:** 1.0.0
**Descrição:** Sistema de autenticação com MySQL
**Funcionalidades:**
- `/register <senha> <senha>` - Registrar conta
- `/login <senha>` - Fazer login
- `/acc <senha_atual> <nova_senha>` - Trocar senha
- Sistema de skins integrado
- Proteção contra movimento/chat antes do login
- Hash SHA-256 para senhas

### Hub.jar
**Versão:** 1.0.0
**Descrição:** Servidor Hub com NPCs, skins e ranks
**Funcionalidades:**
- NPC do Round 6 com skin customizada
- Sistema de skins com Mojang API
- Sistema de ranks com PlaceholderAPI
- Tags hardcoded (DEFAULT, GAMEMASTER, GUARDA, CREATOR)
- Scoreboard personalizada
- TAB list customizada
- Banco de dados MySQL para contas
- Sistema de números (001-456)

### Game.jar
**Versão:** 1.0.0
**Descrição:** Plugin principal do Round 6
**Funcionalidades:**
- Red Light Green Light (Batatinha Frita 1, 2, 3)
- Tug of War (Cabo de Guerra)
- Glass Bridge (Ponte de Vidro)
- Mingle (Agrupamento)
- Sistema de explicação antes de cada jogo
- Comando `/game <modalidade>` para selecionar jogo
- Configuração via JSON
- Sistema de números (001-456)
- Scoreboard dinâmica

---

## 🎖️ Sistema de Ranks

### Ranks Disponíveis

#### DEFAULT (Membro)
- **Cor:** `§7` (Cinza)
- **Prefix:** Sem prefix visível
- **Prioridade:** 5 (mais baixa)
- **Display:** `§x§0§6§4§0§2§B[§f001§x§0§6§4§0§2§B] §7Notch` (com número)

#### GAMEMASTER
- **Cor:** `§x§f§f§c§0§c§b` (Rosa claro)
- **Prefix:** `§x§f§f§c§0§c§b§oGameMaster`
- **Prioridade:** 3
- **Display:** `§x§f§f§c§0§c§b§oGameMaster §x§f§f§c§0§c§bAlex`

#### GUARDA
- **Cor:** `§x§3§D§3§D§3§D` (Cinza escuro)
- **Prefix:** `§x§3§D§3§D§3§D§lGuarda`
- **Prioridade:** 2
- **Display:** `§x§3§D§3§D§3§D§lGuarda §x§3§D§3§D§3§DSteve`

#### CREATOR
- **Cor:** `§x§1§1§1§1§8§4` (Azul escuro)
- **Prefix:** `§x§1§1§1§1§8§4§lCreator`
- **Prioridade:** 1 (mais alta)
- **Display:** `§x§1§1§1§1§8§4§lCreator §x§1§1§1§1§8§4Notch`

### Comandos de Rank

```bash
/rank <jogador> set <rank>    # Define rank (requer round6.admin)
/rank <jogador> remove         # Remove rank (volta para DEFAULT)
/rank <jogador> info           # Ver informações do rank
```

**Exemplos:**
```bash
/rank Notch set CREATOR
/rank Steve set GUARDA
/rank Alex set GAMEMASTER
/rank Herobrine remove
/rank Notch info
```

### PlaceholderAPI

#### Placeholders de Rank
- `%round6_rank%` - Nome do rank
- `%round6_prefix%` - Prefix do rank
- `%round6_color%` - Cor do rank
- `%round6_rank_priority%` - Prioridade (1-5)
- `%round6_tag%` - Tag formatada
- `%round6_display_name%` - Nome completo com prefix e número

#### Placeholders de Número
- `%round6_number%` - Número do jogador (001-456)
- `%round6_number_prefix%` - Prefix do número formatado

#### Placeholders de Estatísticas
- `%round6_wins%` - Vitórias no Round 6
- `%round6_losses%` - Derrotas no Round 6
- `%round6_kills%` - Kills no Round 6
- `%round6_deaths%` - Deaths no Round 6
- `%round6_kdr%` - K/D Ratio
- `%round6_winrate%` - Taxa de vitória (%)

---

## 🚀 Como Usar

### 1. Copiar para Servidores

```bash
# BungeeCord
cp EventBungee.jar deploy-pronto/bungeecord/plugins/

# Auth Server
cp Auth.jar deploy-pronto/auth/plugins/

# Hub Server
cp Hub.jar deploy-pronto/hub/plugins/

# Game Server
cp Game.jar deploy-pronto/game/plugins/
```

### 2. Instalar Dependências

#### Hub Server
```bash
# PlaceholderAPI (OBRIGATÓRIO para ranks)
wget https://ci.extendedclip.com/job/PlaceholderAPI/lastSuccessfulBuild/artifact/build/libs/PlaceholderAPI-2.11.5.jar
mv PlaceholderAPI-2.11.5.jar deploy-pronto/hub/plugins/

# ViaVersion (OPCIONAL para suporte 1.8-1.20)
wget https://ci.viaversion.com/job/ViaVersion/lastSuccessfulBuild/artifact/build/libs/ViaVersion-4.9.2.jar
mv ViaVersion-4.9.2.jar deploy-pronto/hub/plugins/
```

#### Game Server
```bash
# WorldEdit (OBRIGATÓRIO para seleção de áreas)
wget https://dev.bukkit.org/projects/worldedit/files/latest
mv worldedit-bukkit-*.jar deploy-pronto/game/plugins/
```

### 3. Configurar MySQL

Todos os plugins usam o mesmo banco de dados:
```yaml
database:
  host: mysql52-farm1.kinghost.net
  port: 3306
  database: zrfa
  username: zrfa
  password: Rafael141011
```

### 4. Configurar BungeeCord

Adicione a permissão de admin no `config.yml`:
```yaml
groups:
  admin:
    permissions:
      - round6.admin
```

### 5. Iniciar Servidores

Ordem correta:
1. BungeeCord (porta 25100)
2. Auth (porta 25579)
3. Hub (porta 25578)
4. Game (porta 25580)

### 6. Definir Ranks

```bash
# No console do BungeeCord ou in-game com permissão
/rank SeuNick set CREATOR
/rank OutroNick set GAMEMASTER
/rank MaisUm set GUARDA
```

---

## 🔧 Comandos Principais

### BungeeCord
```bash
/rank <jogador> set <rank>     # Define rank
/rank <jogador> remove          # Remove rank
/rank <jogador> info            # Ver informações
```

### Auth
```bash
/register <senha> <senha>       # Registrar
/login <senha>                  # Fazer login
/acc <senha_atual> <nova_senha> # Trocar senha
```

### Hub
```bash
/play                           # Conectar ao Game
/npc create                     # Criar NPC
/skin <nick>                    # Trocar skin
```

### Game
```bash
/game redlight                  # Red Light Green Light
/game tugofwar                  # Cabo de Guerra
/game glassbridge               # Ponte de Vidro
/game mingle                    # Agrupamento
/r6 gm <player>                 # Definir gamemaster
/r6 guard add <player>          # Adicionar guarda
```

---

## 📊 Changelog

### Versão 1.0.1 (13/04/2026 20:30)
- ✅ Documentação completa de placeholders (15 placeholders)
- ✅ Fallback automático: Game → Hub ao fechar servidor
- ✅ Sistema de NPC removido (simplificação)
- ✅ Comando /play funciona sem NPC
- ✅ Mensagem de boas-vindas atualizada

### Versão 1.0.0 (13/04/2026 20:06)
- ✅ Sistema de ranks completo
- ✅ Integração com PlaceholderAPI
- ✅ Tags hardcoded (DEFAULT, GAMEMASTER, GUARDA, CREATOR)
- ✅ Comando /rank com hierarquia
- ✅ Armazenamento em MySQL (Hub) e JSON (BungeeCord)
- ✅ Sistema de contas com estatísticas
- ✅ Display names automáticos
- ✅ Suporte a números do Round 6 (001-456)
- ✅ Sistema de autenticação completo
- ✅ Sistema de skins integrado
- ✅ NPC do Round 6 no Hub
- ✅ 4 minigames completos
- ✅ Scoreboard dinâmica
- ✅ BungeeCord com detecção premium/pirata
- ✅ TabManager com ranks no tab list
- ✅ Display name acima da cabeça com prefixo do rank
- ✅ Tab completers para todos os comandos
- ✅ Scoreboard sem flickering
- ✅ Gamemaster e guardas não contam na contagem
- ✅ Tab list com prefixos [GM] e [GUARD]

### Próxima Versão 2.0.0 (Planejado)
- 🔄 Sistema de votação (continuar ou sair)
- 🔄 Sistema de cofre dinâmico ($1,000 por eliminação)
- 🔄 Dormitório entre jogos
- 🔄 Uniformes automáticos
- 🔄 Efeitos especiais de eliminação
- 🔄 Sons customizados

---

## 📚 Documentação Adicional

- **SISTEMA-RANKS.md** - Documentação completa do sistema de ranks
- **MIGRATION_GUIDE.md** - Guia de migração de versões antigas
- **NEW_STRUCTURE.md** - Estrutura do projeto

---

## 🎯 Inspiração

Este projeto é baseado no vídeo do MrBeast:
**"$456,000 Squid Game In Real Life!"**
- 456 jogadores reais
- Prêmio de $456,000
- Sets perfeitos
- Jogos fiéis à série
- Eliminação dramática (sem morte)

---

**Desenvolvido por: Rafa**
**Data: 13/04/2026**
**Versão: 1.0.0**
**Pacote: rip.rafa.event**

# ✅ ATUALIZAÇÃO - /ACC NO GAME E WHITELIST

## 📅 Data: 13/04/2026 21:00
## 🔖 Versão: 1.0.2

---

## 🎯 Mudanças Implementadas

### 1. ✅ Comando /acc no Game Server
**Arquivo:** `game/src/main/java/rip/rafa/event/game/command/AccountCommand.java`

Agora os jogadores podem ver suas estatísticas diretamente no servidor Game sem precisar voltar ao Hub.

**Funcionalidades:**
- `/acc` - Ver suas próprias informações
- `/acc <jogador>` - Ver informações de outro jogador (requer permissão `round6.admin`)

**Informações Exibidas:**
- Nome do jogador
- UUID
- Rank atual
- Número do Round 6 (001-456)
- Vitórias
- Derrotas
- Kills
- Deaths
- K/D Ratio
- Winrate

**Exemplo de Uso:**
```bash
# Ver suas próprias informações
/acc

# Admin ver informações de outro jogador
/acc Notch
```

---

### 2. ✅ Permissão nametag.default no Rank DEFAULT
**Arquivo:** `hub/src/main/java/rip/rafa/event/hub/rank/Rank.java`

Adicionado sistema de permissões aos ranks. Cada rank agora tem uma permissão associada.

**Permissões por Rank:**
- `DEFAULT` → `nametag.default`
- `GAMEMASTER` → `round6.gamemaster`
- `GUARDA` → `round6.guard`
- `CREATOR` → `round6.admin`

**Uso:**
Essas permissões podem ser usadas por plugins de nametag (como TAB, NametagEdit, etc.) para aplicar tags personalizadas automaticamente.

**Exemplo de Integração com TAB Plugin:**
```yaml
# TAB plugin config
groups:
  default:
    permission: nametag.default
    tabprefix: "&7"
    tagprefix: "&7"
  
  gamemaster:
    permission: round6.gamemaster
    tabprefix: "&x&f&f&c&0&c&b&oGameMaster "
    tagprefix: "&x&f&f&c&0&c&b&oGameMaster "
```

---

### 3. ✅ Sistema de Whitelist Completo
**Arquivos Criados:**
- `game/src/main/java/rip/rafa/event/game/manager/WhitelistManager.java`
- `game/src/main/java/rip/rafa/event/game/command/WhitelistCommand.java`
- `game/src/main/java/rip/rafa/event/game/listener/WhitelistListener.java`

Sistema completo de whitelist para controlar quem pode entrar no servidor Game.

**Comandos Disponíveis:**
```bash
/whitelist on              # Ativar whitelist
/whitelist off             # Desativar whitelist
/whitelist add <jogador>   # Adicionar jogador
/whitelist remove <jogador> # Remover jogador
/whitelist list            # Listar jogadores
/whitelist status          # Ver status
```

**Aliases:** `/wl`

**Funcionalidades:**
- ✅ Ativar/desativar whitelist
- ✅ Adicionar/remover jogadores
- ✅ Listar todos os jogadores na whitelist
- ✅ Ver status (ativada/desativada + quantidade de jogadores)
- ✅ Persistência em JSON (`plugins/Game/whitelist.json`)
- ✅ Bypass automático para admins (permissão `round6.admin`)
- ✅ Mensagem customizada ao ser kickado
- ✅ Log de todas as ações

**Arquivo whitelist.json:**
```json
{
  "enabled": true,
  "players": {
    "uuid-do-jogador-1": "Notch",
    "uuid-do-jogador-2": "Steve"
  }
}
```

**Mensagem de Kick:**
```
⬛ ROUND 6 ⬛

ᴠᴏᴄᴇ̂ ɴᴀ̃ᴏ ᴇꜱᴛᴀ́ ɴᴀ ᴡʜɪᴛᴇʟɪꜱᴛ!

ᴄᴏɴᴛᴀᴛᴇ ᴜᴍ ᴀᴅᴍɪɴɪꜱᴛʀᴀᴅᴏʀ
```

---

## 📦 Arquivos Criados/Modificados

### Criados
1. `game/src/main/java/rip/rafa/event/game/command/AccountCommand.java`
2. `game/src/main/java/rip/rafa/event/game/command/WhitelistCommand.java`
3. `game/src/main/java/rip/rafa/event/game/manager/WhitelistManager.java`
4. `game/src/main/java/rip/rafa/event/game/listener/WhitelistListener.java`

### Modificados
1. `hub/src/main/java/rip/rafa/event/hub/rank/Rank.java` - Adicionado campo `permission`
2. `game/src/main/java/rip/rafa/event/game/GamePlugin.java` - Registrado WhitelistManager e comandos
3. `game/src/main/resources/plugin.yml` - Adicionados comandos `/acc` e `/whitelist`

---

## 🎮 COMO USAR

### Comando /acc no Game

#### Jogador Normal
```bash
# Ver suas próprias estatísticas
/acc
```

**Saída:**
```
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                    ɪɴꜰᴏʀᴍᴀᴄ̧ᴏ̃ᴇꜱ ᴅᴀ ᴄᴏɴᴛᴀ
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

 ▪ ᴜꜱᴜᴀ́ʀɪᴏ: Notch
 ▪ ᴜᴜɪᴅ: 069a79f4-44e9-4726-a5be-fca90e38aaf5
 ▪ ʀᴀɴᴋ: DEFAULT
 ▪ ɴᴜ́ᴍᴇʀᴏ: 001

 ▪ ᴠɪᴛᴏ́ʀɪᴀꜱ: 5
 ▪ ᴅᴇʀʀᴏᴛᴀꜱ: 3
 ▪ ᴋɪʟʟꜱ: 23
 ▪ ᴅᴇᴀᴛʜꜱ: 15
 ▪ ᴋᴅʀ: 1.53
 ▪ ᴡɪɴʀᴀᴛᴇ: 62.5%

▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
```

#### Admin
```bash
# Ver estatísticas de outro jogador
/acc Steve
```

---

### Sistema de Whitelist

#### Ativar Whitelist
```bash
/whitelist on
```

**Resultado:**
- Whitelist ativada
- Apenas jogadores na lista podem entrar
- Admins têm bypass automático

#### Adicionar Jogador
```bash
/whitelist add Notch
```

**Resultado:**
- Notch adicionado à whitelist
- Pode entrar no servidor mesmo com whitelist ativada
- Salvo automaticamente em `whitelist.json`

#### Remover Jogador
```bash
/whitelist remove Notch
```

**Resultado:**
- Notch removido da whitelist
- Não pode mais entrar se whitelist estiver ativada
- Salvo automaticamente

#### Listar Jogadores
```bash
/whitelist list
```

**Saída:**
```
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                    ᴡʜɪᴛᴇʟɪꜱᴛ
▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬

 ▪ ꜱᴛᴀᴛᴜꜱ: ᴀᴛɪᴠᴀᴅᴀ
 ▪ ᴊᴏɢᴀᴅᴏʀᴇꜱ: 2

 ʟɪꜱᴛᴀ:
 ▪ Notch
 ▪ Steve

▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
```

#### Ver Status
```bash
/whitelist status
```

**Saída:**
```
ᴡʜɪᴛᴇʟɪꜱᴛ: ᴀᴛɪᴠᴀᴅᴀ
ᴊᴏɢᴀᴅᴏʀᴇꜱ: 2
```

#### Desativar Whitelist
```bash
/whitelist off
```

**Resultado:**
- Whitelist desativada
- Todos podem entrar novamente
- Lista de jogadores é mantida

---

## 🔒 PERMISSÕES

### Comando /acc (Game)
- Sem permissão: Ver apenas suas próprias informações
- `round6.admin`: Ver informações de qualquer jogador

### Comando /whitelist
- `round6.admin`: Usar todos os comandos de whitelist

### Bypass Whitelist
- `round6.admin`: Entrar mesmo com whitelist ativada

---

## 📊 STATUS DOS SISTEMAS

| Sistema | Status | Versão |
|---------|--------|--------|
| /acc no Game | ✅ | 1.0.0 |
| Permissões nos Ranks | ✅ | 1.0.0 |
| Whitelist Manager | ✅ | 1.0.0 |
| Whitelist Commands | ✅ | 1.0.0 |
| Whitelist Listener | ✅ | 1.0.0 |
| Persistência JSON | ✅ | 1.0.0 |
| Build | ✅ | Success |
| Deploy | ✅ | Complete |

---

## 💡 CASOS DE USO

### Caso 1: Evento Privado
```bash
# Preparar servidor para evento privado
/whitelist on

# Adicionar participantes
/whitelist add Jogador1
/whitelist add Jogador2
/whitelist add Jogador3

# Verificar lista
/whitelist list

# Iniciar evento
# Apenas os 3 jogadores podem entrar
```

### Caso 2: Manutenção
```bash
# Ativar whitelist para manutenção
/whitelist on

# Apenas admins podem entrar (bypass automático)
# Fazer manutenção

# Desativar quando terminar
/whitelist off
```

### Caso 3: Torneio
```bash
# Ativar whitelist
/whitelist on

# Adicionar todos os participantes do torneio
/whitelist add Participante1
/whitelist add Participante2
# ... etc

# Verificar quantos estão na lista
/whitelist status

# Iniciar torneio
```

---

## 🔧 TROUBLESHOOTING

### Problema: Jogador não consegue entrar
**Solução:**
1. Verificar se whitelist está ativada: `/whitelist status`
2. Verificar se jogador está na lista: `/whitelist list`
3. Adicionar jogador: `/whitelist add <jogador>`

### Problema: Admin não consegue usar comandos
**Solução:**
- Verificar se tem permissão `round6.admin`
- Dar permissão: `/lp user <admin> permission set round6.admin`

### Problema: Whitelist não salva
**Solução:**
- Verificar permissões da pasta `plugins/Game/`
- Verificar logs do servidor para erros
- Recriar arquivo manualmente se necessário

---

## 📝 LOGS

### Whitelist Logs
Todas as ações são registradas no console:

```
[INFO] [WHITELIST] Ativada por Admin
[INFO] [WHITELIST] Notch adicionado por Admin
[INFO] [WHITELIST] Steve tentou entrar mas não está na whitelist
[INFO] [WHITELIST] Desativada por Admin
```

---

## 🎉 CONCLUSÃO

Todas as funcionalidades foram implementadas com sucesso:
- ✅ /acc disponível no Game
- ✅ Permissão nametag.default no rank DEFAULT
- ✅ Sistema de whitelist completo e funcional
- ✅ Persistência em JSON
- ✅ Bypass para admins
- ✅ Logs de todas as ações
- ✅ Build successful
- ✅ Deploy completo

O projeto Round 6 do Rafa está ainda mais completo!

---

**Desenvolvido por: Rafa**  
**Assistido por: Kiro AI**  
**Data: 13/04/2026 21:00**  
**Versão: 1.0.2**  
**Build: Successful**  
**Status: ✅ PRONTO PARA PRODUÇÃO**

# 🎮 Round 6 - Funcionalidades Implementadas

## 📦 Plugins Desenvolvidos

### 1. Auth Plugin
Sistema de autenticação para jogadores piratas com skin embutida.

**Funcionalidades:**
- ✅ Sistema de registro e login
- ✅ Bloqueio de movimento até autenticação
- ✅ Bloqueio de chat até autenticação
- ✅ Sistema de skins embutido (sem SkinsRestorer)
- ✅ Armazenamento em JSON
- ✅ Redirecionamento automático para hub após login
- ✅ Suporte a ViaVersion

**Comandos:**
- `/register <senha> <senha>` - Criar conta
- `/login <senha>` - Fazer login

### 2. Hub Plugin
Lobby principal com NPCs, scoreboard e sistema completo de tags.

**Funcionalidades:**
- ✅ Sistema de NPCs com holograma
- ✅ Scoreboard personalizada
- ✅ Sistema de skins embutido
- ✅ Sistema de tags com PlaceholderAPI
- ✅ Suporte a cores hexadecimais
- ✅ Textos em small caps (ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ)
- ✅ Plugin hider (esconde plugins de terceiros)
- ✅ Suporte a ViaVersion
- ✅ Gradientes de cores
- ✅ Proteção contra dano e fome

**Comandos:**
- `/play` - Conectar ao servidor de jogo
- `/npc <reload>` - Gerenciar NPCs (admin)
- `/skin <jogador>` - Alterar skin
- `/plugins` ou `/pl` - Ver plugins (filtrado)

### 3. EventBungee Plugin
Plugin para Velocity/BungeeCord com detecção automática de jogadores piratas.

**Funcionalidades:**
- ✅ Detecção automática pirata/original
- ✅ Redirecionamento inteligente
- ✅ Comando de administração
- ✅ Sem dependências externas

**Comandos:**
- `/acc <jogador>` - Ver informações de conta (admin)

## 🎨 Sistema de Cores e Textos

### Cores Hexadecimais
Suporte completo a cores RGB com fallback automático para versões antigas.

**Formato:** `&#RRGGBB`

**Exemplos:**
```
&#FF1744 - Vermelho Round 6
&#FFD700 - Dourado
&#00FF00 - Verde
&#8B00FF - Roxo
```

### Gradientes
Sistema de gradiente automático entre duas cores.

**Exemplo:**
```java
TextUtil.gradient("ROUND 6", "FF1744", "8B00FF")
// Resultado: R vermelho → 6 roxo
```

### Small Caps
Textos em Unicode small caps para estilo único.

**Alfabeto:** ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ  
**Números:** ⁰¹²³⁴⁵⁶⁷⁸⁹

**Exemplo:**
```java
TextUtil.toSmallCaps("Round 6")
// Resultado: ʀᴏᴜɴᴅ ⁶
```

## 🏷️ Sistema de Tags

Sistema completo de tags integrado com PlaceholderAPI e LuckPerms.

### Tags Padrão

| Grupo | Tag | Cor | Prioridade |
|-------|-----|-----|------------|
| default | ᴊᴏɢᴀᴅᴏʀ | Cinza | 999 |
| vip | ᴠɪᴘ | Dourado | 100 |
| vip+ | ᴠɪᴘ⁺ | Verde | 90 |
| mvp | ᴍᴠᴘ | Ciano/Dourado | 80 |
| mvp+ | ᴍᴠᴘ⁺ | Vermelho/Dourado | 70 |
| youtuber | ʏᴏᴜᴛᴜʙᴇʀ | Vermelho | 60 |
| helper | ᴀᴊᴜᴅᴀɴᴛᴇ | Verde | 50 |
| mod | ᴍᴏᴅᴇʀᴀᴅᴏʀ | Laranja | 40 |
| admin | ᴀᴅᴍɪɴ | Vermelho | 30 |
| dono | ᴅᴏɴᴏ | Roxo/Vermelho | 10 |

### Configuração

1. Instale LuckPerms
2. Crie os grupos:
```bash
/lp creategroup vip
/lp creategroup admin
# etc...
```

3. Adicione jogadores aos grupos:
```bash
/lp user <jogador> parent set vip
```

4. As tags são aplicadas automaticamente!

## 🎭 Sistema de Skins

Sistema de skins completamente embutido, sem necessidade de plugins externos.

### Características
- ✅ Busca skins da API oficial da Mojang
- ✅ Cache de skins para performance
- ✅ Aplicação automática no login/register
- ✅ Comando manual para trocar skin
- ✅ Atualização visual instantânea

### Uso
```bash
/skin <jogador>
```

### API
```java
plugin.getSkinManager().applySkin(player, "Notch");
```

## 🔌 Plugin Hider

Sistema que esconde plugins de terceiros de jogadores normais.

### Comportamento
- **Jogadores normais:** Veem apenas "Auth, Hub, EventBungee"
- **Admins (round6.admin):** Veem todos os plugins

### Comandos Interceptados
- `/plugins`
- `/pl`

## 🌐 Suporte Multi-Versão

Com ViaVersion, o servidor suporta jogadores de 1.8 até 1.21+.

### Adaptações Automáticas
- ✅ Cores hex para 1.16+ (fallback para cores tradicionais em versões antigas)
- ✅ Text Display para versões modernas
- ✅ Item Display para versões modernas
- ✅ Detecção automática de versão do jogador

### Verificação de Versão
```java
if (TextUtil.supportsHexColors(player)) {
    // Jogador está em 1.16+
}
```

## 📊 Comparação: Antes vs Depois

| Recurso | Antes | Depois |
|---------|-------|--------|
| Skins | SkinsRestorer (externo) | Sistema embutido |
| Tags | Sem sistema | PlaceholderAPI + LuckPerms |
| Cores | Apenas &c | Hex colors + gradientes |
| Textos | Normais | Small caps Unicode |
| Versões | Apenas 1.8 | 1.8 até 1.21+ |
| Plugins visíveis | Todos | Apenas próprios |
| Display entities | Não | Sim (1.19.4+) |

## 🚀 Performance

### Otimizações
- Cache de skins para evitar requisições repetidas
- Atualização assíncrona de tags
- Busca de skins em thread separada
- Scoreboard otimizada com update a cada 1 segundo

### Rate Limits
- API Mojang: ~600 requisições por 10 minutos
- Recomendado: Cache de skins em banco de dados para produção

## 📝 Exemplos de Uso

### Criar NPC com Holograma
```yaml
# npcs.json
{
  "id": "round6",
  "name": "§c§lROUND 6",
  "location": {
    "world": "world",
    "x": 0.5,
    "y": 64.0,
    "z": 0.5,
    "yaw": 0.0,
    "pitch": 0.0
  },
  "hologram": {
    "enabled": true,
    "height": 2.5,
    "lines": [
      "&#FF1744ʀᴏᴜɴᴅ ⁶",
      "&#FFFFFF{online} ᴊᴏɢᴀᴅᴏʀᴇꜱ"
    ]
  },
  "action": {
    "type": "CONNECT_SERVER",
    "server": "game"
  }
}
```

### Mensagem com Gradiente
```java
String msg = TextUtil.gradient("BEM-VINDO", "FF1744", "8B00FF");
player.sendMessage(msg);
```

### Tag Customizada
```java
tagManager.addTag("especial", "&#FF1744ᴇꜱᴘᴇᴄɪᴀʟ", 50);
```

## 🔧 Configuração Recomendada

### Permissões
```yaml
round6.admin:
  description: Acesso admin completo
  children:
    event.admin: true
    hub.npc: true
```

### Grupos LuckPerms
```bash
# VIP
/lp creategroup vip
/lp group vip permission set round6.vip true

# Admin
/lp creategroup admin
/lp group admin permission set round6.admin true
/lp group admin parent add vip
```

## 📚 Documentação Adicional

- [DEPENDENCIES.md](DEPENDENCIES.md) - Lista completa de dependências
- [SETUP_GUIDE.md](SETUP_GUIDE.md) - Guia de configuração do servidor
- [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) - Guia de migração

## 🎯 Próximos Passos

Para usar em produção:
1. Implementar cache de skins em banco de dados
2. Adicionar mais tags customizadas
3. Criar sistema de Text Display para NPCs (1.19.4+)
4. Implementar Item Display para itens flutuantes
5. Adicionar animações de gradiente

# Dependências dos Plugins

## Plugins Necessários

### Para o Servidor Auth
- **ViaVersion** 4.9.2+
  - Download: https://hangar.papermc.io/ViaVersion/ViaVersion
  - Permite jogadores de diferentes versões conectarem

### Para o Servidor Hub
- **ViaVersion** 4.9.2+
  - Download: https://hangar.papermc.io/ViaVersion/ViaVersion
  - Permite jogadores de diferentes versões conectarem

- **PlaceholderAPI** 2.11.5+
  - Download: https://hangar.papermc.io/HelpChat/PlaceholderAPI
  - Necessário para o sistema de tags

- **LuckPerms** (Recomendado)
  - Download: https://luckperms.net/download
  - Para gerenciar permissões e grupos (usado pelas tags)

### Para o Velocity (Proxy)
- **ViaVersion** para Velocity
  - Download: https://hangar.papermc.io/ViaVersion/ViaVersion
  - Versão para Velocity

## Recursos Implementados

### ✅ Sistema de Skins Embutido
- Não precisa mais do SkinsRestorer
- Busca skins diretamente da API da Mojang
- Comando: `/skin <jogador>`
- Aplica automaticamente no login/register

### ✅ Suporte a Múltiplas Versões
- ViaVersion permite jogadores de 1.8 até 1.21+
- Cores hexadecimais adaptadas por versão
- Text Display e Item Display para versões modernas

### ✅ Cores Hexadecimais
- Formato: `&#RRGGBB`
- Exemplo: `&#FF1744` (vermelho Round 6)
- Gradientes automáticos disponíveis
- Fallback para cores tradicionais em versões antigas

### ✅ Textos Small Caps
- Caracteres Unicode: ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ
- Números: ⁰¹²³⁴⁵⁶⁷⁸⁹
- Função: `TextUtil.toSmallCaps(text)`

### ✅ Sistema de Tags
- Integrado com PlaceholderAPI
- Usa grupos do LuckPerms
- Tags padrão:
  - `default` - ᴊᴏɢᴀᴅᴏʀ (cinza)
  - `vip` - ᴠɪᴘ (dourado)
  - `vip+` - ᴠɪᴘ⁺ (verde)
  - `mvp` - ᴍᴠᴘ (ciano/dourado)
  - `mvp+` - ᴍᴠᴘ⁺ (vermelho/dourado)
  - `youtuber` - ʏᴏᴜᴛᴜʙᴇʀ (vermelho)
  - `helper` - ᴀᴊᴜᴅᴀɴᴛᴇ (verde)
  - `mod` - ᴍᴏᴅᴇʀᴀᴅᴏʀ (laranja)
  - `admin` - ᴀᴅᴍɪɴ (vermelho)
  - `dono` - ᴅᴏɴᴏ (roxo/vermelho)

### ✅ Plugin Hider
- Comando `/plugins` e `/pl` interceptados
- Jogadores normais veem apenas: Auth, Hub, EventBungee
- Admins com permissão `round6.admin` veem todos

## Uso das Funcionalidades

### Cores Hexadecimais
```java
String text = TextUtil.colorize("&#FF1744Round 6 &#FFFFFFServer");
```

### Gradientes
```java
String gradient = TextUtil.gradient("ROUND 6", "FF1744", "8B00FF");
```

### Small Caps
```java
String small = TextUtil.toSmallCaps("Round 6"); // ʀᴏᴜɴᴅ ⁶
```

### Estilo Round 6
```java
String styled = TextUtil.round6Style("jogador"); // ᴊᴏɢᴀᴅᴏʀ em vermelho
```

### Aplicar Skin
```java
plugin.getSkinManager().applySkin(player, "Notch");
```

### Adicionar Tag Customizada
```java
plugin.getTagManager().addTag("especial", "&#FF1744ᴇꜱᴘᴇᴄɪᴀʟ", 50);
```

## Configuração do LuckPerms

Para as tags funcionarem, configure os grupos no LuckPerms:

```bash
# Criar grupos
/lp creategroup vip
/lp creategroup vip+
/lp creategroup mvp
/lp creategroup mvp+
/lp creategroup youtuber
/lp creategroup helper
/lp creategroup mod
/lp creategroup admin
/lp creategroup dono

# Adicionar jogador a um grupo
/lp user <jogador> parent set vip
```

## Estrutura de Arquivos

```
plugins/
├── Auth.jar
├── Hub.jar
├── ViaVersion.jar
├── PlaceholderAPI.jar
└── LuckPerms.jar
```

## Links de Download

1. **ViaVersion**: https://hangar.papermc.io/ViaVersion/ViaVersion
2. **PlaceholderAPI**: https://hangar.papermc.io/HelpChat/PlaceholderAPI
3. **LuckPerms**: https://luckperms.net/download
4. **Velocity**: https://papermc.io/downloads/velocity
5. **Paper**: https://papermc.io/downloads/paper

## Notas Importantes

- O sistema de skins faz requisições à API da Mojang (rate limit: ~600 req/10min)
- Tags são atualizadas automaticamente no join
- Cores hex só aparecem para jogadores 1.16+
- Small caps funcionam em todas as versões (Unicode)

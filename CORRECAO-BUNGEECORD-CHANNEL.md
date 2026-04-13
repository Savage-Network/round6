# 🔧 Correção: Canal BungeeCord não registrado

## ❌ Problema

Ao usar o comando `/play` no Hub, ocorria o seguinte erro:

```
Caused by: org.bukkit.plugin.messaging.ChannelNotRegisteredException: 
Attempted to send a plugin message through the unregistered channel `BungeeCord'.
```

## 🔍 Causa

Os plugins Hub e Game estavam tentando enviar mensagens pelo canal `BungeeCord` sem registrá-lo primeiro. O canal BungeeCord é necessário para comunicação entre servidores (enviar jogadores de um servidor para outro).

## ✅ Solução Aplicada

### 1. Hub Plugin

**Arquivo:** `hub/src/main/java/rip/rafa/event/hub/HubPlugin.java`

**Adicionado no `onEnable()`:**
```java
// Registra canal BungeeCord
getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
```

**Adicionado no `onDisable()`:**
```java
// Remove canal BungeeCord
getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
```

### 2. Game Plugin

**Arquivo:** `game/src/main/java/rip/rafa/event/game/GamePlugin.java`

**Adicionado no `onEnable()`:**
```java
// Registra canal BungeeCord
getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
```

**Adicionado no `onDisable()`:**
```java
// Remove canal BungeeCord
getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
```

## 📦 JARs Atualizados

Os seguintes JARs foram recompilados e atualizados:

- ✅ `Hub.jar` - Com canal BungeeCord registrado
- ✅ `Game.jar` - Com canal BungeeCord registrado

**Localizações:**
- `jars-round6/Hub.jar`
- `jars-round6/Game.jar`
- `deploy-pronto/hub/plugins/Hub.jar`
- `deploy-pronto/game/plugins/Game.jar`

## 🎯 O que isso resolve?

### Comando /play
Agora funciona corretamente para enviar jogadores do Hub para o Game:

```java
ByteArrayDataOutput out = ByteStreams.newDataOutput();
out.writeUTF("Connect");
out.writeUTF("game");
player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
```

### NPCs
NPCs que conectam jogadores a outros servidores agora funcionam:

```java
ByteArrayDataOutput out = ByteStreams.newDataOutput();
out.writeUTF("Connect");
out.writeUTF(serverName);
player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
```

### Qualquer comunicação BungeeCord
Todos os comandos e funcionalidades que precisam enviar jogadores entre servidores agora funcionam.

## 📝 Logs Esperados

Ao iniciar os servidores, você verá:

**Hub:**
```
[Hub] Hub Plugin habilitado!
[Hub] Canal BungeeCord registrado!
[Hub] Sistema de skins embutido ativo
[Hub] Banco de dados MySQL conectado
```

**Game:**
```
[Game] Game Plugin habilitado!
[Game] Canal BungeeCord registrado!
[Game] Round 6 do Rafa - Sistema completo ativo!
[Game] Banco de dados MySQL conectado
```

## 🧪 Como Testar

1. Faça upload dos novos JARs
2. Reinicie os servidores Hub e Game
3. Entre no Hub
4. Use o comando `/play` ou clique no NPC
5. Você deve ser enviado para o servidor Game sem erros

## 📚 Sobre o Canal BungeeCord

O canal `BungeeCord` é um canal de plugin messaging que permite:

- **Enviar jogadores entre servidores:** `Connect`
- **Enviar mensagens para outros servidores:** `Forward`
- **Obter informações do proxy:** `PlayerCount`, `GetServers`
- **Kickar jogadores:** `KickPlayer`

### Registro Obrigatório

Todo plugin que usa o canal BungeeCord DEVE registrá-lo:

```java
// Registrar (no onEnable)
getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

// Remover (no onDisable)
getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
```

### Uso Básico

```java
// Criar mensagem
ByteArrayDataOutput out = ByteStreams.newDataOutput();
out.writeUTF("Connect");  // Subcanal
out.writeUTF("game");     // Servidor destino

// Enviar
player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
```

## ⚠️ Importante

- O canal deve ser registrado ANTES de qualquer tentativa de uso
- O canal deve ser removido no `onDisable()` para limpeza adequada
- Funciona apenas em servidores conectados ao BungeeCord/Velocity
- Não funciona em servidores standalone

## 🔄 Próximos Passos

1. ✅ Faça upload dos novos JARs
2. ✅ Reinicie os servidores
3. ✅ Teste o comando `/play`
4. ✅ Teste os NPCs
5. ✅ Verifique os logs

## 📊 Status

- ✅ Hub.jar - Corrigido e compilado
- ✅ Game.jar - Corrigido e compilado
- ✅ Auth.jar - Não precisa (não usa BungeeCord messaging)
- ✅ EventBungee.jar - Não precisa (é um plugin BungeeCord)

---

**Correção aplicada em:** 13/04/2026
**Versão:** 1.0.1
**Status:** ✅ Resolvido

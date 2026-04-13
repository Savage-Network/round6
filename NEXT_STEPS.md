# Next Steps - Game Plugin Migration

## 🎯 Objetivo

Migrar o plugin `round6-game/` para `game/` com o novo package `rip.rafa.event.game`.

## 📋 Checklist de Migração

### 1. Estrutura Base
- [ ] Criar `game/build.gradle.kts`
- [ ] Criar `game/src/main/java/rip/rafa/event/game/GamePlugin.java`
- [ ] Criar `game/src/main/resources/plugin.yml`
- [ ] Copiar `config.json` (já criado)

### 2. Core Systems
- [ ] `manager/GameManager.java` - Gerenciamento de jogos
- [ ] `config/GameConfig.java` - Carregamento de configurações

### 3. Minigames (copiar de round6-game/src/main/java/com/round6/event/game/games/)
- [ ] `GameType.java` - Enum dos jogos
- [ ] `GameExplanation.java` - Sistema de explicações
- [ ] `RedLightGreenLight.java` - Jogo 1
- [ ] `TugOfWar.java` - Jogo 2
- [ ] `Mingle.java` - Jogo 3
- [ ] `GlassBridge.java` - Jogo 4

### 4. Player Systems
- [ ] `tag/TagHandler.java` - Sistema de tags
- [ ] `number/PlayerNumberManager.java` - Sistema de números
- [ ] `guard/GuardManager.java` - Sistema de guardas
- [ ] `whitelist/WhitelistManager.java` - Sistema de whitelist

### 5. UI Systems
- [ ] `scoreboard/GameScoreboardManager.java` - Scoreboard do jogo
- [ ] `tab/GameTabManager.java` - Tab list

### 6. Commands
- [ ] `command/GameCommand.java` - /game <modalidade>
- [ ] `command/StartCommand.java` - /start
- [ ] `command/StopCommand.java` - /stop
- [ ] `command/GuardCommand.java` - /definirguarda
- [ ] `command/WhitelistCommand.java` - /whitelist

### 7. Listeners
- [ ] `listener/GameListener.java` - Eventos do jogo
- [ ] `listener/PlayerJoinListener.java` - Join/quit

## 🔄 Processo de Migração

Para cada arquivo:

1. **Copiar** o arquivo de `round6-game/src/main/java/com/round6/event/game/`
2. **Renomear** package de `com.round6.event.game` para `rip.rafa.event.game`
3. **Atualizar** imports para usar novos packages
4. **Remover** dependências de Redis/PostgreSQL
5. **Simplificar** código removendo abstrações desnecessárias
6. **Testar** funcionalidade

## 📝 Exemplo de Migração

### Antes (round6-game)
```java
package com.round6.event.game.manager;

import com.round6.event.game.Round6Plugin;
import com.round6.event.game.games.*;
// ...

public class GameManager {
    // código
}
```

### Depois (game)
```java
package rip.rafa.event.game.manager;

import rip.rafa.event.game.GamePlugin;
import rip.rafa.event.game.games.*;
// ...

public class GameManager {
    // código (mesmo, só packages diferentes)
}
```

## 🛠️ Build.gradle.kts Template

```kotlin
plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "rip.rafa.event"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks {
    shadowJar {
        archiveFileName.set("Game.jar")
    }
    
    build {
        dependsOn(shadowJar)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}
```

## 🎮 Plugin.yml Template

```yaml
name: Game
version: 1.0.0
main: rip.rafa.event.game.GamePlugin
author: Rafa
api-version: 1.8

commands:
  game:
    description: Escolher minigame
    usage: /game <modalidade>
    permission: event.admin
  start:
    description: Iniciar evento
    usage: /start
    permission: event.admin
  stop:
    description: Parar evento
    usage: /stop
    permission: event.admin
  definirguarda:
    description: Definir guarda
    usage: /definirguarda <jogador>
    permission: event.admin
  whitelist:
    description: Gerenciar whitelist
    usage: /whitelist <status|on|off|definir>
    permission: event.admin
```

## 🔍 Arquivos Prioritários

Migrar nesta ordem para ter funcionalidade básica:

1. **GamePlugin.java** - Classe principal
2. **GameConfig.java** - Configurações
3. **GameManager.java** - Gerenciador
4. **GameType.java** - Enum dos jogos
5. **GameExplanation.java** - Explicações
6. **RedLightGreenLight.java** - Primeiro jogo
7. **GameCommand.java** - Comando principal

Depois disso, o plugin já pode ser testado com 1 jogo funcionando.

## ✅ Validação

Após migração, verificar:

- [ ] Plugin carrega sem erros
- [ ] Config.json é lido corretamente
- [ ] Comando /game funciona
- [ ] Pelo menos 1 minigame funciona
- [ ] Scoreboard aparece
- [ ] Tab list funciona
- [ ] Sistema de eliminação funciona
- [ ] Explicações aparecem antes dos jogos

## 🚀 Comando de Build

```bash
# Build apenas o game
./gradlew :game:build

# Build tudo
./gradlew build

# Limpar e build
./gradlew clean build
```

## 📦 Output

JAR final: `game/build/libs/Game.jar`

Copiar para: `servidor-game/plugins/Game.jar`

## 🐛 Troubleshooting

### Erro: Package não encontrado
- Verifique se todos os imports foram atualizados
- Procure por `com.round6.event` e substitua por `rip.rafa.event`

### Erro: Classe não encontrada
- Verifique o `main` no plugin.yml
- Deve ser: `rip.rafa.event.game.GamePlugin`

### Erro: Config não carrega
- Verifique se config.json está em `src/main/resources/`
- Verifique se o arquivo é JSON válido

### Jogo não inicia
- Verifique logs do console
- Confirme que coordenadas estão configuradas
- Teste com 2+ jogadores (minPlayers)

## 💡 Dicas

1. Use Find & Replace para trocar packages em massa
2. Compile frequentemente para pegar erros cedo
3. Teste cada jogo individualmente
4. Mantenha backup do código original
5. Commit após cada arquivo migrado

## 📞 Ajuda

Se encontrar problemas:
1. Verifique console logs
2. Compare com código original
3. Teste em servidor local primeiro
4. Verifique se config.json está correto
5. Confirme que todos os imports estão atualizados

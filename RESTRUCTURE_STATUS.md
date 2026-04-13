# Restructure Status

## Completed ✅

### 1. Auth Plugin (NEW)
- ✅ Created `auth/` module with `rip.rafa.event.auth` package
- ✅ Implemented login/register system
- ✅ Added SkinsRestorer integration
- ✅ JSON-based user storage (no database)
- ✅ Auto-redirect to hub after authentication
- ✅ Movement/chat/command blocking until authenticated

**Files Created:**
- `auth/build.gradle.kts`
- `auth/src/main/java/rip/rafa/event/auth/AuthPlugin.java`
- `auth/src/main/java/rip/rafa/event/auth/manager/AuthManager.java`
- `auth/src/main/java/rip/rafa/event/auth/user/AuthUser.java`
- `auth/src/main/java/rip/rafa/event/auth/listener/AuthListener.java`
- `auth/src/main/java/rip/rafa/event/auth/command/LoginCommand.java`
- `auth/src/main/java/rip/rafa/event/auth/command/RegisterCommand.java`
- `auth/src/main/resources/plugin.yml`

### 2. BungeeCord Plugin (UPDATED)
- ✅ Created `bungee/` module with `rip.rafa.event.bungee` package
- ✅ Implemented pirate/original detection using `isOnlineMode()`
- ✅ Auto-redirect: Pirate → auth, Original → hub
- ✅ Simplified account command
- ✅ Removed all Redis dependencies

**Files Created:**
- `bungee/build.gradle.kts`
- `bungee/src/main/java/rip/rafa/event/bungee/EventBungee.java`
- `bungee/src/main/java/rip/rafa/event/bungee/listener/ServerConnectListener.java`
- `bungee/src/main/java/rip/rafa/event/bungee/command/AccountCommand.java`
- `bungee/src/main/resources/bungee.yml`
- `bungee/src/main/resources/config.yml`

### 3. Project Structure
- ✅ Updated `settings.gradle.kts` with new modules
- ✅ Created example `config.json` for game coordinates

## In Progress 🔄

### 4. Hub Plugin
- ✅ Created module structure
- ✅ Created build.gradle.kts
- ⏳ Need to migrate NPC system with custom skin
- ⏳ Need to migrate scoreboard/tab system
- ⏳ Need to migrate player listener
- ⏳ Need to create PlayCommand

**Status:** Basic structure created, needs full migration from `round6-hub/`

### 5. Game Plugin
- ⏳ Need to create complete structure with `rip.rafa.event.game` package
- ⏳ Need to migrate all game systems
- ⏳ Need to migrate GameManager
- ⏳ Need to migrate all 4 minigames (RedLight, Tug, Mingle, Glass)
- ⏳ Need to migrate tag/guard/number systems
- ⏳ Need to migrate whitelist system
- ⏳ Need to migrate scoreboard/tab systems

**Status:** Not started, needs full migration from `round6-game/`

## TODO 📋

### Hub Plugin Migration
1. Copy and update NPC system from `round6-hub/src/main/java/com/round6/event/hub/npc/`
   - Update package to `rip.rafa.event.hub.npc`
   - Implement custom skin from https://pt.namemc.com/skin/9e01f1a8aa1a9b61
   - Use ProtocolLib for skin application
   - Remove Redis dependencies

2. Copy scoreboard system
   - Update package to `rip.rafa.event.hub.scoreboard`

3. Copy listener
   - Update package to `rip.rafa.event.hub.listener`

4. Create PlayCommand
   - Connect to game server via BungeeCord messaging

5. Create npcs.json config

### Game Plugin Migration
1. Create base structure
   - `rip.rafa.event.game.GamePlugin`
   - Update all imports

2. Migrate game systems
   - `games/` folder with all 4 minigames
   - `manager/GameManager.java`
   - `config/GameConfig.java`
   - `command/` folder (GameCommand, GuardCommand, etc.)

3. Migrate player systems
   - `tag/` - Tag system (PLAYER, GUARD, VIP)
   - `number/` - Player number system (001-456)
   - `whitelist/` - Whitelist with permissions

4. Migrate UI systems
   - `scoreboard/` - Game scoreboard
   - `tab/` - Tab list manager

5. Migrate listeners
   - Game listeners
   - Player join listeners

### NPC Custom Skin Implementation
The NPC should use this skin: https://pt.namemc.com/skin/9e01f1a8aa1a9b61

**Texture Data (from NameMC):**
- Value: `ewogICJ0aW1lc3RhbXAiIDogMTcyOTk2NzU5NzY5MSwKICAicHJvZmlsZUlkIiA6ICI5ZTAxZjFhOGFhMWE5YjYxIiwKICAicHJvZmlsZU5hbWUiIDogIkd1YXJkIiwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QzZDNkM2QiCiAgICB9CiAgfQp9`

Use ProtocolLib to apply this texture to the NPC ArmorStand.

## Migration Commands

To complete the migration, run these commands:

```bash
# Build auth plugin
./gradlew :auth:build

# Build bungee plugin
./gradlew :bungee:build

# After completing hub migration
./gradlew :hub:build

# After completing game migration
./gradlew :game:build
```

## BungeeCord Configuration

Add these servers to your `config.yml`:

```yaml
servers:
  auth:
    motd: 'Auth Server'
    address: localhost:25566
    restricted: false
  hub:
    motd: 'Hub Server'
    address: localhost:25567
    restricted: false
  game:
    motd: 'Round 6 Game'
    address: localhost:25568
    restricted: false
```

## Required Dependencies

### Auth Server
- Spigot 1.8.8
- SkinsRestorer plugin

### Hub Server
- Spigot 1.8.8
- ProtocolLib plugin

### Game Server
- Spigot 1.8.8

### BungeeCord
- Waterfall/BungeeCord latest

## Next Steps

1. Complete hub plugin migration (NPC with custom skin is priority)
2. Complete game plugin migration
3. Test pirate/original detection
4. Test authentication flow
5. Test NPC skin application
6. Test all minigames with config.json

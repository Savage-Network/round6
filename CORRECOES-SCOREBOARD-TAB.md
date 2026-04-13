# 🔧 Correções: Scoreboard e Tab List

## ✅ Problemas Corrigidos

### 1. Scoreboard Piscando
**Problema:** A scoreboard estava piscando/tremendo constantemente.

**Causa:** O método `updateScoreboard()` estava limpando todos os scores a cada atualização com `scoreboard.resetScores(entry)`, causando flickering visual.

**Solução:** Removida a limpeza de scores. Agora cada linha usa um identificador único que não muda, evitando recriação desnecessária:

```java
// Antes (causava piscada):
for (String entry : scoreboard.getEntries()) {
    scoreboard.resetScores(entry);
}
Score score = objective.getScore(colorize(lineText));

// Depois (sem piscada):
String entry = colorized + "§" + line;
Score score = objective.getScore(entry);
```

---

### 2. Gamemaster não Contado na Scoreboard
**Problema:** Gamemasters e guardas eram contados como jogadores normais na scoreboard.

**Causa:** Os métodos `getPlayerCount()` e `getAliveCount()` contavam todos os jogadores sem filtrar gamemasters/guardas.

**Solução:** Adicionado filtro para excluir gamemasters e guardas da contagem:

```java
public int getPlayerCount() {
    int count = 0;
    for (UUID uuid : players) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && !plugin.getGameMasterManager().isGameMaster(player) 
            && !plugin.getGameMasterManager().isGuard(player)) {
            count++;
        }
    }
    return count;
}

public int getAliveCount() {
    int count = 0;
    for (UUID uuid : alivePlayers) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && !plugin.getGameMasterManager().isGameMaster(player) 
            && !plugin.getGameMasterManager().isGuard(player)) {
            count++;
        }
    }
    return count;
}
```

---

### 3. Tab List sem Prefixo
**Problema:** O tab list não mostrava nenhum prefixo ao lado do nome dos jogadores.

**Causa:** Não havia sistema de teams configurado para o tab list.

**Solução:** Implementado sistema de teams que adiciona prefixos baseados no papel do jogador:

```java
public void updateTabList(Player player) {
    for (Player online : Bukkit.getOnlinePlayers()) {
        Scoreboard scoreboard = scoreboards.get(online.getUniqueId());
        if (scoreboard == null) continue;
        
        Team team = scoreboard.getTeam(player.getName());
        if (team == null) {
            team = scoreboard.registerNewTeam(player.getName());
        }
        
        String prefix = "";
        if (plugin.getGameMasterManager().isGameMaster(player)) {
            prefix = colorize("&#FF1744[GM] ");
        } else if (plugin.getGameMasterManager().isGuard(player)) {
            prefix = colorize("&#FF1744[GUARD] ");
        }
        
        team.setPrefix(prefix);
        team.addEntry(player.getName());
    }
}
```

---

## 📊 Resultado

### Scoreboard
✅ Não pisca mais
✅ Atualiza suavemente a cada segundo
✅ Gamemasters e guardas não são contados
✅ Contagem precisa de jogadores

### Tab List
✅ Gamemasters aparecem como `[GM] Nome`
✅ Guardas aparecem como `[GUARD] Nome`
✅ Jogadores normais aparecem sem prefixo
✅ Cores hex funcionando

---

## 🎮 Como Funciona

### Contagem de Jogadores
```
Jogadores Online: 10
- 1 Gamemaster (não conta)
- 2 Guardas (não contam)
- 7 Jogadores normais

Scoreboard mostra: 7 jogadores
```

### Tab List
```
[GM] zrwfaxd
[GUARD] guard1
[GUARD] guard2
player1
player2
player3
player4
player5
player6
player7
```

---

## 🔧 Arquivos Modificados

### ScoreboardManager.java
**Localização:** `game/src/main/java/rip/rafa/event/game/manager/ScoreboardManager.java`

**Mudanças:**
1. Removida limpeza de scores no `updateScoreboard()`
2. Adicionado identificador único para cada linha
3. Adicionado método `updateTabList()`
4. Chamada de `updateTabList()` no `createScoreboard()`

### GameManager.java
**Localização:** `game/src/main/java/rip/rafa/event/game/manager/GameManager.java`

**Mudanças:**
1. `getPlayerCount()` agora filtra gamemasters e guardas
2. `getAliveCount()` agora filtra gamemasters e guardas

---

## 🧪 Como Testar

### Teste 1: Scoreboard não pisca
1. Entre no servidor Game
2. Observe a scoreboard
3. Ela deve atualizar suavemente sem piscar

### Teste 2: Gamemaster não conta
1. Defina alguém como gamemaster: `/r6 gm <player>`
2. Observe a contagem na scoreboard
3. O gamemaster não deve ser contado

### Teste 3: Tab list com prefixo
1. Pressione TAB
2. Gamemasters devem aparecer como `[GM] Nome`
3. Guardas devem aparecer como `[GUARD] Nome`
4. Jogadores normais sem prefixo

---

## 📝 Notas Técnicas

### Performance
- Scoreboard atualiza a cada 1 segundo (20 ticks)
- Tab list atualiza quando jogador entra
- Filtros de contagem são eficientes (O(n))

### Compatibilidade
- ✅ Paper 1.20.6
- ✅ Spigot 1.20.x
- ✅ Cores hex funcionando
- ✅ Teams do Bukkit

### Limitações
- Tab list só mostra prefixo para GM e GUARD
- Jogadores normais não têm prefixo (pode ser expandido)
- Máximo de 40 caracteres por linha da scoreboard

---

## 🚀 Próximas Melhorias

### Tab List
- [ ] Adicionar prefixo de rank (VIP, ADMIN, etc)
- [ ] Adicionar número do Round 6 (001-456)
- [ ] Ordenar por rank/papel
- [ ] Adicionar sufixo com ping

### Scoreboard
- [ ] Animações no título
- [ ] Mais informações dinâmicas
- [ ] Scoreboard personalizada por minigame
- [ ] Estatísticas pessoais

---

**Desenvolvido por: Rafa**
**Versão: 1.0.3**
**Data: 13/04/2026**
**Status: ✅ CORRIGIDO**

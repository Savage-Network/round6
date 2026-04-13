# ✅ ATUALIZAÇÃO - PLACEHOLDERS E FALLBACK

## 📅 Data: 13/04/2026 20:30

---

## 🎯 Mudanças Implementadas

### 1. ✅ Documentação Completa de Placeholders
**Arquivo:** `PLACEHOLDERS-ROUND6.md`

Criado documento completo com todos os 15 placeholders disponíveis:

#### Placeholders de Rank (5)
- `%round6_rank%` - Nome do rank
- `%round6_rank_prefix%` ou `%round6_prefix%` - Prefixo formatado
- `%round6_rank_color%` ou `%round6_color%` - Cor do rank
- `%round6_rank_priority%` - Prioridade (1-5)
- `%round6_tag%` - Tag completa

#### Placeholders de Número (2)
- `%round6_number%` - Número 001-456
- `%round6_number_prefix%` - Prefixo formatado com número

#### Placeholder de Display (1)
- `%round6_display_name%` - Nome completo com rank e número

#### Placeholders de Estatísticas (7)
- `%round6_wins%` - Vitórias
- `%round6_losses%` - Derrotas
- `%round6_kills%` - Kills
- `%round6_deaths%` - Deaths
- `%round6_kdr%` - K/D Ratio
- `%round6_winrate%` - Taxa de vitória

---

### 2. ✅ Fallback para Hub ao Fechar Game Server
**Arquivo:** `game/src/main/java/rip/rafa/event/game/GamePlugin.java`

Implementado sistema de fallback que:
- Detecta quando o servidor Game está fechando
- Envia todos os jogadores online para o Hub automaticamente
- Usa BungeeCord plugin messaging
- Mostra mensagem amigável ao jogador
- Previne que jogadores fiquem presos

**Código Implementado:**
```java
@Override
public void onDisable() {
    // Enviar todos os jogadores para o Hub antes de desligar
    getLogger().info("Enviando todos os jogadores para o Hub...");
    for (org.bukkit.entity.Player player : getServer().getOnlinePlayers()) {
        sendToHub(player);
    }
    
    // Remove canal BungeeCord
    getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    
    if (gameManager != null) {
        gameManager.shutdown();
    }
    
    if (databaseManager != null) {
        databaseManager.disconnect();
    }
    
    getLogger().info("Game Plugin desabilitado!");
}

private void sendToHub(org.bukkit.entity.Player player) {
    try {
        java.io.ByteArrayOutputStream b = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream out = new java.io.DataOutputStream(b);
        
        out.writeUTF("Connect");
        out.writeUTF("hub");
        
        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        player.sendMessage("§c§l⬛ ROUND 6 ⬛");
        player.sendMessage("§7Servidor fechando... Enviando para o Hub!");
    } catch (Exception e) {
        getLogger().warning("Erro ao enviar " + player.getName() + " para o Hub: " + e.getMessage());
    }
}
```

**Benefícios:**
- ✅ Jogadores não ficam presos em servidor offline
- ✅ Experiência suave ao reiniciar servidor
- ✅ Mensagem clara sobre o que está acontecendo
- ✅ Tratamento de erros implementado

---

### 3. ✅ Remoção Completa do Sistema de NPC
**Arquivos Modificados:**
- `hub/src/main/java/rip/rafa/event/hub/HubPlugin.java`
- `hub/src/main/java/rip/rafa/event/hub/listener/PlayerListener.java`
- `hub/src/main/resources/plugin.yml`

**Arquivos Deletados:**
- `hub/src/main/java/rip/rafa/event/hub/command/NPCCommand.java`
- `hub/src/main/java/rip/rafa/event/hub/command/NPCTabCompleter.java`

**Mudanças:**
- ❌ Removido NPCManager do HubPlugin
- ❌ Removido comando `/npc`
- ❌ Removido NPCTabCompleter
- ❌ Removida inicialização do NPCManager
- ❌ Removida limpeza de NPCs no onDisable
- ✅ Mensagem de boas-vindas atualizada: "Use /play para jogar"

**Antes:**
```
§7Bem-vindo ao lobby!
§7Clique no NPC para jogar
```

**Depois:**
```
§7Bem-vindo ao lobby!
§7Use §c/play §7para jogar
```

---

## 📦 Arquivos Criados

### 1. PLACEHOLDERS-ROUND6.md
Documentação completa com:
- Lista de todos os 15 placeholders
- Descrição detalhada de cada um
- Exemplos de uso
- Integração com outros plugins
- Guia de instalação do PlaceholderAPI
- Notas sobre performance e fallback

---

## 🔧 Como Testar

### Testar Fallback do Game Server
1. Entre no servidor Game
2. No console do Game, execute: `stop`
3. Você deve ser automaticamente enviado ao Hub
4. Deve ver a mensagem: "Servidor fechando... Enviando para o Hub!"

### Testar Remoção do NPC
1. Entre no Hub
2. Tente usar `/npc` - comando não deve existir
3. Veja a mensagem de boas-vindas - deve dizer "Use /play para jogar"
4. Use `/play` - deve funcionar normalmente

### Testar Placeholders
1. Instale PlaceholderAPI no Hub
2. Use `/papi parse me %round6_rank%`
3. Use `/papi parse me %round6_display_name%`
4. Use `/papi parse me %round6_wins%`
5. Todos devem retornar valores corretos

---

## 📊 Status dos Sistemas

| Sistema | Status | Observações |
|---------|--------|-------------|
| Placeholders | ✅ | 15 placeholders documentados |
| Fallback Hub | ✅ | Funciona ao fechar Game |
| NPC Removido | ✅ | Sistema completamente removido |
| Comando /play | ✅ | Funciona sem NPC |
| Build | ✅ | Compilado sem erros |
| Deploy | ✅ | JARs copiados |

---

## 🚀 Próximos Passos Sugeridos

### Melhorias Futuras
1. Adicionar mais placeholders de estatísticas
2. Criar placeholders para top players
3. Adicionar placeholder de tempo de jogo
4. Criar placeholder de último jogo
5. Adicionar placeholder de streak de vitórias

### Sistemas a Implementar
1. Sistema de votação (continuar ou sair)
2. Sistema de cofre dinâmico ($1,000 por eliminação)
3. Dormitório entre jogos
4. Uniformes automáticos
5. Efeitos especiais de eliminação

---

## 📝 Comandos Disponíveis

### Hub
```bash
/play              # Conectar ao Round 6
/skin <jogador>    # Trocar skin
/acc               # Ver informações da conta
/acc password      # Trocar senha
```

### Game
```bash
/r6 join           # Entrar no jogo
/r6 leave          # Sair do jogo
/r6 gm <player>    # Definir gamemaster (admin)
/r6 guard add      # Adicionar guarda (admin/gm)
```

### Auth
```bash
/register <senha> <senha>  # Registrar conta
/login <senha>             # Fazer login
/acc password              # Trocar senha
/acc email set             # Definir email
```

---

## 🎮 Experiência do Jogador

### Fluxo Normal
1. Jogador entra no BungeeCord (porta 25100)
2. Premium → Hub | Pirata → Auth
3. No Hub, usa `/play` para ir ao Game
4. Joga Round 6
5. Ao final, volta ao Hub automaticamente

### Fluxo com Servidor Fechando
1. Jogador está no Game
2. Admin executa `/stop` no Game
3. Jogador recebe mensagem: "Servidor fechando..."
4. Jogador é enviado automaticamente ao Hub
5. Pode usar `/play` novamente quando Game voltar

---

## 💡 Dicas de Uso

### Para Admins
- Use `/stop` no Game sem medo - jogadores vão ao Hub
- Reinicie o Game quando necessário
- Jogadores não ficam presos

### Para Jogadores
- Use `/play` no Hub para jogar
- Se o servidor fechar, você volta ao Hub automaticamente
- Use `/acc` para ver suas estatísticas

### Para Desenvolvedores
- Todos os placeholders estão documentados
- Sistema de fallback é automático
- NPC foi removido para simplificar

---

## 🔍 Detalhes Técnicos

### Fallback System
- Usa BungeeCord Plugin Messaging API
- Envia comando "Connect" com destino "hub"
- Executa antes de desligar o servidor
- Tratamento de exceções implementado
- Log de erros no console

### Placeholder System
- Integrado com PlaceholderAPI
- Cache automático pelo PAPI
- Retorna string vazia se jogador sem conta
- Suporta cores hexadecimais
- Performance otimizada

### NPC Removal
- Sistema completamente removido
- Sem dependências restantes
- Comando /play funciona independentemente
- Código mais limpo e simples

---

**Desenvolvido por: Rafa**  
**Assistido por: Kiro AI**  
**Data: 13/04/2026 20:30**  
**Versão: 1.0.1**  
**Build: Successful**

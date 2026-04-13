# Round 6 Event - Minecraft Server Network

Sistema completo de evento Round 6 para Minecraft 1.8.8 com suporte para jogadores piratas e originais.

## 🎮 Visão Geral

Este projeto implementa um servidor de eventos baseado na série Round 6 (Squid Game) com:
- Sistema de autenticação para jogadores piratas
- Detecção automática pirata/original
- 4 minigames da série
- Sistema de NPCs com skins customizadas
- Gerenciamento de jogadores (números, guardas, tags)

## 📦 Estrutura do Projeto

```
event/
├── auth/          # Plugin de autenticação (servidor auth)
├── bungee/        # Plugin BungeeCord (proxy)
├── hub/           # Plugin do lobby (servidor hub)
├── game/          # Plugin do evento (servidor game) - EM MIGRAÇÃO
└── docs/          # Documentação
```

## ✅ Status da Migração

### Completo
- ✅ Auth Plugin - Sistema de login/registro
- ✅ BungeeCord Plugin - Detecção pirata/original
- ✅ Hub Plugin - NPCs com skin customizada

### Em Progresso
- 🔄 Game Plugin - Minigames e sistemas de jogo

## 🚀 Início Rápido

### Pré-requisitos
- Java 21+ (recomendado)
- Paper 1.20.6 (ou superior)
- Velocity 3.3.0+ (Waterfall foi descontinuado!)
- SkinsRestorer plugin
- ProtocolLib plugin

### Build
```bash
# Build todos os plugins
./gradlew build

# Build individual
./gradlew :auth:build
./gradlew :bungee:build
./gradlew :hub:build
```

### Instalação

1. **Configure o Velocity** (`velocity.toml`):
```toml
[servers]
  auth = "127.0.0.1:25566"
  hub = "127.0.0.1:25567"
  game = "127.0.0.1:25568"

[player-info-forwarding]
  mode = "modern"
  secret = "SUA_CHAVE_SECRETA"  # Gere com: openssl rand -base64 32
```

2. **Configure os servidores Paper** (`server.properties`):
```properties
online-mode=false
```

E `paper-global.yml`:
```yaml
proxies:
  velocity:
    enabled: true
    online-mode: true
    secret: "MESMA_CHAVE_DO_VELOCITY"
```

3. **Instale os plugins**:
   - `auth/build/libs/Auth.jar` → servidor auth + SkinsRestorer
   - `velocity/build/libs/EventVelocity.jar` → Velocity
   - `hub/build/libs/Hub.jar` → servidor hub + ProtocolLib

4. **Inicie os servidores** na ordem:
   - Velocity
   - Servidor auth
   - Servidor hub
   - Servidor game

**⚠️ IMPORTANTE**: Waterfall foi descontinuado! Use Velocity 3.3.0+  
Veja `SETUP_GUIDE.md` para instruções detalhadas.

## 🎯 Funcionalidades

### Sistema de Autenticação
- Detecção automática de jogadores piratas
- Login/registro com comandos simples
- Integração com SkinsRestorer
- Armazenamento em JSON (sem banco de dados)

### Sistema de NPCs
- NPCs com skin customizada do guarda
- Holograma com informações
- Clique para conectar ao servidor de jogo
- Configuração via JSON

### Minigames (4 jogos)
1. **Red Light, Green Light** - Detecta movimento de câmera
2. **Tug of War** - Cabo de guerra em equipes
3. **Mingle** - Formar grupos do número anunciado
4. **Glass Bridge** - Ponte de vidro

### Sistemas de Jogador
- Números (001-456) como na série
- Sistema de guardas com prefixo especial
- Tags (PLAYER, GUARD, VIP)
- Whitelist baseada em permissões

## 📖 Documentação

- [MIGRATION_COMPLETE.md](MIGRATION_COMPLETE.md) - Status completo da migração
- [RESTRUCTURE_STATUS.md](RESTRUCTURE_STATUS.md) - Detalhes técnicos
- [NEW_STRUCTURE.md](NEW_STRUCTURE.md) - Nova arquitetura
- [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) - Guia de migração

## 🔧 Configuração

### auth/users.json
Gerado automaticamente ao registrar jogadores.

### hub/npcs.json
```json
{
  "npcs": [{
    "id": "round6_main",
    "name": "§c§lROUND 6",
    "useGuardSkin": true,
    "location": { "world": "world", "x": 0.5, "y": 64, "z": 0.5 }
  }]
}
```

### game/config.json
Coordenadas de todos os minigames. Ver arquivo para detalhes.

## 🎮 Comandos

### Jogadores
- `/login <senha>` - Fazer login (auth)
- `/register <senha> <senha>` - Registrar (auth)
- `/play` - Conectar ao evento (hub)

### Administradores
- `/acc info <jogador>` - Ver informações (bungee)
- `/acc send <jogador> <servidor>` - Enviar para servidor (bungee)
- `/npc reload` - Recarregar NPCs (hub)
- `/game <modalidade>` - Escolher minigame (game)
- `/definirguarda <jogador>` - Definir guarda (game)

## 🔐 Permissões

- `event.admin` - Comandos administrativos
- `event.whitelist.guard` - Acesso como guarda
- `event.whitelist.player` - Acesso como jogador

## 🌐 Fluxo de Conexão

```
Jogador conecta ao BungeeCord
         ↓
    Verifica tipo
    ↙          ↘
Pirata      Original
   ↓            ↓
Auth         Hub
   ↓            ↓
Login/Register  ↓
   ↓            ↓
   └─→ Hub ←───┘
         ↓
    Clica no NPC
         ↓
       Game
```

## 🎨 Skin do NPC

O NPC usa a skin do guarda da série:
- URL: https://pt.namemc.com/skin/9e01f1a8aa1a9b61
- Aplicada via ProtocolLib
- Configurável por NPC

## 🐛 Problemas Conhecidos

1. Contagem de jogadores no holograma precisa de plugin messaging
2. Hash de senha simples (melhorar para produção)
3. Plugin game ainda em migração

## 📝 TODO

- [ ] Completar migração do plugin game
- [ ] Implementar contagem de jogadores via plugin messaging
- [ ] Melhorar sistema de hash de senhas
- [ ] Adicionar mais minigames
- [ ] Sistema de ranking/estatísticas
- [ ] Replay system

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request

## 📄 Licença

Este projeto é privado e proprietário.

## 👤 Autor

Rafa - rip.rafa.event

## 🙏 Agradecimentos

- Inspirado na série Round 6 (Squid Game)
- Comunidade Minecraft
- SkinsRestorer e ProtocolLib developers

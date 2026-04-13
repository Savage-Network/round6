# ✅ Deploy Final - Round 6 Server (Configurado para Host)

## 🎯 Status: PRONTO PARA TESTES!

Todas as configurações foram atualizadas com base nas informações da sua host.

---

## 📦 Pasta: `deploy-pronto/`

### Estrutura Completa:

```
deploy-pronto/
├── velocity/          → Proxy (Porta 25100)
├── auth/              → Auth Server (Porta 25579)
├── hub/               → Hub Server (Porta 25578)
├── game/              → Game Server (Porta 25580) ✨ NOVO!
├── INICIAR-TUDO.bat   → Script Windows
├── INICIAR-TUDO.sh    → Script Linux
└── README.md          → Documentação completa
```

---

## 🔌 Portas Configuradas

| Servidor | Porta | Endereço | Firewall |
|----------|-------|----------|----------|
| Velocity | 25100 | admin.savagemc.net:25100 | ✅ Abrir |
| Hub | 25578 | admin.savagemc.net:25578 | ❌ Interna |
| Auth | 25579 | admin.savagemc.net:25579 | ❌ Interna |
| Game | 25580 | admin.savagemc.net:25580 | ❌ Interna |

---

## 🔽 Downloads Necessários

Você precisa baixar 5 arquivos JAR:

1. **Velocity 3.3.0+** → `velocity/velocity.jar`
   - https://papermc.io/downloads/velocity

2. **Paper 1.20.6** → `auth/paper.jar`
   - https://papermc.io/downloads/paper

3. **Paper 1.20.6** → `hub/paper.jar`
   - https://papermc.io/downloads/paper

4. **Paper 1.20.6** → `game/paper.jar`
   - https://papermc.io/downloads/paper

---

## ✅ O que já está configurado

### Velocity (velocity.toml)
```toml
bind = "0.0.0.0:25100"
auth = "admin.savagemc.net:25579"
hub = "admin.savagemc.net:25578"
game = "admin.savagemc.net:25580"
forwarding-secret = "round6secret2024"
```

### Auth Server
- ✅ Porta: 25579
- ✅ EULA aceito
- ✅ MySQL configurado
- ✅ Redirect para hub após login
- ✅ Sistema de skins embutido
- ✅ ViaVersion incluído

### Hub Server
- ✅ Porta: 25578
- ✅ EULA aceito
- ✅ MySQL configurado
- ✅ Sistema de tags com PlaceholderAPI
- ✅ Cores hex e small caps
- ✅ NPCs e hologramas
- ✅ ViaVersion incluído

### Game Server (NOVO!)
- ✅ Porta: 25580
- ✅ EULA aceito
- ✅ MySQL configurado
- ✅ Configurações do Round 6
- ✅ Scoreboard customizada
- ✅ Sistema de prêmios
- ✅ ViaVersion incluído

---

## 🗄️ MySQL (já configurado)

```yaml
Host: mysql52-farm1.kinghost.net:3306
Database: zrfa
User: zrfa
Password: Rafael141011
```

**Tabelas criadas automaticamente:**
- `round6_users` - Autenticação
- `round6_skins` - Cache de skins
- `round6_stats` - Estatísticas dos jogadores
- `round6_npcs` - NPCs do hub
- `round6_games` - Jogos Round 6
- `round6_players` - Jogadores em jogo

---

## 🚀 Como Iniciar

### Opção 1: Automático (Recomendado)

**Windows:**
```batch
INICIAR-TUDO.bat
```

**Linux:**
```bash
chmod +x INICIAR-TUDO.sh
./INICIAR-TUDO.sh
```

### Opção 2: Manual

**Ordem correta:**
1. Auth Server (25579) → Aguardar 30s
2. Hub Server (25578) → Aguardar 30s
3. Game Server (25580) → Aguardar 30s
4. Velocity (25100) → Aguardar 10s

**Windows:**
```batch
auth\start.bat
hub\start.bat
game\start.bat
velocity\start.bat
```

**Linux:**
```bash
./dar-permissao.sh
./auth/start.sh
./hub/start.sh
./game/start.sh
./velocity/start.sh
```

---

## 🎮 Fluxo de Jogadores

```
1. Jogador conecta → admin.savagemc.net:25100 (Velocity)
   ↓
2. Jogador pirata → Auth (25579)
   - /register senha senha
   - Skin aplicada automaticamente
   ↓
3. Redirecionamento → Hub (25578)
   - NPCs, hologramas, scoreboard
   - Tags com PlaceholderAPI
   ↓
4. Entrar no jogo → Game (25580)
   - Round 6 completo
   - Sistema de prêmios
   - Scoreboard do jogo

Jogador original → Vai direto para Hub (25578)
```

---

## ✨ Recursos Incluídos

### Auth Server
- ✅ Sistema de login/register
- ✅ Detecção automática pirata/original
- ✅ Sistema de skins embutido (sem SkinsRestorer)
- ✅ Bloqueio de movimento/chat até autenticar
- ✅ Redirecionamento automático após login

### Hub Server
- ✅ Sistema de NPCs
- ✅ Sistema de hologramas
- ✅ Scoreboard customizada
- ✅ Sistema de tags com PlaceholderAPI
- ✅ Cores hexadecimais (&#RRGGBB)
- ✅ Texto small caps (ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ)
- ✅ Plugin hider (mostra apenas plugins próprios)

### Game Server
- ✅ Sistema Round 6 completo
- ✅ Configurações personalizáveis
- ✅ Sistema de prêmios
- ✅ Scoreboard do jogo
- ✅ Mensagens com cores hex e small caps
- ✅ Suporte para múltiplos jogadores

### Todos os Servidores
- ✅ Suporte multi-versão 1.8-1.21+ (ViaVersion)
- ✅ MySQL com HikariCP (pool de conexões)
- ✅ Forwarding secret configurado
- ✅ Scripts de inicialização prontos

---

## 📝 Arquivos Importantes

- `README.md` - Documentação completa
- `CONFIGURACAO-ATUALIZADA.txt` - Detalhes das configurações
- `DOWNLOAD-JARS.txt` - Links para download
- `INICIAR-TUDO.bat` - Script Windows
- `INICIAR-TUDO.sh` - Script Linux
- `dar-permissao.sh` - Permissões Linux

---

## 🔧 Configurações do Game

Arquivo: `game/plugins/Game/config.yml`

```yaml
round6:
  waiting-time: 30          # Tempo de espera (segundos)
  min-players: 2            # Mínimo de jogadores
  max-players: 100          # Máximo de jogadores
  game-cooldown: 60         # Tempo entre jogos
  win-reward: 1000          # Prêmio por vitória
```

---

## 🧪 Testar

1. Baixe os 5 JARs (Velocity + 4x Paper)
2. Coloque nas pastas corretas
3. Execute `INICIAR-TUDO.bat` ou `INICIAR-TUDO.sh`
4. Conecte em: `admin.savagemc.net:25100`
5. Teste o fluxo: Auth → Hub → Game

---

## 🐛 Troubleshooting

### "Can't connect to MySQL"
→ Verifique se o host MySQL está acessível  
→ Teste: `ping mysql52-farm1.kinghost.net`

### "Connection refused"
→ Inicie na ordem: Auth → Hub → Game → Velocity  
→ Aguarde cada servidor mostrar "Done!"

### "Invalid forwarding secret"
→ NÃO altere os arquivos de configuração  
→ O secret já está correto em todos os arquivos

### Jogadores não redirecionam
→ Verifique `velocity/logs/latest.log`  
→ Confirme que todos os 4 servidores estão rodando

---

## 📊 Tamanhos dos Plugins

- Auth.jar: 24 KB
- Hub.jar: 39 KB
- Game.jar: ~50 KB
- ViaVersion.jar: ~3 MB
- PlaceholderAPI.jar: ~500 KB

---

## 🎉 Pronto para Testes!

Tudo está configurado com as portas corretas da sua host:
- ✅ Velocity: 25100
- ✅ Hub: 25578
- ✅ Auth: 25579
- ✅ Game: 25580

Só baixar os JARs e iniciar os servidores!

**Boa sorte com os testes do Round 6! 🚀**

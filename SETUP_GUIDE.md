# Guia de Configuração e Instalação

## 📦 Versões Recomendadas

### Paper (Servidores Bukkit)
**Versão Recomendada: Paper 1.20.6**
- Download: https://papermc.io/downloads/paper
- Build mais recente estável de 1.20.6
- Suporta Java 21+
- Melhor performance e compatibilidade

**Alternativas:**
- Paper 1.20.4 (estável)
- Paper 1.20.1 (LTS)

### Proxy (BungeeCord)
**⚠️ IMPORTANTE: Waterfall foi descontinuado!**

**Versão Recomendada: Velocity 3.3.0+**
- Download: https://papermc.io/downloads/velocity
- Substituto oficial do Waterfall pela equipe PaperMC
- Até 8x mais rápido que BungeeCord/Waterfall
- Melhor segurança e performance
- Suporta Java 21+

**Por que Velocity?**
- Waterfall atingiu fim de vida em março de 2024
- Velocity é construído do zero com foco em performance
- Totalmente compatível com plugins BungeeCord (com adaptação)
- Recomendado oficialmente pela equipe PaperMC

**Se precisar usar BungeeCord:**
- BungeeCord latest (não recomendado)
- Download: https://ci.md-5.net/job/BungeeCord/

## 🔧 Requisitos do Sistema

### Java
- **Java 21** (recomendado para Paper 1.20.6 e Velocity)
- Download: https://adoptium.net/

### Memória RAM
- **Auth Server**: 512MB - 1GB
- **Hub Server**: 1GB - 2GB
- **Game Server**: 2GB - 4GB
- **Proxy (Velocity)**: 512MB - 1GB

## 📁 Estrutura de Diretórios

```
servidor/
├── velocity/                    # Proxy Velocity
│   ├── velocity.jar
│   ├── plugins/
│   │   └── EventVelocity.jar   # Plugin do proxy (a criar)
│   └── velocity.toml           # Configuração
│
├── auth/                        # Servidor de autenticação
│   ├── paper-1.20.6.jar
│   ├── plugins/
│   │   ├── Auth.jar
│   │   └── SkinsRestorer.jar
│   └── server.properties
│
├── hub/                         # Servidor lobby
│   ├── paper-1.20.6.jar
│   ├── plugins/
│   │   ├── Hub.jar
│   │   └── ProtocolLib.jar
│   └── server.properties
│
└── game/                        # Servidor do evento
    ├── paper-1.20.6.jar
    ├── plugins/
    │   └── Game.jar
    └── server.properties
```

## ⚙️ Configuração do Velocity

### 1. velocity.toml

```toml
# Configuração básica
bind = "0.0.0.0:25577"
motd = "<aqua>Round 6 Event</aqua>"
show-max-players = 500

# Servidores
[servers]
auth = "127.0.0.1:25566"
hub = "127.0.0.1:25567"
game = "127.0.0.1:25568"

# Ordem de tentativa de conexão
try = [
  "hub"
]

[forced-hosts]
"auth.seuservidor.com" = [
  "auth"
]
"hub.seuservidor.com" = [
  "hub"
]
"game.seuservidor.com" = [
  "game"
]

[advanced]
compression-threshold = 256
compression-level = -1
login-ratelimit = 3000
connection-timeout = 5000
read-timeout = 30000
haproxy-protocol = false

[query]
enabled = false
port = 25577
```

### 2. Forwarding (Importante!)

No `velocity.toml`:
```toml
[player-info-forwarding]
mode = "modern"  # ou "legacy" para compatibilidade
secret = "GERE_UMA_CHAVE_SECRETA_AQUI"
```

Gere uma chave secreta:
```bash
openssl rand -base64 32
```

## 🔌 Configuração dos Servidores Paper

### server.properties (Todos os servidores)

```properties
# Modo online DEVE ser false (Velocity cuida da autenticação)
online-mode=false

# Porta específica de cada servidor
server-port=25566  # auth
# server-port=25567  # hub
# server-port=25568  # game

# Configurações gerais
max-players=100
view-distance=8
simulation-distance=6
```

### paper-global.yml (Todos os servidores)

```yaml
proxies:
  velocity:
    enabled: true
    online-mode: true
    secret: "MESMA_CHAVE_DO_VELOCITY"
```

### spigot.yml (Todos os servidores)

```yaml
settings:
  bungeecord: false  # IMPORTANTE: false para Velocity
```

## 📦 Plugins Necessários

### Auth Server
1. **SkinsRestorer** (v15.0.0+)
   - Download: https://www.spigotmc.org/resources/skinsrestorer.2124/
   - Permite trocar skins de jogadores piratas

### Hub Server
1. **ProtocolLib** (v5.1.0+)
   - Download: https://www.spigotmc.org/resources/protocollib.1997/
   - Necessário para aplicar skins customizadas nos NPCs

## 🚀 Ordem de Inicialização

1. **Velocity** (proxy)
2. **Auth Server**
3. **Hub Server**
4. **Game Server**

## 🔐 Configuração de Segurança

### Firewall
Apenas a porta do Velocity deve estar aberta publicamente:
- **Velocity**: 25577 (pública)
- **Auth**: 25566 (apenas localhost)
- **Hub**: 25567 (apenas localhost)
- **Game**: 25568 (apenas localhost)

### iptables (Linux)
```bash
# Permitir apenas Velocity publicamente
iptables -A INPUT -p tcp --dport 25577 -j ACCEPT

# Bloquear portas dos servidores
iptables -A INPUT -p tcp --dport 25566 -j DROP
iptables -A INPUT -p tcp --dport 25567 -j DROP
iptables -A INPUT -p tcp --dport 25568 -j DROP

# Permitir localhost
iptables -A INPUT -i lo -j ACCEPT
```

## 🧪 Testando a Configuração

### 1. Teste de Conexão Básica
```bash
# Testar se Velocity está rodando
telnet localhost 25577

# Testar servidores individuais
telnet localhost 25566  # auth
telnet localhost 25567  # hub
telnet localhost 25568  # game
```

### 2. Teste de Jogador Pirata
1. Conecte com cliente pirata (TLauncher, etc)
2. Deve ser redirecionado para `auth`
3. Use `/register senha senha`
4. Deve ser redirecionado para `hub`

### 3. Teste de Jogador Original
1. Conecte com cliente original (Minecraft comprado)
2. Deve ir direto para `hub`
3. Não precisa de autenticação

## 📝 Scripts de Inicialização

### start-velocity.sh
```bash
#!/bin/bash
cd velocity
java -Xms512M -Xmx1G -XX:+UseG1GC -XX:G1HeapRegionSize=4M \
  -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled \
  -XX:+AlwaysPreTouch -XX:MaxInlineLevel=15 \
  -jar velocity.jar
```

### start-auth.sh
```bash
#!/bin/bash
cd auth
java -Xms512M -Xmx1G -XX:+UseG1GC -XX:G1HeapRegionSize=4M \
  -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled \
  -XX:+AlwaysPreTouch \
  -jar paper-1.20.6.jar --nogui
```

### start-hub.sh
```bash
#!/bin/bash
cd hub
java -Xms1G -Xmx2G -XX:+UseG1GC -XX:G1HeapRegionSize=4M \
  -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled \
  -XX:+AlwaysPreTouch \
  -jar paper-1.20.6.jar --nogui
```

### start-game.sh
```bash
#!/bin/bash
cd game
java -Xms2G -Xmx4G -XX:+UseG1GC -XX:G1HeapRegionSize=4M \
  -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled \
  -XX:+AlwaysPreTouch \
  -jar paper-1.20.6.jar --nogui
```

## 🐛 Troubleshooting

### Problema: "Can't connect to server"
- Verifique se Velocity está rodando
- Confirme que as portas estão corretas
- Verifique firewall

### Problema: "Disconnected: Invalid player data"
- Verifique se `online-mode=false` nos servidores Paper
- Confirme que o forwarding está configurado corretamente
- Verifique se a secret key é a mesma em todos os lugares

### Problema: Jogadores não são redirecionados
- Verifique logs do Velocity
- Confirme que os servidores estão registrados corretamente
- Teste conexão direta com cada servidor

### Problema: Skins não funcionam
- Verifique se SkinsRestorer está instalado no auth
- Confirme que ProtocolLib está no hub
- Verifique logs para erros

## 📊 Monitoramento

### Logs Importantes
- **Velocity**: `velocity/logs/latest.log`
- **Auth**: `auth/logs/latest.log`
- **Hub**: `hub/logs/latest.log`
- **Game**: `game/logs/latest.log`

### Comandos Úteis
```bash
# Ver logs em tempo real
tail -f velocity/logs/latest.log

# Buscar erros
grep -i error auth/logs/latest.log

# Verificar uso de memória
ps aux | grep java
```

## 🔄 Migração de BungeeCord para Velocity

Se você já tem plugins BungeeCord, precisará adaptá-los:

1. Mudar dependência de `bungeecord-api` para `velocity-api`
2. Atualizar imports de `net.md_5.bungee` para `com.velocitypowered.api`
3. Adaptar eventos e comandos para API do Velocity
4. Recompilar plugins

**Nota**: Criaremos um plugin específico para Velocity no próximo passo.

## 📚 Recursos Adicionais

- **Velocity Docs**: https://docs.papermc.io/velocity
- **Paper Docs**: https://docs.papermc.io/paper
- **SkinsRestorer Wiki**: https://skinsrestorer.net/docs
- **ProtocolLib**: https://github.com/dmulloy2/ProtocolLib

## ✅ Checklist de Configuração

- [ ] Java 21 instalado
- [ ] Velocity baixado e configurado
- [ ] Paper 1.20.6 em todos os servidores
- [ ] Forwarding configurado (secret key)
- [ ] online-mode=false nos servidores
- [ ] SkinsRestorer no auth
- [ ] ProtocolLib no hub
- [ ] Firewall configurado
- [ ] Scripts de inicialização criados
- [ ] Teste com jogador pirata
- [ ] Teste com jogador original
- [ ] NPCs funcionando
- [ ] Skins aplicadas corretamente

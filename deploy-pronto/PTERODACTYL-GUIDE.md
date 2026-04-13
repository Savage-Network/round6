# 🦖 Guia Pterodactyl - Round 6 Server

## ⚠️ Erro Atual

```
Error: Unable to access jarfile server.jar
```

**Causa:** O Pterodactyl está configurado para executar `server.jar`, mas o arquivo não existe no servidor.

---

## 🔧 Solução por Servidor

### 1️⃣ VELOCITY (Proxy - Porta 25100)

#### Configuração do Pterodactyl:
```
Startup Command: java -Xms128M -XX:MaxRAMPercentage=95.0 -jar velocity.jar
```

#### Arquivos para fazer upload:
1. **velocity.jar** (baixar de https://papermc.io/downloads/velocity)
2. **velocity.toml** (da pasta `deploy-pronto/velocity/`)
3. **plugins/ViaVersion.jar** (da pasta `deploy-pronto/velocity/plugins/`)

#### Estrutura no Pterodactyl:
```
/
├── velocity.jar          ← Upload
├── velocity.toml         ← Upload
└── plugins/
    └── ViaVersion.jar    ← Upload
```

---

### 2️⃣ AUTH SERVER (Porta 25579)

#### Configuração do Pterodactyl:
```
Startup Command: java -Xms1G -Xmx2G -jar paper.jar --nogui
```

#### Arquivos para fazer upload:
1. **paper.jar** (renomear de paper-1.20.6-xxx.jar)
2. **eula.txt** (da pasta `deploy-pronto/auth/`)
3. **server.properties** (da pasta `deploy-pronto/auth/`)
4. **spigot.yml** (da pasta `deploy-pronto/auth/`)
5. **config/paper-global.yml** (da pasta `deploy-pronto/auth/config/`)
6. **plugins/Auth.jar** (da pasta `deploy-pronto/auth/plugins/`)
7. **plugins/ViaVersion.jar** (da pasta `deploy-pronto/auth/plugins/`)

#### Estrutura no Pterodactyl:
```
/
├── paper.jar                    ← Upload (renomear)
├── eula.txt                     ← Upload
├── server.properties            ← Upload
├── spigot.yml                   ← Upload
├── config/
│   └── paper-global.yml         ← Upload
└── plugins/
    ├── Auth.jar                 ← Upload
    └── ViaVersion.jar           ← Upload
```

#### Após primeira inicialização:
8. **plugins/Auth/config.yml** (da pasta `deploy-pronto/auth/plugins/Auth/`)

---

### 3️⃣ HUB SERVER (Porta 25578)

#### Configuração do Pterodactyl:
```
Startup Command: java -Xms1G -Xmx2G -jar paper.jar --nogui
```

#### Arquivos para fazer upload:
1. **paper.jar** (renomear de paper-1.20.6-xxx.jar)
2. **eula.txt** (da pasta `deploy-pronto/hub/`)
3. **server.properties** (da pasta `deploy-pronto/hub/`)
4. **spigot.yml** (da pasta `deploy-pronto/hub/`)
5. **config/paper-global.yml** (da pasta `deploy-pronto/hub/config/`)
6. **plugins/Hub.jar** (da pasta `deploy-pronto/hub/plugins/`)
7. **plugins/ViaVersion.jar** (da pasta `deploy-pronto/hub/plugins/`)
8. **plugins/PlaceholderAPI.jar** (da pasta `deploy-pronto/hub/plugins/`)

#### Estrutura no Pterodactyl:
```
/
├── paper.jar                    ← Upload (renomear)
├── eula.txt                     ← Upload
├── server.properties            ← Upload
├── spigot.yml                   ← Upload
├── config/
│   └── paper-global.yml         ← Upload
└── plugins/
    ├── Hub.jar                  ← Upload
    ├── ViaVersion.jar           ← Upload
    └── PlaceholderAPI.jar       ← Upload
```

#### Após primeira inicialização:
9. **plugins/Hub/config.yml** (da pasta `deploy-pronto/hub/plugins/Hub/`)

---

### 4️⃣ GAME SERVER (Porta 25580)

#### Configuração do Pterodactyl:
```
Startup Command: java -Xms2G -Xmx4G -jar paper.jar --nogui
```

#### Arquivos para fazer upload:
1. **paper.jar** (renomear de paper-1.20.6-xxx.jar)
2. **eula.txt** (da pasta `deploy-pronto/game/`)
3. **server.properties** (da pasta `deploy-pronto/game/`)
4. **spigot.yml** (da pasta `deploy-pronto/game/`)
5. **config/paper-global.yml** (da pasta `deploy-pronto/game/config/`)
6. **plugins/Game.jar** (da pasta `deploy-pronto/game/plugins/`)
7. **plugins/ViaVersion.jar** (da pasta `deploy-pronto/game/plugins/`)

#### Estrutura no Pterodactyl:
```
/
├── paper.jar                    ← Upload (renomear)
├── eula.txt                     ← Upload
├── server.properties            ← Upload
├── spigot.yml                   ← Upload
├── config/
│   └── paper-global.yml         ← Upload
└── plugins/
    ├── Game.jar                 ← Upload
    └── ViaVersion.jar           ← Upload
```

#### Após primeira inicialização:
8. **plugins/Game/config.yml** (da pasta `deploy-pronto/game/plugins/Game/`)

---

## 📝 Passo a Passo Detalhado

### Para cada servidor no Pterodactyl:

#### 1. Ajustar Startup Command
No painel do Pterodactyl, vá em **Startup** e altere o comando:

**Velocity:**
```bash
java -Xms128M -XX:MaxRAMPercentage=95.0 -jar velocity.jar
```

**Auth/Hub/Game:**
```bash
java -Xms1G -Xmx2G -jar paper.jar --nogui
```

#### 2. Fazer Upload dos Arquivos
No painel **Files**, faça upload dos arquivos listados acima.

**IMPORTANTE:** Renomeie o Paper baixado para `paper.jar`

#### 3. Criar Pastas Necessárias
Crie as pastas antes de fazer upload:
- `plugins/`
- `config/`

#### 4. Iniciar o Servidor
Inicie o servidor e aguarde a primeira inicialização.

#### 5. Upload dos Configs dos Plugins
Após a primeira inicialização, as pastas dos plugins serão criadas:
- `plugins/Auth/` (Auth Server)
- `plugins/Hub/` (Hub Server)
- `plugins/Game/` (Game Server)

Faça upload dos arquivos `config.yml` correspondentes.

#### 6. Reiniciar
Reinicie o servidor para aplicar as configurações.

---

## 🔌 Configuração de Portas no Pterodactyl

Certifique-se que as portas estão configuradas corretamente:

| Servidor | Porta | Allocation |
|----------|-------|------------|
| Velocity | 25100 | Primary |
| Hub | 25578 | Primary |
| Auth | 25579 | Primary |
| Game | 25580 | Primary |

---

## 🚀 Ordem de Inicialização

**IMPORTANTE:** Inicie nesta ordem!

1. **Auth Server** (25579) → Aguardar "Done!"
2. **Hub Server** (25578) → Aguardar "Done!"
3. **Game Server** (25580) → Aguardar "Done!"
4. **Velocity** (25100) → Aguardar inicialização

---

## 📥 Downloads Necessários

### Velocity
- Link: https://papermc.io/downloads/velocity
- Versão: 3.3.0+
- Renomear para: `velocity.jar`

### Paper (para Auth, Hub e Game)
- Link: https://papermc.io/downloads/paper
- Versão: 1.20.6
- Baixar 3 cópias
- Renomear todas para: `paper.jar`

---

## ✅ Checklist por Servidor

### Velocity
- [ ] Baixar velocity.jar
- [ ] Alterar Startup Command
- [ ] Upload velocity.jar
- [ ] Upload velocity.toml
- [ ] Criar pasta plugins/
- [ ] Upload plugins/ViaVersion.jar
- [ ] Iniciar servidor

### Auth
- [ ] Baixar paper.jar
- [ ] Alterar Startup Command
- [ ] Upload paper.jar
- [ ] Upload eula.txt
- [ ] Upload server.properties
- [ ] Upload spigot.yml
- [ ] Criar pasta config/
- [ ] Upload config/paper-global.yml
- [ ] Criar pasta plugins/
- [ ] Upload plugins/Auth.jar
- [ ] Upload plugins/ViaVersion.jar
- [ ] Iniciar servidor (primeira vez)
- [ ] Upload plugins/Auth/config.yml
- [ ] Reiniciar servidor

### Hub
- [ ] Baixar paper.jar
- [ ] Alterar Startup Command
- [ ] Upload paper.jar
- [ ] Upload eula.txt
- [ ] Upload server.properties
- [ ] Upload spigot.yml
- [ ] Criar pasta config/
- [ ] Upload config/paper-global.yml
- [ ] Criar pasta plugins/
- [ ] Upload plugins/Hub.jar
- [ ] Upload plugins/ViaVersion.jar
- [ ] Upload plugins/PlaceholderAPI.jar
- [ ] Iniciar servidor (primeira vez)
- [ ] Upload plugins/Hub/config.yml
- [ ] Reiniciar servidor

### Game
- [ ] Baixar paper.jar
- [ ] Alterar Startup Command
- [ ] Upload paper.jar
- [ ] Upload eula.txt
- [ ] Upload server.properties
- [ ] Upload spigot.yml
- [ ] Criar pasta config/
- [ ] Upload config/paper-global.yml
- [ ] Criar pasta plugins/
- [ ] Upload plugins/Game.jar
- [ ] Upload plugins/ViaVersion.jar
- [ ] Iniciar servidor (primeira vez)
- [ ] Upload plugins/Game/config.yml
- [ ] Reiniciar servidor

---

## 🐛 Troubleshooting

### "Unable to access jarfile server.jar"
**Solução:** Altere o Startup Command para o nome correto do JAR:
- Velocity: `velocity.jar`
- Outros: `paper.jar`

### "EULA not accepted"
**Solução:** Faça upload do arquivo `eula.txt` com `eula=true`

### "Failed to bind to port"
**Solução:** Verifique se a porta está correta no `server.properties` e no Pterodactyl Allocation

### "Invalid forwarding secret"
**Solução:** Certifique-se de fazer upload do `paper-global.yml` correto

### MySQL connection failed
**Solução:** Verifique se o host MySQL permite conexões externas

---

## 📊 Verificar Logs

Após iniciar cada servidor, verifique os logs no Pterodactyl:

**Velocity:**
```
[INFO] Listening on /0.0.0.0:25100
```

**Auth/Hub/Game:**
```
[INFO] Done! For help, type "help"
```

---

## 🎯 Resumo Rápido

1. **Baixar JARs** (Velocity + 3x Paper)
2. **Alterar Startup Commands** em cada servidor
3. **Upload dos arquivos** da pasta `deploy-pronto/`
4. **Iniciar na ordem:** Auth → Hub → Game → Velocity
5. **Conectar:** admin.savagemc.net:25100

---

**Pronto para usar no Pterodactyl! 🦖**

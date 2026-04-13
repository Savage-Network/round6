# 🎮 Round 6 do Rafa - Deploy Completo

> Sistema completo de Round 6 com Gamemaster, Guardas e múltiplos minigames!

## 📦 O que está incluído

Este deploy contém **TUDO** que você precisa para rodar o servidor Round 6 do Rafa:

- ✅ **3 Plugins compilados** (Auth, Hub, Game)
- ✅ **4 Servidores configurados** (Velocity, Auth, Hub, Game)
- ✅ **MySQL configurado** em todos os plugins
- ✅ **Suporte para piratas e originais**
- ✅ **Sistema de Gamemaster único**
- ✅ **Sistema de Guardas**
- ✅ **Minigame Green Light Red Light**
- ✅ **Auto-join profissional**
- ✅ **Scoreboard em tempo real**
- ✅ **Efeitos dramáticos**

## 🚀 Quick Start

### 1. Baixar JARs principais

Você precisa baixar apenas 5 arquivos:

1. [Velocity 3.3.0+](https://papermc.io/downloads/velocity) → `velocity/velocity.jar`
2. [Paper 1.20.6](https://papermc.io/downloads/paper) → `auth/server.jar`
3. [Paper 1.20.6](https://papermc.io/downloads/paper) → `hub/server.jar`
4. [Paper 1.20.6](https://papermc.io/downloads/paper) → `game/server.jar`

### 2. Iniciar servidores

**ORDEM IMPORTANTE:**

```bash
# 1. Auth Server
cd auth && ./start.sh

# 2. Hub Server
cd hub && ./start.sh

# 3. Game Server
cd game && ./start.sh

# 4. Velocity
cd velocity && ./start.sh
```

### 3. Configurar Game

Edite `game/plugins/Game/config.yml`:

```yaml
lobby-location:
  world: "world"
  x: 0
  y: 100
  z: 0

arena-location:
  world: "world"
  x: 100
  y: 100
  z: 100
```

### 4. Definir Gamemaster

```
/r6 gm <seu_nick>
```

### 5. Jogar!

Conecte em: `admin.savagemc.net:25100`

## 🎮 Funcionalidades

### Sistema de Gamemaster

O Gamemaster tem controle total do jogo através de itens interativos:

- **Menu de Jogos** - Selecionar minigame
- **Iniciar Jogo** - Começar minigame
- **Parar Jogo** - Parar minigame
- **Gerenciar Guardas** - Adicionar/remover guardas
- **Teleporte** - Teleportar para locais
- **Configurações** - Ajustar settings
- **Estatísticas** - Ver stats
- **Eliminar Jogador** - Eliminação manual

### Sistema de Guardas

Guardas ajudam o Gamemaster:

- Recebem espada e arco
- Podem eliminar trapaceiros
- Não podem ser eliminados
- Múltiplos guardas permitidos

### Minigames

#### Green Light, Red Light ✅
- Luz verde = pode se mover
- Luz vermelha = não pode se mover
- Detecção precisa de movimento
- Eliminação automática

#### Outros (Em desenvolvimento)
- Honeycomb (Biscoito de Açúcar)
- Tug of War (Cabo de Guerra)
- Marbles (Bolinhas de Gude)
- Glass Bridge (Ponte de Vidro)
- Squid Game (Jogo da Lula)

## 📝 Comandos

### Gamemaster
```
/r6 gm <player>  - Definir gamemaster
/r6 gm remove    - Remover gamemaster
```

### Guardas
```
/r6 guard add <player>     - Adicionar guarda
/r6 guard remove <player>  - Remover guarda
/r6 guard list             - Listar guardas
```

### Jogadores
```
/r6 join   - Entrar no jogo (opcional, auto-join ativo)
/r6 leave  - Sair do jogo
```

## ⚙️ Configuração

### Portas

- **Velocity**: 25100 (abrir no firewall)
- **Auth**: 25579 (interna)
- **Hub**: 25578 (interna)
- **Game**: 25580 (interna)

### MySQL

```yaml
Host: mysql52-farm1.kinghost.net:3306
Database: zrfa
User: zrfa
Password: Rafael141011
```

### Forwarding Secret

```
round6secret2024
```

## 🎯 Fluxo de Jogadores

### Jogadores Piratas
1. Conectam em `admin.savagemc.net:25100`
2. Vão para Auth
3. Fazem `/register senha senha`
4. Skin aplicada automaticamente
5. Redirecionados para Hub
6. Podem ir para Game

### Jogadores Originais
1. Conectam em `admin.savagemc.net:25100`
2. Vão para Auth
3. Redirecionados automaticamente para Hub
4. Podem ir para Game

### No Servidor Game
1. Jogador entra automaticamente no jogo
2. Aparece no lobby com scoreboard
3. Aguarda Gamemaster iniciar
4. Gamemaster seleciona minigame
5. Jogo começa!

## 📊 Plugins

### Auth.jar (4.43 MB)
- Sistema de login/register
- Sistema de skins embutido
- Detecção pirata/original
- MySQL com HikariCP
- Redirecionamento automático

### Hub.jar (4.44 MB)
- Sistema de NPCs
- Sistema de hologramas
- Scoreboard customizada
- Tags com PlaceholderAPI
- Cores hex e small caps
- Plugin hider
- MySQL com HikariCP

### Game.jar (4.69 MB)
- Round 6 do Rafa completo
- Sistema de Gamemaster
- Sistema de Guardas
- Auto-join
- Green Light Red Light
- Menu de minigames
- Scoreboard em tempo real
- Efeitos dramáticos
- MySQL com HikariCP

## 📚 Documentação

- `DEPLOY-FINAL-ROUND6-DO-RAFA.txt` - Guia completo de deploy
- `ROUND6-DO-RAFA.txt` - Guia do Round 6
- `CHECKLIST-FINAL.txt` - Checklist de verificação
- `LEIA_PRIMEIRO.txt` - Guia rápido
- `PTERODACTYL-GUIDE.md` - Guia para Pterodactyl

## 🎨 Diferenciais

1. **Sistema de Gamemaster único** - Controle total com itens
2. **Menu interativo** - Como MrBeast
3. **Múltiplos guardas** - Ajudantes do gamemaster
4. **Auto-join profissional** - Sem comandos necessários
5. **Scoreboard em tempo real** - Atualizada a cada segundo
6. **Efeitos dramáticos** - Raios, sons, títulos
7. **Cores hex e small caps** - Visual único
8. **Detecção precisa** - Movimento detectado com precisão
9. **Sistema extensível** - Fácil adicionar novos minigames
10. **Suporte piratas** - Aceita todos os jogadores

## 🔧 Troubleshooting

### Erro de conexão
- Verifique se todos os servidores estão rodando
- Verifique as portas no firewall
- Verifique o forwarding secret

### Plugin não carrega
- Verifique se o MySQL está acessível
- Verifique os logs do servidor
- Verifique se as dependências estão incluídas

### Gamemaster não recebe itens
- Use `/r6 gm <nick>` novamente
- Verifique se tem permissão `round6.admin`
- Reinicie o servidor se necessário

## 📞 Suporte

Para dúvidas ou problemas:
1. Leia a documentação completa
2. Verifique o checklist
3. Consulte os logs dos servidores

## 🎉 Créditos

**Desenvolvido por**: Rafa  
**Versão**: 1.0.0 - Round 6 do Rafa Edition  
**Data**: 13 de Abril de 2026

---

**TUDO ESTÁ PRONTO! BOA SORTE! 🚀**

# 🎮 ROUND 6 DO RAFA - BASEADO NO MRBEAST SQUID GAME

## 📋 Funcionalidades do Vídeo do MrBeast

### Características Principais:
1. **456 Jogadores** - Sistema de números únicos (001-456)
2. **Prêmio de $456,000** - Sistema de cofre dinâmico
3. **Eliminação sem morte** - Dispositivo que "estoura" quando eliminado
4. **Sets perfeitos** - Recreação fiel dos cenários
5. **Dormitório** - Sala com beliches onde jogadores descansam
6. **Uniformes** - Verde para jogadores, vermelho para guardas
7. **Votação** - Jogadores decidem continuar ou sair

---

## 🎯 JOGOS IMPLEMENTADOS (Baseado no MrBeast)

### 1. RED LIGHT, GREEN LIGHT (Luz Verde, Luz Vermelha)
**Como funciona no MrBeast:**
- Boneca gigante (Young-Hee) vira de costas
- Quando diz "Green Light" = pode se mover
- Quando diz "Red Light" = deve parar completamente
- Sensores de movimento detectam quem se move
- Dispositivo "estoura" eliminando o jogador
- Tempo: ~5 minutos
- Eliminados: ~100 jogadores

**Implementação Minecraft:**
```yaml
green-light-red-light:
  duration: 300  # 5 minutos
  detection-sensitivity: 0.1  # Sensibilidade de movimento
  green-light-duration: 10  # Segundos de luz verde
  red-light-duration: 5  # Segundos de luz vermelha
  elimination-effect: LIGHTNING  # Raio ao eliminar
  sound-effect: EXPLODE  # Som de explosão
```

### 2. HONEYCOMB (Biscoito de Açúcar/Dalgona)
**Como funciona no MrBeast:**
- Cada jogador recebe um biscoito com forma
- Formas: Triângulo, Círculo, Estrela, Guarda-chuva
- Deve cortar a forma sem quebrar
- Tempo: 10 minutos
- Quebrou = eliminado

**Implementação Minecraft:**
```yaml
honeycomb:
  duration: 600  # 10 minutos
  shapes:
    - TRIANGLE  # Mais fácil
    - CIRCLE    # Médio
    - STAR      # Difícil
    - UMBRELLA  # Muito difícil
  tool: STICK  # Agulha
  block-type: TERRACOTTA  # Bloco do biscoito
```

### 3. TUG OF WAR (Cabo de Guerra)
**Como funciona no MrBeast:**
- 2 equipes de jogadores
- Plataforma elevada
- Puxam a corda
- Equipe que cai = eliminada
- Tempo: ~5 minutos

**Implementação Minecraft:**
```yaml
tug-of-war:
  duration: 300  # 5 minutos
  team-size: 10  # Jogadores por equipe
  platform-height: 20  # Altura da plataforma
  rope-length: 10  # Comprimento da corda
```

### 4. MARBLES (Bolinhas de Gude)
**Como funciona no MrBeast:**
- Jogadores formam duplas
- Cada um recebe 10 bolinhas
- Jogam jogos de azar/habilidade
- Quem perde todas as bolinhas = eliminado
- Tempo: 15 minutos
- Jogo mais emocional

**Implementação Minecraft:**
```yaml
marbles:
  duration: 900  # 15 minutos
  marbles-per-player: 10
  games:
    - EVEN_ODD  # Par ou ímpar
    - GUESS_NUMBER  # Adivinhar número
    - CLOSEST_TO_WALL  # Mais perto da parede
```

### 5. GLASS BRIDGE (Ponte de Vidro)
**Como funciona no MrBeast:**
- Ponte com 18 pares de vidros
- Vidro temperado (seguro) vs vidro normal (quebra)
- Jogadores vão por ordem de número
- Cai = eliminado
- Tempo: 10 minutos
- Jogo mais tenso

**Implementação Minecraft:**
```yaml
glass-bridge:
  duration: 600  # 10 minutos
  bridge-length: 18  # Pares de vidro
  safe-glass: STAINED_GLASS  # Vidro seguro
  fake-glass: GLASS  # Vidro que quebra
  fall-height: 50  # Altura da queda
  order-by-number: true  # Ordem por número do jogador
```

### 6. SQUID GAME (Jogo da Lula) - FINAL
**Como funciona no MrBeast:**
- Não foi usado no vídeo (substituído por outro jogo)
- Seria PvP entre finalistas

**Implementação Minecraft:**
```yaml
squid-game:
  duration: 900  # 15 minutos
  pvp-enabled: true
  weapons: [WOOD_SWORD]
  arena-type: SQUID_SHAPE
```

---

## 🎨 SISTEMAS ESPECIAIS DO MRBEAST

### 1. Sistema de Números
- Cada jogador recebe número único (001-456)
- Número aparece:
  - No nome do jogador
  - Na scoreboard
  - No chat
  - No uniforme (skin)

### 2. Sistema de Cofre
- Começa em $0
- Cada eliminação adiciona $1,000
- Exibido em tempo real
- Vencedor leva tudo

### 3. Dormitório
- Sala com beliches
- Jogadores descansam entre jogos
- Podem conversar e formar alianças
- Votação acontece aqui

### 4. Votação
- Após cada jogo
- Opções: Continuar ou Sair
- Maioria decide
- Se sair: prêmio dividido entre sobreviventes
- Se continuar: próximo jogo

### 5. Eliminação Dramática
- Dispositivo "estoura" no jogador
- Som de explosão
- Efeitos visuais
- Mensagem no chat
- Jogador vira espectador

### 6. Guardas
- Uniformes vermelhos
- Máscaras (círculo, triângulo, quadrado)
- Não falam
- Supervisionam os jogos
- Podem eliminar trapaceiros

### 7. Gamemaster (MrBeast)
- Controle total
- Anuncia os jogos
- Inicia/para jogos
- Pode eliminar manualmente
- Vê tudo

---

## 🔧 COMANDOS NECESSÁRIOS

### Gamemaster
```
/r6 gm <player> - Definir gamemaster
/r6 start <game> - Iniciar jogo
/r6 stop - Parar jogo
/r6 eliminate <player> - Eliminar jogador
/r6 prize set <amount> - Definir prêmio
```

### Guardas
```
/r6 guard add <player> - Adicionar guarda
/r6 guard remove <player> - Remover guarda
/r6 guard mask <circle|triangle|square> - Definir máscara
```

### Jogadores
```
/r6 vote <yes|no> - Votar
/r6 stats - Ver estatísticas
/r6 number - Ver seu número
```

### Sistema
```
/r6 reset - Resetar jogo completo
/r6 config - Configurações
/r6 location set <type> - Definir locais
```

---

## 📊 SCOREBOARD (Baseado no MrBeast)

```
    ʀᴏᴜɴᴅ 6 ᴅᴏ ʀᴀꜰᴀ

ɴᴜ́ᴍᴇʀᴏ: #456
ᴊᴏɢᴀᴅᴏʀᴇꜱ ᴠɪᴠᴏꜱ: 234/456

ᴊᴏɢᴏ ᴀᴛᴜᴀʟ:
§cʟᴜᴢ ᴠᴇʀᴅᴇ, ʟᴜᴢ ᴠᴇʀᴍᴇʟʜᴀ

ᴇꜱᴛᴀᴅᴏ: §aᴇᴍ ᴀɴᴅᴀᴍᴇɴᴛᴏ
ᴛᴇᴍᴘᴏ: §e02:45

ᴄᴏꜰʀᴇ: §6$234,000

§7admin.savagemc.net
```

---

## 🎯 PRIORIDADES DE IMPLEMENTAÇÃO

### FASE 1 - ESSENCIAL (JÁ FEITO)
- [x] Sistema de Gamemaster
- [x] Sistema de Guardas
- [x] Estrutura base dos jogos
- [x] Banco de dados

### FASE 2 - JOGOS (FAZER AGORA)
- [ ] Red Light, Green Light (PRIORIDADE 1)
- [ ] Honeycomb (PRIORIDADE 2)
- [ ] Glass Bridge (PRIORIDADE 3)
- [ ] Marbles (PRIORIDADE 4)
- [ ] Tug of War (PRIORIDADE 5)

### FASE 3 - SISTEMAS
- [ ] Sistema de Números (001-456)
- [ ] Sistema de Cofre Dinâmico
- [ ] Sistema de Votação
- [ ] Dormitório/Lobby
- [ ] Eliminação Dramática

### FASE 4 - VISUAL
- [ ] Uniformes (Skins)
- [ ] Scoreboard Completa
- [ ] Efeitos Especiais
- [ ] Sons Customizados
- [ ] Hologramas

### FASE 5 - EXTRAS
- [ ] Sistema de Câmeras
- [ ] Estatísticas Completas
- [ ] Replay System
- [ ] Achievements
- [ ] Leaderboards

---

## 💡 DIFERENCIAIS DO NOSSO SISTEMA

1. **Multiplayer Real** - Não é single player como alguns servidores
2. **Fiel ao MrBeast** - Baseado no vídeo real, não apenas na série
3. **Sistema de Números** - Cada jogador tem identidade única
4. **Votação Democrática** - Jogadores decidem o rumo
5. **Cofre Dinâmico** - Prêmio aumenta com eliminações
6. **Guardas Interativos** - Não são apenas NPCs
7. **Gamemaster Poderoso** - Controle total profissional
8. **Efeitos Cinematográficos** - Eliminações dramáticas
9. **Dormitório Social** - Interação entre jogos
10. **Stats Completas** - Tracking de tudo

---

**Desenvolvido por: Rafa**
**Inspirado em: MrBeast Squid Game ($456,000 Squid Game In Real Life!)**
**Versão: 2.0.0 - MrBeast Edition**

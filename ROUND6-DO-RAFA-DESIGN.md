# 🎮 ROUND 6 DO RAFA - DESIGN DOCUMENT

## Visão Geral
Sistema completo de Round 6 inspirado no MrBeast, com funcionalidades únicas e profissionais.

---

## 🎯 Funcionalidades Principais

### 1. GAMEMASTER System
**Descrição**: Jogador com controle total do jogo

**Permissões**:
- `/r6 gamemaster <player>` - Definir gamemaster
- `/r6 gamemaster remove` - Remover gamemaster

**Itens do Gamemaster**:
- **Slot 0**: Menu de Jogos (Nether Star) - Selecionar minigame
- **Slot 1**: Iniciar Jogo (Lime Dye) - Começar minigame selecionado
- **Slot 2**: Parar Jogo (Red Dye) - Parar minigame atual
- **Slot 3**: Gerenciar Guardas (Iron Sword) - Adicionar/remover guardas
- **Slot 4**: Teleporte (Ender Pearl) - Teleportar para locais
- **Slot 5**: Configurações (Comparator) - Ajustar configurações
- **Slot 6**: Estatísticas (Book) - Ver stats do jogo
- **Slot 8**: Eliminar Jogador (TNT) - Eliminar manualmente

### 2. Sistema de Guardas
**Descrição**: Jogadores que ajudam o Gamemaster

**Comandos**:
- `/r6 guard add <player>` - Adicionar guarda
- `/r6 guard remove <player>` - Remover guarda
- `/r6 guard list` - Listar guardas

**Itens dos Guardas**:
- **Slot 0**: Espada de Ferro - Eliminar jogadores
- **Slot 1**: Arco - Eliminar à distância
- **Slot 8**: 64 Flechas

**Habilidades**:
- Podem eliminar jogadores que trapaceiam
- Não podem ser eliminados
- Têm permissão para voar (opcional)

### 3. Sistema de Números
**Descrição**: Cada jogador recebe um número único (001-456)

**Funcionalidades**:
- Número exibido no nome do jogador
- Número na scoreboard
- Número no chat
- Uniforme verde com número

### 4. Minigames

#### 4.1 Green Light, Red Light (Luz Verde, Luz Vermelha)
- **Objetivo**: Chegar ao final sem ser pego se movendo
- **Mecânica**: 
  - Luz verde = pode se mover
  - Luz vermelha = não pode se mover
  - Movimento detectado = eliminação
- **Tempo**: 3 minutos
- **Itens**: Nenhum

#### 4.2 Honeycomb (Biscoito de Açúcar)
- **Objetivo**: Cortar a forma sem quebrar
- **Mecânica**:
  - Cada jogador recebe um biscoito (bloco)
  - Deve quebrar apenas a forma
  - Quebrar fora = eliminação
- **Tempo**: 10 minutos
- **Itens**: Agulha (stick)

#### 4.3 Tug of War (Cabo de Guerra)
- **Objetivo**: Puxar a equipe adversária
- **Mecânica**:
  - 2 equipes
  - Puxar corda (minigame especial)
  - Equipe que cai = eliminada
- **Tempo**: 5 minutos
- **Itens**: Nenhum

#### 4.4 Marbles (Bolinhas de Gude)
- **Objetivo**: Ganhar todas as bolinhas do parceiro
- **Mecânica**:
  - Duplas
  - Jogos de azar/habilidade
  - Quem perde = eliminado
- **Tempo**: 15 minutos
- **Itens**: 10 bolinhas (snowballs)

#### 4.5 Glass Bridge (Ponte de Vidro)
- **Objetivo**: Atravessar a ponte
- **Mecânica**:
  - Vidro temperado vs vidro normal
  - Vidro normal quebra = morte
  - Ordem de jogadores por número
- **Tempo**: 10 minutos
- **Itens**: Nenhum

#### 4.6 Squid Game (Jogo da Lula)
- **Objetivo**: Chegar ao centro
- **Mecânica**:
  - Atacante vs Defensor
  - PvP habilitado
  - Último vivo vence
- **Tempo**: 15 minutos
- **Itens**: Espada de madeira

### 5. Sistema de Dinheiro
**Descrição**: Cofre com prêmio acumulado

**Funcionalidades**:
- Cada jogador eliminado = +100.000 won
- Cofre exibido na scoreboard
- Vencedor leva tudo
- Integração com economia (Vault)

### 6. Sistema de Votação
**Descrição**: Jogadores votam para continuar ou sair

**Mecânica**:
- Após cada minigame
- Maioria decide
- Se sair = prêmio dividido
- Se continuar = próximo jogo

**Comandos**:
- `/r6 vote start` - Iniciar votação
- `/r6 vote yes` - Votar para continuar
- `/r6 vote no` - Votar para sair

### 7. Eliminação Dramática
**Descrição**: Efeitos especiais ao morrer

**Efeitos**:
- Raio no local
- Som de explosão
- Partículas de sangue (redstone)
- Mensagem dramática
- Câmera lenta (opcional)

### 8. Sistema de Câmeras
**Descrição**: Espectadores podem assistir

**Funcionalidades**:
- Modo espectador automático
- Teleporte entre jogadores
- Menu de câmeras
- Visão de diferentes ângulos

### 9. Estatísticas Completas
**Descrição**: Tracking de tudo

**Stats Salvos**:
- Jogos jogados
- Vitórias
- Eliminações
- Dinheiro ganho
- Tempo jogado
- Minigames vencidos
- Taxa de sobrevivência

### 10. Sistema de Uniformes
**Descrição**: Skins automáticas

**Uniformes**:
- Jogadores: Uniforme verde com número
- Guardas: Uniforme vermelho com máscara
- Gamemaster: Uniforme preto especial
- VIPs: Máscaras douradas

---

## 🎨 Interface

### Scoreboard
```
    ʀᴏᴜɴᴅ 6 ᴅᴏ ʀᴀꜰᴀ

ɴᴜ́ᴍᴇʀᴏ: 456
ᴊᴏɢᴀᴅᴏʀᴇꜱ: 10/456
ᴠɪᴠᴏꜱ: 7

ᴊᴏɢᴏ: ʟᴜᴢ ᴠᴇʀᴅᴇ
ᴇꜱᴛᴀᴅᴏ: ᴇᴍ ᴀɴᴅᴀᴍᴇɴᴛᴏ
ᴛᴇᴍᴘᴏ: 02:45

ᴄᴏꜰʀᴇ: 45.6M ₩

admin.savagemc.net
```

### Tab List
```
[001] zrwfaxd
[002] player2
[GUARDA] guard1
[GM] Rafa
```

---

## 🔧 Comandos

### Gamemaster
- `/r6 gm <player>` - Definir gamemaster
- `/r6 gm remove` - Remover gamemaster

### Guardas
- `/r6 guard add <player>` - Adicionar guarda
- `/r6 guard remove <player>` - Remover guarda
- `/r6 guard list` - Listar guardas

### Jogo
- `/r6 start <game>` - Iniciar minigame
- `/r6 stop` - Parar minigame
- `/r6 reset` - Resetar jogo

### Jogadores
- `/r6 eliminate <player>` - Eliminar jogador
- `/r6 revive <player>` - Reviver jogador
- `/r6 number <player> <number>` - Definir número

### Configurações
- `/r6 config <key> <value>` - Configurar
- `/r6 location set <type>` - Definir localização
- `/r6 prize set <amount>` - Definir prêmio

### Estatísticas
- `/r6 stats [player]` - Ver estatísticas
- `/r6 top <stat>` - Ver ranking

---

## 📊 Configuração

```yaml
round6:
  # Gamemaster
  gamemaster:
    fly: true
    invulnerable: true
    
  # Guardas
  guards:
    max: 10
    fly: false
    damage-players: true
    
  # Números
  numbers:
    min: 1
    max: 456
    auto-assign: true
    
  # Dinheiro
  money:
    per-elimination: 100000
    currency: "₩"
    vault-integration: true
    
  # Votação
  voting:
    enabled: true
    time: 60
    required-percentage: 50
    
  # Efeitos
  effects:
    elimination:
      lightning: true
      explosion-sound: true
      particles: true
      slow-motion: false
      
  # Minigames
  minigames:
    green-light-red-light:
      time: 180
      detection-sensitivity: 0.1
      
    honeycomb:
      time: 600
      shapes: [triangle, circle, star, umbrella]
      
    tug-of-war:
      time: 300
      team-size: 10
      
    marbles:
      time: 900
      marbles-per-player: 10
      
    glass-bridge:
      time: 600
      bridge-length: 18
      
    squid-game:
      time: 900
      pvp: true
```

---

## 🎯 Próximos Passos

1. ✅ Criar estrutura base
2. ✅ Implementar GameMaster
3. ✅ Implementar Guardas
4. ✅ Criar Green Light Red Light
5. ⏳ Criar outros minigames
6. ⏳ Sistema de números
7. ⏳ Sistema de dinheiro
8. ⏳ Sistema de votação
9. ⏳ Efeitos dramáticos
10. ⏳ Sistema de câmeras
11. ⏳ Estatísticas completas
12. ⏳ Sistema de uniformes

---

## 🚀 Diferenciais do Round 6 do Rafa

1. **Sistema de Gamemaster** - Controle total e profissional
2. **Múltiplos Minigames** - 6 jogos diferentes
3. **Sistema de Guardas** - Ajudantes do gamemaster
4. **Números Únicos** - Cada jogador tem identidade
5. **Cofre Dinâmico** - Prêmio aumenta com eliminações
6. **Votação Democrática** - Jogadores decidem continuar
7. **Efeitos Cinematográficos** - Eliminações dramáticas
8. **Sistema de Câmeras** - Espectadores profissionais
9. **Stats Completas** - Tracking de tudo
10. **Uniformes Automáticos** - Visual profissional

---

**Desenvolvido por: Rafa**
**Versão: 1.0.0 - Round 6 do Rafa Edition**

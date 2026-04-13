# Jogos do Round 6 - Documentação

## Sistema de Explicação

Antes de cada jogo começar, há uma fase de explicação que:
- Mostra o nome do jogo em destaque
- Exibe regras linha por linha com efeitos sonoros
- Dá tempo para os jogadores lerem e se prepararem
- Conta down antes do início

## Jogos Implementados

### 1. Luz Vermelha, Luz Verde (Red Light, Green Light)

**Objetivo:** Chegar até a linha de chegada sem ser pego se movendo durante a luz vermelha.

**Regras:**
- Quando a luz está VERDE, você pode se mover
- Quando a luz está VERMELHA, você NÃO pode se mover
- NÃO pode mexer nem a câmera durante a luz vermelha
- Movimento de posição ou câmera resulta em eliminação
- Tempo limite para chegar ao fim

**Mecânicas:**
- Detecção de movimento de posição (X, Y, Z)
- Detecção de movimento de câmera (Yaw, Pitch)
- Velocidade de movimento zerada durante luz vermelha
- Efeitos visuais e sonoros para cada mudança de luz
- Raio detecta jogadores que se movem

**Dificuldade:** ⭐⭐⭐⭐ (Muito Difícil)
- Não pode mexer absolutamente nada durante a luz vermelha
- Sensibilidade alta de detecção

---

### 2. Biscoito de Açúcar (Honeycomb)

**Status:** Em desenvolvimento

**Objetivo:** Extrair a forma do biscoito sem quebrá-lo.

**Regras:**
- Cada jogador recebe um biscoito com uma forma
- Use uma agulha para cortar cuidadosamente
- Se o biscoito quebrar, você é eliminado
- Complete dentro do tempo limite

---

### 3. Cabo de Guerra (Tug of War)

**Objetivo:** Puxar a corda e fazer o time adversário cair.

**Regras:**
- Jogadores são divididos em 2 times aleatórios
- Cada time deve puxar a corda para seu lado
- O time que acumular mais poder vence
- Time perdedor é eliminado completamente

**Mecânicas:**
- Sistema de poder acumulativo
- Movimento dos jogadores aumenta o poder do time
- Barra de progresso visual
- Diferença de 100 pontos determina o vencedor

**Dificuldade:** ⭐⭐ (Médio)
- Depende de trabalho em equipe
- Movimento constante aumenta chances

---

### 4. Bolinhas de Gude (Marbles)

**Objetivo:** Ganhar todas as bolinhas do seu parceiro.

**Regras:**
- Jogadores são emparelhados aleatoriamente
- Cada jogador recebe 10 bolinhas
- Jogue com seu parceiro
- Quem ficar sem bolinhas é eliminado
- Apenas um do par pode sobreviver

**Mecânicas:**
- Sistema de emparelhamento aleatório
- Jogador ímpar avança automaticamente
- Tempo limite para jogar
- Inventário com bolinhas

**Dificuldade:** ⭐⭐⭐ (Difícil)
- Decisão moral difícil
- Apenas um sobrevive

---

### 5. Ponte de Vidro (Glass Bridge)

**Objetivo:** Atravessar a ponte escolhendo os vidros corretos.

**Regras:**
- Ponte com 18 pares de vidros
- Um vidro é temperado (seguro)
- Outro é comum (quebra e você cai)
- Escolha com cuidado qual pisar
- Observe os jogadores à frente

**Mecânicas:**
- Geração aleatória de vidros seguros
- Detecção de pisada em vidro
- Vidro quebra ao pisar no errado
- Efeito de queda e eliminação

**Dificuldade:** ⭐⭐⭐⭐ (Muito Difícil)
- 50% de chance em cada passo
- Jogadores da frente têm desvantagem

---

### 6. Jogo da Lula (Squid Game)

**Status:** Em desenvolvimento

**Objetivo:** Jogo final - atacantes vs defensores.

**Regras:**
- Atacantes devem chegar ao círculo
- Defensores devem impedir
- Combate físico permitido
- Último jogador em pé vence

---

## Sistema de Progressão

Os jogos são executados em sequência:

1. **Jogo 1:** Luz Vermelha, Luz Verde
2. **Jogo 2:** Biscoito de Açúcar (em desenvolvimento)
3. **Jogo 3:** Cabo de Guerra
4. **Jogo 4:** Bolinhas de Gude
5. **Jogo 5:** Ponte de Vidro
6. **Jogo 6:** Jogo da Lula (em desenvolvimento)

Apenas os sobreviventes de cada jogo avançam para o próximo.

---

## Efeitos Visuais e Sonoros

### Eliminação
- Raio no local do jogador
- Som de explosão
- Mensagem de broadcast
- Morte instantânea

### Vitória
- Title de "PASSOU!"
- Som de level up
- Mensagem de sucesso

### Explicação
- Title com nome do jogo
- Sons de nota musical
- Mensagens formatadas
- Countdown antes do início

---

## Comandos

### Admin
- `/start` - Inicia o Round 6 (Jogo 1)
- `/stop` - Para o jogo atual

### Automático
- Jogos avançam automaticamente após conclusão
- Sobreviventes são teleportados para o próximo jogo
- Sistema detecta quando há apenas 1 vencedor

---

## Configuração

### Localizações
Os jogos usam localizações configuráveis:

```java
// Jogo 1: Luz Vermelha, Luz Verde
Location start = new Location(world, 0, 64, 0);
Location finish = new Location(world, 0, 64, 50);

// Jogo 3: Cabo de Guerra
Location center = new Location(world, 0, 64, 0);

// Jogo 5: Ponte de Vidro
Location bridgeStart = new Location(world, 0, 64, 0);
```

### Tempos
- Explicação: ~20 segundos
- Jogo 1: 5 minutos (6000 ticks)
- Jogo 3: Até diferença de 100 pontos
- Jogo 4: 5 minutos (6000 ticks)
- Jogo 5: 5 minutos (6000 ticks)

---

## Estatísticas

O sistema rastreia:
- Vitórias
- Derrotas
- Abates
- Mortes
- K/D Ratio
- Winrate

Acessível via `/acc <jogador> stats`

---

## Troubleshooting

### Jogo não inicia
- Verifique se há pelo menos 2 jogadores
- Use `/start` para iniciar manualmente

### Jogadores não são eliminados
- Verifique se o jogo está ativo
- Verifique logs do console

### Detecção de movimento muito sensível
- Ajuste os valores de threshold no código
- Luz Vermelha: 0.05 para posição, 1.0 para câmera

### Vidros não quebram
- Verifique se o listener está registrado
- Verifique se os blocos são GLASS

---

## Melhorias Futuras

- [ ] Implementar Jogo 2: Biscoito de Açúcar
- [ ] Implementar Jogo 6: Jogo da Lula
- [ ] Sistema de apostas entre jogadores
- [ ] Replay de eliminações
- [ ] Estatísticas por jogo
- [ ] Leaderboard global
- [ ] Prêmios para vencedores
- [ ] Modos de dificuldade
- [ ] Jogos customizáveis
- [ ] Sistema de espectador melhorado

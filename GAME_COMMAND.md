# Comando /game - Documentação

## Visão Geral

O comando `/game` permite que administradores escolham qual minigame do Round 6 será jogado, ao invés de seguir uma progressão automática.

## Permissão

- `round6.admin`

## Sintaxe

```
/game <modalidade>
/game list
```

## Aliases

- `/jogo`
- `/minigame`

## Modalidades Disponíveis

### Por Número

```bash
/game 1    # Luz Vermelha, Luz Verde
/game 2    # Biscoito de Açúcar (em breve)
/game 3    # Cabo de Guerra
/game 4    # Bolinhas de Gude
/game 5    # Ponte de Vidro
/game 6    # Jogo da Lula (em breve)
```

### Por Nome (Português)

```bash
/game redlight    # ou /game red, /game luz
/game honeycomb   # ou /game biscoito, /game açucar
/game tug         # ou /game cabo, /game corda
/game marbles     # ou /game bolinhas, /game gude
/game glass       # ou /game ponte, /game vidro
/game squid       # ou /game lula
```

### Listar Jogos

```bash
/game list    # ou /game lista
```

Mostra todos os jogos disponíveis com status de implementação.

## Exemplos de Uso

### Iniciar Luz Vermelha, Luz Verde

```bash
/game 1
# ou
/game redlight
# ou
/game luz
```

### Iniciar Cabo de Guerra

```bash
/game 3
# ou
/game tug
# ou
/game cabo
```

### Ver Lista de Jogos

```bash
/game list
```

Saída:
```
§c§l=============================
§e§lJOGOS DO ROUND 6
§c§l=============================
§71. §cLuz Vermelha, §aLuz Verde §8- §a✓ Disponível
§72. §6Biscoito de Açúcar §8- §c✗ Em breve
§73. §eCabo de Guerra §8- §a✓ Disponível
§74. §7Bolinhas de Gude §8- §a✓ Disponível
§75. §bPonte de Vidro §8- §a✓ Disponível
§76. §5Jogo da Lula §8- §c✗ Em breve
§c§l=============================
```

## Fluxo de Jogo

1. Admin usa `/game <modalidade>`
2. Sistema verifica se há jogo em andamento
3. Sistema verifica se há pelo menos 2 jogadores
4. Sistema verifica se o jogo está implementado
5. Mostra mensagem de início
6. Aguarda 3 segundos
7. Inicia a explicação do jogo
8. Jogo começa após explicação
9. Jogo termina automaticamente após tempo limite ou quando há vencedor
10. Sistema mostra resultado final

## Mensagens

### Sucesso

```
§aIniciando Luz Vermelha, Luz Verde...

§a§l=============================
§e§lROUND 6 - LUZ VERMELHA, LUZ VERDE
§7Jogadores: §f10
§a§l=============================
```

### Erros

**Jogo em andamento:**
```
§cJá existe um jogo em andamento!
§cUse /stop para parar o jogo atual.
```

**Poucos jogadores:**
```
§cMínimo de 2 jogadores necessários!
```

**Jogo não implementado:**
```
§cEste jogo ainda não foi implementado!
```

**Comando inválido:**
```
§c§l=============================
§e§lCOMANDO /GAME
§c§l=============================
§fUso: §7/game <modalidade>

§fModalidades disponíveis:
§7▸ §e1 §7ou §eredlight §8- §7Luz Vermelha, Luz Verde
§7▸ §e2 §7ou §ehoneycomb §8- §7Biscoito de Açúcar §c(Em breve)
§7▸ §e3 §7ou §etug §8- §7Cabo de Guerra
§7▸ §e4 §7ou §emarbles §8- §7Bolinhas de Gude
§7▸ §e5 §7ou §eglass §8- §7Ponte de Vidro
§7▸ §e6 §7ou §esquid §8- §7Jogo da Lula §c(Em breve)

§fOutros comandos:
§7▸ §e/game list §8- §7Lista todos os jogos
§7▸ §e/stop §8- §7Para o jogo atual
§c§l=============================
```

## Diferenças do /start

### Comando /start (Antigo)
- Iniciava progressão automática de jogos
- Jogadores passavam por todos os jogos em sequência
- Não permitia escolher jogo específico

### Comando /game (Novo)
- Permite escolher jogo específico
- Jogo termina após conclusão (não avança automaticamente)
- Mais flexível para testes e eventos específicos
- Pode jogar o mesmo jogo múltiplas vezes

## Integração com Outros Comandos

### /stop
Para o jogo atual iniciado com `/game`:
```bash
/stop
```

### /definirguarda
Funciona normalmente durante jogos iniciados com `/game`:
```bash
/definirguarda Steve
```

## Jogos Implementados

### ✓ Disponíveis

1. **Luz Vermelha, Luz Verde** - Totalmente funcional
3. **Cabo de Guerra** - Totalmente funcional
4. **Bolinhas de Gude** - Totalmente funcional
5. **Ponte de Vidro** - Totalmente funcional

### ✗ Em Desenvolvimento

2. **Biscoito de Açúcar** - Planejado
6. **Jogo da Lula** - Planejado

## Notas Técnicas

### Verificações
- Verifica se há jogo em andamento
- Verifica número mínimo de jogadores (2)
- Verifica se jogo está implementado
- Valida permissões do executor

### Timeout
Cada jogo tem seu próprio timeout:
- Luz Vermelha, Luz Verde: 5 minutos
- Cabo de Guerra: Até diferença de 100 pontos
- Bolinhas de Gude: 5 minutos
- Ponte de Vidro: 5 minutos

### Finalização
Jogo termina quando:
- Tempo limite é atingido
- Resta apenas 1 jogador (vencedor)
- Todos os jogadores são eliminados
- Admin usa `/stop`

## Troubleshooting

### Comando não funciona
- Verifique permissão `round6.admin`
- Verifique se o comando está registrado no plugin.yml

### Jogo não inicia
- Verifique se há pelo menos 2 jogadores online
- Verifique se não há outro jogo em andamento
- Use `/stop` para parar jogo atual se necessário

### Jogo não termina
- Use `/stop` para forçar parada
- Verifique logs do console para erros
- Reinicie o servidor se necessário

### Jogadores não são teleportados
- Verifique se o mundo "world" existe
- Verifique coordenadas no código
- Ajuste localizações conforme seu servidor

## Exemplos de Uso em Eventos

### Evento Completo (Todos os Jogos)
```bash
/game 1    # Luz Vermelha, Luz Verde
# Aguardar término
/game 3    # Cabo de Guerra
# Aguardar término
/game 4    # Bolinhas de Gude
# Aguardar término
/game 5    # Ponte de Vidro
# Vencedor final!
```

### Evento Rápido (Apenas 1 Jogo)
```bash
/game 1    # Apenas Luz Vermelha, Luz Verde
# Vencedor é declarado ao final
```

### Torneio (Melhor de 3)
```bash
/game 1    # Rodada 1
/game 1    # Rodada 2
/game 1    # Rodada 3 (desempate)
```

### Teste de Jogo
```bash
/game glass    # Testar Ponte de Vidro
/stop          # Parar para ajustes
/game glass    # Testar novamente
```

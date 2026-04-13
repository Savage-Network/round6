# 📊 PLACEHOLDERS - ROUND 6 DO RAFA

## 🎯 Todos os Placeholders Disponíveis

### PlaceholderAPI Integration
Para usar os placeholders, você precisa ter o PlaceholderAPI instalado no servidor Hub.

---

## 📋 Lista Completa de Placeholders

### 🏆 Rank System

#### `%round6_rank%`
**Descrição:** Nome do rank do jogador  
**Retorno:** `DEFAULT`, `GAMEMASTER`, `GUARDA`, `CREATOR`  
**Exemplo:** `DEFAULT`

#### `%round6_rank_prefix%` ou `%round6_prefix%`
**Descrição:** Prefixo do rank formatado com cores  
**Retorno:** String com códigos de cor  
**Exemplos:**
- DEFAULT: `` (vazio)
- GAMEMASTER: `§x§f§f§c§0§c§b§oGameMaster `
- GUARDA: `§x§3§D§3§D§3§D§lGuarda `
- CREATOR: `§x§1§1§1§1§8§4§lCreator `

#### `%round6_rank_color%` ou `%round6_color%`
**Descrição:** Cor do rank  
**Retorno:** Código de cor  
**Exemplos:**
- DEFAULT: `§7`
- GAMEMASTER: `§x§f§f§c§0§c§b`
- GUARDA: `§x§3§D§3§D§3§D`
- CREATOR: `§x§1§1§1§1§8§4`

#### `%round6_rank_priority%`
**Descrição:** Prioridade do rank (1 = mais alto, 5 = mais baixo)  
**Retorno:** Número de 1 a 5  
**Exemplos:**
- CREATOR: `1`
- GUARDA: `2`
- GAMEMASTER: `3`
- DEFAULT: `5`

#### `%round6_tag%`
**Descrição:** Tag completa do rank (prefix + nome)  
**Retorno:** String formatada  
**Exemplos:**
- `§x§1§1§1§1§8§4§lCreator CREATOR `
- `§x§f§f§c§0§c§b§oGameMaster GAMEMASTER `

---

### 🔢 Number System (001-456)

#### `%round6_number%`
**Descrição:** Número do jogador no Round 6 (001-456)  
**Retorno:** Número formatado com 3 dígitos  
**Exemplos:** `001`, `042`, `456`  
**Nota:** Retorna vazio se o jogador não tiver número

#### `%round6_number_prefix%`
**Descrição:** Prefixo do número formatado com cores  
**Retorno:** `§x§0§6§4§0§2§B[§f001§x§0§6§4§0§2§B] `  
**Exemplo:** `[001] ` (com cores)  
**Nota:** Retorna vazio se o jogador não tiver número

---

### 👤 Display Name

#### `%round6_display_name%`
**Descrição:** Nome completo do jogador com rank e número  
**Retorno:** String formatada completa  
**Exemplos:**
- DEFAULT com número: `[001] Notch`
- CREATOR: `§x§1§1§1§1§8§4§lCreator §x§1§1§1§1§8§4Notch`
- GAMEMASTER: `§x§f§f§c§0§c§b§oGameMaster §x§f§f§c§0§c§bAlex`
- GUARDA: `§x§3§D§3§D§3§D§lGuarda §x§3§D§3§D§3§DSteve`

---

### 📊 Estatísticas do Round 6

#### `%round6_wins%`
**Descrição:** Total de vitórias no Round 6  
**Retorno:** Número inteiro  
**Exemplo:** `5`

#### `%round6_losses%`
**Descrição:** Total de derrotas no Round 6  
**Retorno:** Número inteiro  
**Exemplo:** `12`

#### `%round6_kills%`
**Descrição:** Total de kills no Round 6  
**Retorno:** Número inteiro  
**Exemplo:** `23`

#### `%round6_deaths%`
**Descrição:** Total de deaths no Round 6  
**Retorno:** Número inteiro  
**Exemplo:** `15`

#### `%round6_kdr%`
**Descrição:** K/D Ratio (kills dividido por deaths)  
**Retorno:** Número decimal com 2 casas  
**Exemplo:** `1.53`  
**Nota:** Retorna `0.00` se deaths = 0

#### `%round6_winrate%`
**Descrição:** Taxa de vitória em porcentagem  
**Retorno:** Número decimal com 1 casa + símbolo %  
**Exemplo:** `41.7%`  
**Nota:** Retorna `0.0%` se não houver jogos

---

## 💡 Exemplos de Uso

### Scoreboard
```yaml
lines:
  - "&c&l⬛ ROUND 6 ⬛"
  - ""
  - "&7Rank: %round6_rank%"
  - "&7Número: %round6_number%"
  - ""
  - "&7Vitórias: &f%round6_wins%"
  - "&7Derrotas: &f%round6_losses%"
  - "&7K/D: &f%round6_kdr%"
  - "&7Winrate: &f%round6_winrate%"
  - ""
  - "&7rip.rafa.event"
```

### Chat Format
```yaml
format: "%round6_display_name%&7: &f{message}"
```

### Tab List
```yaml
header: "&c&l⬛ ROUND 6 ⬛"
footer: "&7Seu rank: %round6_rank%"
```

### Hologram
```yaml
lines:
  - "%round6_display_name%"
  - "&7Vitórias: &f%round6_wins%"
  - "&7K/D: &f%round6_kdr%"
```

---

## 🔧 Como Usar

### 1. Instalar PlaceholderAPI
```bash
# Download
wget https://ci.extendedclip.com/job/PlaceholderAPI/lastSuccessfulBuild/artifact/build/libs/PlaceholderAPI-2.11.5.jar

# Copiar para plugins
cp PlaceholderAPI-2.11.5.jar deploy-pronto/hub/plugins/
```

### 2. Verificar Expansão
```bash
# No console ou in-game
/papi info round6
```

### 3. Testar Placeholders
```bash
# No jogo
/papi parse me %round6_rank%
/papi parse me %round6_display_name%
/papi parse me %round6_wins%
```

---

## 📝 Notas Importantes

### Disponibilidade
- ✅ Todos os placeholders funcionam apenas no servidor **Hub**
- ❌ Não funcionam no Auth ou Game (não têm PlaceholderAPI)
- ✅ Requerem que o jogador tenha uma conta carregada

### Performance
- Cache automático pelo PlaceholderAPI
- Atualização em tempo real
- Sem impacto significativo no servidor

### Fallback
- Se o jogador não tiver conta: retorna string vazia
- Se o placeholder não existir: retorna `null`
- Se o PlaceholderAPI não estiver instalado: placeholders não funcionam

---

## 🎨 Cores Hexadecimais

### Como Usar
Os placeholders já retornam cores hexadecimais formatadas.

### Formato
```
§x§R§R§G§G§B§B
```

### Exemplos
- CREATOR: `#111184` → `§x§1§1§1§1§8§4`
- GAMEMASTER: `#ffc0cb` → `§x§f§f§c§0§c§b`
- GUARDA: `#3D3D3D` → `§x§3§D§3§D§3§D`

---

## 🔗 Integração com Outros Plugins

### DeluxeMenus
```yaml
item:
  material: PLAYER_HEAD
  name: "%round6_display_name%"
  lore:
    - "&7Rank: %round6_rank%"
    - "&7Vitórias: %round6_wins%"
```

### FeatherBoard
```yaml
board:
  title: "&c&l⬛ ROUND 6 ⬛"
  lines:
    - "%round6_display_name%"
    - "&7K/D: %round6_kdr%"
```

### EssentialsX Chat
```yaml
format: "%round6_display_name%&7: &f{MESSAGE}"
```

---

**Desenvolvido por: Rafa**  
**Data: 13/04/2026**  
**Versão: 1.0.0**  
**Total de Placeholders: 15**

# 📊 PLACEHOLDERS - RESUMO RÁPIDO

## Total: 15 Placeholders

### 🏆 Rank (5)
```
%round6_rank%              → DEFAULT, GAMEMASTER, GUARDA, CREATOR
%round6_prefix%            → Prefixo formatado com cores
%round6_color%             → Cor do rank
%round6_rank_priority%     → 1-5 (1 = mais alto)
%round6_tag%               → Tag completa
```

### 🔢 Número (2)
```
%round6_number%            → 001-456
%round6_number_prefix%     → [001] formatado
```

### 👤 Display (1)
```
%round6_display_name%      → Nome completo com rank e número
```

### 📊 Estatísticas (7)
```
%round6_wins%              → Vitórias
%round6_losses%            → Derrotas
%round6_kills%             → Kills
%round6_deaths%            → Deaths
%round6_kdr%               → K/D Ratio (0.00)
%round6_winrate%           → Taxa de vitória (0.0%)
```

---

## 💡 Exemplos Rápidos

### Scoreboard
```yaml
- "&7Rank: %round6_rank%"
- "&7Número: %round6_number%"
- "&7Vitórias: %round6_wins%"
- "&7K/D: %round6_kdr%"
```

### Chat
```yaml
format: "%round6_display_name%&7: &f{message}"
```

### Tab
```yaml
header: "&c&l⬛ ROUND 6 ⬛"
footer: "&7Rank: %round6_rank% | Vitórias: %round6_wins%"
```

---

## 🔧 Instalação

1. Instale PlaceholderAPI no Hub
2. Reinicie o servidor
3. Use `/papi parse me %round6_rank%` para testar

---

**Documentação completa:** `PLACEHOLDERS-ROUND6.md`

# Sistema de Ranks - Round 6 do Rafa

## Visão Geral

Sistema completo de ranks com integração ao PlaceholderAPI, tags customizadas e hierarquia de permissões.

## Ranks Disponíveis

### 1. DEFAULT (Membro)
- **Nome**: Membro
- **Cor**: `§7` (Cinza)
- **Prefix**: `§7` (sem prefix visível)
- **Prioridade**: 5 (mais baixa)
- **Descrição**: Rank padrão para todos os jogadores

### 2. GAMEMASTER
- **Nome**: GameMaster
- **Cor**: `§x§f§f§c§0§c§b` (Rosa claro)
- **Prefix**: `§x§f§f§c§0§c§b§o` (Rosa claro itálico)
- **Prioridade**: 3
- **Descrição**: Moderadores do evento

### 3. GUARDA
- **Nome**: Guarda
- **Cor**: `§x§3§D§3§D§3§D` (Cinza escuro)
- **Prefix**: `§x§3§D§3§D§3§D§l` (Cinza escuro negrito)
- **Prioridade**: 2
- **Descrição**: Guardas do Round 6

### 4. CREATOR
- **Nome**: Creator
- **Cor**: `§x§1§1§1§1§8§4` (Azul escuro)
- **Prefix**: `§x§1§1§1§1§8§4§l` (Azul escuro negrito)
- **Prioridade**: 1 (mais alta)
- **Descrição**: Criador do servidor

## Comandos

### /rank <jogador> set <rank>
Define o rank de um jogador.

**Permissão**: `round6.admin`

**Exemplos**:
```
/rank Notch set CREATOR
/rank Steve set GAMEMASTER
/rank Alex set GUARDA
```

**Regras**:
- Você não pode definir um rank igual ou superior ao seu
- Você não pode modificar o rank de alguém igual ou superior a você
- Apenas ranks GAMEMASTER, GUARDA e CREATOR podem ser definidos via comando

### /rank <jogador> remove
Remove o rank de um jogador (volta para DEFAULT).

**Permissão**: `round6.admin`

**Exemplo**:
```
/rank Steve remove
```

### /rank <jogador> info
Mostra informações sobre o rank de um jogador.

**Permissão**: `round6.admin`

**Exemplo**:
```
/rank Notch info
```

## PlaceholderAPI

### Placeholders Disponíveis

#### Rank
- `%round6_rank%` - Nome do rank
- `%round6_rank_prefix%` ou `%round6_prefix%` - Prefix do rank
- `%round6_rank_color%` ou `%round6_color%` - Cor do rank
- `%round6_rank_priority%` - Prioridade do rank (1-5)

#### Display
- `%round6_display_name%` - Nome completo com prefix e número
- `%round6_tag%` - Tag formatada do rank

#### Número Round 6
- `%round6_number%` - Número do jogador (001-456)
- `%round6_number_prefix%` - Prefix do número formatado

#### Estatísticas
- `%round6_wins%` - Vitórias no Round 6
- `%round6_losses%` - Derrotas no Round 6
- `%round6_kills%` - Kills no Round 6
- `%round6_deaths%` - Deaths no Round 6
- `%round6_kdr%` - K/D Ratio
- `%round6_winrate%` - Taxa de vitória (%)

### Exemplos de Uso

#### TAB List
```yaml
header: |
  §c§l⬛ ROUND 6 ⬛
  §7Jogadores: §f%server_online%
footer: |
  §7Rank: %round6_tag%
  §7Vitórias: §f%round6_wins%
```

#### Scoreboard
```yaml
lines:
  - "§c§l⬛ ROUND 6 ⬛"
  - ""
  - "§7Rank: %round6_prefix%%round6_rank%"
  - "§7Número: %round6_number%"
  - ""
  - "§7Vitórias: §f%round6_wins%"
  - "§7K/D: §f%round6_kdr%"
```

#### Chat Format
```yaml
format: "%round6_tag%%player_name%§7: §f%message%"
```

## Armazenamento

### BungeeCord
Os ranks são armazenados em arquivos JSON no BungeeCord:
- **Localização**: `plugins/EventBungee/accounts/<uuid>.json`
- **Formato**:
```json
{
  "uuid": "069a79f4-44e9-4726-a5be-fca90e38aaf5",
  "name": "Notch",
  "rank": "CREATOR"
}
```

### Hub (Bukkit)
Os ranks são armazenados no banco de dados MySQL:
- **Tabela**: `accounts`
- **Campos**:
  - `uuid` (VARCHAR 36) - UUID do jogador
  - `name` (VARCHAR 16) - Nome do jogador
  - `rank` (VARCHAR 20) - Rank do jogador
  - `number` (INT) - Número do Round 6
  - `round6_wins` (INT) - Vitórias
  - `round6_losses` (INT) - Derrotas
  - `round6_kills` (INT) - Kills
  - `round6_deaths` (INT) - Deaths

## Hierarquia de Permissões

A hierarquia funciona da seguinte forma:

1. **CREATOR** (prioridade 1) - Pode modificar todos os ranks
2. **GUARDA** (prioridade 2) - Pode modificar GAMEMASTER e DEFAULT
3. **GAMEMASTER** (prioridade 3) - Pode modificar DEFAULT
4. **DEFAULT** (prioridade 5) - Não pode modificar ranks

## Display Names

Os jogadores terão seus nomes exibidos da seguinte forma:

### Jogadores DEFAULT com Número
```
§x§0§6§4§0§2§B[§f001§x§0§6§4§0§2§B] §7Notch
```

### Jogadores com Rank
```
§x§1§1§1§1§8§4§lCreator §x§1§1§1§1§8§4Notch
§x§3§D§3§D§3§D§lGuarda §x§3§D§3§D§3§DSteve
§x§f§f§c§0§c§b§oGameMaster §x§f§f§c§0§c§bAlex
```

## Integração com Outros Sistemas

### Tag System
O sistema de ranks está integrado ao TagManager do Hub, que atualiza automaticamente as tags dos jogadores quando eles entram no servidor.

### Scoreboard
O ScoreboardManager usa os placeholders do PlaceholderAPI para exibir informações de rank na scoreboard.

### TAB List
Os nomes dos jogadores na TAB list são atualizados automaticamente com base no rank.

## Configuração

### BungeeCord
Adicione a permissão `round6.admin` aos administradores no `config.yml`:

```yaml
groups:
  admin:
    permissions:
      - round6.admin
```

### Hub (Bukkit)
Configure o banco de dados MySQL no `config.yml`:

```yaml
database:
  host: localhost
  port: 3306
  database: round6
  username: root
  password: senha123
```

## Dependências

### BungeeCord
- BungeeCord API 1.21
- Gson 2.10.1
- Lombok 1.18.30

### Hub (Bukkit)
- Spigot API 1.8.8
- PlaceholderAPI 2.11.5
- HikariCP 4.0.3
- MySQL Connector 8.0.33
- Gson 2.10.1
- Lombok 1.18.30

## Instalação

1. Coloque `EventBungee.jar` na pasta `plugins/` do BungeeCord
2. Coloque `Hub.jar` na pasta `plugins/` do servidor Hub
3. Instale o PlaceholderAPI no servidor Hub
4. Configure o banco de dados MySQL no Hub
5. Reinicie o BungeeCord e o servidor Hub
6. Use `/rank <jogador> set <rank>` para definir ranks

## Troubleshooting

### Placeholders não funcionam
- Verifique se o PlaceholderAPI está instalado
- Use `/papi ecloud download Round6` se necessário
- Reinicie o servidor após instalar

### Ranks não salvam
- Verifique as permissões das pastas
- Verifique a conexão com o banco de dados MySQL
- Verifique os logs para erros

### Comando /rank não funciona
- Verifique se você tem a permissão `round6.admin`
- Verifique se o jogador alvo existe
- Verifique a hierarquia de ranks

## Suporte

Para suporte, verifique os logs:
- BungeeCord: `logs/latest.log`
- Hub: `logs/latest.log`

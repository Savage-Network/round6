# Configuração de NPCs - Round 6 Event

## Localização do Arquivo

O arquivo de configuração dos NPCs está localizado em:
```
plugins/Round6Hub/npcs.json
```

## Estrutura do Arquivo

```json
{
  "npcs": [
    {
      "id": "round6_main",
      "name": "§c§lROUND 6",
      "type": "ARMOR_STAND",
      "location": {
        "world": "world",
        "x": 0.5,
        "y": 64.0,
        "z": 0.5,
        "yaw": 0.0,
        "pitch": 0.0
      },
      "properties": {
        "visible": false,
        "gravity": false,
        "marker": true,
        "customNameVisible": true,
        "glowing": false
      },
      "action": {
        "type": "CONNECT_SERVER",
        "server": "ROUND6_GAME"
      },
      "hologram": {
        "enabled": true,
        "lines": [
          "§c§l⬛ ROUND 6 ⬛",
          "§7Clique para jogar",
          "",
          "§fJogadores: §a{online}"
        ],
        "height": 2.0
      }
    }
  ]
}
```

## Propriedades Detalhadas

### NPC Base

#### id (String)
- Identificador único do NPC
- Usado internamente para gerenciamento
- Exemplo: `"round6_main"`

#### name (String)
- Nome exibido acima do NPC
- Suporta códigos de cor (&, §)
- Exemplo: `"§c§lROUND 6"`

#### type (String)
- Tipo de entidade do NPC
- Atualmente suportado: `"ARMOR_STAND"`
- Futuro: PLAYER, VILLAGER, etc.

### Location (Localização)

#### world (String)
- Nome do mundo onde o NPC será criado
- Exemplo: `"world"`, `"world_nether"`, `"world_the_end"`

#### x, y, z (Double)
- Coordenadas exatas do NPC
- Use `.5` para centralizar em blocos
- Exemplo: `0.5, 64.0, 0.5`

#### yaw, pitch (Float)
- Rotação do NPC
- yaw: 0-360 (rotação horizontal)
- pitch: -90 a 90 (rotação vertical)
- Exemplo: `0.0, 0.0`

### Properties (Propriedades Visuais)

#### visible (Boolean)
- Define se o corpo do ArmorStand é visível
- `false` = invisível (recomendado para NPCs)
- `true` = visível

#### gravity (Boolean)
- Define se o NPC é afetado pela gravidade
- `false` = flutua no ar (recomendado)
- `true` = cai no chão

#### marker (Boolean)
- Define se o NPC tem hitbox pequena
- `true` = hitbox pequena, não empurra jogadores (recomendado)
- `false` = hitbox normal

#### customNameVisible (Boolean)
- Define se o nome customizado é visível
- `true` = nome sempre visível
- `false` = nome oculto

#### glowing (Boolean)
- Define se o NPC tem efeito de brilho
- `true` = brilhando
- `false` = sem brilho

### Action (Ação ao Clicar)

#### type (String)
- Tipo de ação executada ao clicar
- Atualmente suportado: `"CONNECT_SERVER"`
- Futuro: COMMAND, MENU, etc.

#### server (String)
- Nome do tipo de servidor para conectar
- Deve corresponder a um ServerType
- Exemplo: `"ROUND6_GAME"`, `"HUB"`

### Hologram (Holograma)

#### enabled (Boolean)
- Define se o holograma está ativo
- `true` = holograma visível
- `false` = sem holograma

#### lines (Array de Strings)
- Linhas do holograma (de cima para baixo)
- Suporta códigos de cor
- Suporta variáveis (veja abaixo)
- Linha vazia: `""`

#### height (Double)
- Altura do holograma acima do NPC
- Recomendado: `2.0` para ArmorStands
- Ajuste conforme necessário

## Variáveis Disponíveis

### {online}
- Mostra número de jogadores online no servidor
- Atualiza automaticamente a cada segundo
- Exemplo: `"§fJogadores: §a{online}"`

### Futuras Variáveis
- `{max}` - Máximo de jogadores
- `{status}` - Status do servidor (Online/Offline)
- `{map}` - Mapa atual
- `{mode}` - Modo de jogo

## Exemplos de Configuração

### NPC Simples (Sem Holograma)

```json
{
  "id": "simple_npc",
  "name": "§aClique Aqui",
  "type": "ARMOR_STAND",
  "location": {
    "world": "world",
    "x": 10.5,
    "y": 65.0,
    "z": 20.5,
    "yaw": 180.0,
    "pitch": 0.0
  },
  "properties": {
    "visible": false,
    "gravity": false,
    "marker": true,
    "customNameVisible": true,
    "glowing": false
  },
  "action": {
    "type": "CONNECT_SERVER",
    "server": "ROUND6_GAME"
  },
  "hologram": {
    "enabled": false,
    "lines": [],
    "height": 0.0
  }
}
```

### NPC com Holograma Detalhado

```json
{
  "id": "detailed_npc",
  "name": "§c§lROUND 6",
  "type": "ARMOR_STAND",
  "location": {
    "world": "world",
    "x": 0.5,
    "y": 64.0,
    "z": 0.5,
    "yaw": 0.0,
    "pitch": 0.0
  },
  "properties": {
    "visible": false,
    "gravity": false,
    "marker": true,
    "customNameVisible": true,
    "glowing": true
  },
  "action": {
    "type": "CONNECT_SERVER",
    "server": "ROUND6_GAME"
  },
  "hologram": {
    "enabled": true,
    "lines": [
      "§c§l⬛ ROUND 6 ⬛",
      "§7Evento Especial",
      "",
      "§fJogadores: §a{online}",
      "§fStatus: §aOnline",
      "",
      "§e§lCLIQUE PARA JOGAR"
    ],
    "height": 2.5
  }
}
```

### Múltiplos NPCs

```json
{
  "npcs": [
    {
      "id": "npc_spawn",
      "name": "§c§lROUND 6",
      "type": "ARMOR_STAND",
      "location": {
        "world": "world",
        "x": 0.5,
        "y": 64.0,
        "z": 0.5,
        "yaw": 0.0,
        "pitch": 0.0
      },
      "properties": {
        "visible": false,
        "gravity": false,
        "marker": true,
        "customNameVisible": true,
        "glowing": false
      },
      "action": {
        "type": "CONNECT_SERVER",
        "server": "ROUND6_GAME"
      },
      "hologram": {
        "enabled": true,
        "lines": [
          "§c§l⬛ ROUND 6 ⬛",
          "§7Clique para jogar"
        ],
        "height": 2.0
      }
    },
    {
      "id": "npc_lobby",
      "name": "§a§lVOLTAR",
      "type": "ARMOR_STAND",
      "location": {
        "world": "world",
        "x": 10.5,
        "y": 64.0,
        "z": 10.5,
        "yaw": 90.0,
        "pitch": 0.0
      },
      "properties": {
        "visible": false,
        "gravity": false,
        "marker": true,
        "customNameVisible": true,
        "glowing": false
      },
      "action": {
        "type": "CONNECT_SERVER",
        "server": "HUB"
      },
      "hologram": {
        "enabled": true,
        "lines": [
          "§a§lVOLTAR AO HUB",
          "§7Clique aqui"
        ],
        "height": 2.0
      }
    }
  ]
}
```

## Como Obter Coordenadas

### No Jogo

1. Vá até a localização desejada
2. Use o comando `/tp ~ ~ ~` para ver suas coordenadas exatas
3. Ou pressione F3 para ver as coordenadas na tela
4. Anote X, Y, Z, Yaw (rotação)

### Dicas

- Use `.5` no X e Z para centralizar (ex: `10.5` ao invés de `10.0`)
- Y deve ser a altura do chão + 0.0 para ArmorStands
- Yaw 0 = Sul, 90 = Oeste, 180 = Norte, 270 = Leste

## Recarregar NPCs

Após editar o arquivo `npcs.json`, use o comando:

```
/npc reload
```

Isso irá:
1. Remover todos os NPCs atuais
2. Recarregar o arquivo de configuração
3. Criar os NPCs novamente nas novas posições

## Troubleshooting

### NPC não aparece
- Verifique se o mundo está correto
- Verifique se as coordenadas estão corretas
- Use `/npc reload` após editar o arquivo
- Verifique o console para erros

### Holograma não aparece
- Verifique se `enabled` está como `true`
- Verifique se há linhas no array `lines`
- Ajuste a altura (`height`) se necessário

### Variável {online} mostra 0
- Verifique se o servidor de jogo está online
- Verifique se o servidor está registrado no Redis
- Use `/servidor listar` no BungeeCord para verificar

### NPC não responde ao clique
- Verifique se `marker` está como `true`
- Verifique se a ação está configurada corretamente
- Verifique se o ServerType existe

## Boas Práticas

1. **IDs Únicos**: Use IDs descritivos e únicos para cada NPC
2. **Backup**: Sempre faça backup do arquivo antes de editar
3. **Teste**: Teste cada NPC após criar/editar
4. **Organização**: Mantenha os NPCs organizados por localização
5. **Comentários**: Use IDs descritivos como "documentação"

## Formato JSON

- Use aspas duplas (`"`) para strings
- Não use vírgula após o último item de um array/objeto
- Indente corretamente para facilitar leitura
- Valide o JSON em sites como jsonlint.com antes de usar

# 🚀 Deploy Automático para Pterodactyl

## 📋 Visão Geral

Este sistema permite fazer deploy automático dos plugins compilados diretamente para seus servidores Pterodactyl usando a API oficial.

## ✨ Funcionalidades

- ✅ Upload automático de plugins compilados
- ✅ Upload de arquivos de configuração
- ✅ Criação automática de diretórios
- ✅ Deploy seletivo (escolha quais servidores atualizar)
- ✅ Validação de arquivos antes do upload
- ✅ Feedback visual do progresso
- ✅ Suporte para Python e Bash

---

## 🔧 Pré-requisitos

### Para o script Python (`deploy-pterodactyl.py`)

```bash
# Python 3.6 ou superior
python3 --version

# Instalar dependências
pip install requests
```

### Para o script Bash (`deploy-pterodactyl.sh`)

```bash
# Bash 4.0 ou superior
bash --version

# curl (geralmente já instalado)
curl --version

# jq (opcional, mas recomendado)
sudo apt install jq  # Ubuntu/Debian
brew install jq      # macOS
```

---

## ⚙️ Configuração

### Passo 1: Obter API Key do Pterodactyl

1. Acesse seu painel Pterodactyl
2. Vá em **Account** → **API Credentials**
3. Clique em **Create API Key**
4. Dê um nome (ex: "Deploy Automation")
5. Copie a chave gerada (começa com `ptlc_`)

⚠️ **IMPORTANTE:** Guarde esta chave em segurança! Ela não será mostrada novamente.

### Passo 2: Obter IDs dos Servidores

Para cada servidor (BungeeCord, Auth, Hub, Game):

1. Acesse o servidor no painel
2. Vá em **Settings** → **Debug Information**
3. Copie o **Server ID** (ex: `d3aac109`)

### Passo 3: Configurar o Script

#### Python (`deploy-pterodactyl.py`)

Edite o arquivo e configure:

```python
# URL do seu painel Pterodactyl
PANEL_URL = "https://admin.savagemc.net"

# Sua API Key
API_KEY = "ptlc_SUA_CHAVE_AQUI"

# IDs dos servidores
SERVERS = {
    "bungeecord": "d3aac109",  # Substitua pelos seus IDs
    "auth": "a1b2c3d4",
    "hub": "e5f6g7h8",
    "game": "i9j0k1l2"
}
```

#### Bash (`deploy-pterodactyl.sh`)

Edite o arquivo e configure:

```bash
# URL do seu painel Pterodactyl
PANEL_URL="https://admin.savagemc.net"

# Sua API Key
API_KEY="ptlc_SUA_CHAVE_AQUI"

# IDs dos servidores
BUNGEECORD_ID="d3aac109"
AUTH_ID="a1b2c3d4"
HUB_ID="e5f6g7h8"
GAME_ID="i9j0k1l2"
```

---

## 🚀 Como Usar

### Usando Python

```bash
# Dar permissão de execução
chmod +x deploy-scripts/deploy-pterodactyl.py

# Executar
python3 deploy-scripts/deploy-pterodactyl.py
```

### Usando Bash

```bash
# Dar permissão de execução
chmod +x deploy-scripts/deploy-pterodactyl.sh

# Executar
./deploy-scripts/deploy-pterodactyl.sh
```

### Menu Interativo

Ao executar, você verá:

```
╔══════════════════════════════════════════════════════════════════════════════╗
║              🎮 DEPLOY AUTOMÁTICO - PTERODACTYL 🎮                           ║
║                    Round 6 do Rafa - BungeeCord Edition                      ║
╚══════════════════════════════════════════════════════════════════════════════╝

📋 Servidores disponíveis:
   1. BungeeCord
   2. Auth
   3. Hub
   4. Game
   5. Todos

🔹 Escolha uma opção (1-5):
```

Escolha a opção desejada e confirme o deploy.

---

## 📦 Arquivos que Serão Enviados

### BungeeCord
- `EventBungee.jar` → `/plugins/EventBungee.jar`

### Auth
- `Auth.jar` → `/plugins/Auth.jar`
- `config.yml` → `/plugins/Auth/config.yml`
- `paper-global.yml` → `/config/paper-global.yml`

### Hub
- `Hub.jar` → `/plugins/Hub.jar`
- `config.yml` → `/plugins/Hub/config.yml`
- `paper-global.yml` → `/config/paper-global.yml`

### Game
- `Game.jar` → `/plugins/Game.jar`
- `config.yml` → `/plugins/Game/config.yml`
- `paper-global.yml` → `/config/paper-global.yml`

---

## 🔄 Workflow Completo

### 1. Compilar os Plugins

```bash
# Compilar todos os plugins
./gradlew build

# Ou compilar individualmente
./gradlew :auth:build
./gradlew :hub:build
./gradlew :game:build
./gradlew :bungee:build
```

### 2. Fazer Deploy

```bash
# Opção 1: Python
python3 deploy-scripts/deploy-pterodactyl.py

# Opção 2: Bash
./deploy-scripts/deploy-pterodactyl.sh
```

### 3. Reiniciar Servidores

Após o deploy, reinicie os servidores no painel Pterodactyl:

1. Acesse cada servidor
2. Clique em **Restart**
3. Aguarde o servidor iniciar
4. Verifique os logs

---

## 🔐 Segurança

### Boas Práticas

1. **Nunca commite a API Key no Git**
   ```bash
   # Adicione ao .gitignore
   echo "deploy-scripts/deploy-pterodactyl.py" >> .gitignore
   echo "deploy-scripts/deploy-pterodactyl.sh" >> .gitignore
   ```

2. **Use variáveis de ambiente** (recomendado)
   ```bash
   # Defina no seu shell
   export PTERO_API_KEY="ptlc_sua_chave"
   export PTERO_PANEL_URL="https://admin.savagemc.net"
   
   # Modifique o script para usar
   API_KEY = os.getenv("PTERO_API_KEY")
   ```

3. **Restrinja permissões do arquivo**
   ```bash
   chmod 600 deploy-scripts/deploy-pterodactyl.py
   chmod 600 deploy-scripts/deploy-pterodactyl.sh
   ```

### Permissões Necessárias

A API Key precisa ter as seguintes permissões:

- ✅ `file.create` - Criar arquivos
- ✅ `file.update` - Atualizar arquivos
- ✅ `file.delete` - Deletar arquivos (opcional)
- ✅ `file.read` - Ler arquivos (opcional)

---

## 🐛 Troubleshooting

### Erro: "401 Unauthorized"

**Causa:** API Key inválida ou expirada

**Solução:**
1. Verifique se a API Key está correta
2. Gere uma nova API Key no painel
3. Atualize o script com a nova chave

### Erro: "404 Not Found"

**Causa:** Server ID incorreto

**Solução:**
1. Verifique os IDs dos servidores no painel
2. Vá em Settings → Debug Information
3. Copie o Server ID correto

### Erro: "File too large"

**Causa:** Arquivo maior que 100 MB

**Solução:**
1. Verifique o tamanho do arquivo
2. Pterodactyl limita uploads a 100 MB por arquivo
3. Se necessário, comprima o arquivo

### Erro: "Connection refused"

**Causa:** URL do painel incorreta ou servidor offline

**Solução:**
1. Verifique se a URL está correta
2. Teste acessar o painel no navegador
3. Verifique se o painel está online

---

## 📊 Exemplo de Output

```
╔══════════════════════════════════════════════════════════════════════════════╗
║              🎮 DEPLOY AUTOMÁTICO - PTERODACTYL 🎮                           ║
║                    Round 6 do Rafa - BungeeCord Edition                      ║
╚══════════════════════════════════════════════════════════════════════════════╝

📋 Servidores disponíveis:
   1. BungeeCord
   2. Auth
   3. Hub
   4. Game
   5. Todos

🔹 Escolha uma opção (1-5): 5

⚠️  Você está prestes a fazer deploy em: BUNGEECORD, AUTH, HUB, GAME
   Continuar? (s/n): s

======================================================================
🚀 Fazendo deploy no servidor: BUNGEECORD
   Server ID: d3aac109
======================================================================
   📤 Fazendo upload: EventBungee.jar
   ✅ Upload concluído: EventBungee.jar (0.05 MB)

📊 Resultado: 1/1 arquivos enviados com sucesso

======================================================================
🚀 Fazendo deploy no servidor: AUTH
   Server ID: a1b2c3d4
======================================================================
   📤 Fazendo upload: Auth.jar
   ✅ Upload concluído: Auth.jar (4.43 MB)
   📤 Fazendo upload: config.yml
   ✅ Upload concluído: config.yml (0.00 MB)
   📤 Fazendo upload: paper-global.yml
   ✅ Upload concluído: paper-global.yml (0.00 MB)

📊 Resultado: 3/3 arquivos enviados com sucesso

[... continua para Hub e Game ...]

======================================================================
✅ DEPLOY CONCLUÍDO COM SUCESSO!

📝 Próximos passos:
   1. Reinicie os servidores no painel Pterodactyl
   2. Verifique os logs para confirmar que tudo carregou
   3. Teste a conexão: admin.savagemc.net:25100
======================================================================
```

---

## 🔄 Integração com CI/CD

### GitHub Actions

Crie `.github/workflows/deploy.yml`:

```yaml
name: Deploy to Pterodactyl

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Build with Gradle
      run: ./gradlew build
    
    - name: Set up Python
      uses: actions/setup-python@v4
      with:
        python-version: '3.x'
    
    - name: Install dependencies
      run: pip install requests
    
    - name: Deploy to Pterodactyl
      env:
        PTERO_API_KEY: ${{ secrets.PTERO_API_KEY }}
        PTERO_PANEL_URL: ${{ secrets.PTERO_PANEL_URL }}
      run: python3 deploy-scripts/deploy-pterodactyl.py
```

Configure os secrets no GitHub:
- `PTERO_API_KEY`: Sua API Key
- `PTERO_PANEL_URL`: URL do painel

---

## 📚 Recursos Adicionais

- [Documentação da API do Pterodactyl](https://pterodactyl-api-docs.netvpx.com/)
- [Pterodactyl Discord](https://discord.gg/pterodactyl)
- [GitHub do Pterodactyl](https://github.com/pterodactyl/panel)

---

## 🆘 Suporte

Se encontrar problemas:

1. Verifique os logs do script
2. Verifique os logs do painel Pterodactyl
3. Consulte a documentação da API
4. Abra uma issue no repositório

---

## 📝 Licença

Este script é parte do projeto Round 6 do Rafa.

Desenvolvido por: Rafa  
Versão: 1.0.0 - BungeeCord Edition  
Data: 13 de Abril de 2026

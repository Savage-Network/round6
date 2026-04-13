# 🚀 Sistema de Deploy Automático - Resumo

## ✅ O Que Foi Criado

Sistema completo de deploy automático para Pterodactyl com suporte a Python e Bash.

---

## 📁 Arquivos Criados

### Scripts Principais

1. **deploy-pterodactyl.py** (Python)
   - Script principal de deploy em Python
   - Suporta upload de múltiplos arquivos
   - Criação automática de diretórios
   - Menu interativo
   - Validação de arquivos
   - Feedback visual do progresso

2. **deploy-pterodactyl.sh** (Bash)
   - Alternativa em Bash puro
   - Mesmas funcionalidades do Python
   - Não requer dependências externas (exceto curl)
   - Cores e formatação visual

3. **setup-deploy.sh** (Assistente de Configuração)
   - Configuração interativa guiada
   - Validação de credenciais
   - Teste de conexão com API
   - Configuração automática dos scripts

### Documentação

4. **README-DEPLOY-AUTOMATICO.md**
   - Documentação completa
   - Guia de instalação
   - Exemplos de uso
   - Troubleshooting
   - Integração com CI/CD
   - Boas práticas de segurança

5. **INICIO-RAPIDO.txt**
   - Guia rápido de início
   - Passo a passo simplificado
   - Checklist
   - Problemas comuns

6. **RESUMO-DEPLOY-AUTOMATICO.md** (este arquivo)
   - Visão geral do sistema
   - Lista de arquivos
   - Como usar

### Configuração

7. **config.example.json**
   - Exemplo de arquivo de configuração
   - Estrutura JSON documentada
   - Template para criar config.json

8. **.gitignore**
   - Proteção de credenciais
   - Evita commit acidental de API Keys

---

## 🎯 Funcionalidades

### ✅ Upload Automático
- Plugins compilados (.jar)
- Arquivos de configuração (.yml)
- Criação automática de diretórios
- Validação de arquivos antes do upload

### ✅ Deploy Seletivo
- BungeeCord (EventBungee.jar)
- Auth (Auth.jar + configs)
- Hub (Hub.jar + configs)
- Game (Game.jar + configs)
- Todos os servidores de uma vez

### ✅ Interface Amigável
- Menu interativo
- Feedback visual com cores
- Barra de progresso
- Mensagens de erro claras
- Confirmação antes do deploy

### ✅ Segurança
- API Key protegida
- Validação de credenciais
- Teste de conexão
- .gitignore automático
- Permissões restritas

---

## 🚀 Como Usar

### Primeira Vez (Configuração)

```bash
# 1. Dar permissão de execução
chmod +x deploy-scripts/setup-deploy.sh

# 2. Executar assistente de configuração
./deploy-scripts/setup-deploy.sh

# 3. Seguir as instruções na tela
# - Informar URL do painel
# - Informar API Key
# - Informar IDs dos servidores
# - Escolher script (Python ou Bash)
```

### Uso Regular

#### Python (Recomendado)
```bash
# Instalar dependências (primeira vez)
pip install requests

# Executar deploy
python3 deploy-scripts/deploy-pterodactyl.py
```

#### Bash (Alternativo)
```bash
# Executar deploy
./deploy-scripts/deploy-pterodactyl.sh
```

### Workflow Completo

```bash
# 1. Compilar plugins
./gradlew build

# 2. Fazer deploy
python3 deploy-scripts/deploy-pterodactyl.py

# 3. Escolher servidores no menu
# Opção 5 = Todos

# 4. Confirmar
# Digite 's'

# 5. Aguardar conclusão

# 6. Reiniciar servidores no painel
```

---

## 📋 Arquivos que Serão Enviados

### BungeeCord
```
deploy-pronto/bungeecord/plugins/EventBungee.jar
  → /plugins/EventBungee.jar
```

### Auth
```
deploy-pronto/auth/plugins/Auth.jar
  → /plugins/Auth.jar

deploy-pronto/auth/plugins/Auth/config.yml
  → /plugins/Auth/config.yml

deploy-pronto/auth/config/paper-global.yml
  → /config/paper-global.yml
```

### Hub
```
deploy-pronto/hub/plugins/Hub.jar
  → /plugins/Hub.jar

deploy-pronto/hub/plugins/Hub/config.yml
  → /plugins/Hub/config.yml

deploy-pronto/hub/config/paper-global.yml
  → /config/paper-global.yml
```

### Game
```
deploy-pronto/game/plugins/Game.jar
  → /plugins/Game.jar

deploy-pronto/game/plugins/Game/config.yml
  → /plugins/Game/config.yml

deploy-pronto/game/config/paper-global.yml
  → /config/paper-global.yml
```

---

## 🔐 Segurança

### Credenciais Protegidas

O sistema protege suas credenciais automaticamente:

1. **.gitignore** criado automaticamente
2. Arquivos de configuração não são commitados
3. Permissões restritas (600) nos arquivos sensíveis
4. API Key nunca é exibida completa nos logs

### Boas Práticas

```bash
# Adicionar ao .gitignore do projeto
echo "deploy-scripts/config.json" >> .gitignore
echo "deploy-scripts/deploy-pterodactyl.py" >> .gitignore
echo "deploy-scripts/deploy-pterodactyl.sh" >> .gitignore

# Restringir permissões
chmod 600 deploy-scripts/config.json
chmod 600 deploy-scripts/deploy-pterodactyl.py
chmod 600 deploy-scripts/deploy-pterodactyl.sh
```

---

## 🔄 Integração com CI/CD

### GitHub Actions

O sistema pode ser integrado com GitHub Actions para deploy automático:

```yaml
name: Deploy to Pterodactyl

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Build
      run: ./gradlew build
    - name: Deploy
      env:
        PTERO_API_KEY: ${{ secrets.PTERO_API_KEY }}
      run: python3 deploy-scripts/deploy-pterodactyl.py
```

Configure os secrets no GitHub:
- `PTERO_API_KEY`: Sua API Key
- `PTERO_PANEL_URL`: URL do painel

---

## 📊 Vantagens

### ✅ Automação Completa
- Sem necessidade de upload manual
- Sem necessidade de SFTP/FTP
- Sem necessidade de acesso SSH
- Deploy com um único comando

### ✅ Economia de Tempo
- Deploy de todos os servidores em minutos
- Não precisa acessar o painel
- Não precisa fazer upload arquivo por arquivo
- Ideal para atualizações frequentes

### ✅ Confiabilidade
- Validação de arquivos
- Criação automática de diretórios
- Tratamento de erros
- Feedback claro do status

### ✅ Flexibilidade
- Deploy seletivo (escolha quais servidores)
- Suporte a Python e Bash
- Configuração via arquivo ou variáveis de ambiente
- Fácil integração com CI/CD

---

## 🐛 Troubleshooting

### Erro: "401 Unauthorized"
**Causa:** API Key inválida  
**Solução:** Gere uma nova API Key no painel

### Erro: "404 Not Found"
**Causa:** Server ID incorreto  
**Solução:** Verifique os IDs em Settings → Debug Information

### Erro: "File too large"
**Causa:** Arquivo maior que 100 MB  
**Solução:** Pterodactyl limita uploads a 100 MB

### Erro: "Connection refused"
**Causa:** URL incorreta ou painel offline  
**Solução:** Verifique a URL e status do painel

---

## 📚 Documentação Adicional

- **README-DEPLOY-AUTOMATICO.md**: Documentação completa
- **INICIO-RAPIDO.txt**: Guia rápido
- **config.example.json**: Exemplo de configuração
- [API do Pterodactyl](https://pterodactyl-api-docs.netvpx.com/)

---

## 🎉 Conclusão

Sistema completo de deploy automático criado com sucesso!

### Próximos Passos

1. Execute o assistente de configuração
2. Configure suas credenciais
3. Teste o deploy em um servidor
4. Configure para todos os servidores
5. Integre com seu workflow de desenvolvimento

### Suporte

Se encontrar problemas:
1. Consulte README-DEPLOY-AUTOMATICO.md
2. Verifique INICIO-RAPIDO.txt
3. Consulte a documentação da API do Pterodactyl

---

**Desenvolvido por:** Rafa  
**Versão:** 1.0.0 - BungeeCord Edition  
**Data:** 13 de Abril de 2026

**BOA SORTE COM O DEPLOY AUTOMÁTICO! 🚀**

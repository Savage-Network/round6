╔══════════════════════════════════════════════════════════════════════════════╗
║              🪟 DEPLOY AUTOMÁTICO PARA WINDOWS 🪟                            ║
║                    Round 6 do Rafa - BungeeCord Edition                      ║
╚══════════════════════════════════════════════════════════════════════════════╝


📋 GUIA RÁPIDO PARA WINDOWS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Este guia é específico para usuários Windows que querem fazer deploy automático
dos plugins para servidores Pterodactyl.


✅ PRÉ-REQUISITOS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Windows 10 ou superior
2. curl (já vem instalado no Windows 10/11)
3. Acesso ao painel Pterodactyl
4. API Key do Pterodactyl


🚀 PASSO A PASSO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

PASSO 1: COMPILAR OS PLUGINS
────────────────────────────────────────────────────────────────────────────────

Abra o Prompt de Comando ou PowerShell na pasta do projeto e execute:

   gradlew.bat build

Aguarde a compilação terminar. Os plugins serão gerados em:
   - deploy-pronto\bungeecord\plugins\EventBungee.jar
   - deploy-pronto\auth\plugins\Auth.jar
   - deploy-pronto\hub\plugins\Hub.jar
   - deploy-pronto\game\plugins\Game.jar


PASSO 2: OBTER API KEY DO PTERODACTYL
────────────────────────────────────────────────────────────────────────────────

1. Acesse seu painel Pterodactyl no navegador
   Exemplo: https://admin.savagemc.net

2. Faça login com sua conta

3. Vá em: Account → API Credentials

4. Clique em: Create API Key

5. Dê um nome (ex: "Deploy Automation")

6. Copie a chave gerada (começa com ptlc_)
   ⚠️ IMPORTANTE: Guarde esta chave! Ela não será mostrada novamente.


PASSO 3: OBTER IDs DOS SERVIDORES
────────────────────────────────────────────────────────────────────────────────

Para cada servidor (BungeeCord, Auth, Hub, Game):

1. Acesse o servidor no painel

2. Vá em: Settings → Debug Information

3. Copie o Server ID (ex: d3aac109)

4. Anote em algum lugar:
   - BungeeCord ID: _______________
   - Auth ID: _______________
   - Hub ID: _______________
   - Game ID: _______________


PASSO 4: CONFIGURAR O SCRIPT
────────────────────────────────────────────────────────────────────────────────

OPÇÃO A - Configuração Automática (Recomendado):

   1. Clique duas vezes em: deploy-scripts\setup-deploy.bat
   
   2. Siga as instruções na tela:
      - Digite a URL do painel
      - Cole a API Key
      - Cole os IDs dos servidores
   
   3. O script irá testar a conexão e salvar a configuração


OPÇÃO B - Configuração Manual:

   1. Abra o arquivo: deploy-scripts\deploy-pterodactyl.bat
      (Clique com botão direito → Editar)
   
   2. Encontre a seção "CONFIGURAÇÃO" no início do arquivo
   
   3. Edite as seguintes linhas:
   
      set "PANEL_URL=https://admin.savagemc.net"
      set "API_KEY=ptlc_sua_chave_aqui"
      set "BUNGEECORD_ID=seu_id_aqui"
      set "AUTH_ID=seu_id_aqui"
      set "HUB_ID=seu_id_aqui"
      set "GAME_ID=seu_id_aqui"
   
   4. Salve o arquivo (Ctrl+S)


PASSO 5: EXECUTAR O DEPLOY
────────────────────────────────────────────────────────────────────────────────

1. Clique duas vezes em: deploy-scripts\deploy-pterodactyl.bat

2. Você verá o menu:
   
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
      0. Sair
   
   🔹 Escolha uma opção (0-5):

3. Digite o número da opção desejada e pressione Enter
   - Opção 5 = Fazer deploy em todos os servidores

4. Confirme digitando 's' e pressionando Enter

5. Aguarde o upload dos arquivos
   O script mostrará o progresso de cada arquivo

6. Quando terminar, você verá:
   
   ======================================================================
   ✅ DEPLOY CONCLUÍDO COM SUCESSO!
   
   📝 Próximos passos:
      1. Reinicie os servidores no painel Pterodactyl
      2. Verifique os logs para confirmar que tudo carregou
      3. Teste a conexão: admin.savagemc.net:25100
   ======================================================================


PASSO 6: REINICIAR SERVIDORES
────────────────────────────────────────────────────────────────────────────────

1. Acesse o painel Pterodactyl

2. Para cada servidor que recebeu deploy:
   - Clique no servidor
   - Clique em "Restart"
   - Aguarde o servidor iniciar

3. Verifique os logs para confirmar que os plugins carregaram


═══════════════════════════════════════════════════════════════════════════════


📊 EXEMPLO DE USO COMPLETO
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Abra o Prompt de Comando na pasta do projeto

2. Compile os plugins:
   gradlew.bat build

3. Configure o deploy (primeira vez):
   deploy-scripts\setup-deploy.bat

4. Faça o deploy:
   deploy-scripts\deploy-pterodactyl.bat

5. Escolha a opção 5 (Todos)

6. Confirme com 's'

7. Aguarde a conclusão

8. Reinicie os servidores no painel


═══════════════════════════════════════════════════════════════════════════════


🔐 SEGURANÇA
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

⚠️ IMPORTANTE:

1. NUNCA compartilhe sua API Key com ninguém

2. NUNCA commite o arquivo deploy-pterodactyl.bat no Git
   (depois de configurado com suas credenciais)

3. Se precisar compartilhar o projeto:
   - Use o arquivo deploy-pterodactyl.bat original (sem configuração)
   - Ou remova suas credenciais antes de compartilhar

4. Se sua API Key for comprometida:
   - Acesse o painel
   - Vá em Account → API Credentials
   - Delete a chave comprometida
   - Crie uma nova chave
   - Reconfigure o script


═══════════════════════════════════════════════════════════════════════════════


🐛 PROBLEMAS COMUNS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

PROBLEMA: "curl não encontrado"
────────────────────────────────────────────────────────────────────────────────
CAUSA: curl não está instalado ou não está no PATH

SOLUÇÃO:
   1. Verifique se você tem Windows 10/11 (curl já vem instalado)
   
   2. Teste no Prompt de Comando:
      curl --version
   
   3. Se não funcionar, baixe curl em:
      https://curl.se/windows/
   
   4. Ou use o Python/Bash como alternativa


PROBLEMA: "401 Unauthorized"
────────────────────────────────────────────────────────────────────────────────
CAUSA: API Key inválida ou expirada

SOLUÇÃO:
   1. Verifique se copiou a API Key corretamente
   2. Gere uma nova API Key no painel
   3. Reconfigure o script com a nova chave


PROBLEMA: "404 Not Found"
────────────────────────────────────────────────────────────────────────────────
CAUSA: Server ID incorreto

SOLUÇÃO:
   1. Acesse o servidor no painel
   2. Vá em Settings → Debug Information
   3. Copie o Server ID correto
   4. Reconfigure o script


PROBLEMA: "Arquivo não encontrado"
────────────────────────────────────────────────────────────────────────────────
CAUSA: Plugins não foram compilados

SOLUÇÃO:
   1. Execute: gradlew.bat build
   2. Aguarde a compilação terminar
   3. Verifique se os JARs foram gerados em deploy-pronto\


PROBLEMA: "Connection refused"
────────────────────────────────────────────────────────────────────────────────
CAUSA: URL do painel incorreta ou painel offline

SOLUÇÃO:
   1. Verifique se a URL está correta
   2. Teste acessar o painel no navegador
   3. Verifique se o painel está online


═══════════════════════════════════════════════════════════════════════════════


💡 DICAS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. ATALHO NO DESKTOP
   Crie um atalho para deploy-pterodactyl.bat na área de trabalho
   para facilitar o acesso

2. DEPLOY RÁPIDO
   Crie um arquivo deploy-rapido.bat com:
   
   @echo off
   gradlew.bat build
   deploy-scripts\deploy-pterodactyl.bat

3. BACKUP AUTOMÁTICO
   O script setup-deploy.bat cria backup automático do arquivo
   anterior em deploy-pterodactyl.bat.bak

4. MÚLTIPLAS CONFIGURAÇÕES
   Se você gerencia múltiplos painéis, crie cópias do script:
   - deploy-pterodactyl-producao.bat
   - deploy-pterodactyl-teste.bat


═══════════════════════════════════════════════════════════════════════════════


📚 DOCUMENTAÇÃO ADICIONAL
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Para mais informações, consulte:

📖 README-DEPLOY-AUTOMATICO.md
   Documentação completa com exemplos avançados

📖 INICIO-RAPIDO.txt
   Guia rápido multiplataforma

🌐 API do Pterodactyl
   https://pterodactyl-api-docs.netvpx.com/


═══════════════════════════════════════════════════════════════════════════════


✅ CHECKLIST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Antes de fazer o deploy:

[ ] Plugins compilados (gradlew.bat build)
[ ] API Key obtida do painel
[ ] IDs dos servidores copiados
[ ] Script configurado (setup-deploy.bat)
[ ] Teste de conexão bem-sucedido

Após o deploy:

[ ] Servidores reiniciados
[ ] Logs verificados
[ ] Plugins carregados corretamente
[ ] Conexão testada (admin.savagemc.net:25100)


═══════════════════════════════════════════════════════════════════════════════


🎉 PRONTO PARA COMEÇAR!
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Execute o assistente de configuração:

   deploy-scripts\setup-deploy.bat

Ou configure manualmente e execute:

   deploy-scripts\deploy-pterodactyl.bat


═══════════════════════════════════════════════════════════════════════════════

Desenvolvido por: Rafa
Versão: 1.0.0 - BungeeCord Edition
Data: 13 de Abril de 2026

BOA SORTE COM O DEPLOY! 🚀

═══════════════════════════════════════════════════════════════════════════════

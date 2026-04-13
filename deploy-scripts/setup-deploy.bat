@echo off
REM ============================================================================
REM Script de Configuração Inicial do Deploy Automático (Windows)
REM Round 6 do Rafa - BungeeCord Edition
REM ============================================================================

setlocal enabledelayedexpansion
chcp 65001 >nul 2>&1

title Round 6 - Configuração do Deploy Automático
color 0B

cls
echo.
echo ╔══════════════════════════════════════════════════════════════════════════════╗
echo ║              🔧 CONFIGURAÇÃO DO DEPLOY AUTOMÁTICO 🔧                         ║
echo ║                    Round 6 do Rafa - BungeeCord Edition                      ║
echo ╚══════════════════════════════════════════════════════════════════════════════╝
echo.
echo Este assistente irá configurar o deploy automático para Pterodactyl.
echo.
pause

REM ============================================================================
REM PASSO 1: URL DO PAINEL
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo PASSO 1: URL do Painel Pterodactyl
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo ℹ️  Digite a URL do seu painel (ex: https://admin.savagemc.net)
echo ⚠️  Não inclua barra (/) no final
echo.

set /p "PANEL_URL=URL do Painel [https://admin.savagemc.net]: "
if "%PANEL_URL%"=="" set "PANEL_URL=https://admin.savagemc.net"

REM Remover barra final se existir
if "%PANEL_URL:~-1%"=="/" set "PANEL_URL=%PANEL_URL:~0,-1%"

echo.
echo ✅ URL configurada: %PANEL_URL%
echo.
pause

REM ============================================================================
REM PASSO 2: API KEY
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo PASSO 2: API Key do Pterodactyl
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo ℹ️  Para obter sua API Key:
echo    1. Acesse: %PANEL_URL%
echo    2. Vá em: Account → API Credentials
echo    3. Clique em: Create API Key
echo    4. Copie a chave gerada (começa com ptlc_)
echo.

:input_api_key
set /p "API_KEY=API Key: "

if "%API_KEY%"=="" (
    echo.
    echo ❌ API Key não pode estar vazia!
    echo.
    goto input_api_key
)

if not "%API_KEY:~0,5%"=="ptlc_" (
    echo.
    echo ❌ API Key inválida! Deve começar com 'ptlc_'
    echo.
    goto input_api_key
)

echo.
echo ✅ API Key configurada: %API_KEY:~0,15%...
echo.
pause

REM ============================================================================
REM PASSO 3: TESTAR CONEXÃO
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo PASSO 3: Testando Conexão
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo ℹ️  Testando conexão com a API...
echo.

curl -s -o nul -w "%%{http_code}" ^
    -H "Authorization: Bearer %API_KEY%" ^
    -H "Accept: application/vnd.pterodactyl.v1+json" ^
    "%PANEL_URL%/api/client" > temp_response.txt

set /p http_code=<temp_response.txt
del temp_response.txt

if "%http_code%"=="200" (
    echo ✅ Conexão estabelecida com sucesso!
) else (
    color 0C
    echo ❌ Falha na conexão (HTTP %http_code%)
    echo.
    echo    Verifique:
    echo    - URL do painel está correta
    echo    - API Key está correta
    echo    - Painel está online
    echo.
    pause
    exit /b 1
)

echo.
pause

REM ============================================================================
REM PASSO 4: IDs DOS SERVIDORES
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo PASSO 4: IDs dos Servidores
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
echo ℹ️  Para cada servidor, você precisa do Server ID
echo ℹ️  Encontre em: Painel → Server → Settings → Debug Information
echo.

set /p "BUNGEECORD_ID=Server ID do BungeeCord: "
set /p "AUTH_ID=Server ID do Auth: "
set /p "HUB_ID=Server ID do Hub: "
set /p "GAME_ID=Server ID do Game: "

echo.
echo ✅ IDs configurados!
echo.
pause

REM ============================================================================
REM PASSO 5: SALVAR CONFIGURAÇÃO
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo PASSO 5: Salvando Configuração
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.

REM Criar backup se arquivo já existir
if exist "deploy-scripts\deploy-pterodactyl.bat" (
    echo ℹ️  Criando backup do arquivo existente...
    copy /y "deploy-scripts\deploy-pterodactyl.bat" "deploy-scripts\deploy-pterodactyl.bat.bak" >nul
)

REM Ler o template
set "template_file=deploy-scripts\deploy-pterodactyl.bat"
set "temp_file=deploy-scripts\deploy-pterodactyl.bat.tmp"

REM Criar arquivo temporário com as substituições
(
    for /f "usebackq delims=" %%a in ("%template_file%") do (
        set "line=%%a"
        
        REM Substituir PANEL_URL
        if "!line:PANEL_URL=https://admin.savagemc.net=!"=="!line!" (
            set "line=!line:PANEL_URL=https://admin.savagemc.net=PANEL_URL=%PANEL_URL%!"
        )
        
        REM Substituir API_KEY
        if "!line:API_KEY=ptlc_YOUR_API_KEY_HERE=!"=="!line!" (
            set "line=!line:API_KEY=ptlc_YOUR_API_KEY_HERE=API_KEY=%API_KEY%!"
        )
        
        REM Substituir IDs
        if "!line:BUNGEECORD_ID=SERVER_ID_BUNGEECORD=!"=="!line!" (
            set "line=!line:BUNGEECORD_ID=SERVER_ID_BUNGEECORD=BUNGEECORD_ID=%BUNGEECORD_ID%!"
        )
        if "!line:AUTH_ID=SERVER_ID_AUTH=!"=="!line!" (
            set "line=!line:AUTH_ID=SERVER_ID_AUTH=AUTH_ID=%AUTH_ID%!"
        )
        if "!line:HUB_ID=SERVER_ID_HUB=!"=="!line!" (
            set "line=!line:HUB_ID=SERVER_ID_HUB=HUB_ID=%HUB_ID%!"
        )
        if "!line:GAME_ID=SERVER_ID_GAME=!"=="!line!" (
            set "line=!line:GAME_ID=SERVER_ID_GAME=GAME_ID=%GAME_ID%!"
        )
        
        echo !line!
    )
) > "%temp_file%"

REM Substituir arquivo original
move /y "%temp_file%" "%template_file%" >nul

echo ✅ Script configurado: deploy-scripts\deploy-pterodactyl.bat
echo.

REM Criar arquivo de configuração JSON
(
    echo {
    echo   "panel_url": "%PANEL_URL%",
    echo   "api_key": "%API_KEY%",
    echo   "servers": {
    echo     "bungeecord": "%BUNGEECORD_ID%",
    echo     "auth": "%AUTH_ID%",
    echo     "hub": "%HUB_ID%",
    echo     "game": "%GAME_ID%"
    echo   },
    echo   "configured_at": "%date% %time%"
    echo }
) > "deploy-scripts\config.json"

echo ✅ Configuração salva: deploy-scripts\config.json
echo.
pause

REM ============================================================================
REM RESUMO
REM ============================================================================

cls
echo.
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo ✅ CONFIGURAÇÃO CONCLUÍDA!
echo ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
echo.
color 0A
echo Configuração salva com sucesso!
echo.
echo 📋 Resumo:
echo    Painel: %PANEL_URL%
echo    API Key: %API_KEY:~0,15%...
echo    BungeeCord ID: %BUNGEECORD_ID%
echo    Auth ID: %AUTH_ID%
echo    Hub ID: %HUB_ID%
echo    Game ID: %GAME_ID%
echo.
echo 🚀 Próximos passos:
echo.
echo    1. Execute o deploy:
echo       deploy-scripts\deploy-pterodactyl.bat
echo.
echo    2. Escolha quais servidores atualizar
echo.
echo    3. Confirme o deploy
echo.
echo 📚 Documentação completa em:
echo    deploy-scripts\README-DEPLOY-AUTOMATICO.md
echo.
echo ⚠️  IMPORTANTE: Mantenha sua API Key em segurança!
echo ⚠️  Não compartilhe o arquivo config.json com ninguém!
echo.
pause

REM Perguntar se quer executar o deploy agora
echo.
set /p "run_deploy=Deseja executar o deploy agora? (s/n): "

if /i "%run_deploy%"=="s" (
    echo.
    echo Iniciando deploy...
    echo.
    call "deploy-scripts\deploy-pterodactyl.bat"
)

exit /b 0

@echo off
REM ============================================================================
REM Script de Deploy Automático para Pterodactyl (Windows)
REM Round 6 do Rafa - BungeeCord Edition
REM ============================================================================

setlocal enabledelayedexpansion
chcp 65001 >nul 2>&1

REM ============================================================================
REM CONFIGURAÇÃO - EDITE AQUI COM SUAS CREDENCIAIS
REM ============================================================================

REM URL do seu painel Pterodactyl (sem barra no final)
set "PANEL_URL=https://admin.savagemc.net"

REM API Key do Pterodactyl (Client API)
REM Gere em: Painel → Account → API Credentials
set "API_KEY=ptlc_YlqJMfEqaUEz84iwuBtFKytrAS6ELJBwkHj2SDyZjXd"

REM IDs dos servidores (encontre em: Painel → Server → Settings → Debug Information)
set "BUNGEECORD_ID=2ad63dd3-ca20-4c51-a75c-a7b19bfb515a"
set "AUTH_ID=6a1abe41-5be8-4f6a-a7a5-2aecae5f6bfb"
set "HUB_ID=7d6a9ad2-21aa-4f10-8504-3d21235a27fb"
set "GAME_ID=4de913d4-c267-4093-adf3-5227c5850da6"

REM ============================================================================
REM VALIDAÇÃO
REM ============================================================================

title Round 6 - Deploy Automático para Pterodactyl
color 0A

echo.
echo ╔══════════════════════════════════════════════════════════════════════════════╗
echo ║              🎮 DEPLOY AUTOMÁTICO - PTERODACTYL 🎮                           ║
echo ║                    Round 6 do Rafa - BungeeCord Edition                      ║
echo ╚══════════════════════════════════════════════════════════════════════════════╝
echo.

REM Validar configuração
if "%API_KEY%"=="ptlc_YOUR_API_KEY_HERE" (
    color 0C
    echo ❌ ERRO: Configure sua API Key no script!
    echo.
    echo    Edite o arquivo deploy-pterodactyl.bat
    echo    Defina API_KEY com sua chave do Pterodactyl
    echo    Gere em: Painel → Account → API Credentials
    echo.
    pause
    exit /b 1
)

if "%BUNGEECORD_ID%"=="SERVER_ID_BUNGEECORD" (
    color 0C
    echo ❌ ERRO: Configure os IDs dos servidores no script!
    echo.
    echo    Edite o arquivo deploy-pterodactyl.bat
    echo    Defina os IDs dos servidores
    echo    Encontre em: Painel → Server → Settings → Debug Information
    echo.
    pause
    exit /b 1
)

REM Verificar se curl está disponível
where curl >nul 2>&1
if errorlevel 1 (
    color 0C
    echo ❌ ERRO: curl não encontrado!
    echo.
    echo    curl é necessário para fazer upload dos arquivos
    echo    No Windows 10/11, curl já vem instalado
    echo    Se não tiver, baixe em: https://curl.se/windows/
    echo.
    pause
    exit /b 1
)

REM ============================================================================
REM MENU
REM ============================================================================

:menu
cls
echo.
echo ╔══════════════════════════════════════════════════════════════════════════════╗
echo ║              🎮 DEPLOY AUTOMÁTICO - PTERODACTYL 🎮                           ║
echo ║                    Round 6 do Rafa - BungeeCord Edition                      ║
echo ╚══════════════════════════════════════════════════════════════════════════════╝
echo.
echo 📋 Servidores disponíveis:
echo.
echo    1. BungeeCord
echo    2. Auth
echo    3. Hub
echo    4. Game
echo    5. Todos
echo    0. Sair
echo.
set /p "choice=🔹 Escolha uma opção (0-5): "

if "%choice%"=="0" exit /b 0
if "%choice%"=="1" goto deploy_bungeecord
if "%choice%"=="2" goto deploy_auth
if "%choice%"=="3" goto deploy_hub
if "%choice%"=="4" goto deploy_game
if "%choice%"=="5" goto deploy_all

echo.
echo ❌ Opção inválida!
timeout /t 2 >nul
goto menu

REM ============================================================================
REM FUNÇÕES DE DEPLOY
REM ============================================================================

:deploy_all
echo.
echo ⚠️  Você está prestes a fazer deploy em: BUNGEECORD, AUTH, HUB, GAME
set /p "confirm=   Continuar? (s/n): "
if /i not "%confirm%"=="s" goto menu

call :deploy_server_bungeecord
call :deploy_server_auth
call :deploy_server_hub
call :deploy_server_game
goto finish

:deploy_bungeecord
echo.
echo ⚠️  Você está prestes a fazer deploy em: BUNGEECORD
set /p "confirm=   Continuar? (s/n): "
if /i not "%confirm%"=="s" goto menu

call :deploy_server_bungeecord
goto finish

:deploy_auth
echo.
echo ⚠️  Você está prestes a fazer deploy em: AUTH
set /p "confirm=   Continuar? (s/n): "
if /i not "%confirm%"=="s" goto menu

call :deploy_server_auth
goto finish

:deploy_hub
echo.
echo ⚠️  Você está prestes a fazer deploy em: HUB
set /p "confirm=   Continuar? (s/n): "
if /i not "%confirm%"=="s" goto menu

call :deploy_server_hub
goto finish

:deploy_game
echo.
echo ⚠️  Você está prestes a fazer deploy em: GAME
set /p "confirm=   Continuar? (s/n): "
if /i not "%confirm%"=="s" goto menu

call :deploy_server_game
goto finish

REM ============================================================================
REM DEPLOY POR SERVIDOR
REM ============================================================================

:deploy_server_bungeecord
echo.
echo ======================================================================
echo 🚀 Fazendo deploy no servidor: BUNGEECORD
echo    Server ID: %BUNGEECORD_ID%
echo ======================================================================
echo.

call :upload_file "%BUNGEECORD_ID%" "deploy-pronto\bungeecord\plugins\EventBungee.jar" "/plugins" "EventBungee.jar"

echo.
echo 📊 Deploy do BungeeCord concluído!
exit /b 0

:deploy_server_auth
echo.
echo ======================================================================
echo 🚀 Fazendo deploy no servidor: AUTH
echo    Server ID: %AUTH_ID%
echo ======================================================================
echo.

call :create_directory "%AUTH_ID%" "/" "plugins"
call :create_directory "%AUTH_ID%" "/plugins" "Auth"
call :create_directory "%AUTH_ID%" "/" "config"

call :upload_file "%AUTH_ID%" "deploy-pronto\auth\plugins\Auth.jar" "/plugins" "Auth.jar"
call :upload_file "%AUTH_ID%" "deploy-pronto\auth\plugins\Auth\config.yml" "/plugins/Auth" "config.yml"
call :upload_file "%AUTH_ID%" "deploy-pronto\auth\config\paper-global.yml" "/config" "paper-global.yml"

echo.
echo 📊 Deploy do Auth concluído!
exit /b 0

:deploy_server_hub
echo.
echo ======================================================================
echo 🚀 Fazendo deploy no servidor: HUB
echo    Server ID: %HUB_ID%
echo ======================================================================
echo.

call :create_directory "%HUB_ID%" "/" "plugins"
call :create_directory "%HUB_ID%" "/plugins" "Hub"
call :create_directory "%HUB_ID%" "/" "config"

call :upload_file "%HUB_ID%" "deploy-pronto\hub\plugins\Hub.jar" "/plugins" "Hub.jar"
call :upload_file "%HUB_ID%" "deploy-pronto\hub\plugins\Hub\config.yml" "/plugins/Hub" "config.yml"
call :upload_file "%HUB_ID%" "deploy-pronto\hub\config\paper-global.yml" "/config" "paper-global.yml"

echo.
echo 📊 Deploy do Hub concluído!
exit /b 0

:deploy_server_game
echo.
echo ======================================================================
echo 🚀 Fazendo deploy no servidor: GAME
echo    Server ID: %GAME_ID%
echo ======================================================================
echo.

call :create_directory "%GAME_ID%" "/" "plugins"
call :create_directory "%GAME_ID%" "/plugins" "Game"
call :create_directory "%GAME_ID%" "/" "config"

call :upload_file "%GAME_ID%" "deploy-pronto\game\plugins\Game.jar" "/plugins" "Game.jar"
call :upload_file "%GAME_ID%" "deploy-pronto\game\plugins\Game\config.yml" "/plugins/Game" "config.yml"
call :upload_file "%GAME_ID%" "deploy-pronto\game\config\paper-global.yml" "/config" "paper-global.yml"

echo.
echo 📊 Deploy do Game concluído!
exit /b 0

REM ============================================================================
REM FUNÇÕES AUXILIARES
REM ============================================================================

:create_directory
set "server_id=%~1"
set "root=%~2"
set "name=%~3"

REM Criar diretório (ignora erro se já existir)
curl -s -X POST ^
    -H "Authorization: Bearer %API_KEY%" ^
    -H "Accept: application/vnd.pterodactyl.v1+json" ^
    -H "Content-Type: application/json" ^
    -d "{\"root\":\"%root%\",\"name\":\"%name%\"}" ^
    "%PANEL_URL%/api/client/servers/%server_id%/files/create-folder" >nul 2>&1

exit /b 0

:upload_file
set "server_id=%~1"
set "local_file=%~2"
set "remote_dir=%~3"
set "filename=%~4"

REM Verificar se arquivo existe
if not exist "%local_file%" (
    echo    ⚠️  Arquivo não encontrado: %filename%
    exit /b 1
)

REM Obter tamanho do arquivo
for %%A in ("%local_file%") do set "filesize=%%~zA"
set /a "filesize_mb=%filesize% / 1048576"

echo    📤 Fazendo upload: %filename% (%filesize_mb% MB^)...

REM URL encode do diretório
set "encoded_dir=%remote_dir:/=%%2F%"

REM Obter URL de upload
for /f "delims=" %%i in ('curl -s -H "Authorization: Bearer %API_KEY%" -H "Accept: application/vnd.pterodactyl.v1+json" "%PANEL_URL%/api/client/servers/%server_id%/files/upload?directory=%encoded_dir%" ^| findstr /C:"\"url\"" ^| for /f "tokens=2 delims=:," %%a in ("%%i") do @echo %%a') do set "upload_url=%%i"

REM Remover aspas
set "upload_url=%upload_url:"=%"

if "%upload_url%"=="" (
    echo    ❌ Erro ao obter URL de upload
    exit /b 1
)

REM Fazer upload
curl -s -X POST ^
    -F "files=@%local_file%" ^
    "%upload_url%?directory=%remote_dir%" >nul 2>&1

if errorlevel 1 (
    echo    ❌ Erro ao fazer upload
    exit /b 1
) else (
    echo    ✅ Upload concluído!
)

exit /b 0

REM ============================================================================
REM FINALIZAÇÃO
REM ============================================================================

:finish
echo.
echo ======================================================================
echo ✅ DEPLOY CONCLUÍDO COM SUCESSO!
echo.
echo 📝 Próximos passos:
echo    1. Reinicie os servidores no painel Pterodactyl
echo    2. Verifique os logs para confirmar que tudo carregou
echo    3. Teste a conexão: admin.savagemc.net:25100
echo ======================================================================
echo.
pause
goto menu

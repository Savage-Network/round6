@echo off
title Round 6 - Iniciar Todos os Servidores
color 0A

echo ╔══════════════════════════════════════════════════════════════╗
echo ║           Round 6 Server - Inicializacao Completa           ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

echo [1/4] Iniciando Auth Server (porta 25579)...
start "Round 6 - Auth" cmd /k "cd auth && start.bat"
echo ✅ Auth Server iniciado!
echo.
echo Aguardando 30 segundos...
timeout /t 30 /nobreak >nul

echo [2/4] Iniciando Hub Server (porta 25578)...
start "Round 6 - Hub" cmd /k "cd hub && start.bat"
echo ✅ Hub Server iniciado!
echo.
echo Aguardando 30 segundos...
timeout /t 30 /nobreak >nul

echo [3/4] Iniciando Game Server (porta 25580)...
start "Round 6 - Game" cmd /k "cd game && start.bat"
echo ✅ Game Server iniciado!
echo.
echo Aguardando 30 segundos...
timeout /t 30 /nobreak >nul

echo [4/4] Iniciando BungeeCord Proxy (porta 25100)...
start "Round 6 - BungeeCord" cmd /k "cd bungeecord && start.bat"
echo ✅ BungeeCord Proxy iniciado!
echo.

echo ╔══════════════════════════════════════════════════════════════╗
echo ║                  Todos os servidores iniciados!              ║
echo ║                                                              ║
echo ║  Conecte em: seu-ip:25100                                   ║
echo ║                                                              ║
echo ║  Verifique as janelas abertas para ver os logs              ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.
pause

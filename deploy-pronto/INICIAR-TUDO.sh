#!/bin/bash

echo "╔══════════════════════════════════════════════════════════════╗"
echo "║           Round 6 Server - Inicializacao Completa           ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo ""

# Dar permissões
chmod +x bungeecord/start.sh
chmod +x auth/start.sh
chmod +x hub/start.sh
chmod +x game/start.sh

echo "[1/4] Iniciando Auth Server (porta 25579)..."
cd auth
gnome-terminal -- bash -c "./start.sh; exec bash" 2>/dev/null || \
xterm -e "./start.sh" 2>/dev/null || \
konsole -e "./start.sh" 2>/dev/null || \
./start.sh &
cd ..
echo "✅ Auth Server iniciado!"
echo ""
echo "Aguardando 30 segundos..."
sleep 30

echo "[2/4] Iniciando Hub Server (porta 25578)..."
cd hub
gnome-terminal -- bash -c "./start.sh; exec bash" 2>/dev/null || \
xterm -e "./start.sh" 2>/dev/null || \
konsole -e "./start.sh" 2>/dev/null || \
./start.sh &
cd ..
echo "✅ Hub Server iniciado!"
echo ""
echo "Aguardando 30 segundos..."
sleep 30

echo "[3/4] Iniciando Game Server (porta 25580)..."
cd game
gnome-terminal -- bash -c "./start.sh; exec bash" 2>/dev/null || \
xterm -e "./start.sh" 2>/dev/null || \
konsole -e "./start.sh" 2>/dev/null || \
./start.sh &
cd ..
echo "✅ Game Server iniciado!"
echo ""
echo "Aguardando 30 segundos..."
sleep 30

echo "[4/4] Iniciando BungeeCord Proxy (porta 25100)..."
cd bungeecord
gnome-terminal -- bash -c "./start.sh; exec bash" 2>/dev/null || \
xterm -e "./start.sh" 2>/dev/null || \
konsole -e "./start.sh" 2>/dev/null || \
./start.sh &
cd ..
echo "✅ BungeeCord Proxy iniciado!"
echo ""

echo "╔══════════════════════════════════════════════════════════════╗"
echo "║                  Todos os servidores iniciados!              ║"
echo "║                                                              ║"
echo "║  Conecte em: seu-ip:25100                                   ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo ""

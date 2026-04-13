#!/bin/bash
echo "========================================"
echo "  Round 6 - Game Server"
echo "  Porta: 25580"
echo "========================================"
echo ""
echo "Iniciando servidor game..."
java -Xms2G -Xmx4G -jar server.jar --nogui

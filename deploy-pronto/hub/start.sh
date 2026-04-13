#!/bin/bash
echo "========================================"
echo "  Round 6 - Hub Server"
echo "  Porta: 25578"
echo "========================================"
echo ""
echo "Iniciando servidor hub..."
java -Xms1G -Xmx2G -jar server.jar --nogui

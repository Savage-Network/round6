#!/bin/bash
echo "========================================"
echo "  Round 6 - Auth Server"
echo "  Porta: 25579"
echo "========================================"
echo ""
echo "Iniciando servidor de autenticacao..."
java -Xms1G -Xmx2G -jar server.jar --nogui

#!/bin/bash
# Script para dar permissão de execução nos scripts .sh

echo "Dando permissão de execução aos scripts..."

chmod +x velocity/start.sh
chmod +x auth/start.sh
chmod +x hub/start.sh
chmod +x game/start.sh

echo "✅ Permissões configuradas!"
echo ""
echo "Agora você pode executar:"
echo "  ./auth/start.sh"
echo "  ./hub/start.sh"
echo "  ./game/start.sh"
echo "  ./velocity/start.sh"

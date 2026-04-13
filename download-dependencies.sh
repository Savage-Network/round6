#!/bin/bash

echo "========================================="
echo "  Baixando Dependências dos Plugins"
echo "========================================="
echo ""

# Cria diretórios
mkdir -p downloads

cd downloads

# ViaVersion
echo "📥 Baixando ViaVersion..."
curl -L -o ViaVersion.jar "https://hangarcdn.papermc.io/plugins/ViaVersion/ViaVersion/versions/5.1.1/PAPER/ViaVersion-5.1.1.jar"

# PlaceholderAPI
echo "📥 Baixando PlaceholderAPI..."
curl -L -o PlaceholderAPI.jar "https://hangarcdn.papermc.io/plugins/HelpChat/PlaceholderAPI/versions/2.11.6/PAPER/PlaceholderAPI-2.11.6.jar"

# LuckPerms
echo "📥 Baixando LuckPerms..."
curl -L -o LuckPerms-Bukkit.jar "https://download.luckperms.net/1556/bukkit/loader/LuckPerms-Bukkit-5.4.141.jar"
curl -L -o LuckPerms-Velocity.jar "https://download.luckperms.net/1556/velocity/LuckPerms-Velocity-5.4.141.jar"

echo ""
echo "✅ Downloads concluídos!"
echo ""
echo "Arquivos baixados em: downloads/"
echo ""
echo "Copie os arquivos para as pastas de plugins:"
echo "  - ViaVersion.jar → auth/plugins/ e hub/plugins/"
echo "  - PlaceholderAPI.jar → hub/plugins/"
echo "  - LuckPerms-Bukkit.jar → auth/plugins/ e hub/plugins/"
echo "  - LuckPerms-Velocity.jar → velocity/plugins/"
echo ""

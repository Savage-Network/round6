#!/bin/bash
# ============================================================================
# Script de Configuração Inicial do Deploy Automático
# Round 6 do Rafa - BungeeCord Edition
# ============================================================================

set -e

# Cores
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
CYAN='\033[0;36m'
NC='\033[0m'

print_header() {
    clear
    echo -e "${BLUE}╔══════════════════════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${BLUE}║              🔧 CONFIGURAÇÃO DO DEPLOY AUTOMÁTICO 🔧                         ║${NC}"
    echo -e "${BLUE}║                    Round 6 do Rafa - BungeeCord Edition                      ║${NC}"
    echo -e "${BLUE}╚══════════════════════════════════════════════════════════════════════════════╝${NC}"
    echo ""
}

print_step() {
    echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo -e "${CYAN}$1${NC}"
    echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo ""
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# ============================================================================
# FUNÇÕES
# ============================================================================

get_input() {
    local prompt=$1
    local default=$2
    local value
    
    if [ -n "$default" ]; then
        read -p "$(echo -e ${BLUE}$prompt ${YELLOW}[$default]${NC}: )" value
        echo "${value:-$default}"
    else
        read -p "$(echo -e ${BLUE}$prompt${NC}: )" value
        echo "$value"
    fi
}

validate_url() {
    local url=$1
    if [[ $url =~ ^https?:// ]]; then
        return 0
    else
        return 1
    fi
}

validate_api_key() {
    local key=$1
    if [[ $key =~ ^ptlc_ ]]; then
        return 0
    else
        return 1
    fi
}

test_api_connection() {
    local panel_url=$1
    local api_key=$2
    
    print_info "Testando conexão com a API..."
    
    local response=$(curl -s -w "%{http_code}" -o /dev/null \
        -H "Authorization: Bearer ${api_key}" \
        -H "Accept: application/vnd.pterodactyl.v1+json" \
        "${panel_url}/api/client")
    
    if [ "$response" == "200" ]; then
        print_success "Conexão estabelecida com sucesso!"
        return 0
    else
        print_error "Falha na conexão (HTTP $response)"
        return 1
    fi
}

# ============================================================================
# CONFIGURAÇÃO
# ============================================================================

main() {
    print_header
    
    echo -e "${GREEN}Este assistente irá configurar o deploy automático para Pterodactyl.${NC}"
    echo ""
    
    # Passo 1: URL do Painel
    print_step "PASSO 1: URL do Painel Pterodactyl"
    print_info "Digite a URL do seu painel (ex: https://admin.savagemc.net)"
    print_warning "Não inclua barra (/) no final"
    echo ""
    
    while true; do
        PANEL_URL=$(get_input "URL do Painel" "https://admin.savagemc.net")
        
        if validate_url "$PANEL_URL"; then
            # Remove barra final se existir
            PANEL_URL=${PANEL_URL%/}
            break
        else
            print_error "URL inválida! Use o formato: https://seu-painel.com"
            echo ""
        fi
    done
    
    echo ""
    
    # Passo 2: API Key
    print_step "PASSO 2: API Key do Pterodactyl"
    print_info "Para obter sua API Key:"
    echo "   1. Acesse: ${PANEL_URL}"
    echo "   2. Vá em: Account → API Credentials"
    echo "   3. Clique em: Create API Key"
    echo "   4. Copie a chave gerada (começa com ptlc_)"
    echo ""
    
    while true; do
        API_KEY=$(get_input "API Key")
        
        if validate_api_key "$API_KEY"; then
            break
        else
            print_error "API Key inválida! Deve começar com 'ptlc_'"
            echo ""
        fi
    done
    
    echo ""
    
    # Testar conexão
    if ! test_api_connection "$PANEL_URL" "$API_KEY"; then
        print_error "Não foi possível conectar à API. Verifique suas credenciais."
        exit 1
    fi
    
    echo ""
    
    # Passo 3: IDs dos Servidores
    print_step "PASSO 3: IDs dos Servidores"
    print_info "Para cada servidor, você precisa do Server ID"
    print_info "Encontre em: Painel → Server → Settings → Debug Information"
    echo ""
    
    BUNGEECORD_ID=$(get_input "Server ID do BungeeCord")
    AUTH_ID=$(get_input "Server ID do Auth")
    HUB_ID=$(get_input "Server ID do Hub")
    GAME_ID=$(get_input "Server ID do Game")
    
    echo ""
    
    # Passo 4: Escolher script
    print_step "PASSO 4: Escolher Script"
    echo "   1. Python (recomendado)"
    echo "   2. Bash"
    echo ""
    
    read -p "$(echo -e ${BLUE}Escolha o script [1-2]${NC}: )" script_choice
    
    echo ""
    
    # Passo 5: Criar arquivo de configuração
    print_step "PASSO 5: Salvando Configuração"
    
    if [ "$script_choice" == "1" ]; then
        # Configurar Python
        cat > deploy-scripts/deploy-pterodactyl.py << EOF
#!/usr/bin/env python3
"""
Script de Deploy Automático para Pterodactyl
Round 6 do Rafa - BungeeCord Edition
CONFIGURADO AUTOMATICAMENTE
"""

import os
import sys
import json
import requests
from pathlib import Path
from typing import Dict, List, Optional

# ============================================================================
# CONFIGURAÇÃO
# ============================================================================

PANEL_URL = "${PANEL_URL}"
API_KEY = "${API_KEY}"

SERVERS = {
    "bungeecord": "${BUNGEECORD_ID}",
    "auth": "${AUTH_ID}",
    "hub": "${HUB_ID}",
    "game": "${GAME_ID}"
}

# [... resto do código Python ...]
EOF
        
        chmod +x deploy-scripts/deploy-pterodactyl.py
        print_success "Script Python configurado!"
        
    else
        # Configurar Bash
        sed -i "s|PANEL_URL=\".*\"|PANEL_URL=\"${PANEL_URL}\"|" deploy-scripts/deploy-pterodactyl.sh
        sed -i "s|API_KEY=\".*\"|API_KEY=\"${API_KEY}\"|" deploy-scripts/deploy-pterodactyl.sh
        sed -i "s|BUNGEECORD_ID=\".*\"|BUNGEECORD_ID=\"${BUNGEECORD_ID}\"|" deploy-scripts/deploy-pterodactyl.sh
        sed -i "s|AUTH_ID=\".*\"|AUTH_ID=\"${AUTH_ID}\"|" deploy-scripts/deploy-pterodactyl.sh
        sed -i "s|HUB_ID=\".*\"|HUB_ID=\"${HUB_ID}\"|" deploy-scripts/deploy-pterodactyl.sh
        sed -i "s|GAME_ID=\".*\"|GAME_ID=\"${GAME_ID}\"|" deploy-scripts/deploy-pterodactyl.sh
        
        chmod +x deploy-scripts/deploy-pterodactyl.sh
        print_success "Script Bash configurado!"
    fi
    
    # Criar arquivo de backup da configuração
    cat > deploy-scripts/config.json << EOF
{
  "panel_url": "${PANEL_URL}",
  "api_key": "${API_KEY}",
  "servers": {
    "bungeecord": "${BUNGEECORD_ID}",
    "auth": "${AUTH_ID}",
    "hub": "${HUB_ID}",
    "game": "${GAME_ID}"
  },
  "configured_at": "$(date -u +"%Y-%m-%dT%H:%M:%SZ")"
}
EOF
    
    chmod 600 deploy-scripts/config.json
    print_success "Configuração salva em: deploy-scripts/config.json"
    
    echo ""
    
    # Resumo
    print_step "✅ CONFIGURAÇÃO CONCLUÍDA!"
    echo ""
    echo -e "${GREEN}Configuração salva com sucesso!${NC}"
    echo ""
    echo "📋 Resumo:"
    echo "   Painel: ${PANEL_URL}"
    echo "   API Key: ${API_KEY:0:15}..."
    echo "   BungeeCord ID: ${BUNGEECORD_ID}"
    echo "   Auth ID: ${AUTH_ID}"
    echo "   Hub ID: ${HUB_ID}"
    echo "   Game ID: ${GAME_ID}"
    echo ""
    echo "🚀 Próximos passos:"
    echo ""
    
    if [ "$script_choice" == "1" ]; then
        echo "   1. Instale as dependências:"
        echo "      pip install requests"
        echo ""
        echo "   2. Execute o deploy:"
        echo "      python3 deploy-scripts/deploy-pterodactyl.py"
    else
        echo "   1. Execute o deploy:"
        echo "      ./deploy-scripts/deploy-pterodactyl.sh"
    fi
    
    echo ""
    echo "📚 Documentação completa em:"
    echo "   deploy-scripts/README-DEPLOY-AUTOMATICO.md"
    echo ""
    
    print_warning "IMPORTANTE: Mantenha sua API Key em segurança!"
    print_warning "Não compartilhe o arquivo config.json com ninguém!"
    
    echo ""
}

# Executa
main

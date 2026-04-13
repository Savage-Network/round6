#!/bin/bash
# Script de inicialização do BungeeCord
# Round 6 do Rafa

java -Xms512M -Xmx512M -XX:+UseG1GC -XX:G1HeapRegionSize=4M -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled -XX:+AlwaysPreTouch -jar BungeeCord.jar

Para usarlo:
# 1. Levantar Redis (background)
docker compose -f TPs-resoluciones/TP4/docker-compose.yml up -d

# 2. Abrir redis-cli (se conecta automáticamente al contenedor redis)
docker compose -f TPs-resoluciones/TP4/docker-compose.yml run cli

# 3. Cuando termines, borrar todo
docker compose -f TPs-resoluciones/TP4/docker-compose.yml down -v

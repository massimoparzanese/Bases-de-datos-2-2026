Para usarlo:
# 1. Levantar Redis (background)
docker compose up -d

# 2. Abrir redis-cli (se conecta automáticamente al contenedor redis)
docker compose run cli

# 3. Cuando termines, borrar todo
docker compose -f down -v

# Etapa 1: Build (gera os arquivos otimizados)
FROM node:20 AS build
WORKDIR /app

# Copia apenas package.json primeiro para aproveitar cache de dependências
COPY package*.json ./
RUN npm install

# Copia o restante do projeto e builda
COPY . .
RUN npm run build

# Etapa 2: Servir com Nginx (produção)
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Remove arquivos padrão do nginx e copia o build do React
RUN rm -rf ./*
COPY --from=build /app/dist ./

# Copia configuração customizada do Nginx (opcional, ver abaixo)
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

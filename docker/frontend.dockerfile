FROM node:18-alpine AS build

WORKDIR /app

COPY frontend/package*.json ./
RUN npm install

COPY ./frontend .

RUN npm install -g @angular/cli

RUN ng build --configuration=production

FROM nginx:alpine

COPY ./docker/nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/dist/my-dear-dairy /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
FROM nginx:latest

#COPY deploy/web/nginx.conf /etc/nginx/nginx.conf
#COPY deploy/web/server.conf /etc/nginx/conf.d/server.conf
COPY src/main/resources/static/css /usr/share/nginx/html/css
COPY src/main/resources/static/js /usr/share/nginx/html/js
COPY src/main/resources/static/img /usr/share/nginx/html/img


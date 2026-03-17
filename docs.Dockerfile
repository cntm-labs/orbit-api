FROM nginx:alpine
COPY ./docs/out /usr/share/nginx/html/orbit-api
COPY ./docs/public/favicon.webp /usr/share/nginx/html/favicon.webp
COPY ./docs/public/favicon.webp /usr/share/nginx/html/favicon.ico
COPY ./docs/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000

FROM swaggerapi/swagger-ui:latest

USER root
RUN mkdir -p /usr/share/nginx/html/specs && apk add --no-cache jq

COPY ./specs-*.yaml /usr/share/nginx/html/specs

RUN rm /docker-entrypoint.d/40-swagger-ui.sh

COPY generate-config.sh /docker-entrypoint.d/35-generate.sh
RUN chmod +x /docker-entrypoint.d/35-generate.sh

EXPOSE 8080

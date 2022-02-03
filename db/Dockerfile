FROM postgres:13.3

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY init_db.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
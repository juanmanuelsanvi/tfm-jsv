FROM mariadb:latest
ADD scripts-bbdd/modelo-fisico.sql /docker-entrypoint-initdb.d/init.sql
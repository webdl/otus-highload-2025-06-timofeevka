# Домашняя работа

1. [HW01: Тестирование REST API через Postman](doc/hw/HW01.md)
2. [HW02: Нагрузочное тестирование с JMeter](doc/hw/HW02.md)
3. [HW03: Репликация и нагрузочное тестирование](doc/hw/HW03.md)
4. [HW04: Лента постов и ее кэширование](doc/hw/HW04.md)
5. [HW05: Масштабируемая подсистема диалогов](doc/hw/HW05.md)

# Окружение

В корневом [docker-compose.yml](docker-compose.yml) находятся общие для всех микросервисов подсистемы:

- **traefik** — для проксирования трафика в микросервисы и системы ниже
- **prometheus** (+[конфигурация](/deploy/prometheus/config/prometheus.yml)) — для сбора метрик с остальных систем
- **grafana** (+[дашборды](/deploy/grafana/dashboards)) — для отображения графиков по метрикам
- **cadvisor** — для мониторинга docker-контейнеров
- **node-exporter** — для мониторинга операционной системы (ставится в т.ч. на сервера с БД)
- **postgres-exporter** — для мониторинга кластера PostgreSQL
- **rabbitmq** — брокер сообщений

В каждом микросервисе свой docker-compose.yml:

- user-service/[docker-compose.yml](user-service/docker-compose.yml)
- post-service/[docker-compose.yml](post-service/docker-compose.yml)
- chat-service/[docker-compose.yml](chat-service/docker-compose.yml)

Traefik отвечает за проксирование во все микросервисы, но требует дополнительной настройки. Для этого добавьте в свой hosts файл строки:

```
127.0.0.1 grafana.socnet.ru prometheus.socnet.ru cadviser.socnet.ru
127.0.0.1 rabbitmq.socnet.ru
127.0.0.1 user.socnet.ru post.socnet.ru chat.socnet.ru
```

# Инициализация user-service

## Переменные окружения

Создайте файл user-service/.env и наполните его содержимым:

```
SERVICE_PORT=8080
SPRING_PROFILES_ACTIVE=dev
SECURITY_JWT_SECRET=fallback-key-only-for-testing

PGHOST=10.0.2.2
PGHOST_SLAVE_1=10.0.2.3
PGHOST_SLAVE_2=10.0.2.4
PGPORT=5432
PGDATABASE=socialnet
PGUSER=socialnet
PGPASSWORD=socialnet
```

## Установка БД

**Обратите внимание!** В Docker compose нет конфигурации серверов PostgreSQL, он должен быть развернут отдельно в кластере из трех
нод. IP-адреса нод указываются в переменных `PGHOST`, `PGHOST_SLAVE_1`, `PGHOST_SLAVE_2`. Необходимо именно 3 ноды, так как в микросервисах
используется множество Data Sources.

## Инициализация данных в БД

Подключитесь к своему серверу PostgreSQL, создайте УЗ и БД:

```sql
CREATE ROLE socialnet WITH
    LOGIN
    PASSWORD 'socialnet';

CREATE DATABASE socialnet
    WITH
    OWNER = socialnet
    ENCODING = 'UTF8';
```

Перейдите в [docker-compose.yml](docker-compose.yml) и запустите приложение `user-service`.

После первого запуска в каталог `/app/target/` контейнера автоматически будет скачан файл `people.v2.csv` из репозитория
https://github.com/OtusTeam/highload/tree/master/homework. Он содержит стартовый набор пользователей.

Зайдите в запущенный образ, подключитесь к базе данных с помощью psql (команду можно выполнить без параметров):

```shell
psql
```

Выполните импорт csv-файла во временную таблицу:

```sql
\COPY tmp_users(full_name, birth_date, city_name) FROM '/app/target/people.v2.csv' WITH (FORMAT csv, DELIMITER ',', HEADER false);
```

Выполнители импорт с преобразованием в таблицу сервиса:

```sql
DROP SEQUENCE IF EXISTS username_seq;
CREATE SEQUENCE username_seq START 1;

INSERT INTO users (first_name, last_name, birth_date, city_id, username, password)
SELECT split_part(full_name, ' ', 2)                                  AS first_name,
       split_part(full_name, ' ', 1)                                  AS last_name,
       birth_date,
       c.city_id,
       ru_to_en(
               lower(split_part(full_name, ' ', 1)
                   || left(split_part(full_name, ' ', 2), 1)
               ))
           || nextval('username_seq')::text                           AS username,
       '$2a$04$OGuod6JuDmEsUdK/MgN4AOpGkn0jjKVLdQE4DLhC1y9FnT6YQFbmi' as password -- пароль "password" для всех пользователей
FROM tmp_users t
         JOIN cities c ON c.name = t.city_name;

DROP SEQUENCE IF EXISTS username_seq;
DROP TABLE IF EXISTS tmp_users;

```

# Инициализация post-service

## Переменные окружения

Создайте файл post-service/.env и наполните его содержимым:

```
SERVICE_PORT=8080
SPRING_PROFILES_ACTIVE=dev

PGHOST=10.0.2.2
PGHOST_SLAVE_1=10.0.2.3
PGHOST_SLAVE_2=10.0.2.4
PGPORT=5432
PGDATABASE=socialnet_post
PGUSER=socialnet
PGPASSWORD=socialnet

USER_SERVICE_URL=http://user-service:8080
```

# Инициализация chat-service

## Переменные окружения

Создайте файл chat-service/.env и наполните его содержимым:

```
SERVICE_PORT=8080
SPRING_PROFILES_ACTIVE=dev

USER_SERVICE_URL=http://user-service:8080

MONGODB_URI=mongodb://mongos1:27017/chatdb
```

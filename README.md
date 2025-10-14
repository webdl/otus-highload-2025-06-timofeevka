# Домашняя работа

1. [HW01: Тестирование REST API через Postman](hw/HW01.md)
2. [HW02: Нагрузочное тестирование с JMeter](hw/HW02.md)
3. [HW03: Репликация и нагрузочное тестирование](hw/HW03.md)
4. [HW04: Лента постов и ее кэширование](hw/HW04.md)
4. [HW05: Масштабируемая подсистема диалогов](hw/HW05.md)

# Среда разработки

Чтобы запускать микросервисы через [docker-compose.yml](docker-compose.yml), в корне проекта создайте файл `.env` с переменными окружения 
ниже.

**Обратите внимание!** В Docker compose нет конфигурации серверов PostgreSQL, он должен быть развернут отдельно в кластере из трех
нод. IP-адреса нод указываются в переменных `PGHOST`, `PGHOST_SLAVE_1`, `PGHOST_SLAVE_2`. Необходимо именно 3 ноды, так как в микросервисах
используется множество Data Sources.

```
SPRING_PROFILES_ACTIVE=dev
SECURITY_JWT_SECRET=fallback-key-only-for-testing

PGHOST=10.0.2.2
PGHOST_SLAVE_1=10.0.2.3
PGHOST_SLAVE_2=10.0.2.4
PGPORT=5432
PGDATABASE=socialnet
PGUSER=socialnet
PGPASSWORD=socialnet

USER_SERVICE_URL=http://user-service:8080

MONGODB_URI=mongodb://mongos1:27017/chatdb
```

# Инициализация user-service

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

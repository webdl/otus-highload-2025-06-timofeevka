# Домашняя работа

1. [HW01: Тестирование REST API через Postman](hw/HW01.md)
2. [HW02: Нагрузочное тестирование с JMeter](hw/HW02.md)
3. [HW03: Репликация и нагрузочное тестирование](hw/HW03.md)

# Подготовка к запуску приложения

В корне проекта создайте файл `.env` с переменными окружения (поменяйте их при необходимости):

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
```

# Инициализация данных в БД

Подключитесь к своему серверу БД PostgreSQL, создайте УЗ и БД:

```sql
CREATE ROLE socialnet WITH
    LOGIN
    PASSWORD 'socialnet';

CREATE DATABASE socialnet
    WITH
    OWNER = socialnet
    ENCODING = 'UTF8';
```

# Инициализация приложения

Перейдите в [docker-compose.yml](docker-compose.yml) и запустите приложение.

**Обратите внимание!**

После первого запуска приложения в каталоге `/app/target/` контейнера автоматически скачается файл `people.v2.csv` из репозитория
https://github.com/OtusTeam/highload/tree/master/homework.

Зайдите в запущенный образ, подключитесь к базе данных с помощью psql (команду можно выполнить без параметров):

```shell
psql
```
```sql
\COPY tmp_users(full_name, birth_date, city_name) FROM '/app/target/people.v2.csv' WITH (FORMAT csv, DELIMITER ',', HEADER false);
```

Далее выполнители запросы ниже:

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

# Запуск приложения локально

1. Создайте УЗ `socialnet` на своем сервере PostgreSQL. Убедитесь, что УЗ может подключаться к серверу по настройкам файла `pg_hba.conf`
2. Создайте БД `socialnet`, установив владельцем УЗ `socialnet`
3. Откройте каталог `socialnetwork` в IDEA Ultimate (умеет работать со Spring)
4. Перейдите в файл [application.properties](src/main/resources/application.properties) и задайте параметры:
   1. spring.datasource.url
   2. spring.datasource.password
5. Запустите приложение в режиме иницилизации, для этого:
   1. создайте отдельную конфигурация запуска с именем `Init Application`
   2. в настройках конфигурации запуска добавьте (Modify options) следующий параметр запуск (Program arguments): `--spring.sql.init.mode=always`
6. Запустите приложение через созданную конфигурацию

После всех действий в вашей БД должны появиться предзаполненные таблицы `users` и `cities`, а REST API приложения будет доступно по адресу 
http://localhost:8080/user/

# Тестирование

1. Загрузите файл [HW01_postman_collection.json](src/test/postman/HW01_postman_collection.json) в Postman
2. Последовательно выполните каждый из запросов, чтобы увидеть возвращаемые результаты

-- Вспомогательная функция для генерации текста
DROP FUNCTION IF EXISTS generate_random_text(words TEXT[], words_length INTEGER, word_count INTEGER);
CREATE OR REPLACE FUNCTION generate_random_text(
    words TEXT[],
    words_length INTEGER,
    word_count INTEGER
) RETURNS TEXT AS
$$
DECLARE
    j           INTEGER;
    random_word TEXT;
    result_text TEXT := '';
BEGIN
    FOR j IN 1..word_count
        LOOP
            random_word := words[1 + floor(random() * words_length)::INTEGER];
            result_text := result_text || random_word;
            IF j < word_count THEN
                IF random() < 0.1 THEN
                    result_text := result_text || ',';
                END IF;
                IF random() < 0.05 THEN
                    result_text := result_text || '. ';
                ELSE
                    result_text := result_text || ' ';
                END IF;
            ELSE
                result_text := result_text || '.';
            END IF;
        END LOOP;

    RETURN result_text;
END;
$$ LANGUAGE plpgsql;

-- Основная функция с входным параметром UUID[]
DROP FUNCTION IF EXISTS generate_random_text(p_user_uuids UUID[], p_total_records INTEGER, p_batch_size INTEGER);

CREATE OR REPLACE FUNCTION generate_random_posts_batch(
    p_user_uuids UUID[],
    p_total_records INTEGER DEFAULT 1000000,
    p_batch_size INTEGER DEFAULT 1000
)
    RETURNS void AS
$$
DECLARE
    batch_count       INTEGER;
    i                 INTEGER;
    j                 INTEGER;
    records_in_batch  INTEGER;
    random_user_id    UUID;
    word_count        INTEGER;
    content_text      TEXT;
    words             TEXT[] := ARRAY [
        'технологии', 'разработка', 'программирование', 'база', 'данных', 'приложение',
        'сервер', 'клиент', 'интерфейс', 'алгоритм', 'структура', 'функция',
        'переменная', 'объект', 'класс', 'метод', 'система', 'сеть', 'безопасность',
        'производительность', 'оптимизация', 'тестирование', 'развертывание', 'мониторинг',
        'анализ', 'проектирование', 'реализация', 'интеграция', 'микросервисы', 'контейнеры',
        'облако', 'искусственный', 'интеллект', 'машинное', 'обучение', 'большие', 'данные',
        'аналитика', 'визуализация', 'инфраструктура', 'автоматизация', 'скрипт', 'библиотека',
        'фреймворк', 'платформа', 'архитектура', 'компонент', 'модуль', 'конфигурация',
        'документация', 'релиз', 'версия', 'обновление', 'патч', 'фича', 'баг', 'отладка',
        'логирование', 'трассировка', 'профилирование', 'кэширование', 'сессия', 'аутентификация',
        'авторизация', 'шифрование', 'токен', 'запрос', 'ответ', 'протокол', 'формат', 'json',
        'xml', 'api', 'endpoint', 'middleware', 'контроллер', 'модель', 'представление',
        'шаблон', 'виджет', 'плагин', 'расширение', 'пакет', 'зависимость', 'репозиторий',
        'коммит', 'ветка', 'мерж', 'конфликт', 'рефакторинг', 'легаси', 'техдолг', 'спринт',
        'бэклог', 'требования', 'спецификация', 'интерфейс', 'взаимодействие', 'интеграция',
        'совместимость', 'производительность', 'надежность', 'масштабируемость', 'доступность',
        'поддержка', 'обслуживание', 'развитие', 'инновации', 'тренды', 'будущее', 'потенциал',
        'компания', 'продукт', 'решение', 'задача', 'проблема', 'вызов', 'возможность', 'результат',
        'качество', 'эффективность', 'удобство', 'функциональность', 'стабильность', 'надежность',
        'безопасность', 'скорость', 'производительность', 'оптимизация', 'улучшение', 'развитие'
        ];
    words_length      INTEGER;
    uuid_array_length INTEGER;
BEGIN
    -- Проверка входных параметров
    IF p_user_uuids IS NULL OR array_length(p_user_uuids, 1) IS NULL THEN
        RAISE EXCEPTION 'Массив UUID не может быть пустым';
    END IF;

    IF p_total_records <= 0 THEN
        RAISE EXCEPTION 'Количество записей должно быть положительным числом';
    END IF;

    IF p_batch_size <= 0 THEN
        RAISE EXCEPTION 'Размер пачки должен быть положительным числом';
    END IF;

    words_length := array_length(words, 1);
    uuid_array_length := array_length(p_user_uuids, 1);
    batch_count := ceil(p_total_records::FLOAT / p_batch_size);

    RAISE NOTICE 'Начало генерации: % записей, пачка: %, пользователей: %',
        p_total_records, p_batch_size, uuid_array_length;

    FOR i IN 1..batch_count
        LOOP
            -- Определяем количество записей в текущей пачке
            records_in_batch := LEAST(p_batch_size, p_total_records - (i - 1) * p_batch_size);

            -- Пакетная вставка с использованием подзапроса
            INSERT INTO posts (user_id, content)
            SELECT
                -- Исправленный расчет индекса массива
                p_user_uuids[1 + (floor(random() * uuid_array_length)::INTEGER % uuid_array_length)],
                generate_random_text(words, words_length, 500 + floor(random() * 501)::INTEGER)
            FROM generate_series(1, records_in_batch);

            RAISE NOTICE 'Обработано записей: %', i * p_batch_size;
        END LOOP;

    RAISE NOTICE 'Генерация завершена. Всего записей: %', p_total_records;
END;
$$ LANGUAGE plpgsql;

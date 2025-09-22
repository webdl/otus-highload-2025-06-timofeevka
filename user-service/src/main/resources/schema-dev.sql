-- Функция и таблица для генерации пользователей
DROP FUNCTION IF EXISTS ru_to_en(input_text text);
CREATE FUNCTION ru_to_en(input_text text) RETURNS text AS
'
DECLARE
    result text;
BEGIN
    result := input_text;

    result := replace(result, ''а'', ''a'');
    result := replace(result, ''б'', ''b'');
    result := replace(result, ''в'', ''v'');
    result := replace(result, ''г'', ''g'');
    result := replace(result, ''д'', ''d'');
    result := replace(result, ''е'', ''e'');
    result := replace(result, ''ё'', ''yo'');
    result := replace(result, ''ж'', ''zh'');
    result := replace(result, ''з'', ''z'');
    result := replace(result, ''и'', ''i'');
    result := replace(result, ''й'', ''y'');
    result := replace(result, ''к'', ''k'');
    result := replace(result, ''л'', ''l'');
    result := replace(result, ''м'', ''m'');
    result := replace(result, ''н'', ''n'');
    result := replace(result, ''о'', ''o'');
    result := replace(result, ''п'', ''p'');
    result := replace(result, ''р'', ''r'');
    result := replace(result, ''с'', ''s'');
    result := replace(result, ''т'', ''t'');
    result := replace(result, ''у'', ''u'');
    result := replace(result, ''ф'', ''f'');
    result := replace(result, ''х'', ''kh'');
    result := replace(result, ''ц'', ''ts'');
    result := replace(result, ''ч'', ''ch'');
    result := replace(result, ''ш'', ''sh'');
    result := replace(result, ''щ'', ''shch'');
    result := replace(result, ''ъ'', '''');
    result := replace(result, ''ы'', ''y'');
    result := replace(result, ''ь'', '''');
    result := replace(result, ''э'', ''e'');
    result := replace(result, ''ю'', ''yu'');
    result := replace(result, ''я'', ''ya'');

    RETURN result;
END;
' LANGUAGE plpgsql IMMUTABLE;

DROP TABLE IF EXISTS tmp_users;
CREATE TABLE tmp_users
(
    full_name  VARCHAR(200),
    birth_date DATE,
    city_name  VARCHAR(100)
);


# Note App

Spring Boot приложение для заметок с PostgreSQL.

## Функции
- Регистрация и вход пользователей
- Создание, просмотр, редактирование, удаление заметок
- Заметки привязаны к конкретному пользователю
- Выход из системы

## Запуск

```bash
# 1. Клонировать
git clone https://github.com/cyxapuk/note-app.git
cd note-app

# 2. Создать базу в PostgreSQL
sudo -u postgres psql -c "CREATE DATABASE noteapp;"
sudo -u postgres psql -c "CREATE USER noteuser WITH PASSWORD 'password';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE noteapp TO noteuser;"

# 3. Настроить конфиг
cp src/main/resources/application.yaml
# Отредактируйте application.yaml под свою базу данных

# 4. Запустить
./mvnw spring-boot:run
Открыть: http://localhost:8080/auth/login

Страницы

    /auth/login — вход

    /auth/register — регистрация

    /notes — список заметок (только для авторизованных)
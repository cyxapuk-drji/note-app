# Note App

Spring Boot приложение для заметок с PostgreSQL.

## Запуск

```bash
# 1. Клонировать
git clone https://github.com/cyxapuk/note-app.git
cd note-app

# 2. Создать базу в PostgreSQL
sudo -u postgres psql -c "CREATE DATABASE noteapp;"
sudo -u postgres psql -c "CREATE USER noteuser WITH PASSWORD 'password';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE noteapp TO noteuser;"

# 3. Запустить
./mvnw spring-boot:run

Открыть: http://localhost:8080/notes

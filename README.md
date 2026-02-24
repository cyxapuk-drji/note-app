# Note App

Spring Boot приложение для заметок с регистрацией, авторизацией и PostgreSQL.

## Возможности
- Регистрация и вход пользователей
- Создание, просмотр, редактирование и удаление заметок
- Заметки привязаны к конкретному пользователю
- Выход из системы
- Полная контейнеризация через Docker

## Технологии
- **Java 17**
- **Spring Boot 4**
- **Spring MVC**
- **Spring Data JPA**
- **PostgreSQL**
- **Thymeleaf**
- **Docker / Docker Compose**
- **Maven**

## Быстрый старт (Docker)

Самый простой способ запустить приложение — использовать Docker Compose.

```bash
# 1. Клонировать репозиторий
git clone https://github.com/cyxapuk-drji/note-app
cd note-app

# 2. Собрать проект (пропуская тесты, т.к. им нужна БД)
./mvnw clean package -DskipTests

# 3. Запустить через Docker Compose
docker-compose up
```
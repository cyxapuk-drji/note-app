# Note&TaskManager — приложение для заметок

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Docker](https://img.shields.io/badge/Docker-blue)
![Maven](https://img.shields.io/badge/Maven-red)

## Описание

REST API на Java Spring Boot и PostgreSQL для создания, организации заметок и задач.

---

## Функции

- Создание, редактирование и удаление заметок или задач
- Теги с выбором цвета
- Docker-контейнеризация
- Полностью открытое API

---

## Технологии

**Бэкенд**
- Java 21
- Spring Boot 4.0
- Spring Data JPA
- Swagger/OpenAPI
- PostgreSQL 15
- Docker & Docker Compose
- Hibernate
- Maven
---

## Установка и запуск

### Требования
- Java 21
- Docker и Docker Compose (опционально)

### Быстрый старт (через Docker)

```bash
git clone https://github.com/drji-dev/Note-Task-Manager.git
cd Note-Task-Manager
./mvnw clean package -DskipTests
docker-compose up -d
```
Приложение будет доступно по адресу: http://localhost:8080

## Запуск без Docker

    Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE your_db_name;
CREATE USER your_username WITH
GRANT ALL PRIVILEGES ON DATABASE
```
    Настройте подключение в src/main/resources/application.yml:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/your_db_name
    username: your_user_db_name
    password: your_user_password
```
    Запустите приложение:

```bash
./mvnw spring-boot:run
```
## Переменные окружения (для Docker)

Создайте файл .env в корне проекта:
```env
DB_NAME=your_db_name
DB_USERNAME=your_user_db_name
DB_PASSWORD=your_password
```

### REST API эндпоинты

## Теги
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/tags/{id}` | Получить тег по ID |
| POST | `/api/tags` | Создать тег |
| PUT | `/api/tags/{id}` | Обновить тег |
| DELETE | `/api/tags/{id}` | Удалить тег |

## Заметки
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/items?tag=your_tagName` | Получить все заметки по TagName
| GET | `/api/items?type=NOTE/TASK` | Получить все заметки по типу заметки
| GET | `/api/items/favorites` | Получить фаворитные заметки
| GET | `/api/items/{id}` | Получить item по ID |
| POST | `/api/items` | Создать item |
| PUT | `/api/items/{id}` | Обновить item |
| DELETE | `/api/items/{id}` | Удалить item |

## Документация API

После запуска приложения документация доступна по адресу:

http://localhost:8080/swagger-ui/index.html

## Примеры запросов через curl

### Создать тег
```bash
curl -X POST http://localhost:8080/api/tags 
-H "Content-Type: application/json" 
-H "User-Id: 1" -d '
    {
        "tagName":"your_tagName",
        "color":"your_color"
    }' 
```
Цвет по умолчанию #FFFFFF

### Создать item
```bash
curl -X POST http://localhost:8080/api/items 
-H "Content-Type: application/json" 
-H "User-Id: 1" -d '
    {
        "title":"your_title",
        "content":"your_content",
        "tagName":"your_tagName",
        "type":"TASK/NOTE",
        "priority":"LOW/MEDIUM/HIGH"
    }' 
```
Eсли type:NOTE priority игнорируется

## Автор: 
Drji

## Лицензия

MIT License

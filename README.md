# Note&TaskManager — приложение для заметок

![Java](https://img.shields.io/badge/Java-21-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue)
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
- PostgreSQL 18
- Docker & Docker Compose
- Maven
---

## Установка и запуск

### Быстрый старт (Docker-Compose)

### Требования
- Java 21
- Docker и Docker Compose

```bash
git clone https://github.com/drji-dev/Note-Task-Manager.git
cd Note-Task-Manager
./mvnw clean package -DskipTests
docker-compose up --build
```

    Создайте файл .env в корне проекта:
```env
DOCKER_DB_URL=your_DB_url
DOCKER_DB_NAME=your_DB_name
DOCKER_DB_USERNAME=your_DB_username
DOCKER_DB_PASSWORD=your_DB_password
JWT_SECRET=your_secret_key
JWT_LIFE_TIME=86400000 # 1 day
```

Приложение будет доступно по адресу: http://localhost:8080

## Запуск без Docker

### Требования
- Java 21
- PostgreSQL 18


        Создайте файл .env в корне проекта или добавте к существующему:
```env
LOCAL_DB_URL=your_DB_url
DB_USERNAME=your_DB_username
DB_PASSWORD=your_DB_password
JWT_SECRET=your_secret_key
JWT_LIFE_TIME=86400000 #1 day
```

    Создайте базу данных PostgreSQL:

```sql
CREATE DATABASE your_db_name;
CREATE USER your_username WITH
GRANT ALL PRIVILEGES ON DATABASE
```
    Запустите приложение:

```bash
./mvnw spring-boot:run
```

### REST API эндпоинты

## Авторизация
| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/auth/signup` | Создать пользователя |
| POST | `/api/auth/signin` | Авторизация/получение jwt токена |

### Для доступа к остальным REST API эндпоинтам нужен jwt токен полученый после авторизации

## Теги
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/tags/{id}` | Получить тег по ID |
| POST | `/api/tags` | Создать тег |
| PUT | `/api/tags/{id}` | Обновить тег |
| DELETE | `/api/tags/{id}` | Удалить тег |

## Заметки/Задачи
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

## Автор: 
Drji

## Лицензия

MIT License

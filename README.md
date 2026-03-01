# NoteApp — приложение для заметок

Веб-приложение на Java Spring Boot и PostgreSQL для создания и организации заметок.

**Автор:** cyxapuk

---

## Функции

- Создание и редактирование заметок
- Категории с выбором цвета
- Избранное
- Фильтрация заметок по категориям
- Счётчик заметок в каждой категории
- Регистрация и вход
- Защита от редактирования чужих заметок
- Masonry-сетка

---

## Технологии

**Бэкенд**
- Java 21
- Spring Boot 3.2
- Spring Data JPA
- Spring MVC
- PostgreSQL 15
- Hibernate
- Maven

**Фронтенд**
- HTML5 / CSS3
- Thymeleaf
- JavaScript

**Инфраструктура**
- Docker
- Docker Compose
- Git

---

## Установка и запуск

### Требования
- Java 21
- Docker и Docker Compose

### Быстрый старт (через Docker)

```bash
git clone https://github.com/cyxapuk/note-app
cd note-app
./mvnw clean package -DskipTests
docker-compose up
```
Приложение будет доступно по адресу: http://localhost:8080

## Запуск без Docker

```bash
sudo -u postgres psql -c "CREATE DATABASE noteapp;"
sudo -u postgres psql -c "CREATE USER your_username WITH PASSWORD 'your_password';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE noteapp TO your_password;"
./mvnw spring-boot:run
```
## В файле src/main/resources/application.yml нужно указать свои данные:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/noteapp
    username: your_username
    password: your_password
```

## По умолчанию используются:

   - База: noteapp

   - Пользователь: admin

   - Пароль: admin

## Основные эндпоинты

### Заметки
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/notes` | Все заметки |
| GET | `/notes?categoryId={id}` | Заметки по категории |
| POST | `/notes` | Создать заметку |
| POST | `/notes/delete/{id}` | Удалить заметку |
| POST | `/notes/update/{id}` | Обновить заметку |
| POST | `/notes/favorite/toggle/{id}` | Переключить избранное |
| GET | `/notes/edit/{id}` | Страница редактирования |

### Категории
| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/categories/create` | Создать категорию |
| POST | `/categories/delete/{id}` | Удалить категорию |

### Авторизация
| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/auth/login` | Страница входа |
| POST | `/auth/login` | Вход |
| GET | `/auth/register` | Страница регистрации |
| POST | `/auth/register` | Регистрация |
| GET | `/auth/change-password` | Страница смены пароля |
| POST | `/auth/change-password` | Смена пароля |
| GET | `/auth/logout` | Выход |OST	/categories/delete/{id}	Удалить категорию

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    color VARCHAR(50),
    user_id BIGINT REFERENCES users(id)
);

CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    favorite BOOLEAN DEFAULT FALSE,
    user_id BIGINT REFERENCES users(id),
    category_id BIGINT REFERENCES categories(id)
);
```

## Лицензия

MIT
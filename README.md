# Project Cost Estimator Backend (Spring Boot 3, Java 21)

Backend API для системы оценки стоимости проектов.

## Требования:
- Java 21 (JDK)
- Docker и Docker Compose (для PostgreSQL)
- Интернет для загрузки зависимостей Gradle

## Запуск с PostgreSQL:

### 1. Настройка переменных окружения
Скопируйте `env.example` в `.env` и настройте параметры:
```bash
cp env.example .env
```

### 2. Запуск через Docker Compose
```bash
docker-compose up -d
```

Это запустит:
- PostgreSQL 15 на порту 5432
- Spring Boot приложение на порту 8080

### 3. Локальный запуск (без Docker)
Если PostgreSQL уже запущен локально:
```bash
# Windows PowerShell
./gradlew.bat bootRun

# Unix/macOS
./gradlew bootRun
```

## API Endpoints:

### Estimates
- `GET /api/estimates` - список всех оценок
- `GET /api/estimates/{id}` - получить оценку по ID
- `POST /api/estimates` - создать новую оценку
- `PUT /api/estimates/{id}` - обновить оценку
- `DELETE /api/estimates/{id}` - удалить оценку

### Estimate Items
- `POST /api/estimates/{id}/items` - добавить элемент к оценке
- `DELETE /api/estimates/{estimateId}/items/{itemId}` - удалить элемент из оценки

## Структура данных:

Оценка содержит:
- Основную информацию (название, клиент, валюта, общая стоимость)
- Уровень качества (low, basic, standard, high)
- Статус (Актуальный, Не актуальный)
- Параметры проекта (JSON)
- Задачи (JSON)

## Миграции:
- Управляются Flyway, скрипты в `src/main/resources/db/migration`
- V1: Базовая структура таблиц
- V2: Добавлены поля для полных данных проекта
- V3: Добавлены таблицы параметров и задач
- V4: Добавлена таблица оценок задач

## Сборка:
```bash
./gradlew build
```

## Переменные окружения:
- `DB_HOST` - хост PostgreSQL (по умолчанию: localhost)
- `DB_PORT` - порт PostgreSQL (по умолчанию: 5432)
- `DB_NAME` - имя базы данных (по умолчанию: estimator_db)
- `DB_USERNAME` - пользователь PostgreSQL (по умолчанию: postgres)
- `DB_PASSWORD` - пароль PostgreSQL (по умолчанию: postgres)
- `SERVER_PORT` - порт приложения (по умолчанию: 8080)



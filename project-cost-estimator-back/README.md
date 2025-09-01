# Project Cost Estimator Backend (Spring Boot 3, Java 21)

Backend API для системы оценки стоимости проектов.

## Запуск локально:

- Требуется: Java 21 (JDK), интернет для загрузки зависимостей Gradle
- Команды:
  - Windows PowerShell: `./gradlew.bat bootRun`
  - Unix/macOS: `./gradlew bootRun`

После запуска:
- REST API: `http://localhost:8080/api/estimates`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/estimator-db`)

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

## Сборка:
- `./gradlew build`



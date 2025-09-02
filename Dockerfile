# Многоэтапный Dockerfile для Spring Boot приложения
FROM eclipse-temurin:21-jdk-alpine AS build

# Установка рабочей директории
WORKDIR /app

# Копирование файлов Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Установка прав на выполнение для gradlew
RUN chmod +x ./gradlew

# Скачивание зависимостей (кэширование слоя)
RUN ./gradlew dependencies --no-daemon

# Копирование исходного кода
COPY src src

# Сборка приложения
RUN ./gradlew build -x test --no-daemon

# Второй этап - создание runtime образа
FROM eclipse-temurin:21-jre-alpine

# Установка рабочей директории
WORKDIR /app

# Создание пользователя для безопасности
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Копирование собранного JAR файла из первого этапа
COPY --from=build /app/build/libs/*.jar app.jar

# Создание директории для данных H2
RUN mkdir -p /app/data && \
    chown -R appuser:appgroup /app

# Переключение на непривилегированного пользователя
USER appuser

# Открытие порта
EXPOSE 8080

# Переменные окружения
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Команда запуска
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]


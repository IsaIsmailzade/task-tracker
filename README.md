# 📋 Task Tracker API

> Многопользовательский планировщик задач в стиле Trello с микросервисной архитектурой


## 🚀 Обзор проекта

Task Tracker API — это современное решение для управления задачами, вдохновленное функциональностью Trello. Проект реализован с использованием микросервисной архитектуры и предоставляет полнофункциональный REST API для создания TODO-листов.


## 🛠️ Технологический стек

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-green?style=flat-square&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-blue?style=flat-square&logo=docker)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-black?style=flat-square&logo=apache-kafka)

### **Основные технологии:**
- **Java 17**
- **Gradle**
- **Spring Framework:**
    - Spring Boot
    - Spring Web
    - Spring Security (JWT)
    - Spring Data JPA
    - Spring for Apache Kafka
    - Spring Scheduler
    - Spring Mail
- **PostgreSQL**
- **Liquibase**
- **Apache Kafka**
- **JUnit & Mockito**
- **Testcontainers**
- **Docker**
- **Swagger (OpenAPI)**
- **CI/CD, GitHub Actions**


## 🏗️ Архитектура приложения

Проект построен на основе микросервисной архитектуры с моно-репозиторием и включает три основных микросервиса:

### 🔧 Backend Service
**Основной сервис** для работы с бизнес-логикой
- REST API для управления пользователями
- Система аутентификации и авторизации (JWT)
- Интеграция с Kafka для отправки уведомлений в топик

### 📧 Email Sender Service
**Сервис уведомлений** для отправки email
- Подписка на Kafka топик `EMAIL_SENDING_TASKS`
- Автоматическая десериализация сообщений
- Отправка email через SMTP

### ⏰ Scheduler Service
**Сервис планировщика** для автоматической отчетности
- Ежедневное формирование отчетов о задачах
- Анализ изменений за сутки для каждого пользователя
- Автоматическая отправка сводок через Kafka в топик каждую полночь


## 🔄 CI/CD

Автоматизированный процесс развертывания включает:

- **Continuous Integration:**
  - Автоматическая сборка Docker образов
  - Публикация образов в [Docker Hub](https://hub.docker.com/)

- **Continuous Deployment:**
  - Автоматический деплой на удаленный сервер
  - Запуск при каждом push в главную ветку

## 🚀 Инструкция по запуску проекта на локальном компьютере

### Предварительные требования
- [Установить Docker Desktop](https://www.docker.com/products/docker-desktop/) на локальную машину
- [Установить Git](https://git-scm.com/) для клонирования репозитория

### Запуск

1. **Склонируйте репозиторий приложения:**
   ```bash
   git clone https://github.com/IsaIsmailzade/task-tracker.git
   ```

2. **Перейдите в корневую папку проекта:**
   ```bash
   cd task-tracker
   ```

3. **Сделайте копию файла .env.example с новым названием:**
   ```bash
   cp .env.example .env
   ```

4. **Настройте переменные окружения в новом файле. Вставьте свои данные для настройки отправки сообщений по протоколу SMTP. Можно использовать SMTP-сервис [Mail.ru](https://help.mail.ru/mail/mailer/settings/).**
   ```bash
   # Образец сконфигурированного .env файла
   
   # База данных
   DB_URL=jdbc:postgresql://db:5432/task_tracker
   DB_USER=postgres
   DB_PASSWORD=1234
   DB_NAME=task_tracker
   DB_DRIVER=org.postgresql.Driver
   
   # JWT настройки
   JWT_SECRET=secret_key # Генерация секретного ключа: openssl rand -hex 32 
   JWT_ACCESS_TOKEN_EXPIRATION=1d
   
   # Kafka конфигурация
   KAFKA_BOOTSTRAP_SERVERS=kafka:29092
   KAFKA_TOPIC=EMAIL_SENDING_TASKS
   KAFKA_CONSUMER_GROUP_ID=email-sender-consumer
   
   # SMTP настройки (пример для Mail.ru)
   MAIL_HOST=smtp.mail.ru
   MAIL_PORT=587
   MAIL_USERNAME=username@mail.ru
   MAIL_PASSWORD=password 
   # Пароль можете создать с помощью инструкции отсюда: https://help.mail.ru/mail/mailer/password/
   
   # Планировщик
   SCHEDULER_CRON=0 0 0 * * ?
   SCHEDULER_CRON_NEW=@daily
   SCHEDULER_TASKS_COUNT_LIMIT=5
   SCHEDULER_MESSAGE_TITLE=Task Tracker: Day's Report
   ```

5. **(Опционально) Если хотите проверить работоспособность планировщика, измените почтовые адреса на свои в [этом](https://github.com/IsaIsmailzade/task-tracker/blob/6f9089c58712f80bf188db27158c7a6a59494fef/task-tracker-backend/src/main/resources/db/changelog/2025-07-04-insert-users-and-tasks-data.sql) SQL-скрипте миграции.  
Также надо изменить аннотацию @Scheduled на тестовую в классе [ReportService](https://github.com/IsaIsmailzade/task-tracker/blob/6f9089c58712f80bf188db27158c7a6a59494fef/task-tracker-scheduler/src/main/java/com/isa/scheduler/service/ReportService.java), чтобы он отправлял отчет на почту не в полночь, а каждые 2 минуты.**

6. **Запустите приложение:**
   ```bash
   docker-compose -f compose-local.yaml up --build
   ```


## 📚 API Документация

После запуска приложения документация Swagger UI будет доступна по адресу:

**🔗 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**


## 🧪 Тестирование

### Предварительные требования
- **Запустите Docker Desktop.**
- **Перейдите в папку микросервиса**  
  Откройте терминал и перейдите в директорию микросервиса, в котором вы хотите запустить тесты:
   ```bash
   cd task-tracker-*
   ```

### Запуск тестов локально:

1. **В зависимости от вашей операционной системы выполните следующие действия:**

   ### Для Windows
   Выполните следующую команду для очистки и запуска тестов:
   ```bash
   gradlew.bat clean test
   ```

   ### Для Unix-систем (Linux/Mac)
    1. Предоставьте права на выполнение Gradle wrapper:
       ```bash
       chmod +x gradlew
       ```
    2. Запустите тесты:
       ```bash
       ./gradlew clean test
       ```


## 📦 Структура проекта

```
task-tracker/
├── .github/workflows/          # CI/CD конфигурация
├── task-tracker-backend/       # Основной REST API сервис
├── task-tracker-email-sender/  # Сервис отправки email
├── task-tracker-scheduler/     # Сервис планировщика
├── compose.yaml                # Конфигурация контейнеров
└── README.md
```

## 🤝 Вклад в проект

1. Сделайте Fork репозитория
2. Создайте ветку для новой функции (`git checkout -b feature/new-feature`)
3. Зафиксируйте изменения (`git commit -m 'Add new feature'`)
4. Отправьте в ветку (`git push origin feature/new-feature`)
5. Создайте Pull Request

## 👨‍💻 Автор

**Isa Ismailzade**
- GitHub: [@IsaIsmailzade](https://github.com/IsaIsmailzade)
- Telegram: [@isa_ismailzade](https://t.me/isa_ismailzade)

---

⭐ Если понравился проект, поставьте звездочку!
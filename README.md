# Training Daily Service
## Task2 - JDBC. Migrations database
### Requirements
* JDK 17
* gradle 8.4
### Install
* git clone git@github.com:sushilyaz/Ylab.git
* git checkout task1
* do task gradle -> build
* docker compose up -d (or: docker-compose up -d)
* java -jar .\build\libs\Ylab-1.0-SNAPSHOT.jar
### Features
* При запуске приложения уже будет существовать администратор и один обычный пользователь:

Admin:

login: admin; password: admin

Simple user:

login:user1; password: user1

* Типы уже имеющихся тренировок: "gym", "yoga"
* Новые типы тренировок может добавлять только администратор
* Для получения сженных калорий в диапазоне времени, внимательно читать sout перед вводом (границы вводимого диапазона не включительны)

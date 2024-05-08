# Training Daily Service
## Task4 - Spring
### Requirements
* JDK 17
* gradle 8.4
### Install
* git clone git@github.com:sushilyaz/Ylab.git
* git checkout task3
* docker compose up -d (or: docker-compose up -d)
* add Tomcat 10
* run

### Features
* При запуске приложения уже будет существовать администратор и один обычный пользователь:
  Admin:
  login: admin; password: admin
  Simple user:
  login:user1; password: user1
* Типы уже имеющихся тренировок: "gym", "yoga"
* Новые типы тренировок может добавлять только администратор
* Для получения сженных калорий в диапазоне времени, учесть границы вводимого диапазона (не включительны)

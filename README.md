__README__
============

Данное приложение является библиотечным сервисом, предназначенным для отслеживания статуса книг. 
Использованный стек: Java SE 17, Spring Boot 3.2.3, Maven, JUnit, Mockito, REST-архитектура.

Описание контроллера LendingController, формат входящих и исходящих параметров:
----------

*__Метод getAllBooks__* - принимает запросы GET на http://localhost:8080/api/v1/lending/books 

Он возвращает JSON с кодом ```200``` и листом книг, зарегистрированных в базе. 

В случае, если в базе нет книг, об этом будет выдано сообщение с кодом ```404```. 

*__Метод getClientsByBookIsbn__* - принимает запросы GET на http://localhost:8080/api/v1/lending/clients/{isbn} 

В пути запроса необходимо указать ISBN зарегистрированной в библиотеке книги. 

В случае корректного запроса метод возвращает JSON с кодом ```200``` и листом клиентов, которым была выдана книга с таким 
ISBN.

В случае, если в базе нет книги с запрошенным ISBN, об этом будет выдано сообщение с кодом ```404```. 
В случае, если в книга с запрошенным ISBN в момент запрос не является выданной какому-либо клиенту, об этом будет 
выдано сообщение с кодом ```404```.

*__Метод lendBook__* - принимает запросы POST на http://localhost:8080/api/v1/lending/lend-book

В качестве параметров запроса необходимо указать ISBN книги (String), номер библиотечной карточки клиента (String), 
количество дней, на которой выдается книга (Integer). Все параметры обязательны для заполнения.

В качестве количества дней, на которой выдается книга, принимается только число больше нуля.

В случае корректного запроса метод вносит запись о выдаче книги в базу и возвращает JSON с кодом ```200``` и 
информацией о выдаче книги (ISBN книги, номер библиотечной карточки клиента, дата и время окончания срока выдачи).

В случае, если в базе нет книги с запрошенным ISBN, об этом будет выдано сообщение с кодом ```404```.
В случае, если в базе нет клиента с запрошенным номером библиотечной карточки, об этом будет выдано сообщение 
с кодом ```404```. В случае, если в базе имеется информация, что книга с запрошенным ISBN уже выдана клиенту с 
запрошенным номером библиотечной карточки, об этом будет выдано сообщение с кодом ```400```. В случае, если выдача 
книги клиенту приведет к превышению максимального количества книг в библиотеке, об этом будет выдано сообщение 
с кодом ```400```.

*__Метод returnBook__* - принимает запросы DELETE на http://localhost:8080/api/v1/lending/return-book

В качестве параметров запроса необходимо указать ISBN книги (String), номер библиотечной карточки клиента (String).

В случае корректного запроса метод удаляет запись о выдаче книги из базы и возвращает JSON с кодом ```200``` 
и сообщением о возврате книги.

В случае, если в базе нет клиента с запрошенным номером библиотечной карточки, об этом будет выдано сообщение
с кодом ```404```. В случае, если в базе нет книги с запрошенным ISBN, об этом будет выдано сообщение с кодом 
```404```. В случае, если в базе не информации, что книга с запрошенным ISBN выдана клиенту с запрошенным номером 
библиотечной карточки, об этом будет выдано сообщение с кодом ```404```.

Уведомления:
-------------

В приложении имеется сервис уведомлений в ```com/libraryapp/notifications``` (без реализации отправки уведомления). 

*__getLendingRecordsToReturn__* в ```com/libraryapp/notifications/SchedulerService.java``` с заданной периодичностью 
проводит поиск книг, по которым нужно отправить уведомление.

Настроить метод можно в ```src/main/resources/application.yml```:

1) ```scheduler.lendingTerm.daysBeforeLendingTermExpiry``` - здесь можно установить количество дней перед датой окончания 
срока выдачи, за которое посылается уведомление.

2) ```scheduler.lendingTerm.cron``` - здесь можно установить периодичность, с которой будет запускаться метод.

В ```com/libraryapp/notifications/NotificationStrategyEmail.java``` находится заглушка отправки уведомления. 
В настоящее время она выдает запись в лог о том, что клиенту с указанным номер читательского билета нужно вернуть 
книгу с указанным ISBN до указанных даты и времени.

Тестирование:
-------------

К приложению подключена база H2. В ```src/main/resources``` имеется файл ```data.sql``` с тестовыми данными, 
необходимый для интеграционных тестов.

Тесты находятся в ```src/main/test```.

Также тестирование можно провести вручную. Добавить клиентов и книги в базу можно с помощью контроллера 
*__RegistrationController__*:

*__Метод createClient__* - принимает запросы POST на http://localhost:8080/api/v1/registration/register_client

В теле запроса нужно указать номер библиотечной карточки клиента (String), ФИО клиента (String). Номер библиотечной 
карточки клиента должен быть уникальным. Все поля обязательны для заполнения.

*__Метод createBook__* - принимает запросы POST на http://localhost:8080/api/v1/registration/register_book

В теле запроса нужно указать ISBN (String), ФИО автора (String), название книги (String), 
дату публикации книги (LocalDate), максимальное количество копий книги в библиотеке (Integer), максимальное количество
выданных клиентам копий книги (Integer). Максимальное количество выданных клиентам копий книги нужно поставить на 
```0```.
ISBN должен быть уникальным. Все поля обязательны для заполнения.

Приложение поддерживает Swagger. Получить доступ к Swagger UI можно по адресу http://localhost:8080/swagger-ui.html.

Подключиться к базе можно по адресу: http://localhost:8080/h2-console/

Установка и запуск:
-------------

*Вариант 1: запуск в Docker container*

> На устройстве должен быть установлен Docker. Склонируйте репозиторий и в корневой директории откройте командную 
> строку и выполните команду:
>
> ```docker-compose build```
>
> Затем выполните команду для запуска приложения:
>
> ```docker-compose up -d```

*Вариант 2: запуск из командной строки*

> На устройстве должна быть установлена Java. Склонируйте репозиторий и в корневой директории откройте GitBash 
> и выполните команду:
>
> ```./mvnw clean package```
>
> В директории library-app\target появится jar файл. Откройте GitBash в этой директории и выполните команду 
> для запуска приложения:
>
> java -jar [имя jar файла]
>
> Например:
>
> ```java -jar library-app-1.0.0-SNAPSHOT.jar```

После запуска приложение будет доступно по адресу: http://localhost:8080

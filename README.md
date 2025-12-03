### Petstore autotests project

Пример проекта автотестов для сервиса petstore.
Проект включает функциональные тесты, проверяющие работу методов API:
> *petstore.swagger.io*.

API для теста: [ссылка](https://petstore.swagger.io)

Стек: Java, Junit5, REST Assured

Для запуска тестов необходимо выполнить:  
`mvn clean test`  
Для просмотра отчет необходимо выполнить:  
`mvn allure:serve`
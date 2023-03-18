1. Запустить Docker Toolbox
2. Запустить IntelliJ IDEA
3. Terminal: docker-compose up
4. Terminal: java -jar artifacts/aqa-shop.jar
5. Перейти в браузере по адресу http://localhost:8080/

Для запуска тестов в терминале ввести команду:
- ./gradlew clean test

Для получения отчета по тестирования из Allure ввести в терминал команду:
- ./gradlew allureServe

После завершения тестирования для выхода ввести в терминале Ctrl+C, далее для подтверждения нажать Y.
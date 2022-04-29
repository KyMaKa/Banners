# JarSoft Test Task
#### Made by Ivan Shatalov

## Installation and usage.
* Create MySql DataBase.
* In application.properties file change datasource username, password to   match yours. Also set datasource url to yours (jdbc:mysql://${MYSQL_HOST:localhost}:{port of your mysql server}/{name of your database})
* In main directory (AdBanners) open console and type: ./mvnw spring-boot:run to start the server.
* Go to http://localhost:8080/ for admin panel. (user: user, password: password)
* Or to http://localhost:8080/bid for public page. (Please note that valid address is http://localhost:8080/bid?cat={somecategoryname}.)

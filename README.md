# skydive-app

## Requirements
### Backend:
* Java JRE
* Maven
* MYSQL Database (Docker)

### Frontend:
* NPM

## How to run
1. Copy `.env.template` and rename it as `.env` Configure environment variables
2. Setup database
    * `docker run -d --name skydive-db -e MYSQL_USER=test -e MYSQL_PASSWORD=test -e MYSQL_DATABASE=skydive -e MYSQL_ROOT_PASSWORD=test -p 3306:3306 mysql:5.7 --skip-name-resolve` with Docker
    * `mvn liquibase:dropAll` to clear database
3. Setup java server
    * `mvn install` to install dependencies. Usually IDEs do this automatically
    * `mvn package` to to setup project
    * `mvn clean` if everything else fails and retry.

## Development
* This project uses Snoozy, which comes automatically configured with Jubics Checkstyle. Install CheckStyle plugin to your IDE and add new config: `https://raw.githubusercontent.com/jubicoy/checkstyle/master/src/main/resources/checkstyle.xml`
* When using dependency injection, often times program doesn't rerun without error. Run `mvn clean` and rerun backend to fix this.
* When creating db migrations, use `mvn jooq-codegen:generate` to generate new classes to db package. If software doesn't rerun because of foreign key issues, run `mvn liquibase:dropAll` to clear database.
* When creating commit, make sure that your application (front & back) doesn't have errors `mvn test` & `npm run test`.
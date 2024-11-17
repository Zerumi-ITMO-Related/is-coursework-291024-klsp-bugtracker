# is-coursework-291024-klsp-bugtracker

Kotlin Language Server Protocol Implementation Bugtracker

---

## What is this?

This is back-end of bugtracker aimed for Kotlin Language Server. Part of ITMO University 3rd year term paper.

---

## How to build?

1. `git clone https://github.com/Zerumi-ITMO-Related/is-coursework-291024-klsp-bugtracker`
2. Provide `src/main/resources/application.properties` file (in example below using Postgre database):

        spring.application.name=is-coursework-291024-klsp-bugtracker
        spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/studs
        spring.datasource.username=XXXXX
        spring.datasource.password=XXXXXXXX
        spring.datasource.driver-class-name=org.postgresql.Driver
        spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
        spring.jpa.generate-ddl=true
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true

3. Build using Gradle `gradle bootRun`/`gradle bootJar`. The database schema should be generated automatically.



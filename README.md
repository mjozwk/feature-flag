# Introduction
feature-flag is a service that allows you to manage feature flags for users. The service is implemented using Spring Boot and Spring Data and REST API is provided.
# Features
* Create a feature flag for a user
* Get a feature flag for a user
* Update a feature flag for a user

# Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
## Prerequisites
You need to have Java 17 installed.
# Installation
To install the service, you need to clone the repository and build the project using Gradle.
```bash
git clone https://github.com/mjozwk/feature-flag.git

cd feature-flag

./gradlew build
```


## Running the tests
To run the tests, you need to run the following command:
```bash
./gradlew test
```

## Deployment
To deploy the service, you need to run the following command:
```bash
./gradlew bootRun
```

## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
* [Spring Data](https://spring.io/projects/spring-data) - Data Access
* [Lombok](https://projectlombok.org/) - Library for reducing boilerplate code
* [JUnit](https://junit.org/junit5/) - Testing framework
* [Mockito](https://site.mockito.org/) - Mocking framework
* [AssertJ](https://assertj.github.io/doc/) - Assertion library
* [Spring Boot Test](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing) - Testing framework for Spring Boot applications
* [Spring Data JPA Test](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#test) - Testing framework for Spring Data JPA

## Author
* **Marcin Jóźwiak** - https://www.linkedin.com/in/marcin-j%C3%B3%C5%BAwiak-67157b97/

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
### Things to improve

To authorize incoming requests, we can consider using the @PreAuthorize mechanism.

You can consider creating a class to handle returned HTTP statuses, if we know that more such functionality will come.

In the next iteration, the inclusion of the global flag could be implemented. So, to disable the flag in the entire system, we need to update the data for all users.

In the integration test, the creation and loading of data into the database could be improved.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

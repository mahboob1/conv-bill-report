### Intuit Services Platform (ISP) Reference Service built on [Spring Boot MVC](https://spring.io/guides/gs/serving-web-content/) and [JSK](https://github.intuit.com/services-java/what-is-jsk)

### About the Example

**Hello-World Service** is a simple [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) service that stores and retrieves documents in-memory. 

### Prerequisites
* [Java 1.8 OR later](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 

* [Maven 3.1 OR later](https://maven.apache.org/download.cgi) 

#### How to run the application locally

From Terminal line run this command:

```
   mvn clean spring-boot:run -s settings.xml
```

To make sure the application is running, check its health


```
curl https://localhost:8443/health/full

```

#### How to run unit tests

```
    mvn test -Punit
```

#### How to run karate tests

run this command for testing against locally running server with e2e downstream services

```
    mvn test -Pkarate -Dkarate.env=local
```

run this command for testing against locally running server with mocked downstream services

```
    mvn test -Pmock -Dkarate.env=mock
```

The `api-mock.feature` file is an example of how you can setup a mock api for any downstream dependencies. In addition to setting up mock endpoints and responses in the `api-mock.feature` file, you'll also have to set the url for that downstream dependency in the `karate-config.js` file. Instead of pointing to the actual url for the downstream dependency, it should point to your localhost and the karate mock port. By default, the karate mock port is randomized, but you can specify a port in the argline if you so choose. See below:

```
    mvn test -Pmock -Dkarate.env=mock -DargLine='-Dkarate.mock.port=9000'
``` 

To test with other environments, the name of the environment can be passed via `karate.env` variable. The configurations related to the selected environment needs to be updated in the `karate-config.js`


#### How to run gatling perf tests
Karate tests can be configured to run as perf tests. The perf tests can be run against any environment using the `-Dkarate.env=<env name>`

```
    mvn gatling:test -Pperf -Dkarate.env=<environment name>
``` 

#### How to use Swagger Documentation

From your web browser navigate [here](http://localhost:8080/swagger-ui.html)


#### Note about included certificate

The packaged certificate is OK for local development. However, we encourage you to use Intuit CA-signed or generate your own self-signed certificate for **TLS** termination

# testing-otro-nivel

Este proyecto emplea el testing para garantizar que la arquitecturas REST del proyecto se este cumpliendo.

## Pre requisitos

 - Manejo de jUnit (pruebas unitarias)
 - Conocimientos en Java (reflection / instrospeccion)
 - API REST

## 🕵 Proyecto y ejecucion

Este proyecto usa Quarkus, un micro Framework de Java. Si quiere saber mas dde quarkus visite esta pagina: https://quarkus.io/ .

Puede ejecutar esta aplicación en modo ´dev´, que permite la codificación en vivo ejecutar con terminal desde la raiz del proyecto:

``` mvn clean compile quarkus:dev```

> **_NOTA:_**  Quarkus cuenta con una interfaz de usuario para desarrolladores, que está disponible en modo de desarrollo solo en http://localhost:8080/q/dev/.

## 💪 Guías recomendadas con la charla

[Reflection (en java)](https://jarroba.com/reflection-en-java/)  
[RFC - 3986 (Hierarchical Identifiers)](https://www.rfc-editor.org/rfc/rfc3986#section-1.2.3)  
[REST](https://www.tecnologias-informacion.com/api-rest.html)  

## 🤔 Guías recomendadas con el framework

- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive)): A JAX-RS implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- Quarkus Extension for Spring Web API ([guide](https://quarkus.io/guides/spring-web)): Use Spring Web annotations to create your REST services
- Jacoco - Code Coverage ([guide](https://quarkus.io/guides/tests-with-coverage)): Jacoco test coverage support

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

### Spring Web

Spring, the Quarkus way! Start your RESTful Web Services with a Spring Controller.

[Related guide section...](https://quarkus.io/guides/spring-web#greetingcontroller)

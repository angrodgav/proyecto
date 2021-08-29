# Introducción

**WeatherForecast** es un ejercicio similar a la [Weather kata](https://github.com/CodiumTeam/weather-kata) de [Codium Team](https://www.codium.team).

Se trata de una clase con un método público que devuelve la previsión del tiempo de una ciudad en una fecha concreta.

Para ello, esta clase utiliza una API externa (requiere conexión a internet): [www.metaweather.com](https://www.metaweather.com) 

Ejemplo:

```java
WeatherForecast weatherForecast = new WeatherForecast();
weatherForecast.getCityWeather("Madrid", new Date());
```


# Ejercicio

El ejercicio consiste en **refactorizar** el código para hacerlo más **mantenible**, ya que el código existente, aunque **funciona**, es muy difícil de entender. 
  
Para ello se pueden realizar múltiples modificaciones siempre que se mantenga el API público. Ejemplos de modificaciones: incluir tests automáticos, extraer métodos, renombrar variables, modificar tipos de datos, crear nuevas clases, añadir librerías, etc. 


# Documentación

La solución debería contener un fichero README donde se respondan estas preguntas:
- ¿Qué has empezado implementando y por qué?
- ¿Qué problemas te has encontrado al implementar los tests y cómo los has solventado?
- ¿Qué componentes has creado y por qué?
- Si has utilizado dependencias externas, ¿por qué has escogido esas dependencias?
- ¿Has utilizado  streams, lambdas y optionals de Java 8? ¿Qué te parece la programación funcional?
- ¿Qué piensas del rendimiento de la aplicación? 
- ¿Qué harías para mejorar el rendimiento si esta aplicación fuera a recibir al menos 100 peticiones por segundo?
- ¿Cuánto tiempo has invertido para implementar la solución? 
- ¿Crees que en un escenario real valdría la pena dedicar tiempo a realizar esta refactorización?


# A tener en cuenta

- Se valorará positivamente el uso de TDD, se revisarán los commits para comprobar su uso.
- Se valorará positivamente la aplicación de los principios SOLID y "código limpio".
- La aplicación ya tiene un API público: un método público que devuelve el tiempo en un String. No es necesario crear nuevas interfaces: REST, HTML, GraphQL, ni nada por el estilo.


# Entrega

La solución se puede subir a un repositorio de código público como [github](https://github.com/). 

Otra opción sería comprimir la solución (incluyendo el directorio .git) en un fichero .zip/.tar.gz y enviarlo por email.

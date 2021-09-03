
He comenzado a implementar los test unitarios que se deben cumplir en el api "weatherForecast.getCityWeather".
La razón es que para seguir la metodología TDD necesito apoyarme principalmente en los Test, antes de realizar cualquier refactor. Estos test, al hacerse sobre una lógica ya existente, se adaptan a ella, es decir que si al hacer tal cosa se produce una excepción, esto se registrará en el test tal cual.
Observamos en este punto problemas con algunos casos en la entrada:
- Ciudad:
	- Busqueda del tiempo de una ciudad aleatoria en caso de pasar cadena coincidente con más de una ciudad
- Fecha:
	- El uso de Date para realizar calculos es complejo y desacertado. La Api externa devuelve 6 días incluyendo el dia actual, por ello en nuestra Api se descartan aquellas fechas de las que no tenemos predicción(mayor de 6 días del actual). Esto se realiza comparando fechas tipo "Date" pero para encontrar la fecha 6 días posterior al actual se recurre al constructor Date que recibe los milisegundos, a este se le suma lo que supuestamente son 6 días en milisegundos pero no es del todo cierto:

	1.- (este caso al principio lo tuve en cuenta, pero despues me di cuenta que no aplica) Particularmente en los casos de cambio horario tendria 1 hora mas o 1 hora menos este calculo para ser 6 dias exactos. En este caso tratandose de la clase java.util.Date si significa 6 días exactos, dado que no tiene en cuenta cambios horarios, la complejidad de este calculo puede dar lugar a este tipo de confusiones.
	
	2.- Suma y compara estrictamente la suma de 6 dias a la fecha actual con sus horas, la Api solo tiene en cuenta los dias. Si por ejemplo hacemos la peticion a las 3 de la mañana del pronóstico del tiempo de un lugar, dentro de 6 dias a las 2 de la mañana, esta validación dirá que fechaActual(3 de la mañana)+6 dias > fechaPeticion por lo que se saltará la validación y hará llamada a la Api externa aunque sepamos que no hay pronostico para esa fecha.

El principal problema que he tenido al implementar los test es con la inyeccion de dependencias. Para poder incluir para los test de integración del contexto de Spring he añadido la libreria de test de Spring, y en aquellos que los necesite la utilice. En cuando a la inyección de mocks no fue dificil.

Los componentes creados:(Primero los test que hay que aplicar en los casos de metodos públicos)
1.- Creación al principio de un metodo privado para tratar en el con LocalDate y no java.util.Date (esto se ha hecho dejando la api publica igual para los que la están consumiendo)
2.- Un servicio para pasar la lógica que habia en el controlador, 
3.- Una clase que llama a la api externa, como hay 2 llamadas, 2 metodos distintos,
4.- Una clase que hace calculos de LocalDate
5.- Creacion de los modelos que se obtienen en la Api Externa 

He añadido las siguientes dependencias:
1.- Mockito para hacer los dobles para las pruebas unitarias
2.- Spring-context para la inyeccion de dependencias	
3.- spring-web y jackson-databind para usar RestTemplate y su paso a los modelos que tenemos para el uso mas claro
4.- spring-test para poder hacer inyeccion de dependencias de spring en los test de integracion

He utilizado Streams 2 veces, y optional por el resultado de estos en los calculos de la busqueda del lugar y la de la busqueda del tiempo que hace en un lugar y un dia. Me parece que simplifica código y puede resultar mas legible.

El rendimiento de la aplicación es dependiente de la Api externa que estamos consumiendo, creo que es el principal cuello de botella. Si el rendimiento bajase por la alta concurrencia nos podriamos plantear:

1.- Hacer una potica de cacheo de peticiones, habria que averiguar cuanto tiempo se considera que la prediccion es estable para ajustar el cacheo a esto. De esta forma cuando se realicen las peticiones que esten cacheadas será mucho más óptimo.

2.- Otra opcion sería cambiar la api externa que se esta utilizando por una que tenga mejor rendimiento y de un resultado con la información lo más precisa posible que requerimos, esta Api externa tiene ya una url que nos proporciona el tiempo de un lugar en una fecha "/api/location/(woeid)/(date)/" (https://www.metaweather.com/api/)... Pero el resultado es aun más pesado, por lo que no nos sirve para optimizar. 

3.- Si baja el rendimiento por la saturación interna, en caso de ser microservicios, podriamos escalarlo creandose más instancias para distribuir la concurrencia.

Para implementar la refactorizacion he trabajado de 20 a 25 horas.

Creo que es buena práctica:
1.- hemos separado las dependencias, que si se quiere cambiar será mas fácil de implementar.
2.- hemos implementado test unitarios y de integración que hace que futuros cambios tengan alguna cobertura.
3.- Como es más facil de leer es más mantenible, por lo que si hay alguna incidencia será mas sencillo de corregir y se ahorrará tiempo... 
El único escenario en el que sería tiempo perdido es si nunca se tocase esa api, ni para mantenimiento, ni para evolutivo ni escalabilidad... El problema es que en términos generales no se va a saber a ciencia cierta que algún desarrollo no se tocará.


	
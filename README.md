Proyecto primer parcial backend individual.
=
El proyecto actual es una API REST que cumple con los requisitos pedidos por Magneto.
La API REST está construida de forma tal que se puedan agregar más servicios, repositorios y controladores, de ser 
necesario, ya que trabaja con herencia, interfaces y clases genéricas.

En el proyecto está formado por dos repositorios, siendo el más específico y utilizado: "DnaRepository"

También vamos a encontrar los métodos, de los cuales los fundamentales son: "DnaServiceImpl" y "StatsServiceImpl"

Así mismo, vamos a tener los controlares, donde encontramos los controladores bases (Genéricos) y los controladores
para los servicios fundamentales nombrados anteriormente.

En cada servicio se contemplan casos en los que el usuario ingrese datos erróneos o en los que se pueda crear una
indeterminación.

Tests
=
En el proyecto se realizaron test unitarios sobre los servicios utilizados para llevar a cabo la funcionalidad de la API, 
teniendo ambos test una cobertura mayor al 80%.

Host
=
La API esta siendo hosteada en render, la dirección es:
https://proyectomagnetoapi.onrender.com

Se puede verificar rápidamente el funcionamiento de la misma mediante un HTTP POST con el siguiente formato:
POST -> /mutant
{
"dna": [ "AAGAGA", "GATTTT", "GTATGG", "AGAGAG", "CCGGAA", "TCAATA" ]
}

(Caso mutante) -> Devuelve True

En caso de ser false, o sea, humano, el servicio lanzará una excepción.
{
"dna": [ "AAGAGA", "GATTTA", "GTATGG", "AGAGAG", "CCGGAA","TCAATA" ]
}

(Caso No mutante) -> Devuelve Error

También existe un servicio "/Stats" que devuelve un Json con las estadísticas de las verificaciones de ADN.

Ejecutar localmente
=
Para ejecutar la API localmente es necesario tener instalada y configurada la base de datos H2.
Si ya lo tenemos:
1. Verificar las propiedades de la base de datos en application.properties
2. Si su base de datos se llama de otra manera o el usuario y contraseña son otros, entonces debe cambiarlos en ese archivo
3. Ejecutar MagnetoApiApplication

Podemos acceder localmente a openApi con el siguiente comando:
#http://localhost:8080/swagger-ui/index.html 

Y también podemos acceder a la base de datos para corroborar el correcto funcionamiento con:
# http://localhost:8080/h2-console/

IMPORTANTE
=
Este proyecto está construido con la herramienta de springboot, es un proyecto Gradle y utiliza la version de java 17.
El proyecto responde a la arquitectura en capas de controladores, servicios y repositorios.
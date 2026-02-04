# 🧬 Magneto API - Mutant Detection System

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge&logo=spring-boot)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue?style=for-the-badge&logo=docker)
![Coverage](https://img.shields.io/badge/Coverage-80%25%2B-success?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)

## 📋 Sobre el Proyecto

**Magneto API** es un servicio REST de alto rendimiento diseñado para analizar secuencias genéticas y detectar patrones específicos (el "gen mutante").

Más allá de ser una prueba técnica, este proyecto simula un escenario de **procesamiento masivo de datos** donde la eficiencia algorítmica y la integridad de los datos son críticas. El sistema recibe una matriz de cadenas de ADN (NxN), aplica lógica de verificación horizontal, vertical y oblicua, y persiste los resultados para generar estadísticas en tiempo real.

### 🚀 Valor que aporta
*   **Eficiencia:** Implementación de algoritmos de búsqueda optimizados para detener el procesamiento tan pronto se confirma la condición de mutante.
*   **Escalabilidad:** Arquitectura Stateless lista para ser desplegada en contenedores (Docker).
*   **Calidad de Software:** Enfoque robusto en Testing Unitario (JUnit 5 + Mockito) y reporte de cobertura con Jacoco.

---

## 🏗️ Arquitectura y Diseño Técnico

El proyecto sigue una **Arquitectura en Capas (Layered Architecture)** robusta, utilizando **Spring Boot 3**. Se ha puesto especial énfasis en la **reusabilidad de código** mediante el uso de Genéricos y Patrones de Diseño.

### Stack Tecnológico y Decisiones de Diseño

| Tecnología | Rol | ¿Por qué se eligió? |
| :--- | :--- | :--- |
| **Java 17** | Lenguaje | Versión LTS que ofrece records, pattern matching y mejor rendimiento de memoria. |
| **Spring Boot 3** | Framework | Facilita la configuración y provee un ecosistema maduro (JPA, Web, Validation). |
| **H2 Database** | Persistencia | Base de datos en memoria para facilitar el despliegue y testing sin dependencias externas complejas. |
| **MapStruct** | Mapeo | Elegido sobre ModelMapper por su rendimiento, ya que genera el código de mapeo en tiempo de compilación (sin reflexión). |
| **Hibernate Envers** | Auditoría | Permite un seguimiento histórico de los datos analizados, crucial para trazabilidad. |
| **Lombok** | Boilerplate | Reduce el código repetitivo (Getters, Setters, Builders), manteniendo las clases limpias. |
| **Jacoco** | QA | Herramienta estándar para asegurar que la cobertura de tests se mantenga sobre el umbral del 80%. |

---

## ✨ Funcionalidades Clave (Key Features)

*   **Detección de Mutantes (`/mutant`):** Algoritmo capaz de detectar más de una secuencia de cuatro letras iguales oblicua, horizontal o verticalmente.
*   **Estadísticas en Tiempo Real (`/stats`):** Endpoint optimizado que devuelve el ratio de mutantes vs. humanos analizados.
*   **Arquitectura Genérica:** Implementación de Controladores, Servicios y Repositorios base genéricos para facilitar la extensión a nuevas entidades en el futuro.
*   **Documentación API:** Integración con **OpenAPI/Swagger** para exploración interactiva de los endpoints.
*   **Validación de Datos:** Manejo de excepciones y validaciones de entrada para asegurar que las matrices de ADN sean cuadradas y contengan caracteres válidos (A, T, C, G).

---

## 🧪 Testing

El proyecto cuenta con una suite de tests unitarios exhaustiva (>80% de cobertura) utilizando **JUnit 5** y **Mockito**. Se han contemplado los siguientes escenarios de prueba:

| Escenario | Resultado Esperado | Descripción |
| :--- | :--- | :--- |
| **Matriz Null** | `Excepción` | Se valida que la entrada no sea nula antes de procesar. |
| **Matriz Vacía** | `Excepción` | Se valida que la matriz contenga datos. |
| **Matriz No Cuadrada** | `Excepción` | Validaciones para asegurar que `NxN` se cumpla (ej: 4 filas, 5 caracteres). |
| **Caracteres Inválidos** | `Excepción` | Rechazo inmediato si contiene letras distintas a `[A, T, C, G]`. |
| **ADN Humano** | `HTTP 403` | No se detectan secuencias repetidas suficientes. |
| **ADN Mutante (Horizontal)** | `HTTP 200` | Detección de secuencias como `GGGGA`. |
| **ADN Mutante (Vertical)** | `HTTP 200` | Detección de patrones repetidos en la misma columna. |
| **ADN Mutante (Oblicuo)** | `HTTP 200` | Detección diagonal, el caso algorítmico más complejo. |

Se puede verificar rápidamente el funcionamiento con los siguientes posts:

{ "dna": [ "AAGAGA", "GATTTT", "GTATGG", "AGAGAG", "CCGGAA", "TCAATA" ] } [Caso True]

{ "dna": [ "AAGAGA", "GATTTA", "GTATGG", "AGAGAG", "CCGGAA","TCAATA" ] } [Caso False]


---

## 🛠️ Instalación y Uso Local

### Prerrequisitos
*   Java SDK 17
*   Docker (Opcional)

### Opción A: Ejecución con Gradle (Recomendado)

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/tu-usuario/magneto-api.git
    cd magneto-api
    ```

2.  **Ejecutar la aplicación:**
    ```bash
    ./gradlew bootRun
    ```
    *La aplicación iniciará en `http://localhost:8080`*

3.  **Verificar cobertura de tests (Jacoco):**
    ```bash
    ./gradlew test jacocoTestReport
    ```
    *El reporte se generará en `build/reports/jacoco/test/html/index.html`*

### Opción B: Ejecución con Docker

El proyecto incluye un `Dockerfile` optimizado (Multi-stage build).

1.  **Construir la imagen:**
    ```bash
    docker build -t magneto-api .
    ```

2.  **Correr el contenedor:**
    ```bash
    docker run -p 9000:9000 magneto-api
    ```
    *Nota: El Dockerfile expone el puerto 9000 internamente.*

### 🔗 Accesos Útiles
*   **Swagger UI (Docs):** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
*   **Consola H2 (DB):** [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)
    *   *JDBC URL:* `jdbc:h2:mem:testdb` (Verificar en logs al inicio)
    *   *User/Pass:* `sa` / `password` (o vacío, según `application.properties`)

---

## 🧠 Desafíos y Aprendizajes

Durante el desarrollo de este proyecto, me enfrenté a varios desafíos técnicos interesantes:

1.  **Complejidad Algorítmica:**
    El desafío principal fue optimizar la búsqueda en la matriz. En lugar de recorrer la matriz completa múltiples veces, implementé un algoritmo que busca patrones en diferentes direcciones. Una mejora futura sería implementar concurrencia para procesar segmentos grandes de la matriz en paralelo.

2.  **Diseño de Componentes Genéricos:**
    Para demostrar un diseño orientado a objetos avanzado, abstraje la lógica CRUD común en componentes genéricos (`GenericService`, `GenericRepository`). Esto requirió un uso profundo de **Genéricos de Java**, lo que permite agregar nuevas entidades al sistema con un esfuerzo mínimo de codificación.

3.  **Integración de Auditoría (Envers):**
    Implementar `Hibernate Envers` fue clave para entender cómo mantener un historial de cambios automático sin ensuciar la lógica de negocio principal con código de inserción en tablas de log.

---

### 📬 Contacto

Si tienes sugerencias o quieres hablar sobre arquitectura de software, ¡no dudes en contactarme!

Alvaro Tapia - https://www.linkedin.com/in/alvaro-tapia-123414340/ - tapia.alvaro.02@gmail.com
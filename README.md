# TROYA_STATISTICS_SUSTAINABILITY_BACKEND

Microservicio de Estadisticas y Sostenibilidad

## 👥 Integrantes del equipo
- Julian Camilo Lopez Barrero
- Julian David Castiblanco Real
- Valeria Bermudez Aguilar
- Sebastian Enrique Barros Barros
- Santiago Suarez Puchigay


--- 

## Descripción

Modulo del proyecto RIDECI que permite medir el impacto ambiental y social del uso de RidECI, generando indicadores sobre ahorro de CO₂, número de viajes compartidos y nivel de participación.

---

## Estrategia de versionamiento y ramas

- main: Versión estable para PREPROD
- develop: Rama principal de desarrollo
- bugfix/*: Manejo de errores
- release/*: Manejo de versiones.

**Template commits** : ``feature: Tarea - Acción Realizada``

--- 

## Tecnologías usadas

| Tipo                   | Tecnología                         | Versión | Descripción                                                            |
|------------------------|------------------------------------|---------|------------------------------------------------------------------------|
| Lenguaje               | **Java**                           | 17      | Lenguaje principal del proyecto, orientado a objetos y multiplataforma |
| Framework              | **Spring Boot**                    | 3.5.7   | Framework para desarrollo de microservicios y APIs REST                |
| Gestor de Dependencias | **Maven**                          | 3.9.x   | Herramienta de compilación y gestión de dependencias                   |
| Cobertura de Código    | **JaCoCo**                         | 0.8.11  | Generación de reportes de cobertura de pruebas unitarias               |
| Análisis de Calidad    | **SonarQube**                      | 10.x    | Análisis estático del código para asegurar calidad y mantenibilidad    |
| Documentación de API   | **Swagger UI (Springdoc OpenAPI)** | 2.6.0   | Interfaz interactiva para probar y documentar endpoints REST           |
| Contenedores           | **Docker**                         | 27.x    | Creación y ejecución de contenedores para despliegues portables        |
| CI/CD                  | **GitHub Actions**                 | -       | Automatización del ciclo de integración y despliegue continuo          |
| Pruebas de API         | **Postman**                        | 11.x    | Plataforma para diseño, ejecución y validación de pruebas de endpoints |
| Seguridad              | **JWT (Java JSON Web Tokens)**     | 0.11.5  | Mecanismo de autenticación y autorización basada en tokens             |

---

## Arquitectura y Funcionamiento

El microservicio está desarrollado bajo el patrón de **Arquitectura Hexagonal**,
cuyo objetivo principal es aislar la lógica de negocio del resto de los componentes del sistema.  
Esto permite que el núcleo de la aplicación sea completamente independiente de los detalles de infraestructura.


# Estructura

    src/
    ├── main/
    │   ├── java/edu/dosw/rideci/
    │   │   ├── domain/         
    │   │   ├── application/    
    │   │   └── infrastructure/ 
    │   └── resources/
    └── test/

<h3>Domain </h3>
Contiene las entidades y las reglas de negocio puras.




<h3>Application </h3>

- Casos de uso: Responsables de gestionar la lógica del dominio.
- DTos: Objetos de transferencia de datos usados entre las capas.
- Mappers: Convertidores entre entidades y DTOs.
- Exceptions: Excepciones personalizadas que representan errores del negocio o de la aplicación.


<h3>Infrastructure </h3>

- API / Controllers: Endpoints REST que reciben las solicitudes del usuario, llaman los casos de uso y devuelven las respuestas.

- Database: Configuración de la base de datos y sus modelos concretos.

- Config: Clases de configuración general del sistema.

---

## Interacción con otros módulos




---

## Despliegue CI/CD

- Pipeline de desarrollo;
- Pipeline de producción:
- Swagger Expuesto:

---

## Diagramas


---

## Funcionalidades


---
## Pruebas

**Evidencia de Pruebas**

**Cómo Ejecutar las Pruebas**

``mvn clean test``

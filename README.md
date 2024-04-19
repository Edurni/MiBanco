# Proyecto de Acceso a Datos: Simulador de Banco

Este proyecto es una aplicación de simulación bancaria que utiliza Hibernate como herramienta de acceso a datos para interactuar con una base de datos MySQL. Proporciona opciones básicas de operaciones bancarias, como creación de cuentas, depósitos, retiros y transferencias entre cuentas.

## Requisitos previos

Antes de utilizar esta aplicación, asegúrate de tener instalado lo siguiente:

- **Java Development Kit (JDK)**: Necesitarás tener instalado al menos la versión 8 de JDK.
- **MySQL**: Se requiere un servidor MySQL para alojar la base de datos. Se recomienda utilizar phpMyAdmin para una administración más sencilla.
- **Eclipse o IntelliJ IDEA**: Se recomienda utilizar uno de estos IDE para importar y ejecutar el proyecto.

## Configuración de la base de datos

1. Crea una base de datos en tu servidor MySQL llamada `ad_banco`.
2. Ejecuta el proyecto y se crearán datos de prueba en la base de datos para poder usar adecuandamente el programa.

## Ejecución del proyecto

1. Abre el proyecto en tu IDE (Eclipse o IntelliJ IDEA).
2. Configura las credenciales de tu base de datos en el archivo `hibernate.cfg.xml` ubicado en la carpeta `src/main/resources`.
3. Ejecuta la aplicación desde la clase principal `Main.java`.
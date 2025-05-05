# Local de Comida con Delivery - Mr. Cherry

## Descripción

Este es un sistema de gestión de pedidos en línea para un local de comida con opción a delivery. Los usuarios pueden realizar pedidos a través de la página web, elegir productos, pagar mediante Mercado Pago y gestionar su pedido.

## Tecnologías Utilizadas

- **Lenguaje**: ![Java](https://img.shields.io/badge/Java-21-orange) **Java 17**
- **Framework**: ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green) **Spring Boot**
- **Base de Datos**: ![MySQL](https://img.shields.io/badge/MySQL-blue?logo=mysql&logoColor=white) **MySQL**
- **ORM**: JPA, Hibernate
- **Mapeo de Objetos**: ![MapStruct](https://img.shields.io/badge/MapStruct-07B4B0?logo=mapstruct&logoColor=white) **MapStruct**
- **Pago**: ![MercadoPago](https://img.shields.io/badge/Mercado%20Pago-009cde?logo=mercadopago&logoColor=white) **Mercado Pago API**
- **Docker**: ![Docker](https://img.shields.io/badge/Docker-blue?logo=docker&logoColor=white) **Docker**

## Requisitos Previos

Asegúrate de tener las siguientes herramientas instaladas:

- **Java 21** o superior
- **Docker** para la gestión de contenedores
- **Maven** (solo si deseas ejecutar el proyecto desde la línea de comandos)




## Configuración del Proyecto

## 1. Clonar el Repositorio

```bash
https://github.com/cristiandz23/mr-cherry.git
```
## 2. Ejecutar el Proyecto
- **Recuerda que debes tener instalado y ejecutando docker para que la aplicacion ejecute el archivo docker-compose y levante los contenedores**
- **En este punto tienes dos formas de ejecutar el proyecto**
#### a. Si tienes maven instalado ubicate en el directorio rariz del proyecto y ejecuta
```bash
  ./mvnw spring-boot:run
```
#### b. Si quieres hacerlo desde un IDE (de esta forma no necesitar tener instalado maven). Solo ejecuta el metodo 
 `main`


### 🔧 Configuración del entorno

* El contenedor docker que inicia con la aplicacion ya tiene configurado los accesos y el puerto pero si no vas a usarlos recuerda quitar el archivo `docker-compose.yml` y modificar los accesos a base de datos en el `src/main/resources/application-dev.yml` 

```bash
  spring:
  datasource:
    url: jdbc:mysql://localhost:3306/tu_base_de_datos?ServerTimezone=UTC
    username: tu_username
    password: tu_password
```

+ ![MercadoPago](https://img.shields.io/badge/Mercado%20Pago-009cde?logo=mercadopago&logoColor=white)  Antes de ejecutar la aplicación, asegurate de configurar el access-token de mercado pago  para poder recibir pagos.

  1. Cambia el nombre del archivo  `.env.example` por `.env`
  2. Indica tu access token que te properciona mercado pago cuando creas una aplicacion.

```bash
  MP_TOKEN=access_token
```
* Cuando ejecute la aplicacion la misma va a leer el acces token desde el archivo `.env`


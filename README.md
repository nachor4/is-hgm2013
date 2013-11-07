Proyecto ING. Software 2013
===========================
## Reversi - Aplicacion web usando WebSockets

### Integrantes del proyecto
 - Gomez, Nicolás
 - Herrero, Ignacio
 - Morilla, Guillermo
 
## Repositorios:
 - https://github.com/nachor4/is-hgm2013
 - https://www.assembla.com/code/is-hgm2013/git/nodes
 
## Requisitos
- Maven
- Java EE7
- MySQL Server

El sistema y el websocket corren en GlassFish. No hay necesidad de tener el servidor
instalado pues se lo configuró con un plugin "embebido" que será descargado 
deployed por MVN


Configuración Inicial
=====================

Se debe generar la base de datos y el usario correspondiente.
El script para la base de datos se enceuntra en src/config

##Permisos en la base de datos:
- Usuario: reversi
- Password: 123
- Privilegios: SELECT, INSERT, UPDATE

Para más información sobre como crear un usuario, ver el archivo info 
de la carpeta src/config


Inciar el Server
================

- run.sh: Compila, Ejecuta test y e inicia el servidor
- ntrun.sh: Compila e inicia el servidor (sin tests)
- justrun.sh: Inciar el Servidor.


Acceso a la Aplicacion Web
==========================

El puerto del servidor es el 8080 
http://localhost:8080 | http://IP-DEL-SERVER:8080


Technology Colophon
===================
## Aplicacion Java | JSP
- JavaX (versión: 7-Beta83): WebSockets y otras librerías
- jUnit 4.1
- MySQL 5.1.17
- ActiveJDBC 1.4.8
- Gson 2.2.4 - Librería de google para manejar objetos JSON
- GlassFish 3.1.2.2
- Maven2

## Front End
- HTML + JS + CSS3
- Jquery

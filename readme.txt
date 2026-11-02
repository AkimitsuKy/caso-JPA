Laboratorio JPA - Caso de estudio Concursos
==========================================

Efecto de la anotacion @OneToMany(mappedBy) de Competitor sobre la tabla PRODUCTO
---------------------------------------------------------------------------------

La anotación @OneToMany(cascade=ALL, mappedBy="competitor") que se agregó en la
entidad Competitor no agrega ninguna columna a la tabla PRODUCTO ni crea una tabla
nueva. El atributo mappedBy indica que Competitor es el lado inverso (no dueño) de la
relación y que la relación ya está mapeada por el atributo "competitor" de Producto,
anotado con @ManyToOne. Por eso, el lado dueño (Producto) es el que define la
estructura física: en la tabla PRODUCTO se crea la columna COMPETITOR_ID con una llave
foránea (PRDUCTOCMPETITORID) que referencia a COMPETITOR.ID. Cada producto guarda el
id de su competidor. Si se hubiera usado @OneToMany sin mappedBy, JPA trataría la
relación como unidireccional e independiente y crearía una tabla intermedia adicional
(COMPETITOR_PRODUCTO) con las dos llaves. Con mappedBy se evita esa tabla y la
relación queda bidireccional en Java: desde Competitor se obtienen sus productos
(getProducts()) con un SELECT sobre PRODUCTO filtrado por COMPETITOR_ID. Además,
cascade=ALL hace que las operaciones de persistencia (persist, merge, remove, refresh)
sobre un competidor se propaguen a sus productos.

Actividad: contraseña y servicio de log-in
------------------------------------------

- Competitor y CompetitorDTO tienen ahora el atributo "password". La contraseña no se
  devuelve en las respuestas JSON (@JsonIgnore).
- Nuevo servicio POST /competitors/login, que recibe el correo (campo "address") y la
  contraseña en JSON:

    { "address": "laura.gomez@gmail.com", "password": "secreto123" }

  Si las credenciales son correctas responde 200 con el competidor. Si no, lanza
  javax.ws.rs.NotAuthorizedException (HTTP 401).

- Para usar NotAuthorizedException (que existe desde JAX-RS 2.0), el proyecto pasó de
  Jersey 1.8 (JAX-RS 1.1) a Jersey 2.25.1. Se actualizaron pom.xml y web.xml.

Servicios
---------

  GET  http://localhost:8080/competitors/get     -> lista de competidores
  POST http://localhost:8080/competitors/add     -> registra un competidor
  POST http://localhost:8080/competitors/login   -> log-in

Ejecución
---------

1. Iniciar Derby (NetBeans: Services > Databases > jdbc:derby://localhost:1527/sample,
   usuario app / contraseña app).
2. mvn clean package
3. java -cp "target/classes;target/dependency/*" com.example.Main   (Windows)
   java -cp "target/classes:target/dependency/*" com.example.Main   (Linux/Mac)
   o "Run File" sobre Main.java en NetBeans.

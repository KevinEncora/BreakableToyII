# BreakableToyII

### Overview

Este proyecto busca crear una **aplicación full-stack** que se conecte con la **Spotify API** para obtener y mostrar información sobre artistas, álbumes y canciones. Para lograrlo, usamos un **backend** hecho en **Java** con **Spring Boot** y Gradle, y un **frontend** con **React** y **TypeScript**. Además, utilizamos el flujo de **OAuth 2.0** de Spotify para autenticar usuarios y gestionar tokens de acceso y de refresh.

**¿Qué puede hacer esta app?**

- Permitir que los usuarios inicien sesión con su cuenta de Spotify (OAuth 2.0).
- Mostrar sus artistas, álbumes y canciones más destacados.
- Refrescar tokens automáticamente cuando expiren, sin que el usuario tenga que iniciar sesión otra vez.
- Reproducir vistas previas de 30 segundos de canciones (opcional).

### Contexto

Este proyecto nació con la idea de practicar la integración con APIs externas como la de Spotify, además de explorar cómo desarrollar aplicaciones full-stack con tecnologías como Spring Boot y React.

**Objetivos principales:**

- Aprender a integrar un backend robusto con un frontend amigable.
- Entender cómo manejar autenticaciones seguras con OAuth 2.0.
- Crear una experiencia atractiva para los usuarios que quieren explorar su música favorita.

En el pasado, muchas apps dependían únicamente del frontend para interactuar con Spotify, pero aquí combinamos un backend completo para manejar la lógica de autenticación con un frontend elegante y fácil de usar.

---

### Requirements

#### Autenticación con OAuth 2.0

- **Usuario**: Solo se almacenan el token de acceso y el de refresco.
- **Artista**: Nombre, popularidad, géneros e imágenes.
- **Álbum**: Portada, fecha de lanzamiento y lista de canciones.
- **Pista**: Título, duración y enlace para previsualizar.

#### Endpoints del Backend

1. **POST /auth/spotify**: Comienza la autenticación y guarda los tokens.
2. **GET /me/top/artists**: Devuelve los artistas principales del usuario.
3. **GET /artists/{id}**: Detalles de un artista.
4. **GET /albums/{id}**: Detalles de un álbum.
5. **GET /search**: Busca artistas, álbumes o pistas.

#### Manejo de Tokens

- Los tokens se deben de hacer refresh automáticamente al expirar.
- Se detectan errores (como 401) para actualizar los tokens antes de fallar.

#### Frontend (React + TypeScript)

- **Framework**: React con TypeScript.
- **Estado Global**: React Context o Redux.
- **Estilo UI**: Material UI o Ant Design.
- **Comandos principales**:
    - `npm install` para instalar dependencias.
    - `npm run start` para desarrollo.
    - `npm run build` para producción.

#### Backend (Spring Boot)

- **Framework**: Spring Boot.
- **Build Tool**: Gradle.
- **Comandos principales**:
    - `gradle bootRun` para correr el backend.
    - `gradle test` para pruebas unitarias.

---

### Solución (Detalles Técnicos)

El proyecto está dividido en dos carpetas:

- **/backend**: Todo lo relacionado al backend en Spring Boot.
- **/frontend**: Componentes y lógica del frontend en React.

**Backend:**

- Estructura basada en controladores, servicios y modelos.
- Librerías como Spring Security para manejar OAuth.

**Frontend:**

- Componentes organizados en carpetas (`src/components/`).
- Llamadas a la API con fetch o axios encapsuladas en un servicio.
- Rutas como `/login`, `/me/top/artists`, `/artist/:id` y `/album/:id`.

**Interfaz de Usuario (UI):**

- **Login**: Botón de inicio de sesión con Spotify.
- **Top Artists**: Tarjetas con información básica y accesos rápidos.
- **Detalles de Artistas/Álbumes**: Información más detallada con imágenes y listas.
- **Búsqueda**: Barra para explorar contenido musical en Spotify.

---

### Data Flow

1. **Autenticación**:
    - El usuario inicia sesión desde el frontend → el backend maneja los tokens.
2. **Obtención de datos**:
    - El frontend pide datos al backend → el backend consulta a Spotify.
3. **Renderizado**:
    - Los datos se procesan y se muestran en componentes de React.

---

### Consideraciones Extra

+ Mejora de algunos endpoints, para tener mayor eficiencia al hacer llamadas al back
- **Caching**: De toda la información principal para mejorar rendimiento y que sea fácilmente escalable.
- **Rate Limits**: Manejo adecuado de los límites de la Spotify API.
- **Seguridad**: Cifrrar tokens y limitar su exposición.
- **Testing**: Pruebas que validen integración y funcionalidad.

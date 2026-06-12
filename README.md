# 🐉 GrimoireLink

GrimoireLink es una **API REST para la gestión de campañas de Dungeons & Dragons 5e (SRD 2014)**. Permite a un grupo de juego administrar de forma centralizada sus campañas, personajes, hechizos, inventario, encuentros y monstruos, apoyándose en los datos oficiales del SRD a través de la D&D 5e API.

Desarrollada como **Trabajo Práctico Final de Programación III** — UTN Mar del Plata.

## 🏰 Descripción

Llevar el registro de una campaña de D&D de forma manual (fichas en papel, planillas sueltas) provoca desincronización entre el master y los jugadores, dificulta el seguimiento de la progresión de los personajes a lo largo de varias sesiones, y obliga a reescribir constantemente datos que ya existen en el SRD oficial.

GrimoireLink digitaliza ese flujo: centraliza el estado de la partida en una API consistente para todo el grupo, consume los datos oficiales (razas, clases, hechizos, objetos, monstruos) desde la **D&D 5e SRD API**, y completa con un *seeder* local aquellos datos que la API externa no cubre (trasfondos y sus habilidades).

Los usuarios tienen un **rol contextual según la campaña**:

- **DM (Dungeon Master):** crea la campaña y la administra. Gestiona encuentros y monstruos.
- **PLAYER (Jugador):** se une a una campaña con un código de invitación y administra su propio personaje.

El rol no se guarda en el JWT, sino en la tabla intermedia `UsersXCampaign`, y la autorización se resuelve en la capa de servicio. Un mismo usuario puede ser DM en una campaña y PLAYER en otra.

## 🕯️ Tecnologías utilizadas

- Java 21
- Spring Boot 4.0.6
- Spring Data JPA / Hibernate
- Spring Security (JWT, *stateless*)
- MySQL
- SpringDoc OpenAPI (Swagger UI)
- Lombok
- MapStruct
- RestClient (consumo de la D&D 5e SRD API)
- Maven

## 🧙 Instalación

1. Clonar el repositorio:

```bash
git clone <https://github.com/MacchiLisandro/grimoireLink.git>
cd GrimoireLink
```

2. Crear la base de datos en MySQL:

```sql
CREATE DATABASE grimoirelink;
```

3. Configurar las credenciales en `src/main/resources/application.yaml`:

```
spring:
  application:
    name: grimoireLink
  datasource:
    url: TU_URL
    username: TU_USUARIO
    password: TU_CONTRASEÑA
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        type:
          preferred_uuid_jdbc_type: VARCHAR
```

4. Ejecutar la aplicación:

```bash
./mvnw spring-boot:run
```

> Al iniciar por primera vez, el *seeder* carga automáticamente los trasfondos y sus habilidades asociadas. Es idempotente: no duplica datos en reinicios posteriores.

## 🗡️ Documentación de la API

El proyecto incluye **Swagger UI** para la documentación interactiva de todos los endpoints, sus parámetros, cuerpos de request y respuestas. Una vez levantada la aplicación:

- Swagger UI: `http://localhost:8080/swagger-ui.html`

En la UI, usá el botón **Authorize** 🔒 (arriba a la derecha) para pegar tu JWT y probar los endpoints protegidos directamente desde el navegador.

## 🛡️ Autenticación

El sistema utiliza **JWT** en modo *stateless*. El flujo es:

1. Registrarse (`POST /api/auth/register`).
2. Iniciar sesión (`POST /api/auth/login`) → devuelve un **access token** (JWT) y un **refresh token**.
3. Enviar el access token en cada request protegido:

```
Authorization: Bearer <access_token>
```

Cuando el access token expira, se renueva con `POST /api/auth/refresh` enviando el refresh token, sin volver a loguearse.

| Acceso | Detalle |
| --- | --- |
| Público | `register`, `login`, `refresh` y las rutas de Swagger |
| Requiere JWT | Todos los demás endpoints |
| DM / PLAYER | Roles **contextuales por campaña**, validados en la capa de servicio |

## 🎲 Funcionalidades principales

### Jugador (PLAYER)

- Unirse a una campaña mediante un **código de invitación numérico**.
- Crear y administrar su personaje (ABM completo) dentro de una campaña.
- Gestionar hechizos: agregar, quitar y marcar como *preparados*.
- Gestionar inventario: agregar, quitar y marcar objetos como *equipados*.
- Actualizar puntos de vida y oro del personaje.

### Dungeon Master (DM)

- Crear y administrar campañas (queda asignado automáticamente como DM).
- Ver los miembros de cada campaña.
- Crear y gestionar **encuentros** de combate.
- Agregar personajes y **monstruos** (traídos del SRD) a un encuentro.
- Llevar el seguimiento de HP de personajes y monstruos durante el combate.

## 📜 Reglas de negocio

- **Autenticación obligatoria:** todos los endpoints requieren JWT, salvo registro y login.
- **Creación de campaña:** quien la crea queda como **DM**.
- **Unión a campaña:** mediante `inviteCode` (código numérico), distinto del `publicId` (UUID).
- **Un personaje vivo por campaña:** cada usuario solo puede tener un personaje vivo en una misma campaña.
- **Soft delete:** "eliminar" un personaje lo marca como `DEAD`, preservando el historial.
- **HP y oro absolutos:** las actualizaciones usan el nuevo total, no incrementos.
- **Datos externos vs. locales:** razas, clases, hechizos, objetos y monstruos provienen de la [D&D 5e SRD API](https://www.dnd5eapi.co/api/2014/); los trasfondos se cargan localmente vía *seeder*.
- **Monstruos sin recurso propio:** un monstruo no se crea con un POST independiente. Se **materializa desde el SRD** al agregarlo a un encuentro, generando una instancia con su propio `publicId` y HP para el seguimiento del combate.
- **Denormalización controlada:** los nombres e índices de raza, clase y subclase se cachean localmente en el personaje al crearlo, evitando llamadas repetidas a la API externa.

## 🔮 Funcionalidades futuras

- **Mapa de batalla** (visualización de enfrentamientos en tablero).
- **Sistema de tirada de dados**
- **Sistema de combate con gestión de turnos e iniciativa** y registro de eventos (`Combat_Log`).
- **Notificaciones en tiempo real** (WebSockets / patrón Observer) para sincronizar el estado de la partida entre DM y jugadores.
- **Frontend en Angular** para consumir la API.
- Log de auditoría (AOP) sobre operaciones sensibles.

## 🧝 Autores

- Mateo Benegas
- Agostina Fenoy
- Lisandro Macchi

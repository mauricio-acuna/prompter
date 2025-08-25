# Documentación Odin Prompting

## 1. Descripción General y Arquitectura

Odin Prompting es una plataforma para la gestión y edición de prompts de IA, compuesta por un backend en Java (Gradle) y dos frontends en Angular. El sistema está preparado para despliegue en contenedores Docker, facilitando la escalabilidad y el mantenimiento.

### Arquitectura
- **Backend (Java, Gradle):** API RESTful, lógica de negocio, persistencia de datos.
- **Frontend (Angular):** Interfaz de usuario, gestión de sesiones, edición y visualización de prompts.
- **Docker:** Orquestación de servicios, despliegue sencillo.

## 2. Principales Módulos y Funciones

### Backend
- `src/main/java/com/odinprompting/`: Código fuente principal.
- `resources/application.properties`: Configuración de la aplicación.
- `db/init.sql`: Script de inicialización de base de datos.
- **Endpoints principales:** Gestión de prompts, usuarios, autenticación.

### Frontend
- `frontend/odin-prompting-frontend/` y `frontend-app/`: Aplicaciones Angular.
- `src/app/features/`: Módulos funcionales (auth, dashboard, prompt-editor).
- `src/app/core/`: Servicios y lógica compartida.
- `src/app/shared/`: Componentes reutilizables.

### Docker
- `Dockerfile` y `docker-compose.yml`: Configuración de contenedores y servicios.

## 3. Guía de Mantenimiento y Mejores Prácticas

- **Versionado:** Utiliza ramas para nuevas funcionalidades y hotfixes. Realiza pull requests y revisiones de código.
- **Pruebas:** Mantén y actualiza los tests unitarios y de integración.
- **Documentación:** Actualiza este archivo y el README con cada cambio relevante.
- **Seguridad:** Revisa dependencias y actualiza regularmente. Usa variables de entorno para credenciales.
- **Logs:** Configura logs claros y centralizados para backend y frontend.
- **Docker:** Mantén las imágenes ligeras y actualizadas. Usa `.dockerignore` para excluir archivos innecesarios.

## 4. Guía para Despliegue a Producción

1. **Preparar variables de entorno:** Configura credenciales y endpoints en archivos `.env` y `application.properties`.
2. **Construir imágenes Docker:**
   ```sh
   docker-compose build
   ```
3. **Levantar servicios:**
   ```sh
   docker-compose up -d
   ```
4. **Verificar logs y estado:**
   ```sh
   docker-compose logs
   docker ps
   ```
5. **Configurar dominios y certificados SSL:** Usa Nginx o Traefik como proxy reverso si es necesario.
6. **Backup y restauración:** Documenta y automatiza backups de la base de datos.

## 5. Guía Evolutiva para el Equipo

- **Onboarding:**
  - Lee el README y este documento.
  - Clona el repositorio y levanta el entorno local con Docker.
  - Revisa la arquitectura y los módulos principales.
- **Evolución:**
  - Prioriza la modularidad y la escalabilidad.
  - Documenta cada nueva funcionalidad.
  - Mantén la compatibilidad entre backend y frontend.
  - Realiza revisiones de código y pruebas automáticas.
- **Comunicación:**
  - Usa issues y pull requests para seguimiento de tareas.
  - Documenta decisiones técnicas y cambios relevantes.

## 6. Recomendaciones de Onboarding

- Familiarízate con la estructura del proyecto y los flujos principales.
- Levanta el entorno local y realiza pruebas de los endpoints y la interfaz.
- Consulta los scripts de inicialización y configuración.
- Participa en revisiones de código y sesiones de planificación.

---

> Odin Prompting: Documentación viva para un equipo ágil y preparado para la evolución.

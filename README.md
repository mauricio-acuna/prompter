# Odin Prompting

¡Bienvenido a Odin Prompting!

Odin Prompting es una plataforma integral para la gestión, edición y despliegue de prompts de IA, diseñada para equipos que buscan eficiencia, escalabilidad y una experiencia moderna tanto en backend como en frontend.

## Características principales

- **Backend robusto en Java (Gradle):**
  - API RESTful para la gestión de prompts y usuarios.
  - Seguridad y escalabilidad empresarial.
  - Integración con bases de datos y soporte para despliegue en Docker.

- **Frontend moderno en Angular:**
  - Dos aplicaciones Angular para diferentes experiencias de usuario.
  - Interfaz intuitiva, responsiva y personalizable.
  - Módulos para autenticación, dashboard, editor de prompts y más.

- **Contenedores Docker:**
  - Fácil despliegue y escalabilidad en cualquier entorno.
  - Archivos Dockerfile y docker-compose listos para producción y desarrollo.

- **Documentación y configuración:**
  - Archivos de configuración claros y bien estructurados.
  - Ejemplos de uso y scripts de inicialización de base de datos.

## Estructura del proyecto

```
OdinPrompting/
├── backend/                # Backend Java con Gradle
├── frontend/               # Frontend Angular principal
├── frontend-app/           # Frontend Angular alternativo
├── docker-compose.yml      # Orquestación de contenedores
├── PRD.md                  # Documento de requisitos
├── README_status.md        # Estado del proyecto
```

## ¿Cómo empezar?

1. **Clona el repositorio:**
   ```sh
   git clone https://github.com/mauricio-acuna/prompter.git
   ```
2. **Levanta los servicios con Docker:**
   ```sh
   docker-compose up --build
   ```
3. **Accede al frontend:**
   - Navega a `http://localhost:4200` o el puerto configurado.

## Contribuciones

¡Las contribuciones son bienvenidas! Por favor, revisa el archivo `PRD.md` para conocer los requisitos y lineamientos del proyecto.

## Licencia

Este proyecto está bajo la licencia MIT.

---

> Odin Prompting: potencia tus flujos de trabajo con IA, desde la edición hasta el despliegue, todo en un solo lugar.

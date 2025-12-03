# G11 - MVP CRUD con React

Este proyecto es una aplicación CRUD (Crear, Leer, Actualizar, Eliminar) básica construida con React.

## Cómo iniciar el proyecto

1.  **Crear el proyecto**
    ```bash
    npm create vite@latest g11-crud -- --template react
    cd g11-crud
    ```

2.  **Instalar dependencias**
    ```bash
    npm install
    ```

3.  **Configurar la API base (MockAPI)**
    Crea un archivo `.env.local` con el siguiente contenido:
    ```
    VITE_API_BASE_URL="https://<tu-subdominio>.mockapi.io/api/v1"
    ```

4.  **Iniciar en modo de desarrollo**
    ```bash
    npm run dev
    ```
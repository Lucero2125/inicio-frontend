# G11 - MVP CRUD con React

This project is a basic CRUD (Create, Read, Update, Delete) application built with React.

## How to start the project

1.  **Create the project**
    ```bash
    npm create vite@latest g11-crud -- --template react
    cd g11-crud
    ```

2.  **Install dependencies**
    ```bash
    npm install
    ```

3.  **Configure the base API (MockAPI)**
    Create a `.env.local` file with the following content:
    ```
    VITE_API_BASE_URL="https://<your-subdomain>.mockapi.io/api/v1"
    ```

4.  **Start in development**
    ```bash
    npm run dev
    ```
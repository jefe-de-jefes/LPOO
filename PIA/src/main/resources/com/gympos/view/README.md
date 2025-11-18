# Módulo View

## Propósito y Responsabilidad

El módulo **view** contiene las definiciones de interfaz de usuario mediante archivos FXML que describen la estructura visual y layout de cada pantalla del sistema. Estos archivos son interpretados por JavaFX para renderizar componentes gráficos y vincularlos con sus respectivos controladores.

## Archivos FXML

El módulo incluye las siguientes vistas principales:

- **login.fxml**: Pantalla de autenticación con campos de usuario, contraseña y botón de acceso.
- **dashboard.fxml**: Menú principal con botones de navegación a todos los módulos, reloj en tiempo real y opciones de respaldo/logout.
- **gestion_clientes.fxml**: Interfaz CRUD para administración de clientes con tabla, formularios y botones de acción.
- **sistema_membresias.fxml**: Gestión de membresías con selección de tipo, cálculo de precios y fechas de vigencia.
- **control_acceso.fxml**: Control de entradas/salidas al gimnasio y clases con validación de membresías.
- **procesador_pagos.fxml**: Ventana modal para procesamiento de pagos con método de pago y confirmación.
- **inventario.fxml**: Administración de productos con control de stock y ubicaciones.
- **schedules.fxml**: Calendario de clases grupales con validación de horarios y capacidad.
- **gestion_empleados.fxml**: CRUD de empleados con gestión de credenciales (solo admin).
- **generador_reportes.fxml**: Generación de reportes PDF con opciones de filtrado y exportación.

Cada vista está vinculada a su controlador correspondiente mediante el atributo `fx:controller` y utiliza `fx:id` para referenciar componentes desde código Java. Los estilos se aplican mediante el archivo CSS compartido en `resources/css/app.css`.

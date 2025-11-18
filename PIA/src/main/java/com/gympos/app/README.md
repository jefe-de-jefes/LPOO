# Módulo App

## Propósito y Responsabilidad

El módulo **app** es el punto de entrada de la aplicación GymPOS y se encarga de la inicialización del sistema, la gestión del ciclo de vida de la aplicación JavaFX y la coordinación de los servicios principales. Este módulo actúa como el orquestador central que conecta todos los componentes del sistema y proporciona utilidades para la navegación entre vistas.

## Componentes Clave

### `MainApp`
Clase principal de JavaFX que inicia la aplicación. Se encarga de:
- Cargar la ventana de login inicial
- Inicializar el contexto de la aplicación (`AppContext`)
- Poblar datos de prueba mediante `DataSeeder`
- Gestionar el cierre ordenado de recursos al finalizar

```java
public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AppContext.init();
        DataSeeder.seed();
        // Carga la vista de login
    }
}
```

### `AppContext`
Contenedor estático singleton que centraliza todos los servicios del sistema y mantiene el estado de sesión. Proporciona acceso global a:
- Usuario actualmente autenticado (`activeUser`)
- Instancias de todos los servicios (auth, clientes, membresías, pagos, etc.)
- Inicialización de hilos de ejecución y respaldos programados

### `Navigation`
Utilidad para cargar vistas FXML de manera segura y centralizada. Simplifica la navegación entre diferentes pantallas de la aplicación manejando excepciones de carga.

```java
Parent view = Navigation.load("/com/gympos/view/dashboard.fxml");
```

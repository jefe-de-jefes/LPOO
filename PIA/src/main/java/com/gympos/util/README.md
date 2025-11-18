# Módulo Util

## Propósito y Responsabilidad

El módulo **util** proporciona clases utilitarias y componentes de infraestructura que brindan funcionalidad transversal al sistema. Incluye mecanismos de persistencia, validación, formateo, manejo de excepciones, concurrencia, diálogos emergentes y población de datos de prueba.

## Componentes Clave

### `SerializationStore`
Sistema de persistencia mediante serialización Java que guarda objetos en archivos binarios.

**Responsabilidades:**
- Configuración de ruta base de almacenamiento
- Serialización de objetos a archivos .dat
- Deserialización de objetos desde archivos
- Manejo transparente de errores de I/O

**Métodos principales:**
```java
void initBasePaths(String path)     // Configura directorio base (./data)
void save(String filename, Object)  // Guarda objeto en archivo
T load(String filename)             // Carga objeto desde archivo
```

**Uso típico:**
```java
SerializationStore.initBasePaths("./data");
List<Client> clients = new ArrayList<>();
SerializationStore.save("clients.dat", clients);

// Más tarde...
List<Client> loaded = SerializationStore.load("clients.dat");
```

**Ventajas:**
- Persistencia simple sin base de datos
- Serialización automática de grafos de objetos
- Retorna `null` si el archivo no existe (primera ejecución)

### `Threading`
Administrador centralizado de hilos para tareas asíncronas y de fondo.

**Responsabilidades:**
- Inicialización de pool de hilos (10 threads)
- Ejecución de tareas en segundo plano
- Cierre ordenado de recursos al finalizar aplicación

**Métodos principales:**
```java
void init()                    // Crea ExecutorService con 10 threads
void shutdown()                // Cierra pool de hilos
void submitTask(Runnable task) // Ejecuta tarea en background
```

**Uso típico:**
```java
Threading.init();

// Tarea de larga duración sin bloquear UI
Threading.submitTask(() -> {
    while(true) {
        // Verificar vencimientos cada hora
        Thread.sleep(3600000);
        checkExpiredMemberships();
    }
});
```

### `Validation`
Utilidades de validación de datos de entrada.

**Responsabilidades:**
- Validación de campos nulos o vacíos
- Validación de formato de email con regex
- Validación de números positivos

**Métodos principales:**
```java
boolean isNullOrEmpty(String text)
boolean isValidEmail(String email)
boolean isPositive(double number)
```

**Patrones de validación:**
- Email: `^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$`

**Ejemplo:**
```java
if (Validation.isNullOrEmpty(nameField.getText())) {
    showError("El nombre es obligatorio");
    return;
}

if (!Validation.isValidEmail(emailField.getText())) {
    showError("Email inválido");
    return;
}
```

### `Formatters`
Formateadores de datos para presentación consistente en la UI.

**Responsabilidades:**
- Formateo de moneda en pesos mexicanos (MXN)
- Formateo de fechas en formato local (dd/MM/yyyy)

**Métodos principales:**
```java
String formatCurrency(double amount)    // $1,234.56 MXN
String formatDate(LocalDate date)       // 14/11/2025
```

**Configuración:**
- Locale: español de México (`es_MX`)
- Formato de fecha: día/mes/año

**Ejemplo:**
```java
String precio = Formatters.formatCurrency(6033.00);  // "$6,033.00"
String fecha = Formatters.formatDate(LocalDate.now()); // "14/11/2025"
```

### `Exceptions`
Jerarquía de excepciones personalizadas para reglas de negocio.

**Excepciones definidas:**

#### `AccessDeniedException`
Se lanza cuando se deniega acceso al gimnasio o clase por:
- Membresía vencida
- Fuera de horario permitido
- Cupo lleno
- Cliente ya registrado

#### `AuthenticationException`
Se lanza cuando fallan las credenciales de login:
- Usuario inexistente
- Contraseña incorrecta

#### `MembershipException`
Se lanza en operaciones inválidas de membresía:
- Intento de crear membresía duplicada
- Cliente sin membresía al intentar renovar

#### `PaymentException`
Se lanza en errores de procesamiento de pago:
- Monto inválido (cero o negativo)

#### `ValidationException`
Se lanza en validaciones generales:
- Datos obligatorios faltantes
- Formato de datos incorrecto
- Entidad no encontrada

**Todas heredan de `RuntimeException` (no checked exceptions)**

**Ejemplo de uso:**
```java
if (amount <= 0) {
    throw new PaymentException("El monto debe ser mayor a cero");
}

try {
    service.doSomething();
} catch (ValidationException e) {
    showAlert(e.getMessage());
}
```

### `PopupUtils`
Utilidad para mostrar ventanas modales reutilizables.

**Responsabilidades:**
- Creación de ventanas emergentes modales
- Carga de vistas FXML para popups
- Aplicación de estilos CSS
- Bloqueo de ventana principal hasta cerrar popup

**Método principal:**
```java
ProcesadorPagosController showPaymentPopup(
    String clientId, 
    Type type, 
    double basePrice, 
    double discount, 
    boolean isRenewal, 
    String currentEndDate
)
```

**Características:**
- Modal: bloquea interacción con ventana padre
- Sin decoración (sin barra de título estándar)
- Centrado en pantalla
- Estilos personalizados aplicados

**Ejemplo:**
```java
ProcesadorPagosController ctrl = PopupUtils.showPaymentPopup(
    "C-001", 
    Membership.Type.PREMIUM, 
    7528.00, 
    0.10, 
    false, 
    null
);

if (ctrl.isPaymentSuccessful()) {
    // Procesar resultado
}
```

### `DataSeeder`
Generador de datos de prueba para desarrollo y demostración.

**Responsabilidades:**
- Población inicial de 20 clientes de ejemplo
- Creación automática de membresías
- Generación de pagos de prueba
- Solo ejecuta si no hay datos existentes

**Lógica de generación:**
- 20 clientes con nombres predefinidos
- Emails generados: nombre.apellido@gym.com
- Teléfonos: 555-01XX
- 1 de cada 5 clientes inactivo (20%)
- Tipos de membresía aleatorios
- Método de pago alternado (CASH/CARD)

**Ejemplo de datos generados:**
```
C-001 | Juan Pérez     | juan.pérez@gym.com     | PREMIUM | ACTIVO
C-002 | María López    | maría.lópez@gym.com    | BASIC   | ACTIVO
C-003 | Carlos Ruiz    | carlos.ruiz@gym.com    | ANUAL   | ACTIVO
C-004 | Ana Torres     | ana.torres@gym.com     | STANDARD| ACTIVO
C-005 | Pedro Sánchez  | pedro.sánchez@gym.com  | BASIC   | INACTIVO
```

**Invocación:**
```java
public static void seed() {
    if (!AppContext.clientService.getAllClients().isEmpty()) {
        return; // Ya hay datos, no poblar
    }
    // Generar 20 clientes con membresías y pagos
}
```

## Principios de Diseño

1. **Reutilización**: Código común extraído a utilidades
2. **Separación de responsabilidades**: Cada clase tiene un propósito específico
3. **Facilidad de mantenimiento**: Cambios centralizados (ej: formato de fecha)
4. **Testabilidad**: Métodos estáticos facilitan pruebas unitarias
5. **Configurabilidad**: Paths y formatos configurables

## Flujo de Uso Típico

```
Aplicación inicia
    ↓
SerializationStore.initBasePaths("./data")
    ↓
Threading.init()
    ↓
DataSeeder.seed() // Si es primera vez
    ↓
Servicios usan SerializationStore para persistencia
    ↓
Controladores usan Validation/Formatters para UI
    ↓
Threading.submitTask() para tareas background
    ↓
Threading.shutdown() al cerrar aplicación
```

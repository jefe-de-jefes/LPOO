# Módulo Service

## Propósito y Responsabilidad

El módulo **service** implementa la capa de lógica de negocio del sistema, encapsulando operaciones complejas, reglas de negocio, validaciones y coordinación entre entidades. Cada servicio gestiona un aspecto específico del dominio y proporciona una API limpia para los controladores. Todos los servicios utilizan `SerializationStore` para persistencia de datos.

## Componentes Clave

### `AuthService`
Gestiona autenticación, autorización y administración de empleados.

**Responsabilidades:**
- CRUD completo de empleados
- Autenticación mediante usuario/contraseña
- Hashing de contraseñas con SHA-256
- Validación de credenciales únicas

**Métodos principales:**
```java
Employee login(String username, String password) // Autenticar usuario
void addEmployee(Employee employee)              // Registrar empleado
void updateEmployee(Employee updated)            // Actualizar datos
void removeEmployee(String employeeId)           // Eliminar empleado
List<Employee> getAllEmployees()                 // Listar todos
```

**Lógica de negocio:**
- Las contraseñas se hashean automáticamente al agregar empleados
- No se permiten nombres de usuario duplicados
- El hash SHA-256 es irreversible (seguridad)

**Ejemplo de uso:**
```java
Employee user = authService.login("admin", "123");
if (user != null) {
    // Login exitoso
}
```

### `ClientService`
Administra el ciclo de vida de clientes del gimnasio.

**Responsabilidades:**
- CRUD de clientes
- Generación automática de IDs secuenciales
- Filtrado de clientes activos
- Activación/desactivación de cuentas

**Métodos principales:**
```java
void addClient(Client client)
void updateClient(Client updated)
void removeClient(String clientId)
Client getClientById(String clientId)
List<Client> getAllClients()
List<Client> getActiveClients()
String generateNextId()  // Genera C-001, C-002, etc.
void deactivateClient(String clientId)
```

### `MembershipService`
Gestiona membresías con lógica compleja de precios, renovaciones y vencimientos.

**Responsabilidades:**
- Creación de membresías con cálculo de fechas de vigencia
- Renovación de membresías (extensión de fecha)
- Cancelación de membresías
- Detección de membresías próximas a vencer
- Notificaciones programadas de vencimiento
- Cálculo de precios base por tipo

**Precios configurados (personalizables por matrícula):**
```java
BASIC (DAVILA):    $4,122 MXN - 1 mes
STANDARD (FLORES): $6,033 MXN - 1 mes
PREMIUM (SEGOBIA): $7,528 MXN - 1 mes
ANUAL (ZACATENCO): $7,791 MXN - 12 meses
```

**Métodos principales:**
```java
Membership createMembership(String clientId, Type type, double discount)
void renewMembership(String clientId, Type type, double pricePaid)
void cancelMembership(String clientId)
boolean hasActiveMembership(String clientId)
Membership getCurrentMembership(String clientId)
List<Membership> getExpiringMemberships(int daysThreshold)
double getBasePrice(Type type)
LocalDate calculateEndDate(LocalDate start, Type type)
```

**Lógica de renovación:**
- Si existe membresía activa: extiende la fecha de vencimiento
- Si no existe o ya venció: crea nueva membresía desde hoy
- Previene duplicación de membresías activas

**Notificaciones automáticas:**
```java
scheduleExpiringNotifications() // Verifica cada hora si hay membresías próximas a vencer (7 días)
```

### `PaymentService`
Registra y valida transacciones de pago.

**Responsabilidades:**
- Procesamiento de pagos
- Validación de montos positivos
- Registro histórico de transacciones

**Métodos principales:**
```java
void processPayment(Payment payment)
List<Payment> getAllPayments()
```

**Validaciones:**
- El monto debe ser mayor a cero
- Se registra timestamp automáticamente al crear Payment

### `AccessService`
Controla el acceso físico al gimnasio y a clases grupales con reglas de negocio estrictas.

**Responsabilidades:**
- Registro de entradas/salidas generales
- Control de acceso a clases con validación de horario y cupo
- Verificación de membresía activa
- Prevención de accesos duplicados
- Historial de accesos por cliente

**Métodos principales:**
```java
void registerEntry(Client client)                              // Entrada general
void registerExit(Client client)                               // Salida general
void registerClassEntry(Client client, ClassSchedule schedule) // Entrada a clase
boolean isClientInside(String clientId)
List<AccessLog> getClientAccessHistory(String clientId)
```

**Reglas de negocio para clases:**
1. El cliente debe tener membresía activa
2. La clase debe estar programada para el día actual
3. Acceso permitido: 5 minutos antes hasta 15 minutos después del inicio
4. Verificación de cupo disponible (capacidad máxima)
5. No se permiten entradas duplicadas a la misma clase en el mismo día

**Ejemplo de validación:**
```java
// Clase a las 8:00 AM con capacidad de 20 personas
// Acceso permitido: 7:55 AM - 8:15 AM
// Si ya hay 20 registros de entrada: CUPO LLENO
```

### `InventoryService`
Gestiona inventario de productos e insumos del gimnasio.

**Responsabilidades:**
- CRUD de artículos de inventario
- Control de stock y ubicaciones
- Búsqueda de productos por ID

**Métodos principales:**
```java
void addItem(InventoryItem item)
void updateItem(InventoryItem updated)
void removeItem(String id)
InventoryItem getItemById(String id)
List<InventoryItem> getAllItems()
```

### `ScheduleService`
Administra calendario de clases con validación de traslapes de horarios.

**Responsabilidades:**
- CRUD de horarios de clases
- Detección de conflictos de horario
- Validación de disponibilidad al crear/editar clases

**Métodos principales:**
```java
void addClass(ClassSchedule classSchedule)
void updateClass(ClassSchedule updated)
void removeClass(String id)
List<ClassSchedule> getAllClasses()
```

**Lógica de traslape:**
```java
// Detecta si dos clases del mismo día se traslapan
// Clase A: 8:00 - 9:00
// Clase B: 8:30 - 9:30 → CONFLICTO
// Clase C: 9:00 - 10:00 → OK (no se traslapa)
```

### `BackupService`
Gestiona respaldos de datos del sistema.

**Responsabilidades:**
- Respaldos manuales bajo demanda
- Programación de respaldos nocturnos automáticos
- Copia de archivos .dat a carpeta timestamped

**Métodos principales:**
```java
void manualBackup()              // Crea backup inmediato
void scheduleNightlyBackup()     // Programa backup automático
```

**Estructura de respaldos:**
```
backups/
  backup_20251114_193045/
    clients.dat
    memberships.dat
    payments.dat
    ...
```

### `ReportService`
Genera reportes PDF profesionales con iText.

**Responsabilidades:**
- Generación de múltiples tipos de reportes
- Formato profesional con tablas y metadatos
- Almacenamiento en carpeta `reports/`

**Reportes disponibles:**
```java
void generatePaymentsReport(List<Payment> payments)
void generateClientsReport(List<Client> clients)
void generateMembershipsReport(List<Membership> memberships)
void generateAccessReport(List<AccessLog> logs)
void generateInventoryReport(List<InventoryItem> items)
void generateScheduleReport(List<ClassSchedule> classes)
void generateBackupsReport(List<String> backupNames)
```

**Formato de archivos:**
- Nombre: `TipoReporte_timestamp.pdf`
- Incluye fecha de generación
- Tablas con datos relevantes según tipo de reporte

### `RewardService`
Sistema de puntos de recompensa para clientes.

**Responsabilidades:**
- Acumulación de puntos por pagos
- Canje de puntos por beneficios
- Consulta de balance de puntos

**Métodos principales:**
```java
RewardPoint getPoints(String clientId)
void addPoints(String clientId, int points)
void redeemPoints(String clientId, int pointsToRedeem)
```

**Regla de negocio:**
- Los puntos canjeados no pueden resultar en balance negativo
- Típicamente: 10% del monto pagado = puntos otorgados

## Flujo de Datos Típico

```
Controller
    ↓
  Service (validación + lógica de negocio)
    ↓
  Model (entidad)
    ↓
  SerializationStore (persistencia)
```

## Principios de Diseño

1. **Single Responsibility**: Cada servicio gestiona un aspecto del dominio
2. **Encapsulación**: La lógica de negocio está oculta de los controladores
3. **Validación centralizada**: Los servicios validan antes de persistir
4. **Manejo de excepciones**: Lanzan excepciones personalizadas para errores de negocio
5. **Persistencia automática**: Todos los cambios se guardan inmediatamente

## Excepciones de Negocio

Los servicios lanzan excepciones específicas definidas en `Exceptions`:
- `AccessDeniedException`: Acceso denegado por reglas de negocio
- `AuthenticationException`: Credenciales inválidas
- `MembershipException`: Error en operación de membresía
- `PaymentException`: Error en procesamiento de pago
- `ValidationException`: Datos inválidos

# Módulo Model

## Propósito y Responsabilidad

El módulo **model** contiene las entidades de dominio que representan los conceptos fundamentales del negocio del gimnasio. Estas clases implementan `Serializable` para permitir la persistencia mediante serialización Java y encapsulan los datos y reglas básicas de validación de cada entidad.

## Componentes Clave

### `Client`
Representa a un cliente del gimnasio con sus datos personales y estado de actividad.

**Atributos:**
- `id`: Identificador único (formato: C-XXX)
- `fullName`: Nombre completo del cliente
- `email`: Correo electrónico
- `phone`: Número telefónico
- `active`: Estado de la cuenta (activo/inactivo)
- `createdAt`: Fecha de registro

**Métodos destacados:**
- `getStatusString()`: Retorna "Activo" o "Inactivo" según el estado

```java
Client client = new Client("C-001", "Juan Pérez", "juan@gym.com", "555-0100");
client.setActive(false); // Desactivar cuenta
```

### `Employee`
Representa un empleado del sistema con credenciales de acceso.

**Atributos:**
- `id`: Identificador único
- `name`: Nombre del empleado
- `username`: Usuario para login
- `passwordHash`: Contraseña hasheada con SHA-256

### `Membership`
Representa una membresía de cliente con vigencia y tipo de servicio.

**Tipos de membresía (Enum Type):**
- `BASIC`: Membresía básica mensual
- `STANDARD`: Membresía estándar mensual
- `PREMIUM`: Membresía premium mensual
- `ANUAL`: Membresía anual

**Atributos:**
- `clientId`: ID del cliente propietario
- `type`: Tipo de membresía
- `start`: Fecha de inicio de vigencia
- `end`: Fecha de vencimiento
- `pricePaid`: Monto pagado por la membresía
- `discountApplied`: Descuento aplicado (decimal, ej: 0.15 = 15%)

### `Payment`
Registra transacciones de pago realizadas por clientes.

**Atributos:**
- `id`: Identificador único del pago
- `clientId`: Cliente que realiza el pago
- `amount`: Monto pagado
- `timestamp`: Fecha y hora del pago (automática)
- `method`: Método de pago ("CASH" o "CARD")

### `AccessLog`
Registra entradas y salidas de clientes al gimnasio o clases.

**Atributos:**
- `clientId`: Cliente que accede
- `classScheduleId`: ID de clase (null para acceso general)
- `timestamp`: Fecha y hora del acceso
- `action`: Tipo de acción ("ENTRADA" o "SALIDA")

**Constructores:**
```java
// Acceso general al gimnasio
AccessLog log1 = new AccessLog("C-001", "ENTRADA");

// Acceso a clase específica
AccessLog log2 = new AccessLog("C-001", "ENTRADA", "CLS-001");
```

### `ClassSchedule`
Define horarios de clases grupales programadas.

**Atributos:**
- `id`: Identificador único de la clase
- `className`: Nombre de la clase (ej: "Yoga", "Spinning")
- `day`: Día de la semana (`DayOfWeek`)
- `time`: Hora de inicio (`LocalTime`)
- `duration`: Duración en minutos
- `capacity`: Cupo máximo de participantes

**Métodos de utilidad:**
- `getEndTime()`: Calcula hora de finalización
- `getTimeRange()`: Retorna rango "HH:MM - HH:MM"

```java
ClassSchedule yoga = new ClassSchedule(
    "CLS-001", 
    "Yoga Matutino", 
    DayOfWeek.MONDAY, 
    LocalTime.of(8, 0), 
    60, 
    20
);
```

### `InventoryItem`
Representa un artículo o producto en el inventario del gimnasio.

**Atributos:**
- `id`: Identificador único
- `name`: Nombre del producto
- `quantity`: Cantidad en stock
- `location`: Ubicación física en el gimnasio

### `RewardPoint`
Gestiona puntos de recompensa acumulados por clientes.

**Atributos:**
- `clientId`: Cliente propietario de los puntos
- `points`: Cantidad de puntos acumulados

**Métodos:**
- `add(int p)`: Suma puntos
- `redeem(int p)`: Canjea puntos (no puede ser negativo)

## Relaciones Entre Entidades

```
Client (1) ----< (0..n) Membership
Client (1) ----< (0..n) Payment
Client (1) ----< (0..n) AccessLog
Client (1) ----< (0..1) RewardPoint
ClassSchedule (1) ----< (0..n) AccessLog
```

## Características Comunes

Todas las entidades:
- Implementan `Serializable` para persistencia
- Usan tipos inmutables de Java 8+ (LocalDate, LocalDateTime, DayOfWeek)
- Sobrescriben `equals()` y `hashCode()` basándose en el ID
- Siguen convenciones JavaBean con getters/setters
- Incluyen constructores sin argumentos para compatibilidad con frameworks

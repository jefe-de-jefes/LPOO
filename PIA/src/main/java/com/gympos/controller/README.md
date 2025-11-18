# Módulo Controller

## Propósito y Responsabilidad

El módulo **controller** implementa el patrón MVC (Model-View-Controller) actuando como intermediario entre las vistas JavaFX (archivos FXML) y la lógica de negocio encapsulada en los servicios. Cada controlador maneja las interacciones del usuario, valida entradas, invoca servicios correspondientes y actualiza la interfaz gráfica según los resultados.

## Componentes Clave

### `LoginController`
Gestiona la autenticación de usuarios:
- Captura credenciales (usuario y contraseña)
- Valida mediante `AuthService`
- Redirige al Dashboard tras login exitoso
- Muestra mensajes de error en caso de credenciales inválidas

```java
@FXML
public void onLogin(ActionEvent e) {
    AppContext.activeUser = AppContext.authService.login(username, password);
    // Navegar al Dashboard
}
```

### `DashboardController`
Pantalla principal del sistema que proporciona:
- Navegación a todos los módulos funcionales
- Reloj en tiempo real actualizado cada segundo
- Gestión de respaldos manuales
- Control de acceso a funciones administrativas (gestión de empleados)
- Cierre de sesión con confirmación

**Flujo de seguridad para funciones administrativas:**
1. Verifica que el usuario sea "admin"
2. Solicita reconfirmación de contraseña mediante diálogo modal
3. Permite acceso solo si la validación es exitosa

### `GestionClientesController`
CRUD completo de clientes:
- Registro de nuevos clientes con validación de campos
- Edición de información de clientes existentes
- Activación/desactivación de cuentas
- Búsqueda y filtrado de clientes
- Visualización en tabla con datos: ID, nombre, email, teléfono, estado

### `SistemaMembresiaController`
Administración del ciclo de vida de membresías:
- Creación de nuevas membresías con cálculo automático de fechas
- Renovación de membresías existentes (extiende fecha de vencimiento)
- Cancelación de membresías
- Aplicación de descuentos (porcentaje personalizable)
- Tipos de membresía: BASIC, STANDARD, PREMIUM, ANUAL
- Integración con `PaymentService` para registro de pagos

**Lógica de precios:**
- BASIC: $4,122 (1 mes)
- STANDARD: $6,033 (1 mes)
- PREMIUM: $7,528 (1 mes)
- ANUAL: $7,791 (12 meses)

### `ControlAccesoController`
Control de entrada y salida del gimnasio:
- Registro de entradas generales (gimnasio)
- Registro de entradas a clases específicas con validación de horario
- Validación de membresía activa antes de permitir acceso
- Verificación de cupo disponible en clases
- Prevención de registros duplicados
- Visualización de historial de accesos por cliente

**Reglas de negocio:**
- Solo clientes con membresía activa pueden ingresar
- Entrada a clases: 5 minutos antes hasta 15 minutos después del inicio
- Control de capacidad máxima por clase
- No se permiten entradas duplicadas a la misma clase

### `ProcesadorPagosController`
Ventana modal para procesar pagos:
- Muestra información de la membresía a pagar
- Calcula monto final con descuentos aplicados
- Registra método de pago (CASH/CARD)
- Integra con `MembershipService` y `PaymentService`
- Otorga puntos de recompensa (10% del monto pagado)
- Genera recibo en PDF mediante `ReportService`

### `InventarioController`
Gestión de artículos e insumos:
- Alta, baja y modificación de productos
- Control de cantidades y ubicaciones
- Búsqueda y filtrado de inventario
- Actualización en tiempo real de stock

### `SchedulesController`
Calendario de clases grupales:
- Creación de horarios de clases
- Edición de clases existentes
- Eliminación de clases
- Validación de traslapes de horarios
- Configuración de capacidad, duración y día de la semana

### `GestionEmpleadosController`
Administración de personal (solo para admin):
- Registro de nuevos empleados
- Asignación de credenciales de acceso
- Actualización de contraseña con doble confirmación
- Hash SHA-256 de contraseñas para seguridad
- Eliminación de empleados

### `GeneradorReportesController`
Generación de reportes PDF:
- Reportes de pagos
- Reportes de clientes
- Reportes de membresías
- Reportes de accesos
- Reportes de inventario
- Reportes de calendario de clases
- Reportes de respaldos

## Flujo de Datos Típico

1. **Usuario interactúa con la vista** (botones, campos de texto)
2. **Controlador captura el evento** mediante anotaciones `@FXML`
3. **Validación de entrada** usando `Validation` utilities
4. **Invocación de servicio** correspondiente a través de `AppContext`
5. **Actualización de la interfaz** con los resultados (tablas, alertas, navegación)
6. **Manejo de excepciones** con diálogos informativos al usuario

```java
@FXML
private void onSave() {
    try {
        // Validar datos
        if (Validation.isNullOrEmpty(nameField.getText())) {
            showError("El nombre es obligatorio");
            return;
        }
        
        // Invocar servicio
        Client client = new Client(...);
        AppContext.clientService.addClient(client);
        
        // Actualizar vista
        refreshTable();
        showSuccess("Cliente guardado exitosamente");
    } catch (Exception e) {
        showError(e.getMessage());
    }
}
```

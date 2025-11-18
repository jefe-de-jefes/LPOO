# Manual de Usuario - GymPOS

## Introducción
GymPOS es un sistema integral para la gestión de gimnasios que permite administrar clientes, membresías, pagos, accesos, inventario y generar reportes. Este manual describe cómo utilizar cada pantalla del sistema y solucionar problemas comunes.

---

## 1. Pantalla de Login (Inicio de Sesión)

### ¿Qué hace esta pantalla?
Es la puerta de entrada al sistema donde los empleados inician sesión con sus credenciales para acceder a todas las funcionalidades del gimnasio.

### ¿Cómo usarla?
1. Ingrese su nombre de usuario en el campo "Usuario"
2. Ingrese su contraseña en el campo "Contraseña"
3. Presione el botón "Ingresar"
4. Si los datos son correctos, será redirigido al Dashboard principal

### Errores comunes y soluciones
- **"Usuario o contraseña incorrectos"**: Verifique que está escribiendo correctamente sus credenciales. Las contraseñas distinguen entre mayúsculas y minúsculas.
- **Usuario por defecto**: Si es la primera vez usando el sistema, el usuario administrador es `admin` con contraseña `123`.

---

## 2. Dashboard (Panel Principal)

### ¿Qué hace esta pantalla?
Es el centro de control del sistema desde donde puede navegar a todas las funciones principales. Muestra la hora y fecha actual en tiempo real.

### ¿Cómo usarla?
1. **Gestionar Clientes**: Click para administrar la base de datos de clientes
2. **Gestionar Empleados**: Solo para administradores. Requiere confirmación de contraseña
3. **Gestionar Membresías**: Para crear, renovar o cancelar membresías
4. **Control de Acceso**: Para registrar entradas y salidas del gimnasio
5. **Generar Reportes**: Para crear reportes en PDF
6. **Inventario**: Para administrar equipos e insumos
7. **Calendario Clases**: Para programar clases grupales
8. **Respaldo Manual**: Para crear una copia de seguridad de los datos
9. **Cerrar Sesión**: Para salir del sistema de forma segura

### Errores comunes y soluciones
- **"Permisos Insuficientes" al acceder a Gestión de Empleados**: Esta función está restringida al usuario administrador. Solo el usuario `admin` puede acceder.
- **"Contraseña incorrecta" al confirmar acceso a Empleados**: Debe ingresar nuevamente su contraseña de administrador para confirmar su identidad antes de acceder a esta sección sensible.

---

## 3. Gestión de Clientes

### ¿Qué hace esta pantalla?
Permite administrar toda la información de los clientes del gimnasio: registrar nuevos clientes, editar información existente, buscar clientes y ver el estado de sus membresías.

### ¿Cómo usarla?
1. **Buscar un cliente**: Use el campo de búsqueda en la parte superior para filtrar por ID o nombre
2. **Agregar nuevo cliente**:
   - Complete los campos: Nombre completo, Email, Teléfono
   - Presione "Agregar Cliente"
   - El sistema generará automáticamente un ID único (ej. C-001, C-002)
3. **Editar cliente existente**:
   - Seleccione un cliente de la tabla haciendo click en la fila
   - Los datos aparecerán en el formulario de la derecha
   - Modifique los campos necesarios
   - Presione "Actualizar Cliente"
4. **Desactivar cliente**: Seleccione el cliente y presione "Desactivar Cliente"
5. **Limpiar formulario**: Presione "Limpiar Campos" para vaciar el formulario

### Errores comunes y soluciones
- **"El email no es válido"**: Asegúrese de escribir un email con formato correcto (ejemplo@dominio.com)
- **"El nombre es obligatorio"**: Todos los campos deben estar completos antes de guardar
- **"Cliente no encontrado"**: Verifique que el ID del cliente existe en el sistema
- **No aparecen clientes en la tabla**: Verifique que hay clientes registrados. Si es la primera vez, el sistema debe haber cargado 20 clientes de ejemplo automáticamente.

---

## 4. Gestión de Empleados

### ¿Qué hace esta pantalla?
Permite al administrador gestionar las cuentas de los empleados que tendrán acceso al sistema, incluyendo la creación de usuarios y contraseñas.

### ¿Cómo usarla?
1. **Agregar nuevo empleado**:
   - Ingrese el nombre completo del empleado
   - Ingrese un nombre de usuario único (será usado para iniciar sesión)
   - Ingrese una contraseña
   - Confirme la contraseña en el segundo campo
   - Presione "Agregar Empleado"
2. **Actualizar contraseña**:
   - Seleccione un empleado de la tabla
   - Ingrese la nueva contraseña dos veces
   - Presione "Actualizar Contraseña"
3. **Eliminar empleado**: Seleccione el empleado y presione "Eliminar"
4. **Limpiar formulario**: Presione "Limpiar Campos"

### Errores comunes y soluciones
- **"El nombre de usuario ya existe"**: Debe elegir un nombre de usuario diferente que no esté en uso
- **"Las contraseñas no coinciden"**: Verifique que escribió exactamente la misma contraseña en ambos campos
- **"Empleado no encontrado"**: Asegúrese de seleccionar un empleado de la tabla antes de intentar actualizarlo o eliminarlo
- **No puede acceder a esta pantalla**: Solo el usuario `admin` puede gestionar empleados. Si intenta acceder con otro usuario, recibirá un mensaje de acceso restringido.

---

## 5. Gestión de Membresías

### ¿Qué hace esta pantalla?
Administra el ciclo completo de las membresías de los clientes: creación, renovación y cancelación. Calcula automáticamente precios, descuentos y fechas de vigencia.

### ¿Cómo usarla?
1. **Crear nueva membresía**:
   - Ingrese el ID del cliente (ej. C-001)
   - Seleccione el tipo de membresía en el menú desplegable
   - El sistema mostrará automáticamente el precio
   - Opcionalmente, marque "Usar Puntos de Recompensa" si el cliente tiene puntos acumulados
   - Presione "💳 Proceder al Pago"
   - Se abrirá la ventana de procesador de pagos
2. **Tipos de membresía disponibles**:
   - **BASIC**: $4,122 (1 mes)
   - **STANDARD**: $6,033 (1 mes)
   - **PREMIUM**: $7,528 (1 mes)
   - **ANUAL**: $7,791 (12 meses)

### Errores comunes y soluciones
- **"El cliente ya tiene una membresía activa"**: No puede crear una membresía nueva si el cliente ya tiene una vigente. Use la opción de renovación en su lugar
- **"Cliente no encontrado"**: Verifique que el ID del cliente existe en el sistema de gestión de clientes
- **"Debe seleccionar un tipo de membresía"**: Debe elegir una de las opciones del menú desplegable antes de proceder
- **El botón "Proceder al Pago" está deshabilitado**: Asegúrese de ingresar un ID de cliente válido y seleccionar un tipo de membresía

---

## 6. Procesador de Pagos (Ventana Modal)

### ¿Qué hace esta pantalla?
Esta ventana emergente aparece cuando va a procesar un pago de membresía. Muestra el resumen del pago y permite confirmar la transacción.

### ¿Cómo usarla?
1. Revise la información mostrada:
   - Datos del cliente
   - Tipo de plan seleccionado
   - Precio base
   - Descuento aplicado (si hay)
   - Total a pagar
   - Vigencia de la membresía
2. Seleccione el método de pago (Efectivo o Tarjeta)
3. Presione "Confirmar Pago" para procesar
4. El sistema generará automáticamente:
   - Registro de la membresía
   - Registro del pago
   - Puntos de recompensa (10% del monto pagado)
   - Recibo en PDF guardado en la carpeta `reports/`

### Errores comunes y soluciones
- **"Debe seleccionar un método de pago"**: Debe elegir entre Efectivo o Tarjeta antes de confirmar
- **"El monto debe ser mayor a cero"**: Esto indica un error en el cálculo. Cierre la ventana y vuelva a intentar
- **No se puede cerrar la ventana**: Use el botón "✕" en la esquina superior derecha o presione "Cancelar"

---

## 7. Control de Acceso

### ¿Qué hace esta pantalla?
Controla y registra las entradas y salidas de clientes al gimnasio. Valida que los clientes tengan membresía activa y controla el acceso a clases específicas verificando horarios y cupos.

### ¿Cómo usarla?
1. **Registrar entrada general al gimnasio**:
   - Ingrese el ID del cliente
   - Presione "Registrar ENTRADA"
2. **Registrar entrada a una clase específica**:
   - Ingrese el ID del cliente
   - Seleccione la clase del menú desplegable
   - Presione "Registrar ENTRADA"
3. **Registrar salida**:
   - Ingrese el ID del cliente
   - Presione "Registrar SALIDA"
4. **Ver historial**: La parte inferior muestra el historial de accesos recientes

### Errores comunes y soluciones
- **"Membresía vencida o inexistente"**: El cliente no tiene una membresía activa. Dirija al cliente a renovar su membresía primero
- **"El cliente ya está dentro"**: Ya existe un registro de entrada sin salida. Si es un error, registre la salida primero
- **"El cliente NO ha registrado entrada"**: No puede registrar una salida si no hay entrada previa
- **"Esta clase no está programada para hoy"**: La clase seleccionada es de otro día de la semana
- **"Demasiado temprano. Por favor espere X minutos"**: Las clases permiten entrada desde 5 minutos antes de la hora programada
- **"La clase ya comenzó hace mucho. Acceso cerrado"**: Solo se permite entrada hasta 15 minutos después del inicio de la clase
- **"CUPO LLENO (20/20). No puede ingresar"**: La clase alcanzó su capacidad máxima de participantes
- **"El cliente ya registró su entrada a esta clase"**: No se permiten registros duplicados en la misma clase del mismo día

---

## 8. Inventario de Equipos

### ¿Qué hace esta pantalla?
Administra el inventario de equipos, máquinas y suministros del gimnasio, controlando cantidades y ubicaciones.

### ¿Cómo usarla?
1. **Agregar nuevo equipo**:
   - Ingrese el nombre (ej. "Pesas", "Caminadora", "Colchonetas")
   - Ingrese la cantidad
   - Ingrese la ubicación (ej. "Área Cardio", "Bodega")
   - Presione "Agregar / Sumar"
2. **Actualizar equipo existente**:
   - Seleccione el artículo de la tabla
   - Los datos aparecerán en el formulario
   - Modifique los campos necesarios
   - Presione "Actualizar Selección"
3. **Eliminar equipo**: Seleccione y presione "Eliminar"
4. **Limpiar formulario**: Presione "Limpiar Campos"

### Errores comunes y soluciones
- **"La cantidad debe ser un número"**: Ingrese solo dígitos en el campo de cantidad
- **"Todos los campos son obligatorios"**: Complete nombre, cantidad y ubicación antes de guardar
- **"Debe seleccionar un artículo"**: Haga click en una fila de la tabla antes de intentar actualizar o eliminar
- **La tabla está vacía**: Si es la primera vez, agregue artículos manualmente. El sistema no incluye datos de ejemplo para inventario.

---

## 9. Calendario de Clases

### ¿Qué hace esta pantalla?
Permite programar y administrar el horario de clases grupales del gimnasio (yoga, spinning, zumba, etc.), controlando horarios, días, duración y capacidad máxima.

### ¿Cómo usarla?
1. **Agendar nueva clase**:
   - Ingrese el nombre de la clase (ej. "Yoga Matutino", "Spinning")
   - Seleccione el día de la semana
   - Ingrese la hora de inicio en formato HH:mm (ej. 08:00, 18:30)
   - Ingrese la duración en minutos (ej. 60)
   - Ingrese el cupo máximo de personas
   - Presione "Agendar Clase"
2. **Actualizar clase existente**:
   - Seleccione la clase de la tabla
   - Modifique los campos necesarios
   - Presione "Actualizar"
3. **Eliminar clase**: Seleccione y presione "Eliminar"
4. **Limpiar formulario**: Presione "Limpiar"

### Errores comunes y soluciones
- **"¡Conflicto! Ya existe una clase en ese horario"**: No puede programar dos clases que se traslapen en el mismo día. Cambie el horario o la duración
- **"Formato de hora inválido"**: Use el formato de 24 horas con dos dígitos: 08:00, 14:30, 19:00
- **"La duración debe ser un número"**: Ingrese solo dígitos en el campo de duración (minutos)
- **"El cupo debe ser mayor a cero"**: Ingrese un número positivo de participantes permitidos
- **Ejemplo de traslape**: Si hay una clase de 08:00 a 09:00 (60 min), no puede crear otra de 08:30 a 09:30 el mismo día

---

## 10. Generador de Reportes

### ¿Qué hace esta pantalla?
Genera reportes en formato PDF con la información del sistema para análisis, auditorías o presentaciones.

### ¿Cómo usarla?
1. Seleccione el tipo de reporte del menú desplegable:
   - **Reporte de Pagos**: Lista de todas las transacciones
   - **Reporte de Clientes**: Base de datos completa de clientes
   - **Reporte de Membresías**: Estado de todas las membresías
   - **Reporte de Accesos**: Historial de entradas y salidas
   - **Reporte de Inventario**: Stock actual de equipos
   - **Reporte de Calendario**: Horario de clases programadas
   - **Reporte de Respaldos**: Historial de copias de seguridad
2. Presione "Generar Reporte"
3. El sistema creará un archivo PDF en la carpeta `reports/`
4. Aparecerá un mensaje confirmando la ubicación del archivo

### Errores comunes y soluciones
- **"Debe seleccionar un tipo de reporte"**: Elija una opción del menú desplegable antes de generar
- **"No hay datos para generar el reporte"**: El reporte seleccionado no tiene información. Por ejemplo, si no hay pagos registrados, el reporte de pagos estará vacío
- **No encuentra el archivo PDF**: Los reportes se guardan en la carpeta `reports/` en el directorio de instalación del sistema con el formato: `TipoReporte_timestamp.pdf`
- **Error al abrir el PDF**: Asegúrese de tener un lector de PDF instalado (Adobe Reader, navegador web, etc.)

---

## Funciones Adicionales

### Respaldo Manual
Accesible desde el Dashboard, permite crear una copia de seguridad inmediata de todos los datos del sistema.

**Cómo usar**:
1. Presione "Respaldo Manual" en el Dashboard
2. Confirme la operación
3. El sistema copiará todos los archivos de datos a la carpeta `backups/backup_YYYYMMDD_HHMMSS/`

**Errores comunes**:
- **"Error al crear respaldo"**: Verifique que tiene permisos de escritura en la carpeta del sistema
- **El respaldo parece vacío**: Revise que existen datos en la carpeta `data/` antes de crear el respaldo

### Cerrar Sesión
Siempre use el botón "Cerrar Sesión" del Dashboard para salir del sistema de forma segura.

**Cómo usar**:
1. Presione "Cerrar Sesión" en el Dashboard
2. Confirme la acción en el diálogo
3. Será redirigido a la pantalla de login

---

## Consejos Generales

### Navegación
- Use el botón "< Volver al Dashboard" o "< Volver" en la parte superior de cada pantalla para regresar al menú principal
- Evite cerrar la ventana bruscamente; use siempre "Cerrar Sesión"

### Búsqueda y Filtrado
- Los campos de búsqueda filtran en tiempo real mientras escribe
- Las búsquedas no distinguen entre mayúsculas y minúsculas

### Formularios
- Los campos obligatorios deben completarse antes de poder guardar
- Use "Limpiar Campos" para vaciar formularios y empezar de nuevo
- Al seleccionar un registro de la tabla, sus datos aparecen automáticamente en el formulario

### Validaciones
- El sistema valida automáticamente formatos de email, números positivos y campos obligatorios
- Los mensajes de error aparecen en rojo debajo de los formularios

### Datos de Prueba
- El sistema incluye 20 clientes de ejemplo la primera vez que se ejecuta
- El usuario administrador inicial es: `admin` / `123`
- Puede eliminar o modificar estos datos de prueba según necesite

---

## Solución de Problemas Generales

### El sistema no inicia
- Verifique que Java está instalado correctamente
- Asegúrese de tener permisos de lectura/escritura en la carpeta del programa

### Los datos no se guardan
- Verifique que la carpeta `data/` existe y tiene permisos de escritura
- Revise que no haya cerrado el programa de forma abrupta

### Error al cargar vista
- Asegúrese de que todos los archivos del sistema están presentes
- Verifique que la carpeta `resources/` contiene todas las vistas FXML

### Rendimiento lento
- Si tiene muchos registros (miles), considere hacer limpiezas periódicas de datos antiguos
- Use los filtros de búsqueda para reducir la cantidad de información mostrada

---

## Información de Contacto y Soporte

Para asistencia técnica o reportar problemas, contacte al administrador del sistema de su gimnasio.

**Versión del Manual**: 1.0  
**Fecha**: Noviembre 2025

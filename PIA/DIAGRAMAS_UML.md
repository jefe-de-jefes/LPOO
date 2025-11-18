# Diagramas UML del Sistema GymPOS

## 1. Diagrama de Clases Completo del Sistema

```mermaid
classDiagram
    %% ============ CAPA APP ============
    class MainApp {
        +start(Stage) void
        +stop() void
        +main(String[]) void
    }
    
    class AppContext {
        +Employee activeUser
        +AuthService authService
        +ClientService clientService
        +MembershipService membershipService
        +PaymentService paymentService
        +ReportService reportService
        +AccessService accessService
        +InventoryService inventoryService
        +ScheduleService scheduleService
        +BackupService backupService
        +RewardService rewardService
        +init() void
        +shutdown() void
    }
    
    class Navigation {
        +load(String) Parent
    }

    %% ============ CAPA MODEL ============
    class Client {
        -String id
        -String fullName
        -String email
        -String phone
        -boolean active
        -LocalDate createdAt
        +getId() String
        +getFullName() String
        +getEmail() String
        +getPhone() String
        +isActive() boolean
        +setActive(boolean) void
        +getStatusString() String
    }
    
    class Employee {
        -String id
        -String name
        -String username
        -String passwordHash
        +getId() String
        +getName() String
        +getUsername() String
        +getPasswordHash() String
        +setPasswordHash(String) void
    }
    
    class Membership {
        <<enumeration>> Type
        -String clientId
        -Type type
        -LocalDate start
        -LocalDate end
        -double pricePaid
        -double discountApplied
        +getClientId() String
        +getType() Type
        +getStart() LocalDate
        +getEnd() LocalDate
        +setEnd(LocalDate) void
    }
    
    class MembershipType {
        <<enumeration>>
        BASIC
        STANDARD
        PREMIUM
        ANUAL
    }
    
    class Payment {
        -String id
        -String clientId
        -double amount
        -LocalDateTime timestamp
        -String method
        +getId() String
        +getClientId() String
        +getAmount() double
        +getTimestamp() LocalDateTime
        +getMethod() String
    }
    
    class AccessLog {
        -String clientId
        -String classScheduleId
        -LocalDateTime timestamp
        -String action
        +getClientId() String
        +getTimestamp() LocalDateTime
        +getAction() String
        +getClassScheduleId() String
    }
    
    class ClassSchedule {
        -String id
        -String className
        -DayOfWeek day
        -LocalTime time
        -int duration
        -int capacity
        +getId() String
        +getClassName() String
        +getDay() DayOfWeek
        +getTime() LocalTime
        +getDuration() int
        +getCapacity() int
        +getEndTime() LocalTime
        +getTimeRange() String
    }
    
    class InventoryItem {
        -String id
        -String name
        -int quantity
        -String location
        +getId() String
        +getName() String
        +getQuantity() int
        +getLocation() String
        +setQuantity(int) void
    }
    
    class RewardPoint {
        -String clientId
        -int points
        +getClientId() String
        +getPoints() int
        +add(int) void
        +redeem(int) void
    }

    %% ============ CAPA SERVICE ============
    class AuthService {
        -List~Employee~ employees
        +login(String, String) Employee
        +addEmployee(Employee) void
        +updateEmployee(Employee) void
        +removeEmployee(String) void
        +getEmployeeById(String) Optional~Employee~
        +getAllEmployees() List~Employee~
        +hashPassword(String) String
    }
    
    class ClientService {
        -List~Client~ clients
        +addClient(Client) void
        +updateClient(Client) void
        +removeClient(String) void
        +getClientById(String) Client
        +getAllClients() List~Client~
        +getActiveClients() List~Client~
        +generateNextId() String
        +deactivateClient(String) void
    }
    
    class MembershipService {
        -List~Membership~ memberships
        +createMembership(String, Type, double) Membership
        +renewMembership(String, Type, double) void
        +cancelMembership(String) void
        +hasActiveMembership(String) boolean
        +getCurrentMembership(String) Membership
        +getExpiringMemberships(int) List~Membership~
        +calculateEndDate(LocalDate, Type) LocalDate
        +getBasePrice(Type) double
        +scheduleExpiringNotifications() void
    }
    
    class PaymentService {
        -List~Payment~ payments
        +processPayment(Payment) void
        +getAllPayments() List~Payment~
    }
    
    class AccessService {
        -List~AccessLog~ accessLogs
        +registerEntry(Client) void
        +registerExit(Client) void
        +registerClassEntry(Client, ClassSchedule) void
        +isClientInside(String) boolean
        +getClientAccessHistory(String) List~AccessLog~
        +getAllAccessLogs() List~AccessLog~
    }
    
    class InventoryService {
        -List~InventoryItem~ items
        +addItem(InventoryItem) void
        +updateItem(InventoryItem) void
        +removeItem(String) void
        +getItemById(String) InventoryItem
        +getAllItems() List~InventoryItem~
    }
    
    class ScheduleService {
        -List~ClassSchedule~ schedules
        +addClass(ClassSchedule) void
        +updateClass(ClassSchedule) void
        +removeClass(String) void
        +getAllClasses() List~ClassSchedule~
    }
    
    class BackupService {
        +manualBackup() void
        +scheduleNightlyBackup() void
    }
    
    class ReportService {
        +generatePaymentsReport(List~Payment~) void
        +generateClientsReport(List~Client~) void
        +generateMembershipsReport(List~Membership~) void
        +generateAccessReport(List~AccessLog~) void
        +generateInventoryReport(List~InventoryItem~) void
        +generateScheduleReport(List~ClassSchedule~) void
        +generateBackupsReport(List~String~) void
    }
    
    class RewardService {
        -List~RewardPoint~ rewards
        +getPoints(String) RewardPoint
        +addPoints(String, int) void
        +redeemPoints(String, int) void
    }

    %% ============ CAPA CONTROLLER ============
    class LoginController {
        -TextField usernameField
        -PasswordField passwordField
        -Label errorLabel
        +onLogin(ActionEvent) void
    }
    
    class DashboardController {
        -BorderPane root
        -Label timeLabel
        -Label dateLabel
        +goToGestionClientes() void
        +goToSistemaMembresias() void
        +goToControlAcceso() void
        +goToGeneradorReportes() void
        +goToProcesadorPagos() void
        +goToInventario() void
        +goToGestionEmpleados() void
        +goToCalendario() void
        +handleManualBackup() void
        +handleLogout() void
    }
    
    class GestionClientesController {
        +handleAdd() void
        +handleEdit() void
        +handleDelete() void
        +refreshTable() void
    }
    
    class SistemaMembresiaController {
        +handleCreateMembership() void
        +handleRenewMembership() void
        +handleCancelMembership() void
    }
    
    class ControlAccesoController {
        +handleEntry() void
        +handleExit() void
        +handleClassEntry() void
    }
    
    class ProcesadorPagosController {
        +setPaymentData(...) void
        +handlePayment() void
    }
    
    class InventarioController {
        +handleAdd() void
        +handleUpdate() void
        +handleRemove() void
    }
    
    class SchedulesController {
        +handleAddClass() void
        +handleUpdateClass() void
        +handleRemoveClass() void
    }
    
    class GestionEmpleadosController {
        +handleAddEmployee() void
        +handleUpdateEmployee() void
        +handleRemoveEmployee() void
    }
    
    class GeneradorReportesController {
        +generateReport(String) void
    }

    %% ============ CAPA UTIL ============
    class SerializationStore {
        -String basePath
        +initBasePaths(String) void
        +save(String, Object) void
        +load(String) T
    }
    
    class Threading {
        -ExecutorService executor
        +init() void
        +shutdown() void
        +submitTask(Runnable) void
    }
    
    class Validation {
        +isNullOrEmpty(String) boolean
        +isValidEmail(String) boolean
        +isPositive(double) boolean
    }
    
    class Formatters {
        +formatCurrency(double) String
        +formatDate(LocalDate) String
    }
    
    class PopupUtils {
        +showPaymentPopup(...) ProcesadorPagosController
    }
    
    class DataSeeder {
        +seed() void
    }
    
    class Exceptions {
        <<interface>>
    }
    
    class AccessDeniedException {
        +AccessDeniedException(String)
    }
    
    class AuthenticationException {
        +AuthenticationException(String)
    }
    
    class MembershipException {
        +MembershipException(String)
    }
    
    class PaymentException {
        +PaymentException(String)
    }
    
    class ValidationException {
        +ValidationException(String)
    }

    %% ============ RELACIONES ============
    
    %% App con Services
    AppContext --> AuthService
    AppContext --> ClientService
    AppContext --> MembershipService
    AppContext --> PaymentService
    AppContext --> ReportService
    AppContext --> AccessService
    AppContext --> InventoryService
    AppContext --> ScheduleService
    AppContext --> BackupService
    AppContext --> RewardService
    AppContext --> Employee
    
    MainApp --> AppContext
    MainApp --> DataSeeder
    
    %% Services con Models
    AuthService --> Employee
    ClientService --> Client
    MembershipService --> Membership
    PaymentService --> Payment
    AccessService --> AccessLog
    AccessService --> Client
    AccessService --> ClassSchedule
    InventoryService --> InventoryItem
    ScheduleService --> ClassSchedule
    RewardService --> RewardPoint
    
    %% Services con Utils
    AuthService --> SerializationStore
    ClientService --> SerializationStore
    MembershipService --> SerializationStore
    MembershipService --> Threading
    PaymentService --> SerializationStore
    AccessService --> SerializationStore
    InventoryService --> SerializationStore
    ScheduleService --> SerializationStore
    RewardService --> SerializationStore
    
    ReportService --> Payment
    ReportService --> Client
    ReportService --> Membership
    ReportService --> AccessLog
    ReportService --> InventoryItem
    ReportService --> ClassSchedule
    
    %% Controllers con Services
    LoginController --> AppContext
    LoginController --> Navigation
    DashboardController --> AppContext
    DashboardController --> Navigation
    GestionClientesController --> AppContext
    SistemaMembresiaController --> AppContext
    ControlAccesoController --> AppContext
    ProcesadorPagosController --> AppContext
    InventarioController --> AppContext
    SchedulesController --> AppContext
    GestionEmpleadosController --> AppContext
    GeneradorReportesController --> AppContext
    
    %% Controllers con Utils
    GestionClientesController --> Validation
    SistemaMembresiaController --> PopupUtils
    
    %% Model relationships
    Membership --> MembershipType
    Client "1" -- "0..*" Membership
    Client "1" -- "0..*" Payment
    Client "1" -- "0..*" AccessLog
    Client "1" -- "0..1" RewardPoint
    ClassSchedule "1" -- "0..*" AccessLog
    
    %% Exceptions
    Exceptions <|-- AccessDeniedException
    Exceptions <|-- AuthenticationException
    Exceptions <|-- MembershipException
    Exceptions <|-- PaymentException
    Exceptions <|-- ValidationException
```

## 2. Diagrama de Secuencia: Proceso de Login

```mermaid
sequenceDiagram
    actor Usuario
    participant LoginView as Login View (FXML)
    participant LoginCtrl as LoginController
    participant AppCtx as AppContext
    participant AuthSvc as AuthService
    participant Store as SerializationStore
    participant Nav as Navigation
    participant Dashboard as Dashboard View
    
    Usuario->>LoginView: Ingresa usuario y contraseña
    Usuario->>LoginView: Click en "Login"
    LoginView->>LoginCtrl: onLogin(ActionEvent)
    
    LoginCtrl->>LoginCtrl: Obtiene texto de campos
    LoginCtrl->>AppCtx: authService
    LoginCtrl->>AuthSvc: login(username, password)
    
    AuthSvc->>AuthSvc: hashPassword(password)
    AuthSvc->>AuthSvc: Busca en lista de empleados
    
    alt Credenciales válidas
        AuthSvc-->>LoginCtrl: Employee
        LoginCtrl->>AppCtx: activeUser = employee
        LoginCtrl->>Nav: load("/view/dashboard.fxml")
        Nav->>Dashboard: Carga vista
        Nav-->>LoginCtrl: Parent (dashboard)
        LoginCtrl->>LoginView: setRoot(dashboard)
        LoginView-->>Usuario: Muestra Dashboard
    else Credenciales inválidas
        AuthSvc-->>LoginCtrl: throw AuthenticationException
        LoginCtrl->>LoginView: errorLabel.setText(error)
        LoginView-->>Usuario: Muestra mensaje de error
    end
```

## 3. Diagrama de Secuencia: Creación de Membresía con Pago

```mermaid
sequenceDiagram
    actor Usuario
    participant SistemaMembresiaView as Vista Membresías
    participant SistemaMembresiaCtrl as SistemaMembresiaController
    participant PopupUtil as PopupUtils
    participant ProcesadorPagosView as Popup Pagos
    participant ProcesadorPagosCtrl as ProcesadorPagosController
    participant AppCtx as AppContext
    participant MembershipSvc as MembershipService
    participant PaymentSvc as PaymentService
    participant RewardSvc as RewardService
    participant ReportSvc as ReportService
    participant Store as SerializationStore
    
    Usuario->>SistemaMembresiaView: Selecciona cliente
    Usuario->>SistemaMembresiaView: Selecciona tipo de membresía
    Usuario->>SistemaMembresiaView: Ingresa descuento (opcional)
    Usuario->>SistemaMembresiaView: Click "Crear Membresía"
    
    SistemaMembresiaView->>SistemaMembresiaCtrl: handleCreateMembership()
    SistemaMembresiaCtrl->>MembershipSvc: hasActiveMembership(clientId)
    
    alt Cliente ya tiene membresía activa
        MembershipSvc-->>SistemaMembresiaCtrl: true
        SistemaMembresiaCtrl-->>Usuario: Alert: "Cliente ya tiene membresía activa"
    else Cliente sin membresía activa
        MembershipSvc-->>SistemaMembresiaCtrl: false
        SistemaMembresiaCtrl->>MembershipSvc: getBasePrice(type)
        MembershipSvc-->>SistemaMembresiaCtrl: basePrice
        
        SistemaMembresiaCtrl->>PopupUtil: showPaymentPopup(clientId, type, basePrice, discount, false, null)
        PopupUtil->>ProcesadorPagosView: Carga FXML
        PopupUtil->>ProcesadorPagosCtrl: setPaymentData(...)
        ProcesadorPagosCtrl->>ProcesadorPagosView: Muestra datos calculados
        ProcesadorPagosView-->>Usuario: Modal de pago
        
        Usuario->>ProcesadorPagosView: Selecciona método (CASH/CARD)
        Usuario->>ProcesadorPagosView: Click "Confirmar Pago"
        
        ProcesadorPagosView->>ProcesadorPagosCtrl: handlePayment()
        
        ProcesadorPagosCtrl->>MembershipSvc: createMembership(clientId, type, discount)
        MembershipSvc->>MembershipSvc: calculateEndDate(start, type)
        MembershipSvc->>MembershipSvc: new Membership(...)
        MembershipSvc->>Store: save("memberships.dat", memberships)
        Store-->>MembershipSvc: OK
        MembershipSvc-->>ProcesadorPagosCtrl: Membership
        
        ProcesadorPagosCtrl->>PaymentSvc: processPayment(payment)
        PaymentSvc->>PaymentSvc: Valida monto > 0
        PaymentSvc->>Store: save("payments.dat", payments)
        Store-->>PaymentSvc: OK
        PaymentSvc-->>ProcesadorPagosCtrl: OK
        
        ProcesadorPagosCtrl->>RewardSvc: addPoints(clientId, points)
        RewardSvc->>Store: save("rewards.dat", rewards)
        Store-->>RewardSvc: OK
        RewardSvc-->>ProcesadorPagosCtrl: OK
        
        ProcesadorPagosCtrl->>ReportSvc: generatePaymentReceipt(payment)
        ReportSvc->>ReportSvc: Genera PDF
        ReportSvc-->>ProcesadorPagosCtrl: Archivo guardado
        
        ProcesadorPagosCtrl->>ProcesadorPagosView: Cierra modal
        ProcesadorPagosView-->>SistemaMembresiaCtrl: Retorna control
        SistemaMembresiaCtrl->>SistemaMembresiaView: refreshTable()
        SistemaMembresiaView-->>Usuario: Muestra membresía creada
        SistemaMembresiaCtrl-->>Usuario: Alert: "Membresía creada exitosamente"
    end
```

## 4. Diagrama de Secuencia: Control de Acceso a Clase

```mermaid
sequenceDiagram
    actor Usuario
    participant ControlAccesoView as Vista Control Acceso
    participant ControlAccesoCtrl as ControlAccesoController
    participant AppCtx as AppContext
    participant AccessSvc as AccessService
    participant MembershipSvc as MembershipService
    participant ClientSvc as ClientService
    participant ScheduleSvc as ScheduleService
    participant Store as SerializationStore
    
    Usuario->>ControlAccesoView: Ingresa ID de cliente
    Usuario->>ControlAccesoView: Selecciona clase
    Usuario->>ControlAccesoView: Click "Registrar Entrada a Clase"
    
    ControlAccesoView->>ControlAccesoCtrl: handleClassEntry()
    ControlAccesoCtrl->>ClientSvc: getClientById(clientId)
    
    alt Cliente no encontrado
        ClientSvc-->>ControlAccesoCtrl: null
        ControlAccesoCtrl-->>Usuario: Alert: "Cliente no encontrado"
    else Cliente encontrado
        ClientSvc-->>ControlAccesoCtrl: Client
        
        ControlAccesoCtrl->>ScheduleSvc: getAllClasses()
        ScheduleSvc-->>ControlAccesoCtrl: List<ClassSchedule>
        ControlAccesoCtrl->>ControlAccesoCtrl: Obtiene clase seleccionada
        
        ControlAccesoCtrl->>AccessSvc: registerClassEntry(client, schedule)
        
        AccessSvc->>MembershipSvc: hasActiveMembership(clientId)
        
        alt Membresía inactiva/vencida
            MembershipSvc-->>AccessSvc: false
            AccessSvc-->>ControlAccesoCtrl: throw AccessDeniedException("Membresía vencida")
            ControlAccesoCtrl-->>Usuario: Alert: "Acceso denegado: Membresía vencida"
        else Membresía activa
            MembershipSvc-->>AccessSvc: true
            
            AccessSvc->>AccessSvc: Valida día de clase == hoy
            
            alt Clase no programada para hoy
                AccessSvc-->>ControlAccesoCtrl: throw AccessDeniedException("Clase no es hoy")
                ControlAccesoCtrl-->>Usuario: Alert: "Esta clase no está programada para hoy"
            else Clase es hoy
                AccessSvc->>AccessSvc: Valida horario (5 min antes - 15 min después)
                
                alt Fuera de horario
                    AccessSvc-->>ControlAccesoCtrl: throw AccessDeniedException("Fuera de horario")
                    ControlAccesoCtrl-->>Usuario: Alert: "Demasiado temprano o tarde"
                else Dentro de horario
                    AccessSvc->>AccessSvc: Cuenta ocupación actual
                    
                    alt Cupo lleno
                        AccessSvc-->>ControlAccesoCtrl: throw AccessDeniedException("CUPO LLENO")
                        ControlAccesoCtrl-->>Usuario: Alert: "Cupo lleno (20/20)"
                    else Cupo disponible
                        AccessSvc->>AccessSvc: Verifica si ya está registrado
                        
                        alt Ya registrado
                            AccessSvc-->>ControlAccesoCtrl: throw AccessDeniedException("Ya registrado")
                            ControlAccesoCtrl-->>Usuario: Alert: "Ya registró su entrada"
                        else No registrado
                            AccessSvc->>AccessSvc: new AccessLog("ENTRADA", classScheduleId)
                            AccessSvc->>Store: save("access_logs.dat", logs)
                            Store-->>AccessSvc: OK
                            AccessSvc-->>ControlAccesoCtrl: OK
                            ControlAccesoCtrl->>ControlAccesoView: refreshHistory()
                            ControlAccesoView-->>Usuario: Muestra registro exitoso
                            ControlAccesoCtrl-->>Usuario: Alert: "Entrada registrada exitosamente"
                        end
                    end
                end
            end
        end
    end
```

## 5. Diagrama de Arquitectura General del Sistema

```mermaid
graph TB
    subgraph "Capa de Presentación"
        A[JavaFX Views FXML]
        B[Controllers]
    end
    
    subgraph "Capa de Aplicación"
        C[MainApp]
        D[AppContext]
        E[Navigation]
    end
    
    subgraph "Capa de Lógica de Negocio"
        F[AuthService]
        G[ClientService]
        H[MembershipService]
        I[PaymentService]
        J[AccessService]
        K[InventoryService]
        L[ScheduleService]
        M[BackupService]
        N[ReportService]
        O[RewardService]
    end
    
    subgraph "Capa de Dominio"
        P[Employee]
        Q[Client]
        R[Membership]
        S[Payment]
        T[AccessLog]
        U[ClassSchedule]
        V[InventoryItem]
        W[RewardPoint]
    end
    
    subgraph "Capa de Infraestructura"
        X[SerializationStore]
        Y[Threading]
        Z[Validation]
        AA[Formatters]
        AB[PopupUtils]
        AC[Exceptions]
    end
    
    subgraph "Persistencia"
        AD[(Archivos .dat)]
    end
    
    subgraph "Generación de Reportes"
        AE[iText PDF Library]
        AF[/Reportes PDF/]
    end
    
    A --> B
    B --> D
    B --> E
    B --> Z
    B --> AA
    B --> AB
    
    C --> D
    D --> F
    D --> G
    D --> H
    D --> I
    D --> J
    D --> K
    D --> L
    D --> M
    D --> N
    D --> O
    
    F --> P
    G --> Q
    H --> R
    I --> S
    J --> T
    J --> U
    K --> V
    O --> W
    
    F --> X
    G --> X
    H --> X
    I --> X
    J --> X
    K --> X
    L --> X
    O --> X
    
    H --> Y
    M --> Y
    
    X --> AD
    
    N --> AE
    AE --> AF
    
    F -.throw.-> AC
    G -.throw.-> AC
    H -.throw.-> AC
    I -.throw.-> AC
    J -.throw.-> AC
    
    style A fill:#e1f5ff
    style B fill:#e1f5ff
    style C fill:#fff4e1
    style D fill:#fff4e1
    style E fill:#fff4e1
    style F fill:#e8f5e9
    style G fill:#e8f5e9
    style H fill:#e8f5e9
    style I fill:#e8f5e9
    style J fill:#e8f5e9
    style K fill:#e8f5e9
    style L fill:#e8f5e9
    style M fill:#e8f5e9
    style N fill:#e8f5e9
    style O fill:#e8f5e9
    style P fill:#fce4ec
    style Q fill:#fce4ec
    style R fill:#fce4ec
    style S fill:#fce4ec
    style T fill:#fce4ec
    style U fill:#fce4ec
    style V fill:#fce4ec
    style W fill:#fce4ec
    style X fill:#f3e5f5
    style Y fill:#f3e5f5
    style Z fill:#f3e5f5
    style AA fill:#f3e5f5
    style AB fill:#f3e5f5
    style AC fill:#f3e5f5
    style AD fill:#ffe0b2
    style AE fill:#e0f2f1
    style AF fill:#e0f2f1
```

## 6. Diagrama de Componentes: Estructura de Módulos

```mermaid
graph LR
    subgraph "com.gympos.app"
        A1[MainApp]
        A2[AppContext]
        A3[Navigation]
    end
    
    subgraph "com.gympos.controller"
        C1[LoginController]
        C2[DashboardController]
        C3[GestionClientesController]
        C4[SistemaMembresiaController]
        C5[ControlAccesoController]
        C6[Otros Controllers...]
    end
    
    subgraph "com.gympos.service"
        S1[AuthService]
        S2[ClientService]
        S3[MembershipService]
        S4[PaymentService]
        S5[AccessService]
        S6[Otros Services...]
    end
    
    subgraph "com.gympos.model"
        M1[Employee]
        M2[Client]
        M3[Membership]
        M4[Payment]
        M5[AccessLog]
        M6[Otros Models...]
    end
    
    subgraph "com.gympos.util"
        U1[SerializationStore]
        U2[Threading]
        U3[Validation]
        U4[Formatters]
        U5[Exceptions]
        U6[Otros Utils...]
    end
    
    subgraph "com.gympos.view"
        V1[login.fxml]
        V2[dashboard.fxml]
        V3[gestion_clientes.fxml]
        V4[Otras vistas FXML...]
    end
    
    A1 --> A2
    A1 --> V1
    A2 --> S1
    A2 --> S2
    A2 --> S3
    A2 --> S4
    A2 --> S5
    
    C1 --> A2
    C1 --> A3
    C2 --> A2
    C3 --> A2
    C4 --> A2
    C5 --> A2
    
    V1 --> C1
    V2 --> C2
    V3 --> C3
    
    S1 --> M1
    S2 --> M2
    S3 --> M3
    S4 --> M4
    S5 --> M5
    
    S1 --> U1
    S2 --> U1
    S3 --> U1
    
    C3 --> U3
    C4 --> U4
    
    S1 -.-> U5
    S2 -.-> U5
    S5 -.-> U5
    
    style A1 fill:#fff4e1
    style A2 fill:#fff4e1
    style A3 fill:#fff4e1
    style S1 fill:#e8f5e9
    style S2 fill:#e8f5e9
    style S3 fill:#e8f5e9
    style S4 fill:#e8f5e9
    style S5 fill:#e8f5e9
    style M1 fill:#fce4ec
    style M2 fill:#fce4ec
    style M3 fill:#fce4ec
    style M4 fill:#fce4ec
    style M5 fill:#fce4ec
    style U1 fill:#f3e5f5
    style U2 fill:#f3e5f5
    style U3 fill:#f3e5f5
    style U4 fill:#f3e5f5
    style U5 fill:#f3e5f5
    style V1 fill:#e1f5ff
    style V2 fill:#e1f5ff
    style V3 fill:#e1f5ff
```

## 7. Diagrama de Secuencia: Generación de Reporte PDF

```mermaid
sequenceDiagram
    actor Usuario
    participant ReportView as Vista Reportes
    participant ReportCtrl as GeneradorReportesController
    participant AppCtx as AppContext
    participant PaymentSvc as PaymentService
    participant ReportSvc as ReportService
    participant iText as iText Library
    participant FileSystem as Sistema de Archivos
    
    Usuario->>ReportView: Selecciona "Reporte de Pagos"
    Usuario->>ReportView: Click "Generar Reporte"
    
    ReportView->>ReportCtrl: generateReport("PAYMENTS")
    ReportCtrl->>PaymentSvc: getAllPayments()
    PaymentSvc-->>ReportCtrl: List<Payment>
    
    ReportCtrl->>ReportSvc: generatePaymentsReport(payments)
    ReportSvc->>ReportSvc: createDocument("Reporte de Pagos", filename)
    
    ReportSvc->>iText: new Document()
    ReportSvc->>iText: document.open()
    ReportSvc->>iText: document.add(new Paragraph("GYMPOS - Reporte de Pagos"))
    ReportSvc->>iText: document.add(new Paragraph("Generado: " + timestamp))
    
    ReportSvc->>iText: new PdfPTable(4)
    ReportSvc->>iText: table.addCell("ID Pago")
    ReportSvc->>iText: table.addCell("Cliente")
    ReportSvc->>iText: table.addCell("Monto")
    ReportSvc->>iText: table.addCell("Método")
    
    loop Para cada Payment
        ReportSvc->>iText: table.addCell(payment.getId())
        ReportSvc->>iText: table.addCell(payment.getClientId())
        ReportSvc->>iText: table.addCell(formatCurrency(payment.getAmount()))
        ReportSvc->>iText: table.addCell(payment.getMethod())
    end
    
    ReportSvc->>iText: document.add(table)
    ReportSvc->>iText: document.close()
    
    iText->>FileSystem: Escribe archivo PDF en reports/
    FileSystem-->>iText: OK
    iText-->>ReportSvc: PDF generado
    ReportSvc-->>ReportCtrl: OK
    
    ReportCtrl-->>Usuario: Alert: "Reporte generado en reports/Pagos_timestamp.pdf"
```

## Descripción de la Arquitectura

### Patrón Arquitectónico
El sistema GymPOS implementa una **arquitectura en capas** con separación clara de responsabilidades:

1. **Capa de Presentación**: Vistas FXML y Controllers JavaFX
2. **Capa de Aplicación**: Punto de entrada y coordinación global (MainApp, AppContext)
3. **Capa de Lógica de Negocio**: Services que encapsulan reglas de negocio
4. **Capa de Dominio**: Entidades del modelo de datos
5. **Capa de Infraestructura**: Utilidades transversales y persistencia

### Principios Aplicados
- **Separation of Concerns**: Cada capa tiene responsabilidades específicas
- **Dependency Injection**: AppContext actúa como contenedor de servicios
- **Single Responsibility**: Cada clase tiene un propósito único
- **Data Transfer**: Modelos como DTOs entre capas
- **Facade Pattern**: AppContext simplifica acceso a servicios
- **Observer Pattern**: JavaFX properties para binding de UI
- **Template Method**: Controllers siguen flujo estándar de validación-servicio-actualización

### Flujo de Datos
```
Usuario → Vista FXML → Controller → Service → Model → SerializationStore → Archivo .dat
```

### Características Técnicas
- **Persistencia**: Serialización Java (archivos .dat)
- **Concurrencia**: ExecutorService para tareas background
- **Reportes**: iText para generación de PDFs
- **UI**: JavaFX con FXML + CSS
- **Validación**: Centralizada en capa de servicios
- **Excepciones**: Jerarquía personalizada para errores de negocio

package practica10;

// Importaciones necesarias para ExecutorService y TimeUnit
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
// Mantenemos la importación de tu librería Validar (aunque se recomienda usar System.out.println)
import static lpoo.utils.Validar.*; 

public class BancoConcurrenteLS {

    public static void main(String[] args) throws InterruptedException {
        
        cleaner(); 
        double saldoInicial = 1000.00;
        String titular = "Luis Segobia";
        String cotitular1 = "Aldo Davila";
        String cotitular2 = "Beto Sanchez";

        CuentaBancaria cuentaPrincipal = new CuentaBancaria(saldoInicial, titular);
        cuentaPrincipal.agregarCotitular1(cotitular1);
        cuentaPrincipal.agregarCotitular2(cotitular2);

        print("\n=======================================================");
        print("1. INICIO - SIMULADOR DE BANCO ");
        print("=======================================================");
        print("Saldo Inicial de la Cuenta: $" + cuentaPrincipal.getSaldo());

        CajeroThread14 cajero1 = new CajeroThread14(cuentaPrincipal, "ATM-1");
        Thread cliente1 = new Thread(new ClienteRunnable10(cuentaPrincipal, cuentaPrincipal.getCotitular1()));
        Thread cliente2 = new Thread(new ClienteRunnable10(cuentaPrincipal, cuentaPrincipal.getCotitular2()));

        cajero1.start();
        cliente1.start();
        cliente2.start();

        cajero1.join();
        cliente1.join();
        cliente2.join();

        print("\nFIN SIMULADOR BANCO. Saldo Final Real (debe ser $1000 + 200*(total cajeros) + 25*(total clientes)): $" + cuentaPrincipal.getSaldo());
        print("(Si el Saldo es correcto, la sincronización funcionó.)");
        
        
        print("\n=======================================================");
        print("2. INICIO - PATRÓN PRODUCTOR-CONSUMIDOR ");
        print("=======================================================");

        BufferSegobia<Integer> bufferPC = new BufferSegobia<>(); 

        Thread p1 = new Thread(new ProductorSegobia(bufferPC), "Productor-A");
        Thread p2 = new Thread(new ProductorSegobia(bufferPC), "Productor-B");
        Thread c1 = new Thread(new ConsumidorSegobia(bufferPC), "Consumidor-X");
        
        p1.start();
        p2.start();
        c1.start();

        p1.join();
        p2.join();
        c1.join();
        
        print("FIN PATRÓN PRODUCTOR-CONSUMIDOR.");


        print("\n=======================================================");
        print("3. DEMOSTRACIÓN DE DEADLOCK ");
        print("=======================================================");
        
        Object recursoA = new Object();
        Object recursoB = new Object();

        Runnable t1Dead = () -> {
            synchronized (recursoA) { 
                print("T1: Adquirió Recurso A. Esperando Recurso B...");
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (recursoB) { 
                    print("T1: Adquirió A y B. (¡Esto no debería pasar!)");
                }
            }
        };

        Runnable t2Dead = () -> {
            synchronized (recursoB) { 
                print("T2: Adquirió Recurso B. Esperando Recurso A...");
                synchronized (recursoA) { // Aquí espera A, que lo tiene T1
                    print("T2: Adquirió B y A. (¡Esto no debería pasar!)");
                }
            }
        };

            
        Thread t1 = new Thread(t1Dead, "Deadlock-T1");
        t1.setDaemon(true);
        t1.start();

        Thread t2 = new Thread(t2Dead, "Deadlock-T2");
        t2.setDaemon(true); 
        t2.start();

        
        Thread.sleep(500);
        print("ADVERTENCIA: Los hilos T1 y T2 están bloqueados (Deadlock). El programa principal continúa.");


        print("\n=======================================================");
        print("4. USO DE ThreadPoolSegobia (Pool Personalizado)");
        print("=======================================================");

        ThreadPoolSegobia poolPersonalizado = new ThreadPoolSegobia(2);

        for(int i = 1; i <= 5; i++) {
             poolPersonalizado.execute(() -> print("Tarea " + Thread.currentThread().getName() + " procesando..."));
        }

        Thread.sleep(500); 
        poolPersonalizado.shutdown(); 


        
        print("\n=======================================================");
        print("5. DEMOSTRACIÓN DE ExecutorService (Pool Estándar)");
        print("=======================================================");
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        executor.submit(() -> print("ExecutorService: Tarea A."));
        executor.submit(() -> print("ExecutorService: Tarea B."));
        executor.submit(() -> print("ExecutorService: Tarea C."));
        
        executor.shutdown(); 
        executor.awaitTermination(1, TimeUnit.SECONDS); 
        
        print("\nPROGRAMA PRINCIPAL COMPLETADO.");
    }
}

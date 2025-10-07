

# Compilar clases
javac -cp ../libreria/bin -d bin src/practica7/*.java
# Compilar tests
javac -cp "bin:../libreria/bin:../junit-platform-console-standalone-1.13.4.jar" -d bin test/practica7/*.java

# Ejecutar tests

java -cp bin practica7.SistemaBancoLS
java -cp bin:../libreria/bin practica7.FileResource 
java -jar ../junit-platform-console-standalone-1.13.4.jar -cp "bin:../libreria/bin" --scan-classpath


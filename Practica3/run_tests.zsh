
javac -d bin -cp "../libreria/bin" src/practica3/*.java

# Compilar los tests
javac -d bin -cp "bin:../libreria/bin:../junit-platform-console-standalone-1.13.4.jar" test/practica3/*.java

# Ejecutar los tests con JUnit
java -jar ../junit-platform-console-standalone-1.13.4.jar -cp "bin:../libreria/bin" --scan-classpath


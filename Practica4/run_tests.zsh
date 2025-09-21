
#!/bin/zsh

# Compilar clases
javac -cp ../libreria/bin -d bin src/practica4/*.java
# Compilar tests
javac -cp "bin:../libreria/bin:junit-platform-console-standalone-1.13.4.jar" -d bin test/practica4/*.java

# Ejecutar tests

java -jar junit-platform-console-standalone-1.13.4.jar -cp "bin:../libreria/bin" --scan-classpath


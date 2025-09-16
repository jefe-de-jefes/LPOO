# Documentación Completa: Instalación de Java

Documenta el proceso de instalación de Java JDK en Kali Linux.

Pasos de instalación y configuración:

1. Actualizar repositorios y sistema:
sudo apt update
sudo apt upgrade -y

2. Instalar Java JDK 21:
sudo apt install openjdk-21-jdk -y

3. Verificar instalación:
java -version
# openjdk version "21.0.8" 2025-07-15
# penJDK Runtime Environment (build 21.0.8+9-Debian-1)
# openJDK 64-Bit Server VM (build 21.0.8+9-Debian-1, mixed mode, sharing)
javac -version
# javac 21.0.8

4. Configurar variable de entorno JAVA_HOME (opcional):
# echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.zshrc
# echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.zshrc
# source ~/.zshrc

5. Verificar variable JAVA_HOME:
# echo $JAVA_HOME
# Salida esperada:
# /usr/lib/jvm/java-21-openjdk-amd64

- Todos los pasos fueron realizados en zsh en Kali Linux.
Por Luis Fernando Segobia Torres 2177528

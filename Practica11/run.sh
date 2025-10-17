#!/bin/bash

# Ruta absoluta a JavaFX
JAVAFX_HOME=/usr/local/javafx-sdk-21.0.8/lib
MODULE_PATH=$(echo $JAVAFX_HOME/*.jar | tr ' ' ':')

# Ejecutar JAR con JavaFX
java --module-path "$MODULE_PATH" \
     --add-modules javafx.controls,javafx.fxml \
     -jar SistemaEstudiantil2177528.jar

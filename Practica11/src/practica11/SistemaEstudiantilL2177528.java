package practica11;

import static lpoo.utils.Validar.*;


import javafx.collections.FXCollections;//observableList
import javafx.collections.ObservableList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Optional;

public class SistemaEstudiantilL2177528 extends Application {


    private static final String RUTA_ICONO = "/practica11/recursos_Segobia/logo_principal.png"; 
    private static final String TITULO_APP = "Sistema Estudiantil L2177528";
    private static final String TITULO_MENU = "Menú Principal - Opciones CRUD";

    private static final ObservableList<Estudiantes> listaEstudiantes = FXCollections.observableArrayList();

    // --- CLASE PRINCIPAL: PANTALLA DE PRESENTACIÓN (SPLASH) ---

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITULO_APP + " - Presentación");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;"); // Fondo claro

        try {
            String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
            root.getStylesheets().add(cssPath);
            
        } catch (NullPointerException e) {
            System.err.println("ERROR: No se pudo cargar el CSS.");
        }

        Image iconoApp = null;
        try {
            iconoApp = new Image(getClass().getResourceAsStream(RUTA_ICONO));
            primaryStage.getIcons().add(iconoApp);
        } catch (Exception e) {
            print("\nADVERTENCIA: No se pudo cargar el icono: " + e.getMessage());
        }

        VBox vboxCentral = new VBox(20);
        vboxCentral.setAlignment(Pos.CENTER);
        vboxCentral.setPadding(new Insets(50));
        
        Label lblTitulo = new Label("SISTEMA DE REGISTRO ESTUDIANTIL");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setStyle("-fx-text-fill: #2c3e50;");
        
        ImageView imageViewLogo = new ImageView(iconoApp);
        imageViewLogo.setFitWidth(350); //tamaño logo
        imageViewLogo.setPreserveRatio(true);
        imageViewLogo.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0); -fx-cursor: hand;");

        // para abrir menu
        imageViewLogo.setOnMouseClicked(e -> {
            new VentanaMenu(); 
            primaryStage.close();
        });

        Label lblInstruccion = new Label("Haga clic en la imagen para empezar.");
        lblInstruccion.setStyle("-fx-font-size: 10pt; -fx-text-fill: #7f8c8d;");

        vboxCentral.getChildren().addAll(lblTitulo, imageViewLogo, lblInstruccion);
        root.setCenter(vboxCentral);

        Scene scene = new Scene(root, 500, 400);//ventana para crud 
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private class VentanaMenu extends Stage {
        public VentanaMenu() { 
            setTitle(TITULO_MENU);
            
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(30);
            grid.setVgap(30);
            grid.setPadding(new Insets(40));
            grid.setStyle("-fx-background-color: #f4f6f7;");

            Boton14Estilizado btnAgregar = new Boton14Estilizado("AGREGAR ESTUDIANTE", TipoBoton.MENU_CRUD);
            btnAgregar.setOnAction(e -> new VentanaAgregar(this).showAndWait());
            grid.add(btnAgregar, 0, 0); 

            Boton14Estilizado btnLeer = new Boton14Estilizado("VER ESTUDIANTES", TipoBoton.MENU_CRUD);
            btnLeer.setOnAction(e -> new VentanaVer(this).showAndWait()); 
            grid.add(btnLeer, 1, 0); 

            Boton14Estilizado btnActualizar = new Boton14Estilizado("ACTUALIZAR ESTUDIANTE", TipoBoton.MENU_CRUD);
            btnActualizar.setOnAction(e -> new VentanaActualizar(this).showAndWait());
            grid.add(btnActualizar, 0, 1);

            Boton14Estilizado btnEliminar = new Boton14Estilizado("ELIMINAR ESTUDIANTE", TipoBoton.MENU_CRUD);
            btnEliminar.setOnAction(e -> new VentanaEliminar(this).showAndWait());
            grid.add(btnEliminar, 1, 1);

            Scene scene = new Scene(grid, 600, 450);

            try {
                String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo cargar el CSS en VentanaMenu: " + e.getMessage());
            }

            setScene(scene);
            show();
        }
    }


    private class VentanaAgregar extends Stage {
        private static final String REGEX_MATRICULA = "\\d{7,8}"; 
        private static final String REGEX_NOMBRE = ".{3,}";      
        private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        private static final String REGEX_EDAD = "\\d{2}";        
        public VentanaAgregar(Stage ownerStage) {
            setTitle("Agregar Nuevo Estudiante");
            initOwner(ownerStage);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            
            VBox vbox = new VBox(15);
            vbox.setPadding(new Insets(20));
            vbox.setAlignment(Pos.CENTER);
            
            CampoSegobiaValidado txtMatricula = new CampoSegobiaValidado("Matrícula (ej. 2177528)", REGEX_MATRICULA);
            CampoSegobiaValidado txtNombre = new CampoSegobiaValidado("Nombre Completo", REGEX_NOMBRE);
            CampoSegobiaValidado txtEmail = new CampoSegobiaValidado("Email (opcional)", REGEX_EMAIL);
            CampoSegobiaValidado txtEdad = new CampoSegobiaValidado("Edad (opcional)", REGEX_EDAD);
            
            Boton14Estilizado btnGuardar = new Boton14Estilizado("GUARDAR ESTUDIANTE", TipoBoton.ACCION_GUARDAR);
            btnGuardar.setDefaultButton(true);
            
            btnGuardar.setOnAction(e -> {
                if (!txtMatricula.esValido(REGEX_MATRICULA)) {
                    mostrarAlertaAdvertencia("Error de Validación", "La matrícula no es válida. Formato esperado: 7 u 8 números.");
                    return;
                }
                if (!txtNombre.esValido(REGEX_NOMBRE)) {
                    mostrarAlertaAdvertencia("Error de Validación", "El nombre debe tener al menos 3 caracteres.");
                    return;
                }
                // Validamos el email solo si el usuario ha escrito algo en el campo
                if (!txtEmail.getText().isEmpty() && !txtEmail.esValido(REGEX_EMAIL)) {
                    mostrarAlertaAdvertencia("Error de Validación", "El formato del correo electrónico no es válido.");
                    return;
                }

                boolean matriculaExiste = listaEstudiantes.stream()
                    .anyMatch(est -> est.getMatricula().equals(txtMatricula.getText()));
                
                if (matriculaExiste) {
                    mostrarAlertaAdvertencia("Error", "La matrícula ya existe. Por favor, ingrese una diferente.");
                    return;
                }

                Estudiantes nuevoEstudiante = new Estudiantes(
                    txtMatricula.getText(),
                    txtNombre.getText(), 
                    txtEmail.getText(),
                    txtEdad.getText()
                );

                listaEstudiantes.add(nuevoEstudiante);
                mostrarAlertaInfo("Registro Exitoso", txtNombre.getText() + " ha sido registrado correctamente.");
                this.close(); // Cierra la ventana de agregar
            });

            vbox.getChildren().addAll(
                new Label("Ingrese los datos del nuevo estudiante:"),
                txtMatricula,
                txtNombre,
                txtEmail,
                txtEdad,
                btnGuardar
            );
            
            Scene scene = new Scene(vbox, 350, 300); 

            try {
                String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo cargar el CSS: " + e.getMessage());
            }
            
            setScene(scene);
        }
    }


    private class VentanaVer extends Stage {
        private static final String REGEX_MATRICULA = "\\d{7,8}"; 
        
        public VentanaVer(Stage ownerStage) {
            setTitle("Consultar Estudiantes Registrados");
            initOwner(ownerStage);
            initModality(Modality.APPLICATION_MODAL);

            TableView<Estudiantes> tabla = new TableView<>();
            
            TableColumn<Estudiantes, String> colMatricula = new TableColumn<>("Matrícula");
            colMatricula.setCellValueFactory(data -> data.getValue().matriculaProperty());

            TableColumn<Estudiantes, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
            
            TableColumn<Estudiantes, String> colEmail = new TableColumn<>("Email");
            colEmail.setCellValueFactory(data -> data.getValue().emailProperty());
            
            TableColumn<Estudiantes, String> colEdad = new TableColumn<>("Edad");
            colEdad.setCellValueFactory(data -> data.getValue().edadProperty());

            tabla.getColumns().addAll(colMatricula, colNombre, colEmail, colEdad);
            
            tabla.setItems(listaEstudiantes);

            CampoSegobiaValidado txtBusqueda = new CampoSegobiaValidado("Ingrese la matrícula a buscar...", REGEX_MATRICULA);
            txtBusqueda.setPrefWidth(250);

            Boton14Estilizado btnBuscar = new Boton14Estilizado("BUSCAR", TipoBoton.ACCION_BUSCAR);
            btnBuscar.setDefaultButton(true);
            Boton14Estilizado btnMostrarTodos = new Boton14Estilizado("MOSTRAR TODOS", TipoBoton.ACCION_ACTUALIZAR);

            HBox panelBusqueda = new HBox(10, new Label("Buscar por Matrícula:"), txtBusqueda, btnBuscar, btnMostrarTodos);
            panelBusqueda.setAlignment(Pos.CENTER_LEFT);
            panelBusqueda.setPadding(new Insets(0, 0, 10, 0));

            btnBuscar.setOnAction(e -> {
                String matriculaBuscada = txtBusqueda.getText().trim();

                if (!txtBusqueda.esValido(REGEX_MATRICULA)) {
                    mostrarAlertaAdvertencia("Campo Inválido", "Por favor, ingrese una matrícula con el formato de las matriculas de la UNI.");
                    return;
                }

                Optional<Estudiantes> estudianteEncontrado = listaEstudiantes.stream()
                    .filter(est -> est.getMatricula().equalsIgnoreCase(matriculaBuscada))
                    .findFirst();

                if (estudianteEncontrado.isPresent()) {
                    ObservableList<Estudiantes> resultadoBusqueda = FXCollections.observableArrayList(estudianteEncontrado.get());
                    tabla.setItems(resultadoBusqueda);
                } else {
                    mostrarAlertaInfo("No Encontrado", "No se encontró ningún estudiante con la matrícula: " + matriculaBuscada);
                    tabla.setItems(FXCollections.observableArrayList()); // Mostramos la tabla vacía
                }
            });

            btnMostrarTodos.setOnAction(e -> {
                tabla.setItems(listaEstudiantes); // Vuelve a mostrar todos
                txtBusqueda.clear();
                txtBusqueda.getStyleClass().removeAll("campo-invalido", "campo-valido");
            });

            VBox vbox = new VBox(15, panelBusqueda, tabla); 
            vbox.setPadding(new Insets(20));
            
            Scene scene = new Scene(vbox, 700, 500); 

            // Cargamos el CSS
            try {
                String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo cargar el CSS: " + e.getMessage());
            }
            
            setScene(scene);    }
}

    
    private class VentanaActualizar extends Stage {
        private static final String REGEX_MATRICULA = "\\d{7,8}"; 
        private static final String REGEX_NOMBRE = ".{3,}";
        private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        private static final String REGEX_EDAD = "\\d*";

        private Estudiantes estudianteAEditar = null;

        public VentanaActualizar(Stage ownerStage) {
            setTitle("Actualizar Datos del Estudiante");
            initOwner(ownerStage);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);

            VBox vbox = new VBox(15);
            vbox.setPadding(new Insets(20));
            vbox.setAlignment(Pos.TOP_CENTER);

            HBox panelBusqueda = new HBox(10);
            panelBusqueda.setAlignment(Pos.CENTER);
            CampoSegobiaValidado txtMatriculaBusqueda = new CampoSegobiaValidado("Matrícula a buscar...", REGEX_MATRICULA);
            
            Boton14Estilizado btnBuscar = new Boton14Estilizado("BUSCAR", TipoBoton.ACCION_BUSCAR);
            btnBuscar.setDefaultButton(true);
            panelBusqueda.getChildren().addAll(new Label("Matrícula:"), txtMatriculaBusqueda, btnBuscar);

            GridPane panelEdicion = new GridPane();
            panelEdicion.setHgap(10);
            panelEdicion.setVgap(10);
            panelEdicion.setVisible(false); 

            CampoSegobiaValidado txtNombre = new CampoSegobiaValidado("Nombre Completo", REGEX_NOMBRE);
            CampoSegobiaValidado txtEmail = new CampoSegobiaValidado("Email (opcional)", REGEX_EMAIL);
            CampoSegobiaValidado txtEdad = new CampoSegobiaValidado("Edad (opcional)", REGEX_EDAD);
            Boton14Estilizado btnGuardarCambios = new Boton14Estilizado("GUARDAR CAMBIOS", TipoBoton.ACCION_GUARDAR);
            btnGuardarCambios.setDefaultButton(false);

            panelEdicion.add(new Label("Nombre:"), 0, 0);
            panelEdicion.add(txtNombre, 1, 0);
            panelEdicion.add(new Label("Email:"), 0, 1);
            panelEdicion.add(txtEmail, 1, 1);
            panelEdicion.add(new Label("Edad:"), 0, 2);
            panelEdicion.add(txtEdad, 1, 2);
            panelEdicion.add(btnGuardarCambios, 1, 3);
            
            
            btnBuscar.setOnAction(e -> {
                String matricula = txtMatriculaBusqueda.getText().trim();
                if (matricula.isEmpty()) {
                    mostrarAlertaAdvertencia("Error", "Debe ingresar una matrícula.");
                    return;
                }

                Optional<Estudiantes> resultado = listaEstudiantes.stream()
                    .filter(est -> est.getMatricula().equalsIgnoreCase(matricula))
                    .findFirst();

                if (resultado.isPresent()) {
                    estudianteAEditar = resultado.get(); 
                    
                    txtNombre.setText(estudianteAEditar.getNombre());
                    txtEmail.setText(estudianteAEditar.getEmail());
                    txtEdad.setText(estudianteAEditar.getEdad());

                    panelBusqueda.setVisible(false);
                    panelEdicion.setVisible(true);
                    setTitle("Editando a: " + estudianteAEditar.getNombre());
                    btnBuscar.setDefaultButton(false);
                    btnGuardarCambios.setDefaultButton(true);

                } else {
                    mostrarAlertaAdvertencia("No Encontrado", "No existe un estudiante con esa matrícula.");
                    estudianteAEditar = null;
                }
            });

            btnGuardarCambios.setOnAction(e -> {
                if (!txtNombre.esValido(REGEX_NOMBRE)) {
                    mostrarAlertaAdvertencia("Error de Validación", "El nombre debe tener al menos 3 caracteres.");
                    return;
                }
                if (!txtEmail.getText().isEmpty() && !txtEmail.esValido(REGEX_EMAIL)) {
                    mostrarAlertaAdvertencia("Error de Validación", "El formato del correo electrónico no es válido.");
                    return;
                }
                
                estudianteAEditar.setNombre(txtNombre.getText());
                estudianteAEditar.setEmail(txtEmail.getText());
                estudianteAEditar.setEdad(txtEdad.getText());

                mostrarAlertaInfo("Actualizado", "Los datos del estudiante han sido actualizados correctamente.");
                this.close(); 
            });


            vbox.getChildren().addAll(panelBusqueda, panelEdicion);
            Scene scene = new Scene(vbox, 450, 300); 

            try {
                String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo cargar el CSS: " + e.getMessage());
            }
            
            setScene(scene);
        }
    }
    
    private class VentanaEliminar extends Stage {
        private static final String REGEX_MATRICULA = "\\d{7,8}"; 
        
        public VentanaEliminar(Stage ownerStage) {
            setTitle("Eliminar Estudiante");
            initOwner(ownerStage);
            initModality(Modality.APPLICATION_MODAL);
            setResizable(false);
            
            VBox vbox = new VBox(15);
            vbox.setPadding(new Insets(20));
            vbox.setAlignment(Pos.CENTER);


            CampoSegobiaValidado txtMatriculaEliminar = new CampoSegobiaValidado("Matrícula del Estudiante a Eliminar", REGEX_MATRICULA);
            Boton14Estilizado btnConfirmarEliminar = new Boton14Estilizado("ELIMINAR ESTUDIANTE", TipoBoton.ACCION_ELIMINAR);
            btnConfirmarEliminar.setDefaultButton(true);
            
            btnConfirmarEliminar.setOnAction(e -> {
            String matriculaAEliminar = txtMatriculaEliminar.getText().trim();
                
                if (!txtMatriculaEliminar.esValido(REGEX_MATRICULA)) {
                    mostrarAlertaAdvertencia("Error de Validación", "La matrícula no es válida. Formato esperado: 7 u 8 números.");
                    return;
                }

                Alert alertaConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
                alertaConfirmacion.setTitle("Confirmar Eliminación");
                alertaConfirmacion.setHeaderText("¿Está seguro de que desea eliminar al estudiante?");
                alertaConfirmacion.setContentText("Esta acción no se puede deshacer.\nMatrícula: " + matriculaAEliminar);

                Optional<ButtonType> resultado = alertaConfirmacion.showAndWait();

                if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                    
                    boolean eliminado = listaEstudiantes.removeIf(est -> est.getMatricula().equalsIgnoreCase(matriculaAEliminar));

                    if (eliminado) {
                        mostrarAlertaInfo("Éxito", "Estudiante con matrícula " + matriculaAEliminar + " eliminado.");
                        this.close();
                    } else {
                        mostrarAlertaAdvertencia("Error", "Matrícula no encontrada. No se eliminó ningún estudiante.");
                    }
                }
            });
            
            vbox.getChildren().addAll(
                new Label("Ingrese la matrícula para eliminar:"),
                txtMatriculaEliminar,
                btnConfirmarEliminar
            );

            Scene scene = new Scene(vbox, 350, 300); 

            try {
                String cssPath = getClass().getResource("/practica11/estilos_2177528.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo cargar el CSS: " + e.getMessage());
            }
            
            setScene(scene);
        }
    }
    

    private void mostrarAlertaInfo(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    private void mostrarAlertaAdvertencia(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

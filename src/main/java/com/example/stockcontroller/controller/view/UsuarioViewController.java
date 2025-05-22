package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class UsuarioViewController {

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> columnaNombre, columnaEmail, columnaRol;
    @FXML private TextField campoNombre, campoEmail, campoRol;
    @FXML private PasswordField campoContrasena;
    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        columnaEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));
        columnaRol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getRol()));
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoNombre.setText(newSel.getNombre());
                campoEmail.setText(newSel.getEmail());
                campoContrasena.setText(""); // nunca muestras la contraseña
                campoRol.setText(newSel.getRol());
            }
        });
        cargarUsuarios();
    }

    @FXML
    public void cargarUsuarios() {
        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/usuarios"))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                List<Usuario> usuarios = mapper.readValue(response.body(), new TypeReference<>() {});
                Platform.runLater(() -> tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios)));
            } catch (Exception e) {
                mostrarError("Error al cargar usuarios", e.getMessage());
            }
        }).start();
    }

    @FXML
    public void crearUsuario() {
        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombre(campoNombre.getText());
            nuevo.setEmail(campoEmail.getText());
            nuevo.setContrasena(campoContrasena.getText());
            nuevo.setRol(campoRol.getText());

            String json = mapper.writeValueAsString(nuevo);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/usuarios"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarUsuarios));
        } catch (Exception e) {
            mostrarError("Error al crear usuario", e.getMessage());
        }
    }

    @FXML
    public void actualizarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            seleccionado.setNombre(campoNombre.getText());
            seleccionado.setEmail(campoEmail.getText());
            seleccionado.setRol(campoRol.getText());
            if (!campoContrasena.getText().isEmpty())
                seleccionado.setContrasena(campoContrasena.getText());

            String json = mapper.writeValueAsString(seleccionado);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/usuarios/" + seleccionado.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarUsuarios));
        } catch (Exception e) {
            mostrarError("Error al actualizar usuario", e.getMessage());
        }
    }

    @FXML
    public void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/usuarios/" + seleccionado.getId()))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> Platform.runLater(this::cargarUsuarios));
    }

    private void mostrarError(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(titulo);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}

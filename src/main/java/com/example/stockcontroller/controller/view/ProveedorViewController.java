package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Proveedor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class ProveedorViewController {

    @FXML private TableView<Proveedor> tablaProveedores;
    @FXML private TableColumn<Proveedor, String> columnaNombre, columnaDireccion, columnaTelefono, columnaEmail;
    @FXML private TextField campoNombre, campoDireccion, campoTelefono, campoEmail;
    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        columnaDireccion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDireccion()));
        columnaTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));
        columnaEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        tablaProveedores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoNombre.setText(newSel.getNombre());
                campoDireccion.setText(newSel.getDireccion());
                campoTelefono.setText(newSel.getTelefono());
                campoEmail.setText(newSel.getEmail());
            }
        });
        cargarProveedores();
    }

    @FXML
    public void cargarProveedores() {
        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/proveedores"))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                List<Proveedor> proveedores = mapper.readValue(response.body(), new TypeReference<>() {});
                Platform.runLater(() -> tablaProveedores.setItems(FXCollections.observableArrayList(proveedores)));
            } catch (Exception e) {
                mostrarError("Error al cargar proveedores", e.getMessage());
            }
        }).start();
    }

    @FXML
    public void crearProveedor() {
        try {
            Proveedor nuevo = new Proveedor();
            nuevo.setNombre(campoNombre.getText());
            nuevo.setDireccion(campoDireccion.getText());
            nuevo.setTelefono(campoTelefono.getText());
            nuevo.setEmail(campoEmail.getText());

            String json = mapper.writeValueAsString(nuevo);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/proveedores"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarProveedores));
        } catch (Exception e) {
            mostrarError("Error al crear proveedor", e.getMessage());
        }
    }

    @FXML
    public void actualizarProveedor() {
        Proveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            seleccionado.setNombre(campoNombre.getText());
            seleccionado.setDireccion(campoDireccion.getText());
            seleccionado.setTelefono(campoTelefono.getText());
            seleccionado.setEmail(campoEmail.getText());

            String json = mapper.writeValueAsString(seleccionado);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/proveedores/" + seleccionado.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarProveedores));
        } catch (Exception e) {
            mostrarError("Error al actualizar proveedor", e.getMessage());
        }
    }

    @FXML
    public void eliminarProveedor() {
        Proveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/proveedores/" + seleccionado.getId()))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> Platform.runLater(this::cargarProveedores));
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

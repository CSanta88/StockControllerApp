package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Pedido;
import com.example.stockcontroller.frontmodel.Proveedor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class PedidoViewController {

    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, String> columnaEstado;
    @FXML private TableColumn<Pedido, String> columnaProveedor;
    @FXML private TableColumn<Pedido, String> columnaFecha;
    @FXML private TextField campoEstado;
    @FXML private DatePicker campoFecha;
    @FXML private ComboBox<Proveedor> comboProveedor;
    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        columnaEstado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEstado()));
        columnaProveedor.setCellValueFactory(data -> {
            Proveedor p = data.getValue().getProveedor();
            return new javafx.beans.property.SimpleStringProperty(p != null ? p.getNombre() : "");
        });
        columnaFecha.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFecha() != null ? data.getValue().getFecha().toString() : ""));
        cargarPedidos();
        cargarProveedores();
    }

    @FXML
    public void cargarPedidos() {
        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/pedidos"))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                List<Pedido> pedidos = mapper.readValue(response.body(), new TypeReference<>() {});
                Platform.runLater(() -> tablaPedidos.setItems(FXCollections.observableArrayList(pedidos)));
            } catch (Exception e) {
                mostrarError("Error al cargar pedidos", e.getMessage());
            }
        }).start();
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
                Platform.runLater(() -> comboProveedor.setItems(FXCollections.observableArrayList(proveedores)));
            } catch (Exception e) {
                mostrarError("Error al cargar proveedores", e.getMessage());
            }
        }).start();
    }

    @FXML
    public void crearPedido() {
        try {
            Pedido nuevo = new Pedido();
            nuevo.setEstado(campoEstado.getText());
            nuevo.setFecha(campoFecha.getValue() != null ? campoFecha.getValue() : LocalDate.now());
            nuevo.setProveedor(comboProveedor.getValue());

            String json = mapper.writeValueAsString(nuevo);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/pedidos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarPedidos));
        } catch (Exception e) {
            mostrarError("Error al crear pedido", e.getMessage());
        }
    }

    @FXML
    public void actualizarPedido() {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;
        try {
            seleccionado.setEstado(campoEstado.getText());
            seleccionado.setFecha(campoFecha.getValue());
            seleccionado.setProveedor(comboProveedor.getValue());

            String json = mapper.writeValueAsString(seleccionado);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/pedidos/" + seleccionado.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarPedidos));
        } catch (Exception e) {
            mostrarError("Error al actualizar pedido", e.getMessage());
        }
    }

    @FXML
    public void eliminarPedido() {
        Pedido seleccionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/pedidos/" + seleccionado.getId()))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> Platform.runLater(this::cargarPedidos));
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

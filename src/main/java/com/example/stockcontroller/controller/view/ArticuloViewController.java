package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Articulo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ArticuloViewController {

    @FXML private TableView<Articulo> tablaArticulos;
    @FXML private TableColumn<Articulo, String> columnaNombre;
    @FXML private TableColumn<Articulo, String> columnaDescripcion;
    @FXML private TableColumn<Articulo, BigDecimal> columnaPrecio;
    @FXML private TableColumn<Articulo, Integer> columnaStock;
    @FXML private TextField campoNombre, campoDescripcion, campoPrecio, campoStock;
    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        columnaDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        columnaPrecio.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrecio()));
        columnaStock.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getStock()));
        cargarArticulos();
    }

    @FXML
    public void cargarArticulos() {
        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/articulos"))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                List<Articulo> articulos = mapper.readValue(response.body(), new TypeReference<>() {});
                Platform.runLater(() -> tablaArticulos.setItems(FXCollections.observableArrayList(articulos)));
            } catch (Exception e) {
                mostrarError("Error al cargar artículos", e.getMessage());
            }
        }).start();
    }

    @FXML
    public void crearArticulo() {
        try {
            Articulo nuevo = new Articulo();
            nuevo.setNombre(campoNombre.getText());
            nuevo.setDescripcion(campoDescripcion.getText());
            nuevo.setPrecio(new BigDecimal(campoPrecio.getText()));
            nuevo.setStock(Integer.parseInt(campoStock.getText()));

            String json = mapper.writeValueAsString(nuevo);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/articulos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarArticulos));
        } catch (Exception e) {
            mostrarError("Error al crear artículo", e.getMessage());
        }
    }

    @FXML
    public void actualizarArticulo() {
        Articulo seleccionado = tablaArticulos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        try {
            seleccionado.setNombre(campoNombre.getText());
            seleccionado.setDescripcion(campoDescripcion.getText());
            seleccionado.setPrecio(new BigDecimal(campoPrecio.getText()));
            seleccionado.setStock(Integer.parseInt(campoStock.getText()));

            String json = mapper.writeValueAsString(seleccionado);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/articulos/" + seleccionado.getId()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(this::cargarArticulos));
        } catch (Exception e) {
            mostrarError("Error al actualizar artículo", e.getMessage());
        }
    }

    @FXML
    public void eliminarArticulo() {
        Articulo seleccionado = tablaArticulos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/articulos/" + seleccionado.getId()))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> Platform.runLater(this::cargarArticulos));
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

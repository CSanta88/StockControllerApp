package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.DTOFront.ArticuloDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class ArticuloViewController {

    @FXML private TableView<ArticuloDTO> tablaArticulos;
    @FXML private TableColumn<ArticuloDTO, String> columnaNombre;
    @FXML private TableColumn<ArticuloDTO, String> columnaDescripcion;
    @FXML private TableColumn<ArticuloDTO, Number> columnaPrecio;
    @FXML private TableColumn<ArticuloDTO, Number> columnaStock;
    @FXML private TableColumn<ArticuloDTO, Number> columnaStockMinimo;

    @FXML private TextField campoNombre;
    @FXML private TextField campoDescripcion;
    @FXML private TextField campoPrecio;
    @FXML private TextField campoStock;
    @FXML private TextField campoStockMinimo;

    @FXML private Button btnVolver;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ArticuloDTO articuloSeleccionado = null;

    private final String API_URL = "http://localhost:8080/api/articulos";

    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNombre()));
        columnaDescripcion.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getDescripcion()));
        columnaPrecio.setCellValueFactory(cell -> new ReadOnlyDoubleWrapper(cell.getValue().getPrecio()));
        columnaStock.setCellValueFactory(cell -> new ReadOnlyIntegerWrapper(cell.getValue().getStock()));
        columnaStockMinimo.setCellValueFactory(cell -> new ReadOnlyIntegerWrapper(cell.getValue().getStockMinimo()));

        tablaArticulos.setOnMouseClicked(this::seleccionarArticulo);
        cargarArticulos();
    }

    private void cargarArticulos() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            List<ArticuloDTO> articulos = objectMapper.readValue(response.body(), new TypeReference<>() {});
            tablaArticulos.setItems(FXCollections.observableArrayList(articulos));
        } catch (Exception e) {
            mostrarError("Error al cargar artículos", e.getMessage());
        }
    }

    @FXML
    private void crearArticulo() {
        guardarArticulo(null);
    }

    @FXML
    private void actualizarArticulo() {
        if (articuloSeleccionado == null) {
            mostrarError("Selecciona un artículo", "Debes seleccionar un artículo para actualizar.");
            return;
        }
        guardarArticulo(articuloSeleccionado.getId());
    }

    private void guardarArticulo(Long id) {
        if (!validarCampos()) return;

        ArticuloDTO dto = new ArticuloDTO();
        dto.setNombre(campoNombre.getText());
        dto.setDescripcion(campoDescripcion.getText());
        dto.setPrecio(Double.parseDouble(campoPrecio.getText()));
        dto.setStock(Integer.parseInt(campoStock.getText()));
        dto.setStockMinimo(Integer.parseInt(campoStockMinimo.getText()));
        if (id != null) dto.setId(id);

        try {
            HttpRequest request;
            String body = objectMapper.writeValueAsString(dto);

            if (id == null) {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();
                mostrarInfo("Artículo creado", "El artículo se ha guardado correctamente.");
            } else {
                request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + "/" + id))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(body))
                        .build();
                mostrarInfo("Artículo actualizado", "Cambios guardados correctamente.");
            }

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            limpiarFormulario();
            cargarArticulos();

        } catch (Exception e) {
            mostrarError("Error al guardar artículo", e.getMessage());
        }
    }

    @FXML
    private void eliminarArticulo() {
        if (articuloSeleccionado == null) {
            mostrarError("Selecciona un artículo", "Debes seleccionar un artículo para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Estás seguro de que quieres eliminar este artículo?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + "/" + articuloSeleccionado.getId()))
                        .DELETE()
                        .build();

                httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                mostrarInfo("Artículo eliminado", "El artículo ha sido eliminado correctamente.");
                limpiarFormulario();
                cargarArticulos();
            } catch (Exception e) {
                mostrarError("Error al eliminar artículo", e.getMessage());
            }
        }
    }

    private void seleccionarArticulo(MouseEvent event) {
        ArticuloDTO seleccionado = tablaArticulos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            articuloSeleccionado = seleccionado;
            campoNombre.setText(seleccionado.getNombre());
            campoDescripcion.setText(seleccionado.getDescripcion());
            campoPrecio.setText(String.valueOf(seleccionado.getPrecio()));
            campoStock.setText(String.valueOf(seleccionado.getStock()));
            campoStockMinimo.setText(String.valueOf(seleccionado.getStockMinimo()));
        }
    }

    private void limpiarFormulario() {
        campoNombre.clear();
        campoDescripcion.clear();
        campoPrecio.clear();
        campoStock.clear();
        campoStockMinimo.clear();
        articuloSeleccionado = null;
        tablaArticulos.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (campoNombre.getText().trim().isEmpty()) errores.append("- El nombre es obligatorio.\n");
        if (campoDescripcion.getText().trim().isEmpty()) errores.append("- La descripción es obligatoria.\n");

        try {
            double precio = Double.parseDouble(campoPrecio.getText());
            if (precio < 0 || precio > 99999999.99)
                errores.append("- El precio debe estar entre 0 y 99.999.999,99.\n");
        } catch (NumberFormatException e) {
            errores.append("- El precio debe ser un número válido.\n");
        }

        try {
            int stock = Integer.parseInt(campoStock.getText());
            if (stock < 0 || stock > 999999999)
                errores.append("- El stock debe ser un número entre 0 y 999.999.999.\n");
        } catch (NumberFormatException e) {
            errores.append("- El stock debe ser un número entero.\n");
        }

        try {
            int minimo = Integer.parseInt(campoStockMinimo.getText());
            if (minimo < 0 || minimo > 999999999)
                errores.append("- El stock mínimo debe estar entre 0 y 999.999.999.\n");
        } catch (NumberFormatException e) {
            errores.append("- El stock mínimo debe ser un número entero.\n");
        }

        if (!errores.isEmpty()) {
            mostrarError("Campos inválidos", errores.toString());
            return false;
        }

        return true;
    }

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void volverAlDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Panel Principal");
        } catch (Exception e) {
            mostrarError("Error", "No se pudo volver al panel principal.");
        }
    }
}


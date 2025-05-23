package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Proveedor;
import com.example.stockcontroller.model.Articulo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URI;
import java.net.http.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Controlador de la vista de gestión de proveedores.
 * Permite crear, actualizar, eliminar, listar y buscar proveedores.
 */
public class ProveedorViewController {

    @FXML private TableView<Proveedor> tablaProveedores;
    @FXML private TableColumn<Proveedor, String> columnaNombre, columnaDireccion, columnaTelefono, columnaEmail;
    @FXML private TextField campoNombre, campoDireccion, campoTelefono, campoEmail, campoBusqueda;
    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();

    /**
     * Inicializa la vista, configura las columnas y carga los proveedores.
     */
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

    /**
     * Carga la lista de proveedores desde el backend y actualiza la tabla.
     */
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
                Platform.runLater(() -> {
                    listaProveedores.setAll(proveedores);
                    tablaProveedores.setItems(listaProveedores);
                });
            } catch (Exception e) {
                mostrarError("Error al cargar proveedores", e.getMessage());
            }
        }).start();
    }

    /**
     * Crea un nuevo proveedor con los datos introducidos.
     */
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
                    .thenAccept(response -> Platform.runLater(() -> {
                        cargarProveedores();
                        limpiarCampos();
                    }));
        } catch (Exception e) {
            mostrarError("Error al crear proveedor", e.getMessage());
        }
    }

    /**
     * Actualiza el proveedor seleccionado con los nuevos datos.
     */
    @FXML
    public void actualizarProveedor() {
        Proveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un proveedor", "Debes seleccionar un proveedor para actualizar.");
            return;
        }
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
                    .thenAccept(response -> Platform.runLater(() -> {
                        cargarProveedores();
                        limpiarCampos();
                    }));
        } catch (Exception e) {
            mostrarError("Error al actualizar proveedor", e.getMessage());
        }
    }

    /**
     * Elimina el proveedor seleccionado de la base de datos.
     */
    @FXML
    public void eliminarProveedor() {
        Proveedor seleccionado = tablaProveedores.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarError("Selecciona un proveedor", "Debes seleccionar un proveedor para eliminar.");
            return;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/proveedores/" + seleccionado.getId()))
                .DELETE()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> Platform.runLater(() -> {
                    cargarProveedores();
                    limpiarCampos();
                }));
    }

    /**
     * Busca proveedores por nombre y actualiza la tabla mostrando solo los que coincidan.
     * La búsqueda se hace en la lista cargada (no en el backend).
     */
    @FXML
    private void buscarPorNombre() {
        String textoBusqueda = campoBusqueda.getText();
        if (textoBusqueda == null || textoBusqueda.isBlank()) {
            tablaProveedores.setItems(listaProveedores);
            return;
        }
        String texto = textoBusqueda.trim().toLowerCase();
        List<Proveedor> filtrados = listaProveedores.stream()
                .filter(prov -> prov.getNombre() != null && prov.getNombre().toLowerCase().contains(texto))
                .collect(Collectors.toList());
        tablaProveedores.setItems(FXCollections.observableArrayList(filtrados));
    }
    /**
     * Navega de vuelta al dashboard.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void volverAlDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/estilos.css")).toExternalForm());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            mostrarError("No se pudo volver al dashboard.", e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * Limpia los campos de entrada del formulario.
     */
    private void limpiarCampos() {
        campoNombre.clear();
        campoDireccion.clear();
        campoTelefono.clear();
        campoEmail.clear();
        tablaProveedores.getSelectionModel().clearSelection();
    }

    /**
     * Muestra una alerta de error en pantalla.
     * @param titulo Título del error.
     * @param mensaje Mensaje detallado del error.
     */
    private void mostrarError(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(titulo);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
    public static class ArticuloProveedorRow {
        private final Articulo articulo;
        private final BooleanProperty asignado = new SimpleBooleanProperty(false);
        private final DoubleProperty precio = new SimpleDoubleProperty();

        public ArticuloProveedorRow(Articulo articulo, boolean asignado, double precio) {
            this.articulo = articulo;
            this.asignado.set(asignado);
            this.precio.set(precio);
        }
        public Articulo getArticulo() { return articulo; }
        public BooleanProperty asignadoProperty() { return asignado; }
        public boolean isAsignado() { return asignado.get(); }
        public void setAsignado(boolean value) { asignado.set(value); }
        public DoubleProperty precioProperty() { return precio; }
        public double getPrecio() { return precio.get(); }
        public void setPrecio(double value) { precio.set(value); }
        @Override
        public String toString() {
            return articulo.getNombre() + " (" + (isAsignado() ? "✓" : "✗") + ") - " + getPrecio() + "€";
        }
    }
}

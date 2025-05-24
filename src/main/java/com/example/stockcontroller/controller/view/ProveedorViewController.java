package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.frontmodel.Proveedor;
import com.example.stockcontroller.model.Articulo;
import com.example.stockcontroller.frontmodel.DTOFront.ProveedorArticuloResponseDTO;
import com.example.stockcontroller.frontmodel.DTOFront.ProveedorArticuloRequestDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URI;
import java.net.http.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProveedorViewController {

    @FXML private TableView<Proveedor> tablaProveedores;
    @FXML private TableColumn<Proveedor, String> columnaNombre, columnaDireccion, columnaTelefono, columnaEmail;
    @FXML private TextField campoNombre, campoDireccion, campoTelefono, campoEmail, campoBusqueda;

    @FXML private TableView<ArticuloProveedorRow> tablaArticulosProveedor;
    @FXML private TableColumn<ArticuloProveedorRow, Boolean> colSeleccion;
    @FXML private TableColumn<ArticuloProveedorRow, String> colArticulo;
    @FXML private TableColumn<ArticuloProveedorRow, Double> colPrecio;

    @FXML private Button btnCrear, btnActualizar, btnEliminar, btnVolver;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    private final ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();
    private final ObservableList<ArticuloProveedorRow> listaArticulosProveedor = FXCollections.observableArrayList();

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
            return articulo.getNombre() + " (" + (isAsignado() ? "\u2713" : "\u2717") + ") - " + getPrecio() + "€";
        }
    }

    // ... (initialize, cargarProveedores, cargarTodosArticulos, cargarArticulosProveedor, guardarArticulosProveedor)
    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        columnaDireccion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDireccion()));
        columnaTelefono.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTelefono()));
        columnaEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        tablaProveedores.setItems(listaProveedores);

        if (tablaArticulosProveedor != null) {
            colSeleccion.setCellValueFactory(cellData -> cellData.getValue().asignadoProperty());
            colSeleccion.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleccion));
            colArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getNombre()));
            colPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asObject());
            colPrecio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            tablaArticulosProveedor.setItems(listaArticulosProveedor);
            tablaArticulosProveedor.setEditable(true);
        }

        tablaProveedores.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoNombre.setText(newSel.getNombre());
                campoDireccion.setText(newSel.getDireccion());
                campoTelefono.setText(newSel.getTelefono());
                campoEmail.setText(newSel.getEmail());
                cargarArticulosProveedor(newSel.getId());
            } else {
                limpiarCampos();
            }
        });

        cargarProveedores();
        cargarTodosArticulos();
    }
    public void cargarTodosArticulos() {
        new Thread(() -> {
            try {
                HttpRequest reqArticulos = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/articulos"))
                        .GET()
                        .build();
                HttpResponse<String> respArticulos = client.send(reqArticulos, HttpResponse.BodyHandlers.ofString());
                List<Articulo> todosArticulos = mapper.readValue(respArticulos.body(), new TypeReference<>() {});

                System.out.println("📦 Artículos cargados en modo 'todos':");
                todosArticulos.forEach(a -> System.out.println("ID: " + a.getId() + ", Nombre: " + a.getNombre()));

                Platform.runLater(() -> {
                    listaArticulosProveedor.clear();
                    for (Articulo articulo : todosArticulos) {
                        listaArticulosProveedor.add(new ArticuloProveedorRow(articulo, false, 0.0));
                    }
                });
            } catch (Exception e) {
                mostrarError("Error al cargar artículos", e.getMessage());
                e.printStackTrace();
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
                Platform.runLater(() -> {
                    listaProveedores.setAll(proveedores);
                    tablaProveedores.setItems(listaProveedores);
                });
            } catch (Exception e) {
                mostrarError("Error al cargar proveedores", e.getMessage());
            }
        }).start();
    }
    public void cargarArticulosProveedor(Long proveedorId) {
        new Thread(() -> {
            try {
                // 1. Obtener todos los artículos (catálogo)
                HttpRequest reqArticulos = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/articulos"))
                        .GET()
                        .build();
                HttpResponse<String> respArticulos = client.send(reqArticulos, HttpResponse.BodyHandlers.ofString());
                List<Articulo> todosArticulos = mapper.readValue(respArticulos.body(), new TypeReference<>() {});

                System.out.println("📦 Todos los artículos:");
                todosArticulos.forEach(a -> System.out.println("ID: " + a.getId() + ", Nombre: " + a.getNombre()));

                // 2. Obtener artículos asignados al proveedor
                HttpRequest reqPA = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/proveedores/" + proveedorId + "/articulos"))
                        .GET()
                        .build();
                HttpResponse<String> respPA = client.send(reqPA, HttpResponse.BodyHandlers.ofString());
                List<ProveedorArticuloResponseDTO> relaciones = mapper.readValue(respPA.body(), new TypeReference<>() {});

                System.out.println("🔗 Artículos asignados al proveedor con ID " + proveedorId + ":");
                relaciones.forEach(r -> {
                    if (r != null && r.getArticulo() != null) {
                        System.out.println(
                                "Articulo ID: " + r.getArticulo().getId() +
                                        ", Nombre: " + r.getArticulo().getNombre() +
                                        ", Precio: " + r.getPrecioCompra()
                        );
                    }
                });

                // 3. Actualizar la tabla en el hilo principal, con null checks robustos
                Platform.runLater(() -> {
                    listaArticulosProveedor.clear();
                    for (Articulo articulo : todosArticulos) {
                        ProveedorArticuloResponseDTO rel = relaciones.stream()
                                .filter(pa ->
                                        pa != null &&
                                                pa.getArticulo() != null &&
                                                pa.getArticulo().getId() != null &&
                                                articulo.getId() != null &&
                                                pa.getArticulo().getId().equals(articulo.getId())
                                )
                                .findFirst()
                                .orElse(null);

                        boolean asignado = rel != null;
                        double precio = (rel != null && rel.getPrecioCompra() != null) ? rel.getPrecioCompra() : 0.0;
                        listaArticulosProveedor.add(new ArticuloProveedorRow(articulo, asignado, precio));
                    }
                });
            } catch (Exception e) {
                mostrarError("Error al cargar artículos del proveedor", e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }





    public void guardarArticulosProveedor(Long proveedorId) {
        List<ProveedorArticuloRequestDTO> relaciones = listaArticulosProveedor.stream()
                .filter(ArticuloProveedorRow::isAsignado)
                .map(row -> new ProveedorArticuloRequestDTO(row.getArticulo().getId(), row.getPrecio()))
                .collect(Collectors.toList());
        try {
            String json = mapper.writeValueAsString(relaciones);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/proveedores/" + proveedorId + "/articulos"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> Platform.runLater(() ->
                            mostrarInfo("Éxito", "Artículos asignados correctamente.")
                    ));
        } catch (Exception e) {
            mostrarError("Error al asignar artículos", e.getMessage());
        }
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
                    .thenAccept(response -> {
                        try {
                            Proveedor proveedorCreado = mapper.readValue(response.body(), Proveedor.class);
                            guardarArticulosProveedor(proveedorCreado.getId());
                        } catch (Exception ex) {
                            mostrarError("Error al procesar proveedor creado", ex.getMessage());
                        }
                        Platform.runLater(() -> {
                            cargarProveedores();
                            limpiarCampos();
                        });
                    });
        } catch (Exception e) {
            mostrarError("Error al crear proveedor", e.getMessage());
        }
    }

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
                    .thenAccept(response -> {
                        guardarArticulosProveedor(seleccionado.getId());
                        Platform.runLater(() -> {
                            cargarProveedores();
                            limpiarCampos();
                        });
                    });
        } catch (Exception e) {
            mostrarError("Error al actualizar proveedor", e.getMessage());
        }
    }

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

    @FXML
    private void limpiarCampos() {
        campoNombre.clear();
        campoDireccion.clear();
        campoTelefono.clear();
        campoEmail.clear();
        tablaProveedores.getSelectionModel().clearSelection();
        if (listaArticulosProveedor != null) cargarTodosArticulos();
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

    private void mostrarInfo(String titulo, String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }
}

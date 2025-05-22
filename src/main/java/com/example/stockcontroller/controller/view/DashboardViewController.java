package com.example.stockcontroller.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DashboardViewController {

    @FXML private Button btnArticulos;
    @FXML private Button btnProveedores;
    @FXML private Button btnPedidos;
    @FXML private Button btnUsuarios;
    @FXML private Button btnInformes;
    @FXML private Button btnLogout;

    @FXML
    public void initialize() {
        btnArticulos.setOnAction(e -> cargarVista("/fxml/articulo.fxml", "Gestión de Artículos"));
        btnProveedores.setOnAction(e -> cargarVista("/fxml/proveedor.fxml", "Gestión de Proveedores"));
        btnPedidos.setOnAction(e -> cargarVista("/fxml/pedido.fxml", "Gestión de Pedidos"));
        btnUsuarios.setOnAction(e -> cargarVista("/fxml/usuario.fxml", "Gestión de Usuarios"));
        btnInformes.setOnAction(e -> cargarVista("/fxml/informe.fxml", "Generar Informes"));
        btnLogout.setOnAction(e -> cerrarSesion());
    }

    private void cargarVista(String rutaFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();

            Stage stage = (Stage) btnArticulos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);

        } catch (Exception e) {
            mostrarError("Error", "No se pudo cargar la vista: " + titulo);
        }
    }

    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Stock Controller - Login");
        } catch (Exception e) {
            mostrarError("Error", "No se pudo volver a la pantalla de login.");
        }
    }

    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

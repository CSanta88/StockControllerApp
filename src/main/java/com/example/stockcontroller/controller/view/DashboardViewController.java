package com.example.stockcontroller.controller.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador para la vista principal del dashboard de la aplicación.
 * Gestiona la navegación a las diferentes secciones del sistema de gestión de stock.
 */
public class DashboardViewController {

    /**
     * Navega a la vista de gestión de artículos.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handleArticulos(ActionEvent event) {
        cargarVista("/fxml/articulo.fxml", "Gestión de Artículos", event);
    }

    /**
     * Navega a la vista de gestión de proveedores.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handleProveedores(ActionEvent event) {
        cargarVista("/fxml/proveedor.fxml", "Gestión de Proveedores", event);
    }

    /**
     * Navega a la vista de gestión de pedidos.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handlePedidos(ActionEvent event) {
        cargarVista("/fxml/pedido.fxml", "Gestión de Pedidos", event);
    }

    /**
     * Navega a la vista de gestión de usuarios.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handleUsuarios(ActionEvent event) {
        cargarVista("/fxml/usuario.fxml", "Gestión de Usuarios", event);
    }

    /**
     * Navega a la vista de generación de informes.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handleInformes(ActionEvent event) {
        cargarVista("/fxml/informe.fxml", "Generar Informes", event);
    }

    /**
     * Cierra la sesión actual y vuelve a la pantalla de login.
     * @param event Evento de acción generado por el botón correspondiente.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        cargarVista("/fxml/login.fxml", "Stock Controller - Login", event);
    }

    /**
     * Cambia la escena actual a la vista especificada.
     * @param rutaFXML Ruta al archivo FXML de la nueva vista.
     * @param titulo Título de la ventana para la nueva vista.
     * @param event Evento de acción utilizado para obtener la ventana actual.
     */
    private void cargarVista(String rutaFXML, String titulo, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(titulo);
        } catch (Exception e) {
            mostrarError("Error", "No se pudo cargar la vista: " + titulo);
            e.printStackTrace();
        }
    }

    /**
     * Muestra una alerta de error en pantalla.
     * @param header Título del mensaje de error.
     * @param content Descripción detallada del error.
     */
    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

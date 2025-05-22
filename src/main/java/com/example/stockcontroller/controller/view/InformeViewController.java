package com.example.stockcontroller.controller.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InformeViewController {

    private static final Logger LOGGER = Logger.getLogger(InformeViewController.class.getName());

    @FXML private Button btnVolver;
    @FXML private ComboBox<String> comboTipoInforme;
    @FXML private Label labelEstado;

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        comboTipoInforme.getItems().addAll("Artículos", "Proveedores", "Pedidos", "Usuarios");
        btnVolver.setOnAction(e -> volverAlDashboard());
    }

    private void volverAlDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Panel Principal - Stock Controller");
        } catch (Exception e) {
            mostrarError("Error", "No se pudo volver al Dashboard");
        }
    }

    @FXML
    public void generarInforme() {
        String tipo = comboTipoInforme.getValue();
        if (tipo == null) {
            mostrarError("Seleccione un tipo de informe", "");
            return;
        }
        // Puedes mapear el tipo a un endpoint específico, ejemplo:
        String endpoint = "/api/informes/" + tipo.toLowerCase();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar informe");
        fileChooser.setInitialFileName("informe_" + tipo.toLowerCase() + ".csv");
        File file = fileChooser.showSaveDialog(btnVolver.getScene().getWindow());
        if (file == null) return;

        new Thread(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080" + endpoint))
                        .GET()
                        .build();

                HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                Files.write(file.toPath(), response.body());

                Platform.runLater(() -> labelEstado.setText("Informe generado correctamente."));
            } catch (Exception e) {
                Platform.runLater(() -> labelEstado.setText("Error al generar informe."));
                LOGGER.log(Level.SEVERE, "Error al generar informe", e);
            }
        }).start();
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

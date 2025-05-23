package com.example.stockcontroller.controller.view;

import com.example.stockcontroller.model.Usuario;
import com.example.stockcontroller.repository.UsuarioRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

public class CrearUsuarioViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtContrasena;
    @FXML private TextField txtRol;
    @FXML private Label lblMensaje;

    // Usa tu bean de Spring Boot
    private UsuarioRepository usuarioRepo = com.example.stockcontroller.MainApp.getSpringContext().getBean(UsuarioRepository.class);

    @FXML
    private void handleRegistrar() {
        try {
            Usuario usuario = new Usuario(
                    txtNombre.getText(),
                    txtEmail.getText(),
                    txtContrasena.getText(),
                    txtRol.getText()
            );
            usuarioRepo.save(usuario);
            lblMensaje.setText("¡Usuario creado correctamente! Ahora inicia sesión.");
        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVolverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            lblMensaje.setText("No se pudo volver al login.");
            e.printStackTrace();
        }
    }
}

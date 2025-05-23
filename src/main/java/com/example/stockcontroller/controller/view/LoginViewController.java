package com.example.stockcontroller.controller.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginViewController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button btnCrearUsuario;

    @FXML
    public void initialize() {
        btnLogin.setOnAction(e -> autenticarUsuario());
    }

    @FXML
    private void handleCrearUsuario(ActionEvent event) {
        try {
            System.out.println(getClass().getResource("/fxml/crear_usuario.fxml")); // debug
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/crear_usuario.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            mostrarError("Error", "No se pudo abrir la pantalla de registro.");
            e.printStackTrace();
        }
    }

    private void autenticarUsuario() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (!validarEmail(email)) {
            mostrarError("Email inválido", "Introduce un correo electrónico válido.");
            return;
        }
        if (!validarPassword(password)) {
            mostrarError("Contraseña inválida", "La contraseña debe tener al menos 8 caracteres, incluyendo letras y números.");
            return;
        }

        boolean autenticado = autenticarConBackend(email, password);
        if (autenticado) {
            cargarDashboard();
            cerrarVentanaActual();
        } else {
            mostrarError("Login incorrecto", "Usuario o contraseña inválidos.");
        }
    }

    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(regex);
    }

    private boolean validarPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        return password != null && password.matches(regex);
    }

    private boolean autenticarConBackend(String email, String password) {
        try {
            URL url = new URL("http://localhost:8080/api/usuarios/autenticar");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            String jsonInputString = String.format("{\"email\":\"%s\", \"contrasena\":\"%s\"}", email, password);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            return code == 200;
        } catch (Exception e) {
            mostrarError("Error de conexión", "No se pudo conectar con el servidor.");
        }
        return false;
    }

    private void cargarDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Panel Principal - Stock Controller");
            stage.show();
        } catch (Exception e) {
            mostrarError("Error", "No se pudo cargar el Dashboard.");
            e.printStackTrace();
        }
    }

    private void cerrarVentanaActual() {
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

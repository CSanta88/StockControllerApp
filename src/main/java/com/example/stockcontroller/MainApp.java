package com.example.stockcontroller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {

    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    // Guarda el contexto de Spring Boot por si necesitas cerrarlo después
    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        LOGGER.info("Iniciando aplicación Stock Controller...");

        // Arrancar Spring Boot en un hilo separado y guardar el contexto
        Thread springThread = new Thread(() -> {
            try {
                LOGGER.info("Levantando backend Spring Boot...");
                springContext = SpringApplication.run(StockControllerApplication.class);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al iniciar Spring Boot", e);
                Platform.exit();
            }
        });
        springThread.setDaemon(true);
        springThread.start();

        // Espera breve para dar tiempo al backend a arrancar (puedes ajustar el tiempo si lo necesitas)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.warning("Interrupción durante la espera de arranque");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            LOGGER.info("Cargando pantalla de Login...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Stock Controller - Login");
            primaryStage.show();
            LOGGER.info("Aplicación lanzada correctamente.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar la pantalla de Login", e);
            Platform.exit();
        }
    }

    @Override
    public void stop() {
        LOGGER.info("Cerrando aplicación...");
        if (springContext != null) {
            springContext.close();
        }
    }

    public static void main(String[] args) {
        LOGGER.info("Lanzando JavaFX...");
        launch(args);
    }

    // Si necesitas acceder al contexto de Spring desde otras clases (ej: para servicios backend)
    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}

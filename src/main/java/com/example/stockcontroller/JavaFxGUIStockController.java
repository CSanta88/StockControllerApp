package com.example.stockcontroller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxGUIStockController extends Application {

    private static ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        // Inicia el contexto Spring
        springContext = new SpringApplicationBuilder(StockControllerApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));

        // Inyecta controladores usando el contexto de Spring
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root = fxmlLoader.load();

        primaryStage.setTitle("Gestión de Artículos");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.model.Cashier;
import org.example.model.Game;
import org.example.repository.CashierDBRepository;
import org.example.repository.GameDBRepository;
import org.example.repository.TicketDBRepository;
import org.example.service.CashierService;
import org.example.service.GameService;
import org.example.service.TicketService;
import org.example.utils.AppConfig;
import org.example.utils.JdbcUtils;
import org.example.utils.SpringFXMLLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Component
public class Main extends Application{
    private static ApplicationContext springContext;

    @Override
    public void init() {
        springContext = new AnnotationConfigApplicationContext(AppConfig.class);
//        springContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        SpringFXMLLoader loader = springContext.getBean(SpringFXMLLoader.class);
//        loader.setContext(springContext);
    }

    @Override
    public void start(Stage primaryStage) {
        SpringFXMLLoader loader = springContext.getBean(SpringFXMLLoader.class);
//        Cashier cashier = new Cashier(0, "numele", "david", "parola");
//        Game game = new Game(0, "Steaua", "Ajax", 10, 0, "Champtions League", 100, "Finals", 100.99F);
//        GameService gameService = springContext.getBean(GameService.class);
//        CashierService cashierService = springContext.getBean(CashierService.class);
//
//        cashierService.saveCashier(cashier);
//        gameService.saveGame(game);
        try {
            Parent root = loader.load("/xmlFiles/login.fxml");

            Scene scene = new Scene(root);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
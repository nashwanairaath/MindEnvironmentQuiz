package MindEnvQuizApp.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenu {
    private Stage stage;
    private Scene scene;
    
    public MainMenu(Stage stage) {
        this.stage = stage;
        createScene();
    }
    
    private void createScene() {
        // Background gradient
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
        
        // CONTAINER UTAMA
        VBox container = new VBox(40);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(60, 50, 60, 50));
        
        // ========== LOGO & TITLE SECTION ==========
        VBox titleSection = new VBox(20);
        titleSection.setAlignment(Pos.CENTER);
        
        
        Label titleLabel = new Label("Mind & Environment Quiz");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
        titleLabel.setTextFill(Color.WHITE);
        
        Label subtitleLabel = new Label("Ayo, explore apakah lingkungan memengaruhi mood & mental health kamu !");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        subtitleLabel.setTextFill(Color.web("#ecf0f1"));
        
        titleSection.getChildren().addAll(titleLabel, subtitleLabel);
        
        VBox descriptionBox = new VBox(15);
        descriptionBox.setAlignment(Pos.CENTER);
        descriptionBox.setPadding(new Insets(40));
        descriptionBox.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-background-radius: 20; " +
                               "-fx-border-color: rgba(255,255,255,0.3); -fx-border-width: 2; -fx-border-radius: 20;");
        
        Label descTitle = new Label("Sebelum kita mulai, ");
        descTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
        descTitle.setTextFill(Color.WHITE);
        
        Label descText = new Label(
            "Lewat quiz ini, kamu akan mengenal lebih dalam tentang :\n" +
            " 1. Seberapa peduli kamu terhadap lingkungan\n" +
            " 2. Kesehatan mental yang stabil\n" +
            " 3. Hubungan antara lingkungan dan kesehatan mental\n\n" +
            "Ada 16 soal yang membantumu untuk memahami gimana lingkungan dan kesehatan mental saling berhubungan, termasuk pengaruhnya ke diri kamu"
        );
        descText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        descText.setTextFill(Color.web("#ecf0f1"));
        descText.setWrapText(true);
        descText.setLineSpacing(3);
        
        descriptionBox.getChildren().addAll(descTitle, descText);
        
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(20, 0, 0, 0));
        
        VBox stat1 = createStatCard("📝", "16", "Jumlah Soal");
        VBox stat2 = createStatCard("⭐", "64", "Poin Maksimal");
        VBox stat3 = createStatCard("🎯", "3", "Tahap Quiz");
        
        statsBox.getChildren().addAll(stat1, stat2, stat3);
        
        VBox buttonSection = new VBox(15);
        buttonSection.setAlignment(Pos.CENTER);
        buttonSection.setPadding(new Insets(30, 0, 0, 0));
        
        Button startButton = new Button("AYO MULAI");
        startButton.setPrefWidth(300);
        startButton.setPrefHeight(60);
        startButton.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 18));
        startButton.setStyle("-fx-background-color: #7bff47ff; -fx-text-fill: white; " +
                            "-fx-background-radius: 30; -fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);");
        
        Button guideButton = new Button(" PETUNJUK ");
        guideButton.setPrefWidth(300);
        guideButton.setPrefHeight(50);
        guideButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        guideButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                            "-fx-background-radius: 25; -fx-cursor: hand; " +
                            "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;");
        
        Button aboutButton = new Button(" INFORMASI ");
        aboutButton.setPrefWidth(300);
        aboutButton.setPrefHeight(50);
        aboutButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        aboutButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                            "-fx-background-radius: 25; -fx-cursor: hand; " +
                            "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;");
        
        Button exitButton = new Button("KELUAR");
        exitButton.setPrefWidth(300);
        exitButton.setPrefHeight(50);
        exitButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
        exitButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                           "-fx-background-radius: 25; -fx-cursor: hand;");
        
        // Hover effects - START BUTTON
        startButton.setOnMouseEntered(e ->
            startButton.setStyle("-fx-background-color: #7bff47ff; -fx-text-fill: white; " +
                                "-fx-background-radius: 30; -fx-cursor: hand; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 20, 0, 0, 8);")
        );
        startButton.setOnMouseExited(e ->
            startButton.setStyle("-fx-background-color: #7bff47ff; -fx-text-fill: white; " +
                                "-fx-background-radius: 30; -fx-cursor: hand; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);")
        );
        
        // Hover effects - GUIDE BUTTON
        guideButton.setOnMouseEntered(e ->
            guideButton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; " +
                                "-fx-background-radius: 25; -fx-cursor: hand; " +
                                "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;")
        );
        guideButton.setOnMouseExited(e ->
            guideButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                                "-fx-background-radius: 25; -fx-cursor: hand; " +
                                "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;")
        );
        
        // Hover effects - ABOUT BUTTON
        aboutButton.setOnMouseEntered(e ->
            aboutButton.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-text-fill: white; " +
                                "-fx-background-radius: 25; -fx-cursor: hand; " +
                                "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;")
        );
        aboutButton.setOnMouseExited(e ->
            aboutButton.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; " +
                                "-fx-background-radius: 25; -fx-cursor: hand; " +
                                "-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 25;")
        );
        
        // Hover effects - EXIT BUTTON
        exitButton.setOnMouseEntered(e ->
            exitButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; " +
                               "-fx-background-radius: 25; -fx-cursor: hand;")
        );
        exitButton.setOnMouseExited(e ->
            exitButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                               "-fx-background-radius: 25; -fx-cursor: hand;")
        );
        
        // Button actions
        startButton.setOnAction(e -> goToLogin());
        guideButton.setOnAction(e -> showGuide());
        aboutButton.setOnAction(e -> showAbout());
        exitButton.setOnAction(e -> stage.close());
        
        buttonSection.getChildren().addAll(startButton, guideButton, aboutButton, exitButton);
        
        // ========== FOOTER ==========
        VBox footerBox = new VBox(5);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(20, 0, 0, 0));
        
        Label footerLabel1 = new Label("© 2025 Mind & Environment Quiz, L0124067, L0124104, L0124115");
        footerLabel1.setFont(Font.font("Times New Roman", 11));
        footerLabel1.setTextFill(Color.web("#212121ff"));
        
        footerBox.getChildren().addAll(footerLabel1);
        
        // Gabungkan semua ke container
        container.getChildren().addAll(titleSection, descriptionBox, statsBox, buttonSection, footerBox);
        
        // Wrapper dengan ScrollPane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        root.setCenter(scrollPane);
        scene = new Scene(root, 900, 1000);
    }
    
    private VBox createStatCard(String icon, String number, String label) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-background-radius: 15; " +
                     "-fx-border-color: rgba(255,255,255,0.4); -fx-border-width: 2; -fx-border-radius: 15;");
        card.setPrefWidth(200);
        
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Segoe UI", 40));
        
        Label numberLabel = new Label(number);
        numberLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        numberLabel.setTextFill(Color.WHITE);
        
        Label labelText = new Label(label);
        labelText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        labelText.setTextFill(Color.web("#ecf0f1"));
        
        card.getChildren().addAll(iconLabel, numberLabel, labelText);
        return card;
    }
    
    private void goToLogin() {
        LoginPage loginPage = new LoginPage(stage);
        loginPage.show();
    }
    
    private void showGuide() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("📖 Petunjuk Penggunaan ");
        alert.setHeaderText("Yuk, baca dulu sebelum mulai !");
        alert.setContentText(
            "1. Klik 'AYO MULAI' untuk memulai quiz\n" +
            "2. Masukkan nama kamu\n" +
            "3. Jawab 16 soal dengan jujur sesuai keadaan personal mu ! \n\n" +
            "Tahap Quiz:\n" +
            " • Tahap 1: Kesadaran Lingkungan (5 soal)\n" +
            " • Tahap 2: Kesehatan Mental (5 soal)\n" +
            " • Tahap 3: Keterkaitan (6 soal)\n\n" +
            "Sistem Penilaian:\n" +
            " • Setiap jawaban memiliki bobot poin tertentu\n" +
            " • Total maksimal = 64 poin\n" +
            " • Hasil akan ditampilkan di akhir\n\n" 
        );
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    private void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMASI");
        alert.setHeaderText("Mind & Environment Quiz ");
        alert.setContentText(
            "Quiz ini mengedukasi dan meningkatkan kesadaran tentang :\n\n" +
            "1. Kesadaran Lingkungan : Memahami pentingnya menjaga kelestarian alam\n" +
            "2. Kesehatan Mental : Mengenali dan mengatasi masalah kesehatan mental\n" +
            "3. Hubungan Lingkungan & Mental : Memahami bagaimana lingkungan mempengaruhi mental\n\n" +
            "Mari bersama-sama menjaga lingkungan dan kesehatan mental kita di dunia yang semakin berkembang ini!"
        );
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    
    public void show() {
        stage.setScene(scene);
        stage.setTitle("Mind & Environment Quiz - Main Menu");
        stage.show();
    }
}

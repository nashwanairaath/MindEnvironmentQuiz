package MindEnvQuizApp.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import MindEnvQuizApp.model.User;

public class ResultPage {
    private Stage stage;
    private Scene scene;
    private User user;
    
    public ResultPage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
        createScene();
    }
    
    private void createScene() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
        
        VBox root = new VBox(30);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: transparent;");
        
        // CONTAINER UTAMA
        VBox container = new VBox(25);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(700);
        container.setPadding(new Insets(40));
        container.setStyle("-fx-background-color: white; -fx-background-radius: 25; " + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 30, 0, 0, 8);");
        
        // HEADER
        VBox headerBox = new VBox(15);
        headerBox.setAlignment(Pos.CENTER);
        
        Label congratsLabel = new Label(" Yeayyy! ");
        congratsLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 40));
        congratsLabel.setTextFill(Color.web("#2c3e50"));
        
        Label nameLabel = new Label(user.getName());
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        nameLabel.setTextFill(Color.web("#3498db"));
        
        Label completionLabel = new Label("Kamu sudah menyelesaikan seluruh kuis!");
        completionLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        completionLabel.setTextFill(Color.web("#7f8c8d"));
        
        headerBox.getChildren().addAll(congratsLabel, nameLabel, completionLabel);
        
        // RINGKASAN SKOR
        VBox summaryBox = new VBox(20);
        summaryBox.setAlignment(Pos.CENTER);
        summaryBox.setPadding(new Insets(30));
        summaryBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 20;");
        
        Label summaryTitle = new Label(" Perolehan Skor");
        summaryTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        summaryTitle.setTextFill(Color.web("#2c3e50"));
        
        VBox scoresBox = new VBox(15);
        scoresBox.setAlignment(Pos.CENTER);
        
        // Skor per kategori
        HBox score1 = createScoreItem("Kesadaran Lingkungan", user.getEnvironmentScore(), 20, "#11998e");
        HBox score2 = createScoreItem("Kesehatan Mental", user.getMentalHealthScore(), 20, "#ff8391ff");
        HBox score3 = createScoreItem("Keterkaitan Keduanya", user.getRelationScore(), 24, "#ff6b6b");
        
        Separator separator = new Separator();
        separator.setPrefWidth(400);
        separator.setStyle("-fx-background-color: #bdc3c7;");
        
        HBox totalScore = createTotalScoreItem("Total Skor", user.getTotalScore(), 64);
        
        scoresBox.getChildren().addAll(score1, score2, score3, separator, totalScore);
        summaryBox.getChildren().addAll(summaryTitle, scoresBox);
        
        // ANALISIS
        VBox analysisBox = new VBox(20);
        analysisBox.setAlignment(Pos.CENTER);
        analysisBox.setPadding(new Insets(30));
        analysisBox.setStyle("-fx-background-color: " + getResultColor() + "; -fx-background-radius: 20;");
        
        Label analysisTitle = new Label("✨ Tipe Persona Kamu :");
        analysisTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        analysisTitle.setTextFill(Color.WHITE);
        
        Label levelLabel = new Label(user.getAwarenessLevel());
        levelLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 34));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setWrapText(true);
        levelLabel.setAlignment(Pos.CENTER);
        
        Label archetypeDescLabel = new Label(user.getArchetypeDescription());
        archetypeDescLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        archetypeDescLabel.setTextFill(Color.web("#f0f0f0"));
        archetypeDescLabel.setWrapText(true);
        archetypeDescLabel.setAlignment(Pos.CENTER);
        archetypeDescLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label percentageLabel = new Label(String.format("Skor Kamu : %d/64 (%s)", user.getTotalScore(), user.getGrade()));
        percentageLabel.setFont(Font.font("Segoe UI", 15));
        percentageLabel.setTextFill(Color.WHITE);
        percentageLabel.setPadding(new Insets(10,0,0,0));
        analysisBox.getChildren().addAll(analysisTitle, levelLabel, archetypeDescLabel, percentageLabel);

        // details category
        VBox detailBox = new VBox(15);
        detailBox.setAlignment(Pos.CENTER);
        detailBox.setPadding(new Insets(25));
        detailBox.setStyle("-fx-background-color: #e8f5e9; -fx-background-radius: 15;");
        
        Label detailTitle = new Label("Berdasarkan jawaban kamu, ");
        detailTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        detailTitle.setTextFill(Color.web("#2c3e50"));
        
        Label strongestLabel = new Label(" Kamu sangat paham akan pentingnya : " + user.getStrongestCategory());
        strongestLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        strongestLabel.setTextFill(Color.web("#27ae60"));
        strongestLabel.setWrapText(true);
        
        Label weakestLabel = new Label(" Namun, kamu perlu meningkatkan pemahaman tentang : " + user.getWeakestCategory());
        weakestLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        weakestLabel.setTextFill(Color.web("#e74c3c"));
        weakestLabel.setWrapText(true);
        
        detailBox.getChildren().addAll(detailTitle, strongestLabel, weakestLabel);
        
        // Button Quotes
        VBox recommendationBox = new VBox(15);
        recommendationBox.setAlignment(Pos.CENTER);
        recommendationBox.setPadding(new Insets(25));
        recommendationBox.setStyle("-fx-background-color: #fff3cd; -fx-background-radius: 15; " + "-fx-border-color: #ffc107; -fx-border-width: 2; -fx-border-radius: 15;");
        
        Label recTitle = new Label(" Quotes untuk kamu :");
        recTitle.setFont(Font.font("Garamond", FontWeight.BOLD, 20)); 
        recTitle.setTextFill(Color.web("#5d4037")); 
        
        Label recText = new Label(user.getAwarenessDescription());
        recText.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 16));
        recText.setTextFill(Color.web("#856404"));
        recText.setWrapText(true);
        recommendationBox.getChildren().addAll(recTitle, recText);
        
        // Button 
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button retakeButton = new Button(" Ulangi Kuis");
        retakeButton.setPrefWidth(200);
        retakeButton.setPrefHeight(50);
        retakeButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        retakeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " + "-fx-background-radius: 25; -fx-cursor: hand;");
        
        Button exitButton = new Button(" Keluar");
        exitButton.setPrefWidth(200);
        exitButton.setPrefHeight(50);
        exitButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        exitButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; " + "-fx-background-radius: 25; -fx-cursor: hand;");
        
        // Hover effects
        retakeButton.setOnMouseEntered(e -> 
            retakeButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; " + "-fx-background-radius: 25; -fx-cursor: hand;"));
        retakeButton.setOnMouseExited(e -> 
            retakeButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +"-fx-background-radius: 25; -fx-cursor: hand;"));
    
        exitButton.setOnMouseEntered(e -> 
            exitButton.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; "+ "-fx-background-radius: 25; -fx-cursor: hand;"));
        exitButton.setOnMouseExited(e -> 
            exitButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; " + "-fx-background-radius: 25; -fx-cursor: hand;"));
        
        buttonBox.getChildren().addAll(retakeButton, exitButton);
        
        // Button actions
        retakeButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });
        
        exitButton.setOnAction(e -> 
                stage.close()
            );
        
        // Gabungkan semua
        container.getChildren().addAll(headerBox, summaryBox, analysisBox, detailBox, recommendationBox, buttonBox);
        root.getChildren().add(container);
        
        scrollPane.setContent(root);
        scene = new Scene(scrollPane, 800, 600);
    }
    
    private HBox createScoreItem(String label, int score, int maxScore, String color) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        nameLabel.setTextFill(Color.web("#2c3e50"));
        nameLabel.setPrefWidth(250);
        
        ProgressBar progressBar = new ProgressBar((double) score / maxScore);
        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(15);
        progressBar.setStyle("-fx-accent: " + color + ";");
        
        Label scoreLabel = new Label(score + "/" + maxScore);
        scoreLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        scoreLabel.setTextFill(Color.web(color));
        scoreLabel.setPrefWidth(70);
        scoreLabel.setAlignment(Pos.CENTER_RIGHT);
        
        box.getChildren().addAll(nameLabel, progressBar, scoreLabel);
        return box;
    }
    
    private HBox createTotalScoreItem(String label, int score, int maxScore) {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15, 10, 10, 10));
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        nameLabel.setTextFill(Color.web("#2c3e50"));
        nameLabel.setPrefWidth(250);
        
        ProgressBar progressBar = new ProgressBar((double) score / maxScore);
        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);
        progressBar.setStyle("-fx-accent: #9b59b6;");
        
        Label scoreLabel = new Label(score + "/" + maxScore);
        scoreLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        scoreLabel.setTextFill(Color.web("#9b59b6"));
        scoreLabel.setPrefWidth(70);
        scoreLabel.setAlignment(Pos.CENTER_RIGHT);
        
        box.getChildren().addAll(nameLabel, progressBar, scoreLabel);
        return box;
    }
    
    private String getResultColor() {
        int total = user.getTotalScore();
        if (total >= 56) {
            return "#27ae60";
        }
        if (total >= 41) {
            return "#3498db";
        }
        if (total >= 26) {
            return "#f39c12";
        }
        else {
            return "#e74c3c";
        }
    }
    
    public void show() {
        stage.setScene(scene);
    }
}

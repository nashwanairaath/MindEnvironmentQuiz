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
import MindEnvQuizApp.model.User;
import MindEnvQuizApp.model.QuizLogic;
import java.io.File;

public class LoginPage {
    private Stage stage;
    private Scene scene;
    private TextField nameField;
    private Label errorLabel;
    private QuizLogic quizLogic;

    public LoginPage(Stage stage) {
        this.stage = stage;
        this.quizLogic = new QuizLogic();
        createScene();
    }

    public LoginPage(Stage stage, boolean reset) {
        this.stage = stage;
        createScene();
    }

    private void createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");

        VBox container = new VBox(40);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(70));
        container.setMaxWidth(650);

        VBox headerBox = new VBox(15);
        headerBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Mind & Environment");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 50));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Quiz Kesadaran Lingkungan & Mental");
        subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 18));
        subtitleLabel.setTextFill(Color.web("#ecf0f1"));

        headerBox.getChildren().addAll(titleLabel, subtitleLabel);

        VBox formBox = new VBox(25);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(50));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 30, 0, 0, 8);");

        Label nameLabel = new Label("👤 Nama Kamu:");
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#2c3e50"));

        nameField = new TextField();
        nameField.setPromptText("Masukkan nama kamu...");
        nameField.setPrefHeight(55);
        nameField.setStyle("-fx-font-size: 16; -fx-padding: 10; " +
                "-fx-border-color: #bdc3c7; -fx-border-radius: 10; " +
                "-fx-background-radius: 10;");

        errorLabel = new Label();
        errorLabel.setFont(Font.font("Segoe UI", 14));
        errorLabel.setTextFill(Color.web("#e74c3c"));
        errorLabel.setWrapText(true);

        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 10;");
        infoBox.setAlignment(Pos.TOP_LEFT);

        infoBox.setMinHeight(Region.USE_PREF_SIZE);
        infoBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        infoBox.setMaxHeight(Region.USE_PREF_SIZE);

        Label infoTitle = new Label("Informasi Kuis:");
        infoTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        infoTitle.setTextFill(Color.web("#2c3e50"));

        Label infoText = new Label(
                "• Total 16 soal\n" +
                        "• Kombinasi materi:\n" +
                        "  - Kesadaran Lingkungan\n" +
                        "  - Kesehatan Mental\n" +
                        "  - Hubungan Keduanya\n" +
                        "• Total Skor Maksimal: 64 poin"
        );
        infoText.setFont(Font.font("Segoe UI", 13));
        infoText.setTextFill(Color.web("#34495e"));
        infoText.setWrapText(true);
        

        infoText.setMaxWidth(450);
        infoText.setPrefWidth(450);

        infoText.setMinHeight(Region.USE_PREF_SIZE);
        infoText.setPrefHeight(Region.USE_COMPUTED_SIZE);
        infoText.setMaxHeight(Region.USE_PREF_SIZE);

        formBox.setFillWidth(true);
        infoBox.getChildren().addAll(infoTitle, infoText);
        container.setFillWidth(true);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(30, 0, 0, 0));

        Button startButton = new Button("🚀 Mulai Quiz");
        startButton.setPrefWidth(250);
        startButton.setPrefHeight(60);
        startButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 17));
        startButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; " +
                "-fx-background-radius: 25; -fx-cursor: hand;");

        Button backButton = new Button(" Kembali");
        backButton.setPrefWidth(120);
        backButton.setPrefHeight(60);
        backButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        backButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; " +
                "-fx-background-radius: 25; -fx-cursor: hand;");

        startButton.setOnMouseEntered(e ->
                startButton.setStyle("-fx-background-color: #5568d3; -fx-text-fill: white; " +
                        "-fx-background-radius: 25; -fx-cursor: hand;")
        );
        startButton.setOnMouseExited(e ->
                startButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; " +
                        "-fx-background-radius: 25; -fx-cursor: hand;")
        );

        backButton.setOnMouseEntered(e ->
                backButton.setStyle("-fx-background-color: #7f8c8d; -fx-text-fill: white; " +
                        "-fx-background-radius: 25; -fx-cursor: hand;")
        );
        backButton.setOnMouseExited(e ->
                backButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; " +
                        "-fx-background-radius: 25; -fx-cursor: hand;")
        );

        startButton.setOnAction(e -> handleStartQuiz());
        backButton.setOnAction(e -> goBackToMenu());

        buttonBox.getChildren().addAll(startButton, backButton);

        formBox.getChildren().addAll(
                nameLabel, nameField,
                infoBox,
                errorLabel,
                buttonBox
        );

        container.getChildren().addAll(headerBox, formBox);


        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);

        container.setAlignment(Pos.CENTER);
        
        root.setCenter(scrollPane);
        BorderPane.setAlignment(scrollPane, Pos.CENTER);
        scene = new Scene(root, 1000, 700);

        container.maxWidthProperty().bind(scene.widthProperty());

    }

    private void handleStartQuiz() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            errorLabel.setText("❌ Nama tidak boleh kosong!");
            return;
        }

        if (name.length() < 3) {
            errorLabel.setText("❌ Nama minimal 3 karakter!");
            return;
        }

        if (name.length() > 30) {
            errorLabel.setText("❌ Nama maksimal 30 karakter!");
            return;
        }

        String workingDir = System.getProperty("user.dir");
        System.out.println("\n=== DEBUG INFO ===");
        System.out.println("Working Directory: " + workingDir);
        System.out.println("File Separator: " + System.getProperty("file.separator"));

        String[] pathsToTry = {
        		"assets/environment.txt",
                "src/assets/environment.txt",
                "assets/environtment.txt",
                "src/assets/environtment.txt",
                workingDir + "/assets/environment.txt",
                workingDir + "/src/assets/environment.txt"
        };

        boolean fileFound = false;
        String foundPath = "";

        for (String testPath : pathsToTry) {
            System.out.println("🔍 Mencoba: " + testPath);
            File file = new File(testPath);

            if (file.exists() && file.isFile()) {
                System.out.println("✅ File ditemukan!");
                System.out.println("   Absolute path: " + file.getAbsolutePath());
                System.out.println("   File size: " + file.length() + " bytes");

                foundPath = testPath.substring(0, testPath.lastIndexOf("/") + 1);
                fileFound = true;
                break;
            } else {
                System.out.println("❌ File tidak ditemukan");
            }
        }

        System.out.println("=================\n");

        if (!fileFound) {
            errorLabel.setText(
                    "❌ File soal tidak ditemukan!\n\n" +
                            "Pastikan struktur folder BENAR:\n\n" +
                            "Online-Quiz-PBO-main/\n" +
                            "  └── src/\n" +
                            "      └── assets/\n" +
                            "          ├── environtment.txt\n" +
                            "          ├── mental_health.txt\n" +
                            "          └── relation.txt\n\n" +
                            "Working Dir: " + workingDir
            );
            return;
        }

        errorLabel.setText("");
        User user = new User(name);

        EnvironmentStage environmentStage = new EnvironmentStage(stage, user, foundPath);
        environmentStage.show();
    }

    private void goBackToMenu() {
        MainMenu mainMenu = new MainMenu(stage);
        mainMenu.show();
    }

    public void show() {
        stage.setScene(scene);
        stage.setTitle("Mind & Environment Quiz - Login");
        stage.show();
    }
}

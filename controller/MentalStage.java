package MindEnvQuizApp.controller;

import MindEnvQuizApp.model.Question;
import MindEnvQuizApp.model.QuizLogic;
import MindEnvQuizApp.model.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class MentalStage {

    private Stage stage;
    private User user;
    private QuizLogic quizLogic;
    private List<Question> questions;
    private String basePath;
    private int index = 0;
    
    // Store answers untuk semua soal
    private String[] userAnswers;
    private Scene scene;

    public MentalStage(Stage stage, User user, String basePath) {
        this.stage = stage;
        this.user = user;
        this.basePath = basePath;
        this.quizLogic = new QuizLogic();
        
        String filePath = basePath + "mental_health.txt";
        System.out.println("\nLoading Mental Questions from: " + filePath);
        
        quizLogic.loadFromFile(filePath);
        this.questions = quizLogic.getQuestions();
        
        System.out.println("Total mental questions loaded: " + (questions != null ? questions.size() : 0));
        
        // Initialize answer storage
        if (questions != null) {
            this.userAnswers = new String[questions.size()];
        }
        
        if (questions != null && !questions.isEmpty()) {
            createScene();
        } else {
            showErrorScene("Tidak ada soal Mental Health yang dimuat!");
        }
    }
    
    public MentalStage(Stage stage, User user) {
        this(stage, user, "src/assets/");
    }

    private void createScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #eb3349 0%, #f45c43 100%);");

        // HEADER
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 15;");
        headerBox.setPadding(new Insets(20));

        Label stageTitle = new Label("Tahap 2: Kesehatan Mental");
        stageTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        stageTitle.setTextFill(Color.web("#eb3349"));

        Label progressLabel = new Label("Soal " + (index + 1) + " dari " + questions.size());
        progressLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        progressLabel.setTextFill(Color.web("#7f8c8d"));

        headerBox.getChildren().addAll(stageTitle, progressLabel);

        // QUESTION BOX
        VBox questionBox = new VBox(8);
        questionBox.setAlignment(Pos.CENTER_LEFT);
        questionBox.setPadding(new Insets(18));
        questionBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);");
        questionBox.setMaxWidth(850);
        VBox.setMargin(questionBox, new Insets(0, 50, 0, 50));

        Label questionNumber = new Label("Soal " + (index + 6)); // Soal 6-10
        questionNumber.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        questionNumber.setTextFill(Color.web("#ff6f80ff"));
 
        Question q = questions.get(index);
        Label questionText = new Label(q.getText());
        questionText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        questionText.setTextFill(Color.web("#2c3e50"));
        questionText.setWrapText(true);
        questionText.setLineSpacing(7);

        questionBox.getChildren().addAll(questionNumber, questionText);

        // OPTIONS BOX
        VBox optionsBox = new VBox(15);
        optionsBox.setPadding(new Insets(30));
        optionsBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                           "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);");
        optionsBox.setMaxWidth(850);
        VBox.setMargin(optionsBox, new Insets(0, 50, 0, 50));

        Label optionsTitle = new Label("Pilih Jawaban:");
        optionsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        optionsTitle.setTextFill(Color.web("#2c3e50"));

        optionsBox.getChildren().add(optionsTitle);

        // Track selected answer
        final String[] selectedAnswer = {userAnswers[index]};
        final Button[] selectedButton = {null};

        // Create option buttons
        String[] choices = q.getChoices();
        String[] labels = {"A", "B", "C", "D"};
        
        for (int i = 0; i < choices.length; i++) {
            final String answerLabel = labels[i];
            Button btn = new Button(answerLabel + ". " + choices[i]);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(60);
            btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
            btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; " +
                        "-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 10; " +
                        "-fx-background-radius: 10; -fx-cursor: hand;");
            btn.setWrapText(true);

            // Jika jawaban sudah dipilih sebelumnya, highlight button tersebut
            if (selectedAnswer[0] != null && selectedAnswer[0].equals(answerLabel)) {
                btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                           "-fx-border-color: #27ae60; -fx-border-width: 2; -fx-border-radius: 10; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;");
                selectedButton[0] = btn;
            }

            // Hover effect
            btn.setOnMouseEntered(e -> {
                if (selectedButton[0] != btn) {
                    btn.setStyle("-fx-background-color: #eb3349; -fx-text-fill: white; " +
                               "-fx-border-color: #eb3349; -fx-border-width: 2; -fx-border-radius: 10; " +
                               "-fx-background-radius: 10; -fx-cursor: hand;");
                }
            });

            btn.setOnMouseExited(e -> {
                if (selectedButton[0] != btn) {
                    btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; " +
                               "-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 10; " +
                               "-fx-background-radius: 10; -fx-cursor: hand;");
                }
            });

            // On click - hanya pilih jawaban, JANGAN langsung pindah soal
            btn.setOnAction(e -> {
                System.out.println("\n Button " + answerLabel + " clicked on question " + (index + 1));
                
                // Unselect previous button
                if (selectedButton[0] != null) {
                    selectedButton[0].setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; " +
                               "-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 10; " +
                               "-fx-background-radius: 10; -fx-cursor: hand;");
                }
                
                // Select new button
                selectedAnswer[0] = answerLabel;
                selectedButton[0] = btn;
                btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                           "-fx-border-color: #27ae60; -fx-border-width: 2; -fx-border-radius: 10; " +
                           "-fx-background-radius: 10; -fx-cursor: hand;");
                
                System.out.println(" Answer selected: " + answerLabel);
            });

            optionsBox.getChildren().add(btn);
        }

        // NAVIGATION BOX
        HBox navBox = new HBox(20);
        navBox.setAlignment(Pos.CENTER);
        navBox.setPadding(new Insets(20));

        Button prevBtn = new Button(" Sebelumnya");
        prevBtn.setPrefWidth(180);
        prevBtn.setPrefHeight(55);
        prevBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        prevBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-background-radius: 20;");
        prevBtn.setDisable(index == 0);

        Button nextBtn = new Button("Selanjutnya ");
        nextBtn.setPrefWidth(180);
        nextBtn.setPrefHeight(55);
        nextBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        nextBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 20;");
        nextBtn.setVisible(index < questions.size() - 1);

        Button submitBtn = new Button(" Kirim Jawaban");
        submitBtn.setPrefWidth(180);
        submitBtn.setPrefHeight(55);
        submitBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        submitBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 20;");
        submitBtn.setVisible(index == questions.size() - 1);

        prevBtn.setOnAction(e -> {
            System.out.println("\n Previous clicked");
            if (index > 0) {
                index--;
                createScene();
            }
        });

        nextBtn.setOnAction(e -> {
            System.out.println("\n Next clicked");
            if (selectedAnswer[0] != null) {
                handleAnswer(selectedAnswer[0]);
                if (index < questions.size() - 1) {
                    index++;
                    createScene();
                }
            } else {
                showWarning("Pilih jawaban terlebih dahulu!");
            }
        });

        submitBtn.setOnAction(e -> {
            System.out.println("\n Mental Stage Submit clicked!");
            if (selectedAnswer[0] != null) {
                handleAnswer(selectedAnswer[0]);
                submitQuiz();
            } else {
                showWarning(" Pilih jawaban terlebih dahulu!");
            }
        });

        navBox.getChildren().addAll(prevBtn, nextBtn, submitBtn);

        // PROGRESS BOX
        VBox footerBox = new VBox(25);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(20));
        footerBox.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 15;");

        Label progressText = new Label(String.format("%.0f%% Selesai", ((double) (index + 1) / questions.size()) * 100));
        progressText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
        progressText.setTextFill(Color.web("#7f8c8d"));

        footerBox.getChildren().add(progressText);

        // MAIN CONTENT - tampil langsung tanpa ScrollPane dan tanpa progress footer
        VBox contentBox = new VBox(25);
        contentBox.getChildren().addAll(questionBox, optionsBox);
        
        root.getChildren().addAll(headerBox, contentBox, navBox);
        root.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(contentBox, Priority.ALWAYS);
        
        scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void handleAnswer(String answer) {
        System.out.println(" Answer selected for question " + (index + 1) + ": " + answer);
        userAnswers[index] = answer;
        
        Question current = questions.get(index);

        // Jika jawaban benar, tambah skor
        if (answer.equals(current.getCorrectAnswer())) {
            user.setMentalHealthScore(user.getMentalHealthScore() + 4);
            System.out.println(" Jawaban BENAR! Skor +4");
        } else {
            System.out.println(" Jawaban SALAH! Benar: " + current.getCorrectAnswer());
        }
        
        System.out.println(" Mental Health Score: " + user.getMentalHealthScore());
    }

    private void submitQuiz() {
        System.out.println("\n========================================");
        System.out.println(" Quiz Mental selesai!");
        System.out.println(" Skor Mental: " + user.getMentalHealthScore() + " / 20");
        System.out.println("========================================\n");
        
        // Validasi path
        String relationPath = basePath;
        if (!relationPath.endsWith("/")) {
            relationPath += "/";
        }
        
        System.out.println("Moving to Relation Stage with path: " + relationPath);
        
        try {
            RelationStage relationStage = new RelationStage(stage, user, relationPath);
            relationStage.show();
        } catch (Exception e) {
            System.out.println("Error transitioning to Relation Stage: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showErrorScene(String errorMessage) {
        VBox errorBox = new VBox(20);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(50));
        errorBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #eb3349 0%, #f45c43 100%);");
        
        Label errorLabel = new Label(errorMessage);
        errorLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        errorLabel.setTextFill(Color.RED);
        
        Button backButton = new Button("Kembali");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(50);
        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });
        
        errorBox.getChildren().addAll(errorLabel, backButton);
        
        Scene scene = new Scene(errorBox, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void show() {
        stage.setScene(scene);
        stage.setTitle("Mind & Environment - Mental Stage");
        stage.show();
    }
}

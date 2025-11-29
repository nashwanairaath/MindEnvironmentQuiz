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
import MindEnvQuizApp.model.Question;
import java.util.List;

public class EnvironmentStage {
    private Stage stage;
    private User user;
    private QuizLogic quizLogic;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private String basePath;
    private String[] userAnswers;
    private Scene scene;
    
    public EnvironmentStage(Stage stage, User user, String basePath) {
        this.stage = stage;
        this.user = user;
        this.basePath = basePath;
        this.quizLogic = new QuizLogic();
        
        String filePath = basePath + "environtment.txt";
        System.out.println("\nLoading Environment Questions from: " + filePath);
        
        quizLogic.loadFromFile(filePath);
        this.questions = quizLogic.getQuestions();
        
        System.out.println("Total questions loaded: " + (questions != null ? questions.size() : 0));
        
        // Initialize answer storage
        if (questions != null) {
            this.userAnswers = new String[questions.size()];
        }
        
        if (questions != null && !questions.isEmpty()) {
            createScene();
        } else {
            showErrorScene("Tidak ada soal Environment yang dimuat!");
        }
    }
    
    public EnvironmentStage(Stage stage, User user) {
        this(stage, user, "src/assets/");
    }
    
    private void createScene() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            System.out.println("Invalid question index: " + currentQuestionIndex);
            return;
        }

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");

        // HEADER
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 15;");
        headerBox.setPadding(new Insets(20));

        Label stageTitle = new Label("Tahap 1: Kesadaran Lingkungan");
        stageTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        stageTitle.setTextFill(Color.web("#667eea"));

        Label progressLabel = new Label("Soal " + (currentQuestionIndex + 1) + " dari " + questions.size());
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

        Label questionNumber = new Label("Soal " + (currentQuestionIndex + 1));
        questionNumber.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        questionNumber.setTextFill(Color.web("#667eea"));
 
        Question q = questions.get(currentQuestionIndex);
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

        // Create option buttons
        String[] choices = q.getChoices();
        String[] labels = {"A", "B", "C", "D"};
        
        // Track selected answer
        final String[] selectedAnswer = {null};
        final Button[] selectedButton = {null};
        
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

            // Hover effect
            btn.setOnMouseEntered(e -> {
                if (selectedButton[0] != btn) {
                    btn.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; " +
                               "-fx-border-color: #667eea; -fx-border-width: 2; -fx-border-radius: 10; " +
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
                System.out.println("\nButton " + answerLabel + " clicked on question " + (currentQuestionIndex + 1));
                
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
                
                System.out.println("✅ Answer selected: " + answerLabel);
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
        prevBtn.setDisable(currentQuestionIndex == 0);

        Button nextBtn = new Button("Selanjutnya");
        nextBtn.setPrefWidth(180);
        nextBtn.setPrefHeight(55);
        nextBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        nextBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 20;");
        nextBtn.setVisible(currentQuestionIndex < questions.size() - 1);

        Button submitBtn = new Button("Kirim Jawaban");
        submitBtn.setPrefWidth(180);
        submitBtn.setPrefHeight(55);
        submitBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        submitBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 20;");
        submitBtn.setVisible(currentQuestionIndex == questions.size() - 1);

        prevBtn.setOnAction(e -> {
            System.out.println("\n⬅️ Previous clicked");
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                createScene();
            }
        });

        nextBtn.setOnAction(e -> {
            System.out.println("\n➜ Next clicked");
            if (selectedAnswer[0] != null) {
                scoreAnswer(selectedAnswer[0]);
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    createScene();
                }
            } else {
                showWarning("⚠️ Pilih jawaban terlebih dahulu!");
            }
        });

        submitBtn.setOnAction(e -> {
            System.out.println("\nSubmit button clicked on last question");
            if (selectedAnswer[0] != null) {
                scoreAnswer(selectedAnswer[0]);
                submitQuiz();
            } else {
                showWarning("⚠️ Pilih jawaban terlebih dahulu!");
            }
        });

        navBox.getChildren().addAll(prevBtn, nextBtn, submitBtn);


        // MAIN CONTENT - tampil langsung tanpa ScrollPane dan tanpa progress footer
        VBox contentBox = new VBox(25);
        contentBox.getChildren().addAll(questionBox, optionsBox);
        
        // Gabungkan semua ke root (header + content + nav)
        root.getChildren().addAll(headerBox, contentBox, navBox);
        root.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(contentBox, Priority.ALWAYS);
        
        scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    
// Metode untuk menilai jawaban dan memperbarui skor
    private void scoreAnswer(String answer) {
        System.out.println(" Calculating score for answer: " + answer);
        
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            System.out.println(" Invalid question index: " + currentQuestionIndex);
            return;
        }
        
        Question current = questions.get(currentQuestionIndex);
        String correctAnswer = current.getCorrectAnswer();
        
        System.out.println("   Answer provided: " + answer);
        System.out.println("   Correct answer: " + correctAnswer);

        // Jika jawaban benar, tambah skor
        if (answer.equals(correctAnswer)) {
            user.setEnvironmentScore(user.getEnvironmentScore() + 4);
            System.out.println(" CORRECT! +4 points");
        } else {
            System.out.println(" WRONG!");
        }
        
        System.out.println(" Current Environment Score: " + user.getEnvironmentScore() + " / 20");
    }

    private void submitQuiz() {
        System.out.println("\n========================================");
        System.out.println(" Quiz Environment SELESAI!");
        System.out.println(" Skor Environment Final: " + user.getEnvironmentScore() + " / 20");
        System.out.println("========================================\n");
        
        // Validasi path
        String mentalPath = basePath;
        if (!mentalPath.endsWith("/")) {
            mentalPath += "/";
        }
        
        System.out.println(" Moving to Mental Stage with path: " + mentalPath);
        
        try {
            MentalStage mentalStage = new MentalStage(stage, user, mentalPath);
            mentalStage.show();
        } catch (Exception e) {
            System.out.println(" Error transitioning to Mental Stage: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showErrorScene(String errorMessage) {
        VBox errorBox = new VBox(20);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(50));
        errorBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
        
        Label errorLabel = new Label(errorMessage);
        errorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        errorLabel.setTextFill(Color.RED);
        
        Button backButton = new Button("Kembali");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(50);
        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });
        
        errorBox.getChildren().addAll(errorLabel, backButton);
        
        Scene errorScene = new Scene(errorBox, 800, 600);
        stage.setScene(errorScene);
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
        stage.setTitle("Mind & Environment - Environment Stage");
        stage.show();
    }
}

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class RelationStage {

    private Stage stage;
    private User user;
    private QuizLogic quizLogic;
    private List<Question> questions;
    private int index = 0;

    private String[] userAnswers;
    private Scene scene;

    public RelationStage(Stage stage, User user, String basePath) {
        this.stage = stage;
        this.user = user;
        this.quizLogic = new QuizLogic();

        String filePath = basePath + "relation.txt";
        System.out.println("\n Loading Relation Questions from: " + filePath);

        quizLogic.loadFromFile(filePath);
        this.questions = quizLogic.getQuestions();

        System.out.println(" Total relation questions loaded: " + (questions != null ? questions.size() : 0));

        if (questions != null) {
            this.userAnswers = new String[questions.size()];
        }

        if (questions != null && !questions.isEmpty()) {
            createScene();
        } else {
            showErrorScene(" Tidak ada soal Relation yang dimuat!");
        }
    }

    public RelationStage(Stage stage, User user) {
        this(stage, user, "src/assets/");
    }

    private void createScene() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ff6b6b 0%, #feca57 100%);");

        // HEADER
        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 12;");
        headerBox.setPadding(new Insets(20));

        Label stageTitle = new Label("Tahap 3: Keterkaitan Lingkungan & Mental");
        stageTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        stageTitle.setTextFill(Color.web("#ff6b6b"));

        Label progressLabel = new Label("Soal " + (index + 1) + " dari " + questions.size());
        progressLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        progressLabel.setTextFill(Color.web("#7f8c8d"));

        headerBox.getChildren().addAll(stageTitle, progressLabel);

        // QUESTION BOX
        VBox questionBox = new VBox(8);
        questionBox.setAlignment(Pos.CENTER_LEFT);
        questionBox.setPadding(new Insets(18));
        questionBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 12, 0, 0, 4);");
        questionBox.setMaxWidth(850);
        VBox.setMargin(questionBox, new Insets(0, 50, 0, 50));


        Label questionNumber = new Label("Soal " + (index + 11)); // Soal 11-...
        questionNumber.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        questionNumber.setTextFill(Color.web("#ff6b6b"));

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
        VBox.setMargin(questionBox, new Insets(0, 50, 0, 50));

        Label optionsTitle = new Label("Pilih Jawaban:");
        optionsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        optionsTitle.setTextFill(Color.web("#2c3e50"));
        optionsBox.getChildren().add(optionsTitle);

        // Track selected answer for this screen
        final String[] selectedAnswer = { userAnswers[index] };
        final Button[] selectedButton = { null };

        String[] choices = q.getChoices();
        String[] labels = {"A", "B", "C", "D"};

        for (int i = 0; i < choices.length; i++) {
            final String label = labels[i];
            Button btn = new Button(label + ". " + choices[i]);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(60);
            btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
            btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; " +
                        "-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 10; " +
                        "-fx-background-radius: 10; -fx-cursor: hand;");
            btn.setWrapText(true);

            // restore previous selection visually
            if (selectedAnswer[0] != null && selectedAnswer[0].equals(label)) {
                selectedButton[0] = btn;
                btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-border-radius: 8; -fx-background-radius: 8;");
            }

            btn.setOnMouseEntered(e -> {
                if (selectedButton[0] != btn) {
                    btn.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-border-radius: 8; -fx-background-radius: 8;");
                }
            });
            btn.setOnMouseExited(e -> {
                if (selectedButton[0] != btn) {
                    btn.setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; -fx-border-radius: 8; -fx-background-radius: 8;");
                }
            });

            // only select, do NOT navigate
            btn.setOnAction(e -> {
                if (selectedButton[0] != null && selectedButton[0] != btn) {
                    selectedButton[0].setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50; -fx-border-radius: 8; -fx-background-radius: 8;");
                }
                selectedAnswer[0] = label;
                selectedButton[0] = btn;
                btn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-border-radius: 8; -fx-background-radius: 8;");
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
        submitBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        submitBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 20;");
        submitBtn.setVisible(index == questions.size() - 1);

        prevBtn.setOnAction(e -> {
            if (index > 0) {
                index--;
                createScene();
            }
        });

        nextBtn.setOnAction(e -> {
            if (selectedAnswer[0] == null) {
                showWarning(" Pilih jawaban terlebih dahulu!");
                return;
            }
            applyAnswerForIndex(index, selectedAnswer[0]);
            if (index < questions.size() - 1) {
                index++;
                createScene();
            }
        });

        submitBtn.setOnAction(e -> {
            if (selectedAnswer[0] == null) {
                showWarning(" Pilih jawaban terlebih dahulu!");
                return;
            }
            applyAnswerForIndex(index, selectedAnswer[0]);
            submitQuiz();
        });

        navBox.getChildren().addAll(prevBtn, nextBtn, submitBtn);

        // Compose root (no scroll, no footer progress)
        VBox contentBox = new VBox(12);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.getChildren().addAll(questionBox, optionsBox);
        root.getChildren().addAll(headerBox, contentBox, navBox);
        root.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(contentBox, Priority.ALWAYS);

        scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Mind & Environment - Relation Stage");
        stage.show();
    }

    private void applyAnswerForIndex(int qIndex, String answer) {
        Question current = questions.get(qIndex);
        String prev = userAnswers[qIndex];

        // remove previous points if it was correct
        if (prev != null && prev.equals(current.getCorrectAnswer())) {
            user.setRelationScore(user.getRelationScore() - 4);
        }

        // store new answer
        userAnswers[qIndex] = answer;

        // add points if correct
        if (answer.equals(current.getCorrectAnswer())) {
            user.setRelationScore(user.getRelationScore() + 4);
        }

        System.out.println(" Soal " + (qIndex + 1) + " jawaban: " + answer + " | Relation Score: " + user.getRelationScore());
    }

    private void submitQuiz() {
        System.out.println("\n Quiz Relation selesai!");
        System.out.println("Skor Relation: " + user.getRelationScore() + " / 24");
        System.out.println("Total Score: " + user.getTotalScore() + " / 64");
        System.out.println("Grade: " + user.getGrade());

        try {
            ResultPage resultPage = new ResultPage(stage, user);
            resultPage.show();
        } catch (Exception e) {
            System.out.println("Error transitioning to Result Page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorScene(String errorMessage) {
        VBox errorBox = new VBox(18);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(40));
        errorBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ff6b6b 0%, #feca57 100%);");

        Label errorLabel = new Label(errorMessage);
        errorLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        errorLabel.setTextFill(Color.RED);

        Button backButton = new Button("Kembali");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(44);
        backButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });

        errorBox.getChildren().addAll(errorLabel, backButton);

        Scene s = new Scene(errorBox, 1000, 700);
        stage.setScene(s);
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
        createScene();
    }
}

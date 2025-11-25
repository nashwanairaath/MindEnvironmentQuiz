package controller;

import model.Question;
import model.QuizLogic;
import model.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class EnvironmentStage {

    private Stage stage;
    private User user;
    private QuizLogic quiz;
    private List<Question> envQuestions;
    private int index = 0;

    public EnvironmentStage(Stage stage, User user) {
        this.stage = stage;
        this.user = user;

        quiz = new QuizLogic();
        quiz.loadFromFile("assets/environment.txt");

        // Ambil 5 soal environment
        envQuestions = quiz.getQuestions();
    }

    public void show() {

        Question q = envQuestions.get(index);

        Label title = new Label("Tahap 1 : Lingkungan");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label questionText = new Label((index + 1) + ". " + q.getText());
        questionText.setWrapText(true);
        questionText.setStyle("-fx-font-size: 16px;");

        VBox root = new VBox(15, title, questionText);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        // Tampilkan opsi jawaban
        for (String choice : q.getChoices()) {
            Button btn = new Button(choice);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-font-size: 14px;");

            btn.setOnAction(e -> {
                handleAnswer(choice);
                nextQuestion();
            });

            root.getChildren().add(btn);
        }

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void handleAnswer(String answer) {
        Question current = envQuestions.get(index);

        // Jika jawaban benar, tambah skor
        if (answer.equals(current.getCorrectAnswer())) {
            user.setEnvironmentScore(user.getEnvironmentScore() + 4); // total max 20
        }
    }

    private void nextQuestion() {
        index++;

        if (index < envQuestions.size()) {
            show();
        } else {
            // Tahap 1 selesai → masuk ke MentalStage
            MentalStage next = new MentalStage(stage, user);
            next.show();
        }
    }
}

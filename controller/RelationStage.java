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

public class RelationStage {
    // Properties untuk menyimpan data
    private Stage stage;
    private Scene scene;
    private User user;
    private QuizLogic quizLogic;
    private List<Question> questions;
    private int currentQuestionIndex = 0;  // Soal ke berapa sekarang (0-5)
    private int totalScore = 0;             // Total skor tahap ini
    
    // UI Components
    private VBox questionContainer;
    private Label questionNumberLabel;
    private Label questionTextLabel;
    private ToggleGroup answerGroup;
    private VBox optionsBox;
    private Button nextButton;
    private ProgressBar progressBar;
    private Label progressLabel;
    
    // Constructor - dipanggil saat tahap 3 dimulai
    public RelationStage(Stage stage, User user, QuizLogic quizLogic) {
        this.stage = stage;
        this.user = user;
        this.quizLogic = quizLogic;
        this.questions = quizLogic.getRelationQuestions();  // Ambil 6 soal relation
        createScene();  // Buat tampilan
    }
    
    private void createScene() {
        // Layout utama
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ff6b6b 0%, #feca57 100%);");
        
        // ========== TOP BAR - Progress & Info ==========
        VBox topBar = new VBox(10);
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: rgba(255,255,255,0.95);");
        
        // Label tahap
        Label stageLabel = new Label("🔗 Tahap 3: Keterkaitan Lingkungan & Mental");
        stageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        stageLabel.setTextFill(Color.web("#ff6b6b"));
        
        // Progress bar
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(760);
        progressBar.setPrefHeight(12);
        progressBar.setStyle("-fx-accent: #feca57;");
        
        // Label progress "Soal X dari Y"
        progressLabel = new Label("Soal 1 dari 6");
        progressLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        progressLabel.setTextFill(Color.web("#2c3e50"));
        
        topBar.getChildren().addAll(stageLabel, progressBar, progressLabel);
        
        // ========== CENTER - Pertanyaan & Pilihan ==========
        questionContainer = new VBox(25);
        questionContainer.setAlignment(Pos.TOP_CENTER);
        questionContainer.setPadding(new Insets(40));
        questionContainer.setMaxWidth(700);
        
        // Box pertanyaan
        VBox questionBox = new VBox(15);
        questionBox.setAlignment(Pos.CENTER_LEFT);
        questionBox.setPadding(new Insets(30));
        questionBox.setStyle("-fx-background-color: white; -fx-background-radius: 20; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 20, 0, 0, 5);");
        
        // Label nomor soal (contoh: "Soal 11")
        questionNumberLabel = new Label();
        questionNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        questionNumberLabel.setTextFill(Color.web("#ff6b6b"));
        
        // Label teks pertanyaan
        questionTextLabel = new Label();
        questionTextLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
        questionTextLabel.setTextFill(Color.web("#2c3e50"));
        questionTextLabel.setWrapText(true);  // Supaya otomatis wrap kalau panjang
        
        questionBox.getChildren().addAll(questionNumberLabel, questionTextLabel);
        
        // ========== PILIHAN JAWABAN (Radio Buttons) ==========
        optionsBox = new VBox(12);
        optionsBox.setPadding(new Insets(20, 0, 0, 0));
        answerGroup = new ToggleGroup();  // Group untuk radio button (cuma bisa pilih 1)
        
        // ========== TOMBOL NEXT ==========
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        nextButton = new Button("Lanjut →");
        nextButton.setPrefWidth(150);
        nextButton.setPrefHeight(45);
        nextButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nextButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; " +
                           "-fx-background-radius: 25; -fx-cursor: hand;");
        nextButton.setDisable(true);  // Disabled dulu sampai user pilih jawaban
        
        // Hover effect - warna berubah saat mouse di atas
        nextButton.setOnMouseEntered(e -> {
            if (!nextButton.isDisabled()) {
                nextButton.setStyle("-fx-background-color: #e85555; -fx-text-fill: white; " +
                                   "-fx-background-radius: 25; -fx-cursor: hand;");
            }
        });
        
        nextButton.setOnMouseExited(e -> {
            if (!nextButton.isDisabled()) {
                nextButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; " +
                                   "-fx-background-radius: 25; -fx-cursor: hand;");
            }
        });
        
        // Action saat tombol diklik
        nextButton.setOnAction(e -> handleNextQuestion());
        
        buttonBox.getChildren().add(nextButton);
        
        // Gabungkan semua komponen
        questionContainer.getChildren().addAll(questionBox, optionsBox, buttonBox);
        
        // Wrapper dengan scroll (kalau soal panjang)
        ScrollPane scrollPane = new ScrollPane(questionContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        root.setTop(topBar);
        root.setCenter(scrollPane);
        
        // Load soal pertama
        loadQuestion();
        
        scene = new Scene(root, 800, 600);
    }
    
    // Method untuk load & tampilkan soal
    private void loadQuestion() {
        Question question = questions.get(currentQuestionIndex);
        
        // Set label nomor soal (11-16)
        questionNumberLabel.setText("Soal " + (currentQuestionIndex + 11));
        
        // Set teks pertanyaan
        questionTextLabel.setText(question.getQuestion());
        
        // Clear pilihan sebelumnya
        optionsBox.getChildren().clear();
        answerGroup = new ToggleGroup();
        
        // Buat radio button untuk setiap pilihan
        String[] options = question.getOptions();
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = new RadioButton(options[i]);
            radioButton.setToggleGroup(answerGroup);
            radioButton.setFont(Font.font("Arial", 15));
            radioButton.setPadding(new Insets(10));
            radioButton.setStyle("-fx-text-fill: #2c3e50;");
            
            // Saat radio button dipilih, enable tombol Next
            radioButton.setOnAction(e -> nextButton.setDisable(false));
            
            // Simpan skor di userData (untuk diambil nanti)
            radioButton.setUserData(question.getScore(i));
            
            optionsBox.getChildren().add(radioButton);
        }
        
        // Update progress bar
        double progress = (double) currentQuestionIndex / questions.size();
        progressBar.setProgress(progress);
        progressLabel.setText("Soal " + (currentQuestionIndex + 1) + " dari " + questions.size());
        
        // Disable tombol sampai user pilih jawaban
        nextButton.setDisable(true);
    }
    
    // Method yang dipanggil saat user klik tombol Next/Selesai
    private void handleNextQuestion() {
        // Ambil jawaban yang dipilih
        RadioButton selectedRadio = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedRadio != null) {
            // Ambil skor dari jawaban yang dipilih
            int score = (int) selectedRadio.getUserData();
            totalScore += score;  // Tambahkan ke total skor
        }
        
        // Lanjut ke soal berikutnya
        currentQuestionIndex++;
        
        if (currentQuestionIndex < questions.size()) {
            // Masih ada soal lagi
            
            // Kalau ini soal terakhir, ubah teks tombol
            if (currentQuestionIndex == questions.size() - 1) {
                nextButton.setText("Selesai ✓");
            }
            
            loadQuestion();  // Load soal berikutnya
        } else {
            // Sudah selesai semua soal
            
            // Simpan skor ke user
            user.setRelationScore(totalScore);
            
            // Tampilkan halaman hasil
            showResults();
        }
    }
    
    // Method untuk pindah ke halaman hasil
    private void showResults() {
        ResultPage resultPage = new ResultPage(stage, user);
        resultPage.show();
    }
    
    // Method untuk menampilkan scene ini
    public void show() {
        stage.setScene(scene);
    }
}

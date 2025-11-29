package MindEnvQuizApp.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuizLogic {

    private List<Question> questions;

    public QuizLogic() {
        questions = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        questions.clear();
        
        System.out.println("\n=== LOADING QUIZ ===");
        System.out.println("📖 Path: " + filePath);
        
        // Cek apakah file ada
        File file = new File(filePath);
        System.out.println("📁 File exists: " + file.exists());
        System.out.println("📄 Is file: " + file.isFile());
        System.out.println("📊 File size: " + file.length() + " bytes");
        System.out.println("🔐 Can read: " + file.canRead());

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String currentQ = null;
            String[] choices = new String[4];
            String correct = null;
            int lineNum = 0;
            int questionCount = 0;

            while ((line = br.readLine()) != null) {
                lineNum++;
                String trimmedLine = line.trim();
                
                System.out.println("Line " + lineNum + ": [" + trimmedLine + "]");

                // Skip empty lines dan comments
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    System.out.println("  → Skipped (empty/comment)");
                    continue;
                }

                // Parse pertanyaan
                if (trimmedLine.startsWith("Q:")) {
                    currentQ = trimmedLine.substring(2).trim();
                    System.out.println("  ✓ Question: " + currentQ);
                    choices = new String[4]; // Reset choices
                }
                // Parse pilihan A
                else if (trimmedLine.startsWith("A:")) {
                    choices[0] = trimmedLine.substring(2).trim();
                    System.out.println("  ✓ Choice A: " + choices[0]);
                }
                // Parse pilihan B
                else if (trimmedLine.startsWith("B:")) {
                    choices[1] = trimmedLine.substring(2).trim();
                    System.out.println("  ✓ Choice B: " + choices[1]);
                }
                // Parse pilihan C
                else if (trimmedLine.startsWith("C:")) {
                    choices[2] = trimmedLine.substring(2).trim();
                    System.out.println("  ✓ Choice C: " + choices[2]);
                }
                // Parse pilihan D
                else if (trimmedLine.startsWith("D:")) {
                    choices[3] = trimmedLine.substring(2).trim();
                    System.out.println("  ✓ Choice D: " + choices[3]);
                }
                // Parse jawaban benar
                else if (trimmedLine.startsWith("ANSWER:")) {
                    correct = trimmedLine.substring(7).trim();
                    System.out.println("  ✓ Answer: " + correct);
                    
                    // Buat Question object dan tambahkan ke list
                    if (currentQ != null && correct != null && choices[0] != null) {
                        Question q = new Question(currentQ, choices.clone(), correct);
                        questions.add(q);
                        questionCount++;
                        System.out.println("  ✅ Question #" + questionCount + " added!");
                        
                        // Reset untuk soal berikutnya
                        currentQ = null;
                        choices = new String[4];
                        correct = null;
                    } else {
                        System.out.println("  ❌ Error: Missing data");
                        System.out.println("     Q=" + currentQ + ", A=" + (choices[0] != null ? "OK" : "NULL") + ", Answer=" + correct);
                    }
                }
                else {
                    System.out.println("  ? Unknown format: " + trimmedLine);
                }
            }
            
            System.out.println("\n📊 Total soal dimuat: " + questions.size());
            System.out.println("==================\n");

        } catch (Exception e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
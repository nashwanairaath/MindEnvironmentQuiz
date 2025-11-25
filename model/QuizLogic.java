package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class QuizLogic {

    private List<Question> questions;

    public QuizLogic() {
        questions = new ArrayList<>();
    }

    public void loadFromFile(String filePath) {
        questions.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String currentQ = null;
            String[] choices = new String[4];
            String correct = null;
            int idx = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#"))
                    continue;

                if (line.startsWith("Q:")) {
                    currentQ = line.substring(2).trim();
                }
                else if (line.startsWith("A:") || line.startsWith("B:")
                        || line.startsWith("C:") || line.startsWith("D:")) {

                    choices[idx] = line.substring(2).trim();
                    idx++;
                }
                else if (line.startsWith("ANSWER:")) {
                    correct = line.substring(7).trim();

                    // tambahkan ke list
                    questions.add(new Question(currentQ, choices.clone(), correct));

                    // reset
                    idx = 0;
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading TXT file: " + e.getMessage());
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

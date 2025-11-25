package model;

public class Question {
    private String text;
    private String[] choices;
    private String correctAnswer;

    public Question(String text, String[] choices, String correctAnswer) {
        this.text = text;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public String[] getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

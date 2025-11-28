package MindEnvQuizApp.model;

public class User {
    private String name;
    private int environmentScore;
    private int mentalHealthScore;
    private int relationScore;
    private int totalScore;
    
    public User(String name) {
        this.name = name;
        this.environmentScore = 0;
        this.mentalHealthScore = 0;
        this.relationScore = 0;
        this.totalScore = 0;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getEnvironmentScore() {
        return environmentScore;
    }
    
    public int getMentalHealthScore() {
        return mentalHealthScore;
    }
    
    public int getRelationScore() {
        return relationScore;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    // Setters
    public void setEnvironmentScore(int score) {
        this.environmentScore = score;
        updateTotalScore();
    }
    
    public void setMentalHealthScore(int score) {
        this.mentalHealthScore = score;
        updateTotalScore();
    }
    
    public void setRelationScore(int score) {
        this.relationScore = score;
        updateTotalScore();
    }
    
    private void updateTotalScore() {
        this.totalScore = environmentScore + mentalHealthScore + relationScore;
    }
    
    // Analisis \hasil
    public String getAwarenessLevel() {
        if (totalScore >= 56) {
            return "Skor kamu sangat tinggi !!!!";
        } else if (totalScore >= 41) {
            return "Skor kamu tinggi !!";
        } else if (totalScore >= 26) {
            return "Skor kamu sedang";
        } else {
            return "Skor kamu rendah :(";
        }
    }
    
    public String getAwarenessDescription() {
        if (totalScore >= 56) {
            return "Keren banget! Kamu memiliki tingkat kesadaran yang tinggi akan pentingnya lingkungan dan kesehatan mental";
        } else if (totalScore >= 41) {
            return "Bagus! Kamu udah cukup paham tentang kesadaran pada hubungan lingkungan dan kesehatan mental.";
        } else if (totalScore >= 26) {
            return "Nggak buruk! Kamu masih bisa berkembang dalam kesadaran lingkungan dan mental.";
        } else {
            return "Ayo semangat! Kamu masih bisa meningkatkan kesadaran akan lingkungan dan kesehatan mental.";
        }
    }
    
    public double getPercentage() {
        return (totalScore / 64.0) * 100;
    }
    
    public String getGrade() {
        double percentage = getPercentage();
        if (percentage >= 87.5) {
            return "A";
        }
        if (percentage >= 64.0) {
            return "B";
        }
        if (percentage >= 40.6) {
            return "C";
        }
        return "D";
    }
    
    public boolean isStageCompleted(int stage) {
        switch (stage) {
            case 1:
                return environmentScore > 0;
            case 2:
                return mentalHealthScore > 0;
            case 3:
                return relationScore > 0;
            default:
                return false;
        }
    }
    
    public boolean isAllStagesCompleted() {
        return isStageCompleted(1) && isStageCompleted(2) && isStageCompleted(3);
    }
    
    public void resetScores() {
        this.environmentScore = 0;
        this.mentalHealthScore = 0;
        this.relationScore = 0;
        this.totalScore = 0;
    }
    
    public String getWeakestCategory() {
        double envNormalized = (environmentScore / 20.0) * 100;
        double mentalNormalized = (mentalHealthScore / 20.0) * 100;
        double relationNormalized = (relationScore / 24.0) * 100;
        
        if (envNormalized <= mentalNormalized && envNormalized <= relationNormalized) {
            return "Kesadaran Lingkungan";
        } else if (mentalNormalized <= relationNormalized) {
            return "Kesehatan Mental";
        } else {
            return "Keterkaitan Lingkungan & Mental";
        }
    }
    
    public String getStrongestCategory() {
        double envNormalized = (environmentScore / 20.0) * 100;
        double mentalNormalized = (mentalHealthScore / 20.0) * 100;
        double relationNormalized = (relationScore / 24.0) * 100;
        
        if (envNormalized >= mentalNormalized && envNormalized >= relationNormalized) {
            return "Kesadaran Lingkungan";
        } else if (mentalNormalized >= relationNormalized) {
            return "Kesehatan Mental";
        } else {
            return "Keterkaitan Lingkungan & Mental";
        }
    }
    
    @Override
    public String toString() {
        return String.format(
            "User: %s\n" +
            "├─ Environment Score: %d/20\n" +
            "├─ Mental Health Score: %d/20\n" +
            "├─ Relation Score: %d/24\n" +
            "├─ Total Score: %d/64 (%.1f%%)\n" +
            "├─ Awareness Level: %s\n" +
            "└─ Grade: %s",
            name, environmentScore, mentalHealthScore, relationScore, totalScore, getPercentage(), getAwarenessLevel(), getGrade()
        );
    }
}

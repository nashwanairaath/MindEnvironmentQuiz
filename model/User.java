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
    
    // Analisis & Hasil
    public String getAwarenessLevel() {
        if (totalScore >= 43) { 
            return "🌲 The Nature Seeker";
        } else if (totalScore >= 22) {
            return "🌆 The Urban Balancer";
        } else {
            return "🏠 The Comfort Zoner";
        }
    }

    public String getArchetypeDescription() {
        if (totalScore >= 43) {
            return "Kehadiran alam memberikanmu rasa tenang dan damai. Ada kenyamanan dan kehangatan ketika kamu berada di alam & ruang terbuka. Rasanya tubuh dan pikiran jadi lebih fresh !!";
        } else if (totalScore >= 22) {
            return "Kamu cukup nyaman di tengah padatnya aktivitas harian mu, tetapi kamu tetap membutuhkan sedikit healing dari alam yang membantumu menjadi lebih tenang dan relax...";
        } else {
            return "Tempat yang tenang dan sudah lama kamu kenal membuatmu merasa nyaman. Namun sesekali, cobalah untuk keluar sebentar untuk menlihat indahnya alam yang bisa menyegarkan pikiranmu.";
        }
    }    
   //quotes
    public String getAwarenessDescription() {
        if (totalScore >= 56) {
            return "\"Alam bukan tempat untuk dikunjungi. Alam adalah rumah.\"\n  – Gary Snyder";
        } else if (totalScore >= 41) {
            return "\"Lihatlah jauh ke dalam alam, dan kamu akan mengerti segalanya dengan lebih baik.\"\n  – Albert Einstein";
        } else if (totalScore >= 26) {
            return "\"Ancaman terbesar bagi planet kita adalah keyakinan bahwa orang lain yang akan menyelamatkannya.\"\n  – Robert Swan";
        } else {
            return "\"Perjalanan ribuan mil dimulai dengan satu langkah kecil. Jangan menyerah!\"\n  – Lao Tzu";
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

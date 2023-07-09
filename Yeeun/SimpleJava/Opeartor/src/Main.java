public class Main {
    public static void main(String[] args) {
        int score = 100;
        String grade = "";


        if (score >= 90) {
            grade += "A"; // GRADE = A

            if (score >= 98) { // score >= 90 && score >= 98
                grade += "+"; // GRADE = A+
            } else if (score < 94) {
                grade += "-";
            }
        } else if (score >= 80) {
            grade += "B";
            //....
        }


        // 80점 이상 > B
        // 88 > B+
        // 84 > B-

        // F
    }
}
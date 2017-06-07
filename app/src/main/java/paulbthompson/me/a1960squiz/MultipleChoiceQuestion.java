package paulbthompson.me.a1960squiz;

/**
 * Created by Paul on 6/6/2017.
 */

public class MultipleChoiceQuestion {

    public String questionText;
    public String choice1Text;
    public String choice2Text;
    public String choice3Text;
    public String choice4Text;
    public String correctChoiceText;
    private static int currentIndex;
    private static MultipleChoiceQuestion[] questionArray;

    public MultipleChoiceQuestion(String questionText, String choice1Text,
                                  String choice2Text, String choice3Text,
                                  String choice4Text, String correctChoiceText) {

        this.questionText = questionText;
        this.choice1Text = choice1Text;
        this.choice2Text = choice2Text;
        this.choice3Text = choice3Text;
        this.choice4Text = choice4Text;
        this.correctChoiceText = correctChoiceText;
    }

    public static MultipleChoiceQuestion[] createQuestionBank() {

        questionArray = new MultipleChoiceQuestion[2];

        for(int i = 0; i < 2; i++) {

            questionArray[i] = new MultipleChoiceQuestion(mQuestion[i], mChoices[i][0],
                    mChoices[i][1], mChoices[i][2], mChoices[i][3],
                    mCorrectAnswer[i]);

        }

        currentIndex = 0;
        return questionArray;
    }

    private static String mQuestion [] = {
            "Who was the father of LSD?",
            "Who assassinated Robert F. Kennedy?"
    };

    private static String mChoices [][] = {
            {"Timothy Leary", "Albert Hofmann", "Aldous Huxley", "Denis Leary"},
            {"Lee Harvey Oswald", "Joseph Stalin", "Sirhan Sirhan", "No one knows"}
    };

    private static String mCorrectAnswer [] = {
            "Albert Hofmann", "Sirhan Sirhan"
    };

    public static String getQuestion() {
        return questionArray[currentIndex].questionText;
    }

    public static String getChoice1() {
        return questionArray[currentIndex].choice1Text;
    }

    public static String getChoice2() {
        return questionArray[currentIndex].choice2Text;
    }

    public static String getChoice3() {
        return questionArray[currentIndex].choice3Text;
    }

    public static String getChoice4() {
        return questionArray[currentIndex].choice4Text;
    }

    public static String getCorrectAnswer() {
        return questionArray[currentIndex].correctChoiceText;
    }

    public static void setIndex(int x) {
        currentIndex = x;
    }

    public static int getIndex() {
        return currentIndex;
    }

}

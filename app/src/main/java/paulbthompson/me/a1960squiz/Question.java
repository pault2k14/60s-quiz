package paulbthompson.me.a1960squiz;
/**
 * Created by Paul.Thompson on 3/7/2017.
 */

public class Question {

    private int mTestResId;
    private boolean mAnswerTrue;

    public Question(int testResId, boolean answerTrue) {
        mTestResId = testResId;
        mAnswerTrue = answerTrue;

    }

    private String mQuestion [] = {
            "Who was the father of LSD?",
            "Who assassinated Robert F. Kennedy?"
    };

    private String mChoices [][] = {
            {"Timothy Leary", "Albert Hofmann", "Aldous Huxley", "Denis Leary"},
            {"Lee Harvey Oswald", "Joseph Stalin", "Sirhan Sirhan", "No one knows"}
    };

    private String mCorrectAnswer [] = {
            "Albert Hofmann", "Sirhan Sirhan"
    };

    public String getQuestion(int x) {
        return mQuestion[x];
    }

    public String getChoice1(int a) {
        return mChoices[a][0];
    }

    public String getChoice2(int a) {
        return mChoices[a][1];
    }

    public String getChoice3(int a) {
        return mChoices[a][2];
    }

    public String getChoice4(int a) {
        return mChoices[a][3];
    }

    public String getCorrectAnswer(int a) {
        return mCorrectAnswer[a];
    }





    public void setTestResId(int testResId) {
        mTestResId = testResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTestResId() {
        return mTestResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
}

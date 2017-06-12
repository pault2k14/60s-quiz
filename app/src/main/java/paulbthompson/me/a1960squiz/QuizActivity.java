package paulbthompson.me.a1960squiz;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_SCORE = "score";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mChoice1Button;
    private Button mChoice2Button;
    private Button mChoice3Button;
    private Button mChoice4Button;
    private TextView mScoreTextView;
    private TextView mQuestionTextView;
    private TextView mQuestionNumTextView;
    private QuestionBank mMultiQuestionBank = new QuestionBank();
    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Questions where you can choose more than one answer
        // TODO  - choose all that apply.
        // TODO Change First Name and Last Name to just initials
        // TODO Change question / answer storage to local nosql.
        // TODO Add spinner to user input activity
        // TODO setup database online w/ Updated datetime in question
        // TODO - and a deleted_yn field.
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCurrentScore = savedInstanceState.getInt(KEY_SCORE, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
        }
        mQuestionNumTextView = (TextView) findViewById(R.id.question_num);
        mQuestionNumTextView.setText("" + (1 + mMultiQuestionBank.getIndex()) + "/"
        + mMultiQuestionBank.getSize());
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mChoice1Button = (Button) findViewById(R.id.choice1_button);
        mChoice2Button = (Button) findViewById(R.id.choice2_button);
        mChoice3Button = (Button) findViewById(R.id.choice3_button);
        mChoice4Button = (Button) findViewById(R.id.choice4_button);

        mChoice1Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onAnswer(mChoice1Button);
            }
        });

        mChoice2Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onAnswer(mChoice2Button);
            }
        });

        mChoice3Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onAnswer(mChoice3Button);
            }
        });

        mChoice4Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onAnswer(mChoice4Button);
            }
        });

        updateQuestion();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CODE_CHEAT) {
            if(data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_SCORE, mCurrentScore);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void onAnswer(Button choiceButton) {
        checkMultiChoiceAnswer(choiceButton.getText().toString());

        if(!mMultiQuestionBank.isNextQuestion()) {
            if(isNetworkAvailable()) {
                Intent intent = UserNameInputActivity.newIntent(
                        QuizActivity.this, mCurrentScore);
                startActivity(intent);
                return;
            }
            else {
                ArrayList<ScoreItem> emptyScoreItems = new ArrayList<>();
                Intent intent = EndOfQuizActivity.newIntent(
                        QuizActivity.this, mCurrentScore, emptyScoreItems);
                startActivity(intent);
                return;
            }
        }

        mMultiQuestionBank.nextQuestion();
        updateQuestion();

    }

    private void updateQuestion() {

        mQuestionNumTextView.setText("" + (1 + mMultiQuestionBank.getIndex()) + "/"
                + mMultiQuestionBank.getSize());
        mQuestionTextView.setText(mMultiQuestionBank.getQuestionText());
        mChoice1Button.setText(mMultiQuestionBank.getChoice1Text());
        mChoice2Button.setText(mMultiQuestionBank.getChoice2Text());
        mChoice3Button.setText(mMultiQuestionBank.getChoice3Text());
        mChoice4Button.setText(mMultiQuestionBank.getChoice4Text());

    }

    private void checkMultiChoiceAnswer(String userPressed) {

        boolean answerIsCorrect = mMultiQuestionBank.getCorrectChoiceText()
                .equals(userPressed);

        Log.d(TAG, "mScoreTextView: " + mScoreTextView.getText());


        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgement_toast;
        } else {
            if(answerIsCorrect) {
                mCurrentScore = mCurrentScore + 10;
                mScoreTextView.setText(String.valueOf(mCurrentScore));
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, QuizActivity.class);
        return intent;

    }
}

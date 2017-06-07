package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class EndOfQuizActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_SCORE =
            "paulbthompson.me.a1960squiz.current_score";
    private int mCurrentScore;
    private TextView mScoreTextTextView;
    private TextView mScoreTextView;
    private Button mRestartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_quiz);

        mRestartButton = (Button) findViewById(R.id.restart_button);
        mCurrentScore = getIntent().getIntExtra(EXTRA_CURRENT_SCORE, 0);
        mScoreTextTextView = (TextView) findViewById(R.id.score_text);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mScoreTextView.setText(String.valueOf(mCurrentScore));

        mRestartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    public static Intent newIntent(Context packageContext, int currentScore) {
        Intent intent = new Intent(packageContext, EndOfQuizActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        return intent;
    }

}

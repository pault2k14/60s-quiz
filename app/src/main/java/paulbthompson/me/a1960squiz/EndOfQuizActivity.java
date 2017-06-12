package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class EndOfQuizActivity extends AppCompatActivity {

    private static final String TAG = "EndOfQuizActivity";
    private static final String EXTRA_CURRENT_SCORE =
            "paulbthompson.me.a1960squiz.current_score";
    private static final String EXTRA_FIRST_NAME =
            "paulbthompson.me.a1960squiz.first_name";
    private static final String EXTRA_LAST_NAME =
            "paulbthompson.me.a1960squiz.last_name";
    private static final String EXTRA_ALL_SCORES =
            "paulbthompson.me.a1960squiz.all_scores";
    private int mCurrentScore;
    private TextView mScoreTextView;
    private TextView mLeaderboardText;
    private Button mRestartButton;
    private ArrayList<ScoreItem> mScoreItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_quiz);

        mRestartButton = (Button) findViewById(R.id.restart_button);
        mCurrentScore = getIntent().getIntExtra(EXTRA_CURRENT_SCORE, 0);
        mScoreItems = getIntent().getParcelableArrayListExtra(EXTRA_ALL_SCORES);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mLeaderboardText = (TextView) findViewById(R.id.leaderboard_text);

        if(isNetworkAvailable()) {
            init();
        }
        else {
            mLeaderboardText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            mLeaderboardText.setText("Enable internet access to view the leaderboard.");
        }

        mRestartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = QuizActivity.newIntent(EndOfQuizActivity.this);
                startActivity(intent);
            }
        });
    }


    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" No. ");
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Score ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        stk.addView(tbrow0);
        int i = 0;
        for (ScoreItem item : mScoreItems) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + (i + 1));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(item.getFirstName() + " " + item.getLastName());
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("" + item.getScore());
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            stk.addView(tbrow);
            ++i;
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Intent newIntent(Context packageContext, int currentScore, ArrayList<ScoreItem> allScores) {
        Intent intent = new Intent(packageContext, EndOfQuizActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        intent.putParcelableArrayListExtra(EXTRA_ALL_SCORES, allScores);

        return intent;
    }

}

package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.services.dynamodbv2.model.*;

import org.w3c.dom.Text;

import java.util.List;


public class EndOfQuizActivity extends AppCompatActivity {

    private static final String TAG = "EndOfQuizActivity";
    private static final String EXTRA_CURRENT_SCORE =
            "paulbthompson.me.a1960squiz.current_score";
    private int mCurrentScore;
    private String name_first = "Paul";
    private String name_last = "Thompson";
    private TextView mScoreTextView;
    private Button mRestartButton;
    private TextView mLeaderBoardTextView;
    private DynamoDBMapper mapper;
    private PaginatedScanList<ScoreItem> scores;
    private String highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_quiz);

        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
        AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
        AmazonDynamoDBClient ddbClient = awsMobileClient.getDynamoDBClient();
        mapper = new DynamoDBMapper(ddbClient);

        mRestartButton = (Button) findViewById(R.id.restart_button);
        mCurrentScore = getIntent().getIntExtra(EXTRA_CURRENT_SCORE, 0);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mScoreTextView.setText(String.valueOf(mCurrentScore));
        mLeaderBoardTextView = (TextView) findViewById(R.id.leaderboard);

        addScore();
        getAllScores();

        mRestartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    public void addScore() {

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                ScoreItem scoreItem = new ScoreItem();
                scoreItem.setFirstName(name_first);
                scoreItem.setLastName(name_last);
                scoreItem.setScore(mCurrentScore);
                mapper.save(scoreItem);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public void getAllScores() {

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
                DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
                DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING);
                scores = mapper.scan(ScoreItem.class, dynamoDBScanExpression, config);

                scores.loadAllResults();

                Log.d(TAG, "scores.size()" + scores.size());

                for(ScoreItem scoreItem : scores) {
                    highScores += scoreItem.getFirstName() + " "
                            + scoreItem.getLastName() + " "
                            + scoreItem.getScore() + " ";
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stuff that updates ui
                        mLeaderBoardTextView.setText(highScores);
                    }
                });
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();
    }

    public static Intent newIntent(Context packageContext, int currentScore) {
        Intent intent = new Intent(packageContext, EndOfQuizActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        return intent;
    }

}

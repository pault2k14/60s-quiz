package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class UserNameInputActivity extends AppCompatActivity {

    private static final String TAG = "UserNameInputActivity";
    private static final String EXTRA_CURRENT_SCORE =
            "paulbthompson.me.a1960squiz.current_score";
    private int mCurrentScore;
    private Button mSubmitButton;
    private EditText mFirstName;
    private EditText mLastName;
    private String mFirstNameString;
    private String mLastNameString;
    private DynamoDBMapper mapper;
    private PaginatedScanList<ScoreItem> scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_input);

        mCurrentScore = getIntent().getIntExtra(EXTRA_CURRENT_SCORE, 0);

        mSubmitButton = (Button)findViewById(R.id.submitButton);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mFirstName = (EditText)findViewById(R.id.firstName);
                mFirstNameString = mFirstName.getText().toString();
                mLastName = (EditText)findViewById(R.id.lastName);
                mLastNameString = mLastName.getText().toString();
                updateAndgetAllScores();
            }
        });
    }


    public void updateAndgetAllScores() {

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here

                if(mFirstNameString == null) mFirstNameString = "Unknown";
                if(mLastNameString == null) mLastNameString = "Unknown";

                ScoreItem scoreItemToSave = new ScoreItem();
                scoreItemToSave.setFirstName(mFirstNameString);
                scoreItemToSave.setLastName(mLastNameString);
                scoreItemToSave.setScore(mCurrentScore);
                mapper.save(scoreItemToSave);

                DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
                DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.PaginationLoadingStrategy.EAGER_LOADING);
                scores = mapper.scan(ScoreItem.class, dynamoDBScanExpression, config);
                scores.loadAllResults();
            }
        };

        Thread mythread = new Thread(runnable);
        mythread.start();

        try {
            mythread.join();
        } catch(InterruptedException e) {
            Log.d(TAG, "Interrupted trying to rejoin threads!");
        }

        ArrayList<ScoreItem> scoresArrayList = new ArrayList<>(Arrays.asList((ScoreItem[]) scores.toArray()));
        Intent intent = EndOfQuizActivity.newIntent(UserNameInputActivity.this,
                mCurrentScore,
                mFirstNameString,
                mLastNameString,
                scoresArrayList);
        startActivity(intent);
    }

    public static Intent newIntent(Context packageContext, int currentScore) {
        Intent intent = new Intent(packageContext, UserNameInputActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        return intent;

    }
}

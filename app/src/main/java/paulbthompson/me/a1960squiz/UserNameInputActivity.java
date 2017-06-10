package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapperConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

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

        AWSMobileClient.initializeMobileClientIfNecessary(getApplicationContext());
        AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();
        AmazonDynamoDBClient ddbClient = awsMobileClient.getDynamoDBClient();
        mapper = new DynamoDBMapper(ddbClient);

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

        if(mFirstNameString == null || mFirstNameString.equals(""))
            mFirstNameString = "Unknown";
        if(mLastNameString == null || mLastNameString.equals(""))
            mLastNameString = "Unknown";

        if(mFirstNameString.equals("Unknown") && mLastNameString.equals("Unknown")) {
            mLastNameString = " ";
        }

        Runnable runnable = new Runnable() {
            public void run() {
                //DynamoDB calls go here
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

        int maxIndex = 0;
        int maxScore = 0;
        int arraylength = scores.size();
        ArrayList<ScoreItem> scoreItemsArrayList = new ArrayList<>();
        ArrayList<ScoreItem> scoresArray = new ArrayList<>();
        scoresArray.addAll(scores);

        for(int x = 0; x < arraylength; ++x) {
            for (int i = 0; i < scoresArray.size(); ++i) {
                if (scoresArray.get(i).getScore() > maxScore) {
                    maxIndex = i;
                    maxScore = scoresArray.get(i).getScore();
                }
            }
            scoreItemsArrayList.add(scoresArray.remove(maxIndex));
            maxIndex = 0;
            maxScore = 0;
        }

        Intent intent = EndOfQuizActivity.newIntent(UserNameInputActivity.this,
                mCurrentScore,
                scoreItemsArrayList);
        startActivity(intent);
    }

    public static Intent newIntent(Context packageContext, int currentScore) {
        Intent intent = new Intent(packageContext, UserNameInputActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        return intent;

    }
}

package paulbthompson.me.a1960squiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

                Intent intent = EndOfQuizActivity.newIntent(UserNameInputActivity.this, mCurrentScore, mFirstNameString, mLastNameString);
                startActivity(intent);
            }
        });


    }

    public static Intent newIntent(Context packageContext, int currentScore) {
        Intent intent = new Intent(packageContext, UserNameInputActivity.class);
        intent.putExtra(EXTRA_CURRENT_SCORE, currentScore);
        return intent;

    }
}

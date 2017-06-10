package paulbthompson.me.a1960squiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            synchronized(this){
                wait(3000);
            }
        }
        catch(InterruptedException ex){
        }

        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}

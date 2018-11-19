package pe.area51.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public final static String PARAM_WELCOME_MESSAGE = "pe.area51.myfirstapp.WelcomeMessage";

    private String welcomeMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final TextView textViewWelcomeMessage = findViewById(R.id.textViewWelcomeMessage);
        final Intent intentThatStartedThisActivity = getIntent();
        welcomeMessage = intentThatStartedThisActivity.getStringExtra(PARAM_WELCOME_MESSAGE);
        textViewWelcomeMessage.setText(welcomeMessage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionShareWelcomeMessage) {
            shareWelcomeMessage();
            return true;
        }
        return false;
    }

    private void shareWelcomeMessage() {
        ShareCompat
                .IntentBuilder
                .from(this)
                .setText(welcomeMessage)
                .setType("text/plain")
                .startChooser();
    }
}

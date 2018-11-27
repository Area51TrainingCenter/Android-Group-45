package pe.area51.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    public final static String PARAM_WELCOME_MESSAGE = "pe.area51.myfirstapp.WelcomeMessage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final TextView textViewWelcomeMessage = findViewById(R.id.textViewWelcomeMessage);
        final Intent intentThatStartedThisActivity = getIntent();
        final String welcomeMessage = intentThatStartedThisActivity.getStringExtra(PARAM_WELCOME_MESSAGE);
        textViewWelcomeMessage.setText(welcomeMessage);
    }
}

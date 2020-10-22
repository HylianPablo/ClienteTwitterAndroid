package com.example.yamba;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final String TAG = "MainActivity";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editStatus = (EditText) findViewById(R.id.editStatus);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey("KEY")
                .setOAuthConsumerSecret("KEY")
                .setOAuthAccessToken("KEY")
                .setOAuthAccessTokenSecret("KEY");
        TwitterFactory factory = new TwitterFactory(builder.build());
        twitter = factory.getInstance();

        textCount = (TextView) findViewById(R.id.textCount);
        textCount.setText(Integer.toString(280));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener((TextWatcher) this);
    }

    @Override
    public void onClick(View v) {
        String status = editStatus.getText().toString();
        Log.d(TAG,"onClicked");

        new PostTask(v).execute(status);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int count = 280 - s.length();
        textCount.setText(Integer.toString(count));
        textCount.setTextColor(Color.GREEN);
        if (count < 10)
            textCount.setTextColor(Color.rgb(255,213,0));
        if (count < 0)
            textCount.setTextColor(Color.RED);
    }

    private final class PostTask extends AsyncTask<String,Void,String> {
        View view;
        PostTask(View v) {view=v;}
        @Override
        protected String doInBackground(String... strings) {
            try{
                twitter.updateStatus(strings[0]);
                return "Tweet enviado correctamente";
            }catch(TwitterException ex){
                Log.e(TAG,"Fallo en el envío");
                ex.printStackTrace();
                return "Fallo en el envío";
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Snackbar.make(view,result,Snackbar.LENGTH_LONG).show();
        }


    }
}

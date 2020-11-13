package com.example.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RefreshService extends IntentService {

    public static final String ACTION_FETCH_TIMELINE = "yamba.action.FETCH_TIMELINE";
    public static final String ACTION_OTHER = "yamba.action.OTHER";

    private static final String TAG = "RefreshService";

    static final int DELAY = 30000; // medio minuto
    private boolean runFlag = false;

    public RefreshService() {
        super("RefreshService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d( TAG, "onCreated" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.runFlag=false;
        Log.d( TAG, "onDestroyed" );
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d( TAG, "onHandleIntent..." );
        if (intent != null) {
            final String action = intent.getAction();
            Log.d( TAG, "Action read: " + action );

            if (ACTION_FETCH_TIMELINE.equals(action)) {
                Log.d( TAG, "ACTION_FETCH_TIMELINE selected" );
                handleActionFetchTimeline();
            } else  {
                Log.e( TAG, "Action not yet implemented!" );
            }
        }
    }
    private void handleActionFetchTimeline() {
        this.runFlag = true;

        SharedPreferences prefs = getDefaultSharedPreferences(this);
        String accesstoken = prefs.getString("accesstoken", "1126862183974014976-243ApY4dYRne1PFuCu7nDZDBnB4pMP");
        String accesstokensecret = prefs.getString("accesstokensecret", "YUkqxGnJjgFqEfM8gR0t02GAMjjAKoK2AniqlEENGwoJI");

        System.out.println("HOLAAAAAAAAAAAAAAAAA"+accesstoken);


        while (runFlag) {
            Log.d(TAG, "Updater running");
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey("OxvoeCK1YDgO4u7nxlHrnoAQD")
                        .setOAuthConsumerSecret("SF0tpacg1SzS54BdzyPPwD3Fq6XTalRgp1HiQcGt6oTQLszTnw")
                        .setOAuthAccessToken(accesstoken)
                        .setOAuthAccessTokenSecret(accesstokensecret);
                TwitterFactory factory = new TwitterFactory(builder.build());
                Twitter twitter = factory.getInstance();

                try {
                    List<Status> timeline = twitter.getUserTimeline();

                    // Imprimimos las actualizaciones en el log
                    for (Status status : timeline) {
                        Log.d(TAG, String.format("%s: %s", status.getUser().getName(),
                                status.getText()));
                    }
                }
                catch (TwitterException e) {
                    Log.e(TAG, "Failed to fetch the timeline", e);
                }

                Log.d(TAG, "Updater ran");
                Thread.sleep(DELAY);
            }
            catch (InterruptedException e) {
                runFlag = false;
            }
        }
    }
}

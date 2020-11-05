package com.example.yamba;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment implements View.OnClickListener, TextWatcher {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "StatusFragment";
    EditText editStatus;
    Button buttonTweet;
    Twitter twitter;
    TextView textCount;

    public StatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance(String param1, String param2) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_status, container, false);

        editStatus = (EditText) vista.findViewById(R.id.editStatus);
        buttonTweet = (Button) vista.findViewById(R.id.buttonTweet);
        buttonTweet.setOnClickListener(this);

        textCount = (TextView) vista.findViewById(R.id.textCount);
        textCount.setText(Integer.toString(280));
        textCount.setTextColor(Color.GREEN);
        editStatus.addTextChangedListener((TextWatcher) this);
        return vista;
    }

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

        PostTask(View v) {
            view = v;
        }

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

            String accesstoken = prefs.getString("accesstoken", "");
            String accesstokensecret = prefs.getString("accesstokensecret", "");

            // Comprobar si el nombre de usuario o el password están vacíos.
            // Si lo están, indicarlo en el SnackBar y redirigir al usuario a Settings
            if (TextUtils.isEmpty(accesstoken) || TextUtils.isEmpty(accesstokensecret)) {
                getActivity().startActivity(new Intent(getActivity(), SettingsActivity.class));
                return "Por favor, actualiza tu nombre de usuario y tu contraseña";
            }

            Log.d( TAG, "AccessToken read: " + accesstoken );
            Log.d( TAG, "AccessTokenSecret read: " + accesstokensecret );

            // Inicialización
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey("OxvoeCK1YDgO4u7nxlHrnoAQD")
                    .setOAuthConsumerSecret("SF0tpacg1SzS54BdzyPPwD3Fq6XTalRgp1HiQcGt6oTQLszTnw")
                    .setOAuthAccessToken(accesstoken)
                    .setOAuthAccessTokenSecret(accesstokensecret);
            TwitterFactory factory = new TwitterFactory(builder.build());
            twitter = factory.getInstance();
            try {
                twitter.updateStatus(strings[0]);
                return "Tweet enviado correctamente";
            } catch (TwitterException ex) {
                Log.e(TAG, "Fallo en el envío");
                ex.printStackTrace();
                return "Fallo en el envío";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Snackbar.make(view, result, Snackbar.LENGTH_LONG).show();
        }
    }
}
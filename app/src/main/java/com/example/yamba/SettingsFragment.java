package com.example.yamba;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String TAG = "SettingsFragment";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        EditTextPreference accesstoken = findPreference("accesstoken");
        EditTextPreference accesstokensecret = findPreference("accesstokensecret");

        assert accesstoken != null;
        accesstoken.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Log.d(TAG, "AccessToken changed: " + newValue);
                        return true;
                    }
                });

        assert accesstokensecret != null;
        accesstokensecret.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Log.d(TAG, "AccessTokenSecret changed: " + newValue);
                        return true;
                    }
                });

        accesstokensecret.setOnBindEditTextListener(
                new EditTextPreference.OnBindEditTextListener() {
                    @Override
                    public void onBindEditText(@NonNull EditText editText) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT |
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                });

    }
}
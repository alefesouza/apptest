package net.aloogle.apps.teste;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceActivity;

@SuppressLint("NewApi")
public class SettingsActivity extends PreferenceActivity {
	@SuppressWarnings("unused")

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

		Preference button = findPreference("buttonok");
		button.setOnPreferenceClickListener(new Preference.
			OnPreferenceClickListener() {
			@Override
			public boolean
			onPreferenceClick(Preference preference) {
				finish();
				return true;
			}
		});
	}
}

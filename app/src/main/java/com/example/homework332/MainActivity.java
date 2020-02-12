package com.example.homework332;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
	private static final String LANG_KEY = "app_language";

	private CheckBox checkBoxExample;
	private Button exampleButton;
	private Spinner languagesSpinner;
	private Button applyButton;

	private SharedPreferences sharedPref;
	private int currentLanguageIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		sharedPref = getPreferences(MODE_PRIVATE);

		if (!sharedPref.contains(LANG_KEY)) { // on first creation
			currentLanguageIndex = getSystemLanguageIndex();
			sharedPref.edit().putInt(LANG_KEY, currentLanguageIndex).apply();
		} else { // on any consequent creation
			currentLanguageIndex = sharedPref.getInt(LANG_KEY, 0);
			sharedPref.edit().remove(LANG_KEY).apply(); // don't save for next app launch
		}

		checkBoxExample.setOnCheckedChangeListener((v, isOn) -> exampleButton.setEnabled(isOn));

		exampleButton.setOnClickListener(v -> Toast.makeText(this,
				R.string.button_toast_text, Toast.LENGTH_SHORT).show());

		ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				getResources().getStringArray(R.array.languages));
		languagesSpinner.setAdapter(languagesAdapter);
		languagesSpinner.setSelection(currentLanguageIndex, true);

		applyButton.setOnClickListener(v -> apply());
	}

	private void initViews() {
		checkBoxExample = findViewById(R.id.checkBoxExample);
		exampleButton = findViewById(R.id.exampleButton);
		languagesSpinner = findViewById(R.id.languagesSpinner);
		applyButton = findViewById(R.id.applyButton);
	}

	private int getSystemLanguageIndex() {
		String[] codes = getResources().getStringArray(R.array.languages_codes);
		String lang = Locale.getDefault().getLanguage();
		int result = -1;
		for (int i = 0; i < codes.length; i++)
			if (codes[i].equals(lang)) {
				result = i;
				break;
			}
		return result == -1 ? 0 : result; // if not found, set to 0
	}

	private void apply() {
		int pos = languagesSpinner.getSelectedItemPosition();
		if (pos == currentLanguageIndex)
			return;
		sharedPref.edit().putInt(LANG_KEY, pos).apply();

		String code = getResources().getStringArray(R.array.languages_codes)[pos];
		Configuration config = new Configuration();
		config.setLocale(new Locale(code));
		DisplayMetrics m = getBaseContext().getResources().getDisplayMetrics();
		getResources().updateConfiguration(config, m);

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}

package com.example.homework332;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import static android.content.res.Resources.getSystem;

public class MainActivity extends AppCompatActivity {
	private static int currentLanguageIndex = -1;

	private CheckBox checkBoxExample;
	private Button exampleButton;
	private Spinner languagesSpinner;
	private Button applyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

		if (currentLanguageIndex == -1) { // on first launch, find system language index
			String[] codes = getResources().getStringArray(R.array.languages_codes);
			String lang = Locale.getDefault().getLanguage();
			for (int i = 0; i < codes.length; i++)
				if (codes[i].equals(lang)) {
					currentLanguageIndex = i;
					break;
				}
			if (currentLanguageIndex == -1)
				currentLanguageIndex = 0; // set to default (en) if index not found
		}

		checkBoxExample.setOnCheckedChangeListener((v, isOn) -> exampleButton.setEnabled(isOn));

		exampleButton.setOnClickListener(v -> Toast.makeText(this,
				R.string.button_toast_text, Toast.LENGTH_SHORT).show());

		ArrayAdapter<String> languagesAdapter = new ArrayAdapter<>(this,
				android.R.layout.simple_spinner_dropdown_item,
				getResources().getStringArray(R.array.languages));
		languagesSpinner.setAdapter(languagesAdapter);
		languagesSpinner.setSelection(currentLanguageIndex, true);

		applyButton.setOnClickListener(v -> {
			int pos = languagesSpinner.getSelectedItemPosition();
			if (pos == currentLanguageIndex)
				return;
			currentLanguageIndex = pos;

			String code = getResources().getStringArray(R.array.languages_codes)[pos];
			Configuration config = new Configuration();
			config.setLocale(new Locale(code));
			DisplayMetrics m = getBaseContext().getResources().getDisplayMetrics();
			getResources().updateConfiguration(config, m);

			startActivity(new Intent(this, MainActivity.class));
			finish();
		});
	}

	private void initViews() {
		checkBoxExample = findViewById(R.id.checkBoxExample);
		exampleButton = findViewById(R.id.exampleButton);
		languagesSpinner = findViewById(R.id.languagesSpinner);
		applyButton = findViewById(R.id.applyButton);
	}
}

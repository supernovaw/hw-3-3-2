package com.example.homework332;

import android.app.Activity;
import android.content.Intent;

public class ThemeSwitcher {
	private static int selectedMargins;

	private static final int[] MARGINS_IDS = {R.style.AppTheme,
			R.style.AppTheme_MarginsMedium, R.style.AppTheme_MarginsLarge};

	public static int getSelectedMargins() {
		return selectedMargins;
	}

	public static void changeThemeMargins(Activity activity, int theme) {
		if (theme == selectedMargins)
			return;
		selectedMargins = theme;
		activity.startActivity(new Intent(activity, activity.getClass()));
		activity.finish();
	}

	public static void applyTheme(Activity creating) {
		creating.setTheme(MARGINS_IDS[selectedMargins]);
	}
}

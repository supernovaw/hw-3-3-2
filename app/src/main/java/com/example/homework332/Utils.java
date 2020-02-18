package com.example.homework332;

import android.app.Activity;
import android.content.Intent;

public class Utils {
	public final static int[] THEME_IDS = {R.style.AppThemeBlack,
			R.style.AppThemeGreen, R.style.AppThemeBlue, R.style.AppThemeRed};
	private static int currentTheme;

	/**
	 * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
	 */
	public static void changeToTheme(Activity activity, int theme) {
		if (currentTheme == theme)
			return;

		currentTheme = theme;
		activity.finish();
		activity.startActivity(new Intent(activity, activity.getClass()));
	}

	/**
	 * Set the theme of the activity, according to the configuration.
	 */
	public static void onActivityCreateSetTheme(Activity activity) {
		activity.setTheme(THEME_IDS[currentTheme]);
	}

	public static int getCurrentTheme() {
		return currentTheme;
	}
}

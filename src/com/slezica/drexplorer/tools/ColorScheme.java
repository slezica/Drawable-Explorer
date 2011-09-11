package com.slezica.drexplorer.tools;

public class ColorScheme {
	int mForegroundColor, mBackgroundColor;
	String mName;

	public ColorScheme(String name, int bgColor, int fgColor) {
		mName = name;
		mBackgroundColor = bgColor;
		mForegroundColor = fgColor;
	}
	
	public int getBackgroundColor() {
		return mBackgroundColor;
	}
	
	public int getForegroundColor() {
		return mForegroundColor;
	}
	
	public String getName() {
		return mName;
	}
}

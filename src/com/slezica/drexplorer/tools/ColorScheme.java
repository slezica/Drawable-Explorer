/*
 * Copyright (C) 2011 Santiago Lezica
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

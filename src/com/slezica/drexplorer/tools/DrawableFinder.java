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

import java.lang.reflect.Field;

public class DrawableFinder {

	private static DrawableResource[] sResourceIds;

	private DrawableFinder() {}

	public static DrawableResource[] getResourceIds() {
		if (sResourceIds == null) {
			Class<?> androidDrawables = android.R.drawable.class;
			Field[] fields = androidDrawables.getDeclaredFields();

			sResourceIds = new DrawableResource[fields.length];

			for (int i = 0; i < fields.length; i++) {
				try {
					Field field = fields[i];
					sResourceIds[i] = new DrawableResource((Integer) field.get(null), field.getName());

				} catch (Exception e) {}
			}
		}

		return sResourceIds;
	}
}

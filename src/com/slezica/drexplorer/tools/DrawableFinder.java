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

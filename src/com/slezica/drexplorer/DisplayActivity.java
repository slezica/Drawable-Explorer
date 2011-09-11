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

package com.slezica.drexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity {

	public static interface EXTRAS {
		static final String NAME   = "name",
							RES_ID = "res_id";
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		TextView  name = (TextView)  findViewById(R.id.name);
		ImageView icon = (ImageView) findViewById(R.id.image);
		
		Bundle extras = getIntent().getExtras();
		
		name.setText(extras.getString(EXTRAS.NAME));
		icon.setImageResource(extras.getInt(EXTRAS.RES_ID));
	}
	
}

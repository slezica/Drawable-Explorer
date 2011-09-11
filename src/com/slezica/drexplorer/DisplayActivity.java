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

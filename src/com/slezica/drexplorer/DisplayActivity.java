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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.slezica.drexplorer.tools.DrawableResource;

public class DisplayActivity extends Activity {

	public static interface EXTRAS {
		static final String NAME   = "name",
							RES_ID = "res_id";
	}
	
	private TextView  mName;
	private ImageView mIcon;
	
	private Button mSave, mSend;
	
	DrawableResource mResource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		
		mName = (TextView)  findViewById(R.id.name);
		mIcon = (ImageView) findViewById(R.id.image);
		
		mSave = (Button) findViewById(R.id.save);
		mSend = (Button) findViewById(R.id.send);
		
		mSave.setOnClickListener(mSaveListener);
		mSend.setOnClickListener(mSendListener);
		
		Bundle extras = getIntent().getExtras();
		
		mResource = new DrawableResource(
	        extras.getInt(EXTRAS.RES_ID),
	        extras.getString(EXTRAS.NAME)
        );
		
		mName.setText(mResource.getName());
		mIcon.setImageResource(mResource.getId());
	}
	
	final OnClickListener mSaveListener = new OnClickListener() {
        public void onClick(View view) {
            try {
                File file = save(mResource.getName(), generateBitmap());
                toast("Saved drawable to:\n" + file.getPath());
                
            } catch (IOException e) {
                toast("Failed to save: " + e.getMessage());
            }
        }
	};
	
	final OnClickListener mSendListener = new OnClickListener() {
        public void onClick(View view) {
            try {
                send(generateBitmap());
                
            } catch (IOException e) {
                toast("Failed to send: " + e.getMessage());
            }
        }
    };
	
	private void toast(String message) {
	    Toast toast = Toast.makeText(
                DisplayActivity.this,
                message,
                Toast.LENGTH_SHORT
            );
            
        toast.setGravity(Gravity.BOTTOM, 0, mSend.getHeight());
        toast.show();
	}
	
	private Bitmap generateBitmap() {
	    mIcon.buildDrawingCache();
	    Bitmap bm = mIcon.getDrawingCache();
	   
	    return bm;
	}
	
	private File save(String name, Bitmap bitmap) throws IOException {
        File file = new File(getDirectory(), name + ".png");
        FileOutputStream fOut = new FileOutputStream(file);
        
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        
        fOut.flush();
        fOut.close();
        
        return file;
    }
    
    private void send(Bitmap bitmap) throws IOException {
        String name = bitmap.toString() + "@" + System.currentTimeMillis();
        
        File file = save(name, bitmap);
        
        Intent intent = new Intent(Intent.ACTION_SEND);  
        intent.setType("image/png"); 
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        
        startActivity(Intent.createChooser(intent, "Send this with..."));
    }
    
    private File getDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory(), "drexplorer_saved");
        
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        return dir;
    }
}

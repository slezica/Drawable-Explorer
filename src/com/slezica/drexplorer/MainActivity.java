package com.slezica.drexplorer;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.slezica.drexplorer.tools.ColorScheme;
import com.slezica.drexplorer.tools.DrawableFinder;
import com.slezica.drexplorer.tools.DrawableResource;
import com.slezica.tools.widget.FilterableAdapter;

public class MainActivity extends Activity {
	
	private static final ColorScheme[] sColors = {
		new ColorScheme("Black", Color.BLACK, Color.WHITE),
		new ColorScheme("White", Color.WHITE, Color.BLACK)
	};
	
	private ColorScheme mSelectedScheme;
	
	private ListView mList;
	private EditText mFilter;
	private Spinner  mBackground;
		
	private DrawableAdapter mAdapter;
	private LayoutInflater mInflater;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mInflater = LayoutInflater.from(MainActivity.this);
		
		mList       = (ListView) findViewById(R.id.drawable_list);
		mFilter     = (EditText) findViewById(R.id.filter);
		mBackground = (Spinner)  findViewById(R.id.background);
		
		mList.setAdapter(mAdapter = new DrawableAdapter());
		mList.setOnItemClickListener(mOnDrawableClick);
		
		mFilter.addTextChangedListener(mFilterWatcher);
		
		mBackground.setAdapter(new ColorAdapter());
		mBackground.setOnItemSelectedListener(mOnColorSelected);
		
		selectBackgroundColor(sColors[0]);
		
		mList.requestFocus();
	}
	
	public void selectBackgroundColor(ColorScheme colorDesc) {
		mSelectedScheme = colorDesc;
		mList.setBackgroundColor(colorDesc.getBackgroundColor());	
		mAdapter.notifyDataSetChanged();
	}
	
	final TextWatcher mFilterWatcher = new TextWatcher() {

		@Override public void afterTextChanged(Editable s) {}
		@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mAdapter.getFilter().filter(s);
		}
	};
	
	final OnItemSelectedListener mOnColorSelected = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			selectBackgroundColor(sColors[position]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			selectBackgroundColor(sColors[0]);
		}
	};
	
	final OnItemClickListener mOnDrawableClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			DrawableResource dR = mAdapter.getItem(position);
			
			Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
				intent.putExtra(DisplayActivity.EXTRAS.NAME, dR.getName());
				intent.putExtra(DisplayActivity.EXTRAS.RES_ID, dR.getId());
				
		    startActivity(intent);
		}
	};
	
	private class ColorAdapter extends ArrayAdapter<ColorScheme> {
		public ColorAdapter() {
			super(MainActivity.this, 0, sColors);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ColorScheme colorScheme = getItem(position);
			
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.color_list_item, parent, false);
			
			view = convertView;
			
			ImageView image = (ImageView) view.findViewById(R.id.image);
			image.setBackgroundColor(colorScheme.getBackgroundColor());
			
			return view;
		}
		
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View view;
			ColorScheme colorScheme = getItem(position);
			
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.color_dropdown_item, parent, false);
			
			view = convertView;
			
			TextView  name = (TextView)  view.findViewById(R.id.item_label);
			ImageView icon = (ImageView) view.findViewById(R.id.item_icon);

			name.setText(colorScheme.getName());
			name.setTextColor(Color.BLACK);
			
			icon.setBackgroundColor(colorScheme.getBackgroundColor());
			
			return view;
		}
	}
	
	private class DrawableAdapter extends FilterableAdapter<DrawableResource, String> {
		public DrawableAdapter() {
			super(MainActivity.this, Arrays.asList(DrawableFinder.getResourceIds()));
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			DrawableResource dR = getItem(position);
			
			if (convertView == null)
				convertView = mInflater.inflate(R.layout.drawable_list_item, parent, false);
			
			view = convertView;
			
			TextView  name = (TextView)  view.findViewById(R.id.item_label);
			ImageView icon = (ImageView) view.findViewById(R.id.item_icon);

			name.setText(dR.getName());
			icon.setImageResource(dR.getId());

			name.setTextColor(mSelectedScheme.getForegroundColor());
			view.setBackgroundColor(mSelectedScheme.getBackgroundColor());
			
			return view;
		}
		
		@Override
	    protected String prepareFilter(CharSequence seq) {
	        return seq.toString().trim().toLowerCase();
	    }

	    @Override
	    protected boolean passesFilter(DrawableResource dR, String constraint) {
	        return (dR.getName().contains(constraint));
	    }
	}
}
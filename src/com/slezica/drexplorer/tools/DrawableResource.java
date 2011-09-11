package com.slezica.drexplorer.tools;

public class DrawableResource {
	int mId;
	String mName;
	
	public DrawableResource(int id, String name) {
		mId = id;
		mName = name;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getName() {
		return mName;
	}
}

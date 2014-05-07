package com.example.horizontalview;

import android.graphics.drawable.Drawable;

public class Emotions {
	String value;
	String icon;
	String identifier;
	Drawable emotion;
	
	public Drawable getEmotion() {
		return emotion;
	}
	public void setEmotion(Drawable emotion) {
		this.emotion = emotion;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "Emotions [value=" + value + ", icon=" + icon + "]";
	}
	
}

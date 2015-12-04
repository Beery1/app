package com.beery.appinfo;

import android.graphics.drawable.Drawable;

public class Info {
	public Drawable icon;
	public String packageName;
	public String appName;
	public String sourceDir;
	public String MD5;

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String mD5) {
		MD5 = mD5;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	@Override
	public String toString() {
		return "AppInfo[icon=" + icon + "packgeName=" + packageName
				+ "appName=" + appName + "sourceDir=" + sourceDir + "MD5="
				+ MD5 + "]";
	}
}

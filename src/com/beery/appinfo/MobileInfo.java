package com.beery.appinfo;

import java.io.File;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

public class MobileInfo {
	String mobileType;// 手机型号
	String imsi;// imsi
	int sdkVersion;// sdk版本
	String systemVersion;// 系统版本
	String imei;// imei
	String macAdress;// mac地址
	String providerName;// 运营商
	String externalStorageTotalSize;// 外部存储总大小
	String externalStorageAvailableSize;// 外部存储剩余大小
	String internalStorageTotalSize;// 机身存储总大小
	String internalStorageAvailableSize;// 机身存储剩余大小
	String cpuType;// cpu型号
	String manufacturer;// 手机制造商
	String hardwareSerialNumber;// 机身序列号
	String iccID;// iccID

	private TelephonyManager mTelephonyManager;
	private WifiManager mWifiManager;
	private Context context;

	public MobileInfo(Context context) {
		this.context = context;
		mTelephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
	}

	public String getMobileType() {
		mobileType = android.os.Build.MODEL;
		return mobileType;
	}

	public String getImsi() {
		imsi = mTelephonyManager.getSubscriberId();
		return imsi;
	}

	public int getSdkVersion() {
		sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		return sdkVersion;
	}

	public String getSystemVersion() {
		systemVersion = android.os.Build.VERSION.RELEASE;
		return systemVersion;
	}

	public String getImei() {
		imei = mTelephonyManager.getDeviceId();
		return imei;
	}

	public String getMacAdress() {
		WifiInfo info = mWifiManager.getConnectionInfo();
		macAdress = info.getMacAddress();
		return macAdress;
	}

	public String getExternalStorageTotalSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(context, blockSize * totalBlocks);
	}

	public String getExternalStorageAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(context, blockSize * availableBlocks);
	}

	public String getInternalStorageTotalSize() {
		{
			StatFs sf = new StatFs(context.getCacheDir().getAbsolutePath());
			long blockSize = sf.getBlockSize();
			long totalBlocks = sf.getBlockCount();
			return Formatter.formatFileSize(context, blockSize * totalBlocks);
		}
	}

	public String getInternalStorageAvailableSize() {
		{
			StatFs sf = new StatFs(Environment.getDataDirectory().getPath());
			long blockSize = sf.getBlockSize();
			long totalBlocks = sf.getAvailableBlocks();
			return Formatter.formatFileSize(context, blockSize * totalBlocks);
		}
	}

	public String getCpuType() {
		cpuType = android.os.Build.CPU_ABI;
		return cpuType;
	}

	public String getManufacturer() {
		manufacturer = android.os.Build.MANUFACTURER;
		return manufacturer;

	}

	public String getHardwareSerialNumber() {
		hardwareSerialNumber = android.os.Build.SERIAL;
		return hardwareSerialNumber;

	}

	public String getIccID() {
		iccID = mTelephonyManager.getSimSerialNumber();
		return iccID;
	}

	@Override
	public String toString() {
		return "MobileInfo=[" + " mobileType=" + getMobileType() + " imsi="
				+ getImsi() + " sdkVersion=" + getSdkVersion()
				+ " systemVersion=" + getSystemVersion() + " imei=" + getImei()
				+ " macAdress=" + getMacAdress() + " externalTotalStorage="
				+ getExternalStorageTotalSize()
				+ " externalStorageAvailableSize="
				+ getExternalStorageAvailableSize() + " cpuType="
				+ getCpuType() + " Manufacturer=" + getManufacturer()
				+ " HardwareSerialNumber=" + getHardwareSerialNumber()
				+ " IccID=" + getIccID() + "]";
	}
}

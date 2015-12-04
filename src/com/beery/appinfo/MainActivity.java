package com.beery.appinfo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView mListView;
	private Info mInfo;
	private PackageManager pm;
	private String TAG = "MainActivity";
	private MobileInfo mobileInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mobileInfo = new MobileInfo(getApplicationContext());
		pm = this.getPackageManager();
		// mListView.setAdapter(new MyAdpter(this, getInfoList()));
		TextView textView = (TextView) findViewById(R.id.tv_main_show);
		textView.setText("手机检测中，请勿断开");

		// ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		// imageView
		// .setImageBitmap(Utils
		// .base64ToImage("rqTU1P2p44zzrfwUpuKQnPZ/Bv4D+nMjBurC0WUAAAAASUVORK5CYII="));

		new Thread(new Runnable() {

			@Override
			public void run() {
				String string1 = Utils.parseListToXml(getInfoList());

				Utils.writeToSdcard(string1, "sjjc.xml");
				Log.d(TAG, "写入sjjc.xml");
				String string2 = stringToXml();

				Utils.writeToSdcard(string2, "info.xml");

				Log.d(TAG, "写入info.xml");

				Log.d(TAG, mobileInfo.toString());

				Utils.writeToSdcard("0", "ok.txt");
				Log.d(TAG, "写入ok.txt");
				Log.d(TAG, "写入完毕");
				// String path = "/sdcard/hjrich";
				// Utils.base64ToImage("", path);
			}
		}).start();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	public String stringToXml() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sBuffer.append("<nodes>");
		sBuffer.append("<node mobileType='" + mobileInfo.getMobileType()
				+ "' imsi='" + mobileInfo.getImsi() + "' sdkVersion='"
				+ mobileInfo.getSdkVersion() + "' systemVersion='"
				+ mobileInfo.getSystemVersion() + "' imei='"
				+ mobileInfo.getImei() + "' macAdress='"
				+ mobileInfo.getMacAdress() + "' externalStorageTotalSize='"
				+ mobileInfo.getExternalStorageTotalSize()
				+ "' externalStorageAvailableSize='"
				+ mobileInfo.getExternalStorageAvailableSize() + "' cpuType='"
				+ mobileInfo.getCpuType() + "' manufacturer='"
				+ mobileInfo.getManufacturer() + "' internalStorageTotalSize='"
				+ mobileInfo.getInternalStorageTotalSize()
				+ "' internalStorageAvailableSize='"
				+ mobileInfo.getInternalStorageAvailableSize()
				+ "' HardwareSerialNumber='"
				+ mobileInfo.getHardwareSerialNumber() + "' iccID='"
				+ mobileInfo.getIccID()

				+ "'>" + "</node>" + "</nodes>");
		// sBuffer.append("ok");
		return sBuffer.toString();

	}

	private List<Info> getInfoList() {
		List<Info> infos = new ArrayList<Info>();
		List<ApplicationInfo> applicationInfos = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		for (ApplicationInfo info : applicationInfos) {
			mInfo = new Info();
			Drawable icon = info.loadIcon(pm);
			mInfo.setIcon(icon);
			String appName = (String) info.loadLabel(pm);
			mInfo.setAppName(appName);
			String packageName = info.packageName;
			mInfo.setPackageName(packageName);
			String sourceDir = info.sourceDir;
			mInfo.setSourceDir(sourceDir);
			String MD5 = getSingInfo(packageName);
			mInfo.setMD5(MD5);

			infos.add(mInfo);
		}
		System.out.println(infos.toString());
		return infos;

	}

	public String getSingInfo(String packageName) {
		try {
			PackageInfo packageInfo = pm.getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);
			Signature[] signs = packageInfo.signatures;
			Signature sign = signs[0];
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sign.toByteArray());
			byte[] digest = md.digest();
			String res = toHexString(digest);

			Log.d(TAG + "=====++++  ", res + "  ======");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);

	}

	/*
	 * 
	 * Converts a byte array to hex string
	 */

	private String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();
		int len = block.length;
		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}

	// public void parseSignature(byte[] signature) {
	// try {
	// CertificateFactory certFactory = CertificateFactory
	// .getInstance("X.509");
	// X509Certificate cert = (X509Certificate) certFactory
	// .generateCertificate(new ByteArrayInputStream(signature));
	// String pubKey = cert.getPublicKey().toString();
	// String signNumber = cert.getSerialNumber().toString();
	// System.out.println("signName:" + cert.getSigAlgName());
	// System.out.println("pubKey:" + pubKey);
	// System.out.println("signNumber:" + signNumber);
	// System.out.println("subjectDN:" + cert.getSubjectDN().toString());
	// } catch (CertificateException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public void getSingInfo2(String packageName) {
	// try {
	// PackageInfo packageInfo = getPackageManager().getPackageInfo(
	// packageName, PackageManager.GET_SIGNATURES);
	// Signature[] signs = packageInfo.signatures;
	// Signature sign = signs[0];
	// parseSignature(sign.toByteArray());
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}

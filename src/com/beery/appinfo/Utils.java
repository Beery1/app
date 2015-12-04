package com.beery.appinfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class Utils {
	private static String s = "data:image/png;base64,";// Base64转码后所需的头

	public static String parseListToXml(List<Info> infos) {
		StringBuffer sBuffer = new StringBuffer();
		if (infos != null && infos.size() > 0) {
			sBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sBuffer.append("<nodes>");
			for (int i = 0; i < infos.size(); i++) {
				Info info = infos.get(i);
				sBuffer.append("<node iconId='" + String.format("%04d", i)
						+ "' iconContent='" + Encode(info.getIcon())
						+ "' packageName='" + info.getPackageName()
						+ "' AppName='" + info.getAppName() + "' sourceDir='"
						+ info.getSourceDir() + "' MD5='" + info.getMD5()
						+ "'>" + "</node>");
			}
			sBuffer.append("</nodes>");
		}
		return sBuffer.toString();

	}

	public static void writeToSdcard(String xmlFile, String fileName) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "hjrichtmp");

		Log.d("+++++++", Environment.getExternalStorageDirectory()
				+ File.separator + "hjrichtmp");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (!file.exists()) {
				file.mkdir();
			}
			File file2 = new File(file, fileName);
			if (!file2.exists()) {
				try {
					file2.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(file2);
				outputStream.write(xmlFile.getBytes());
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void deleteFile(File file) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				}
				// 如果它是一个目录
				else if (file.isDirectory()) {
					// 声明目录下所有的文件 files[];
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
			}
		}
	}

	public static Bitmap drawableToBitmap(Drawable icon) {
		// 首先将drawable转换成bitmap
		Bitmap bitmap = Bitmap.createBitmap(

		icon.getIntrinsicWidth(),

		icon.getIntrinsicHeight(),

		icon.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

		: Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(bitmap);

		icon.setBounds(0, 0, icon.getIntrinsicWidth(),
				icon.getIntrinsicHeight());

		icon.draw(canvas);

		return bitmap;
	}

	public static void storeInSD(Bitmap bitmap, String fileName) {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "hjrichtmp");
		if (!file.exists()) {
			file.mkdir();
		}
		File imageFile = new File(file, fileName);
		try {
			imageFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(imageFile);
			if (bitmap != null) {

				bitmap.compress(CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
			} else {

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 将Drawable encode成base64
	public static String Encode(Drawable drawable) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		drawableToBitmap(drawable).compress(CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();

		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println("======" + encoder.encode(bytes));
		return encoder.encode(bytes);
	}

	public static Bitmap stringtoBitmap(String string) {

		// 将字符串转换成Bitmap类型

		Bitmap bitmap = null;

		try {

			byte[] bitmapArray;

			bitmapArray = Base64.decode(string, Base64.DEFAULT);

			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,

			bitmapArray.length);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return bitmap;

	}

	public static Bitmap base64ToImage(String base64) {// 对字节数组字符串进行Base64解码并生成图片
		Bitmap bitmap = null;

		if (base64 == null) { // 图像数据为空
			return null;
		}

		byte[] bitmapArray = null;
		BASE64Decoder decoder = new BASE64Decoder();

		try {
			bitmapArray = decoder.decodeBuffer(base64);
			for (int i = 0; i < bitmapArray.length; ++i) {
				if (bitmapArray[i] < 0) {// 调整异常数据
					bitmapArray[i] += 256;
				}
			}
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}

	/*
	 * 通过遍历获取到的Info的Icon保存到sdcard
	 */
	public static void saveBitmapToSdcard(List<Info> infos) {
		// File file = new File(Environment.getExternalStorageDirectory()
		// + File.separator + "hjrichtmp");
		// if (file.exists()) {
		//
		// Utils.deleteFile(file);
		// }
		if (infos != null && infos.size() > 0) {
			for (int i = 0; i < infos.size(); i++) {
				Info info = infos.get(i);
				Utils.storeInSD(Utils.drawableToBitmap(info.getIcon()),
						String.format("%04d", i) + ".jpg");
			}
		}
	}
}

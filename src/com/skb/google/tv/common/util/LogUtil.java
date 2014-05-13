package com.skb.google.tv.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class LogUtil {
	public static int mLogLevel = Log.VERBOSE;

	private static boolean mIsFileWrite;
	private static boolean mIsPrintTime;
	private static FileWriter mFileWriter;

	private static SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US);
	private static Date currentDate = new Date();

	public static void initLogger(int level, boolean isFileWrite) {
		initLogger(level, isFileWrite, true);
	}

	public static void initLogger(int level, boolean isFileWrite, boolean isPrintTime) {
		mLogLevel = level;
		mIsFileWrite = isFileWrite;
		mIsPrintTime = isPrintTime;

		if (mIsFileWrite) {
			prepareWrite();
		}

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, final Throwable ex) {
				// if (false == mIsFileWrite) {
				// prepareWrite();
				// }
				error("UncaughtException", "", ex);
				// try {
				// mFileWriter.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }

				// new Thread() {
				// @Override
				// public void run() {
				// Looper.prepare();
				// AndroidUtil.showToast(STBGlobal.getInstance(), "일시적인 장애로 다시 시작합니다.");
				// Looper.loop();
				//
				// }
				// }.start();
				// try {
				// Thread.sleep(2500);
				// } catch (InterruptedException e) {
				// }

				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(10);
			}
		});
	}

	private static void prepareWrite() {
		// mIsFileWrite = true;
		// String dirPath = SSMUtil.getRootPath(ePATH_TYPE.PATH_TYPE_RUN);
		// String fileName = dirPath + "/UILog.log";
		// File file = new File(fileName);
		// try {
		// if (!file.exists()) {
		// file.createNewFile();
		// }
		// mFileWriter = new FileWriter(file, true);
		// } catch (IOException e) {
		// error("LogUtil", "File Create ERROR", e);
		// }
	}

	public static void debug(String tag, String msg) {
		if (mLogLevel <= Log.DEBUG && tag != null && msg != null) {
			if (false == mIsPrintTime) {
				Log.d(tag, msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][DEBUG] " + msg);
				}
			} else {
				String currentTime = getCurrentTime();
				Log.d(tag, currentTime + ": " + msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][DEBUG] " + currentTime + ": " + msg);
				}
			}
		}
	}

	public static void info(String tag, String msg) {
		if (mLogLevel <= Log.INFO && tag != null && msg != null) {
			if (false == mIsPrintTime) {
				Log.i(tag, msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][INFO] " + msg);
				}
			} else {
				String currentTime = getCurrentTime();
				Log.i(tag, currentTime + ": " + msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][INFO] " + currentTime + ": " + msg);
				}
			}
		}
	}

	public static void error(String tag, String msg) {
		if (mLogLevel <= Log.ERROR && tag != null && msg != null) {
			if (false == mIsPrintTime) {
				Log.e(tag, msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][ERROR] " + msg);
				}
			} else {
				String currentTime = getCurrentTime();
				Log.e(tag, currentTime + ": " + msg);
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][ERROR] " + currentTime + ": " + msg);
				}
			}
		}
	}

	public static void error(String tag, String msg, Throwable e) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream print = new PrintStream(out);
		e.printStackTrace(print);

		if (mLogLevel <= Log.ERROR && tag != null && msg != null) {
			if (false == mIsPrintTime) {
				Log.e(tag, msg + out.toString());
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][ERROR] " + msg + out.toString());
				}
			} else {
				String currentTime = getCurrentTime();
				Log.e(tag, currentTime + ": " + msg + out.toString());
				if (mIsFileWrite) {
					writeLogFile("[" + tag + "][ERROR] " + currentTime + ": " + msg + out.toString());
				}
			}
		}
	}

	public static void time(String msg) {
		Log.i("HOMEUITIME", msg);
	}

	private static void writeLogFile(String log) {
		// if (mFileWriter != null && mIsFileWrite) {
		// try {
		// mFileWriter.write(log + "\r\n");
		// } catch (IOException e) {
		// }
		// }
	}

	private static String getDateText() {
		StringBuffer sb = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		sb.append(calendar.get(Calendar.YEAR) + "-");
		sb.append((calendar.get(Calendar.MONTH) + 1) + "-");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}

	private static String getCurrentTime() {
		currentDate.setTime(System.currentTimeMillis());
		String currentTime = mSimpleDateFormat.format(currentDate);
		return currentTime;
	}
}

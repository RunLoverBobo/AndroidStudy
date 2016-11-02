package com.example.mygraduation_s.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	// 判断移动网络是否已连接
	public static boolean isMobileConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileInfo != null && mobileInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}

}

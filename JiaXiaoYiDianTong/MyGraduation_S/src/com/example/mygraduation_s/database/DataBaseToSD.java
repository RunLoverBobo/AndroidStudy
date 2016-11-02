package com.example.mygraduation_s.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.mygraduation_s.config.QuestionConfig;

public class DataBaseToSD {

	private Context mContext;

	public DataBaseToSD(Context context) {
		this.mContext = context;
	}

	// �ж�SD���Ƿ����
	public static boolean isMounted() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	// ��raw�µ����ݿ�д��SD��
	public void WriteToSD() {
		// ��SD���ѹ���
		if (isMounted()) {
			// ����SD��·�������ݿ��ļ��洢
			File file = new File(QuestionConfig.FILE_PATH);
			// �����·��û�б�������,�򴴽���·��
			if (!file.exists()) {
				file.mkdirs();
			}
			File file_db = new File(QuestionConfig.DB_FILE_PATH);
			// ��������ڶ�Ӧ·�������ݿ��ļ�,��д��
			if (!file_db.exists()) {
				InputStream is = this.mContext.getResources().openRawResource(
						com.example.mygraduation_s.R.raw.question_bank);
				try {
					FileOutputStream fos = new FileOutputStream(
							QuestionConfig.DB_FILE_PATH);
					byte[] data = new byte[2048];
					int length = 0;
					while ((length = is.read(data)) != -1) {
						fos.write(data, 0, length);
					}
					is.close();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			Toast.makeText(mContext, "����SD��û�й���", Toast.LENGTH_LONG).show();
		}
	}
}

package com.example.mygraduation_s.config;

import java.io.File;

import android.os.Environment;

public class QuestionConfig {

	// ��DBHelperʹ��
	public static final String DB_NAME = "question_bank.db";// �ļ�����
	public static final String TABLE_NAME = "question";// ����
	public static final int DB_VERSION = 1;// �汾��

	//
	public static String FILE_PATH = Environment.getExternalStorageDirectory()
			+ File.separator + "question_bank";

	// ��raw�µ����ݿ�д��SD��ʱ�����·��
	public static final String DB_FILE_PATH = FILE_PATH + File.separator
			+ "question_bank.db";

}

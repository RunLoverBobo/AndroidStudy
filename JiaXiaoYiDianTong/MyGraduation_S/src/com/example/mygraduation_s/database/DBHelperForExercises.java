package com.example.mygraduation_s.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mygraduation_s.config.QuestionConfig;
import com.example.mygraduation_s.javabean.QuestionBean;

public class DBHelperForExercises {

	private static SQLiteDatabase db;
	private Context mContext;

	public DBHelperForExercises(Context context) {
		this.mContext = context;
	}

	// ��������
	public void openConn() {

		if (db == null) {
			DataBaseToSD dbtsd = new DataBaseToSD(mContext);
			// ��raw�µ����ݿ�д��SD��
			dbtsd.WriteToSD();
			// ��SD���е����ݿⲢ����db
			db = SQLiteDatabase.openOrCreateDatabase(
					QuestionConfig.DB_FILE_PATH, null);
		}

	}

	// �ر�����
	public void closeConn() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	// ��ø������͵ķ����Ĺ�������
	public void baseCursor(List<QuestionBean> list, String where) {
		Cursor cursor = db.query(QuestionConfig.TABLE_NAME, new String[] {
				"_id", "mexam_type", "question", "optionA", "optionB",
				"optionC", "optionD", "answer", "q_type", "image" }, where,
				null, null, null, null);
		while (cursor.moveToNext()) {
			QuestionBean question = new QuestionBean(cursor.getInt(0),
					cursor.getString(2), cursor.getString(3),
					cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getInt(7), cursor.getInt(8),
					cursor.getBlob(9));
			list.add(question);
		}
		cursor.close();
		closeConn();
	}

	// ȫ�����⹩˳����ϵ,�����ϵʹ��
	public List<QuestionBean> orderedExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();

		String where = null;
		baseCursor(list, where);

		return list;
	}

	// ����������ȫ��ʻ����
	public List<QuestionBean> badWeatherExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'%��%'";
		baseCursor(list, where);
		return list;
	}

	// ������ȫ��ʻ����
	public List<QuestionBean> safeDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'��ʻ����%'";
		baseCursor(list, where);
		return list;
	}

	// ҹ���г�ע������
	public List<QuestionBean> eveningDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'%ҹ��%'";
		baseCursor(list, where);
		return list;
	}

	// �������·�ϰ�ȫ��ʻ����
	public List<QuestionBean> whileDrivingExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'%�г���%'";
		baseCursor(list, where);
		return list;
	}

	// �Զ���
	public List<QuestionBean> autoExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'%�Զ���%'";
		baseCursor(list, where);
		return list;
	}

	// ������
	public List<QuestionBean> motorExercise() {
		List<QuestionBean> list = new ArrayList<QuestionBean>();
		// ��������
		openConn();
		String where = "question like " + "'%������%'";
		baseCursor(list, where);
		return list;
	}

}

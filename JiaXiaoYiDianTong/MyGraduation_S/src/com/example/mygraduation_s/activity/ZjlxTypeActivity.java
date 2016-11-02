package com.example.mygraduation_s.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.CollectionDao;
import com.example.mygraduation_s.database.CtjlbDao;
import com.example.mygraduation_s.database.DBHelperForExercises;
import com.example.mygraduation_s.javabean.QuestionBean;

public class ZjlxTypeActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView tv_title;
	private RadioButton rb_option_a;
	private RadioButton rb_option_b;
	private RadioButton rb_option_c;
	private RadioButton rb_option_d;
	private ImageView iv_picture;
	private Button btn_previous;
	private Button btn_collect;
	private Button btn_check;
	private Button btn_next;
	private int currentQuestionIndex = 0;
	private ArrayList<QuestionBean> questionList;
	private int sizeOfList;
	private RadioGroup rg_base;
	private int option = 0;
	private QuestionBean question;
	private DBHelperForExercises dbHelperForExercises;
	private boolean hasCollected = false;
	private CollectionDao collectionDao;
	private CtjlbDao ctjlbDao;
	private ImageView ivFavor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sxlx);

		// ��ʼ���ؼ�
		initView();
		// ���������ղص����ݿ������Ķ���
		collectionDao = new CollectionDao(this);
		// �������ڽ������ղؽ������¼�������ݿ������Ķ���
		ctjlbDao = new CtjlbDao(this);
		dbHelperForExercises = new DBHelperForExercises(this);
		// ���questionList
		getQuestionList();

		// ������
		setData();

	}

	private void getQuestionList() {

		Intent intent = getIntent();
		int index = intent.getIntExtra("index", 0);
		switch (index) {
		case 0:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.badWeatherExercise();
			break;
		case 1:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.safeDrivingExercise();
			break;
		case 2:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.eveningDrivingExercise();
			break;
		case 3:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.whileDrivingExercise();
			break;
		case 4:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.autoExercise();
			break;
		case 5:
			questionList = (ArrayList<QuestionBean>) dbHelperForExercises
					.motorExercise();
			break;

		default:
			break;
		}
		sizeOfList = questionList.size();
	}

	// ������
	private void setData() {

		question = questionList.get(currentQuestionIndex);
		tv_title.setText((currentQuestionIndex + 1) + "." + question.getTitle());
		int q_type = question.getQ_type();
		// �еĿ������ĸ�ѡ�����Щ����ֻ������ѡ��
		if (q_type == 1) {
			rb_option_a.setText(question.getOptionA());
			rb_option_b.setText(question.getOptionB());
			rb_option_c.setText(question.getOptionC());
			rb_option_d.setText(question.getOptionD());
		} else if (q_type == 0) {
			rb_option_a.setText(question.getOptionA());
			rb_option_b.setText(question.getOptionB());
			rb_option_c.setVisibility(RadioButton.INVISIBLE);
			rb_option_d.setVisibility(RadioButton.INVISIBLE);
		}
		if (question.getPicture() != null) {
			byte[] data = question.getPicture();
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			// ����ͼƬΪ�ɼ�
			iv_picture.setVisibility(ImageView.VISIBLE);
			iv_picture.setImageBitmap(bitmap);
		}
		// ��RadioButton���ü���
		rg_base.setOnCheckedChangeListener(this);
	}

	// ��ʼ���ؼ�
	private void initView() {

		tv_title = (TextView) findViewById(R.id.tv_title);
		rg_base = (RadioGroup) findViewById(R.id.rg_base);
		rb_option_a = (RadioButton) findViewById(R.id.rb_option_a);
		rb_option_b = (RadioButton) findViewById(R.id.rb_option_b);
		rb_option_c = (RadioButton) findViewById(R.id.rb_option_c);
		rb_option_d = (RadioButton) findViewById(R.id.rb_option_d);
		iv_picture = (ImageView) findViewById(R.id.iv_picture);
		btn_previous = (Button) findViewById(R.id.btn_previous);
		btn_collect = (Button) findViewById(R.id.btn_collect);
		btn_check = (Button) findViewById(R.id.btn_check);
		btn_next = (Button) findViewById(R.id.btn_next);
		ivFavor = (ImageView) findViewById(R.id.iv_favor);
		btn_previous.setOnClickListener(this);
		btn_collect.setOnClickListener(this);
		btn_check.setOnClickListener(this);
		btn_next.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_previous:
			if (currentQuestionIndex > 0) {
				currentQuestionIndex--;
				// ��ͼƬ����Ϊ���ɼ�
				iv_picture.setVisibility(ImageView.INVISIBLE);
				// ��ѡ�ѡ�е�״̬���
				rb_option_a.setChecked(false);
				rb_option_b.setChecked(false);
				rb_option_c.setChecked(false);
				rb_option_d.setChecked(false);
				setData();
			} else {
				Toast.makeText(ZjlxTypeActivity.this, "�Ѿ���ͷ��!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_collect:
			if (!hasCollected) {
				hasCollected = true;
				ivFavor.setImageResource(R.drawable.icon_collect);
				ContentValues values = new ContentValues();
				values.put("q_type", question.getQ_type());
				values.put("title", question.getTitle());
				values.put("optionA", question.getOptionA());
				values.put("optionB", question.getOptionB());
				values.put("optionC", question.getOptionC());
				values.put("optionD", question.getOptionD());
				values.put("rightAnswer", question.getAnswer());
				values.put("picture", question.getPicture());
				collectionDao.insert("collection", values);
				Toast.makeText(ZjlxTypeActivity.this, "�ղسɹ�",
						Toast.LENGTH_SHORT).show();
			} else {
				hasCollected = false;
				ivFavor.setImageResource(R.drawable.ic_favou);
				collectionDao.delete("collection", "title=?",
						new String[] { question.getTitle() });
				Toast.makeText(ZjlxTypeActivity.this, "��ȡ���ղ�",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_check:

			if (option == 0) {
				Toast.makeText(ZjlxTypeActivity.this, "��ûѡ�ţ�",
						Toast.LENGTH_LONG).show();
			} else if (option == question.getAnswer()) {
				Toast.makeText(ZjlxTypeActivity.this, "���������ش���ȷ��",
						Toast.LENGTH_LONG).show();
			} else {
				String showAnswer = null;
				switch (question.getAnswer()) {
				case 1:
					showAnswer = "A";
					break;
				case 2:
					showAnswer = "B";
					break;
				case 3:
					showAnswer = "C";
					break;
				case 4:
					showAnswer = "D";
					break;
				default:
					break;
				}
				Toast.makeText(ZjlxTypeActivity.this,
						"�ش������ȷ��Ϊ" + showAnswer, Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btn_next:
			if (currentQuestionIndex < sizeOfList - 1) {
				currentQuestionIndex++;
				// ��ͼƬ����Ϊ���ɼ�
				iv_picture.setVisibility(ImageView.INVISIBLE);
				// ��ѡ�ѡ�е�״̬���
				rb_option_a.setChecked(false);
				rb_option_b.setChecked(false);
				rb_option_c.setChecked(false);
				rb_option_d.setChecked(false);
				setData();
			} else {
				Toast.makeText(ZjlxTypeActivity.this, "���Ѿ������һ������",
						Toast.LENGTH_SHORT).show();
			}
			if (option != question.getAnswer()) {
				ContentValues values = new ContentValues();
				values.put("q_type", question.getQ_type());
				values.put("title", question.getTitle());
				values.put("optionA", question.getOptionA());
				values.put("optionB", question.getOptionB());
				values.put("optionC", question.getOptionC());
				values.put("optionD", question.getOptionD());
				values.put("rightAnswer", question.getAnswer());
				values.put("picture", question.getPicture());
				ctjlbDao.insert("ctjlb", values);
			}
			break;

		default:
			break;
		}
	}

	// RadioGroup�ļ����¼�
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == rb_option_a.getId()) {
			option = 1;
		} else if (checkedId == rb_option_b.getId()) {
			option = 2;
		} else if (checkedId == rb_option_c.getId()) {
			option = 3;
		} else if (checkedId == rb_option_d.getId()) {
			option = 4;
		}
	}

}

package com.example.mygraduation_s.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.DBHelperForExercises;
import com.example.mygraduation_s.database.ErrorDao;
import com.example.mygraduation_s.javabean.QuestionBean;

public class MnksActivity extends Activity implements OnCheckedChangeListener,
		OnChronometerTickListener {

	private TextView tv_title;
	private RadioButton rb_option_a;
	private RadioButton rb_option_b;
	private RadioButton rb_option_c;
	private RadioButton rb_option_d;
	private ImageView iv_picture;
	private Button btn_previous;
	private Button btn_submit;
	private Button btn_next;
	private int currentQuestionIndex = 0;// 0-724��
	private ArrayList<QuestionBean> questionList;
	private RadioGroup rg_base;
	private int option = 0;
	private QuestionBean question;
	private Chronometer chronometer;
	private int minute;
	private int second;
	private List<QuestionBean> randomList;
	private int score = 0;// �ܷ�
	private ErrorDao dao;
	private AlertDialog.Builder builder;
	private AlertDialog.Builder selectDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mnks);

		dao = new ErrorDao(this);
		// ��ʼ���ؼ�
		initView();

		// �������ݿ⽫�����100�������װ��������
		getRandomList();

		// ������
		setData();
		setChronometer();

	}

	private void getRandomList() {

		DBHelperForExercises dbHelperForExercises = new DBHelperForExercises(
				this);
		questionList = (ArrayList<QuestionBean>) dbHelperForExercises
				.orderedExercise();
		Intent intent = getIntent();
		int[] ids = intent.getIntArrayExtra("ids");
		randomList = new ArrayList<QuestionBean>();
		for (int i = 0; i < ids.length; i++) {
			randomList.add(questionList.get(ids[i]));
		}

	}

	private void setChronometer() {
		minute = 40;
		second = 0;
		chronometer = (Chronometer) findViewById(R.id.chro_exam);
		chronometer.setText(nowtime());
		chronometer.start();
		chronometer.setOnChronometerTickListener(this);
		chronometer.setOnClickListener(new MyOnClickListener());
	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {

		second--;
		if (second == -1) {
			minute--;
			second = 59;
		}
		if (minute == -1) {
			chronometer.stop();
		}
		if (minute < 5) {
			chronometer.setTextColor(Color.RED);
			chronometer.setText(nowtime());
		} else {
			chronometer.setTextColor(Color.GREEN);
			chronometer.setText(nowtime());
		}
	}

	private String nowtime() {
		if (second < 10) {
			return (minute + ":0" + second);
		} else {
			return (minute + ":" + second);
		}
	}

	// ������
	private void setData() {

		question = randomList.get(currentQuestionIndex);
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
		btn_submit = (Button) findViewById(R.id.btn_submit);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_previous.setOnClickListener(new MyOnClickListener());
		btn_submit.setOnClickListener(new MyOnClickListener());
		btn_next.setOnClickListener(new MyOnClickListener());

	}

	// �����Ƿ�ȷ�Ͻ���ĶԻ���
	private void initAlertDialog() {

		selectDialog = new AlertDialog.Builder(MnksActivity.this);
		selectDialog.setTitle("��ʾ");
		selectDialog.setMessage("��ѡ������õ��ķ�������?");
		selectDialog.setPositiveButton("�ȼ���", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(MnksActivity.this,
						ScoreActivity.class);
				String grade = "";
				if (score <= 100 && score >= 85) {
					grade = "����";
				} else if (score < 85 && score >= 75) {
					grade = "����";
				} else if (score < 75 && score >= 60) {
					grade = "����";
				} else if (score < 60 && score >= 0) {
					grade = "������";
				}
				intent.putExtra("type", 1);
				intent.putExtra("grade", grade);
				startActivity(intent);
			}

		});
		selectDialog.setNegativeButton("�ٷ���", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(MnksActivity.this,
						ScoreActivity.class);
				intent.putExtra("type", 2);
				intent.putExtra("score", score);
				startActivity(intent);
			}
		});

		builder = new AlertDialog.Builder(MnksActivity.this);
		builder.setTitle("��ʾ");
		builder.setMessage("�Ƿ�ȷ������?");
		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				selectDialog.show();
			}

		});
		builder.setNegativeButton("ȡ��", null);
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

	class MyOnClickListener implements android.view.View.OnClickListener {

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
					Toast.makeText(MnksActivity.this, "�Ѿ���ͷ��!",
							Toast.LENGTH_SHORT).show();
				}
				dao.deleteAfterPressPrevious(currentQuestionIndex + 1);
				break;
			case R.id.btn_submit:

				initAlertDialog();
				builder.show();
				if (option == question.getAnswer()) {
					score++;
				} else {
					ContentValues values = new ContentValues();
					values.put("indexQuestion", currentQuestionIndex);
					values.put("q_type", question.getQ_type());
					values.put("title", question.getTitle());
					values.put("optionA", question.getOptionA());
					values.put("optionB", question.getOptionB());
					values.put("optionC", question.getOptionC());
					values.put("optionD", question.getOptionD());
					values.put("rightAnswer", question.getAnswer());
					values.put("wrongAnswer", option);
					values.put("picture", question.getPicture());
					dao.insert("exam", values);
				}
				break;
			case R.id.btn_next:

				if (option == question.getAnswer()) {
					score++;
				} else {
					ContentValues values = new ContentValues();
					values.put("indexQuestion", currentQuestionIndex);
					values.put("q_type", question.getQ_type());
					values.put("title", question.getTitle());
					values.put("optionA", question.getOptionA());
					values.put("optionB", question.getOptionB());
					values.put("optionC", question.getOptionC());
					values.put("optionD", question.getOptionD());
					values.put("rightAnswer", question.getAnswer());
					values.put("wrongAnswer", option);
					values.put("picture", question.getPicture());
					dao.insert("exam", values);
				}
				if (currentQuestionIndex < 99) {
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
					Toast.makeText(MnksActivity.this, "���Ѿ������һ������",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}
	}

}

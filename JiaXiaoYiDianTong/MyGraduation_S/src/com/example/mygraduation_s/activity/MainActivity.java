package com.example.mygraduation_s.activity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.DBHelperForExercises;
import com.example.mygraduation_s.javabean.QuestionBean;
import com.example.mygraduation_s.util.NetworkUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends Activity {

	private AlertDialog.Builder exitDialog;
	private AlertDialog.Builder mobileDialog;
	private Button btnSxlx;
	private Button btnSjlx;
	private Button btnZjlx;
	private Button btnLaw;
	private LinearLayout llSubjectOne;
	private LinearLayout llSubjectTwo;
	private LinearLayout llSubjectThree;
	private LinearLayout llSubjectFour;
	private LinearLayout llLogin;
	private LinearLayout llExit;
	private Button btnMnks;
	private ProgressDialog pd;
	private int totalNum = 725;// ���ݿ��п��������
	private int testNum = 100;// ��Ҫ��ȡ������Ŀ
	private int normalSpan = totalNum / testNum;// ÿһ������ȡ�Ŀ�����ռ������
	private int bigSpanNum = totalNum % testNum;// ���ڿ��������������ܱ���ȡ������������������Щ�������������������һ���ñ�����Ϊ��Щ�����������
	private int smallSpanNum = testNum - bigSpanNum;// �����������Ŀ
	private int[] ids = new int[100];
	private Random random = new Random();
	private SharedPreferences sp;
	private Editor editor;
	private Button btnCollection;
	private Button btnCtjlb;
	private Button btnPoint;
	private Button btnCheats;
	private TextView tvUsername;
	private String usernameFromLogin;
	private String usernameFromRegister;
	private LinearLayout llCircleOfFriends;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			// ��ɳ�ȡ�����Ŀ����װ��������ϵĲ���
			case 1:
				pd.dismiss();
				Intent intent_mnks = new Intent(MainActivity.this,
						MnksActivity.class);
				intent_mnks.putExtra("ids", ids);
				startActivity(intent_mnks);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = getSharedPreferences("isFirstOrNot", MODE_PRIVATE);
		final boolean isFirst = sp.getBoolean("isFirst", true);
		// ��ʼ���໬�˵�
		initMenu();
		// ��ʼ���Ի���
		initAlertDialog();
		boolean mobileConnected = NetworkUtil
				.isMobileConnected(MainActivity.this);
		if (isFirst && mobileConnected) {
			mobileDialog.show();
			editor = sp.edit();
			editor.putBoolean("isFirst", false);
			editor.commit();
		}

		// ��ʼ���ؼ�
		initView();

		Intent intent = getIntent();
		usernameFromLogin = intent.getStringExtra("usernameFromLogin");
		usernameFromRegister = intent.getStringExtra("usernameFromRegister");
		if (usernameFromLogin != null) {
			tvUsername.setText(usernameFromLogin);
		} else if (usernameFromRegister != null) {
			tvUsername.setText(usernameFromRegister);
		}

	}

	// ��ʼ���ؼ�
	private void initView() {
		btnSxlx = (Button) findViewById(R.id.btn_sxlx);
		btnSjlx = (Button) findViewById(R.id.btn_sjlx);
		btnZjlx = (Button) findViewById(R.id.btn_zjlx);
		btnMnks = (Button) findViewById(R.id.btn_mnks);
		btnLaw = (Button) findViewById(R.id.btn_law);
		btnCollection = (Button) findViewById(R.id.btn_collection);
		llSubjectOne = (LinearLayout) findViewById(R.id.ll_subjectOne);
		llSubjectTwo = (LinearLayout) findViewById(R.id.ll_subjectTwo);
		llSubjectThree = (LinearLayout) findViewById(R.id.ll_subjectThree);
		llSubjectFour = (LinearLayout) findViewById(R.id.ll_subjectFour);
		llLogin = (LinearLayout) findViewById(R.id.ll_login);
		llExit = (LinearLayout) findViewById(R.id.ll_exit);
		btnCtjlb = (Button) findViewById(R.id.btn_ctjlb);
		btnPoint = (Button) findViewById(R.id.btn_point);
		btnCheats = (Button) findViewById(R.id.btn_cheats);
		tvUsername = (TextView) findViewById(R.id.tv_sliding_menu_username);
		llCircleOfFriends = (LinearLayout) findViewById(R.id.ll_circle_of_friends);
		btnLaw.setOnClickListener(new MyOnClickListener());
		llSubjectOne.setOnClickListener(new MyOnClickListener());
		llSubjectTwo.setOnClickListener(new MyOnClickListener());
		llSubjectThree.setOnClickListener(new MyOnClickListener());
		llSubjectFour.setOnClickListener(new MyOnClickListener());
		llLogin.setOnClickListener(new MyOnClickListener());
		llExit.setOnClickListener(new MyOnClickListener());
		btnSxlx.setOnClickListener(new MyOnClickListener());
		btnSjlx.setOnClickListener(new MyOnClickListener());
		btnZjlx.setOnClickListener(new MyOnClickListener());
		btnMnks.setOnClickListener(new MyOnClickListener());
		btnCollection.setOnClickListener(new MyOnClickListener());
		btnCtjlb.setOnClickListener(new MyOnClickListener());
		btnPoint.setOnClickListener(new MyOnClickListener());
		btnCheats.setOnClickListener(new MyOnClickListener());
		llCircleOfFriends.setOnClickListener(new MyOnClickListener());
	}

	// ��ʼ���໬�˵�
	private void initMenu() {
		SlidingMenu menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMode(SlidingMenu.LEFT);
		menu.setMenu(R.layout.slidingmenu);
		menu.setBehindWidth(600);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	}

	// ��ʼ���Ի���
	private void initAlertDialog() {
		// �˳��Ի���
		exitDialog = new AlertDialog.Builder(MainActivity.this);
		exitDialog.setTitle("��ʾ");
		exitDialog.setMessage("�Ƿ�ȷ���˳�?");
		exitDialog.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				editor = sp.edit();
				editor.putBoolean("isFirst", true);
				editor.commit();
				finish();
			}
		});
		exitDialog.setNegativeButton("ȡ��", null);
		// �ƶ�������ʾ�Ի���
		mobileDialog = new AlertDialog.Builder(MainActivity.this);
		mobileDialog.setTitle("��ܰ��ʾ");
		mobileDialog.setMessage("��������������������Ƿ��л�ΪWIFIģʽ");
		mobileDialog.setPositiveButton("�����л�", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ����WIFI���ý���
				Intent intent = new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS);
				startActivity(intent);
			}
		});
		mobileDialog.setNegativeButton("���л�", null);

	}

	class MyOnClickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.btn_sxlx:
				Intent intent_sxlx = new Intent(MainActivity.this,
						SxlxActivity.class);
				startActivity(intent_sxlx);
				break;

			case R.id.btn_sjlx:
				Intent intent_sjlx = new Intent(MainActivity.this,
						SjlxActivity.class);
				startActivity(intent_sjlx);
				break;

			case R.id.btn_zjlx:
				Intent intent_zjlx = new Intent(MainActivity.this,
						ZjlxActivity.class);
				startActivity(intent_zjlx);
				break;

			case R.id.btn_mnks:
				pd = ProgressDialog.show(MainActivity.this, "��ܰ��ʾ",
						"����ƴ�����ؿ���><");
				new Thread() {
					private ArrayList<QuestionBean> questionList;
					private int bigSpanNum;

					public void run() {
						DBHelperForExercises dbHelperForExercises = new DBHelperForExercises(
								MainActivity.this);
						questionList = (ArrayList<QuestionBean>) dbHelperForExercises
								.orderedExercise();
						if (bigSpanNum != 0) {
							for (int i = 0; i < testNum - bigSpanNum; i++) {
								ids[i] = random.nextInt(normalSpan) + i
										* normalSpan;
							}
							for (int i = testNum - bigSpanNum; i < testNum; i++) {
								ids[i] = random.nextInt(normalSpan + 1)
										+ smallSpanNum * normalSpan
										+ (i - smallSpanNum) * (normalSpan + 1);
							}
						} else {
							for (int i = 0; i < testNum; i++) {
								ids[i] = random.nextInt(normalSpan) + i
										* normalSpan;
							}

						}
						handler.sendEmptyMessage(1);
					};
				}.start();

				break;

			case R.id.btn_ctjlb:
				Intent intent_ctjlb = new Intent(MainActivity.this,
						CtjlbActivity.class);
				startActivity(intent_ctjlb);
				break;

			case R.id.btn_point:
				Intent intent_point = new Intent(MainActivity.this,
						PointActivity.class);
				startActivity(intent_point);
				break;

			case R.id.btn_law:
				Intent intent_law = new Intent(MainActivity.this,
						LawActivity.class);
				startActivity(intent_law);
				break;

			case R.id.btn_cheats:
				Intent intent_cheats = new Intent(MainActivity.this,
						CheatsActivity.class);
				startActivity(intent_cheats);
				break;

			case R.id.btn_collection:
				Intent intent_collection = new Intent(MainActivity.this,
						CollectionActivity.class);
				startActivity(intent_collection);
				break;

			case R.id.ll_subjectOne:
				Intent intent_subjectOne = new Intent(MainActivity.this,
						MainActivity.class);
				startActivity(intent_subjectOne);
				break;
			case R.id.ll_subjectTwo:
				Intent intent_subjectTwo = new Intent(MainActivity.this,
						SubjectTwoActivity.class);
				startActivity(intent_subjectTwo);
				break;
			case R.id.ll_subjectThree:
				Intent intent_subjectThree = new Intent(MainActivity.this,
						SubjectThreeActivity.class);
				startActivity(intent_subjectThree);
				break;
			case R.id.ll_subjectFour:
				Intent intent_subjectFour = new Intent(MainActivity.this,
						MainActivity.class);
				startActivity(intent_subjectFour);
				break;
			case R.id.ll_login:
				Intent intent_login = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent_login);
				break;
			case R.id.ll_circle_of_friends:
				if (usernameFromLogin != null || usernameFromRegister != null) {
					Intent intent_circle_of_friends = new Intent(
							MainActivity.this, CircleOfFriendsActivity.class);
					startActivity(intent_circle_of_friends);
				} else {
					Toast.makeText(MainActivity.this, "����û�е�¼���ܲ鿴����Ȧ",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.ll_exit:
				exitDialog.show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		editor = sp.edit();
		editor.putBoolean("isFirst", true);
		editor.commit();
	}
}

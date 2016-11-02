package com.example.mygraduation_s.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.UsernameDao;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SubjectThreeActivity extends Activity implements
		OnItemClickListener {
	private AlertDialog.Builder exitDialog;
	private LinearLayout ll_subjectOne;
	private LinearLayout ll_subjectTwo;
	private LinearLayout ll_subjectThree;
	private LinearLayout ll_subjectFour;
	private LinearLayout ll_login;
	private LinearLayout ll_exit;
	private ListView lv_subjectThree;
	private String titles[];
	private String subtitles[];
	private TextView tvUsername;
	private LinearLayout llCircleOfFriends;
	private UsernameDao dao;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject_three);

		dao = new UsernameDao(this);
		initMenu();
		initView();

		lv_subjectThree = (ListView) findViewById(R.id.lv_subjectThree);
		MyAdapter adapter = new MyAdapter();
		initData();
		lv_subjectThree.setAdapter(adapter);
		lv_subjectThree.setOnItemClickListener(this);
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

	// ��ʼ���ؼ�
	private void initView() {
		ll_subjectOne = (LinearLayout) findViewById(R.id.ll_subjectOne);
		ll_subjectTwo = (LinearLayout) findViewById(R.id.ll_subjectTwo);
		ll_subjectThree = (LinearLayout) findViewById(R.id.ll_subjectThree);
		ll_subjectFour = (LinearLayout) findViewById(R.id.ll_subjectFour);
		ll_login = (LinearLayout) findViewById(R.id.ll_login);
		ll_exit = (LinearLayout) findViewById(R.id.ll_exit);
		tvUsername = (TextView) findViewById(R.id.tv_sliding_menu_username);
		Cursor cursor = dao.select("username", null);
		while (cursor.moveToNext()) {

			username = cursor.getString(cursor.getColumnIndex("username"));
		}
		tvUsername.setText(username);
		llCircleOfFriends = (LinearLayout) findViewById(R.id.ll_circle_of_friends);
		ll_subjectOne.setOnClickListener(new MyOnClickListener());
		ll_subjectTwo.setOnClickListener(new MyOnClickListener());
		ll_subjectThree.setOnClickListener(new MyOnClickListener());
		ll_subjectFour.setOnClickListener(new MyOnClickListener());
		ll_login.setOnClickListener(new MyOnClickListener());
		ll_exit.setOnClickListener(new MyOnClickListener());
		llCircleOfFriends.setOnClickListener(new MyOnClickListener());

	}

	// ��ʼ���Ի���
	private void initAlertDialog() {
		// �˳��Ի���
		exitDialog = new AlertDialog.Builder(SubjectThreeActivity.this);
		exitDialog.setTitle("��ʾ");
		exitDialog.setMessage("�Ƿ�ȷ���˳�?");
		exitDialog.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		exitDialog.setNegativeButton("ȡ��", null);

	}

	private void initData() {

		titles = new String[] { "�ϳ�׼��", "��", "ֱ����ʻ", "�Ӽ���λ", "�������", "����ͣ��",
				"ͨ��·��", "ͨ��������", "�ᳵ", "����", "��ͷ", "ҹ����ʻ", "·���ھ�", "�����߲���·���ع�",
				"����Զ��·���־�" };
		subtitles = new String[] { "�ϳ�ǰ�۲쳵����ۺ���Χ������ȷ�ϰ�ȫ",
				"�����ͼ�鳵����ʩ���۲�󷽡��෽��ͨ...", "������Ƴ��٣�ʹ�õ�λ������ֱ����ʻ��",
				"����Ӽ�����������ʱ��ƽ˳��", "��һ������������һ�������ļ�ʻ������", "��ʻ����ʹ֮����ͣ�¡�",
				"���ٻ�ͣ���t����ֱ�а�ȫͨ��·�ڡ�", "ͨ�����к����ѧУ���򣬹�����վ��",
				"��ȷ�жϻᳵ�ص㣬��Է��������ְ�ȫ���...", "������һ�����棬�Ӻ��泬��ͬ������ʻ...",
				"��ȷѡ���ͷ�ص��ʱ����������ͷ�źź�...", "���ݸ�����������͵�·�����ȷʹ�õƹ⡣",
				"��������·��˳���", "·���߲�С�ؼ�", "�˷�·���־�С����" };
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(SubjectThreeActivity.this)
						.inflate(R.layout.list_item_subject_three, null);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_subtitle = (TextView) convertView
						.findViewById(R.id.tv_subtitle);
				holder.iv_order = (ImageView) convertView
						.findViewById(R.id.iv_order);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// ������
			holder.tv_title.setText(titles[position]);
			holder.tv_subtitle.setText(subtitles[position]);

			return convertView;
		}

	}

	class ViewHolder {
		TextView tv_title, tv_subtitle;
		ImageView iv_order;
	}

	class MyOnClickListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			case R.id.ll_subjectOne:
				Intent intent_subjectOne = new Intent(
						SubjectThreeActivity.this, MainActivity.class);
				startActivity(intent_subjectOne);
				break;
			case R.id.ll_subjectTwo:
				Intent intent_subjectTwo = new Intent(
						SubjectThreeActivity.this, SubjectTwoActivity.class);
				startActivity(intent_subjectTwo);
				break;
			case R.id.ll_subjectThree:
				Intent intent_subjectThree = new Intent(
						SubjectThreeActivity.this, SubjectThreeActivity.class);
				startActivity(intent_subjectThree);
				break;

			case R.id.ll_subjectFour:
				Intent intent_subjectFour = new Intent(
						SubjectThreeActivity.this, MainActivity.class);
				startActivity(intent_subjectFour);
				break;
			case R.id.ll_login:
				Intent intent_login = new Intent(SubjectThreeActivity.this,
						LoginActivity.class);
				startActivity(intent_login);
				break;
			case R.id.ll_circle_of_friends:
				Intent intent_circle_of_friends = new Intent(
						SubjectThreeActivity.this,
						CircleOfFriendsActivity.class);
				startActivity(intent_circle_of_friends);
				break;
			case R.id.ll_exit:
				exitDialog.show();
				break;
			default:
				break;
			}
		}
	}

	// lv_subjectThree�ļ���
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(SubjectThreeActivity.this,
				DetailActivity.class);
		intent.putExtra("item", 301 + position);
		startActivity(intent);
	}

}

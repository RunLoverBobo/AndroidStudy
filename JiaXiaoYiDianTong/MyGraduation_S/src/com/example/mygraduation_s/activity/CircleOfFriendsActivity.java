package com.example.mygraduation_s.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.UsernameDao;
import com.example.mygraduation_s.javabean.TalkBean;

public class CircleOfFriendsActivity extends Activity implements
		OnClickListener {

	private ListView lvCircleOfFriends;
	private EditText etTalking;
	private Button btnSubmit;
	private UsernameDao dao;
	private String username;
	private List<TalkBean> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_of_friends);

		dao = new UsernameDao(this);
		initView();
		mList = new ArrayList<TalkBean>();
		getList();
		MyAdapter adapter = new MyAdapter();
		lvCircleOfFriends.setAdapter(adapter);

	}

	private void getList() {

		BmobQuery<TalkBean> query = new BmobQuery<TalkBean>();
		query.addWhereNotEqualTo("username", "");
		query.findObjects(CircleOfFriendsActivity.this,
				new FindListener<TalkBean>() {

					@Override
					public void onSuccess(List<TalkBean> object) {

						for (TalkBean talkBean : object) {
							String usernameOfFriends = talkBean.getUsername();
							String talking = talkBean.getTalking();
							String time = talkBean.getUpdatedAt();
							TalkBean bean = new TalkBean(usernameOfFriends,
									talking, time);
							mList.add(bean);
						}

					}

					@Override
					public void onError(int code, String msg) {

						Toast.makeText(CircleOfFriendsActivity.this,
								"读取朋友圈信息失败：" + msg, Toast.LENGTH_LONG).show();
					}
				});
	}

	private void initView() {

		lvCircleOfFriends = (ListView) findViewById(R.id.lv_activity_circle_of_friends);
		etTalking = (EditText) findViewById(R.id.et_activity_circle_of_talk);
		btnSubmit = (Button) findViewById(R.id.btn_activity_circle_of_submit);
		btnSubmit.setOnClickListener(this);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList != null ? mList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
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
				convertView = LayoutInflater.from(CircleOfFriendsActivity.this)
						.inflate(R.layout.list_item_activity_circle_of_friends,
								null);
				holder.tvUsernameOfFriends = (TextView) convertView
						.findViewById(R.id.tv_activity_circle_of_friends_username);
				holder.tvTalking = (TextView) convertView
						.findViewById(R.id.tv_activity_circle_of_friends_talk);
				holder.tvTime = (TextView) convertView
						.findViewById(R.id.tv_activity_circle_of_friends_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 绑定数据
			TalkBean talk = mList.get(position);
			String usernameOfFriends = talk.getUsername();
			holder.tvUsernameOfFriends.setText(usernameOfFriends);
			String talking = talk.getTalking();
			holder.tvTalking.setText(talking);
			String createdAt = talk.getCreatedAt();
			holder.tvTime.setText(createdAt);

			return convertView;
		}

		class ViewHolder {
			TextView tvUsernameOfFriends, tvTalking, tvTime;
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_activity_circle_of_submit:

			TalkBean talkBean = new TalkBean();
			Cursor cursor = dao.select("username", null);
			while (cursor.moveToNext()) {

				username = cursor.getString(cursor.getColumnIndex("username"));
			}
			String talking = etTalking.getText().toString();
			talkBean.setUsername(username);
			talkBean.setTalking(talking);
			talkBean.save(CircleOfFriendsActivity.this, new SaveListener() {

				@Override
				public void onSuccess() {

					etTalking.setText("");
					Toast.makeText(CircleOfFriendsActivity.this, "发表成功",
							Toast.LENGTH_LONG).show();
				}

				@Override
				public void onFailure(int code, String msg) {

					Toast.makeText(CircleOfFriendsActivity.this, "发表失败:" + msg,
							Toast.LENGTH_LONG).show();
				}
			});

			break;

		default:
			break;
		}

	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.circle_of_friends, menu); MenuItem
	 * action_refresh = menu.findItem(R.id.action_refresh); action_refresh
	 * .setOnMenuItemClickListener(new OnMenuItemClickListener() {
	 * 
	 * @Override public boolean onMenuItemClick(MenuItem arg0) {
	 * 
	 * getList(); MyAdapter adapterAfterRefresh = new MyAdapter();
	 * lvCircleOfFriends.setAdapter(adapterAfterRefresh); return false; } });
	 * return true; }
	 */
}

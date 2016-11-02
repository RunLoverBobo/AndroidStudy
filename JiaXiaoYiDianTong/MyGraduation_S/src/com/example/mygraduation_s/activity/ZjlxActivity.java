package com.example.mygraduation_s.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mygraduation_s.R;

public class ZjlxActivity extends Activity implements OnItemClickListener {

	private ListView lv_zjlx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zjlx);

		lv_zjlx = (ListView) findViewById(R.id.lv_zjlx);
		MyAdapter adapter = new MyAdapter();
		String titles[] = { "恶劣天气安全行驶试题", "车辆安全行驶试题", "夜间行车注意事项",
				"在特殊道路上安全行驶试题", "自动挡汽车安全行驶相关知识", "机动车安全行驶相关试题" };
		adapter.setData(this, titles);
		lv_zjlx.setAdapter(adapter);
		lv_zjlx.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(ZjlxActivity.this, ZjlxTypeActivity.class);
		intent.putExtra("index", position);
		startActivity(intent);

	}

	class MyAdapter extends BaseAdapter {

		String titles[];
		Context context;

		public void setData(Context context, String titles[]) {
			this.titles = titles;
			this.context = context;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Object getItem(int position) {
			return titles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item_zjlx, null);
			TextView tv_zjlx_item_type = (TextView) convertView
					.findViewById(R.id.tv_zjlx_item_type);
			tv_zjlx_item_type.setText(titles[position]);

			return convertView;
		}

	}

}

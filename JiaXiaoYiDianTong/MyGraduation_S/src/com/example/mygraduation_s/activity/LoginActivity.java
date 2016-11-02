package com.example.mygraduation_s.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.UsernameDao;
import com.example.mygraduation_s.javabean.UserBean;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private Button btnRegister;
	private EditText etUsername;
	private EditText etPassword;
	private String username;
	private String password;
	private AlertDialog.Builder dialog;
	private UsernameDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Bmob.initialize(this, "3f840b0172a4701f8447ebc131266e8e");

		dao = new UsernameDao(this);

		initView();

	}

	private void initView() {

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnRegister = (Button) findViewById(R.id.btn_register_for_login);
		etUsername = (EditText) findViewById(R.id.et_username_for_login);
		etPassword = (EditText) findViewById(R.id.et_password_for_login);
		btnLogin.setOnClickListener(new myOnClickListener());
		btnRegister.setOnClickListener(new myOnClickListener());
	}

	// 弹出对话框
	private void showDialog() {

		dialog = new AlertDialog.Builder(LoginActivity.this);
		dialog.setTitle("提示");
		dialog.setMessage("登录成功");
		dialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				intent.putExtra("usernameFromLogin", username);
				startActivity(intent);
				dao.clear();
				ContentValues values = new ContentValues();
				values.put("username", username);
				dao.insert("username", values);
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.show();

	}

	class myOnClickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_login:

				username = etUsername.getText().toString().trim();
				password = etPassword.getText().toString().trim();
				BmobQuery<UserBean> query = new BmobQuery<UserBean>();
				query.addWhereEqualTo("username", username);
				query.addWhereEqualTo("password", password);
				query.findObjects(LoginActivity.this,
						new FindListener<UserBean>() {

							@Override
							public void onSuccess(List<UserBean> object) {

								if (object.size() > 0) {
									showDialog();
								} else {
									Toast.makeText(LoginActivity.this,
											"您还没有账号，请先注册", Toast.LENGTH_LONG)
											.show();
								}
							}

							@Override
							public void onError(int code, String msg) {

								Toast.makeText(LoginActivity.this,
										"登录失败：" + msg, Toast.LENGTH_LONG)
										.show();
							}
						});
				break;
			case R.id.btn_register_for_login:
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}

		}

	}

}

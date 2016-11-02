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
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.example.mygraduation_s.R;
import com.example.mygraduation_s.database.UsernameDao;
import com.example.mygraduation_s.javabean.UserBean;

public class RegisterActivity extends Activity {

	private Button btnRegister;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etPassword02;
	private String username;
	private String password;
	private String password02;
	private AlertDialog.Builder loginDialog;
	private boolean hasRegistered = false;
	private UsernameDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		dao = new UsernameDao(this);
		initView();

	}

	// 弹出对话框
	private void showDialog() {

		loginDialog = new AlertDialog.Builder(RegisterActivity.this);
		loginDialog.setTitle("提示");
		loginDialog.setMessage("注册成功，即将自动登录");
		loginDialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(RegisterActivity.this,
						MainActivity.class);
				intent.putExtra("usernameFromRegister", username);
				startActivity(intent);
				dao.clear();
				ContentValues values = new ContentValues();
				values.put("username", username);
				dao.insert("username", values);
			}
		});
		loginDialog.show();

	}

	private void register() {

		final UserBean user = new UserBean();
		user.setUsername(username);
		user.setPassword(password);
		user.save(RegisterActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {

				showDialog();
			}

			@Override
			public void onFailure(int code, String msg) {

				Toast.makeText(RegisterActivity.this, "注册失败:" + msg,
						Toast.LENGTH_LONG).show();
			}
		});

	}

	private void initView() {

		btnRegister = (Button) findViewById(R.id.btn_register_for_register);
		etUsername = (EditText) findViewById(R.id.et_username_for_register);
		etPassword = (EditText) findViewById(R.id.et_password_for_register);
		etPassword02 = (EditText) findViewById(R.id.et_password02_for_register);
		btnRegister.setOnClickListener(new MyOnClickListener());
	}

	class MyOnClickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {

			username = etUsername.getText().toString().trim();
			password = etPassword.getText().toString().trim();
			password02 = etPassword02.getText().toString().trim();
			BmobQuery<UserBean> query = new BmobQuery<UserBean>();
			query.addWhereEqualTo("username", username);
			query.findObjects(RegisterActivity.this,
					new FindListener<UserBean>() {

						@Override
						public void onSuccess(List<UserBean> object) {
							if (object.size() > 0) {
								hasRegistered = true;
							}
						}

						@Override
						public void onError(int code, String msg) {

						}
					});
			if (password.equals(password02) && !hasRegistered) {

				register();
			} else if (!password.equals(password02)) {
				Toast.makeText(RegisterActivity.this, "您两次输入的密码不一致，请核对后再次尝试",
						Toast.LENGTH_LONG).show();
			} else if (hasRegistered) {
				Toast.makeText(RegisterActivity.this, "该账号已被注册，请重新注册",
						Toast.LENGTH_LONG).show();
			}
		}

	}

}

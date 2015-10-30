package com.test.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fortysevendeg.swipelistview.SwipeListView;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText editText_phoneNumber;
    private EditText editText_password;

    private Button button_login;
    private Button button_regist;
    private Button button_clear_account;
    private Button button_clear_password;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        editText_phoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editText_password = (EditText) findViewById(R.id.editTextPassword);
        button_clear_account = (Button) findViewById(R.id.button_clear_account);
        button_clear_password = (Button) findViewById(R.id.button_clear_password);
        button_clear_account.setOnClickListener(this);
        button_clear_password.setOnClickListener(this);
        editText_phoneNumber.addTextChangedListener(mTextWatcher);
        editText_password.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_regist:
                //TODO:注册逻辑
                break;
            case R.id.button_login:
                //TODO:登录逻辑
                if (true) {     //匹配账号密码

                }
                break;
            case R.id.button_clear_account:
                editText_phoneNumber.setText("");
                break;
            case R.id.button_clear_password:
                editText_password.setText("");
                break;
        }
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!editText_phoneNumber.getText().toString().equals("")) {
                button_clear_account.setVisibility(View.VISIBLE);
            }
            else {
                button_clear_account.setVisibility(View.INVISIBLE);
            }
            if (!editText_password.getText().toString().equals("")) {
                button_clear_password.setVisibility(View.VISIBLE);
            }
            else {
                button_clear_password.setVisibility(View.INVISIBLE);
            }
        }
    };
}

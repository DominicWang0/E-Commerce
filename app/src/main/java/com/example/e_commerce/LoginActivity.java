package com.example.e_commerce;

import static com.example.e_commerce.utils.dataValidUtils.isEmailValid;
import static com.example.e_commerce.utils.dataValidUtils.isEmpty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.utils.dataValidUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    TextView tv_accountDescription, tv_passcodeDescription;
    EditText et_account, et_passcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    void init() {
        btn_login = findViewById(R.id.login_btn_login);
        et_account = findViewById(R.id.login_et_account);
        et_passcode = findViewById(R.id.login_et_passcode);
        tv_accountDescription = findViewById(R.id.login_tv_accountDescription);
        tv_passcodeDescription = findViewById(R.id.login_tv_passcodeDescription);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent toMainWindow = new Intent(this, ShoppingChannelActivity.class);
        if (view.getId() == R.id.login_btn_login) {
            //登录
            //账号格式合法
            if (isAccountValid(et_account.getText().toString().trim())) {
            } else return;
            //密码格式合法
            if (isPasscodeValid(et_passcode.getText().toString().trim())) {
            } else return;
            startActivity(toMainWindow);

        }
    }

    private boolean isPasscodeValid(String passcode) {
        if (passcode.isEmpty() || passcode.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            tv_passcodeDescription.setVisibility(View.GONE);
            return false;
        }
        if (dataValidUtils.isPasscodeValid(passcode)) {
            //密码格式  合法
            tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            tv_passcodeDescription.setVisibility(View.GONE);
            return true;
        } else {
            //密码格式  非法
            tv_passcodeDescription.setTextColor(Color.parseColor("#FF0000"));
            tv_passcodeDescription.setVisibility(View.VISIBLE);
            return false;
        }
    }

    boolean isAccountValid(String account) {

        if (isEmpty(account)) {
            //账号为空
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            tv_accountDescription.setTextColor(Color.parseColor("#000000"));
            tv_accountDescription.setVisibility(View.GONE);
            return false;
        }

        if (isEmailValid(account)) {
            //邮箱格式  合法
            tv_accountDescription.setTextColor(Color.parseColor("#000000"));
            tv_accountDescription.setVisibility(View.GONE);
            return true;
        } else {
            //邮箱格式  非法
            tv_accountDescription.setTextColor(Color.parseColor("#FF0000"));
            tv_accountDescription.setVisibility(View.VISIBLE);
            return false;
        }

    }

}
package com.example.e_commerce.activity;

import static com.example.e_commerce.utils.SPSave.SPGetUserInfo;
import static com.example.e_commerce.utils.dataValidUtils.isEmailValid;
import static com.example.e_commerce.utils.dataValidUtils.isEmpty;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;
import com.example.e_commerce.utils.dataValidUtils;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_signIn;
    TextView tv_accountDescription, tv_passcodeDescription, tv_signUp;
    EditText et_account, et_passcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        getData();
    }

    void init() {
        btn_signIn = findViewById(R.id.sign_in_btn_sign_in);
        et_account = findViewById(R.id.sign_in_et_account);
        et_passcode = findViewById(R.id.sign_in_et_passcode);
        tv_accountDescription = findViewById(R.id.sign_in_tv_accountDescription);
        tv_passcodeDescription = findViewById(R.id.sign_in_tv_passcodeDescription);
        tv_signUp = findViewById(R.id.sign_in_tv_sign_up);

        btn_signIn.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent toSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
        Intent toMainWindow = new Intent(SignInActivity.this, ShoppingChannelActivity.class);
        if (view.getId() == R.id.sign_in_btn_sign_in) {
            //登录
            //账号格式合法
            if (isAccountValid(et_account.getText().toString().trim())) {

            } else return;

            //密码格式合法
            if (isPasscodeValid(et_passcode.getText().toString().trim())) {

            } else return;

            //验证账号密码匹配
            if (isVerifiedPassed(et_account.getText().toString().trim(), et_passcode.getText().toString().trim())) {
                //启动跳转
                startActivity(toMainWindow);
            }

        } else if (view.getId() == R.id.sign_in_tv_sign_up) {
            //注册
            toSignUp.putExtra("account", "");    //未传值的注册页面跳转操作
            startActivity(toSignUp);
        } else if (view.getId() == R.id.sign_in_tv_sign_up) {
            //注册
            toSignUp.putExtra("account", "");    //未传值的注册页面跳转操作
            startActivity(toSignUp);
        }
    }

    private boolean isPasscodeValid(String passcode) {
        if (passcode.length() == 0 || passcode.equals("")) {
            Toast.makeText(this, "Passcode is Empty", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Account is Empty", Toast.LENGTH_SHORT).show();
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

    private boolean isVerifiedPassed(String account, String passcode) {
        String truePasscode = SPGetUserInfo(this, account);
        if (truePasscode == "not found") {
            //账号不存在
            AlertDialog nonAccount = new AlertDialog.Builder(SignInActivity.this).setTitle("账号不存在").setIcon(R.drawable.applelogo).setMessage("是否新建账户？").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent toSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                    toSignUp.putExtra("account", account);
                    toSignUp.putExtra("passcode", passcode);
                    startActivity(toSignUp);
                }
            }).setNegativeButton("Cancel", null).create();
            nonAccount.show();
            return false;
        }
        if (passcode.equals(truePasscode)) {
            return true;
        } else {
            Toast.makeText(this, "Wrong Passcode", Toast.LENGTH_SHORT).show();
            et_passcode.setText(null);
            return false;
        }
    }

    void getData() {
        Intent getData = getIntent();
        if (getData != null) {
            // 从注册返回
            et_account.setText(getData.getStringExtra("account"));
            et_passcode.setText(getData.getStringExtra("passcode"));
        }
    }

}
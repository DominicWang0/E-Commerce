package com.example.e_commerce.activity;

import static com.example.e_commerce.utils.SPSave.SPSaveUserInfo;
import static com.example.e_commerce.utils.dataValidUtils.isEmailValid;
import static com.example.e_commerce.utils.dataValidUtils.isPasscodeValid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_account, et_passcode, et_rePasscode;
    Button btn_signUp;
    TextView tv_backToSignIn, tv_accountDescription, tv_passcodeDescription, tv_rePasscodeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    void init() {
        et_account = findViewById(R.id.sign_up_et_account);
        et_passcode = findViewById(R.id.sign_up_et_passcode);
        et_rePasscode = findViewById(R.id.sign_up_et_re_asscode);
        btn_signUp = findViewById(R.id.sign_up_btn_signup);
        tv_backToSignIn = findViewById(R.id.sign_up_tv_back_to_sign_in);
        tv_accountDescription = findViewById(R.id.sign_up_tv_account_description);
        tv_passcodeDescription = findViewById(R.id.sign_up_tv_passcode_description);
        tv_rePasscodeDescription = findViewById(R.id.sign_up_tv_re_passcode_description);
        btn_signUp.setOnClickListener(this);
        tv_backToSignIn.setOnClickListener(this);

        String account;
        Intent getData = getIntent();
        account = getData.getStringExtra("account");
        if (account.equals("")) {

        } else {
            et_account.setText(account);
            et_passcode.setText(getData.getStringExtra("passcode"));
        }


        Log.i("SignUp", "初始化成功");
    }

    @Override
    public void onClick(View view) {
        Intent toSignIn = new Intent(SignUpActivity.this, SignInActivity.class);
        if (view.getId() == R.id.sign_up_btn_signup) {
            //全部合法标识
            boolean isAllCompliant = true;

            //注册
            if (isEmpty(et_account)) {
                Toast.makeText(this, "Account is Empty", Toast.LENGTH_SHORT).show();
                isAllCompliant = false;
            } else if (isEmpty(et_passcode)) {
                Toast.makeText(this, "Passcode is Empty", Toast.LENGTH_SHORT).show();
                isAllCompliant = false;
            }

            //判断账号是否合法
            if (isEmailValid(et_account.getText().toString().trim())) {
                tv_accountDescription.setTextColor(Color.parseColor("#000000"));
            } else {
                tv_accountDescription.setTextColor(Color.parseColor("#FF0000"));
                isAllCompliant = false;
            }

            //判断密码是否合法
            if (isPasscodeValid(et_passcode.getText().toString().trim())) {
                tv_passcodeDescription.setTextColor(Color.parseColor("#000000"));
            } else {
                tv_passcodeDescription.setTextColor(Color.parseColor("#FF0000"));
                isAllCompliant = false;
            }

            //判断两次密码是否一致
            if (et_passcode.getText().toString().trim().equals(et_rePasscode.getText().toString().trim())) {
                tv_rePasscodeDescription.setVisibility(View.GONE);
            } else {
                tv_rePasscodeDescription.setTextColor(Color.parseColor("#FF0000"));
                tv_rePasscodeDescription.setVisibility(View.VISIBLE);
                isAllCompliant = false;
            }

            if (isAllCompliant) {
                //全部合法
                if (SPSaveUserInfo(this, et_account.getText().toString().trim(), et_passcode.getText().toString().trim())) {
                    Log.i("SignUp", "用户数据成功记录");
                    toSignIn.putExtra("account", et_account.getText().toString().trim());
                    toSignIn.putExtra("passcode", et_passcode.getText().toString());
                    startActivity(toSignIn);
                }
            }
            Log.i("SignUp", "注册按钮被按下");
        } else if (view.getId() == R.id.sign_up_tv_back_to_sign_in) {
            //返回登录
            startActivity(toSignIn);
            Log.i("SignUp", "返回登录页按钮被按下");
        }
    }

    boolean isEmpty(EditText temp) {
        if (temp.getText().length() == 0 || temp.getText().toString().trim() == "") {
            return true;
        }
        return false;
    }

}
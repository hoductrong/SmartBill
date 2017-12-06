package banhang.smartbill.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.Entity.UnauthorizedAccessException;
import banhang.smartbill.R;

public class LoginActivity extends AppCompatActivity {
    public static final String MYAPP = "SMART_BILL_APP";
    public static final String TOKEN = "TOKEN";
    EditText edt_username;
    EditText edt_password;
    Button btn_sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Kiểm tra user đã đăng nhập chưa
        SharedPreferences prefs = getSharedPreferences(MYAPP,MODE_PRIVATE);
        String token = prefs.getString(TOKEN,null);
        if(token != null){
            TokenAPI.TOKEN = token;
            goToMainActivity();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_sign_in = (Button)findViewById(R.id.btn_sign_in);
        edt_username = (EditText)findViewById(R.id.edt_username);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btn_sign_in.setOnClickListener(new signInClick());

        edt_password.addTextChangedListener(getValidateInput());
        edt_username.addTextChangedListener(getValidateInput());

    }

    //thực hiện validate đăng nhập
    private class signInClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            btn_sign_in.setEnabled(false);
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what){
                        case SignInStatus.SUCCESS:
                            goToMainActivity();
                            break;
                        case SignInStatus.Fail:
                            btn_sign_in.setEnabled(true);
                            Toast.makeText(LoginActivity.this,"Đăng nhập thất bại. Vui lòng thử lại",
                                    Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            //phải thực hiện gọi API trong một thread khác, vì main thread không cho phép thực hiện gọi API
            Thread signInThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        TokenAPI tokenApi = new TokenAPI();
                        tokenApi.getToken(edt_username.getText().toString(),edt_password.getText().toString());
                        Message message;
                        if(tokenApi.TOKEN != null){
                            SharedPreferences.Editor editor = getSharedPreferences(MYAPP,MODE_PRIVATE).edit();
                            editor.putString(TOKEN,TokenAPI.TOKEN);
                            editor.apply();
                            message = handler.obtainMessage(SignInStatus.SUCCESS);
                        }else{
                            message = handler.obtainMessage(SignInStatus.Fail);
                        }
                        handler.sendMessage(message);
                    }catch(UnauthorizedAccessException ex){
                        //get token don't need authozire
                    }

                }
            });
            signInThread.start();
        }
    }

    /*
    * di chuyển tới main activity sau khi đăng nhập thành công*/
    private void goToMainActivity(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private TextWatcher getValidateInput(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edt_password.getText().length() == 0 || edt_username.getText().length() == 0)
                    btn_sign_in.setEnabled(false);
                else
                    btn_sign_in.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }


    //
    //Cho phép handler biết được sign in có thành công hay không
    //
    private  interface SignInStatus{
        int SUCCESS = 0;
        int Fail = 1;
    }
}

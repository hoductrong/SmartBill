package banhang.smartbill.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import banhang.smartbill.DAL.TokenAPI;
import banhang.smartbill.R;

public class LoginActivity extends AppCompatActivity {
    public static final String MYAPP = "SMART_BILL_APP";
    public static final String TOKEN = "TOKEN";
    EditText edt_username;
    EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Kiểm tra user đã đăng nhập chưa
        SharedPreferences prefs = getSharedPreferences(MYAPP,MODE_PRIVATE);
        String token = prefs.getString(TOKEN,null);
        if(token != null){
            TokenAPI.TOKEN = token;
            goToMainActivity();
        }
        Button btn_sign_in = (Button)findViewById(R.id.btn_sign_in);
        edt_username = (EditText)findViewById(R.id.edt_username);
        edt_password = (EditText)findViewById(R.id.edt_password);
        btn_sign_in.setOnClickListener(new signInClick());

    }

    //thực hiện validate đăng nhập
    private class signInClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    switch (msg.what){
                        case SignInStatus.SUCCESS:
                            goToMainActivity();
                            break;
                        case SignInStatus.Fail:
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



    //
    //Cho phép handler biết được sign in có thành công hay không
    //
    private  interface SignInStatus{
        int SUCCESS = 0;
        int Fail = 1;
    }
}

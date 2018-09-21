package test1.android.com.test1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;
import test1.android.com.test1.Utils.SharedPrefManager;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginUser)
    EditText loginUser;
    @BindView(R.id.loginPass)
    EditText loginPass;
    @BindView(R.id.BtnLogin)
    Button btnLogin;
    ProgressDialog loading;
    @BindView(R.id.showPassword)
    CheckBox showPassword;
    @BindView(R.id.newUser)
    TextView newUser;

    private ScanTimeOut mScanTimeOut = null;

    //    Call<AccessToken> call;
//    Context mContext;
    ApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        ButterKnife.bind(this);
        mApiService = ApiClient.getClient().create(ApiService.class); // Initialize Api client helper
        sharedPrefManager = new SharedPrefManager(this);  //Initialize SharedPrefManager

        loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    loginPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(LoginActivity.this, null, "Harap Tunggu...", true, false);
                requestLogin();
            }
        });

        newUser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });


    }

    private void requestLogin() {
        mApiService.loginRequest(loginUser.getText().toString(), loginPass.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mScanTimeOut = new ScanTimeOut(15000 / 1000);
                            mScanTimeOut.startTimer();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                String tokenType = jsonRESULTS.getString("token_type");
                                String accessToken = jsonRESULTS.getString("access_token");
                                if (tokenType.equals("Bearer")) {
                                    // Login Success then go to Main Page
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    // Session Login
                                    sharedPrefManager.saveToken(SharedPrefManager.SP_TOKEN, accessToken);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();
                                    loading.dismiss();
                                } else {
                                    // If Login failed
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(LoginActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    public class ScanTimeOut {
        public long timeOut; //Application time out value (600L = 10 mins)
        private Timer appTimer = null;
        private Handler handler = null;

        public ScanTimeOut(int timeOut) {
            this.timeOut = timeOut;
        }

        public void startTimer() {

            final CountDownLatch AuthorisationLatch = new CountDownLatch(
                    (int) timeOut);
            if (appTimer != null && handler != null) {
                appTimer.cancel();
                appTimer.purge();
                appTimer = null;
                handler = null;
            }
            handler = new Handler();
            appTimer = new Timer();
            appTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            AuthorisationLatch.countDown();
                            Log.d("Scan Timer :", " ------ " + AuthorisationLatch.getCount());
                            if (AuthorisationLatch.getCount() == 0) {
                                showTimeOutDialog();
                                if (appTimer != null && handler != null) {
                                    appTimer.cancel();
                                    appTimer.purge();
                                    handler = null;
                                    appTimer = null;
                                }
                            }
                        }
                    });
                }
            }, 0, 1000);
        }

        public void stopTimer() {
            if (appTimer != null && handler != null) {
                appTimer.cancel();
                appTimer.purge();
                handler = null;
                appTimer = null;
            }
        }
    }

    public void showTimeOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        MANUAL_INPUT_ENABLE = getIntent().getExtras().getBoolean("ManualIDEnabled",true);
//
//        if(SELECTED_OCR_TYPE!=null && (SELECTED_OCR_TYPE.equalsIgnoreCase("IMM13") ||
//                SELECTED_OCR_TYPE.equalsIgnoreCase("MYKAS") ||
//                SELECTED_OCR_TYPE.equalsIgnoreCase("IKAD") ||
//                !MANUAL_INPUT_ENABLE)){
//            builder.setMessage("Unable to scan simcard barcode")
//                    .setCancelable(false)
//                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            mScanTimeOut.startTimer();
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = getIntent();
//                            intent.putExtra(EXTRAS_SELECTED_OCR_TYPE, SELECTED_OCR_TYPE);
//                            setResult(Activity.RESULT_CANCELED, intent);
//                            finish();
//                        }
//                    });
//        }else{
//            builder.setMessage("Unable to scan simcard barcode")
//                    .setCancelable(false)
//                    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            mScanTimeOut.startTimer();
//                        }
//                    })
//                    .setNegativeButton("Manual Input", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = getIntent();
//                            intent.putExtra(EXTRAS_SELECTED_OCR_TYPE, SELECTED_OCR_TYPE);
//                            setResult(Activity.RESULT_CANCELED, intent);
//                            finish();
//                        }
//                    });
//        }

        builder.setMessage("Unable to scan simcard barcode")
                .setCancelable(false)
                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mScanTimeOut.startTimer();
                    }
                })
                .setNegativeButton("Manual Input", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Timeout");
        alert.setCancelable(false);
        alert.show();
    }
}
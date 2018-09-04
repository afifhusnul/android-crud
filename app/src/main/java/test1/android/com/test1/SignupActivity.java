package test1.android.com.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import okhttp3.internal.http2.ErrorCode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test1.android.com.test1.Rest.ApiClient;
import test1.android.com.test1.Rest.ApiService;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.newName)
    EditText newUser;
    @BindView(R.id.newEmail)
    EditText newEmail;
    @BindView(R.id.newPass)
    EditText newPass;
    @BindView(R.id.newPass2)
    EditText newPass2;
    @BindView(R.id.btnSignup)
    Button btnSignup;

    private View focusView;
    boolean iCheckEmail = false;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);
        mApiService = ApiClient.getClient().create(ApiService.class); // Initialize Api client helper

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitNewUser();
            }
        });


    }

    private void submitNewUser() {
        if (!performValidation()) {
            return;
        } else {
            mApiService.signupRequest(newUser.getText().toString(), newEmail.getText().toString(), newPass.getText().toString(), newPass2.getText().toString())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if (response.isSuccessful()){
                                Log.i("Debug", "onResponse: Registration Success");

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("message").equals("Successfully created user!")){
                                        Toast.makeText(SignupActivity.this, "Successfully created user!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    } else {
                                        String error_message = jsonRESULTS.getString("error_msg");
                                        Toast.makeText(SignupActivity.this, error_message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.i("debug", "onResponse: Registration failed");

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(SignupActivity.this, "Please check nternet connection", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private boolean performValidation() {
        boolean isSuccessful = true;

        // Reset errors.
        newUser.setError(null);
        newEmail.setError(null);
        newPass.setError(null);

        // Store values at the time of the login attempt.
        String username = newUser.getText().toString();
        String password = newPass.getText().toString();
        String password2 = newPass2.getText().toString();
        String nEmail = newEmail.getText().toString();

        // Check for a valid password, if the user entered one.
        if (password.length() < 4) {
//            tilPassword.setError(getString(R.string.error_invalid_password));
            focusView = newPass;
            isSuccessful = false;
        }
        if (!password2.equals(password)) {
            focusView = newPass2;
            isSuccessful = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
//            tilUsername.setError(getString(R.string.error_require_username));
            focusView = newUser;
            isSuccessful = false;
        }

        // Check for default password
        if (password.equalsIgnoreCase("Abcd1234%") || password.equalsIgnoreCase("Digi1234%")) {
//            ErrorCode.showErrorDialog(this,"Authentication Error","This is a temporary password. Please change password in MCP.").show();
            focusView = newPass;
            isSuccessful = false;
        }

        if (!isValidEmail(nEmail)) {
            Toast.makeText(this, "Error : Invalid email address ", Toast.LENGTH_SHORT).show();
            focusView = newEmail;
        } else {
            iCheckEmail = true;
        }

        if (!isSuccessful) {
            focusView.requestFocus();
        }
        return isSuccessful;
    }

    /*
     * Validate idEmail, Min 5 Char, Max 25 Char
     */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}

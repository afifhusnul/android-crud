package test1.android.com.test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Test2Activity extends AppCompatActivity {

    private static final String TAG = "YouQi";

    @BindView(R.id.btnTest)
    Button btnTest;
    @BindView(R.id.blur_layout) RelativeLayout rlBlur;

//    @BindView(R.id.btnSubmit) Button btnOk;
//    @BindView(R.id.btnCancel) Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        ButterKnife.bind(this);

//        Button btnTest = (Button) findViewById(R.id.btnTest);
        rlBlur.setVisibility(View.GONE);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Test -1 -------------
                //---------------------
//                // Dialog to input manual barcode of simcard
                AlertDialog.Builder barCodeDialog = new AlertDialog.Builder(Test2Activity.this);
//                final EditText input = new EditText(Test2Activity.this);
//
                barCodeDialog.setTitle("Barcode Manual Input");
                barCodeDialog.setMessage("The current balance in your CPP wallet is insufficient. Please top up your CPP wallet to proceed.");
                barCodeDialog.setCancelable(false);


//                        .setView(input);
//                //Set keyboard only numbers
//                input.setInputType(InputType.TYPE_CLASS_NUMBER);
//                input.setText("8960");
//
//                //Set max input only 20 Char
//                input.setFilters(new InputFilter[] {
//                        // Maximum 20 characters.
//                        new InputFilter.LengthFilter(20),
//                });
//
//                barCodeDialog.setPositiveButton(android.R.string.ok, null);
//                barCodeDialog.setNegativeButton(android.R.string.cancel, null);
//
                barCodeDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (!(input.getText().length() < 20)) {
//                            Intent resultIntent = new Intent();
//                            resultIntent.putExtra("iccid", input.getText().toString());
//                            setResult(Activity.RESULT_OK, resultIntent);
//                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Please enter full 20 digit", Toast.LENGTH_SHORT).show();
//                            Boolean wantToCloseDialog = false;
//                            if(wantToCloseDialog) {
//
//                            }
//
//                        }
//                        finish();
                        return;
                    }
                });
//
//                barCodeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Back to previous activity
//                        finish();
//                    }
//                });
                barCodeDialog.show();

                //--- Test 2-------
                //-----------------
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                AlertDialog.Builder builder = new AlertDialog.Builder(Test2Activity.this);
////                AlertDialog.Builder builder = new AlertDialog.Builder(Test2Activity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                builder.setTitle("Test Stay");
//
//                final EditText input = new EditText(Test2Activity.this);
////                builder.setView(getLayoutInflater().inflate(R.layout.my_dialog, null));
//                builder.setPositiveButton(android.R.string.ok, null);
//                builder.setNegativeButton(android.R.string.cancel, null);
//                builder.setTitle("Barcode Manual Input").setView(input);
//                input.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//                input.setText("8960");
//                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
//                input.requestFocus();
//
//
//                //Set max input only 20 Char
//                input.setFilters(new InputFilter[]{
//                        // Maximum 20 characters.
//                        new InputFilter.LengthFilter(20),
//                });
//
//                final AlertDialog dialog = builder.create();
//                dialog.show();
//                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (!(input.getText().length() < 20)) {
////                            Intent resultIntent = new Intent();
////                            resultIntent.putExtra("iccid", input.getText().toString());
////                            setResult(Activity.RESULT_OK, resultIntent);
//                                    finish();
//                                    dialog.dismiss();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Please enter full 20 digit", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//
//                            }
//                        });
//
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "Bye...", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        });
//
//            }
//        });

                //--- Test3 ---

//                rlBlur.setVisibility(View.VISIBLE);
//                //set up dialog
//
//                final Dialog dialog = new Dialog(Test2Activity.this);
//                dialog.setContentView(R.layout.dialog_simpack_barcode);
//                dialog.setCancelable(false);
//                dialog.getWindow().setDimAmount(0.0f);   //Set black background behind dialog window
//                //there are a lot of settings, for dialog, check them all out!
//
//                //set up text
//                final TextView input = (TextView) dialog.findViewById(R.id.barcodeID);
//                final TextView txtCount = (TextView) dialog.findViewById(R.id.textViewCount);
//                final String prefix = "8960";
//
//                Button btnOk = (Button) dialog.findViewById(R.id.btnSubmit);
//                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
//                input.setInputType(InputType.TYPE_CLASS_NUMBER);
//                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20),
//                        //Prevent from deleteion of prefix --> 8960
//                        new InputFilter() {
//                            @Override
//                            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int
//                                    dend) {
//                                return dstart < prefix.length() ? dest.subSequence(dstart, dend) : null;
//                            }
//                        }
//                });
//
////                input.setText("8960");
//                input.requestFocus();
//                txtCount.setText(String.valueOf(input.getText().length()));
//
//                btnOk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!(input.getText().length() < 20)) {
////                            Intent resultIntent = new Intent();
////                            resultIntent.putExtra("iccid", input.getText().toString());
////                            setResult(Activity.RESULT_OK, resultIntent);
//                            dialog.dismiss();
//                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Please enter full 20 digit", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    }
//                });
//
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                        finish();
//                    }
//                });
//                input.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                        txtCount.setText(String.valueOf(s.length()));
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        //txtCount.setText("Total Digit "+txtCount.length());
//                        txtCount.setText(String.valueOf(s.length()));
//                        Log.i("TAG", "Sequence " + s);
//                        Log.i("TAG", "Start " + start);
//                        Log.i("TAG", "Before " + before);
//                        Log.i("TAG", "Count " + count);
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        Log.i("TAG", "afterTextChanged: " + s);
//
//                    }
//                });
//                //now that the dialog is set up, it's time to show it
//                Window window = dialog.getWindow();
//                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(000000));
////                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//
////                Bitmap map=takeScreenShot(Test2Activity.this);
////
////                Bitmap fast=fastblur(map, 10);
////                final Drawable draw=new BitmapDrawable(getResources(),fast);
////                dialog.getWindow().setBackgroundDrawable(draw);
//                dialog.show();
//
            }
        });
    }
}

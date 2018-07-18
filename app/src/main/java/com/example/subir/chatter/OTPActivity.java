package com.example.subir.chatter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    final Context context = this;
    private Button button;
    Dialog dialog;

    EditText et1, et2, et3, et4;
    View view;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //button = findViewById(R.id.buttonShowCustomDialog);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_otp);
        dialog.show();

        et1 = dialog.findViewById(R.id.et1);
        et2 = dialog.findViewById(R.id.et2);
        et3 = dialog.findViewById(R.id.et3);
        et4 = dialog.findViewById(R.id.et4);



        et1.addTextChangedListener(watch);
        et2.addTextChangedListener(watch);
        et3.addTextChangedListener(watch);
        et4.addTextChangedListener(watch);

    }


    TextWatcher watch = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            view = dialog.getCurrentFocus();
            switch(view.getId())
            {
                case R.id.et1:
                    if(text.length()==1)

                        et2.requestFocus();
                    break;
                case R.id.et2:
                    if(text.length()==1)
                        et3.requestFocus();
                    break;
                case R.id.et3:
                    if(text.length()==1)
                        et4.requestFocus();
                    break;
                case R.id.et4:
                    break;
            }

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub
            //output.setText(s);
        }
    };
}
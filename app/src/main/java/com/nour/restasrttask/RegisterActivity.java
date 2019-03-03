package com.nour.restasrttask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    //Define Views
    EditText et_name, et_reg_email, et_reg_mobile, et_hospital, et_university, et_clinicAddress, et_birthDate, et_goverment, et_password, et_confirmPassword;
    RadioGroup radioGroup;
    RadioButton rd_male, rd_female;
    Button btnNext, btnBack;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        addListeners();
    }


    private void findViews() {

        et_name = findViewById(R.id.et_name);
        et_reg_email = findViewById(R.id.et_reg_email);
        et_reg_mobile = findViewById(R.id.et_reg_mobile);
        et_hospital = findViewById(R.id.et_hospital);
        et_university = findViewById(R.id.et_university);
        et_clinicAddress = findViewById(R.id.et_clinicAddress);
        et_birthDate = findViewById(R.id.et_birthDate);
        et_goverment = findViewById(R.id.et_goverment);
        et_password = findViewById(R.id.et_password);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.male) {
                    Toast.makeText(getApplicationContext(), "male",
                            Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.female) {
                    Toast.makeText(getApplicationContext(), "female",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        rd_male = (RadioButton) findViewById(R.id.male);
        rd_female = (RadioButton) findViewById(R.id.female);
        btnNext = findViewById(R.id.btn_next);
        btnBack = findViewById(R.id.btn_back);
        dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

    }

    private void addListeners() {

        et_birthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = et_name.getText().toString();
                String email = et_reg_email.getText().toString();
                String password = et_password.getText().toString();
                String hospital = et_hospital.getText().toString();
                String clincAddress = et_clinicAddress.getText().toString();
                String university = et_university.getText().toString();
                String governate = et_goverment.getText().toString();
                String dateOfBirth = et_birthDate.getText().toString();
                String mobile = et_reg_mobile.getText().toString();

                if (validataRegister(name, email, password, mobile, hospital, clincAddress, university, dateOfBirth, governate)) {

                    Intent i = new Intent(RegisterActivity.this,
                            CompleteRegisterationActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("email", email);
                    i.putExtra("password", password);
                    i.putExtra("hospital", hospital);
                    i.putExtra("clincAddress", clincAddress);
                    i.putExtra("university", university);
                    i.putExtra("governate", governate);
                    i.putExtra("dateOfBirth", dateOfBirth);
                    i.putExtra("mobile", mobile);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId == rd_male.getId()) {
                        i.putExtra("gender", "male");
                    } else if (selectedId == rd_female.getId()) {
                        i.putExtra("gender", "female");
                    }

                    startActivity(i);
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/mm/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_birthDate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean validataRegister(String name, String email, String password, String mobile, String hospital, String clincAddress, String University, String dateOfBirth, String governate) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(this, "mobile is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(hospital)) {
            Toast.makeText(this, "hospital is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(clincAddress)) {
            Toast.makeText(this, "clincAddress is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(University)) {
            Toast.makeText(this, "University is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(dateOfBirth)) {
            Toast.makeText(this, "dateOfBirth is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(governate)) {
            Toast.makeText(this, "governate is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}

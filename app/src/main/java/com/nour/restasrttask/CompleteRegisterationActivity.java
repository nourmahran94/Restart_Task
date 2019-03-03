package com.nour.restasrttask;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class CompleteRegisterationActivity extends AppCompatActivity {

    //Define Views
    ImageButton btnAddIDImageBack, btnAddIdImageFront, btnAddWorkLicenceBack, btnAddWorkLicenceFront;
    Button btnBack, btnSignUp;

    // Define Variables

    String name, email, password, hospital, clincAddress, university, governate, dateOfBirth, mobile;
    static final int GALLERY_REQUEST_CODE = 0;
    String gender;
    int imageType = 0;
    String idImageBack = "";
    String idImageFront = "";
    String workLicenceBack = "";
    String workLicenceFront = "";


    File idImageBackfile;
    File idImageFrontfile;
    File workLicenceBackfile;
    File workLicenceFrontfile;

    RequestBody requestFileIdBack, requestFileIdFront, requestFileworkLicenceBack, requestFileWorkLicenceFront;

    // MultipartBody.Part is used to send  the actual file name
    MultipartBody.Part bodyIDBack, bodyIDFront, bodyWorkLicenceBack, bodyWorkLicenceFront;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registeration);
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");
            hospital = intent.getStringExtra("hospital");
            clincAddress = intent.getStringExtra("clincAddress");
            university = intent.getStringExtra("university");
            governate = intent.getStringExtra("governate");
            dateOfBirth = intent.getStringExtra("dateOfBirth");
            mobile = intent.getStringExtra("mobile");
            gender = intent.getStringExtra("gender");
        }
        ActivityCompat.requestPermissions(CompleteRegisterationActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        findViews();
        addListeners();

    }

    private void findViews() {
        btnBack = findViewById(R.id.btn_back);
        btnAddIDImageBack = findViewById(R.id.btn_id_card_back);
        btnAddIdImageFront = findViewById(R.id.btn_id_card_front);
        btnAddWorkLicenceBack = findViewById(R.id.btn_work_licence_back);
        btnAddWorkLicenceFront = findViewById(R.id.btn_work_licence_front);
        btnSignUp = findViewById(R.id.btn_SignUP);

    }

    private void addListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteRegisterationActivity.super.onBackPressed();
            }
        });

        btnAddIDImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType = 1;
                pickFromGallery();

            }
        });
        btnAddIdImageFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType = 2;
                pickFromGallery();

            }
        });
        btnAddWorkLicenceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType = 3;
                pickFromGallery();
            }
        });
        btnAddWorkLicenceFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType = 4;
                pickFromGallery();

            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validataFiles()) {

                    setFilesData();
                    registerOnNetwork();
                }
            }
        });
    }

    private void setFilesData() {

        /////////////////////////////////idImageBackfile/////////////////////////////////////////

        idImageBackfile = new File(idImageBack);
        requestFileIdBack =
                RequestBody.create(MediaType.parse("multipart/form-data"), idImageBackfile);

        bodyIDBack =
                MultipartBody.Part.createFormData("id_pic_back", idImageBackfile.getName(), requestFileIdBack);


        /////////////////////////////////idImageFrontfile/////////////////////////////////////////

        idImageFrontfile = new File(idImageFront);


        requestFileIdFront =
                RequestBody.create(MediaType.parse("multipart/form-data"), idImageFrontfile);

        bodyIDFront =
                MultipartBody.Part.createFormData("id_pic", idImageFrontfile.getName(), requestFileIdFront);

        /////////////////////////////////workLicenceBackfile/////////////////////////////////////////

        workLicenceBackfile = new File(workLicenceBack);

        requestFileworkLicenceBack =
                RequestBody.create(MediaType.parse("multipart/form-data"), workLicenceBackfile);

        bodyWorkLicenceBack =
                MultipartBody.Part.createFormData("license_pic_back", workLicenceBackfile.getName(), requestFileworkLicenceBack);

        /////////////////////////////////workLicenceFrontfile/////////////////////////////////////////

        workLicenceFrontfile = new File(workLicenceFront);

        requestFileWorkLicenceFront =
                RequestBody.create(MediaType.parse("multipart/form-data"), workLicenceFrontfile);

        bodyWorkLicenceFront =
                MultipartBody.Part.createFormData("license_pic", workLicenceFrontfile.getName(), requestFileWorkLicenceFront);


    }

    private void registerOnNetwork() {
        RequestBody fullNameBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), name);
        RequestBody emailBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody phoneBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), mobile);
        RequestBody passwordBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody hospitalBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), hospital);
        RequestBody clinckAddressBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), clincAddress);
        RequestBody governoteBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), governate);
        RequestBody genderBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody DBBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), dateOfBirth);
        RequestBody universityBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), university);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserModel> Call = apiInterface.normalRegister(bodyIDBack, bodyIDFront, bodyWorkLicenceBack, bodyWorkLicenceFront, fullNameBody, emailBody, passwordBody, phoneBody, hospitalBody, universityBody, clinckAddressBody, DBBody, governoteBody, genderBody);
        Call.enqueue(new Callback<UserModel>() {

            @Override

            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                System.out.println("---------- on response");

                if (response.isSuccessful()) {
                   // UserModel userModel = response.body();

                    Toast.makeText(CompleteRegisterationActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(CompleteRegisterationActivity.this, response.message(), Toast.LENGTH_SHORT).show();


            }


            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println("-------------error");

                Toast.makeText(CompleteRegisterationActivity.this, "error", Toast.LENGTH_SHORT).show();

            }

        });


    }

    private boolean validataFiles() {
        if (TextUtils.isEmpty(idImageBack)) {
            Toast.makeText(this, "IdImageBack is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(idImageFront)) {
            Toast.makeText(this, "IdImageFront is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(workLicenceBack)) {
            Toast.makeText(this, "workLicenceBack is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(workLicenceFront)) {
            Toast.makeText(this, "workLicenceFront is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();

                    if (imageType == 1) {
                        String picturePath = getPath(this, selectedImage );
                        btnAddIDImageBack.setImageURI(selectedImage);
                        idImageBack = picturePath;


                    }

                    if (imageType == 2) {
                        btnAddIdImageFront.setImageURI(selectedImage);
                        String picturePath = getPath(this, selectedImage );
                        idImageFront = picturePath;
                    }
                    if (imageType == 3) {
                        btnAddWorkLicenceBack.setImageURI(selectedImage);
                        String picturePath = getPath(this, selectedImage );

                        workLicenceBack = picturePath;

                    }
                    if (imageType == 4) {
                        btnAddWorkLicenceFront.setImageURI(selectedImage);
                        String picturePath = getPath(this, selectedImage );

                        workLicenceFront = picturePath;

                    }
                    break;
            }
    }

    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CompleteRegisterationActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

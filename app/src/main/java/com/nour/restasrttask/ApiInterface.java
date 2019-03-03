package com.nour.restasrttask;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<UserModel>
    userLogin(@Field("email") String email, @Field("password") String password);


    @FormUrlEncoded
    @POST("social/login")
    Call<UserModel>
    facebookLogin(@Field("social_id") String social_id , @Field("social_type") String social_type );


    @Multipart
    @POST("register")
    Call<UserModel> normalRegister(@Part MultipartBody.Part idCardBackfile,@Part MultipartBody.Part idCardFrontfile , @Part MultipartBody.Part workLicenceBackfile , @Part MultipartBody.Part workLicenceFrontfile ,
                                    @Part("full_name") RequestBody name , @Part("email") RequestBody email , @Part("password") RequestBody password , @Part("phone_number") RequestBody phoneNo ,@Part("hospital_name") RequestBody hospitalName , @Part("university") RequestBody university , @Part("clinc_address") RequestBody clinicAddress ,  @Part("date_of_birth") RequestBody dateOfBirth , @Part("governorate") RequestBody governate, @Part("gender") RequestBody gender  );

}
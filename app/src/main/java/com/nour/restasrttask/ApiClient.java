package com.nour.restasrttask;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

        public static String BASE_URL = "http://dermapedia.net/api/auth/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.MINUTES)
                    .writeTimeout(20, TimeUnit.MINUTES)
                    .readTimeout(20, TimeUnit.MINUTES).build();

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();

            }
            return retrofit;
        }

    }
package com.harvdev.githubuser.api;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver {
    public static final String base_url = "https://api.github.com/search/";


    private static Retrofit retrofit;
    private static Retroserver mInstance;
    public static Retrofit getClient(){
        if (retrofit == null){
            OkHttpClient client=new OkHttpClient();
            try {
                TLSSocketFactory tlsSocketFactory=new TLSSocketFactory();
                if (tlsSocketFactory.getTrustManager()!=null) {
                    client = new OkHttpClient.Builder()
                            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                            .build();
                }
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
    public static synchronized Retroserver getInstance(){
        if (mInstance == null){
            mInstance =  new Retroserver();
        }
        return mInstance;
    }
    public ApiRequest getApi(){
        return retrofit.create(ApiRequest.class);
    }
}

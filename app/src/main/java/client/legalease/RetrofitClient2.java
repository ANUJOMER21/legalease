package client.legalease;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.WebServices.TokenInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    public static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, final String token2) {
        TokenInterceptor interceptor=new TokenInterceptor();
        interceptor.setToken(token2);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
            .build();
        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return  retrofit;
    }
}

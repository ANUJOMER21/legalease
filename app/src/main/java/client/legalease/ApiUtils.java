package client.legalease;

import client.legalease.APIConstant.ApiConstant;
import client.legalease.WebServices.ApiService;

public class ApiUtils {
    private static final String BASE_URL = ApiConstant.BASE_URL;

    public ApiUtils() {
    }

    public static ApiService getApiService(String token) {
        return RetrofitClient2.getClient(BASE_URL, token).create(ApiService.class);
    }
}

package com.example.kikkiapp.Utils;

import retrofit2.Call;

public interface IApiResponseListener {
    void onApiResponseListener(Call<Object> call);
}

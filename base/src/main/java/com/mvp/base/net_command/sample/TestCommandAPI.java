package com.mvp.base.net_command.sample;


import com.mvp.base.net_command.bean.BaseResponse;
import com.mvp.base.net_command.bean.CommonParams;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TestCommandAPI {
    @POST("test/test")
    Observable<BaseResponse<TestResponse>> test(@Body CommonParams commonParams);
}

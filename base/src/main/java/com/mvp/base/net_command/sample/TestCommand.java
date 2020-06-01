package com.mvp.base.net_command.sample;


import com.mvp.base.net_command.BaseCommand;
import com.mvp.base.net_command.ObserverOnResultListener;

/**
 * Created by yy
 */
public class TestCommand extends BaseCommand<TestCommandAPI> {

    public TestCommand() {
        super(TestCommandAPI.class);
    }

    public void test(TestParameter params, ObserverOnResultListener<TestResponse> listener) {
        handleOnResultObserver(mApiService.test(getParams("destination", params)), listener);
    }
}



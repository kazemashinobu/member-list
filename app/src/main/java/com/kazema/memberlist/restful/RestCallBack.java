package com.kazema.memberlist.restful;

/**
 * Created by kazemashinobu on 2017/6/28.
 */

public interface RestCallBack {
    void onSuccess(String respond);

    void onFailed(String respond);
}

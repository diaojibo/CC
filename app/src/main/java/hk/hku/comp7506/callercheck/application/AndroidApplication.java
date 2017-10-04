package hk.hku.comp7506.callercheck.application;

import android.app.Application;

/**
 * Created by rocklct on 2017/10/2.
 */

public class AndroidApplication extends Application {
    private static AndroidApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AndroidApplication getInstance() {
        return instance;
    }

}

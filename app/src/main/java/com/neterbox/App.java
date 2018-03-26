package com.neterbox;



import com.neterbox.qb.SampleConfigs;
import com.neterbox.qb.configs.ConfigUtils;
import com.quickblox.sample.core.CoreApp;
import com.quickblox.sample.core.utils.ActivityLifecycle;

import java.io.IOException;

public class App extends CoreApp {
    private static final String TAG = App.class.getSimpleName();
    private static SampleConfigs sampleConfigs;
    String SAMPLE_CONFIG_FILE_NAME = "sample_config.json";

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycle.init(this);
        initSampleConfigs();
    }

    private void initSampleConfigs() {
        try {
            sampleConfigs = ConfigUtils.getSampleConfigs(SAMPLE_CONFIG_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SampleConfigs getSampleConfigs() {
        return sampleConfigs;
    }
}
package com.zhouwei.customadapter.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhouwei.customadapter.R;

/**
 * Created by zhouwei on 17/2/3.
 */

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        HomePageFragment fragment = new HomePageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
    }

}

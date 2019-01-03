package com.daiy.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daiy.learn.test_badge.BadgeTools;

public class TestBadgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        findViewById(R.id.show_badge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BadgeTools.showBadge(TestBadgeActivity.this, 6);
            }
        });
    }
}

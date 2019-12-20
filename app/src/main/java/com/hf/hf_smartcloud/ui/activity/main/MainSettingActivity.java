package com.hf.hf_smartcloud.ui.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hf.hf_smartcloud.R;

public class MainSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsetting);
        initTitle();
    }

    private void initTitle() {
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.liner:
//                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;

            case R.id.grid:
//                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
        }
//        dataList.clear();
//        getData();
//        recyclerView.setAdapter(loadMoreWrapper);
        return super.onOptionsItemSelected(item);
    }
}

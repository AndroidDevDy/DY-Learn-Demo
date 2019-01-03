package com.daiy.learn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daiy.dylib.adapter.recycler.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerActivity extends AppCompatActivity {
    List<String> stringList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        //构建显示内容
        initData();

        //
        recyclerView = findViewById(R.id.recyclerView);
        //listView方式显示
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        //修改分割线颜色
//        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.color.red));
//        recyclerView.addItemDecoration(itemDecoration);

        //gridView方式显示
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }

    private void initData() {
        if (stringList == null)
            stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add("Item" + i);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(TestRecyclerActivity.this)
                    .inflate(R.layout.item_text_layout, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(stringList.get(position));
            holder.checkBox.setChecked(stringList.get(position).equals("Item6"));
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            CheckBox checkBox;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.item_tv);
                checkBox = itemView.findViewById(R.id.item_box);
            }
        }
    }

    public void Add(View v) {
        initData();
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}

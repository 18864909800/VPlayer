package com.ashin.vplayer.listLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashin.vplayer.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<News> newsList = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = findViewById(R.id.recyclerview);
        for (int i = 0; i < 50; i++) {
            News news = new News();
            news.setTitle("标题" + i);
            news.setContent("内容" + i);
            newsList.add(news);
        }
        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ListActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHoder> {

        @NonNull
        @Override
        public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(ListActivity.this, R.layout.news_list, null);
            MyViewHoder myViewHoder = new MyViewHoder(view);
            return myViewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
            News news = newsList.get(position);
            holder.mTitle.setText(news.getTitle());
            holder.mContent.setText(news.getContent());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }
    }

    class MyViewHoder extends RecyclerView.ViewHolder {
        TextView mTitle;
        TextView mContent;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textView);
            mContent = itemView.findViewById(R.id.textView2);
        }
    }
}
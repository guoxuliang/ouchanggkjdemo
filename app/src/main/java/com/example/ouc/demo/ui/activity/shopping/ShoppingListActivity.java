package com.example.ouc.demo.ui.activity.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ouc.demo.R;

public class ShoppingListActivity extends AppCompatActivity {
    private TextView texttest;
    private ImageView btn_back_search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppinglist);
        texttest = findViewById(R.id.texttest);
        texttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ShoppingListActivity.this,ShoppingGoodsDetailsActivity.class);
                startActivity(intent);
            }
        });
        initViews();
    }

    private void initViews() {
        btn_back_search = findViewById(R.id.btn_back_search);
        btn_back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingListActivity.this.finish();
            }
        });
    }
}

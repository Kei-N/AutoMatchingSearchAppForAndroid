package com.example.keinomoto.automatchingsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // "My keywords"ボタン押下時の処理
        Button myKeyButton = (Button) findViewById(R.id.keyButton);
        myKeyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 第1引数：遷移元のActivityクラス、第2引数：遷移先のActivityクラス
                Intent intent = new Intent(MainActivity.this, MyKeywordsActivity.class);
                // 画面遷移
                startActivity(intent);
            }
        });

        // "CREATE !"ボタン押下時の処理
        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // ボタン押下時の処理を記述
            }
        });
    }

}
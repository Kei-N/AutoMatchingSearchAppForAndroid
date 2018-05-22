package com.example.keinomoto.automatchingsearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView createView;
    private ArrayAdapter<List<SearchInfo>> adapter;

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
                // Google検索URL取得
                String google_url = getString(R.string.google_url);
                // TODO DBからkeywordをランダムで2件取得
                String keyword1 = "プログラミング";
                String keyword2 = "筋トレ";

                HTTPCommunication httpCom = new HTTPCommunication(MainActivity.this);
                // GoogleAND検索結果をスクレイピングし、ListViewに表示
                httpCom.execute(google_url, keyword1, keyword2);
            }
        });
    }

}
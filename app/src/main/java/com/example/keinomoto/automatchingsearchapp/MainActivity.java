package com.example.keinomoto.automatchingsearchapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private ListView createView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createView = (ListView)findViewById(R.id.createResult);

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
                // DBからkeywordをランダムで2件取得
                List<String> keywordList = getKeyRand();
                if(keywordList.size() == 2){
                    String keyword1 = keywordList.get(0);
                    String keyword2 = keywordList.get(1);

                    HTTPCommunication httpCom = new HTTPCommunication(MainActivity.this, createView);
                    // GoogleAND検索結果をスクレイピングし、ListViewに表示
                    httpCom.execute(google_url, keyword1, keyword2);
                }
            }
        });

        // ListViewのアイテムクリック時
        createView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 選択アイテムの取得
                ListView listView = (ListView)parent;
                SearchInfo item = (SearchInfo)listView.getItemAtPosition(position);
                Log.d(TAG, "title:" + item.getTitle() + ", url:" + item.getUrl());
                // 選択したサイトに遷移
                Uri uri = Uri.parse(item.getUrl());
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });
    }

    // DBからkeywordをランダムで2件取得
    private List<String> getKeyRand(){

        List<String> kwList = new ArrayList<>();
        // DBのオープン処理
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        final SQLiteDatabase mydb = helper.getWritableDatabase();
        // SELECT
        Cursor cursor = mydb.rawQuery("SELECT * FROM keywords_tbl ORDER BY RANDOM() LIMIT 2",
                null);
        // ランダムに取得したキーワードをセット
        while(cursor.moveToNext()){
            kwList.add(cursor.getString(cursor.getColumnIndex("keyword")));
        }

        return kwList;
    }
}
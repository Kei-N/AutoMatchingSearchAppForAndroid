package com.example.keinomoto.automatchingsearchapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MyKeywordsActivity extends Activity {
    private final static String TAG = MyKeywordsActivity.class.getSimpleName();

    ListView myListView;

    /*  onCreate()
        Activity が一番最初にとる状態です。
        Activity が起動し、画面を構成するまでの仕事をここで行います。
        XML で定義したレイアウトを読み込んだり、View コンポーネントを取り出したりする処理を実行します。
    */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_keywords);

        myListView = (ListView)findViewById(R.id.myListView);
        // DBのオープン処理
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(getApplicationContext());
        final SQLiteDatabase mydb = helper.getWritableDatabase();
        // DB一覧表示
        dbListDisplay(myListView, mydb);

        // "Entry"ボタン押下時の処理
        Button entryButton = (Button) findViewById(R.id.entryButton);
        entryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 入力値からkeywordを取得
                final EditText editKeyText = (EditText) findViewById(R.id.editKeyText);
                String keyword = editKeyText.getText().toString();
                // DBに登録処理
                ContentValues values = new ContentValues();
                values.put("keyword", keyword);
                mydb.insert("keywords_tbl", null, values);
                // DBから値を全て取得し、昇順にDB一覧を表示する
                dbListDisplay(myListView, mydb);
            }
        });

        // ListViewのアイテムクリック時
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                // 選択アイテムの取得
                ListView listView = (ListView)parent;
                Cursor cursor = (Cursor)listView.getItemAtPosition(position);
                String keyword = cursor.getString(
                        cursor.getColumnIndex("keyword"));
                // DBから削除処理
                ContentValues values = new ContentValues();
                values.put("keyword", keyword);
                mydb.delete("keywords_tbl", "keyword=?", new String[]{keyword});
                // DBから値を全て取得し、昇順にDB一覧を表示する
                dbListDisplay(myListView, mydb);
            }
        });

        // "Home"ボタン押下時の処理
        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(MyKeywordsActivity.this, MainActivity.class);
               startActivity(intent);
           }
        });

        // DBのclose
//        mydb.close();
    }

    // DB一覧表示
    private void dbListDisplay(ListView myListView, SQLiteDatabase mydb){
        // SELECT
        Cursor cursor = mydb.rawQuery("SELECT * FROM keywords_tbl", null);
        // 表示するカラム名
        String[] from = {"keyword"};
        // バインド先のID
        int[] to = {android.R.id.text1};
        //adapter生成
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_1, cursor, from, to, 0);
        // bindして表示
        myListView.setAdapter(adapter);
        // カーソルのclose
//        cursor.close();
    }
}
package com.example.keinomoto.automatchingsearchapp

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter

class MyKeywordsActivity : Activity() {

    /*  onCreate()
        Activity が一番最初にとる状態です。
        Activity が起動し、画面を構成するまでの仕事をここで行います。
        XML で定義したレイアウトを読み込んだり、View コンポーネントを取り出したりする処理を実行します。
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_keywords)

        var myListView: ListView = findViewById<View>(R.id.myListView) as ListView
        // DBのオープン処理
        val helper = MySQLiteOpenHelper(applicationContext)
        val mydb = helper.writableDatabase
        // DB一覧表示
        dbListDisplay(myListView, mydb)

        // "Entry"ボタン押下時の処理
        val entryButton = findViewById<View>(R.id.entryButton) as Button
        entryButton.setOnClickListener {
            // 入力値からkeywordを取得
            val editKeyText = findViewById<View>(R.id.editKeyText) as EditText
            val keyword = editKeyText.text.toString()
            // DBに登録処理
            val values = ContentValues()
            values.put("keyword", keyword)
            mydb.insert("keywords_tbl", null, values)
            // DBから値を全て取得し、昇順にDB一覧を表示する
            dbListDisplay(myListView, mydb)
        }

        // ListViewのアイテムクリック時
        myListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 選択アイテムの取得
            val listView = parent as ListView
            val cursor = listView.getItemAtPosition(position) as Cursor
            val keyword = cursor.getString(
                    cursor.getColumnIndex("keyword"))
            // DBから削除処理
            val values = ContentValues()
            values.put("keyword", keyword)
            mydb.delete("keywords_tbl", "keyword=?", arrayOf(keyword))
            // DBから値を全て取得し、昇順にDB一覧を表示する
            dbListDisplay(myListView, mydb)
        }

        // "Home"ボタン押下時の処理
        val homeButton = findViewById<View>(R.id.homeButton) as Button
        homeButton.setOnClickListener {
            val intent = Intent(this@MyKeywordsActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // DBのclose
        //        mydb.close();
    }

    // DB一覧表示
    private fun dbListDisplay(myListView: ListView, mydb: SQLiteDatabase) {
        // SELECT
        val cursor = mydb.rawQuery("SELECT * FROM keywords_tbl", null)
        // 表示するカラム名
        val from = arrayOf("keyword")
        // バインド先のID
        val to = intArrayOf(android.R.id.text1)
        //adapter生成
        val adapter = SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_1, cursor, from, to, 0)
        // bindして表示
        myListView.adapter = adapter
        // カーソルのclose
        //        cursor.close();
    }

    companion object {
        private val TAG = MyKeywordsActivity::class.java.simpleName
    }
}
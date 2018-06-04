package com.example.keinomoto.automatchingsearchapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var createView: ListView? = null

    // DBからkeywordをランダムで2件取得
    private// DBのオープン処理
    // SELECT
    // ランダムに取得したキーワードをセット
    val keyRand: List<String>
        get() {

            val kwList = ArrayList<String>()
            val helper = MySQLiteOpenHelper(applicationContext)
            val mydb = helper.writableDatabase
            val cursor = mydb.rawQuery("SELECT * FROM keywords_tbl ORDER BY RANDOM() LIMIT 2",
                    null)
            while (cursor.moveToNext()) {
                kwList.add(cursor.getString(cursor.getColumnIndex("keyword")))
            }

            return kwList
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createView = findViewById<View>(R.id.createResult) as ListView

        // "My keywords"ボタン押下時の処理
        val myKeyButton = findViewById<View>(R.id.keyButton) as Button
        myKeyButton.setOnClickListener {
            // 第1引数：遷移元のActivityクラス、第2引数：遷移先のActivityクラス
            val intent = Intent(this@MainActivity, MyKeywordsActivity::class.java)
            // 画面遷移
            startActivity(intent)
        }

        // "CREATE !"ボタン押下時の処理
        val createButton = findViewById<View>(R.id.createButton) as Button
        createButton.setOnClickListener {
            // ボタン押下時の処理を記述
            // Google検索URL取得
            val google_url = getString(R.string.google_url)
            // DBからkeywordをランダムで2件取得
            val keywordList = keyRand
            if (keywordList.size == 2) {
                val keyword1 = keywordList[0]
                val keyword2 = keywordList[1]

                val httpCom = HTTPCommunication(this@MainActivity, createView)
                // GoogleAND検索結果をスクレイピングし、ListViewに表示
                httpCom.execute(google_url, keyword1, keyword2)
            }
        }

        // ListViewのアイテムクリック時
        createView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 選択アイテムの取得
            val listView = parent as ListView
            val item = listView.getItemAtPosition(position) as SearchInfo
            Log.d(TAG, "title:" + item.title + ", url:" + item.url)
            // 選択したサイトに遷移
            val uri = Uri.parse(item.url)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
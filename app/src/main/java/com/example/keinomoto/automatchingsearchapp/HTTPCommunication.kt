package com.example.keinomoto.automatchingsearchapp

import android.app.Activity
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.io.IOException
import java.net.URLDecoder
import java.util.ArrayList

/*
 * AsyncTask<型1, 型2,型3>
 *
 *   型1 … Activityからスレッド処理へ渡したい変数の型
 *          ※ Activityから呼び出すexecute()の引数の型
 *          ※ doInBackground()の引数の型
 *
 *   型2 … 進捗度合を表示する時に利用したい型
 *          ※ onProgressUpdate()の引数の型
 *
 *   型3 … バックグラウンド処理完了時に受け取る型
 *          ※ doInBackground()の戻り値の型
 *          ※ onPostExecute()の引数の型
 *
 *   ※ それぞれ不要な場合は、Voidを設定すれば良い
 */
class HTTPCommunication(private val mActivity: Activity, private var createView: ListView?) : AsyncTask<String, Void, MutableList<SearchInfo>>() {
    private var adapter: SearchResultAdapter? = null

    /*
     * バックグラウンドで実行する処理
     *
     *  @param params: Activityから受け渡されるデータ
     *  @return onPostExecute()へ受け渡すデータ
     */
    override fun doInBackground(vararg params: String): MutableList<SearchInfo> {
        //        android.os.Debug.waitForDebugger();
        val google_url = params[0]
        val keyword1 = params[1]
        val keyword2 = params[2]
        // 検索結果格納用リスト生成
        val searchResultList = mutableListOf<SearchInfo>()

        try {
            // Documentクラスの変数を作成し、その変数に取得したHTML情報を代入
            val document = Jsoup.connect(google_url)
                    .data("query", keyword1, "query", keyword2)
                    .timeout(3000).get()
            // 取得したタグ情報を取得する
            val elements = document.select("h3 a")

            var searchId: Int = 1
            // HTMLのテキストを取得する「textメソッド」
            // 属性の値を取得する「attrメソッド」
            for (element in elements) {
                // Google検索結果を格納する
                val searchInfo = SearchInfo()
                // WebサイトIDを付与
                searchInfo.searchId = searchId
                searchId++
                // タイトルを取得
                searchInfo.title = URLDecoder.decode(element.text(), "UTF-8")
                // URLを取得
                searchInfo.url = element.attr("href")
                // 検索結果をリストに追加
                searchResultList.add(searchInfo)
                Log.d(TAG, "scraping result: title" + searchInfo.title
                        + ", URL" + searchInfo.url)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return searchResultList
    }

    /*
     * メインスレッドで実行する処理
     *
     *  @param result: doInBackground()から受け渡されるデータ
     */
    override fun onPostExecute(result: MutableList<SearchInfo>) {
        createView = mActivity.findViewById<ListView>(R.id.createResult)
        adapter = SearchResultAdapter(mActivity)
        adapter!!.setSearchInfoList(result)
        createView!!.adapter = adapter

    }

    companion object {
        private val TAG = HTTPCommunication::class.java.simpleName
    }

}

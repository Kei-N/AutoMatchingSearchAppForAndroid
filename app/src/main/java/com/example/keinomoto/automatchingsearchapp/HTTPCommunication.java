package com.example.keinomoto.automatchingsearchapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
public class HTTPCommunication extends AsyncTask<String, Void, List<SearchInfo>> {
    private final static String TAG = HTTPCommunication.class.getSimpleName();

    private Activity mActivity;
    private ListView createView;
    private SearchResultAdapter adapter;


    public HTTPCommunication(Activity activity, ListView createView){
        mActivity = activity;
        this.createView = createView;
    }

    // TODO doInBackgroundが呼ばれない
    /*
     * バックグラウンドで実行する処理
     *
     *  @param params: Activityから受け渡されるデータ
     *  @return onPostExecute()へ受け渡すデータ
     */
    @Override
    protected List<SearchInfo> doInBackground(String... params) {
//        android.os.Debug.waitForDebugger();
        String google_url = params[0];
        String keyword1 = params[1];
        String keyword2 = params[2];
        // 検索結果格納用リスト生成
        List<SearchInfo> searchResultList = new ArrayList<>();

        try {
            // Documentクラスの変数を作成し、その変数に取得したHTML情報を代入
            Document document = Jsoup.connect(google_url)
                    .data("query", keyword1, "query", keyword2)
                    .timeout(3000).get();
            // 取得したタグ情報を取得する
            Elements elements = document.select("h3 a");

            Integer searchId = 1;
            // HTMLのテキストを取得する「textメソッド」
            // 属性の値を取得する「attrメソッド」
            for (Element element : elements) {
                // Google検索結果を格納する
                SearchInfo searchInfo = new SearchInfo();
                // WebサイトIDを付与
                searchInfo.setSearchId(searchId);
                searchId++;
                // タイトルを取得
                searchInfo.setTitle(URLDecoder.decode(element.text(), "UTF-8"));
                // URLを取得
                searchInfo.setUrl(element.attr("href"));
                // 検索結果をリストに追加
                searchResultList.add(searchInfo);
                Log.d(TAG, "scraping result: title" + searchInfo.getTitle()
                + ", URL" + searchInfo.getUrl());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResultList;
    }

    /*
     * メインスレッドで実行する処理
     *
     *  @param result: doInBackground()から受け渡されるデータ
     */
    @Override
    protected void onPostExecute(List<SearchInfo> result) {
        // TODO Create結果をListViewに表示
        createView = (ListView)mActivity.findViewById(R.id.createResult);
        adapter = new SearchResultAdapter(mActivity);
        adapter.setSearchInfoList(result);
        createView.setAdapter(adapter);

    }

}

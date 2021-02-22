package cn.tim.imooc_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.main_lv);
        new RequestDataAsyncTask().execute();
    }

    class RequestDataAsyncTask extends AsyncTask<Void, Void, String> {
        String TAG = "RequestDataAsyncTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return requestUrl("http://www.imooc.com/api/teacher?type=2&page=1");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // 数据处理，刷新页面
            try {
                Log.i(TAG, "onPostExecute: s = " + s);
                JSONObject jsonObject = new JSONObject(s);
                List<ResponseData> data = JSON.parseArray(jsonObject.getString("data"), ResponseData.class);
                Log.i(TAG, "onPostExecute: data's size = " + data.size());
                listView.addHeaderView(getLayoutInflater().inflate(R.layout.header_imooc_list, null));
                listView.setAdapter(new ResponseDataAdapter(data, MainActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String requestUrl(String urlString) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.connect();
                int responseCode = con.getResponseCode();
                String responseMessage = con.getResponseMessage();
                Log.i(TAG, "requestUrl: responseMessage = " + responseMessage);
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStreamReader isReader = new InputStreamReader(con.getInputStream());
                    BufferedReader reader = new BufferedReader(isReader);
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                    return builder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
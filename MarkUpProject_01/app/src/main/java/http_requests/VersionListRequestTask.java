package http_requests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.miaodonghan.markupproject.MainActivity;
import com.example.miaodonghan.markupproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by miaodonghan on 3/20/16.
 */
public class VersionListRequestTask extends AsyncTask<String, Integer, List<VersionListRequestTask.VersionItem>> {

    ListView listview;
    Context context;

    int version_selected_id;
    int doc_id;
    List<Map<String, String>> data_version = new ArrayList<Map<String, String>>();

    public VersionListRequestTask(Context context, ListView listview, int version_selected_id, int doc_id) {
        this.listview = listview;
        this.context = context;
        this.version_selected_id = version_selected_id;
        this.doc_id=doc_id;
    }



    static class VersionItem {
        public VersionItem(String id, String name,String content, String updatedAt) {
            this.id = id;
            this.name = name;
            this.content = content;
            this.updatedAt = updatedAt;
        }

        String id;
        String name;
        String content;
        String updatedAt;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign

    }

    @Override
    protected List<VersionListRequestTask.VersionItem> doInBackground(String... uri) {
        List<VersionItem> docList = new ArrayList<>();

        try {
            InputStream response = new URL(uri[0]).openStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";

            JSONArray array = new JSONArray(res);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                VersionItem item = new VersionItem(
                        obj.getString("id"),
                        obj.getString("name"),
                        obj.getString("content"),
                        obj.getString("updatedAt")
                );
                docList.add(item);

            }

        } catch (Exception ex) {
            Log.e("backgroud task", ex.getMessage());
        }

        return docList;
    }

    @Override
    protected void onPostExecute(List<VersionListRequestTask.VersionItem> docList) {

        // close a spinning sign

        for (int i = 0; i < docList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", docList.get(i).id);
            map.put("name", docList.get(i).name);
            map.put("content",docList.get(i).content);
            map.put("updatedAt", docList.get(i).updatedAt);
            data_version.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(context, data_version,
                R.layout.version_item, new String[]{"id","name","content", "updatedAt"},
                new int[]{R.id.version_id, R.id.version_name,R.id.version_content, R.id.version_updatedAt}
        );

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
//                Intent intent = new Intent(context, MainActivity.class);
//                selected_id = Integer.parseInt(data.get(position).get("id"));
//                intent.putExtra("position", selected_id);
//                context.startActivity(intent);
                Intent intent = new Intent(context, MainActivity.class);
                version_selected_id = Integer.parseInt(data_version.get(position).get("id"));
                intent.putExtra("version_position", version_selected_id);
                intent.putExtra("doc_position", doc_id);
                context.startActivity(intent);
            }

        });


    }
}


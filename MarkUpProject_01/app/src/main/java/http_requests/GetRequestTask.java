package http_requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class GetRequestTask extends AsyncTask<String, Integer, String> {


    Context context;

    int version_selected_id;
    EditText editor;

    public GetRequestTask(Context context, int version_selected_id, EditText editor) {

        this.context = context;
        this.version_selected_id = version_selected_id;
        this.editor =editor;
    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... uri) {
        String result ="";
        //String name = "";
        try {

            InputStream response = new URL(uri[0]).openStream();

            Scanner s = new Scanner(response).useDelimiter("\\A");
            String res = s.hasNext() ? s.next() : "";
            Log.e("++++++++++++++++", res);


            JSONObject obj = new JSONObject(res);


            result = obj.getString("name")+"\n\n"+obj.getString("content");


        } catch (Exception ex) {
           // Log.e("backgroud task", ex.getMessage());
        }

        return result;
    }

    //@Override
    protected void onPostExecute(String result) {

         editor.setText(result);


    }
}
package http_requests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;
import com.example.miaodonghan.markupproject.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class LoginRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    String res;
    int error_code;
    String ip;
    String token;
    String expires;
    SharedPreferences sharedPreferences;

    public LoginRequestTask(Context context, String ip) {
        this.context = context;
        this.ip = ip;
        this.sharedPreferences = context.getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);

    }

    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
        String result = "";
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(ip + "/api/auth/login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            //urlConnection.setRequestProperty("access_token", "utf-8");

            JSONObject jsonParam = new JSONObject();
            //body
            jsonParam.put("email", data[0]);
            jsonParam.put("password", data[1]);

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" + requestData.getBytes().length);

            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            Log.e("LoginRequestTask", requestData);



//            InputStream e1 = new BufferedInputStream(urlConnection.getErrorStream());
//            Scanner s1 = new Scanner(e1).useDelimiter("\\A");
//            error = s1.hasNext() ? s1.next() : "";
            error_code = urlConnection.getResponseCode();

            if (error_code == 403) {
                Log.e("error code:", urlConnection.getResponseCode() + "");
                return result;
            } else {

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Scanner s = new Scanner(in).useDelimiter("\\A");
                res = s.hasNext() ? s.next() : "";
                result = res;
                Log.e("login response:", res);

                return res;
            }

        } catch (Exception ex) {
            Log.e("error", ex.toString());

        } finally {
            urlConnection.disconnect();
        }

        return result;
    }


    //@Override
    protected void onPostExecute(String result) {


        if (error_code == 403) {
            Toast.makeText(context, "Invalid email or password!!!.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(context, MainActivity.class);

            try {
                JSONObject r = new JSONObject(result);

                token = r.getString("token");
                expires = r.getString("expires");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LoginActivity.Token_s, token);
            editor.putString(LoginActivity.Expires_s, expires);

            editor.commit();

            context.startActivity(intent);
        }


    }
}

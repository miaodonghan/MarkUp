package http_requests;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.miaodonghan.markupproject.LoginActivity;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by miaodonghan on 4/10/16.
 */
public class RegisterRequestTask extends AsyncTask<String, Integer, String> {

    Context context;
    String email;
    String pwd;
    String ip;
    SharedPreferences sharedPreferences;
    int response_code;



    public RegisterRequestTask(Context context, String ip, SharedPreferences sharedPreferences) {

        this.context = context;
        this.ip = ip;
        this.sharedPreferences =sharedPreferences;

    }
    @Override
    protected void onPreExecute() {
        // start a spinning sign
    }

    @Override
    protected String doInBackground(String... data) {
        String result = "";

        HttpURLConnection urlConnection = null;
        //String url = " http://192.168.155.6:1337/api/doc/" + data[0];
        try {

            URL url = new URL( ip+ "/api/auth/register");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            //header
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            JSONObject jsonParam = new JSONObject();

            //body
            jsonParam.put("email", data[0]);

            jsonParam.put("password", data[1]);
            email =data[0];
            pwd =data[1];

            String requestData = jsonParam.toString();
            urlConnection.setRequestProperty("Content-Length", "" + requestData.getBytes().length);
            DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

            out.writeBytes(requestData);
            out.flush();
            out.close();
            Log.e("====:", requestData);

           // Log.i("err222",urlConnection.getResponseCode()+"");
            // if user has register, response code is 400(bad request)



            //print error ifo
//            InputStream e1 = new BufferedInputStream(urlConnection.getErrorStream());
//            Scanner s1 = new Scanner(e1).useDelimiter("\\A");
//            String r_e1 = s1.hasNext() ? s1.next() : "";
//            Log.e("err1:", r_e1);
//            s1.close();

            response_code = urlConnection.getResponseCode();
            if(urlConnection.getResponseCode() == 400){
                return "";
            }else{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Scanner s = new Scanner(in).useDelimiter("\\A");
                String res = s.hasNext() ? s.next() : "";
                s.close();
                return res;
            }

        } catch (Exception ex) {

            Log.e("er55r", ex.toString());
        } finally {
            urlConnection.disconnect();
        }

        return result;
    }


    //@Override
    protected void onPostExecute(String result) {

        if(response_code == 400){
            //do nothing
            //Intent intent= new Intent(context,RegisterActivity.class);
            Toast.makeText(context, "User already exists!!! please change email.", Toast.LENGTH_SHORT).show();
            //context.startActivity(intent);
        }else{
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(LoginActivity.Email_s,email);
            editor.putString(LoginActivity.Password_s,pwd);
            editor.commit();
            Intent intent= new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }

    }
}

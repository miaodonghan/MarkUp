package com.example.miaodonghan.markupproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import us.feras.mdv.MarkdownView;

/**
 * Created by miaodonghan on 3/28/16.
 */
public class ShowPreviewActivity extends AppCompatActivity {
    Button create_btn;
    Button update_btn;
    SharedPreferences sharedPreferences;
    String ip;
    int version_id;
    int doc_id;
    //String editor_con = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        create_btn =(Button)findViewById(R.id.createbtn);
        update_btn =(Button)findViewById(R.id.updatebtn);
        sharedPreferences = getSharedPreferences(LoginActivity.Markup, Context.MODE_PRIVATE);
        ip = getString(R.string.ip_address);
        version_id =getIntent().getIntExtra("versionId",0);
        doc_id =getIntent().getIntExtra("docId",0);


       // editor_con = getIntent().getStringExtra("editor_c");
        Log.i("+++++++",MainActivity.editor_content);
        MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.loadMarkdown(MainActivity.editor_content);
    }

//    View.OnClickListener mySaveTtn = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            PutRequestTask putRequestTask = new PutRequestTask(ShowPreviewActivity.this,version_id,ip,doc_id,sharedPreferences);
//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart =editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart,text.length());
//            String token = sharedPreferences.getString(LoginActivity.Token_s, null);
//            if(token !=null){
//                putRequestTask.execute(""+version_id,name,content);
//                //String content = text;
//                //putRequestTask.execute(""+version_id,content);
//                Toast.makeText(ShowPreviewActivity.this, "save successfully", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(ShowPreviewActivity.this, "Please LoginActivity first!", Toast.LENGTH_LONG).show();
//            }
//
//        }
//    };
//    View.OnClickListener mySaveTtn_newversion = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//
//            PostRequestTask postRequestTask = new PostRequestTask(MainActivity.this,ip,doc_id,sharedPreferences);
//
//            String text = editor.getText().toString();
//            int start = editor.getLayout().getLineStart(0);
//            int end = editor.getLayout().getLineStart(1);
//            int cstart =editor.getLayout().getLineStart(2);
//            String name = text.substring(start, end);
//            String content = text.substring(cstart,text.length());
//            String token = sharedPreferences.getString(LoginActivity.Token_s,null);
//            if(token !=null){
//                //putRequestTask.execute(""+version_id,name,content);
//                //String content = text;
//                postRequestTask.execute(name, content);
//                Toast.makeText(MainActivity.this, "you created a new version successfully", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(MainActivity.this, "Please LoginActivity first!", Toast.LENGTH_LONG).show();
//            }
//
//        }
//    };
//
//    View.OnClickListener previewbtn_handler = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            editor_content = editor.getText().toString();
//            startActivity(new Intent(MainActivity.this, ShowPreviewActivity.class));
//
//        }
//    };
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.action_settings:
//                Toast.makeText(this, "Goto LoginActivity page", Toast.LENGTH_SHORT).show();
//                Intent intent= new Intent(this,LoginActivity.class);
//                startActivity(intent);
//                break;
//            default:
//                break;
//        }
//
//        return true;
//    }
}

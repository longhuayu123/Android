package com.example.yls.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private WebView contentWebView;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Button btnCall;
    private Button btnSend;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentWebView = (WebView) findViewById(R.id.webview);
        contentWebView.getSettings().setJavaScriptEnabled(true);

        Button  btnSend= (Button) findViewById(R.id.button4);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:10086"));
                intent.putExtra("sms_body","The SMS text");
                startActivity(intent);
            }
        });

        btnCall = (Button) findViewById(R.id.button3);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.CALL_PHONE
                    }, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {
                    callPhone();
                }
            }
        });


        contentWebView.loadUrl("file:///android_asset/web.html");
        contentWebView.addJavascriptInterface(MainActivity.this, "android");


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentWebView.loadUrl("javascript:javacalljs()");

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://mail.163.com'" + ")");
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @JavascriptInterface
    public void startFunction() {

        runOnUiThread(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_LONG).show();
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
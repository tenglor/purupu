package com.dodon.purupu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    Button button;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int permission = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                //if(permission == PackageManager.PERMISSION_DENIED){
                if(true){
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE,
                            1);
                }
                Log.d("A", "O");

                Intent intent = new Intent()
                        .setType("application/pdf")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 1:
                Uri selectedfile = data.getData();
                String path = selectedfile.getPath();
                if(path.contains("document/raw:")){
                    path = path.replace("/document/raw:","");
                }
                Intent intent = new Intent(MainActivity.this, ChapterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("path", path);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("A", "YES-YES-YES!");
                } else {
                    Log.d("A", "OH NO!");
                }
                return;
            }
        }
    }



}
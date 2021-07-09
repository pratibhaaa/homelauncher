package com.example.home_launcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Item> itemList = new ArrayList<>();
    RecyclerView rvItems;
    String versionName, versionCode;
    SearchView searchView;
    ItemData adapter;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm = getPackageManager();

        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo pkginfo : applicationInfos) {

            try {
                versionName = pm.getPackageInfo(getPackageName(), 0).versionName;
                versionCode = String.valueOf(pm.getPackageInfo(getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            itemList.add(new Item(pm.getApplicationLabel(pkginfo).toString(),
                    pkginfo.packageName, versionName, versionCode, pm.getApplicationIcon(pkginfo)));
            Collections.sort(itemList, new Comparator<Item>() {
                @Override
                public int compare(Item lhs, Item rhs) {
                    return lhs.getName().compareTo(rhs.getName());
                }
            });


        }
        searchView = findViewById(R.id.searchview);
        rvItems = findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adapter = new ItemData(this, itemList);
        rvItems.setAdapter(adapter);

        adapter.setListener(new ItemData.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openApp(itemList.get(position).getPack());

                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        search(searchView);

    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                try {
                    List<Item> list = adapter.filterList(newText);

                    rvItems.setAdapter(new ItemData(getApplicationContext(), list));

/*
                    rvItems.setAdapter(new ItemData( MainActivity.this,list, new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            openApp(itemList.get(i).getPack());
                        }
                    }));
*/
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "No retailer", Toast.LENGTH_SHORT).show();

                }

                return true;
            }
        });
    }

    public void onReceive(Context ctx, Intent intent) {
        Uri data = intent.getData();
        Log.d("TAG", "Action: " + intent.getAction());
        Log.d("TAG", "The DATA: " + data);


        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            System.out.println("installed:" + packageName + "package name of the program");
            Toast.makeText(ctx, "installed:" + packageName + "package name of the program", Toast.LENGTH_LONG).show();
        }
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("uninstall:" + packageName + "package name of the program");
            Toast.makeText(ctx, "uninstall:" + packageName + "package name of the program", Toast.LENGTH_LONG).show();
        }
    }

    private void getApps() {


    }

    private void openApp(String packageName) {

        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            startActivity(intent);
        }
    }
}
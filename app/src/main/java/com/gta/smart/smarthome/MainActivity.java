package com.gta.smart.smarthome;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;

import com.gta.smart.entrywindow.LoadingWin;
import com.gta.smart.household_ctrl.HouseholdCtrl;
import com.gta.smart.household_ctrl.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Context context;
    ViewPager mian_viewpager;
    List<View> views = new ArrayList<>();
    private String tag;
    private ImageButton setting_imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawlayout_main);
        tag = getLocalClassName();
        context = this;

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mian_viewpager = (ViewPager) findViewById(R.id.mian_viewpager);
        views.add(getLayoutInflater().inflate(R.layout.view_pager_layout_1, null));
        views.add(getLayoutInflater().inflate(R.layout.view_pager_layout_2, null));
        ViewPageAdapter adapter = new ViewPageAdapter(views);
        mian_viewpager.setAdapter(adapter);
        mian_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(tag, "position:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(tag, "state:" + state);
            }
        });

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setting_imgbtn = (ImageButton) findViewById(R.id.setting_imgbtn);
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_imgbtn:
                // 为控件添加旋转动画
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
                setting_imgbtn.startAnimation(animation);
                startActivity(new Intent(this, SettingClass.class));
                overridePendingTransition(R.anim.translate, R.anim.windowout);
                break;
            case R.id.imageView:
                startActivity(new Intent(context, LoadingWin.class));
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        setting_imgbtn.startAnimation(animation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        Log.i(tag, "onNatigationTtemSelected() onClicked");
        switch (item.getItemId()) {
            case R.id.nav_send:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(android.R.drawable.alert_light_frame);
                builder.setTitle("警告");
                builder.setMessage("确定退出应用程序？");
                builder.setCancelable(false);
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setPositiveButton("No", null);
                builder.show();
                break;
        }
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.a1725121023_wengyuxian_final;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import fragment.HistoryFragment;
import fragment.IndexFragment;
import fragment.IntroduceFragment;
import dto.PostData;
import dto.ResultData;
import service.TranslateService;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private TextView textView;
    private List<Fragment> fragments;
    private BottomNavigationView bottomNavigationView;

    public String FILE_PATH;
    private static int REQUEST_PERMISSION_CODE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
        FILE_PATH = getFilesDir() + "/data.txt";

        //初始化控件
        initViews();
        // 初始化导航
        initNavigation();
        //初始化PageAdapter
        initAdapter();

    }

    // 返回路径
    public String getPath() {
        return FILE_PATH;
    }

    //初始化控件
    private void initViews() {
        viewPager = findViewById(R.id.main_container);
        bottomNavigationView = findViewById(R.id.navigation);
    }

    //初始化PageAdapter
    private void initAdapter() {
        //将三个Fragment加入集合中
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        HistoryFragment historyFragment = new HistoryFragment();
        fragments.add(historyFragment);
        Bundle bundle = new Bundle();
        bundle.putString("path", FILE_PATH);//这里的values就是我们要传的值
        historyFragment.setArguments(bundle);
        fragments.add(new IntroduceFragment());

        //初始化适配器
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        //设置Adapter
        viewPager.setAdapter(fragmentPagerAdapter);
        //设置监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    //初始化导航栏
    private void initNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    // 翻译界面点击按钮
    public void onclick(View v) {

        textView = findViewById(R.id.textView);
        PostData postData = new PostData();
        String inputString = "";
        EditText editText1 = findViewById(R.id.editText);
        inputString = editText1.getText().toString();
        postData.setQ(inputString);
        Spinner spinner;
        spinner = findViewById(R.id.spinner1);
        String value = spinner.getSelectedItem().toString();
        // 设置翻译结果语言
        postData.setFrom("auto");
        if ("中文".equals(value)) {
            postData.setTo("zh");
        }
        if ("英语".equals(value)) {
            postData.setTo("en");
        }
        if ("文言文".equals(value)) {
            postData.setTo("wyw");
        }
        if ("日语".equals(value)) {
            postData.setTo("jp");
        }
        if ("法语".equals(value)) {
            postData.setTo("fra");
        }
        if ("德语".equals(value)) {
            postData.setTo("de");
        }
        /**
         * 下面部分是爬虫需要的参数
         *
         * 由于之前有用python写过,思路都是一样的,所以这次并没有花费很多功夫去分析
         */
        postData.setAppid("20190606000305552");
        double d = Math.random() * 10000;
        int tempSalt = (int) d;
        postData.setSalt(Integer.toString(tempSalt));
        String tempSign = "20190606000305552" + inputString + tempSalt +
                "K2ACZZtjVUHaHIac4oDP";
        // md5加密签名
        MessageDigest message = null;
        try {
            message = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            message.update(tempSign.getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte s[] = message.digest();
        String sign = "";
        for (int i = 0; i < s.length; i++) {
            sign += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        postData.setSign(sign);
        // 创建子线程进行http请求
        Thread thread = new Thread(() -> {
            try {
                TranslateService translateService = new TranslateService();
                // 将url参数传入
                ResultData translateResult = translateService.getTranslateResult(postData);
                // 使用post更新
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    try {
                        String[] ans = translateResult.getTransResult();
                        System.out.println(ans);
                        String inputData = ans[0].split(",")[1].split(":")[1];
                        inputData = inputData.substring(0, inputData.length() - 2);
                        String outputData = ans[0].split(",")[0].split(":")[1];
                        // 保存记录
                        String text = inputData + "\"" + "  :  " + outputData;
                        System.out.println("写入文件:" + FILE_PATH);
                        textView.setText(text);
                        bufferSave(FILE_PATH, text);
                    } catch (Exception e) {
                        System.out.println("读取文件失败");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public static void bufferSave(String filename, String msg) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true));
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 申请文件读取权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }
}

package com.ccr.achengthreelinkage;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ccr.threelinkkagelibrary.listener.OnSimpleCitySelectListener;
import com.ccr.threelinkkagelibrary.view.CityPickerView;
import com.ccr.threelinkkagelibrary.view.OptionsPickerView;
import com.ccr.threelinkkagelibrary.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.city).setOnClickListener(this);
        findViewById(R.id.time).setOnClickListener(this);
        findViewById(R.id.tianjian).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city:
                CityPickerView mCityPickerView = new CityPickerView(this);

                // 设置点击外部是否消失
                mCityPickerView.setCancelable(true);
                //设置滚轮字体大小
                mCityPickerView.setTextSize(16f);

                //设置标题
                mCityPickerView.setTitle("请选择地址");
                mCityPickerView.setTitleSize(14f);
                //设置取消文字
//                mCityPickerView.setCancelText("我是取消文字");
                //设置取消文字颜色
                mCityPickerView.setCancelTextColor(Color.GRAY);
                mCityPickerView.setUnderLineColor(Color.RED);
                //设置取消文字大小
                mCityPickerView.setCancelTextSize(12f);
                //设置确定文字
//                mCityPickerView.setSubmitText("确定");
                //设置确定文字颜色
                mCityPickerView.setSubmitTextColor(Color.BLACK);
                //设置确定文字大小
                mCityPickerView.setSubmitTextSize(12f);
                //设置头部背景
                mCityPickerView.setHeadBackgroundColor(Color.WHITE);
                mCityPickerView.setOnCitySelectListener(new OnSimpleCitySelectListener(){
                    @Override
                    public void onCitySelect(String prov, String city, String area) {
                        // 省、市、区 分开获取
                        Log.d("Acheng", "拼接地址:"+"省: " + prov + " 市: " + city + " 区: " + area);
//                        Toast.makeText(MainActivity.this, "拼接地址:"+"省: " + prov + " 市: " + city + " 区: " + area, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCitySelect(String str) {
                        // 一起获取
                        Log.d("Acheng", "完整地址:"+str);
                        Toast.makeText(MainActivity.this, "完整地址:"+str, Toast.LENGTH_SHORT).show();
                    }
                });
                mCityPickerView.show();
                break;
            case R.id.time:
                //     TimePickerView 同样有上面设置样式的方法
                TimePickerView mTimePickerView = new TimePickerView(this, TimePickerView.Type.ALL);
                // 设置是否循环
                mTimePickerView.setCyclic(false);
                mTimePickerView.setTitle("请选择时间");
                mTimePickerView.setTitleSize(14);
                mTimePickerView.setCancelTextSize(14);
                mTimePickerView.setSubmitTextSize(14);
                mTimePickerView.setUnderLineColor(getResources().getColor(R.color.colorAccent));
                // 设置滚轮文字大小
                mTimePickerView.setTextSize(TimePickerView.TextSize.SMALL);
                // 设置时间可选范围(结合 setTime 方法使用,必须在)
                Calendar calendar = Calendar.getInstance();
                //第一个参数是起始时间，第二个参数是结束时间
                mTimePickerView.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR)+30);
                // 设置选中时间
                mTimePickerView.setMaxMonth(new Date(),10);
                mTimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                        Toast.makeText(MainActivity.this, format.format(date), Toast.LENGTH_SHORT).show();
                    }
                });
                mTimePickerView.show();
                break;
            case R.id.tianjian:
                OptionsPickerView<String> mOptionsPickerView = new OptionsPickerView<>(this);
                final ArrayList<String> list = new ArrayList<>();
                list.add("男");
                list.add("女");
                list.add("保密");
                // 设置数据
                mOptionsPickerView.setPicker(list);
                // 设置选项单位
//                mOptionsPickerView.setLabels("性");
                mOptionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int option1, int option2, int option3) {
                        String sex = list.get(option1);
                        Toast.makeText(MainActivity.this, sex, Toast.LENGTH_SHORT).show();
                    }
                });
                mOptionsPickerView.show();
                break;
        }
    }
}

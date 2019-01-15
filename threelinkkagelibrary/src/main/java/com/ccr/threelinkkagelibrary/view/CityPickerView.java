package com.ccr.threelinkkagelibrary.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ccr.threelinkkagelibrary.bean.JsonBean;
import com.ccr.threelinkkagelibrary.listener.OnCitySelectListener;
import com.ccr.threelinkkagelibrary.utils.GetJsonDataUtil;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * 在此写用途
 * 城市选择器
 *
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 15:12
 * @Version: V1.0 <描述当前版本功能>
 */

public class CityPickerView extends OptionsPickerView {
    private final Context mContext;

//    // 省数据集合
//    private ArrayList<String> mListProvince = new ArrayList<>();
//    // 市数据集合
//    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
//    // 区数据集合
//    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<>();
//    private JSONObject mJsonObj;


    private ArrayList<JsonBean> mListProvince = new ArrayList<>();
    private ArrayList<ArrayList<String>> mListCity = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> mListArea = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private String dataString = "", type = "1";//type 1本地json 2网络json

    public CityPickerView(Context context,String ty,String str) {
        super(context);
        mContext = context;
        dataString = str;
        type = ty;
        // 初始化Json对象
        //initJsonData();
        // 初始化Json数据
        initJsonData();
        initCitySelect();
    }

    public void setData(String type, String str) {
        dataString = str;
        this.type = type;
        initJsonData();
        initCitySelect();
    }

    private void initCitySelect() {
        setTitle("选择城市");
        setPicker(mListProvince, mListCity, mListArea, true);
        setCyclic(false, false, false);
        setSelectOptions(0, 0, 0);
        setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                if (mOnCitySelectListener != null) {
                    if (mListCity.size() > option1 && mListCity.get(option1).size() > option2) {
                        if (mListArea.size() > option1 && mListArea.get(option1).size() > option2
                                && mListArea.get(option1).get(option2).size() > option3) {
                            String prov = mListProvince.get(option1).getPickerViewText();
                            String city = mListCity.get(option1).get(option2);
                            String area = mListArea.get(option1).get(option2).get(option3);
                            mOnCitySelectListener.onCitySelect(prov.concat(city).concat(area));
                            mOnCitySelectListener.onCitySelect(prov, city, area);
                        }
                    }
                }
            }
        });
    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(mContext, "province.json");//获取assets目录下的json文件数据


        String finalJsonData = "";
        Log.d("Acheng", "type:" + type);
        if (type.equals("1")) {
            finalJsonData = JsonData;
        } else {
            finalJsonData = dataString;
            Log.d("Acheng", finalJsonData);
        }


        ArrayList<JsonBean> jsonBean = parseData(finalJsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        mListProvince = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            mListCity.add(CityList);

            /**
             * 添加地区数据
             */
            mListArea.add(Province_AreaList);
        }

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


//    /** 从assert文件夹中读取省市区的json文件，然后转化为json对象 */
//    private void initJsonData() {
//        AssetManager assets = mContext.getAssets();
//        try {
//            InputStream is = assets.open("city.json");
//            byte[] buf = new byte[is.available()];
//            is.read(buf);
//            String json = new String(buf, "UTF-8");
//            mJsonObj = new JSONObject(json);
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /** 初始化Json数据，并释放Json对象 */
//    private void initJsonDatas(){
//        try {
//            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonP = jsonArray.getJSONObject(i);// 获取每个省的Json对象
//                String province = jsonP.getString("name");
//
//                ArrayList<String> options2Items_01 = new ArrayList<>();
//                ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<>();
//                JSONArray jsonCs = jsonP.getJSONArray("city");
//                for (int j = 0; j < jsonCs.length(); j++) {
//                    JSONObject jsonC = jsonCs.getJSONObject(j);// 获取每个市的Json对象
//                    String city = jsonC.getString("name");
//                    options2Items_01.add(city);// 添加市数据
//
//                    ArrayList<String> options3Items_01_01 = new ArrayList<>();
//                    JSONArray jsonAs = jsonC.getJSONArray("area");
//                    for (int k = 0; k < jsonAs.length(); k++) {
//                        options3Items_01_01.add(jsonAs.getString(k));// 添加区数据
//                    }
//                    options3Items_01.add(options3Items_01_01);
//                }
//                mListProvince.add(province);// 添加省数据
//                mListCity.add(options2Items_01);
//                mListArea.add(options3Items_01);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        mJsonObj = null;
//    }

    public OnCitySelectListener mOnCitySelectListener;

    public void setOnCitySelectListener(OnCitySelectListener listener) {
        this.mOnCitySelectListener = listener;
    }
}

package com.ccr.threelinkkagelibrary.listener;


/**
 * 在此写用途
 * 城市选择回调
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 15:12
 * @Version: V1.0 <描述当前版本功能>
 */
public interface OnCitySelectListener {
    void onCitySelect(String str);
    void onCitySelect(String prov, String city, String area);
}

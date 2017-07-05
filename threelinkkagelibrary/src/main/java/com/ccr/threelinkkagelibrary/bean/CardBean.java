package com.ccr.threelinkkagelibrary.bean;


import com.ccr.threelinkkagelibrary.model.IPickerViewData;

/**
 * 在此写用途
 *  TODO
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 17:12
 * @Version: V1.0 <描述当前版本功能>
 */

public class CardBean implements IPickerViewData {
    int id;
    String cardNo;

    public CardBean(int id, String cardNo) {
        this.id = id;
        this.cardNo = cardNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String getPickerViewText() {
        return cardNo;
    }
}

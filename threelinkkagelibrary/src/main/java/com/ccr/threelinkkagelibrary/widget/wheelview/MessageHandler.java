package com.ccr.threelinkkagelibrary.widget.wheelview;

import android.os.Handler;
import android.os.Message;

/**
 * 在此写用途
 * 精仿iOSPickerViewController控件
 * @Author: Acheng
 * @Email: 345887272@qq.com
 * @Date: 2017-05-25 15:26
 * @Version: V1.0 <描述当前版本功能>
 */
final class MessageHandler extends Handler {
    public static final int WHAT_INVALIDATE_LOOP_VIEW = 1000;
    public static final int WHAT_SMOOTH_SCROLL = 2000;
    public static final int WHAT_ITEM_SELECTED = 3000;

    final WheelView loopview;

    MessageHandler(WheelView loopview) {
        this.loopview = loopview;
    }

    @Override
    public final void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_INVALIDATE_LOOP_VIEW:
                loopview.invalidate();
                break;

            case WHAT_SMOOTH_SCROLL:
                loopview.smoothScroll(WheelView.ACTION.FLING);
                break;

            case WHAT_ITEM_SELECTED:
                loopview.onItemSelected();
                break;
        }
    }

}

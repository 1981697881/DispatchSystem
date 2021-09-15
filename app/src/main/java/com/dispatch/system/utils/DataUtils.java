package com.dispatch.system.utils;

import com.blankj.utilcode.util.SPUtils;
import com.dispatch.system.constants.SPConstants;

public class DataUtils {


    /**
     * 登录成功，清理旧数据
     */
    public static void clearOldMsg() {
        SPUtils.getInstance().remove(SPConstants.SESSION_ID);
        SPUtils.getInstance().remove(SPConstants.SCREEN_ON);
        SPUtils.getInstance().remove(SPConstants.USER_ID);
        SPUtils.getInstance().remove(SPConstants.UUID);
        SPUtils.getInstance().remove(SPConstants.NICK_NAME);
        SPUtils.getInstance().remove(SPConstants.VACOLIST_ID);
    }
}

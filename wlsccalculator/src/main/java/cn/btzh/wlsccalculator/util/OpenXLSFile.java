package cn.btzh.wlsccalculator.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import cn.btzh.wlsccalculator.constant.Constant;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class OpenXLSFile {
    public static void openXLSFile(Context mContext,String filename){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(Constant.PATH + filename ));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        mContext.startActivity(intent);
    }
}

package cn.btzh.wlsccalculator.constant;

import android.os.Environment;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class Constant {
    /**
     * Excel保存路径
     */
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZzExcelCreator/";

    public static final String SHEET_NAME = "sheet1";

    public static String upload_url = "http://192.168.0.108:8099/uploadXLSFile.php";
}

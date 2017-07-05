package cn.btzh.wlsccalculator.excel;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.File;

import cn.btzh.wlsccalculator.constant.Constant;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class HebingTable extends AsyncTask<String,Intent,Boolean>{
    HebingTableInterface createTableInterface;

    public HebingTable(HebingTableInterface createTableInterface){
        this.createTableInterface = createTableInterface;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            ZzExcelCreator
                    .getInstance()
                    .openExcel(new File(Constant.PATH + params[0] + ".xls"))
                    .openSheet(0)
                    .merge(0, 0, 0, 0)
                    .close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        createTableInterface.isHebingTableSuccess(aBoolean);
    }
}

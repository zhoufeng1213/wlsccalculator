package cn.btzh.wlsccalculator.excel;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.IOException;

import cn.btzh.wlsccalculator.constant.Constant;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class CreateTable extends AsyncTask<String,Intent,Boolean>{
    CreateTableInterface createTableInterface;

    public CreateTable(CreateTableInterface createTableInterface){
        this.createTableInterface = createTableInterface;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            ZzExcelCreator
                    .getInstance()
                    .createExcel(Constant.PATH, params[0])
                    .createSheet(params[1])
                    .close();
            return true;
        } catch (IOException | WriteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        createTableInterface.isCreateTableSuccess(aBoolean);
    }
}

package cn.btzh.wlsccalculator.excel;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import cn.btzh.wlsccalculator.constant.Constant;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class InsertHang extends AsyncTask<Object,Intent,Boolean>{
    InsertHangInterface createTableInterface;

    public InsertHang(InsertHangInterface createTableInterface){
        this.createTableInterface = createTableInterface;
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        String fileName = (String)params[0];
        String row =(String) params[1] ;//行
        List<HashMap<String,String>> list = (List<HashMap<String,String>>) params[2] ;
        try {
            if(list != null && list.size() > 0){

                for (HashMap<String,String> map :list){

                    WritableCellFormat format = ZzFormatCreator
                            .getInstance()
                            .createCellFont(WritableFont.ARIAL)
                            .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                            .setFontSize(12)
                            .setFontColor(Colour.BLACK)
                            .getCellFormat();

                    File file = new File(Constant.PATH + fileName + ".xls");

                    String col = map.get("col");//列
                    String value = map.get("value");
                    ZzExcelCreator
                            .getInstance()
                            .openExcel(file)
                            .openSheet(0)
                            .setColumnWidth(Integer.parseInt(col), 25)
                            .setRowHeight(Integer.parseInt(row), 400)
                            .fillContent(Integer.parseInt(col),
                                    Integer.parseInt(row),
                                    value, format)
                            .close();
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        createTableInterface.isInsertHangSuccess(aBoolean);
    }
}

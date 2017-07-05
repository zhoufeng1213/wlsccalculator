package cn.btzh.wlsccalculator.iview;

import java.util.HashMap;
import java.util.List;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public interface ICreateTableView {
    void dealClickYuyinBtn(String result,int viewId);
    void dealClickAddHandBtn(boolean isNeedCreatedTableHead,int handNum);
    void dealClickReloadTableBtn();

    List<HashMap<String,String>> getHandData(int type,int currentHang);
    void isAddHangSuccess(boolean isSuccess,int type);
}

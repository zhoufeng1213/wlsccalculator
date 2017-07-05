package cn.btzh.wlsccalculator.presenter;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public interface ICreateTablePresenter {

    void clickYuyinBtn(int id);
    void clickAddHandBtn(String filename);
    void clickAddTableBtn(String filename);
    void clickReloadTableBtn();
    void clickOpenTableBtn(String filename);
    void clickUploadTableBtn(String filename);

    void addHang(int type,int hang,String filename);
    boolean isCreateTable();

}

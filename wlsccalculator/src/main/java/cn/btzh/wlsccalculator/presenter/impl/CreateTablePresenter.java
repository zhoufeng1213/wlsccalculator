package cn.btzh.wlsccalculator.presenter.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import cn.btzh.wlsccalculator.constant.Constant;
import cn.btzh.wlsccalculator.excel.CreateTable;
import cn.btzh.wlsccalculator.excel.CreateTableInterface;
import cn.btzh.wlsccalculator.excel.HebingTable;
import cn.btzh.wlsccalculator.excel.HebingTableInterface;
import cn.btzh.wlsccalculator.excel.InsertHang;
import cn.btzh.wlsccalculator.excel.InsertHangInterface;
import cn.btzh.wlsccalculator.iview.ICreateTableView;
import cn.btzh.wlsccalculator.presenter.ICreateTablePresenter;
import cn.btzh.wlsccalculator.util.OpenXLSFile;
import cn.btzh.wlsccalculator.util.ToastShow;
import cn.btzh.wlsccalculator.util.UploadFileToPhp;
import cn.btzh.wlsccalculator.util.UploadFileToPhpInterface;
import cn.btzh.wlsccalculator.yuyin.YuYinTool;
import cn.btzh.wlsccalculator.yuyin.YuYinToolCallback;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class CreateTablePresenter implements ICreateTablePresenter{

    private Context mContext;
    private ICreateTableView iCreateTableView;

    private int currentHang = 2;
    private boolean isCreateTable = false;

    public CreateTablePresenter(Context mContext,ICreateTableView iCreateTableView) {
        this.mContext = mContext;
        this.iCreateTableView = iCreateTableView;

    }


    @Override
    public void clickYuyinBtn(int id) {
        new YuYinTool(mContext, new YuYinToolCallback() {
            @Override
            public void returnYuYinResult(String result, int viewId) {
                iCreateTableView.dealClickYuyinBtn(result,viewId);
            }
        },id).showDialog();
    }

    @Override
    public void clickAddHandBtn(String filename) {
        //判断是否已经建表，如果没有建，就新建一张表
        if(!isCreateTable){
            if (Build.VERSION.SDK_INT >= 23) {
                if (mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ((Activity)mContext).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                } else {
                    new CreateTable(createTableInterface).execute(filename, Constant.SHEET_NAME);
                }
            } else {
                new CreateTable(createTableInterface).execute(filename,Constant.SHEET_NAME);
            }
        }else{
            //插入行
            iCreateTableView.dealClickAddHandBtn(false,currentHang);
        }
    }

    @Override
    public void clickAddTableBtn(String filename) {
        if(isCreateTable){
            //插入总的合计
            addHang(3,currentHang,filename);
            new HebingTable(new HebingTableInterface() {
                @Override
                public void isHebingTableSuccess(boolean result) {
                    if(result){
                        ToastShow.showToast(mContext,"合并成功");
                    }else{
                        ToastShow.showToast(mContext,"合并失败");
                    }
                }
            }).execute(filename);
        }else{
            ToastShow.showToast(mContext,"请先添加行数据");
        }
    }

    @Override
    public void clickReloadTableBtn() {
        isCreateTable = false;
        iCreateTableView.dealClickReloadTableBtn();
    }

    @Override
    public void clickOpenTableBtn(String filename) {
        if(isCreateTable && filename != null && !"".equals(filename)){
            OpenXLSFile.openXLSFile(mContext,filename + ".xls");
        }else{
            ToastShow.showToast(mContext,"还没有保存表");
        }
    }

    @Override
    public void clickUploadTableBtn(String filename) {
        if(isCreateTable && filename != null && !"".equals(filename)){
            upload(filename + ".xls");
        }else{
            ToastShow.showToast(mContext,"没有表上传");
        }
    }

    public void upload(final String filename){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UploadFileToPhp uploadFileToPhp = new UploadFileToPhp(uploadFileToPhpInterface);
                uploadFileToPhp.execute(Constant.upload_url,Constant.PATH+filename,"");
            }
        },10);
    }

    private UploadFileToPhpInterface uploadFileToPhpInterface = new UploadFileToPhpInterface() {
        @Override
        public void getUploadFileResult(String result, String fileServerPath) {
            if(result != null && "OK".equals(result)){
                ToastShow.showToast(mContext,"上传成功");
            }else{
                ToastShow.showToast(mContext,"上传失败");
            }
        }
    };


    private CreateTableInterface createTableInterface = new CreateTableInterface() {
        @Override
        public void isCreateTableSuccess(boolean result) {
            isCreateTable = result;
            if(result){
               iCreateTableView.dealClickAddHandBtn(true,0);
            }else{
                ToastShow.showToast(mContext,"创建表失败");
            }
        }
    };


    @Override
    public void addHang(final int type,int hang,String filename){
        new InsertHang(new InsertHangInterface() {
            @Override
            public void isInsertHangSuccess(boolean result) {
                iCreateTableView.isAddHangSuccess(result,type);
                if(result){
                    if(type == 2){
                        currentHang++;
                    }
                }
            }
        }).execute(filename,hang+"",iCreateTableView.getHandData(type,hang));
    }

    @Override
    public boolean isCreateTable() {
        return isCreateTable;
    }
}

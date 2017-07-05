package cn.btzh.wlsccalculator.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import cn.btzh.wlsccalculator.R;
import cn.btzh.wlsccalculator.constant.Constant;
import cn.btzh.wlsccalculator.ui.recyler.ViewHolder;
import cn.btzh.wlsccalculator.ui.recyler.base.CommonBaseAdapter;
import cn.btzh.wlsccalculator.ui.recyler.interfaces.OnItemClickListener;
import cn.btzh.wlsccalculator.util.GetFilesUtils;
import cn.btzh.wlsccalculator.util.OpenXLSFile;
import cn.btzh.wlsccalculator.util.ToastShow;
import cn.btzh.wlsccalculator.util.UploadFileToPhp;
import cn.btzh.wlsccalculator.util.UploadFileToPhpInterface;
import jxl.read.biff.BiffException;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class UploadListActivity extends BaseActivity{

    @Bind(R.id.upload_recycler_view)
    RecyclerView upload_recycler_view;

    @Override
    public int getLayoutId() {
        return R.layout.upload_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    void initListView(){
        //获取文件夹下面的所有文件
        List<String> list  = GetFilesUtils.getFiles(Constant.PATH);

        CommonBaseAdapter<String> adapter = new CommonBaseAdapter<String>(mContext,list,false) {
            @Override
            protected void convert(ViewHolder holder,final String data,int position) {
                holder.setText(R.id.upload_list_xh,(position+1)+"");
                holder.setText(R.id.upload_list_name,data);

                holder.setOnClickListener(R.id.upload_list_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        ToastShow.showToast(mContext,"正在上传文件："+data);
                        upload(data);

                    }
                });

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.upload_list_item;
            }
        } ;

        adapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, String data, int position) {
                if(data != null && !"".equals(data)){
                    OpenXLSFile.openXLSFile(mContext,data);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        upload_recycler_view.setLayoutManager(layoutManager);
        upload_recycler_view.setAdapter(adapter);
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
                ToastShow.showToast(mContext,"上传失败,请检查上传ip是否出错");
            }
        }
    };

}

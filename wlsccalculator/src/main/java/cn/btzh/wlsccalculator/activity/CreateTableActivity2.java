package cn.btzh.wlsccalculator.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.btzh.wlsccalculator.R;
import cn.btzh.wlsccalculator.constant.Constant;
import cn.btzh.wlsccalculator.excel.CreateTable;
import cn.btzh.wlsccalculator.excel.CreateTableInterface;
import cn.btzh.wlsccalculator.excel.HebingTable;
import cn.btzh.wlsccalculator.excel.HebingTableInterface;
import cn.btzh.wlsccalculator.excel.InsertHang;
import cn.btzh.wlsccalculator.excel.InsertHangInterface;
import cn.btzh.wlsccalculator.model.Units;
import cn.btzh.wlsccalculator.util.OpenXLSFile;
import cn.btzh.wlsccalculator.util.ToastShow;
import cn.btzh.wlsccalculator.util.UploadFileToPhp;
import cn.btzh.wlsccalculator.util.UploadFileToPhpInterface;
import cn.btzh.wlsccalculator.yuyin.YuYinTool;
import cn.btzh.wlsccalculator.yuyin.YuYinToolCallback;

public class CreateTableActivity2 extends BaseActivity{

    @Bind(R.id.wlsc_address_edit)
    EditText wlsc_address_edit;

    @Bind(R.id.wlsc_content_edit)
    EditText wlsc_content_edit;

    @Bind(R.id.wlsc_count_edit)
    EditText wlsc_count_edit;

    @Bind(R.id.wlsc_price_edit)
    EditText wlsc_price_edit;

    @Bind(R.id.wlsc_bz_edit)
    EditText wlsc_bz_edit;

    @Bind(R.id.wlsc_dw_edit)
    Spinner wlsc_dw_edit;

    @Bind(R.id.wlsc_hj_edit)
    EditText wlsc_hj_edit;

    @Bind(R.id.wlsc_content_button)
    Button wlsc_content_button;

    @Bind(R.id.wlsc_count_button)
    Button wlsc_count_button;

    @Bind(R.id.wlsc_price_button)
    Button wlsc_price_button;

    @Bind(R.id.wlsc_hj_button)
    Button wlsc_hj_button;

    @Bind(R.id.wlsc_bz_button)
    Button wlsc_bz_button;

    @Bind(R.id.wslc_add_hang)
    Button wslc_add_hang;

    @Bind(R.id.wslc_add_table)
    Button wslc_add_table;

    @Bind(R.id.wslc_reload_table)
    Button wslc_reload_table;

    @Bind(R.id.wlsc_address_button)
    Button wlsc_address_button;

    @Bind(R.id.wslc_open_table)
    Button wslc_open_table;

    @Bind(R.id.wslc_upload_table)
    Button wslc_upload_table;

    private Context mContext;

    private int currentHang = 2;
    private boolean isCreateTable = false;
    private String filename = "";
    private List<String> eachPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        SpeechUtility.createUtility(CreateTableActivity2.this, SpeechConstant.APPID + "=578f1af7");
        initView();

        filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,Units.getUnitList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wlsc_dw_edit.setAdapter(adapter);

        wlsc_address_button.setFocusable(true);
        wlsc_address_button.setFocusableInTouchMode(true);
        wlsc_address_button.requestFocus();
        wlsc_address_button.requestFocusFromTouch();

        wlsc_price_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && !"".equals(s.toString())&& !wlsc_count_edit.getText().toString().trim().equals("")){
                    int hj = (int)(Double.valueOf(s.toString().trim())*
                            Double.valueOf(wlsc_count_edit.getText().toString().trim()));
                    wlsc_hj_edit.setText(hj+"");
                }

                if(wlsc_count_edit.getText().toString().trim().equals("")
                        || wlsc_price_edit.getText().toString().trim().equals("")){
                    wlsc_hj_edit.setText("");
                }
            }
        });

        wlsc_count_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && !"".equals(s.toString())&& !wlsc_price_edit.getText().toString().trim().equals("")){
                    int hj = (int)(Double.valueOf(s.toString().trim())*
                            Double.valueOf(wlsc_price_edit.getText().toString().trim()));
                    wlsc_hj_edit.setText(hj+"");
                }

                if(wlsc_count_edit.getText().toString().trim().equals("")
                        || wlsc_price_edit.getText().toString().trim().equals("")){
                    wlsc_hj_edit.setText("");
                }
            }
        });
    }

    @OnClick({R.id.wlsc_content_button,R.id.wlsc_count_button,
            R.id.wlsc_price_button,
            R.id.wlsc_hj_button,
            R.id.wlsc_bz_button,
            R.id.wlsc_address_button,
            R.id.wslc_add_hang,
            R.id.wslc_add_table,
            R.id.wslc_upload_table,
            R.id.wslc_reload_table,R.id.wslc_open_table})
    public void onClick(final View v) {

        switch (v.getId()){
            case R.id.wlsc_content_button:
            case R.id.wlsc_count_button:
            case R.id.wlsc_price_button:
            case R.id.wlsc_hj_button:
            case R.id.wlsc_bz_button:
            case R.id.wlsc_address_button:
                new YuYinTool(this, new YuYinToolCallback() {
                    @Override
                    public void returnYuYinResult(String result, int viewId) {
                        switch (v.getId()) {
                            case R.id.wlsc_content_button:
                                wlsc_content_edit.setText(Units.getContent(result));
                                //判断一些数据对应的单位
                                wlsc_dw_edit.setSelection(Units.getUnit(wlsc_content_edit.getText().toString()));
                                break;
                            case R.id.wlsc_count_button:
//                                wlsc_count_edit.setText(wlsc_count_edit.getText() + result);
                                wlsc_count_edit.setText(Units.getNumber( result));
                                break;
                            case R.id.wlsc_price_button:
//                                wlsc_price_edit.setText(wlsc_price_edit.getText() + result);
                                wlsc_price_edit.setText(Units.getNumber( result));
                                break;
                            case R.id.wlsc_hj_button:
//                                wlsc_price_edit.setText(wlsc_price_edit.getText() + result);
                                wlsc_hj_edit.setText(Units.getNumber( result));
                                break;
                            case R.id.wlsc_bz_button:
                                wlsc_bz_edit.setText(Units.getBZ(result));
                                break;
                            case R.id.wlsc_address_button:
                                wlsc_address_edit.setText(result);
                                break;
                        }
                    }
                },v.getId()).showDialog();

                break;
            case R.id.wslc_add_hang:
                //判断是否已经建表，如果没有建，就新建一张表
                if(!isCreateTable){
                    if(!wlsc_address_edit.getText().toString().trim().equals("")){
                        filename = wlsc_address_edit.getText().toString().trim() +
                                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    }else{
                        ToastShow.showToast(mContext,"请先输入工地名称");
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
                        } else {
                            new CreateTable(createTableInterface).execute(filename, Constant.SHEET_NAME);
                        }
                    } else {
                        new CreateTable(createTableInterface).execute(filename,Constant.SHEET_NAME);
                    }
                }else{
                    //插入行
                    addHang(2,currentHang);
                }
                break;
            case R.id.wslc_add_table:
                if(isCreateTable){
                    //插入总的合计
                    addHang(3,currentHang);
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

                break;
            case R.id.wslc_reload_table:
                filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                wlsc_address_edit.setText("");
                wlsc_hj_edit.setText("");
                wlsc_content_edit.setText("");
                wlsc_count_edit.setText("");
                wlsc_price_edit.setText("");
                wlsc_bz_edit.setText("");
                wlsc_dw_edit.setSelection(0);

                wlsc_address_edit.setEnabled(true);

                eachPrices.clear();

                isCreateTable = false;
                break;
            case R.id.wslc_open_table:
                if(isCreateTable && filename != null && !"".equals(filename)){
                    OpenXLSFile.openXLSFile(mContext,filename + ".xls");
                }else{
                    ToastShow.showToast(mContext,"还没有保存表");
                }
                break;
            case R.id.wslc_upload_table:
                if(isCreateTable && filename != null && !"".equals(filename)){
                    upload(filename + ".xls");
                }else{
                    ToastShow.showToast(mContext,"没有表上传");
                }
                break;
            default:

                break;
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
                wlsc_address_edit.setEnabled(false);
                //插入行
                addHang(0,0);
                addHang(1,1);
                if(!"".equals(wlsc_content_edit.getText().toString().trim())){
                    addHang(2,currentHang);
                }else{
                    ToastShow.showToast(mContext,"请输入内容");
                }
            }else{
                ToastShow.showToast(mContext,"创建表失败");
            }
        }
    };


    private void addHang(final int type,int hang){
        new InsertHang(new InsertHangInterface() {
            @Override
            public void isInsertHangSuccess(boolean result) {
                if(result){
                    if(type == 2){
                        currentHang++;
                        ToastShow.showToast(mContext,"添加成功");
                        //清除组件的值
                        wlsc_content_edit.setText("");
                        wlsc_count_edit.setText("");
                        wlsc_price_edit.setText("");
                        wlsc_hj_edit.setText("");
                        wlsc_bz_edit.setText("");
                        wlsc_dw_edit.setSelection(0);
                    }
                }else{
                    ToastShow.showToast(mContext,"添加失败");
                }
            }
        }).execute(filename,hang+"",getHandData(type));
    }


    /**
     *
     * @param type 0:插入工地名称 1：插入表头 2：插入正式数据 3：合计
     * @return
     */
    private List<HashMap<String,String>> getHandData(int type){
        List<HashMap<String,String>> list = new ArrayList<>();
        if(type == 0){
            list.add(getMap("0",wlsc_address_edit.getText().toString().trim()));
        }else if(type == 1){
            list.add(getMap("0","序号"));
            list.add(getMap("1","内容"));
            list.add(getMap("2","单位"));
            list.add(getMap("3","数量"));
            list.add(getMap("4","价格"));
            list.add(getMap("5","合计"));
            list.add(getMap("6","备注"));
        }else if(type == 3){
            list.add(getMap("0","合计"));
            int allPrice = 0;
            if(eachPrices != null){
                for (String eachPrice:eachPrices){
                    if(eachPrice != null && !"".equals(eachPrice.trim())){
                        try {
                            allPrice+= Integer.valueOf(eachPrice);
                        } catch (NumberFormatException e) {
                            continue;
                        }
                    }
                }
            }
            list.add(getMap("5",allPrice+""));
        }else{
            list.add(getMap("0",(currentHang-1)+""));
            list.add(getMap("1",wlsc_content_edit.getText().toString()));
            list.add(getMap("2",Units.getUnitList().get(wlsc_dw_edit.getSelectedItemPosition())));
            list.add(getMap("3",wlsc_count_edit.getText().toString()));
            list.add(getMap("4",wlsc_price_edit.getText().toString()));
            list.add(getMap("5",wlsc_hj_edit.getText().toString().trim()));
            list.add(getMap("6",wlsc_bz_edit.getText().toString()));

            //保存每行合计的价格
            eachPrices.add(wlsc_hj_edit.getText().toString().trim());
        }
        return list;
    }


    private HashMap<String,String> getMap(String col,String value){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("col",col);
        map.put("value",value);
        return map;
    }

}

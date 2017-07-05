package cn.btzh.wlsccalculator.activity;

import android.content.Context;
import android.os.Bundle;
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
import cn.btzh.wlsccalculator.iview.ICreateTableView;
import cn.btzh.wlsccalculator.model.Units;
import cn.btzh.wlsccalculator.presenter.ICreateTablePresenter;
import cn.btzh.wlsccalculator.presenter.impl.CreateTablePresenter;
import cn.btzh.wlsccalculator.util.EditTextWatcher;
import cn.btzh.wlsccalculator.util.ToastShow;

public class CreateTableActivity extends BaseActivity implements ICreateTableView{

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

    @Bind(R.id.wlsc_address_button)
    Button wlsc_address_button;

    private Context mContext;
    private String filename = "";
    private List<String> eachPrices = new ArrayList<>();
    private ICreateTablePresenter iCreateTablePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        SpeechUtility.createUtility(CreateTableActivity.this, SpeechConstant.APPID + "=578f1af7");
        initView();
        iCreateTablePresenter = new CreateTablePresenter(mContext,this);
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

        wlsc_price_edit.addTextChangedListener(new EditTextWatcher(
                R.id.wlsc_price_edit,editTextWatcherInterface));
        wlsc_count_edit.addTextChangedListener(new EditTextWatcher(
                R.id.wlsc_count_edit,editTextWatcherInterface));

        wlsc_address_button.setFocusable(true);
        wlsc_address_button.setFocusableInTouchMode(true);
        wlsc_address_button.requestFocus();
        wlsc_address_button.requestFocusFromTouch();
    }


    private EditTextWatcher.EditTextWatcherInterface editTextWatcherInterface= new EditTextWatcher.EditTextWatcherInterface() {
        @Override
        public void dealEditTextWatcher(int viewId, String result) {
            switch (viewId){
                case R.id.wlsc_price_edit:
                    if( !"".equals(result)&& !wlsc_count_edit.getText().toString().trim().equals("")){
                        int hj = (int)(Double.valueOf(result.trim())*
                                Double.valueOf(wlsc_count_edit.getText().toString().trim()));
                        wlsc_hj_edit.setText(hj+"");
                    }
                    break;
                case R.id.wlsc_count_edit:
                    if(!"".equals(result)&& !wlsc_price_edit.getText().toString().trim().equals("")){
                        int hj = (int)(Double.valueOf(result.trim())*
                                Double.valueOf(wlsc_price_edit.getText().toString().trim()));
                        wlsc_hj_edit.setText(hj+"");
                    }
                    break;
            }
            if(wlsc_count_edit.getText().toString().trim().equals("")
                    || wlsc_price_edit.getText().toString().trim().equals("")){
                wlsc_hj_edit.setText("");
            }
        }
    };

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
                iCreateTablePresenter.clickYuyinBtn(v.getId());
                break;
            case R.id.wslc_add_hang:

                if(!iCreateTablePresenter.isCreateTable()){
                    if(!wlsc_address_edit.getText().toString().trim().equals("")){
                        filename = wlsc_address_edit.getText().toString().trim() +
                                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    }else{
                        ToastShow.showToast(mContext,"请先输入工地名称");
                    }
                }
                iCreateTablePresenter.clickAddHandBtn(filename);
                break;
            case R.id.wslc_add_table:
                iCreateTablePresenter.clickAddTableBtn(filename);
                break;
            case R.id.wslc_reload_table:
                iCreateTablePresenter.clickReloadTableBtn();
                break;
            case R.id.wslc_open_table:
                iCreateTablePresenter.clickOpenTableBtn(filename);
                break;
            case R.id.wslc_upload_table:
                iCreateTablePresenter.clickUploadTableBtn(filename);
                break;
            default:

                break;
        }

    }


    /**
     *
     * @param type 0:插入工地名称 1：插入表头 2：插入正式数据 3：合计
     * @return
     */
    @Override
    public List<HashMap<String,String>> getHandData(int type,int currentHang){
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


    @Override
    public void dealClickYuyinBtn(String result,int viewId) {
        switch (viewId) {
            case R.id.wlsc_content_button:
                wlsc_content_edit.setText(Units.getContent(result));
                //判断一些数据对应的单位
                wlsc_dw_edit.setSelection(Units.getUnit(wlsc_content_edit.getText().toString()));
                break;
            case R.id.wlsc_count_button:
                wlsc_count_edit.setText(Units.getNumber( result));
                break;
            case R.id.wlsc_price_button:
                wlsc_price_edit.setText(Units.getNumber( result));
                break;
            case R.id.wlsc_hj_button:
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

    @Override
    public void dealClickAddHandBtn(boolean isNeedCreatedTableHead,int handNum) {
        if(isNeedCreatedTableHead){
            wlsc_address_edit.setEnabled(false);
            //插入行
            iCreateTablePresenter.addHang(0,0,filename);
            iCreateTablePresenter.addHang(1,1,filename);
            if(!"".equals(wlsc_content_edit.getText().toString().trim())){
                iCreateTablePresenter.addHang(2,handNum,filename);
            }else{
                ToastShow.showToast(mContext,"请输入内容");
            }
        }else{
            iCreateTablePresenter.addHang(2,handNum,filename);
        }
    }

    @Override
    public void isAddHangSuccess(boolean isSuccess,int type) {
        if(isSuccess){
            if(type == 2){
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

    @Override
    public void dealClickReloadTableBtn() {
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
    }

}

package cn.btzh.wlsccalculator.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class EditTextWatcher implements TextWatcher{

    EditTextWatcherInterface editTextWatcherInterface;
    int viewId = -100000;
    public EditTextWatcher(int viewId,EditTextWatcherInterface editTextWatcherInterface){
        this.viewId = viewId;
        this.editTextWatcherInterface = editTextWatcherInterface;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editTextWatcherInterface.dealEditTextWatcher(viewId,s== null ?"":s.toString());
    }


    public interface EditTextWatcherInterface {
        void dealEditTextWatcher(int viewId,String result);
    }
}

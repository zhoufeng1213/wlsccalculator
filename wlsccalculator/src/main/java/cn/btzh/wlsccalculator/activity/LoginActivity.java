package cn.btzh.wlsccalculator.activity;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import cn.btzh.wlsccalculator.R;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class LoginActivity extends BaseActivity{

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @OnClick({R.id.login_create,R.id.login_upload})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_create:
                startActivity(new Intent(this, CreateTableActivity.class));
                break;
            case R.id.login_upload:
                startActivity(new Intent(this, UploadListActivity.class));
                break;
        }
    }

}

package cn.btzh.wlsccalculator.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.ButterKnife;
import cn.btzh.wlsccalculator.R;
import cn.btzh.wlsccalculator.constant.Constant;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public abstract class BaseActivity extends AppCompatActivity{

    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
    }

    public abstract int getLayoutId();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "修改上传ip");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:// 高级
                showIPsetDialog();
                return true;
        }
        return false;
    }

    public void showIPsetDialog() {
        AlertDialog.Builder builder;
        LayoutInflater inflater = (LayoutInflater) LayoutInflater
                .from(mContext);
        View layout = inflater.inflate(R.layout.alert_dialog_login,
                (ViewGroup) findViewById(R.id.alert_login_root));

        final EditText textIp = (EditText) layout
                .findViewById(R.id.login_backgroundIp);
        textIp.setText(Constant.upload_url);

        builder = new AlertDialog.Builder(mContext);
        builder.setView(layout);

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("服务器设置");

        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Constant.upload_url = textIp.getText().toString().trim();
                        return;
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

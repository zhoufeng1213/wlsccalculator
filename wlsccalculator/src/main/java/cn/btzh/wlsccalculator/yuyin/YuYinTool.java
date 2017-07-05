package cn.btzh.wlsccalculator.yuyin;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.List;

/**
 * 模块名称:语音识别功能
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class YuYinTool {

    //有动画效果
    private RecognizerDialog iatDialog;
    private Context mContext;
    private YuYinToolCallback callback;
    private int viewId;

    public YuYinTool(Context mContext,YuYinToolCallback callback,int viewId){
        this.mContext = mContext;
        this.viewId = viewId;
        this.callback = callback;
    }

    private void initDialog(){
        // 有交互动画的语音识别器
        iatDialog = new RecognizerDialog(mContext, mInitListener);
        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("-----------------   onResult   -----------------");
                Log.e(TAG, "start:---------------------------- "+"" );
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                Log.e(TAG, "onResult: "+ recognizerResult.getResultString());

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    String result = "";
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result += resultList.get(i).toString();
                    }
                    callback.returnYuYinResult(result,viewId);
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                Log.e(TAG, "onError: "+"失败" );
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
    }

    public void showDialog(){
        if(iatDialog == null){
            initDialog();
        }
        iatDialog.show();
    }


    public static final String TAG = "yuyintool";
    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

}

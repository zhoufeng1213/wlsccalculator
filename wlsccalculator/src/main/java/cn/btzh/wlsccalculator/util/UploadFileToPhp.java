package cn.btzh.wlsccalculator.util;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressLint("NewApi")
public class UploadFileToPhp extends AsyncTask<String, String, String>{
	
	private UploadFileToPhpInterface uploadFileInterface;
	private String uploadPath = "";
	
	public UploadFileToPhp( UploadFileToPhpInterface uploadFileInterface) {
		this.uploadFileInterface = uploadFileInterface;
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		String uploadUrl = params [0];
		String path = params [1];
		uploadPath = params[2];
		String result = "";
		try {
			result =  upLoadByCommonPost(uploadUrl,path,uploadPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}  
	
	@Override
	protected void onPostExecute(String result) {
		uploadFileInterface.getUploadFileResult(result, uploadPath);
		super.onPostExecute(result);
	}
	

	 /** 
     * upLoadByAsyncHttpClient:由人造post上传 
     *  
     * @return void 
     * @throws IOException 
     * @throws 
     * @since CodingExample　Ver 1.1 
     */  
    private String upLoadByCommonPost(String uploadUrl, String path, String uploadPath) throws IOException {
        String result = null;
        uploadUrl = uploadUrl+"?filename="+path.substring(path.lastIndexOf("/") + 1);
        try {
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "******";
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
//        httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
//            httpURLConnection.setRequestProperty("Charset", "GB2312");
//            httpURLConnection.setRequestProperty("Charset", "ISO-8859-1");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
        /*设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接*/
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            // 发送参数
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"path\"" + end);
            dos.writeBytes(end);
            dos.writeBytes(uploadPath);
            dos.writeBytes(end);

            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"filename\"" + end);
            dos.writeBytes(end);
            dos.writeBytes(path.substring(path.lastIndexOf("/") + 1));
            dos.writeBytes(end);

            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"type\"" + end);
            dos.writeBytes(end);
            dos.writeBytes("0");
            dos.writeBytes(end);

            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"Filedata\"; filename=\""
                    + path.substring(path.lastIndexOf("/") + 1) + "\"" + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            fis.close();
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            result = br.readLine();
            Log.i("upLoadByAsyncHttpClient", result == null ? "null" : result);
            dos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

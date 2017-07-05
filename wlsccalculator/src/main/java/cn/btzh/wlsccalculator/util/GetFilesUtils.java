package cn.btzh.wlsccalculator.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 模块名称:
 * Created by fly(zhoufeng) on 2017/7/4.
 */

public class GetFilesUtils {

    public static List<String> getFiles(String path){
        File specItemDir = new File(path);
        if(!specItemDir.exists()){
            specItemDir.mkdir();
        }

        List<String> list = new ArrayList<>();
        if(specItemDir.exists()){
            File files [] = specItemDir.listFiles();
            for (File file:files){

                if(file != null &&
                        !"".equals(file.getName().trim()) &&
                        !"".equals(file.getName().trim().substring(0,file.getName().trim().lastIndexOf(".")))
                        ){
                    list.add(file.getName());
                }
            }
        }
        Collections.reverse(list);
        return list;
    }
}

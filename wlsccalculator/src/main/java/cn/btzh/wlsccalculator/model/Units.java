package cn.btzh.wlsccalculator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 模块名称:单位
 * Created by fly(zhoufeng) on 2017/7/3.
 */

public class Units {

    public static String [] getUnits(){
        String [] units = {"m2","m","个","块","工","箱",""};
        return units;
    }

    public static List<String> getUnitList(){
        List<String> list  = new ArrayList<>();
        list.add("平方");
        list.add("米");
        list.add("个");
        list.add("块");
        list.add("工");
        list.add("箱");
        list.add("");
        return list;
    }


    public static String  getNumber(String num){
        String [] nums = {"一","二","三","四","五","六","七","八","九","十","零"};
        int [] numbers = {1,2,3,4,5,6,7,8,9,10,0};
        String rusult = num;
        for(int i = 0;i<nums.length;i++){
            if(nums[i].equals(num)){
                rusult = numbers[i]+"";
                break;
            }
        }

        if(rusult.equals("两百") ||rusult.equals("二百") ){
            rusult = "200";
        }
        if(rusult.equals("一百") ){
            rusult = "100";
        }
        return rusult;
    }


    public static String  getContent(String text){
        String rusult = text;
        if(text.contains("黄金码")) {
            rusult = rusult.replace("黄金码", "黄金麻");
        }
        if(text.contains("黄金马")) {
            rusult = rusult.replace("黄金马", "黄金麻");
        }
        if(text.contains("毛版")) {
            rusult = rusult.replace("毛版", "毛板");
        }
        if(text.contains("垭口")) {
            rusult = rusult.replace("垭口", "压口");
        }
        if(text.contains("住茂")) {
            rusult = rusult.replace("住茂", "柱帽");
        }
        if(text.contains("县条")) {
            rusult = rusult.replace("县条", "线条");
        }
        return rusult;
    }

    public static String  getBZ(String text){
        String [] nums = {"一","二","三","四","五","六","七","八","九","十","零"};
        int [] numbers = {1,2,3,4,5,6,7,8,9,10,0};
        String rusult = text;
        for(int i = 0;i<nums.length;i++){
            if(text.contains(nums[i])){
                rusult = text.replace(nums[i],numbers[i]+"");
                break;
            }
        }
        if(rusult.contains("后") && rusult.contains("公分")){
            rusult = rusult.replace("后","厚");
        }
        return rusult;
    }

    public static int getUnit(String content){
        String unit = "";
        if(content.contains("双边") || content.contains("单边")
                || content.contains("挡水条") || content.contains("挡水")
                || content.contains("倒角") || content.contains("线条")
                || content.contains("线")){
            unit = "米";
            return getUnitIndex(unit);
        }
        if(content.contains("安装人工")){
            unit = "平方";
            return getUnitIndex(unit);
        }
        if(content.contains("台下盆") || content.contains("打孔")){
            unit = "个";
            return getUnitIndex(unit);
        }
        if(content.contains("胶")){
            unit = "箱";
            return getUnitIndex(unit);
        }
        return 0;
    }

    private static int getUnitIndex(String unit){
        int index = 0;
        List<String> list = getUnitList();
        for(int i = 0; i< list.size();i++){
            if(list.get(i).equals(unit)){
                index = i;
                break;
            }
        }
        return  index;
    }


}

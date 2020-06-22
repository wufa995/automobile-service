/**
 * @author: wufa995<wufa995@suixingpay.com>
 * @date: 2018/1/31
 */
package cn.wufa995.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ChineseHelper {

    public static String ToUpperFirst(String chinese) {
        return ToPinyin(chinese).substring(0, 1)+ToPinyin(chinese).substring(1);
    }

    /**
     * 汉字转拼音(小写)
     */
    public static String ToPinyinAll(String chinese){
        if (CheckParam.isEmpty(chinese)) {
            return "";
        }
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }
    /**
     * 汉字转拼音(只包含首字母，小写)
     * @param chinese
     * @return
     */
    public static String ToPinyin(String chinese){
        if (CheckParam.isEmpty(chinese)) {
            return "";
        }
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].charAt(0);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

    /**
     * 判断字符串中是否含有大写字母
     * @param word
     * @return
     */
    public static boolean isAcronym(String word)
    {
        for(int i = 0; i < word.length(); i++)
        {
            char c = word.charAt(i);
            if (Character.isUpperCase(c))
            {
                return true;
            }
        }
        return false;
    }

    /** 仅获取中文姓名部分 **/
    public static String getSingleName(String fullName) {
        if (CheckParam.isEmpty(fullName)) {
            return "";
        } else {
            return fullName.substring(0, fullName.indexOf("|"));
        }
    }
}

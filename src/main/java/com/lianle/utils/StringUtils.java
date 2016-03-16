package com.lianle.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static boolean isNumeric(String str){
        if(isBlank(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("([-+]\\d+|\\d*)(\\.\\d+)?");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 通过get或set方法名获得字段名称
     *
     * @param methodName
     *            方法名称
     * @throws Exception
     */
    public static String getFieldName(String methodName) {
        if(methodName.startsWith("get") || methodName.startsWith("set")) {
            return firstLower(methodName.substring(3));
        }

        throw new RuntimeException("不是有效的属性get|set方法");
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /** 将首字母转为大写，其他不变 */
    public static String firstUp(String str) {
        String first = str.substring(0, 1);
        String orther = str.substring(1);
        return first.toUpperCase() + orther;
    }

    /** 将首字母转为小写，其他不变 */
    public static String firstLower(String str) {
        String first = str.substring(0, 1);
        String orther = str.substring(1);
        return first.toLowerCase() + orther;
    }

    /** 将符合数据库的命名转为java的命名 */
    public static String pareseUnderline(String code) {
        String[] strs = code.split("_");
        String first = strs[0].toLowerCase();
        if (strs.length == 1) {
            return first;
        }
        StringBuffer sb = new StringBuffer(first);
        for (int i = 1; i < strs.length; i++) {
            sb.append(firstUpOnly(strs[i]));
        }
        return sb.toString();
    }

    /** 将首字母转为大写，其他变小写 */
    public static String firstUpOnly(String str) {
        String first = str.substring(0, 1);
        String orther = str.substring(1);
        return first.toUpperCase() + orther.toLowerCase();
    }

    /** 将符合java的命名转为数据库的命名 */
    public static String pareseUpCase(String code) {
        char[] old = code.toCharArray();
        char[] news = code.toLowerCase().toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < old.length; i++) {
            if (old[i] == news[i]) {
                sb.append(news[i]);
            } else {
                sb.append("_" + news[i]);
            }
        }
        return sb.toString();
    }

    public static void addLine(StringBuffer sb, String str) {
        sb.append(str);
        sb.append(System.getProperty("line.separator"));
    }

    /**
     * 获取请求IP
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        //如果是多级代理，那么取第一个不为“null”且不为“unknown”的IP串
        if(ip != null && ip.length() > 0 && ip.indexOf(",") != -1) {
            String[] ips = ip.split(",");
            for(String ip_:ips) {
                if(ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                    ip = ip_;
                    break;
                }
            }
        }

        return ip;
    }

    /** 将字符串转为uicode */
    public static String getUnicode(String str) {
        if (str == null) {
            return "";
        }
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char ch : chars) {
            String temp = Integer.toHexString(ch);
            if (temp.length() == 1) {
                temp = "000" + temp;
            }
            if (temp.length() == 2) {
                temp = "00" + temp;
            }
            if (temp.length() == 3) {
                temp = "0" + temp;
            }
            sb.append("\\u" + temp);
        }
        return sb.toString();
    }

    /**
     * 将字符串转为map 字符串:userName=张三,userCode=zhangsan,age 结果:map.put(userName,张三);
     * map.put(userCode,zhangsan); map.put(age,null);
     */
    public static Map toMap(String str) {
        return toMap(str, ",");
    }

    public static Map toMap(String str, String splitString) {
        Map map = new HashMap();
        if (str == null || str.equals("")) {
            return map;
        }
        String values[] = str.split(splitString);
        for (int i = 0; i < values.length; i++) {
            String tempValue = values[i];
            int pos = tempValue.indexOf("=");
            String key = "";
            String value = "";
            if (pos > -1) {
                key = tempValue.substring(0, pos);
                value = tempValue.substring(pos + splitString.length());
            } else {
                key = tempValue;
            }
            map.put(key, value);
        }

        return map;
    }

    public static Map toMap(List<String> strs) {
        Map map = new HashMap();
        if (strs == null) {
            return map;
        }
        for (String st : strs) {
            map.put(st, null);
        }
        return map;
    }

    public static List<String> getIds(List<String> pageId, String prefix) {
        List list = new ArrayList();
        if (pageId == null) {
            return list;
        }
        for (String id : pageId) {
            list.add(id);
        }
        return list;
    }

    /** 判断对象是否为空 */
    public static boolean isNull(Object object) {
        if (object instanceof String) {
            return isNull((String) object);
        }
        return object == null;
    }

    public static String getPropertyName(String methodName) {
        String propertyName = null;
        if (methodName.startsWith("get")) {
            propertyName = methodName.substring("get".length());
        } else if (methodName.startsWith("is")) {
            propertyName = methodName.substring("is".length());
        } else if (methodName.startsWith("set")) {
            propertyName = methodName.substring("set".length());
        }
        if (propertyName == null || propertyName.length() == 0) {
            return null;
        }

        return firstLower(propertyName);
    }

    /** 判断字符串是否为空 */
    public static boolean isNull(String value) {
        return value == null || value.trim().equals("");
    }

    /** 判断字符串是否为空 */
    public static boolean isNotNull(String value) {
        return value != null && !value.trim().equals("");
    }

    public static boolean isNum(String str) {

        return (str.replaceAll("^\\d+[.]?\\d+$", "").length() == 0);

    };

    /**
     * 截取中文字符串
     *
     * @param oldString
     * @param length
     * @return
     */

    public static String subChineseStr(String oldString, int length) {
        int len = 0;
        int lenZh = 0;
        if (oldString == null) {
            return null;
        }
        if ("".equals(oldString)) {
            return "";
        }

        char c;
        for (int i = 0; i < oldString.length(); i++) {
            c = oldString.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z')) {
                // 字母, 数字
                len++;
            } else {
                if (Character.isLetter(c)) { // 中文
                    len += 4;
                } else { // 符号或控制字符
                    len++;
                }
            }
            if (len <= length) {
                lenZh += 1; // 用来SUBSTRING
            }
        }
        if (len > 0 && len <= length) {
            return oldString;
        } else if (len > length) {
            return oldString.substring(0, lenZh);
        }
        return null;
    }

    /** 将字符串集合转换为字符串 */
    public static String toStirng(Collection<String> strs, String split) {
        StringBuffer sb = new StringBuffer();
        for (String st : strs) {
            sb.append(st + split);
        }
        return sb.substring(0, sb.length() - split.length());
    }

    /** 将null转换为其他字符 */
    public static String NullToMessage(String str, String msg) {
        if (str != null) {
            return str;
        } else {
            return msg;
        }
    }

    /** 将String进行MD5加密 */
    public static String toMd5(String srcStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5 = md.digest(srcStr.getBytes());
            StringBuffer sb = new StringBuffer();
            String part = null;
            for (int i = 0; i < md5.length; i++) {
                part = Integer.toHexString(md5[i] & 0xFF);
                if (part.length() == 1) {
                    part = "0" + part;
                }
                sb.append(part);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            return srcStr;
        }
    }

    /** 生成32位随机字符串 如：B4YG6 28U8O 9OEFP ABXV6 MOKSS DE66L C2 */
    public static String generateGUID() {
        Random RANDOM = new Random();
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }

    /** 生成一位随机字符 */
    public static String getRandomChar() {
        int rand = (int) Math.round(Math.random() * 2);
        long itmp = 0;
        char ctmp = '\u0000';
        switch (rand) {
            case 1:// 大写字母
                itmp = Math.round(Math.random() * 25 + 65);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            case 2:// 小写字母
                itmp = Math.round(Math.random() * 25 + 97);
                ctmp = (char) itmp;
                return String.valueOf(ctmp);
            default:// 数字
                itmp = Math.round(Math.random() * 9);
                return String.valueOf(itmp);
        }
    }

    /**
     * 将有换行符的文本字符串分隔组成字符串数组
     * @param text
     * @return
     * @throws IOException
     */
    public static String[] getStringArray(String text) throws IOException {
        String[] texts = null;
        try {
            String enter = "\n";
            texts = text.split(enter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e);
        }

        return texts;
    }

    /**
     * @Describe：生成给定数量（amount）的随机字符串
     * @Date：May 5, 2010
     */
    public static String getRandomStr(int amount) {
        String sRand = "";
        for (int i = 0; i < amount; i++) {
            String tmp = getRandomChar();
            sRand += tmp;
        }
        return sRand;
    }

    public static String getLine() {
        return System.getProperty("line.separator");
    }

    private static String htmlEncode(int i) {
        if (i == '&')
            return "&amp;";
        else if (i == '<')
            return "&lt;";
        else if (i == '>')
            return "&gt;";
        else if (i == '"')
            return "&quot;";
        else
            return "" + (char) i;
    }

    /**
     * 转换不安全字符
     * @param st
     * @return
     */
    public static String htmlEncode(String str) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            buf.append(htmlEncode(str.charAt(i)));
        }
        return buf.toString();
    }

    /**
     * 检测内容的安全性
     * @param str
     * @return
     */
    public static boolean safeCheck(String str) {
        String[] safeless = {"<script",
                "</script",
                "<iframe",
                "</iframe",
                "<frame",
                "</frame",
                "set-cookie",
                "%3Cscript",
                "%3C/script",
                "%3Ciframe",
                "%3C/iframe",
                "%3Cframe",
                "%3C/frame",
                //"<",
                //">",
                //"</",
                //"/>",
                //"%3C",
                //"%3E",
                //"%3C/",
                //"/%3E"
        };
        for (String s : safeless) {
            int cue = str.toLowerCase().indexOf(s);
            if (cue != -1) {
                return false;
            }
        }
        return true;
    }

    public static String[] ObjsToStrs(Object[] objs) {
        return Arrays.asList(objs).toArray(new String[0]);
    }

    public static List<String> getImgs(String htmlStr){
        if(isNull(htmlStr))
            return new ArrayList<String>();
        String img="";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        //String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址 

        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img,Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while(m_image.find()){
            img = img + "," + m_image.group();
            // Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            Matcher m  = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while(m.find()){
                System.out.println(m.group(1));
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static Long[] stringToLong(String param) {
        String[] arrayStr = param.split(",");
        Long[] longStr = new Long[arrayStr.length];
        for (int i = 0; i < arrayStr.length; i++) {
            longStr[i] = Long.parseLong(arrayStr[i]);
        }
        return longStr;
    }

    public static void main(String[] args){
       /* System.out.println("null:"+StringUtils.isNumeric(null));
        System.out.println(":"+StringUtils.isNumeric(""));
        System.out.println("+:"+StringUtils.isNumeric("+"));
        System.out.println("-:"+StringUtils.isNumeric("-"));
        System.out.println(".:"+StringUtils.isNumeric("."));
        System.out.println("-1:"+StringUtils.isNumeric("-1"));
        System.out.println("-1.:"+StringUtils.isNumeric("-1."));
        System.out.println("-1.0:"+StringUtils.isNumeric("-1.0"));
        System.out.println("-1.1:"+StringUtils.isNumeric("-1.1"));
        System.out.println("-1.1111000000:"+StringUtils.isNumeric("-1.1111000000"));
        System.out.println("-111.1111000000:"+StringUtils.isNumeric("-111.1111000000"));
        System.out.println("-111..1111000000:"+StringUtils.isNumeric("-111..1111000000"));
        System.out.println("-111..:"+StringUtils.isNumeric("-111.."));
        System.out.println("-.:"+StringUtils.isNumeric("-."));
        System.out.println("1111-1111.1111:"+StringUtils.isNumeric("1111-1111.1111"));
        System.out.println("1:"+StringUtils.isNumeric("1"));
        System.out.println("1.:"+StringUtils.isNumeric("1."));
        System.out.println("1.1:"+StringUtils.isNumeric("1.1"));
        System.out.println(".1:"+StringUtils.isNumeric(".1"));
        System.out.println("0.1:"+StringUtils.isNumeric("0.1"));
        System.out.println("+1:"+StringUtils.isNumeric("+1"));
        System.out.println("+1.:"+StringUtils.isNumeric("+1."));
        System.out.println("+1.0:"+StringUtils.isNumeric("+1.0"));
        System.out.println("+1.1:"+StringUtils.isNumeric("+1.1"));
        System.out.println("+1.1111000000:"+StringUtils.isNumeric("+1.1111000000"));
        System.out.println("+111.1111000000:"+StringUtils.isNumeric("+111.1111000000"));
        System.out.println("+111..1111000000:"+StringUtils.isNumeric("+111..1111000000"));
        System.out.println("+111..:"+StringUtils.isNumeric("+111.."));
        System.out.println("+.:"+StringUtils.isNumeric("+."));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("null:"+org.apache.commons.lang3.StringUtils.isNumeric(null));
        System.out.println(":"+org.apache.commons.lang3.StringUtils.isNumeric(""));
        System.out.println("+:"+org.apache.commons.lang3.StringUtils.isNumeric("+"));
        System.out.println("-:"+org.apache.commons.lang3.StringUtils.isNumeric("-"));
        System.out.println(".:"+org.apache.commons.lang3.StringUtils.isNumeric("."));
        System.out.println("-1:"+org.apache.commons.lang3.StringUtils.isNumeric("-1"));
        System.out.println("-1.:"+org.apache.commons.lang3.StringUtils.isNumeric("-1."));
        System.out.println("-1.0:"+org.apache.commons.lang3.StringUtils.isNumeric("-1.0"));
        System.out.println("-1.1:"+org.apache.commons.lang3.StringUtils.isNumeric("-1.1"));
        System.out.println("-1.1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("-1.1111000000"));
        System.out.println("-111.1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("-111.1111000000"));
        System.out.println("-111..1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("-111..1111000000"));
        System.out.println("-111..:"+org.apache.commons.lang3.StringUtils.isNumeric("-111.."));
        System.out.println("-.:"+org.apache.commons.lang3.StringUtils.isNumeric("-."));
        System.out.println("1111-1111.1111:"+org.apache.commons.lang3.StringUtils.isNumeric("1111-1111.1111"));
        System.out.println("1:"+org.apache.commons.lang3.StringUtils.isNumeric("1"));
        System.out.println("1.:"+org.apache.commons.lang3.StringUtils.isNumeric("1."));
        System.out.println("1.1:"+org.apache.commons.lang3.StringUtils.isNumeric("1.1"));
        System.out.println(".1:"+org.apache.commons.lang3.StringUtils.isNumeric(".1"));
        System.out.println("0.1:"+org.apache.commons.lang3.StringUtils.isNumeric("0.1"));
        System.out.println("+1:"+org.apache.commons.lang3.StringUtils.isNumeric("+1"));
        System.out.println("+1.:"+org.apache.commons.lang3.StringUtils.isNumeric("+1."));
        System.out.println("+1.0:"+org.apache.commons.lang3.StringUtils.isNumeric("+1.0"));
        System.out.println("+1.1:"+org.apache.commons.lang3.StringUtils.isNumeric("+1.1"));
        System.out.println("+1.1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("+1.1111000000"));
        System.out.println("+111.1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("+111.1111000000"));
        System.out.println("+111..1111000000:"+org.apache.commons.lang3.StringUtils.isNumeric("+111..1111000000"));
        System.out.println("+111..:"+org.apache.commons.lang3.StringUtils.isNumeric("+111.."));
        System.out.println("+.:"+org.apache.commons.lang3.StringUtils.isNumeric("+."));
        System.out.println("---------------------------------------------------------------------------------------------");
//		System.out.println("null:"+Double.parseDouble(null));
//		System.out.println(":"+Double.parseDouble(""));
//		System.out.println("+:"+Double.parseDouble("+"));
//		System.out.println("-:"+Double.parseDouble("-"));
//		System.out.println(".:"+Double.parseDouble("."));
        System.out.println("-1:"+Double.parseDouble("-1"));
        System.out.println("-1.:"+Double.parseDouble("-1."));
        System.out.println("-1.0:"+Double.parseDouble("-1.0"));
        System.out.println("-1.1:"+Double.parseDouble("-1.1"));
        System.out.println("-1.1111000000:"+Double.parseDouble("-1.1111000000"));
        System.out.println("-111.1111000000:"+Double.parseDouble("-111.1111000000"));
//		System.out.println("-111..1111000000:"+Double.parseDouble("-111..1111000000"));
//		System.out.println("-111..:"+Double.parseDouble("-111.."));
//		System.out.println("-.:"+Double.parseDouble("-."));
//		System.out.println("1111-1111.1111:"+Double.parseDouble("1111-1111.1111"));
        System.out.println("1:"+Double.parseDouble("1"));
        System.out.println("1.:"+Double.parseDouble("1."));
        System.out.println("1.1:"+Double.parseDouble("1.1"));
        System.out.println(".1:"+Double.parseDouble(".1"));
        System.out.println("0.1:"+Double.parseDouble("0.1"));
        System.out.println("+1:"+Double.parseDouble("+1"));
        System.out.println("+1.:"+Double.parseDouble("+1."));
        System.out.println("+1.0:"+Double.parseDouble("+1.0"));
        System.out.println("+1.1:"+Double.parseDouble("+1.1"));
        System.out.println("+1.1111000000:"+Double.parseDouble("+1.1111000000"));
        System.out.println("+111.1111000000:"+Double.parseDouble("+111.1111000000"));*/
//		System.out.println("+111..1111000000:"+Double.parseDouble("+111..1111000000"));
//		System.out.println("+111..:"+Double.parseDouble("+111.."));
//		System.out.println("+.:"+Double.parseDouble("+."));

        String param = "2,3,4";
        Long[] result = StringUtils.stringToLong(param);
        System.out.println(result);

    }
}

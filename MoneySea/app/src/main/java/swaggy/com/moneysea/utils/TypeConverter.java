package swaggy.com.moneysea.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import it.sauronsoftware.base64.Base64;

/**
 * Writer：GuoWei_aoj on 2018/5/3 0003 16:10
 * description:
 */
public class TypeConverter {
    //图片转化成base64字符串
    public static String GetImageStr(String path)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = path;//待处理的图片
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码

        return String.valueOf(Base64.encode(data));//返回Base64编码过的字节数组字符串
    }


    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return "data:image/jpg;base64,"+android.util.Base64.encodeToString(buffer, android.util.Base64.DEFAULT);
    }
}

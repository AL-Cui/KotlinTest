package util;

import android.util.Log;

import com.tencent.cos.utils.SHA1Utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by cuiduo on 2017/5/16.
 */

public class SignatureUtil {
    private static final String TAG = "SignatureUtil";

    public static String createSignature(String secretId, String currentTimeStamp, String random, String secretKey) throws Exception {
//        String expireTime = createExpireTime(currentTimeStamp);
        String expireTime = "1492737957";
        Log.d(TAG, "expireTime=" + expireTime);
        String plainText = "secretId" + "=" + secretId + "&" + "currentTimeStamp" + "=" + currentTimeStamp + "&" +
                "expireTime" + "=" + expireTime + "&" + "random" + "=" + random;
        Log.d(TAG, "plainText=" + plainText);
        String cipherText = HmacSHA1Encrypt(plainText, secretKey);
        Log.d(TAG, "cipherText=" + cipherText);
        Log.d(TAG, "密文串长度=" + stringToBinaryString(cipherText).length());
        String concatText = stringToBinaryString(cipherText) + stringToBinaryString(plainText);
        return concatText;
    }

    public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(Config.ENCODING);
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, Config.MAC_NAME);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(Config.MAC_NAME);
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(Config.ENCODING);
        //完成 Mac 操作
        String cipherText = new String(mac.doFinal(text), Config.ENCODING);
        return cipherText;
    }

    public static String Random() {
//        StringBuilder str = new StringBuilder();//定义变长字符串
//        Random random = new Random();
////随机生成数字，并添加到字符串
//        for (int i = 0; i < 8; i++) {
//            str.append(random.nextInt(10));
//        }
////将字符串转换为数字并输出
//        return str.toString();
        String s = UUID.randomUUID().toString().replace("-","");
        return  s;
    }

    // 将字符串转换成二进制字符串
    private static String stringToBinaryString(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]);
        }
        return result;
    }

    private static String createExpireTime(String string) {
        Long a = Long.parseLong(string);
        Long b = a + Config.EFFECTIVE_TIME;
        String s = String.valueOf(a + b);
        return s;
    }

}

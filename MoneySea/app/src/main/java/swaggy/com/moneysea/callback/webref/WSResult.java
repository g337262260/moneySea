package swaggy.com.moneysea.callback.webref;

import java.io.Serializable;

/**
 * Created by guoiwei on 08/12/2016.
 * description : 网络请求返回的基类
 */

public class WSResult<T> implements Serializable {

    private int code;
    private String msg;    // 返回值: true false
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

package swaggy.com.moneysea.callback.webref;

import java.io.Serializable;

/**
 * Created by michael on 08/12/2016.
 */

public class WSSimpleResult implements Serializable {

    private int code;
    private String msg;
    private String result;

    public WSResult toWSResult() {
        WSResult wsResult = new WSResult();
        wsResult.setCode(code);
        wsResult.setMsg(msg);
        wsResult.setResult(result);
        return wsResult;
    }



}

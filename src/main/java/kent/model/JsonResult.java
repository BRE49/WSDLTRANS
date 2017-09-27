package kent.model;


/**
 * Created by ZhangQ on 2017/9/15.
 * 默认的全局返回数据结构
 *
 * {
 *     code ：状态码
 *     info： 说明信息
 *     data： 数据
 * }
 */
public class JsonResult {

    private Integer code;
    private String info;
    private Object data;

    public final static Integer CODE_SUCCESS = 1;
    public final static Integer CODE_ERROR = 0;
    public final static String  INFO_SUCCESS = "success";
    public final static String  INFO_ERROR = "error";

    public JsonResult() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

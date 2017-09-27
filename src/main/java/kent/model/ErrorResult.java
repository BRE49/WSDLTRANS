package kent.model;

/**
 * 错误返回
 * Created by ZhangQ on 2017/9/16.
 */
public class ErrorResult extends JsonResult {

    public ErrorResult(String errorInfo) {
        this.setCode(JsonResult.CODE_ERROR);
        if(this.getInfo() == null){
            this.setInfo(JsonResult.INFO_ERROR);
        }else {
            this.setInfo(errorInfo);
        }
    }
}

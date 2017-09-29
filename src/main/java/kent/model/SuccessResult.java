package kent.model;

/**
 * 成功返回
 * Created by ZhangQ on 2017/9/16.
 */
public class SuccessResult extends JsonResult {



    public SuccessResult() {
        this.setCode(JsonResult.CODE_SUCCESS);
        this.setInfo(JsonResult.INFO_SUCCESS);
    }


}

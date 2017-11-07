package kent.common;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局统一处理异常类
 * Created by ZhangQ on 2017/9/15.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private Logger logger = Logger.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public JsonResult nullPointer(Exception n){
        String errMsg = n.getMessage();
        logger.error(errMsg);
        return  new ErrorResult("转换出错:"+errMsg);
    }
}

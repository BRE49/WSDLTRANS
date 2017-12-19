package kent.action;

import kent.model.JsonResult;
import kent.service.FileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZhangQ on 2017/12/19.
 */
@RestController
public class WSDLUpload {

    @Resource
    private FileUpload fileUpload;

    @RequestMapping(value = "/fileUpload",method = RequestMethod.POST,produces = "application/json")
    public JsonResult fileUpload(HttpServletRequest request){
        return fileUpload.fileUp(request);
    }
}

package kent.action;

import kent.model.JsonResult;
import kent.model.SuccessResult;
import kent.service.WsdlReadService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 根据文件路径，读取文件信息
 * Created by ZhangQ on 2017/9/15.
 */
@RestController
public class WsdlRead {

    @Resource
    private WsdlReadService readService;

    private Logger logger = Logger.getLogger(WsdlRead.class);

    @RequestMapping(value = "/open",method = RequestMethod.GET,produces = "application/json")
    public JsonResult readFile (@RequestParam String filePath) throws IOException{
        logger.info("open controller");
        return readService.openFile(filePath);
    }


}

package kent.action;

import kent.model.JsonResult;
import kent.service.WsdlReadService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

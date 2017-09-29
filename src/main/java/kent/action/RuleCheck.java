package kent.action;

import kent.model.JsonResult;
import kent.model.Wsdl;
import kent.service.RuleCheckService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by ZhangQ on 2017/9/15.
 */
@RestController
public class RuleCheck {

    @Resource
    private RuleCheckService ruleCheckService;

    private Logger logger = Logger.getLogger(RuleCheck.class);

    @RequestMapping(value = "/ruleCheck",method = RequestMethod.POST,produces = "application/json")
    public JsonResult ruleCheck(Wsdl wsdl) throws Exception{
        logger.info("ruleCheck------");
        logger.info("wsdl is-----"+wsdl);
        return ruleCheckService.ruleCheck(wsdl.getContent());
    }
}

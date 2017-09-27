package kent.action;

import kent.model.JsonResult;
import kent.model.Wsdl;
import kent.service.TransitionServiceOnWeb;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@RestController
public class MainController {
    @Resource
    private TransitionServiceOnWeb transition;

    private Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/trans",method = RequestMethod.POST,produces = "application/json")
    public JsonResult transition(Wsdl wsdl) throws UnsupportedEncodingException,DocumentException{
        String wsdlString =  wsdl.getContent();
        logger.info("wsdl is -----------"+wsdlString);
        return transition.trans(wsdlString);
    }


}

package kent.service;

import kent.model.Radl;
import kent.model.Wsdl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MainController {
    @Resource
    TransitionServiceOnWeb transition;

    @RequestMapping(value = "/trans",method = RequestMethod.POST)
    public @ResponseBody Radl transition(Wsdl wsdl){
        StringBuilder wsdlS = transition.trans(wsdl.getContent());
        Radl radl = new Radl();
        radl.setContent(wsdlS.toString());
        return radl;
    }


}

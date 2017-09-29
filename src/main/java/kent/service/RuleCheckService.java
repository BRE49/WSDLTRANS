package kent.service;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import kent.model.SuccessResult;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by ZhangQ on 2017/9/28.
 */
@Service
public class RuleCheckService {

    private Logger logger = Logger.getLogger(RuleCheckService.class);

    public JsonResult ruleCheck(String wsdl) throws Exception{

        if(wsdl == null){
            return new ErrorResult("内容为空，请检查输入");
        }
        SAXReader saxReader = new SAXReader();
        //将String类型转换为InputStream类型
        InputStream wsdlStream = new ByteArrayInputStream(wsdl.getBytes("UTF-8"));
        //加载document树到内存
        Document document  = saxReader.read(wsdlStream);
        Element root = document.getRootElement();
        logger.info("root element is ------"+root.getName());

        if(root.selectNodes("//types").size() == 0){
            return new ErrorResult("types 节点缺失");
        }else if(root.selectNodes("//message").size() == 0){
            return new ErrorResult("message 节点缺失");
        }else if(root.selectNodes("//service").size() == 0){
            return new ErrorResult("service 节点缺失");
        }else if(root.selectNodes("//portType").size() == 0){
            return new ErrorResult("portType 节点缺失");
        }

        return new SuccessResult().setInfo("ok");
    }
}

package kent.service;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import kent.model.SuccessResult;
import kent.util.Const;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * WsdlRead
 * Created by ZhangQ on 2017/9/17.
| */
@Service
public class WsdlReadService{

    private Logger logger = Logger.getLogger(WsdlReadService.class);

    public JsonResult openFile(String fileName) throws IOException{

        JsonResult result = new SuccessResult();
        //获取文件路径后打印到log
        logger.info("the file path is --------"+fileName);
        File file = new File(Const.WEBPATH + fileName);
        if(!file.exists()) {
            return new ErrorResult("file not exist");
        }else if (!file.canRead()){
            return new ErrorResult("file can not read");
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String wsdlTemp;
        StringBuilder wsdl = new StringBuilder();
        while ((wsdlTemp = bufferedReader.readLine()) != null){
            wsdl.append(wsdlTemp);
            wsdl.append("\n");
        }
        bufferedReader.close();

        result.setData(wsdl);
        return  result;
    }
}

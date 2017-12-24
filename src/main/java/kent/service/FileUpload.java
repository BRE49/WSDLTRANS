package kent.service;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import kent.model.SuccessResult;
import kent.util.Const;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by ZhangQ on 2017/12/19.
 */
@Service
public class FileUpload {

    public JsonResult fileUp(HttpServletRequest request){
        JsonResult result = new SuccessResult();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );

        if(multipartResolver.isMultipart(request)){
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest)request;
            Iterator<String> it = servletRequest.getFileNames();
            it.forEachRemaining(file ->{
                MultipartFile multipartFile = servletRequest.getFile(file);
                if(multipartFile != null){
                    String fileName = multipartFile.getOriginalFilename();
                    try {
                        File wsdl = new File(Const.WEBPATH + fileName);
                        //创建云存储文件夹
                        if(!wsdl.getParentFile().exists())
                            wsdl.getParentFile().mkdirs();
                        multipartFile.transferTo(wsdl);
                        result.setInfo(fileName);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }else return new ErrorResult("Not multiple");
        return result;
    }
}

package kent.service;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import kent.model.SuccessResult;
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
                    //本地处理方法 服务器需要另设路径
                    String path = "F:\\Final\\project\\FileSaver\\"+ fileName;
                    try {
                        multipartFile.transferTo(new File(path));
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

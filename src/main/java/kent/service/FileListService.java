package kent.service;

import kent.model.ErrorResult;
import kent.model.JsonResult;
import kent.model.SuccessResult;
import kent.util.Const;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by ZhangQ on 2017/12/23.
 */
@Service
public class FileListService {

    private Logger logger = Logger.getLogger(FileListService.class);

    public JsonResult fileList(){
        JsonResult result = new SuccessResult();
        File file = new File(Const.WEBPATH);
        if(!file.exists())
            return new ErrorResult("File Not Exist");
        File[] files = file.listFiles();
        List<String> fileName = new LinkedList<>();
        if (files != null)
            Arrays.asList(files).iterator().forEachRemaining(f ->fileName.add(f.getName()));
        logger.info("files:  "+fileName);
        result.setData(fileName);
        return result;
    }
}

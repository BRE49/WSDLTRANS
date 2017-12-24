package kent.action;

import kent.model.JsonResult;
import kent.service.FileListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ZhangQ on 2017/12/23.
 * 给出文件夹下的文件列表
 */
@RestController
public class CloudFile {
    @Resource
    private FileListService listService;
    
    @RequestMapping(value = "/fileList",method = RequestMethod.GET,produces = "application/json")
    public JsonResult getList(){
        return listService.fileList();
    }
}

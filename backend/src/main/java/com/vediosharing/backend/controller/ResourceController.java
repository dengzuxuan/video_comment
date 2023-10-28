package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ResourceController
 * @Description 资源上传 下载接口
 * @Author Colin
 * @Date 2023/10/26 17:51
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.RESOURCE_URL_PREFIX)
public class ResourceController {
    @Autowired
    ResourceService resourceService;

    @PostMapping("/upload/photo")
    Result uploadPhoto(@RequestParam("file") MultipartFile file){
        return resourceService.uploadPhoto(file);
    }
}
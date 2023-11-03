package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.OptVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName UserOptController
 * @Description 用户对视频的操作模块，包括点赞 收藏 评论
 * @Author Colin
 * @Date 2023/10/31 14:01
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.OPTVIDEO_URL_PREFIX)
public class UserOptController {
    @Autowired
    OptVideoService optVideoService;

    @PostMapping("/addcollect")
    public Result addCollect(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.addcollect(videoId);
    }
    @PostMapping("/delcollect")
    public Result delCollect(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.delcollect(videoId);
    }

    @PostMapping("/addlike")
    public Result addlike(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.addLike(videoId);
    }
    @PostMapping("/dellike")
    public Result dellike(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.delLike(videoId);
    }

    @GetMapping("/getlikevideos")
    public Result getlikevideos(){
        return optVideoService.getlikeVideo();
    }
    @GetMapping("/getcollectvideos")
    public Result getcollectvideos(){
        return optVideoService.getcollectVideo();
    }
    @GetMapping("/getotherlikevideos")
    public Result geOthertlikevideos(@RequestParam Map<String,String> m1){
        int userId = Integer.parseInt(m1.get("user_id"));
        return optVideoService.getOtherlikeVideo(userId);
    }
    @GetMapping("/getothercollectvideos")
    public Result getOthercollectvideos(@RequestParam Map<String,String> m1){
        int userId = Integer.parseInt(m1.get("user_id"));
        return optVideoService.getOthercollectVideo(userId);
    }

    @PostMapping("/addcomment")
    public Result addcomment(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        int commentId = Integer.parseInt(m1.get("comment_id"));
        return optVideoService.addcomment(videoId,commentId);
    }
    @PostMapping("/delcomment")
    public Result delcomment(@RequestParam Map<String,String> m1){
        int commentId = Integer.parseInt(m1.get("comment_id"));
        return optVideoService.delcomment(commentId);
    }

}

package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.VideoJudgeReqDto;
import com.vediosharing.backend.dto.req.VideoReqDto;

/**
 * @ClassName UserVideoService
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 2:39
 * @Version 1.0
 */
public interface UserVideoService {
    Result judge(VideoJudgeReqDto dto);
    void changeVideoLike(int type,int delta);
    Result addVideo(VideoReqDto reqDto);
    Result initVideoLike(int userId);
    Result getSingleVideo(int videoId);
    Result getUserVideos();
    Result getSingleUserVideos(int userId);
    Result delUserVideo(int videoId);
}

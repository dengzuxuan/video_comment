package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dao.entity.Likes;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.CollectMapper;
import com.vediosharing.backend.dao.mapper.LikeMapper;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.OptVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName OptVideoServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/31 14:10
 * @Version 1.0
 */
@Service
public class OptVideoServiceImpl implements OptVideoService {
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    VideoMapper videoMapper;
    @Override
    public Result addLike(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        Date now = new Date();

        Video findVideo = videoMapper.selectById(videoId);
        if(findVideo == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        Likes newLike = new Likes(
                null,
                user.getId(),
                videoId,
                now,
                now
        );
        likeMapper.insert(newLike);
        return Result.success(null);
    }

    @Override
    public Result delLike(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("video_id",videoId);
        Likes findLike = likeMapper.selectOne(queryWrapper);
        if(findLike == null){
            return Result.build(null, ResultCodeEnum.LIKE_NOT_EXIST);
        }

        return null;
    }

    @Override
    public Result addcollect(int videoId) {
        return null;
    }

    @Override
    public Result delcollect(int videoId) {
        return null;
    }

    @Override
    public Result addcomment(int videoId, int commentId) {
        return null;
    }

    @Override
    public Result delcomment(int commentId) {
        return null;
    }

    @Override
    public Result addLikeComment(int commentId) {
        return null;
    }

    @Override
    public Result delLikeComment(int commentId) {
        return null;
    }
}

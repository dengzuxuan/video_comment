package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.constant.VideoTypeConsts;
import com.vediosharing.backend.core.utils.CaffeineUtil;
import com.vediosharing.backend.core.utils.RankUtil;
import com.vediosharing.backend.dao.entity.*;
import com.vediosharing.backend.dao.mapper.*;
import com.vediosharing.backend.dto.resp.VideoDetailRespDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.VideoService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName VideoServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 22:29
 * @Version 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    UserLikelyMapper userLikelyMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    RankUtil rankUtil;
    @Autowired
    CaffeineUtil caffeineUtil;

    @Override
    public Result getVideo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();

        Video video = new Video();
        History history = caffeineUtil.getHistory(loginuser.getId());

        //当前视频与最新视频index一致，获取新视频
        if(history.getCurrentIndex() == history.getHistory().size()){
            QueryWrapper<UsertLikely> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",loginuser.getId());
            UsertLikely usertLikely = userLikelyMapper.selectOne(queryWrapper);
            //根据用户喜好权重随机获取视频
            List<WeightedItem<Integer>> weightedItems = new ArrayList<>();
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.SPORT,  usertLikely.getSport()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.GAME,  usertLikely.getGame()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.FOOD,  usertLikely.getFood()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.MUSIC,  usertLikely.getMusic()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.FUN,  usertLikely.getFun()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.KNOWLEDGE,  usertLikely.getKnowledge()));
            weightedItems.add(new WeightedItem<>(VideoTypeConsts.ANIMAL,  usertLikely.getAnimal()));

            Integer selectedOption = selectRandomWeightedOption(weightedItems);

            QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("type",selectedOption);
            List<Video> videos = videoMapper.selectList(queryWrapper1);

            //在选中类型里随机挑选视频
            int videoIndex = (int) (Math.random()* videos.size());
            video = videos.get(videoIndex);
            caffeineUtil.addNextHistory(history,video.getId());
            history = caffeineUtil.getHistory(loginuser.getId());
            System.out.println("new:"+history);
        }else{
            //当前视频与最新视频index不一致，获取历史视频
            Map<Integer, Integer> prehistory = history.getHistory();
            video = videoMapper.selectById(prehistory.get(history.getCurrentIndex()+1));
            //更新currentindex
            caffeineUtil.addCurrentIndex(history);
            history = caffeineUtil.getHistory(loginuser.getId());
            System.out.println("new pre:"+history);
        }

        return Result.success(getVideoDetail(video,loginuser.getId()));
    }
    @Override
    public Result getPreVideo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();
        Video video = new Video();
        History history = caffeineUtil.getHistory(loginuser.getId());
        if(history.getCurrentIndex() <= 1){
            //无法向上滑动
            return Result.success(null);
        }else{
            Map<Integer, Integer> prehistory = history.getHistory();
            video = videoMapper.selectById(prehistory.get(history.getCurrentIndex()-1));
            //更新currentindex
            caffeineUtil.deCurrentIndex(history);
        }
        history = caffeineUtil.getHistory(loginuser.getId());
        System.out.println("pre:"+history);
        return Result.success(getVideoDetail(video,loginuser.getId()));
    }

    @Override
    public Result clearPreVideo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();
        History history = caffeineUtil.getHistory(loginuser.getId());
        caffeineUtil.clearHistory(history);
        return Result.success(null);
    }

    @Override
    public Result getTypeVideos(int Type) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Type);
        List<Video> videoAllList = videoMapper.selectList(queryWrapper);
        Collections.shuffle(videoAllList);

        if(videoAllList.size()>8){
            videoAllList=videoAllList.subList(0,8);
        }

        List<VideoDetailRespDto> videoList = new ArrayList<>();
        for (Video video : videoAllList) {
            if(video == null){
                continue;
            }
            User user = userMapper.selectById(video.getUserId());
            videoList.add(new VideoDetailRespDto(user,video));
        }

        return Result.success(videoList);
    }

    @Override
    public Result getTypeDayTop(int type) {
        return Result.success(getTopVideo(type,RankConsts.DAYLY_RANK,0,9));
    }

    @Override
    public Result getTypeWeekTop(int type) {
        return Result.success(getTopVideo(type,RankConsts.WEEKLY_RANK,0,9));
    }

    @Override
    public Result getTypeMonthTop(int type) {
        return Result.success(getTopVideo(type,RankConsts.MONTH_RANK,0,9));
    }

    private Map<String,Object> getVideoDetail(Video video,Integer userId){
        Map<String,Object> res = new HashMap<>();

        video.setViewsPoints(video.getViewsPoints()+1);
        videoMapper.updateById(video);

        QueryWrapper<Collects> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id",userId).eq("video_id",video.getId());
        Collects findCollect= collectMapper.selectOne(queryWrapper2);
        if(findCollect!=null){
            res.put("is_collect",true);
        }else{
            res.put("is_collect",false);
        }

        QueryWrapper<Likes> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("user_id",userId).eq("video_id",video.getId());
        Likes findLike= likeMapper.selectOne(queryWrapper3);
        if(findLike!=null){
            res.put("is_like",true);
        }else{
            res.put("is_like",false);
        }

        QueryWrapper<Friend> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("recv_userid",video.getUserId()).eq("send_userid",userId);
        Friend findFriend= friendMapper.selectOne(queryWrapper4);
        if(findFriend!=null){
            res.put("is_friend",true);
        }else{
            res.put("is_friend",false);
        }

        User author = userMapper.selectById(video.getUserId());
        author.setPassword(null);
        author.setPasswordReal(null);

        res.put("video",video);
        res.put("user",author);

        return res;
    }

    private List<VideoDetailRespDto> getTopVideo(int type,String rank,int start,int end){
        List<VideoDetailRespDto> videoList = new ArrayList<>();
        Set rankVideoIds = rankUtil.getRank(rank,type , start, end);
        for (Object videoId : rankVideoIds) {
            Video video = videoMapper.selectById((int)videoId);
            if(video == null){
                continue;
            }
            double hotPonit = rankUtil.getSingleScore(rank,type,(int)videoId);
            video.setHotPoints((int) hotPonit);
            User user = userMapper.selectById(video.getUserId());
            videoList.add(new VideoDetailRespDto(user,video));
        }
        return videoList;
    }

    public static <T> T selectRandomWeightedOption(List<WeightedItem<T>> items) {
        int totalWeight = items.stream().mapToInt(WeightedItem::getWeight).sum();
        int randomNumber = new Random().nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (WeightedItem<T> item : items) {
            cumulativeWeight += item.getWeight();
            if (randomNumber < cumulativeWeight) {
                return item.getItem();
            }
        }

        return items.get(0).getItem();
    }
    class WeightedItem<T> {
        private T item;
        private int weight;

        public WeightedItem(T item, int weight) {
            this.item = item;
            this.weight = weight;
        }

        public T getItem() {
            return item;
        }

        public int getWeight() {
            return weight;
        }
    }
}

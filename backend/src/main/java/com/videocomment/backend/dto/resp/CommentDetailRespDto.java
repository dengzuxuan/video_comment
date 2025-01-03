package com.videocomment.backend.dto.resp;

import com.videocomment.backend.dao.entity.Comment;
import com.videocomment.backend.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CommentDetailRespDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 14:40
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailRespDto {
    Comment comment;
    User user;
    boolean isLike;
}

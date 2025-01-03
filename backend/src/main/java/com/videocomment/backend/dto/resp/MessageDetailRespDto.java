package com.videocomment.backend.dto.resp;

import com.videocomment.backend.dao.entity.Message;
import com.videocomment.backend.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MessageDetailRespDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 21:14
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDetailRespDto {
    Message message;
    User user;
}

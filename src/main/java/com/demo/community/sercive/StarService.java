package com.demo.community.sercive;

import com.demo.community.dto.PaginationDTO;
import com.demo.community.entity.User;

import java.util.List;

/**
 * @Author: foofoo3
 */
public interface StarService {

    int questionStar(Long target_id, int uid);

    int questionStarCancel(Long target_id, int uid);

    PaginationDTO userStarList(int uid, Integer page, Integer size);

    List<Integer> selectQuestionStar(User user);
}

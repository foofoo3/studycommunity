package com.demo.community.dto;

import com.demo.community.entity.Question;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: foofoo3
 */
@Data
public class HotQuestionDTO implements Comparable {
    private Question question;
    private Integer priority;

    @Override
    public int compareTo(@NotNull Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();

    }
}

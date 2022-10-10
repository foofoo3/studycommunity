package com.demo.community.dto;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: foofoo3
 */
@Data
public class HotTagDTO implements Comparable{
    private String name;
    private Integer priority;

    @Override
    public int compareTo(@NotNull Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();

    }
}

package com.demo.community.cache;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: foofoo3
 */
@Component
@Data
public class HotTagCache {
    private Map<String,Integer> tags = new HashMap<>();
}

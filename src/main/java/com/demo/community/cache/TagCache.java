package com.demo.community.cache;

import com.demo.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO tags1 = new TagDTO();
        tags1.setCategoryName("分区");
        tags1.setTags(Arrays.asList("",""));
        tagDTOS.add(tags1);

        TagDTO tags2 = new TagDTO();
        tags2.setCategoryName("2");
        tags2.setTags(Arrays.asList("21","22"));
        tagDTOS.add(tags2);

        TagDTO tags3 = new TagDTO();
        tags3.setCategoryName("3");
        tags3.setTags(Arrays.asList("31","32"));
        tagDTOS.add(tags3);

        TagDTO tags4 = new TagDTO();
        tags4.setCategoryName("4");
        tags4.setTags(Arrays.asList("41","42"));
        tagDTOS.add(tags4);

        return tagDTOS;
    }
}

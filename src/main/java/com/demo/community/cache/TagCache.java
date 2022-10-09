package com.demo.community.cache;

import com.demo.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author foofoo3
 */
public class TagCache {
    public static List<TagDTO> get(){
        ArrayList<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO tags1 = new TagDTO();
        tags1.setCategoryName("学科分类");
        tags1.setTags(Arrays.asList("哲学","经济学","法学","教育学","文学","历史学","理学","工学","农学","医学","管理学","艺术学","军事学"));
        tagDTOS.add(tags1);

        TagDTO tags2 = new TagDTO();
        tags2.setCategoryName("哲学与法学");
        tags2.setTags(Arrays.asList("形而上学","逻辑学","认识论","伦理学","美学","商法","知识产权法","经济法","民事诉讼法","刑法","国际经济法","法理学","逻辑学"));
        tagDTOS.add(tags2);

        TagDTO tags3 = new TagDTO();
        tags3.setCategoryName("工学");
        tags3.setTags(Arrays.asList("计算机科学与技术","软件工程","电气信息","交通运输","海洋工程","轻工","纺织","航空航天","力学","生物工程","农业工程","林业工程","植物生产","地矿","公安技术"));
        tagDTOS.add(tags3);

        TagDTO tags4 = new TagDTO();
        tags4.setCategoryName("经济学与农学");
        tags4.setTags(Arrays.asList("金融","会计","财政学","保险","财务管理","西方经济学","政治经济学","经济思想史","金融学","海洋渔业","园艺","茶学","林学","森林保护","沙漠治理","农业气象","畜牧","蚕学","烟草"));
        tagDTOS.add(tags4);

        TagDTO tags5 = new TagDTO();
        tags5.setCategoryName("文学与历史学");
        tags5.setTags(Arrays.asList("汉语言","古典文献学","英语","德语","俄语","日语","西班牙语","法语","阿拉伯语","语言学","新闻学","广告学","编辑出版学","世界史","考古学","西方史学史","中国史","文化人类学","古代汉语"));
        tagDTOS.add(tags5);

        TagDTO tags6 = new TagDTO();
        tags6.setCategoryName("理学");
        tags6.setTags(Arrays.asList("数学与应用数学","信息与计算科学","数理基础科学","统计学","数据科学","物理学","材料物理","声学","化学","应用化学","环境科学","人文地理与城乡规划","地质学","天文学"));
        tagDTOS.add(tags6);

        TagDTO tags7 = new TagDTO();
        tags7.setCategoryName("医学");
        tags7.setTags(Arrays.asList("临床医学","口腔医学","护理学","中医学","医学影像学","基础医学","麻醉学","药学","眼视光医学","口腔医学","儿科学","神经病学","外科学","肿瘤学","妇产科学"));
        tagDTOS.add(tags7);

        TagDTO tags8 = new TagDTO();
        tags8.setCategoryName("教育学与管理学");
        tags8.setTags(Arrays.asList("科学教育","艺术教育","学前教育","小学教育","人文教育","体育教育","社会体育指导与管理","管理科学","营销管理","物流管理","工商管理","行政管理","、旅游管理","人力资源管理","市场营销"));
        tagDTOS.add(tags8);


        TagDTO tags9 = new TagDTO();
        tags9.setCategoryName("艺术学与军事学");
        tags9.setTags(Arrays.asList("表演","动画","舞蹈学","戏剧影视文学","录音艺术","音乐表演","书法学","环境设计","服装与服饰设计","绘画","雕塑","美术学","播音与主持艺术","军事管理学","军事思想及军事历史","军队指挥学","战役学","战术学","战略学"));
        tagDTOS.add(tags9);

        return tagDTOS;
    }
}

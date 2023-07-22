package com.creator.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    /**
     * 私有构造方法，单例模式的实现方法之一
     */
    private BeanCopyUtils() {
    }

    /**
     * 将 source 封装为 Vo
     * @param source
     * @param clazz
     * @return
     * @param <V>
     */
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回目标结果
        return result;
    }

    /**
     * 把 List 集合封装为 Vo 的 List 集合
     * @param list
     * @param clazz
     * @return
     * @param <V>
     */
    public static <V> List<V> copyBeanList(List<?> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}

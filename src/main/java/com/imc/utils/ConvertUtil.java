package com.imc.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author luoly
 * @date 2019/3/15 19:27
 * @description
 */
public class ConvertUtil {

    /**
     * 使用BeanUtils#copy()进行属性拷贝
     *
     * @param source      源对象
     * @param targetClass 目标对象class
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> T convertBean(R source, Class<T> targetClass) {
        //入参非空校验
        if (source == null || targetClass == null) {
            throw new RuntimeException("source及targetClass参数不能为空!");
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用自定义转换器进行bean属性参数拷贝
     *
     * @param source    源对象
     * @param convertor 自定义转换器
     * @param <T>       目标类型
     * @param <R>       源对象类型
     * @return
     */
    public static <T, R> T convertBean(R source, Function<R, T> convertor) {
        //入参非空校验
        if (source == null || convertor == null) {
            throw new RuntimeException("source及convertor参数不能为空!");
        }
        return convertor.apply(source);
    }

    /**
     * 使用BeanUtils转换list
     *
     * @param sourceList  源List对象
     * @param targetClass 目标List泛型Class
     * @param <T>         目标List泛型
     * @param <R>         源对象泛型
     * @return
     */
    public static <T, R> List<T> convertList(List<R> sourceList, Class<T> targetClass) {
        //入参非空校验
        if (sourceList == null || targetClass == null) {
            throw new RuntimeException("sourceList及targetClass参数不能为空!");
        }
        //使用BeanUtils#copy()进行属性拷贝
        Function<R, T> beanConvertor = r -> convertBean(r, targetClass);
        return convertList(sourceList, beanConvertor);
    }

    /**
     * 使用自定义转换器转换list
     *
     * @param sourceList 源List对象
     * @param convertor  自定义bean转换器
     * @param <T>        目标List泛型
     * @param <R>        源对象泛型
     * @return
     */
    public static <T, R> List<T> convertList(List<R> sourceList, Function<R, T> convertor) {
        //入参非空校验
        if (sourceList == null || convertor == null) {
            throw new RuntimeException("source及convertor参数不能为空!");
        }
        if (!CollectionUtils.isEmpty(sourceList)) {
            List<T> resultList = sourceList.stream()
                    .map(r -> convertor.apply(r))
                    .collect(Collectors.toList());
            return resultList;
        }
        return null;
    }
}

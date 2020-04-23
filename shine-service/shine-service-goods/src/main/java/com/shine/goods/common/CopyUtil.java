package com.shine.goods.common;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc: 后台管理_类复制
 * ----------------------------
 * @Author: shine
 * @Date: 2020/1/16 9:47
 * ----------------------------
 */
public class CopyUtil {
    private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<String, BeanCopier>();

    //新建的类放在第二位，空值就会被赋值
    public static void copyProperties(Object source, Object target, Converter converter) {
        boolean needConverter = false;
        if (converter != null) {
            needConverter = true;
        }
        BeanCopier copier = generateCopier(source, target, needConverter);
        copier.copy(source, target, converter);
    }

    private static BeanCopier generateCopier(Object source, Object target, boolean needConverter) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        if (!beanCopierMap.containsKey(beanKey)) {
            BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), needConverter);
            beanCopierMap.put(beanKey, copier);
            return copier;
        } else {
            return beanCopierMap.get(beanKey);
        }
    }

    private static String generateKey(Class<? extends Object> class1, Class<? extends Object> class2) {
        return class1.toString() + class2.toString();
    }
}

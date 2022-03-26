package com.kalacheng.util.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by cxf on 2018/10/11.
 */

public class RandomUtil {
    private static Random sRandom;

    static {
        sRandom = new Random();
    }

    public static int nextInt(int bound) {
        return sRandom.nextInt(bound);
    }
    /**
     * 从list中随机抽取元素
     *
     * @param list
     * @param n
     * @return void
     * @throws
     * @Title: createRandomList
     */
    public static List createRandomList(List list, int n) {
        Map map = new HashMap();
        List listNew = new ArrayList();
        int size;
        if (list.size() < n) {
            size = list.size();
        } else {
            size = n;
        }
        while (map.size() < size) {
            int random = (int) (Math.random() * list.size());
            if (!map.containsKey(random)) {
                map.put(random, "");
                listNew.add(list.get(random));
            }
        }
        return listNew;
    }
}

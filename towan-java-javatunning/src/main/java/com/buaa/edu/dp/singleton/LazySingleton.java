package com.buaa.edu.dp.singleton;

public class LazySingleton {
    private LazySingleton() {
        // 创建单例的过程可能会比较慢
        System.out.println("LazySingleton is create");
    }

    private static LazySingleton instance = null;

    // 同步粒度太大
    public static /*不可缺*/synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }


    private static Object obj = new Object();

    // 使用一个成员的double-if-lock
    public static LazySingleton getInstance2() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}

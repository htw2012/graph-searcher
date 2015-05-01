package com.buaa.edu.dp.singleton;

public class LazySingleton {
    private LazySingleton() {
        // ���������Ĺ��̿��ܻ�Ƚ���
        System.out.println("LazySingleton is create");
    }

    private static LazySingleton instance = null;

    // ͬ������̫��
    public static /*����ȱ*/synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }


    private static Object obj = new Object();

    // ʹ��һ����Ա��double-if-lock
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

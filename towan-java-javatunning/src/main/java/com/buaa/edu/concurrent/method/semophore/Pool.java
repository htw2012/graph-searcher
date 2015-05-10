/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.method.semophore;

import java.util.concurrent.Semaphore;

/**
 * <p>信号量实现的对象池</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月10日
 */

class Pool {
       private static final int MAX_AVAILABLE = 100;
       //创建100个许可
       private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
    
       //获得一个池内对象
       public Object getItem() throws InterruptedException {
           //申请一个许可
           available.acquire();
         return getNextAvailableItem();
       }
       //将给定项放回池内，标记为未被使用
       public void putItem(Object x) {
         if (markAsUnused(x)){//新增一个可用项，请求资源被激活一个
             available.release();
         }
       }
    
    // Not a particularly efficient data structure; just for demo
       protected Object[] items = null;//TODO 这里存放对象池的复用对象
       protected boolean[] used = new boolean[MAX_AVAILABLE];
    
       protected synchronized Object getNextAvailableItem() {
         for (int i = 0; i < MAX_AVAILABLE; ++i) {
           if (!used[i]) {//当前项未被使用则获得它
              used[i] = true;//当前项标记为已经使用
              return items[i];
           }
         }
         return null; // not reached
       }
    
       protected synchronized boolean markAsUnused(Object item) {
         for (int i = 0; i < MAX_AVAILABLE; ++i) {
           if (item == items[i]) {//找到给定的索引项
              if (used[i]) {
                used[i] = false;//将给定的标记为未使用
                return true;
              } else
                return false;
           }
         }
         return false;
       }
    }

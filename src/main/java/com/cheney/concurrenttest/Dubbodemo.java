package com.cheney.concurrenttest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cheney
 * 日期 2024/6/18
 */
public class Dubbodemo {
//    // 创建锁与条件变量
//    private final Lock lock = new ReentrantLock();
//    private final Condition done = lock.newCondition();
//
//    // 调用方通过该方法等待结果
//    Object get(int timeout){
//        long start = System.nanoTime();
//        lock.lock();
//        try {
//            while (!isDone()) {
//                done.await(timeout);
//                long cur=System.nanoTime();
//                if (isDone() ||
//                        cur-start > timeout){
//                    break;
//                }
//            }
//        } finally {
//            lock.unlock();
//        }
//        if (!isDone()) {
//            throw new TimeoutException();
//        }
//        return returnFromResponse();
//    }
//    // RPC结果是否已经返回
//    boolean isDone() {
//        return response != null;
//    }
//    // RPC结果返回时调用该方法
//    private void doReceived(Response res) {
//        lock.lock();
//        try {
//            response = res;
//            if (done != null) {
//                done.signal();
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

}

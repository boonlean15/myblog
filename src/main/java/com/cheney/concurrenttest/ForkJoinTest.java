package com.cheney.concurrenttest;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork/join demo
 *
 * @author cheney
 * 日期 2024/6/11
 */
public class ForkJoinTest {
    public static void main(String[] args){
        //创建分治任务线程池
        ForkJoinPool fjp = new ForkJoinPool(4);
        //创建分治任务
        Fibonacci fib = new Fibonacci(6);
        //1 1 2 3 5 8 13 21 34
        //1 1 2 3 5 8
        //启动分治任务
        Integer result = fjp.invoke(fib);
        //输出结果
        System.out.println(result);
    }
    //递归任务
    static class Fibonacci extends RecursiveTask<Integer> {
        final int n;
        Fibonacci(int n){
            this.n = n;
        }
        protected Integer compute(){
            if (n <= 1)
                return n;
            Fibonacci f1 = new Fibonacci(n - 1);
            //创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            //等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}

package com.cheney.concurrentmodel.stm;

/**
 * STM
 * @author Cheney
 * @date 2024/10/15 14:30
 */
//STM
public final class STM {
    //提交数据需要用到的全局锁
    static final Object commitLock = new Object();
    //原子化提交方法
    public static void atomic (TxnRunnable action){
        boolean committed = false;
        //如果没有提交成功，则一直重试
        while (!committed) {
            //创建新的事务
            STMTxn txn = new STMTxn();
            //执行业务逻辑
            action.run(txn);
            //提交事务
            committed = txn.commit();
        }
    }
    //私有化构造方法
    private STM() {
    }
}
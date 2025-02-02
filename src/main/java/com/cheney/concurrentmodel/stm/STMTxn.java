package com.cheney.concurrentmodel.stm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * STM事务实现类
 * @author Cheney
 * @date 2024/10/15 14:17
 */
public final class STMTxn implements Txn{
    private static AtomicLong txnSeq = new AtomicLong(0);
    //保存当前事务中所有读写的数据的快照
    private Map<TxnRef, VersionedRef> inTxnMap = new HashMap<>();
    //当前事务所有需要修改的数据
    private Map<TxnRef, Object> writeMap = new HashMap<>();
    //当前事务ID
    private long txnId;
    //构造函数，自动生成当前事务ID
    STMTxn() {
        txnId = txnSeq.incrementAndGet();
    }

    //获取当前事务中的数据
    @Override
    public <T> T get(TxnRef<T> ref) {
        //将需要读取的数据，加入inTxnMap
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }
        return (T) inTxnMap.get(ref).value;
    }

    //在当前事务中修改数据
    @Override
    public <T> void set(TxnRef<T> ref, T value) {
        //将需要修改的数据，加入inTxnMap
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.curRef);
        }
        writeMap.put(ref, value);
    }
    //提交事务
    boolean commit() {
        synchronized (STM.commitLock) {
            //是否校验通过
            boolean isValid = true;
            //校验所有读过的数据是否发生过变化
            for(Map.Entry<TxnRef, VersionedRef> entry : inTxnMap.entrySet()){
                VersionedRef curRef = entry.getKey().curRef;
                VersionedRef readRef = entry.getValue();
                //通过版本号来验证数据是否发生过变化
                if (curRef.version != readRef.version) {
                    isValid = false;
                    break;
                }
            }
            //如果校验通过，则所有更改生效
            if (isValid) {
                writeMap.forEach((k, v) -> {
                            k.curRef = new VersionedRef(v, txnId);
                });
            }
            return isValid;
        }
    }
}

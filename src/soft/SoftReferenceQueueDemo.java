package soft;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftReferenceQueueDemo {
    public static void main(String[] args) {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        byte[] bytes = new byte[10 * 1024 * 1024];
        SoftReference softReference = new SoftReference(bytes, queue);
        //删除强引用
        bytes = null;
        //调用gc
        System.gc();
        System.out.println("gc之后的值：" + softReference.get()); //对象依然存在
        //如果obj没有被回收，则软引用不会进入引用队列
        Reference<?> reference1 = queue.poll();
        if (reference1 == null) {
            System.out.println("在内存充足时执行gc软引用对象不会被回收，也不会被放到引用队列中");
        }

        //申请较大内存，使内存空间使用率达到阈值，强迫gc
        byte[] bytes2 = new byte[10 * 1024 * 1024];
        //obj被回收，软引用被回收
        System.out.println("gc之后的值：" + softReference.get()); //对象为null
        //如果obj被回收，则软引用会进入引用队列
        try {
            Reference<?> reference = queue.remove();
            if (reference != null) {
                System.out.println("在内存不足时执行gc软引用对象会被回收：" + reference.get() + "，并且软引用被放到引用队列中");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package weak;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class WeakReferenceQueueDemo {
    public static void main(String[] args) {
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        Object obj = new Object();
        WeakReference weakReference = new WeakReference(obj, queue);
        //删除强引用
        obj = null;
        System.out.println("gc之前的值：" + weakReference.get()); //对象依然存在
        //如果obj没有被回收，则弱引用不会进入引用队列
        Reference<?> reference1 = queue.poll();
        if (reference1 == null) {
            System.out.println("没有执行gc，弱引用对象不会被回收，并且弱引用不会被放到引用队列");
        }

        //调用gc
        System.gc();
        System.out.println("gc之后的值：" + weakReference.get()); //对象被回收
        //如果obj被回收，则弱引用会进入引用队列
        try {
            Reference<?> reference = queue.remove();
            if (reference != null) {
                System.out.println("执行gc后弱引用对象会被回收：" + reference.get() + "，并且弱引用对象会被放到引用队列"); //对象为null
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

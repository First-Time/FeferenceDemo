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
        //调用gc
        System.gc();
        //如果obj被回收，则软引用会进入引用队列
        try {
            Reference<?> reference = queue.remove();
            if (reference != null) {
                System.out.println("对象已被回收：" + reference.get()); //对象为null
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

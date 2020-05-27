package soft;

import java.lang.ref.SoftReference;

/**
 * 当内存不足，会触发JVM的GC，如果GC后，内存还是不足，就会把软引用的包裹的对象给干掉，也就是只有在内存不足，JVM才会回收该对象。
 */
public class SoftReferenceDemo {
    public static void main(String[] args) {
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[10 * 1024 * 1024]);
        System.out.println(softReference.get());
//        System.gc();
        System.out.println(softReference.get());
        byte[] bytes2 = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
    }
}

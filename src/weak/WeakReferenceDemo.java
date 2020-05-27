package weak;

import java.lang.ref.WeakReference;

/**
 * 弱引用的特点是不管内存是否足够，只要发生GC，都会被回收
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {
        /*WeakReference<byte[]> weakReference = new WeakReference<>(new byte[1]);
        System.out.println(weakReference.get());
        System.gc();
        System.out.println(weakReference.get());*/

        //由于bytes也引用了new byte[1]，并且是强引用，所以执行gc的时候弱引用不会被回收
        byte[] bytes = new byte[1];
        WeakReference<byte[]> weakReference = new WeakReference<>(bytes);
        System.out.println(weakReference.get());
//        bytes = null; //当主动将bytes置为null时，弱引用此时就会被回收
        System.gc();
        System.out.println(bytes == null);
        System.out.println(weakReference.get());
    }
}

package phantom;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    public static void main(String[] args) {
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<byte[]> phantomReference = new PhantomReference<>(new byte[1], queue);
        System.out.println(phantomReference.get());
    }
}

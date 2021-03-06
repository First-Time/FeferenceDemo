package phantom;

import finalize.Teacher;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhantomReferenceQueueDemo {
//    static Teacher teacher = new Teacher();
    public static void main(String[] args) {
        ReferenceQueue queue = new ReferenceQueue();
        List<byte[]> bytes = new ArrayList<>();
//        PhantomReference<Teacher> reference = new PhantomReference<>(teacher, queue);
        PhantomReference<Teacher> reference = new PhantomReference<>(new Teacher(), queue);
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                bytes.add(new byte[1024 * 1024]);
            }
        });

        Thread thread2 = new Thread(() -> {
            /*while (true) {
                System.gc();
                Reference poll1 = queue.poll();
                if (poll1 == null) {
                    System.out.println("虚引用没有被放到引用队列");
                } else {
                    System.out.println("虚引用被放到引用队列");
                    return;
                }
            }*/

            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true) {
                System.gc();
                Reference poll = queue.poll();

                /*if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                    return;
                } else {
                    System.out.println("111");
                }*/

//                teacher = null;

                if (poll != null) {
                    System.out.println("虚引用被回收了：" + poll);
                    return;
                } else {
                    System.out.println("虚引用没有被回收");
                }
            }
        });

        thread1.start();
        thread2.start();

        Scanner scanner = new Scanner(System.in);
        scanner.hasNext();
    }
}

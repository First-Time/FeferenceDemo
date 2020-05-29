package recyclertime

import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.util.concurrent.TimeUnit

fun main() {
    var queue = ReferenceQueue<ByteArray>()
    //虚引用必须要和引用队列一起使用，它的get方法永远返回null
    val phantomReference = PhantomReference(ByteArray(1024 * 1024 * 5), queue)
    println(queue.poll())
    //当不设置-Xmx10M的时候
    //当不执行gc的时候，两次queue.poll都返回null，说明虚引用没有被放到引用队列中。没有报OutOfMemory。
    //当执行gc的时候，第二次queue.poll返回虚引用对象，说明执行gc的时候虚引用被放到引用队列中。没有报OutOfMemory。

    //当设置-Xmx10M的时候
    //当不执行gc的时候，第一次queue.poll返回null。此时报OutOfMemoryError。虚引用没有被回收。
    //当不执行gc的时候，第一次queue.poll返回null。此时报OutOfMemoryError。虚引用没有被回收。
    System.gc()
    TimeUnit.MILLISECONDS.sleep(300)

    val bytes = ByteArray(1024 * 1024 * 6)

    println(queue.poll())
}

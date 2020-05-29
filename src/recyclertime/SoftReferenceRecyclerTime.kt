package recyclertime

import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.util.concurrent.TimeUnit

fun main() {
    var queue = ReferenceQueue<ByteArray>()

    val softReference = SoftReference(ByteArray(1024 * 1024 * 5), queue)
    println(queue.poll())
    //当不设置-Xmx10M的时候
    //当不执行gc的时候，两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有报OutOfMemory。
    //当执行gc的时候，两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有报OutOfMemory，说明软引用被回收了。

    //当设置-Xmx10M的时候
    //当不执行gc的时候，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。没有报OutOfMemory，说明软引用被回收了。
    //当执行gc的时候，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。没有报OutOfMemory，说明软引用被回收了。
    System.gc()
    TimeUnit.MILLISECONDS.sleep(300)

    val bytes = ByteArray(1024 * 1024 * 6)

    println(queue.poll())
}

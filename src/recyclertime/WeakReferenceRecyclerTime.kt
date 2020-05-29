package recyclertime

import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

fun main() {
    var queue = ReferenceQueue<ByteArray>()

    val weakReference = WeakReference(ByteArray(1024 * 1024 * 5), queue)
    println(queue.poll())
    //当不设置-Xmx10M的时候
    //当不执行gc的时候，两次queue.poll都返回null，说明弱引用没有被放到引用队列中。没有报OutOfMemory。
    //当执行gc的时候，第二次queue.poll返回弱引用对象，说明执行gc的时候弱引用被放到引用队列中。没有报OutOfMemory。

    //当设置-Xmx10M的时候
    //当不执行gc的时候，两次queue.poll都返回null，说明弱引用没有被放到引用队列中。没有报OutOfMemory，说明弱引用被回收了。
    //当执行gc的时候，第二次queue.poll返回弱引用对象，说明执行gc的时候弱引用被放到引用队列中。没有报OutOfMemory。
    System.gc()
    TimeUnit.MILLISECONDS.sleep(300)

    println(queue.poll())

    val bytes = ByteArray(1024 * 1024 * 6)
}

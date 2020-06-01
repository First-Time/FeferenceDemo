package recyclertime

import java.lang.ref.ReferenceQueue
import java.lang.ref.SoftReference
import java.util.concurrent.TimeUnit

/**
 * 软引用对象在内存充足时执行gc不会被回收，也不会放到引用队列。
 * 软引用对象在内存不足时会被自动回收，并放到引用队列中。
 */
fun main() {
    var queue = ReferenceQueue<ByteArray>()

    var softReference: SoftReference<ByteArray>? = SoftReference(ByteArray(1024 * 1024 * 5), queue)
//    val softReference = SoftReference(ByteArray(1024 * 1024 * 6))
//    softReference = null
    println("softReference = $softReference")
    println("softReference = ${softReference?.get()}")
    println("queue.poll() = ${queue.poll()}")


    //当不设置-Xmx10M的时候
    //当不执行gc的时候
    //先分配第二次内存，后输出第二次信息：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //先输出第二次信息，后分配第二次内存：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //总结：软引用在内存充足时不会被回收，也不会被放到引用队列。

    //当执行gc的时候
    //先执行gc，再分配第二次内存，最后输出第二次信息：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //先分配第二次内存，再执行gc，最后输出第二次信息：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //先执行gc，再输出第二次信息，最后分配第二次内存：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //先分配第二次内存，再输出第二次信息，最后执行gc：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //总结：软引用在内存充足时执行gc的时候不会被回收，也不会被放到引用队列中。


    //当设置-Xmx10M的时候
    //当不执行gc的时候
    //先分配第二次内存，后输出第二次信息：第一次软引用对象不为null，第二次软引用对象为null，说明软引用对象被回收了。第一次queue.poll返回null，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。此时没有报OutOfMemoryError。
    //先输出第二次信息，后分配第二次内存：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。此时没有报OutOfMemoryError。
    //总结：软引用在内存不足时，会被自动回收。

    //当执行gc的时候
    //先执行gc，再分配第二次内存，最后输出第二次信息：第一次软引用对象不为null，第二次软引用对象为null，说明软引用对象被回收了。第一次queue.poll返回null，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。此时没有报OutOfMemoryError。
    //先分配第二次内存，再执行gc，最后输出第二次信息：第一次软引用对象不为null，第二次软引用对象为null，说明软引用对象被回收了。第一次queue.poll返回null，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。此时没有报OutOfMemoryError。
    //先分配第二次内存，再输出第二次信息，最后执行gc：第一次软引用对象不为null，第二次软引用对象为null，说明软引用对象被回收了。第一次queue.poll返回null，第二次queue.poll返回软引用对象，说明软引用被放到引用队列中。此时没有报OutOfMemoryError。
    //先执行gc，再输出第二次信息，最后分配第二次内存：两次软引用对象都不为null，说明软引用对象没有被回收。两次queue.poll都返回null，说明软引用没有被放到引用队列中。此时没有报OutOfMemoryError。
    //总结：在第二次输出信息之前，如果进行分配内存（此时分配的内存总量大于可用内存）则第二次软引用对象不为null，第二次queue.poll返回软引用对象，说明软引用在执行gc的时候被回收了，并且放到引用队列中。


    TimeUnit.MILLISECONDS.sleep(300)

//    val bytes = ByteArray(1024 * 1024 * 6)
    System.gc()
//    val bytes = ByteArray(1024 * 1024 * 6)

    println()
    println("softReference = $softReference")
    println("softReference = ${softReference?.get()}")
    println("queue.poll() = ${queue.poll()}")

//    val bytes = ByteArray(1024 * 1024 * 6)
//    println("bytes = $bytes")
//    System.gc()
}

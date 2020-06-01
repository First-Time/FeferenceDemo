package recyclertime

import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue
import java.util.concurrent.TimeUnit

/**
 * 虚引用对象在内存不足时不会被回收。
 * 虚引用对象在由于其他原因引发内存模型执行gc时，虚引用也不会被回收，但是会被放到引用队列。
 */
fun main() {
    var queue = ReferenceQueue<ByteArray>()
    //虚引用必须要和引用队列一起使用，它的get方法永远返回null
    val phantomReference = PhantomReference(ByteArray(1024 * 1024 * 5), queue)
    println("phantomReference = $phantomReference")
    println("phantomReference = ${phantomReference.get()}")
    println("queue.poll() = ${queue.poll()}")

    //当不设置-Xmx10M的时候
    //当不执行gc的时候
    //先分配第二次内存，后输出第二次信息：两次queue.poll都返回null，说明虚引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //先输出第二次信息，后分配第二次内存：两次queue.poll都返回null，说明虚引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //总结：虚引用在内存充足时不会被回收，也不会被放到引用队列。

    //当执行gc的时候
    //先执行gc，再分配第二次内存，最后输出第二次信息：第一次queue.poll返回null，第二次queue.poll返回虚引用对象，说明虚引用被放到引用队列中。没有也不可能报OutOfMemory。
    //先分配第二次内存，再执行gc，最后输出第二次信息：第一次queue.poll返回null，第二次queue.poll返回虚引用对象，说明虚引用被放到引用队列中。没有也不可能报OutOfMemory。
    //先执行gc，再输出第二次信息，最后分配第二次内存：第一次queue.poll返回null，第二次queue.poll返回虚引用对象，说明虚引用被放到引用队列中。没有也不可能报OutOfMemory。
    //先分配第二次内存，再输出第二次信息，最后执行gc：两次queue.poll都返回null，说明虚引用没有被放到引用队列中。没有也不可能报OutOfMemory。
    //总结：在第二次输出信息之前，如果执行gc，那么第二次虚引用对象不为null，第二次queue.poll返回虚引用对象，说明虚引用在执行gc的时候会被放到引用队列中。


    //当设置-Xmx10M的时候
    //当不执行gc的时候
    //先分配第二次内存，后输出第二次信息：第一次queue.poll返回null。此时报OutOfMemoryError，没有第二次输出，因此虚引用没有被回收。
    //先输出第二次信息，后分配第二次内存：两次queue.poll都返回null，说明虚引用没有被放到引用队列中。此时报OutOfMemoryError，因此虚引用没有被回收。
    //总结：虚引用在内存不足时，不会被回收。

    //当执行gc的时候
    //先执行gc，再分配第二次内存，最后输出第二次信息：第一次queue.poll返回null，此时报OutOfMemoryError，没有第二次输出，因此虚引用没有被回收。
    //先分配第二次内存，再执行gc，最后输出第二次信息：第一次queue.poll返回null，此时报OutOfMemoryError，没有第二次输出，因此虚引用没有被回收。
    //先分配第二次内存，再输出第二次信息，最后执行gc：第一次queue.poll返回null，此时报OutOfMemoryError，没有第二次输出，因此虚引用没有被回收。
    //先执行gc，再输出第二次信息，最后分配第二次内存：第一次queue.poll返回null，第二次queue.poll返回虚引用对象，说明虚引用被放到引用队列中。此时报OutOfMemoryError，因此虚引用没有被回收。
    //总结：在第二次输出信息之前，如果进行分配内存（此时分配的内存总量大于可用内存）则会报OutOfMemoryError，说明虚引用在内存不足执行gc时不会被回收，之后放到引用队列中。

    TimeUnit.MILLISECONDS.sleep(300)

//    val bytes = ByteArray(1024 * 1024 * 6)
    System.gc()
//    val bytes = ByteArray(1024 * 1024 * 6)

    println()
    println("phantomReference = $phantomReference")
    println("phantomReference = ${phantomReference.get()}")
    println("queue.poll() = ${queue.poll()}")

    val bytes = ByteArray(1024 * 1024 * 6)
//    System.gc()
}

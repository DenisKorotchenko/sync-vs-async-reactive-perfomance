package org.dksu.teststand.config

import org.apache.catalina.LifecycleState
import org.apache.catalina.core.StandardThreadExecutor
import org.apache.tomcat.util.threads.TaskQueue
import org.apache.tomcat.util.threads.TaskThreadFactory
import org.apache.tomcat.util.threads.ThreadPoolExecutor
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.TimeUnit

class MyThreadExecutor: StandardThreadExecutor() {
    override fun startInternal() {
        val taskqueue = SynchronousQueue<Runnable>()//TaskQueue(maxQueueSize)
        val tf = TaskThreadFactory(namePrefix, daemon, getThreadPriority())
        executor = ThreadPoolExecutor(
            getMinSpareThreads(),
            getMaxThreads(),
            maxIdleTime.toLong(),
            TimeUnit.MILLISECONDS,
            taskqueue,
            tf
        )
        executor.threadRenewalDelay = threadRenewalDelay
        if (prestartminSpareThreads) {
            executor.prestartAllCoreThreads()
        }
        //taskqueue.setParent(executor)

        state = LifecycleState.STARTING
    }

    override fun execute(command: Runnable?) {
        super.execute(command)
    }
}
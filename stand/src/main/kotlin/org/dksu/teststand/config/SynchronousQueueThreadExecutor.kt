package org.dksu.teststand.config

import org.apache.catalina.LifecycleState
import org.apache.catalina.core.StandardThreadExecutor
import org.apache.tomcat.util.threads.TaskThreadFactory
import org.apache.tomcat.util.threads.ThreadPoolExecutor
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.TimeUnit

class SynchronousQueueThreadExecutor: StandardThreadExecutor() {
    override fun startInternal() {
        val taskqueue = SynchronousQueue<Runnable>()
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

        state = LifecycleState.STARTING
    }

    override fun execute(command: Runnable?) {
        super.execute(command)
    }
}
package net.rikarin.hosting

import kotlinx.coroutines.*

abstract class BackgroundService : HostedService {
    var job: Job? = null
        private set

    protected abstract suspend fun execute()

    override suspend fun start() {
        job = GlobalScope.launch { execute() }
    }

    override suspend fun stop() {
        if (job == null) {
            return
        }

//        try {
            job!!.cancelAndJoin()
//        } catch ()
    }
}
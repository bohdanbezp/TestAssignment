package dev.simplx.testassignment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dev.simplx.domain.bootevent.BootEvent
import dev.simplx.domain.bootevent.InsertBootEventUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BootCompletedReceiver : BroadcastReceiver() {

    @Inject
    lateinit var insertBootEventUseCase: InsertBootEventUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val scope = CoroutineScope(Dispatchers.IO)

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val bootEvent = BootEvent(System.currentTimeMillis())

            scope.launch {
                insertBootEventUseCase.invoke(bootEvent)
            }
        }
    }
}
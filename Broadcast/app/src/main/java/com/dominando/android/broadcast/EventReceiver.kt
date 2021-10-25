package com.dominando.android.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class EventReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        Toast.makeText(context, "Ação:\n$action", Toast.LENGTH_SHORT).show()

        if (Intent.ACTION_BOOT_COMPLETED == action) {
            // Não faça isso na sua aplicação!
            // Isso vai irritar o usuário :)

            val it = Intent(context, MainActivity::class.java)
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(it)
        }
    }
}
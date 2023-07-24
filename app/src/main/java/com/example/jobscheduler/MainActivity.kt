package com.example.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startJobScheduler()
    }

    private fun startJobScheduler() {
        val componentName = ComponentName(this,JobSchedulerService::class.java)
        val jobInfo = JobInfo.Builder(123,componentName)
            .setPeriodic(90000)
            .build()

        val scheduler: JobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val job = scheduler.schedule(jobInfo)

        if(job == JobScheduler.RESULT_SUCCESS) {
            Log.d("JOBSERV", "startJobScheduler: success")
        } else {
            Log.d("JOBSERV", "startJobScheduler: failed")
        }
    }
}
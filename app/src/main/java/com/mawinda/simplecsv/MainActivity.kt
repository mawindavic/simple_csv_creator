package com.mawinda.simplecsv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        //List
        val instance = listOf(
            Message(
                number = "0722149976",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            ),
            Message(
                number = "0712345678",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            ),
            Message(
                number = "0712345679",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            ),
            Message(
                number = "0701234567",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            ),
            Message(
                number = "01012345678",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            ),
            Message(
                number = "079999999",
                body = "This is a sample msg",
                date = System.nanoTime().toString(),
                id = "dsssssds"
            )
        )


        val mFile = CsvUtils.toCsvFile(this, instance, "messages_list")
        Timber.i("File Exists: ${mFile.exists()}")


    }


}

data class Message(
    val number: String,
    val body: String,
    val date: String,
    val id: String
)
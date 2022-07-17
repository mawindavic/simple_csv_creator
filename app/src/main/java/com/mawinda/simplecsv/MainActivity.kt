package com.mawinda.simplecsv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import kotlin.reflect.full.memberProperties

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        data class DataClass(
            val b: String,
            val a: String,
            val c: String,
            val d: String,
            val e: String
        )

        val instance = DataClass("A", "B", "C", "D", "E")

        val list = dataClassToList(instance)
        Timber.i("Data: ${list.joinToString(",")}")

//        DataClass::class.memberProperties.forEach { member ->
//            val name = member.name
//            val value = member.get(instance) as String
//
//            Timber.i("name: $name ,value: $value")
//
//        }
    }

    private inline fun <reified T : Any> dataClassToList(data: T): List<String> {
        return when {
            T::class.isData.not() -> throw UnsupportedClassVersionError("Class Not a Data Class")
            else -> {
                T::class.memberProperties.map {
                    it.get(data) as String
                }
            }
        }
    }
}
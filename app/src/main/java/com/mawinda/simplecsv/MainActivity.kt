package com.mawinda.simplecsv

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import java.io.IOException
import java.io.OutputStreamWriter
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

        val instance = listOf(
            DataClass("A", "B", "C", "D", "E"),
            DataClass("B", "B", "C", "D", "E"),
            DataClass("C", "B", "C", "D", "E"),
            DataClass("D", "B", "C", "D", "E")
        )

        val mData = instance.toCsvData()
        Timber.i("SCV DATA:\n$mData")

    }


    private inline fun <reified T : Any> List<T>.toCsvData(): String {
        return when {
            this.isEmpty() -> throw Exception("List is Empty")
            else -> {
                this.toRawData().rawDataToCsvData()
            }
        }

    }

    private fun List<List<String>>.rawDataToCsvData(): String {
        val listOfRows = this.map { it.joinToString(",") }
        return listOfRows.joinToString("\n")
    }


    private inline fun <reified T : Any> List<T>.toRawData(): List<List<String>> {
        val data: MutableList<List<String>> = mutableListOf()
        val labels = this.first().dataClassParametersToList()
        data.add(0, labels)
        val inputs = this.map { it.dataClassInputsToList() }
        data.addAll(inputs)
        return data.toList()
    }


    private inline fun <reified T : Any> T.dataClassParametersToList(): List<String> {
        return when {
            T::class.isData.not() -> throw UnsupportedClassVersionError("Class Not a Data Class")
            else -> {
                T::class.memberProperties.map {
                    it.name
                }
            }
        }
    }

    private inline fun <reified T : Any> T.dataClassInputsToList(): List<String> {
        return when {
            T::class.isData.not() -> throw UnsupportedClassVersionError("Class Not a Data Class")
            else -> {
                T::class.memberProperties.map {
                    it.get(this) as String
                }
            }
        }
    }
}
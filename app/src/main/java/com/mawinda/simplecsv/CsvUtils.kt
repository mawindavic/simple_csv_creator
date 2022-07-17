package com.mawinda.simplecsv

import android.content.Context
import java.io.File
import kotlin.reflect.full.memberProperties

object CsvUtils {
    inline fun <reified T : Any> toCsvFile(
        context: Context,
        data: List<T>,
        fileName: String
    ): File {

        val csvData = data.toCsvData()
        return writeToFile(csvData, context, fileName)
    }

    /**
     * Writes Data to Internal File
     * @param data: String to be written in file
     * @param context to access directory
     * @param fileName Name of the file
     */
    @PublishedApi
    internal fun writeToFile(data: String, context: Context, fileName: String): File {
        val mFileName = fileName.plus(".csv")
        context.openFileOutput(mFileName, Context.MODE_PRIVATE).use {
            it.write(data.toByteArray())
        }
        return File(context.filesDir, mFileName)
    }


    /**
     * Converts List of Data classess to csv data type
     */
    @PublishedApi
    internal inline fun <reified T : Any> List<T>.toCsvData(): String {
        return when {
            this.isEmpty() -> throw Exception("List is Empty")
            else -> {
                this.toRawData().rawDataToCsvData()
            }
        }

    }

    /**
     * Converts List of list of strings to csv data
     * comma separation for columns
     * new line separator for rows
     */
    @PublishedApi
    internal fun List<List<String>>.rawDataToCsvData(): String {
        val listOfRows = this.map { it.joinToString(",") }
        return listOfRows.joinToString("\n")
    }


    /**
     * Converts List of Data classess to List of list of strings
     * with first list carrying parameters i.e column labels
     */
    @PublishedApi
    internal inline fun <reified T : Any> List<T>.toRawData(): List<List<String>> {
        val data: MutableList<List<String>> = mutableListOf()
        val labels = this.first().dataClassParametersToList()
        data.add(0, labels)
        val inputs = this.map { it.dataClassInputsToList() }
        data.addAll(inputs)
        return data.toList()
    }


    /**
     * Change data class parameters to list of string
     */
    @PublishedApi
    internal inline fun <reified T : Any> T.dataClassParametersToList(): List<String> {
        return when {
            T::class.isData.not() -> throw Exception("${T::class.simpleName} Not a Data Class")
            else -> {
                T::class.memberProperties.map {
                    it.name
                }
            }
        }
    }

    /**
     * Converts data class values to list of strings
     */
    @PublishedApi
    internal inline fun <reified T : Any> T.dataClassInputsToList(): List<String> {
        return when {
            T::class.isData.not() -> throw Exception("${T::class.simpleName} Not a Data Class")
            else -> {
                T::class.memberProperties.map {
                    it.get(this) as String
                }
            }
        }
    }
}
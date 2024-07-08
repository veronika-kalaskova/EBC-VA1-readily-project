package com.example.readily.utils

import java.util.Locale

object LanguageUtils {

    private val CZECH = "cs"
    private val SLOVAK = "sk"

    fun isLanguageCloseToCzech(): Boolean {
        val language = Locale.getDefault().language
        return language.equals(CZECH) || language.equals(SLOVAK)
    }



}

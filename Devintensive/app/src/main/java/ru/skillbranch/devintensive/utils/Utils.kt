package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    private val translitMap = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
    )

    fun parseFullName (fullName:String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)

        if (firstName == null && lastName == null || fullName == "" || fullName == " ") {
            firstName = null
            lastName = null
        }

        return Pair(firstName, lastName)
    }

    fun toInitials(firstName:String?, lastName:String?):String?{
        if (firstName == null){
            val lastNameInitial = lastName?.get(0)?.toUpperCase().toString()
            return (lastNameInitial)
        } else if (lastName == null){
            val firstNameInitial = firstName.get(0).toUpperCase().toString()
            return (firstNameInitial)
        } else if (firstName == "" || lastName == ""){
            return null
        } else if (firstName == " " || lastName == " "){
            return null
        } else{
            val firstNameInitial = firstName.get(0).toUpperCase().toString()
            val lastNameInitial = lastName.get(0).toUpperCase().toString()
            return (firstNameInitial+lastNameInitial)
        }
    }


    fun transliteration(payload:String, divider:String = " "):String {
        var result = ""
        payload.forEach {
            result += when {
                it == ' ' -> divider
                it.isUpperCase() -> translitMap[it.toLowerCase()]?.capitalize()
                        ?: it.toString()
                else -> translitMap[it] ?: it.toString()
            }
        }
        return result
    }
}
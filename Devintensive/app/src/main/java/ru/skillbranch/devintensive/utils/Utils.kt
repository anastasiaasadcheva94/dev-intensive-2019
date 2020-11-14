package ru.skillbranch.devintensive.utils

object Utils {
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
            val lastNameInitial = lastName?.get(0)?.toUpperCase()
            return ("$lastNameInitial")
        } else if (lastName == null){
            val firstNameInitial = firstName.get(0).toUpperCase()
            return ("$firstNameInitial")
        } else if (firstName == ""){
            return null
        } else if (lastName == ""){
            return null
        } else if (firstName == " "){
            return null
        } else if (lastName == " "){
            return null
        } else{
            val firstNameInitial = firstName.get(0).toUpperCase()
            val lastNameInitial = lastName.get(0).toUpperCase()
            return ("$firstNameInitial$lastNameInitial")
        }
    }

    fun transliteration(payload:String, divider:String = " "):String {
        val parts:List<String>? = payload.split(" ")

        val sb = StringBuilder()
        //val sb2 = StringBuilder()
        var i = 0
        var k = 0
        if (parts != null) {
            while (k < parts.size) {
                while (i < parts[k].length) {
                    val res = parts[k]
                    val result = when (res[i].toLowerCase().toString()) {
                        "а" -> "a"
                        "б" -> "b"
                        "в" -> "v"
                        "г" -> "g"
                        "д" -> "d"
                        "е" -> "e"
                        "ё" -> "e"
                        "ж" -> "zh"
                        "з" -> "z"
                        "и" -> "i"
                        "й" -> "i"
                        "к" -> "k"
                        "л" -> "l"
                        "м" -> "m"
                        "н" -> "n"
                        "о" -> "o"
                        "п" -> "p"
                        "р" -> "r"
                        "с" -> "s"
                        "т" -> "t"
                        "у" -> "u"
                        "ф" -> "f"
                        "х" -> "h"
                        "ц" -> "c"
                        "ч" -> "ch"
                        "ш" -> "sh"
                        "щ" -> "sh'"
                        "ъ" -> ""
                        "ы" -> "i"
                        "ь" -> ""
                        "э" -> "e"
                        "ю" -> "yu"
                        "я" -> "ya"
                        " " -> " "
                        else -> res[i].toString()
                    }
                    sb.append(result)//.append(divider)
                    //sb.append(divider)
                    i++
                }
                sb.append(divider)
                k++
                i=0
            }
        }

        val firstName = sb.substring(0,1).toUpperCase()+sb.substring(1)//заглавная первая буква
        //val lastName = sb2.substring(0,1).toUpperCase()+sb2.substring(1)
        return "$firstName"
    }
}
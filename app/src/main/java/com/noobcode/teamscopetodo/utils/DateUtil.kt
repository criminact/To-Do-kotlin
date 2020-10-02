package com.noobcode.teamscopetodo.utils

import java.sql.Date
import java.text.SimpleDateFormat

class DateUtil {

    companion object{
        fun getFormattedDate(time: Long): String{
            var date: Date = Date(time)
            val sdf = SimpleDateFormat("MMMM dd, yyyy")
            return sdf.format(date)
        }
    }

}
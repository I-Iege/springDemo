package com.hege.springDemo.data

import java.util.Date
import org.springframework.data.annotation.Id;
import javax.annotation.processing.Generated

data class Meeting(var title: String,
                   var participants : List<Participant>,
                   var plannedDate: Date,
                   var description : String,
                   var location : String) {

    @Id @Generated
    lateinit var id: String
    var closed = false
}

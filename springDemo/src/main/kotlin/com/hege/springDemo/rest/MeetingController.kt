package com.hege.springDemo.rest

import com.hege.springDemo.data.Meeting
import com.hege.springDemo.business.MeetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/meetings")
class MeetingController {
    @Autowired
    private lateinit var meetingService: MeetingService

    @GetMapping(value = ["/read"])
    fun getAllMeetings(@RequestHeader("X-AUTHENTICATED-USER") username : String): List<Meeting> {
        return meetingService.getAllMeetings(username)
    }
    @GetMapping(value = ["/readwithstate/{state}"])
    fun getMeetingsWithState(@RequestHeader("X-AUTHENTICATED-USER") username : String, closed : Boolean): List<Meeting> {
        return meetingService.getMeetingsWithState(username,closed)
    }
    @GetMapping(value = ["/read/{meetingId}"])
    @ResponseStatus(HttpStatus.FOUND)
    fun getMeetingById(@RequestHeader("X-AUTHENTICATED-USER") username : String, @PathVariable meetingId: String): Meeting {
       return meetingService.getMeetingById(username, meetingId)
    }

    @PostMapping(value = ["/create"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createMeeting(@RequestBody meeting: Meeting): Meeting {
        return meetingService.createMeeting(meeting)
    }

    @PostMapping(value = ["/update"])
    fun updateMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @RequestBody meeting: Meeting): Meeting {
       return meetingService.updateMeeting(username, meeting)
    }

    @DeleteMapping(value = ["/delete/{meetingId}"])
    fun deleteMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @PathVariable meetingId: String) {
        meetingService.deleteMeeting(username, meetingId)
    }

    @PostMapping(value = ["/close"])
    fun closeMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @PathVariable meetingId: String) {
       meetingService.closeMeeting(username, meetingId)
    }
}
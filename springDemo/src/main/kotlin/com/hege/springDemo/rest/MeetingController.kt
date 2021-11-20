package com.hege.springDemo.rest

import com.hege.springDemo.data.Meeting
import com.hege.springDemo.business.MeetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/meetings")
@Component
class MeetingController {
    @Autowired
    private lateinit var meetingService: MeetingService

    @GetMapping(value = [""])
    fun getAllMeetings(@RequestHeader("X-AUTHENTICATED-USER") username: String): List<Meeting> {
        return meetingService.getAllMeetings(username)
    }

    @GetMapping(value = ["/state/{state}"])
    fun getMeetingsWithState(@RequestHeader("X-AUTHENTICATED-USER") username: String, closed: Boolean): List<Meeting> {
        return meetingService.getMeetingsWithState(username, closed)
    }

    @GetMapping(value = ["/{meetingId}"])
    @ResponseStatus(HttpStatus.FOUND)
    fun getMeetingById(
        @RequestHeader("X-AUTHENTICATED-USER") username: String,
        @PathVariable meetingId: String
    ): Meeting {
        return meetingService.getMeetingById(username, meetingId)
    }

    @PostMapping(value = [""])
    @ResponseStatus(HttpStatus.CREATED)
    fun createMeeting(@RequestBody meeting: Meeting): Meeting {
        return meetingService.createMeeting(meeting)
    }

    @PutMapping(value = [""])
    fun updateMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @RequestBody meeting: Meeting): Meeting {
        return meetingService.updateMeeting(username, meeting)
    }

    @DeleteMapping(value = ["/{meetingId}"])
    fun deleteMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @PathVariable meetingId: String) {
        meetingService.deleteMeeting(username, meetingId)
    }

    @PostMapping(value = ["/close"])
    fun closeMeeting(@RequestHeader("X-AUTHENTICATED-USER") username: String, @PathVariable meetingId: String) {
        meetingService.closeMeeting(username, meetingId)
    }
}
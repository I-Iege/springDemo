package com.hege.springDemo.business

import com.hege.springDemo.data.Meeting
import com.hege.springDemo.data.MeetingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.dao.PermissionDeniedDataAccessException
import org.springframework.stereotype.Service

@Service
class MeetingService {
    @Autowired
    private lateinit var meetingRepository: MeetingRepository

    private fun findMeeting(username: String, id: String): Meeting? {
        val meeting = meetingRepository.findMeetingById(id)
        if (meeting != null && meeting.participants.none { it.username == username }) {
            throw DataRetrievalFailureException("Permission Denied")
        }
        return meeting
    }

    fun getAllMeetings(username: String): List<Meeting> {
        return meetingRepository
            .findMeetingByParticipantsUsername(username);
    }

    fun getMeetingById(username: String, meetingId: String): Meeting {
        return findMeeting(username, meetingId)
            ?: throw DataRetrievalFailureException("The specified id $meetingId does not exist!")
    }

    fun createMeeting(meeting: Meeting): Meeting {
        return meetingRepository.save(meeting)
    }

    fun updateMeeting(username: String, meeting: Meeting): Meeting {
        val tmpMeeting = findMeeting(username, meeting.id)
        if (tmpMeeting != null && tmpMeeting.closed) {
            throw DataIntegrityViolationException("Closed Meetings can not be modified!")
        }
        return meetingRepository.save(meeting)
    }

    fun deleteMeeting(username: String, meetingId: String) {
        findMeeting(username, meetingId)?.run {
            meetingRepository.deleteById(meetingId)
        }
    }

    fun closeMeeting(username: String, meetingId: String) {
        val meeting = findMeeting(username, meetingId)
            ?: throw DataRetrievalFailureException("The specified id $meetingId does not exist!")
        if (meeting.closed) {
            throw DataIntegrityViolationException("Meeting already closed!")
        }
        meeting.closed = true
        meetingRepository.save(meeting)
    }

    fun getMeetingsWithState(username: String, closed: Boolean): List<Meeting> {
        return meetingRepository.findMeetingByParticipantsUsernameAndClosed(username, closed)
    }
}
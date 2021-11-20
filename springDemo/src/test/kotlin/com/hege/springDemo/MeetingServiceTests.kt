package com.hege.springDemo

import com.hege.springDemo.business.MeetingService
import com.hege.springDemo.data.Meeting
import com.hege.springDemo.data.MeetingRepository
import com.hege.springDemo.data.Participant
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.DataRetrievalFailureException
import java.util.*
import kotlin.time.measureTime

@SpringBootTest
class MeetingServiceTests {

    @Autowired
    private lateinit var service: MeetingService

    @MockBean
    private lateinit var meetingRepository: MeetingRepository

    @Test
    fun testCloseMeetings() {
        val meeting =  Meeting("1on1", listOf(Participant("user1","user1")), Date(),"","")
        meeting.id = "0"
        Mockito.`when`(meetingRepository.findMeetingById(meeting.id)).thenReturn(meeting)

        assertThrows<DataRetrievalFailureException> {
            service.closeMeeting("user2", meeting.title)
        }

        service.closeMeeting("user1", meeting.id)
        assert(meeting.closed)

        assertThrows<DataIntegrityViolationException> {
            service.closeMeeting("user1", meeting.id)
        }
    }

    @Test
    fun testCreateMeeting(){
       val meeting = Meeting("1on1", listOf(), Date(),"","")
        meeting.id="0"
        Mockito.`when`(meetingRepository.findMeetingById(meeting.id)).thenReturn(meeting)
        val meeting2 = Meeting("second1on1", listOf(), Date(),"","")
        meeting2.id="1"
        Mockito.`when`(meetingRepository.findMeetingById(meeting2.id)).thenReturn(null)

        var saveCalled = false
        Mockito.`when`(meetingRepository.save(meeting2)).then{
            saveCalled = true
            meeting2
        }
        assertDoesNotThrow {
            service.createMeeting(meeting2)
        }
        assert(saveCalled)
    }

    @Test
    fun testUpdateMeeting(){
        val meeting =  Meeting("1on1", listOf(Participant("john","John Doe")), Date(),"","")
        meeting.id = "0"
        Mockito.`when`(meetingRepository.findMeetingById(meeting.id)).thenReturn(meeting)
        val meeting2 = Meeting("1on1", listOf(
            Participant("john","John Doe"),
            Participant("jane","Jane Doe")
        ), Date(),"","")
        meeting2.id = "1"
        Mockito.`when`(meetingRepository.save(meeting2)).then {
            meeting.participants = meeting2.participants
            meeting
        }
        service.updateMeeting("john",meeting2)
        assert(meeting.participants.size ==2)

        assertThrows<DataRetrievalFailureException> {
            service.updateMeeting("jack",meeting)
        }

        meeting.closed = true

        assertThrows<DataIntegrityViolationException> {
            service.updateMeeting("john",meeting)
        }
    }

    @Test
    fun testDeleteMeeting() {
        val meeting =  Meeting("1on1", listOf(Participant("john","John Doe")), Date(),"","")
        meeting.id ="0"
        Mockito.`when`(meetingRepository.findMeetingById(meeting.id)).thenReturn(meeting)

        assertThrows<DataRetrievalFailureException> {
            service.deleteMeeting("jack",meeting.id)
        }
        var deleteCalled = false;
        Mockito.`when`(meetingRepository.deleteById(meeting.id))
            .then {
                deleteCalled = true
                meeting
            }
        service.deleteMeeting("john",meeting.id)
        assert(deleteCalled)
    }

    @Test
    fun testFindMeetingById() {
        val meeting =  Meeting("1on1", listOf(Participant("john","John Doe")), Date(),"","")
        meeting.id = "0"
        val meeting2 =  Meeting("second1on1", listOf(Participant("jane","Jane Doe")), Date(),"","")
        meeting2.id = "1"
        Mockito.`when`(meetingRepository.findMeetingById(meeting.id)).thenReturn(meeting)
        Mockito.`when`(meetingRepository.findMeetingById(meeting2.id)).thenReturn(meeting2)

        assertThrows<DataRetrievalFailureException> {
            service.getMeetingById("jack",meeting.id)
        }
        assertThrows<DataRetrievalFailureException> {
            service.getMeetingById("john",meeting2.id)
        }

        assertDoesNotThrow {
            service.getMeetingById("john",meeting.id)
            service.getMeetingById("jane",meeting2.id)
        }

    }
}
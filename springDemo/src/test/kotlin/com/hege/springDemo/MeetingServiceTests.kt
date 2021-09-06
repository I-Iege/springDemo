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

@SpringBootTest
class MeetingServiceTests {

    @Autowired
    private lateinit var service: MeetingService

    @MockBean
    private lateinit var meetingRepository: MeetingRepository

    @Test
    fun testCloseMeetings() {
        val meeting =  Meeting("1on1", listOf(Participant("user1","user1")), Date(),"","")
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting.title)).thenReturn(meeting)

        assertThrows<DataRetrievalFailureException> {
            service.closeMeeting("user2", meeting.title)
        }

        service.closeMeeting("user1", meeting.title)
        assert(meeting.closed)

        assertThrows<DataIntegrityViolationException> {
            service.closeMeeting("user1", meeting.title)
        }
    }

    @Test
    fun testCreateMeeting(){
       val meeting = Meeting("1on1", listOf(), Date(),"","")
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting.title)).thenReturn(meeting)
        assertThrows<DataIntegrityViolationException> {
            service.createMeeting(meeting)
        }
        val meeting2 = Meeting("second1on1", listOf(), Date(),"","")
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting2.title)).thenReturn(null)

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
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting.title)).thenReturn(meeting)
        val meeting2 = Meeting("1on1", listOf(
            Participant("john","John Doe"),
            Participant("jane","Jane Doe")
        ), Date(),"","")
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
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting.title)).thenReturn(meeting)

        assertThrows<DataRetrievalFailureException> {
            service.deleteMeeting("jack",meeting.title)
        }
        var deleteCalled = false;
        Mockito.`when`(meetingRepository.deleteById(meeting.title))
            .then {
                deleteCalled = true
                meeting
            }
        service.deleteMeeting("john",meeting.title)
        assert(deleteCalled)
    }

    @Test
    fun testFindMeetingById() {
        val meeting =  Meeting("1on1", listOf(Participant("john","John Doe")), Date(),"","")
        val meeting2 =  Meeting("second1on1", listOf(Participant("jane","Jane Doe")), Date(),"","")
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting.title)).thenReturn(meeting)
        Mockito.`when`(meetingRepository.findMeetingByTitle(meeting2.title)).thenReturn(meeting2)

        assertThrows<DataRetrievalFailureException> {
            service.getMeetingById("jack",meeting.title)
        }
        assertThrows<DataRetrievalFailureException> {
            service.getMeetingById("john",meeting2.title)
        }

        assertDoesNotThrow {
            service.getMeetingById("john",meeting.title)
            service.getMeetingById("jane",meeting2.title)
        }

    }
}
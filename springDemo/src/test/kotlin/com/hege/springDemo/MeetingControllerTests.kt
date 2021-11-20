package com.hege.springDemo

import com.fasterxml.jackson.databind.ObjectMapper
import com.hege.springDemo.business.MeetingService
import com.hege.springDemo.data.Meeting
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.mockito.Mockito.`when`
import org.springframework.dao.DataRetrievalFailureException
import java.util.*


@SpringBootTest
@AutoConfigureMockMvc
internal class MeetingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var service: MeetingService

    private lateinit var meeting : Meeting
    private lateinit var jsonMeeting : String
    private val contentType = "application/json; charset=utf8"

    @BeforeEach
    fun init() {
        meeting = Meeting("test", listOf(), Date(),"","")
        meeting.id = "0"
        val objectMapper = ObjectMapper()
        jsonMeeting = objectMapper.writeValueAsString(meeting)
        `when`(service.getAllMeetings("user1")).thenReturn(listOf())
        `when`(service.getMeetingById("user1","test")).thenReturn(meeting)
        `when`(service.getMeetingById("user1","0")).thenThrow(DataRetrievalFailureException("id not found"))
        `when`(service.createMeeting(meeting)).thenReturn(meeting)
        `when`(service.updateMeeting("user1",meeting)).thenReturn(meeting)
        `when`(service.deleteMeeting("user1","test")).then {}
    }

    @Test
    fun testGetAllMeetings() {
        mockMvc.perform(get("/meetings/read")
            .header("X-AUTHENTICATED-USER","user1"))
            .andExpect(status().isOk)
    }

    @Test
    fun testGetMeeting() {
        mockMvc.perform(get("/meetings/read/test")
            .header("X-AUTHENTICATED-USER","user1"))
            .andExpect(status().isFound)
    }

    @Test
    fun testGetNotExistingMeeting() {
        mockMvc.perform(get("/meetings/read/0")
            .header("X-AUTHENTICATED-USER","user1"))
            .andExpect(status().isNotFound)
    }
    @Test
    fun testCreateMeeting() {
        mockMvc.perform(post("/meetings/create")
            .contentType(contentType)
            .content(jsonMeeting)
        )
            .andExpect(status().isCreated)
    }

    @Test
    fun testUpdateMeeting() {
        mockMvc.perform(post("/meetings/update")
            .header("X-AUTHENTICATED-USER","user1")
            .contentType(contentType)
            .content(jsonMeeting)
        )
            .andExpect(status().isOk)
    }
    @Test
    fun testDeleteMeeting() {
        mockMvc.perform(delete("/meetings/delete/test")
            .header("X-AUTHENTICATED-USER","user1")
        )
            .andExpect(status().isOk)
    }

    @Test
    fun testReadMeetingWithState() {
        mockMvc.perform(get("/meetings/readwithstate/false")
            .header("X-AUTHENTICATED-USER","user1")
        )
            .andExpect(status().isOk)


    }

}
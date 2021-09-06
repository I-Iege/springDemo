package com.hege.springDemo.data
import org.springframework.data.mongodb.repository.MongoRepository;

interface MeetingRepository : MongoRepository<Meeting, String> {
       fun findMeetingById(id : String) : Meeting?
       fun findMeetingByTitle(title : String) : Meeting?
       fun findMeetingByClosed(closed : Boolean) : List<Meeting>
       fun findMeetingByParticipantsUsername(username: String) : List<Meeting>
       fun findMeetingByParticipantsUsernameAndClosed(username: String, state: Boolean) : List<Meeting>
}
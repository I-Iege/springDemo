# Description
This application handles the CRUD functionalities of meetings.
The Spring project Contains a rest server configured with mongoDB.

# Getting Started

### Create meeting 
````
curl -i -X POST -H "Content-Type:application/json" -d "{\"title\":\"1on1\",\"participants\":[{\"username\":\"user1\",\"name\":\"people\"},{\"username\":\"user2\",\"name\":\"people2\"}],\"plannedDate\":1630945253743,\"description\":\"meeting\",\"location\":\"zoom\"}" http://localhost:8080/meetings/create
````
### Read meetings

#### Get all meeting
````
curl -i  -H "X-AUTHENTICATED-USER:user1" -H "Content-Type:application/json" http://localhost:8080/meetings/read
````

#### Get meeting with specific title
````
curl -i -H "Content-Type:application/json" -H "X-AUTHENTICATED-USER:user1" http://localhost:8080/meetings/read/{meetingId}
````

#### Get meeting with specific state
Gets all meetings with the selected state. The state is boolean true means closed.
````
curl -i -H "X-AUTHENTICATED-USER:user1" -H "Content-Type:application/json" http://localhost:8080/meetings/readwithstate/{closed}
````


### Update existing meeting
````
curl -i -X POST -H "X-AUTHENTICATED-USER:user1" -H "Content-Type:application/json" -d  "{\"title\":\"1on1\",\"participants\":[{\"username\":\"user1\",\"name\":\"people\"},{\"username\":\"user2\",\"name\":\"people3\"}],\"plannedDate\":1630945253843,\"description\":\"meeting\",\"location\":\"teams\"}" http://localhost:8080/meetings/update
````


### Delete meeting
````
curl -i -X DELETE -H "X-AUTHENTICATED-USER:user1" -H "Content-Type:application/json" http://localhost:8080/meetings/delete/{meetingId}
````


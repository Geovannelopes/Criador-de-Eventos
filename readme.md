# AC2 - POO

# Members:
- Bruno Alexandre | 190567
- Geovanne Bueno Lopes | 190803

### Heroku:
| https://ac1-lab-2021.herokuapp.com/ |

### =-=-=-=-=-=-=-=-=-=-=-=-= Admin [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- GET: /admins
- DELETE: /admins/{id}


- POST: /admins
```sh
{
    "name": "admin",
    "email": "admin@email.com",
    "phoneNumber": "(15) 0000-0000"
}
```

- PUT: /admins/{id}
```sh
{
    "name": "adminUpdate",
    "phoneNumber": "(15) 1234-1234"
}
```

### =-=-=-=-=-=-=-=-=-=-=-=-= Attend [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- GET: /attendees
- DELETE: /attendees/{id}


- POST: /attendees
```sh
{
    "name": "attend",
    "email": "attend@email.com"
}
```

- PUT: /attendees/{id}
```sh
{
    "name": "attendUpdate"
}
```

### =-=-=-=-=-=-=-=-=-=-=-=-= Event [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- GET: /events
- DELETE: /events/{id}


- POST: /events
```sh
{
    "name": "event",
    "description": "eventDescription",
    "startDate": "2021-03-03",
    "endDate": "2021-04-04",
    "startTime": "03:03:03",
    "endTime": "04:04:04",
    "emailContact": "event@email.com",
    "amountFreeTickets": 5,
    "amountPayedTickets": 10,
    "priceTicket": 15,
    "admin": 1
}
```

- PUT: /events/{id}
```sh
{
    "name": "eventUpdate",
    "description": "eventDescriptionUpdate",
    "startDate": "2022-03-03",
    "endDate": "2022-04-04",
    "startTime": "10:03:03",
    "endTime": "11:04:04",
    "emailContact": "event@email.comUpdate",
    "amountFreeTickets": 6,
    "amountPayedTickets": 11,
    "priceTicket": 20
}
```

### =-=-=-=-=-=-=-=-=-=-=-=-= Place [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- GET: /places
- DELETE: /places/{id}


- POST: /events
```sh
{
    "name": "place",
    "address": "streetPlace"
}
```

- PUT: /events/{id}
```sh
{
    "name": "place"
}
```

### =-=-=-=-=-=-=-=-=-=-=-=-= Event(Place) [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- POST: /events/{id}/places/{id}
- DELETE: /events/{id}/places/{id}

### =-=-=-=-=-=-=-=-=-=-=-=-= Event(Ticket) [ JSON ] =-=-=-=-=-=-=-=-=-=-=-=-=
- GET: /events/{id}/tickets
- DELETE: /events/{id}/tickets

- POST: /events/{id}/tickets -> (id = AttendID) -> (type = "Free" or "Payed")
```sh
{
    "id": 5,
    "type": "Payed"
}
```
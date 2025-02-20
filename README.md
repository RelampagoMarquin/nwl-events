# nwl-events
meu projeto Java da nwl-events

rotas


GET: 
```bash
localhost:8080/events/{prettyName} #busca pelo nome
```
GET: 
```bash
localhost:8080/events #busca todos os eventos
```
POST:
```bash
localhost:8080/events #add um no evento

# json de exemplo
{
  "title": "CodeCraft Summit 2027",
  "location": "Online",
  "price": 0.0,
  "startDate": "2027-03-16",
  "endDate": "2027-03-18",
  "startTime": "19:00:00",
  "endTime": "21:00:00"
}
```
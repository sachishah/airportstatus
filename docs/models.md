FAA
===
Docs
---
http://services.faa.gov/docs/services/airport/

Sample JSON URL
---
http://services.faa.gov/airport/status/SFO?format=application/json

Parameters
---
airportcode - follows the slash after "status/"
format - secondary GET parameter

Response
---
`
{
    "IATA": "SFO", 
    "ICAO": "KSFO", 
    "city": "San Francisco", 
    "delay": "true", 
    "name": "San Francisco International", 
    "state": "California", 
    "status": {
        "avgDelay": "1 hour and 28 minutes", 
        "closureBegin": "", 
        "closureEnd": "", 
        "endTime": "", 
        "maxDelay": "", 
        "minDelay": "", 
        "reason": "WEATHER / LOW CEILINGS", 
        "trend": "", 
        "type": "Ground Delay"
    }, 
    "weather": {
        "meta": {
            "credit": "NOAA's National Weather Service", 
            "updated": "9:56 AM Local", 
            "url": "http://weather.gov/"
        }, 
        "temp": "59.0 F (15.0 C)", 
        "visibility": 10.0, 
        "weather": "Overcast", 
        "wind": "West at 11.5mph"
    }
}
`

AirportStatusModel
---
avgDelay = status.avgDelay
weatherTemp = weather.temp
weatherWeather = weather.weather
iata = IATA
delay = delay (boolean)
delayType = status.type
closureBegin = status.closureBegin
closureEnd = status.closureEnd
endTime = status.endTime
maxDelay = status.maxDelay
minDelay = status.minDelay 


TSA
===


Google Directions
=================
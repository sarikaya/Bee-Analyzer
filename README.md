# Bee-Analyzer

Bee Analyzer is about developing GPS tracking mobile phone application and analyzing pedestrian movement of the Istanbul Technical University Ayazaga Campus community. The application is used for keeping trace of the campus members. According to GPS tracks of volunteers a set of queries executed than there was meaningful results came to light.

## Requirements

- Gradle 1.3: http://www.gradle.org/downloads
- Android SDK v22: http://developer.android.com/sdk/index.html
- Gradle Wrapper

## Dependencies

- Google Services
- Google Maps API
- Firebase Messaging & Notification Services

## Gradle Tasks with Wrapper

### Resolve Dependencies

	gradlew androidDependencies

### Build Task

	gradlew build

### Run Tests

	gradlew test

### Install

	gradlew installDebug
  
## Server-Side

- JSON Web Service API (ASP.NET)
- MSSQL Server

### API Documentation

| Title                 | Insert GPS Data |
|-----------------------|-----------------|
| **URL**               | /Handler.aspx |
| **Method**            | POST   |
| **Data Params**       | Content=<br>{<br>&nbsp;&nbsp;&nbsp;"interface":"RestAPI",<br>&nbsp;&nbsp;&nbsp;"method":"InsertGPSdatas",<br>&nbsp;&nbsp;&nbsp;"parameters":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"05-08-2017 19:58:37",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0030576",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.0805082",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"out":"0",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imei":"#imei"<br>&nbsp;&nbsp;&nbsp;}<br>} |
| **Success Response**  | **Status:** 200 OK |
| **Error Response**    | **Status:** 406 Not Acceptable <br>or<br>**Status:** 500 Internal Server Error |

<br>

| Title                 | Get Instant Marks on the Map |
|-----------------------|-----------------|
| **URL**               | /Handler.aspx |
| **Method**            | POST   |
| **Data Params**       | Content=<br>{<br>&nbsp;&nbsp;&nbsp;"interface":"RestAPI",<br>&nbsp;&nbsp;&nbsp;"method":"getMarks"<br>} |
| **Success Response**  | **Status:** 200 OK <br>**Content:** <br>{<br>&nbsp;&nbsp;&nbsp;"#imei":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imei":"#imei",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.0805082",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0030576",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"05-08-2017 19:58:37"<br>&nbsp;&nbsp;&nbsp;}<br>} |
| **Error Response**    | **Status:** 406 Not Acceptable <br>or<br>**Status:** 500 Internal Server Error |

<br>

| Title                 | Get Routes on the Map by IMEI |
|-----------------------|-----------------|
| **URL**               | /Handler.aspx |
| **Method**            | POST   |
| **Data Params**       | Content=<br>{<br>&nbsp;&nbsp;&nbsp;"interface":"RestAPI",<br>&nbsp;&nbsp;&nbsp;"method":"getOwnRoutes",<br>&nbsp;&nbsp;&nbsp;"parameters":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"imei":"#imei",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"mindate":"18-05-2017 13:00:00",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"maxdate":"18-05-2017 14:00:00"<br>&nbsp;&nbsp;&nbsp;}<br>} |
| **Success Response**  | **Status:** 200 OK <br>**Content:** <br>{<br>&nbsp;&nbsp;&nbsp;"#id":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.1044601",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0192417",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"18-05-2017 13:00:45"<br>&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;"#id":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.1034601",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0152417",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"18-05-2017 13:01:20"<br>&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;...<br>} |
| **Error Response**    | **Status:** 406 Not Acceptable <br>or<br>**Status:** 500 Internal Server Error |

<br>

| Title                 | Get All Routes |
|-----------------------|-----------------|
| **URL**               | /Handler.aspx |
| **Method**            | POST   |
| **Data Params**       | Content=<br>{<br>&nbsp;&nbsp;&nbsp;"interface":"RestAPI",<br>&nbsp;&nbsp;&nbsp;"method":"getRoutes"<br>} |
| **Success Response**  | **Status:** 200 OK <br>**Content:** <br>{<br>&nbsp;&nbsp;&nbsp;"#imei":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"#id":<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.0805082",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0030576",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"05-08-2017 19:58:37",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"out":"0"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"#id":<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"41.0305082",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.0630576",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"05-08-2017 20:00:37",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"out":"1"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;},<br>&nbsp;&nbsp;&nbsp;"#imei":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"#id":<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"latitude":"40.0805082",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"longitude":"29.7030576",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"timestamp":"05-08-2017 18:58:37",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"out":"0"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...<br>&nbsp;&nbsp;&nbsp;}<br>&nbsp;&nbsp;&nbsp;...<br>} |
| **Error Response**    | **Status:** 406 Not Acceptable <br>or<br>**Status:** 500 Internal Server Error |

<br>

| Title                 | Admin Login |
|-----------------------|-----------------|
| **URL**               | /Handler.aspx |
| **Method**            | POST   |
| **Data Params**       | Content=<br>{<br>&nbsp;&nbsp;&nbsp;"interface":"RestAPI",<br>&nbsp;&nbsp;&nbsp;"method":"login",<br>&nbsp;&nbsp;&nbsp;"parameters":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"username":"#username",<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"password":"#password"<br>&nbsp;&nbsp;&nbsp;}<br>} |
| **Success Response**  | **Status:** 200 OK <br>**Content:** <br>{<br>&nbsp;&nbsp;&nbsp;"status":"successful"<br>}|
| **Error Response**    | **Status:** 200 OK <br>**Content:** <br>{<br>&nbsp;&nbsp;&nbsp;"status":"failed"<br>}<br>or<br>**Status:** 406 Not Acceptable <br>or<br>**Status:** 500 Internal Server Error |

<br>

| Title                 | Send Notification |
|-----------------------|-----------------|
| **URL**               | https://fcm.googleapis.com/fcm/send |
| **Method**            | POST   |
| **Data Params**       | {<br>&nbsp;&nbsp;&nbsp;"to":"/topics/news",<br>&nbsp;&nbsp;&nbsp;"data":<br>&nbsp;&nbsp;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"text":"message"<br>&nbsp;&nbsp;&nbsp;}<br>} |
| **Success Response**  | **Status:** 200 OK <br>|
| **Error Response**    | **Status:** 400 <br>or<br>**Status:** 401 <br>or<br> **Status:** 5xx|

## License

Copyright (C) 2017 Nurefsan Sarikaya. It is distributed under the MIT license - see the accompanying LICENSE file for more details.

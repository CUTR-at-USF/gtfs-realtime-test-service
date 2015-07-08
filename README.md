#  gtfs-realtime-test-service

```gtfs-realtime-test-service``` is a web service that provides gtfs-rt data for testing. 
This web service stores gtfs-rt data from some source and serves always the same gtfs-rt data.


###Methods

- Trip updates endpoint: ```ip:8080/cutr/trip-updates.do```
- Vehicle position updates endpoint: ```ip:8080/cutr/vehicle-positions.do```
- Update gtfs-rt data from upstream provider: ``` ip:8080/cutr/update-files.do```


# timeseriesserver
This is Akka TCP server, exposes a TCP port to accept input string data in following format: <br/> <b>msg value epoc_time</b>

The application supports HTTP REST and TCP based protocl to store time based information into cassandra database as defualt storage engine.<br/>

The application provides graphical output for set of metric between two time frames. 
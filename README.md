# timeseriesserver
This is Akka TCP server, exposes a TCP port to accept input string data in following format: <br/> <b>msg value epoc_time</b>

The Akka TCP server stores this information into cassandra database. This application also exposes HTTP port for various REST services to query stored time series data in json or HTML format (grahical).

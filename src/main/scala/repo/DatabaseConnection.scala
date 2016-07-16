package repo

import akka.actor.ActorSystem
import com.datastax.driver.core.{HostDistance, PoolingOptions, QueryOptions, Cluster}
import scala.collection.JavaConversions._


/**
  * This is database connection
  * Created by Arun Sethia on 16/07/16.
  */
trait DatabaseConnection {
  val cassandraCluster:Cluster
}


trait CassandraDatabaseConnection extends DatabaseConnection{
  system:ActorSystem=>
  val cassandraCluster = {
    val cassandraConfig = system.settings.config.getConfig("cassandra")
    val port = cassandraConfig.getInt("port")
    val hosts = cassandraConfig.getStringList("hosts").toList
    Cluster.builder()
      .addContactPoints(hosts: _*)
      .withPort(port).withCredentials(cassandraConfig.getString("user"), cassandraConfig.getString("password"))
      .withQueryOptions(new QueryOptions().setConsistencyLevel(QueryOptions.DEFAULT_CONSISTENCY_LEVEL))
      .withPoolingOptions(new PoolingOptions()
        .setConnectionsPerHost(HostDistance.LOCAL,
          cassandraConfig.getInt("pool.min-connection"), cassandraConfig.getInt("pool.max-connection"))
        .setConnectionsPerHost(HostDistance.REMOTE,
          cassandraConfig.getInt("pool.min-connection"), cassandraConfig.getInt("pool.max-connection")))
      .build()
  }
}

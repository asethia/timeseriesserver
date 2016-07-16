package repo

import com.datastax.driver.core._
import com.typesafe.config.Config

import scala.collection.JavaConversions._


/**
  * This is cassandra database connection
  * Created by Arun Sethia on 16/07/16.
  */

trait CassandraDatabaseConnection  {
    config: Config =>
    val cassandraCluster = {
      val cassandraConfig = config.getConfig("cassandra")
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

package com.seadowg.walkerman.test.support

import com.seadowg.walkerman.main
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.DBI
import spark.SparkBase

fun clearDatabase() {
    val dataSource = PGPoolingDataSource()
    dataSource.url = "jdbc:postgresql://localhost:5432/walkerman"
    dataSource.user = "postgres"
    dataSource.password = "mysecretpassword"

    DBI(dataSource).open().use { db ->
        db.createStatement("delete from rsvps").execute()
    }
}

fun bootWalkermanApp() {
    main(arrayOf<String>())
}

fun shutdownWalkermanApp() {
    SparkBase.stop()
}

package com.seadowg.walkerman.test.support

import com.seadowg.walkerman.application.main
import com.seadowg.walkerman.test.page.NewRsvpPage
import org.postgresql.ds.PGPoolingDataSource
import org.skife.jdbi.v2.DBI
import spark.SparkBase

public fun clearDatabase() {
    val dataSource = PGPoolingDataSource()
    dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")

    DBI(dataSource).open().use { db ->
        db.createStatement("delete from rsvps").execute()
    }
}

public fun bootWalkermanApp() {
    main(array<String>())
}

public fun shutdownWalkermanApp() {
    SparkBase.stop();
}

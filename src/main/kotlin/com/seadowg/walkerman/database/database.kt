package com.seadowg.walkerman.database

import org.postgresql.ds.PGPoolingDataSource

val dataSource = PGPoolingDataSource()

fun configure(): Unit {
    dataSource.setUrl("jdbc:postgresql://localhost:5432/walkerman")
}


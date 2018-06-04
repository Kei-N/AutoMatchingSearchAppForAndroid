package com.example.keinomoto.automatchingsearchapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteOpenHelper(c: Context) : SQLiteOpenHelper(c, DB, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    companion object {

        internal val DB = "sqlite_sample.db"
        internal val DB_VERSION = 1
        internal val CREATE_TABLE = "CREATE TABLE keywords_tbl ( " +
                "_id integer PRIMARY KEY AUTOINCREMENT, " +
                "keyword VARCHAR(255) NOT NULL );"
        internal val DROP_TABLE = "DROP TABLE keywords_tbl ;"
    }
}

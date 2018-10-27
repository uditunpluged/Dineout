package com.phantasmist.dineout.Cache.db

object ProjectConstants {
    const val TABLE_NAME = "food_outlets"

    const val COLUMN_PROJECT_ID = "outlet_id"

    const val COLUMN_IS_DISLIKED = "is_disliked"

    const val QUERY_PROJECTS = "SELECT * FROM $TABLE_NAME"

    const val DELETE_PROJECTS = "DELETE FROM $TABLE_NAME"

    const val QUERY_DISLIKED_PROJECTS = "SELECT * FROM $TABLE_NAME " +
            "WHERE $COLUMN_IS_DISLIKED = 1"

    const val QUERY_UPDATE_DISLIKED_STATUS = "UPDATE $TABLE_NAME " +
            "SET $COLUMN_IS_DISLIKED = :is_disliked WHERE " +
            "$COLUMN_PROJECT_ID = :outlet_id"
}
package ru.dreamteam.travelreminder.presentation

enum class RealtimeDatabaseError(val httpStatus: Int, val errorMessage: String) {
    PERMISSION_DENIED(401, "Permission denied"),
    FORBIDDEN(403, "Permission denied"),
    NOT_FOUND(404, "Not found"),
    BAD_REQUEST(400, "Bad Request"),
    DEFAULT_ERROR(-1, "Default error");

    companion object {
        fun fromStatus(status: Int): RealtimeDatabaseError? =
            entries.firstOrNull { it.httpStatus == status }
    }
}
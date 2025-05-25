package ru.dreamteam.travelreminder.presentation.add_travel

enum class AddTravelFieldsValidationErrors(name :String) {
    EMPTY_NAME("EMPTY_NAME"),
    EMPTY_TIME("EMPTY_TIME"),
    EMPTY_DATE("EMPTY_DATE"),
    EMPTY_ROUTE("EMPTY_ROUTE"),
    EMPTY_TIME_BEFORE_REMIND("EMPTY_TIME_BEFORE_REMIND"),
    INVALID_TRAVEL_TIME("INVALID_TRAVEL_TIME")
}


package ru.dreamteam.travelreminder.data.remoute.model

import ru.dreamteam.travelreminder.data.remoute.model.error_response.ErrorResponse

class FirebaseAuthException(val error: ErrorResponse) : Exception(error.error.message)

package ru.dreamteam.travelreminder.presentation

import io.ktor.client.plugins.ClientRequestException
import kotlinx.io.IOException
import org.jetbrains.compose.resources.StringResource
import org.koin.core.qualifier.qualifier
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.data.remoute.model.FirebaseAuthException
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelFieldsValidationErrors
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.auth_error_default_error
import travelreminder.composeapp.generated.resources.auth_error_email_exists
import travelreminder.composeapp.generated.resources.auth_error_invalid_email
import travelreminder.composeapp.generated.resources.auth_error_invalid_login_credentials
import travelreminder.composeapp.generated.resources.auth_error_missing_password
import travelreminder.composeapp.generated.resources.auth_error_operation_not_allowed
import travelreminder.composeapp.generated.resources.auth_error_too_many_attempts_try_later
import travelreminder.composeapp.generated.resources.auth_error_user_disabled
import travelreminder.composeapp.generated.resources.auth_error_user_not_found
import travelreminder.composeapp.generated.resources.auth_error_weak_password
import travelreminder.composeapp.generated.resources.empty_date
import travelreminder.composeapp.generated.resources.empty_name
import travelreminder.composeapp.generated.resources.empty_route
import travelreminder.composeapp.generated.resources.empty_time
import travelreminder.composeapp.generated.resources.empty_time_before_remind
import travelreminder.composeapp.generated.resources.invalid_travel_time

class DefaultErrorMapper : ErrorMapper {
    override fun map(throwable: Throwable): CaughtError =
        when (throwable) {
            is FirebaseAuthException -> mapFirebaseAuthException(throwable)
            is ClientRequestException -> CaughtErrorImpl.InnerError(null, null)
            is IOException -> CaughtErrorImpl.InnerError(null, null)
            else -> CaughtErrorImpl.InnerError(null, null)
        }

    override fun <T : Enum<T>> map(error: Enum<T>): CaughtError {
        when (error) {
            is AddTravelFieldsValidationErrors ->
                return when (error.name) {
                    "EMPTY_NAME" -> CaughtErrorImpl.ValidationError(Res.string.empty_name, error.ordinal)
                    "EMPTY_TIME" -> CaughtErrorImpl.ValidationError(Res.string.empty_time, error.ordinal)
                    "EMPTY_DATE" -> CaughtErrorImpl.ValidationError(Res.string.empty_date, error.ordinal)
                    "EMPTY_ROUTE" -> CaughtErrorImpl.ValidationError(Res.string.empty_route, error.ordinal)
                    "EMPTY_TIME_BEFORE_REMIND" -> CaughtErrorImpl.ValidationError(Res.string.empty_time_before_remind, error.ordinal)
                    "INVALID_TRAVEL_TIME" -> CaughtErrorImpl.ValidationError(Res.string.invalid_travel_time, error.ordinal)
                    else -> CaughtErrorImpl.ValidationError(Res.string.auth_error_default_error, error.ordinal)
                }

        }
        return CaughtErrorImpl.ValidationError(Res.string.auth_error_default_error, error.ordinal)
    }

    private fun mapFirebaseAuthException(throwable: Throwable): CaughtError {
        return when (throwable.message) {
            "INVALID_EMAIL" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_invalid_email)
            "INVALID_LOGIN_CREDENTIALS" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_invalid_login_credentials)
            "MISSING_PASSWORD" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_missing_password)
            "USER_DISABLED" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_user_disabled)
            "WEAK_PASSWORD" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_weak_password)
            "EMAIL_EXISTS" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_email_exists)
            "OPERATION_NOT_ALLOWED" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_operation_not_allowed)
            "TOO_MANY_ATTEMPTS_TRY_LATER" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_too_many_attempts_try_later)
            "USER_NOT_FOUND" -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_user_not_found)

            else -> CaughtErrorImpl.ErrorForUser(Res.string.auth_error_default_error)
        }
    }

}


sealed class CaughtErrorImpl : CaughtError {
    data class ErrorForUser(val resId: StringResource) : CaughtErrorImpl()
    data class ValidationError(val resId: StringResource, val ordinal: Int) : CaughtErrorImpl()
    data class InnerError(val message: String?, val throwable: Throwable?) : CaughtErrorImpl()
}
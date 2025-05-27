package ru.dreamteam.travelreminder.presentation

import io.ktor.client.plugins.ClientRequestException
import kotlinx.io.IOException
import org.jetbrains.compose.resources.StringResource
import ru.dreamteam.travelreminder.common.CaughtError
import ru.dreamteam.travelreminder.common.ErrorMapper
import ru.dreamteam.travelreminder.data.remoute.model.FirebaseAuthException
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelFieldsValidationErrors
import ru.dreamteam.travelreminder.presentation.sing_in.SignInFieldsValidationErrors
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpFieldsValidationErrors
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.auth_error_default_error
import travelreminder.composeapp.generated.resources.auth_error_email_exists
import travelreminder.composeapp.generated.resources.auth_error_invalid_characters
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
import travelreminder.composeapp.generated.resources.passwords_do_not_match

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
                return when (error) {
                    AddTravelFieldsValidationErrors.EMPTY_NAME -> CaughtErrorImpl.SingleError(Res.string.empty_name)
                    AddTravelFieldsValidationErrors.EMPTY_TIME -> CaughtErrorImpl.SingleError(Res.string.empty_time)
                    AddTravelFieldsValidationErrors.EMPTY_DATE -> CaughtErrorImpl.SingleError(Res.string.empty_date)
                    AddTravelFieldsValidationErrors.NO_ROUTE -> CaughtErrorImpl.SingleError(Res.string.empty_route)
                    AddTravelFieldsValidationErrors.EMPTY_TIME_BEFORE_REMIND -> CaughtErrorImpl.SingleError(Res.string.empty_time_before_remind)
                    AddTravelFieldsValidationErrors.INVALID_TRAVEL_TIME -> CaughtErrorImpl.SingleError(Res.string.invalid_travel_time)
                }
            is SignUpFieldsValidationErrors ->
                return when (error){
                    SignUpFieldsValidationErrors.PASSWORDS_DO_NOT_MATCH -> CaughtErrorImpl.CommonError(Res.string.passwords_do_not_match)
                }
        }
        return CaughtErrorImpl.SingleError(Res.string.auth_error_default_error)
    }

    private fun mapFirebaseAuthException(throwable: Throwable): CaughtError {
        return when (throwable.message) {
            "INVALID_EMAIL" -> CaughtErrorImpl.CommonError(Res.string.auth_error_invalid_email)
            "INVALID_LOGIN_CREDENTIALS" -> CaughtErrorImpl.CommonError(Res.string.auth_error_invalid_login_credentials)
            "MISSING_PASSWORD" -> CaughtErrorImpl.CommonError(Res.string.auth_error_missing_password)
            "USER_DISABLED" -> CaughtErrorImpl.CommonError(Res.string.auth_error_user_disabled)
            "WEAK_PASSWORD" -> CaughtErrorImpl.CommonError(Res.string.auth_error_weak_password)
            "EMAIL_EXISTS" -> CaughtErrorImpl.CommonError(Res.string.auth_error_email_exists)
            "OPERATION_NOT_ALLOWED" -> CaughtErrorImpl.CommonError(Res.string.auth_error_operation_not_allowed)
            "TOO_MANY_ATTEMPTS_TRY_LATER" -> CaughtErrorImpl.CommonError(Res.string.auth_error_too_many_attempts_try_later)
            "USER_NOT_FOUND" -> CaughtErrorImpl.CommonError(Res.string.auth_error_user_not_found)

            else -> CaughtErrorImpl.CommonError(Res.string.auth_error_default_error)
        }
    }

}


sealed class CaughtErrorImpl : CaughtError {
    data class CommonError(val resId: StringResource) : CaughtErrorImpl()
    data class SingleError(val resId: StringResource) : CaughtErrorImpl()
    data class InnerError(val message: String?, val throwable: Throwable?) : CaughtErrorImpl()
}
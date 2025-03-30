package ru.dreamteam.travelreminder.data.mapper

import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailRequestParam
import ru.dreamteam.travelreminder.data.remoute.model.ChangePasswordByEmailResponseDto
import ru.dreamteam.travelreminder.data.remoute.model.SignInByEmailAndPasswordRequestParams
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailParam
import ru.dreamteam.travelreminder.domen.model.ChangePasswordByEmailResponse

fun ChangePasswordByEmailResponseDto.toDomain() = ChangePasswordByEmailResponse(
    email = email
)

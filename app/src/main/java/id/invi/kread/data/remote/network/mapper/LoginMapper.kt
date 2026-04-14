package id.invi.kread.data.remote.network.mapper

import id.invi.kread.data.remote.network.response.LoginResponse
import id.invi.kread.domain.model.User

fun LoginResponse?.toDomain(): User {
    return User(
        email = this?.user?.email.orEmpty(),
    )
}
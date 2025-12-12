package com.clarxlabs.lillia.core.repositories.types

import com.clarxlabs.lillia.core.dtos.UserTokens
import com.clarxlabs.lillia.core.entities.User

typealias UserInfoInput = UserTokens
typealias UserInfoError = UserTokens.Error
typealias UserInfoOutput = User

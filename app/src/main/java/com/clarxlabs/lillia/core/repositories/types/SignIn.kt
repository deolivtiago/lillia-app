package com.clarxlabs.lillia.core.repositories.types

import com.clarxlabs.lillia.core.dtos.UserCredentials
import com.clarxlabs.lillia.core.dtos.UserTokens

typealias SignInInput = UserCredentials
typealias SignInError = UserCredentials.Error
typealias SignInOutput = UserTokens

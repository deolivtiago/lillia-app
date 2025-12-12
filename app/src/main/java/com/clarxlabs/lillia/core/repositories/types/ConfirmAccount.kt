package com.clarxlabs.lillia.core.repositories.types

import com.clarxlabs.lillia.core.dtos.UserConfirmation
import com.clarxlabs.lillia.core.entities.User

typealias ConfirmAccountInput = UserConfirmation
typealias ConfirmAccountError = UserConfirmation.Error
typealias ConfirmAccountOutput = User

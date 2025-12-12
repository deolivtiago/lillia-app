package com.clarxlabs.lillia.core.repositories.types

import com.clarxlabs.lillia.core.dtos.UserData
import com.clarxlabs.lillia.core.entities.User

typealias SignUpInput = UserData
typealias SignUpError = UserData.Error
typealias SignUpOutput = User

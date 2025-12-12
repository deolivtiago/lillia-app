package com.clarxlabs.lillia.core.datasources

import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountError
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountInput
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountOutput
import com.clarxlabs.lillia.core.repositories.types.ListUsersError
import com.clarxlabs.lillia.core.repositories.types.ListUsersOutput
import com.clarxlabs.lillia.core.repositories.types.SendCodeError
import com.clarxlabs.lillia.core.repositories.types.SendCodeInput
import com.clarxlabs.lillia.core.repositories.types.SendCodeOutput
import com.clarxlabs.lillia.core.repositories.types.SignInError
import com.clarxlabs.lillia.core.repositories.types.SignInInput
import com.clarxlabs.lillia.core.repositories.types.SignInOutput
import com.clarxlabs.lillia.core.repositories.types.SignOutError
import com.clarxlabs.lillia.core.repositories.types.SignOutInput
import com.clarxlabs.lillia.core.repositories.types.SignOutOutput
import com.clarxlabs.lillia.core.repositories.types.SignUpError
import com.clarxlabs.lillia.core.repositories.types.SignUpInput
import com.clarxlabs.lillia.core.repositories.types.SignUpOutput
import com.clarxlabs.lillia.core.repositories.types.UserInfoError
import com.clarxlabs.lillia.core.repositories.types.UserInfoInput
import com.clarxlabs.lillia.core.repositories.types.UserInfoOutput
import it.czerwinski.kotlin.util.Either

interface AuthenticationDataSource {
    suspend fun signIn(input: SignInInput): Either<SignInError, SignInOutput>
    suspend fun signUp(input: SignUpInput): Either<SignUpError, SignUpOutput>
    suspend fun signOut(input: SignOutInput): Either<SignOutError, SignOutOutput>
    suspend fun sendCode(input: SendCodeInput): Either<SendCodeError, SendCodeOutput>
    suspend fun confirmAccount(input: ConfirmAccountInput): Either<ConfirmAccountError, ConfirmAccountOutput>
    suspend fun userInfo(input: UserInfoInput): Either<UserInfoError, UserInfoOutput>
    suspend fun listUsers(): Either<ListUsersError, ListUsersOutput>
}

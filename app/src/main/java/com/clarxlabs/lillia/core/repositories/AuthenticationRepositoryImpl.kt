package com.clarxlabs.lillia.core.repositories

import com.clarxlabs.lillia.core.datasources.AuthenticationDataSource
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountError
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountInput
import com.clarxlabs.lillia.core.repositories.types.ConfirmAccountOutput
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
import com.clarxlabs.lillia.core.services.validation.LengthValidation
import com.clarxlabs.lillia.core.services.validation.LowerCaseValidation
import com.clarxlabs.lillia.core.services.validation.NumbersValidation
import com.clarxlabs.lillia.core.services.validation.SymbolsValidation
import com.clarxlabs.lillia.core.services.validation.TextValidation
import com.clarxlabs.lillia.core.services.validation.UpperCaseValidation
import it.czerwinski.kotlin.util.Either

class AuthenticationRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {
    override suspend fun signIn(input: SignInInput): Either<SignInError, SignInOutput> {
        return authenticationDataSource.signIn(input)
    }

    override suspend fun signUp(input: SignUpInput): Either<SignUpError, SignUpOutput> {
        return authenticationDataSource.signUp(input)
    }

    override suspend fun signOut(input: SignOutInput): Either<SignOutError, SignOutOutput> {
        return authenticationDataSource.signOut(input)
    }

    override suspend fun verify(input: SendCodeInput): Either<SendCodeError, SendCodeOutput> {
        return authenticationDataSource.sendCode(input)
    }

    override suspend fun confirm(input: ConfirmAccountInput): Either<ConfirmAccountError, ConfirmAccountOutput> {
        return authenticationDataSource.confirmAccount(input)
    }

    override suspend fun userInfo(input: UserInfoInput): Either<UserInfoError, UserInfoOutput> {
        return authenticationDataSource.userInfo(input)
    }
}

object AppMainAPI {
    const val BASE_URL = "lillia.gigalixirapp.com"

    data object SignIn {
        val path = setOf("", "api", "auth", "signin").joinToString("/")
    }

    data object SignUp {
        val path = setOf("", "api", "auth", "signup").joinToString("/")
    }

    data object SignOut {
        val path = setOf("", "api", "auth", "signout").joinToString("/")
    }

    data object Verify {
        val path = setOf("", "api", "auth", "verify").joinToString("/")
    }

    data object Confirm {
        val path = setOf("", "api", "auth", "confirm").joinToString("/")
    }

    data object Me {
        val path = setOf("", "api", "auth", "me").joinToString("/")
    }

    data class Users(val userId: String = "") {
        val path = setOf("", "api", "user", userId).joinToString("/")
    }


    fun mapErrors(it: SignInError) {
        val emailErrors = it.email.map {
            val mapping: Map<Regex, TextValidation.Error> = mapOf(
                Regex("^can't be blank").to(LengthValidation.Error.Required),
                Regex("^should be at most \\d+ character").to(LengthValidation.Error.TooLong(255)),
                Regex("^should be at least \\d+ character").to(LengthValidation.Error.TooShort(2)),

                Regex("^must have number").to(NumbersValidation.Error.AtLeast(1)),
                Regex("^must have lowercase character").to(LowerCaseValidation.Error.AtLeast(1)),
                Regex("^must have uppercase character").to(UpperCaseValidation.Error.AtLeast(1)),
                Regex("^must have special character").to(SymbolsValidation.Error.AtLeast(1)),

                )

        }
    }

    enum class EmailError {
        REQUIRED,
        ALREADY_TAKEN,
        NOT_FOUND,
        INVALID,
        MUST_HAVE,
        TOO_SHORT,
        UNKNOWN;

        fun fromMessage(message: String): EmailError {
            val mappings: Map<Regex, EmailError> = mapOf(

                Regex("^(can't be blank)").to(REQUIRED),

                // From Ecto.Changeset.unique_constraint/3
                Regex("^(has already been taken)").to(ALREADY_TAKEN),

                // From Ecto.Changeset.foreign_key_constraint/3
                Regex("^(does not exist)").to(NOT_FOUND),

                // From Ecto.Changeset.validate_format/3
                Regex("^((is|has) (an )?(reserved|invalid( format| entry)?))").to(INVALID),

                Regex("^(must have number)").to(MUST_HAVE),

                Regex("^(must have lowercase character)").to(MUST_HAVE),

                Regex("^(must have uppercase character)").to(MUST_HAVE),

                Regex("^(must have special character)").to(MUST_HAVE),


                // From Ecto.Changeset.validate_confirmation/3
                Regex("^(does not match confirmation)").to(INVALID),

                // From Ecto.Changeset.no_assoc_constraint/3
                Regex("^((is|are) still associated with this entry)").to(UNKNOWN),


                // From Ecto.Changeset.validate_length/3
                Regex("^(should (have|be)( at (least|most))? \\d+ (item|byte|character))").to(
                    TOO_SHORT
                ),
                Regex("^(should (have|be) at most \\d+ (item|byte|character))").to(
                    TOO_SHORT
                ),
                Regex("^(should have \\d+ item)").to(UNKNOWN),

                Regex("^(should be \\d+ character)").to(UNKNOWN),
                Regex("^(should be \\d+ character)").to(UNKNOWN),

                Regex("^(should be \\d+ byte)").to(UNKNOWN),
                Regex("^(should be \\d+ byte)").to(UNKNOWN),

                Regex("^(should have at least \\d+ item)").to(UNKNOWN),
                Regex("^(should have at least \\d+ item)").to(UNKNOWN),

                Regex("^(should be at least \\d+ character)").to(UNKNOWN),
                Regex("^(should be at least \\d+ character)").to(UNKNOWN),

                Regex("^(should be at least \\d+ byte)").to(UNKNOWN),
                Regex("^(should be at least \\d+ byte)").to(UNKNOWN),

                Regex("^(should have at most \\d+ item)").to(UNKNOWN),
                Regex("^(should have at most \\d+ item)").to(UNKNOWN),

                Regex("^(should be at most \\d+ character)").to(UNKNOWN),
                Regex("^(should be at most \\d+ character)").to(UNKNOWN),

                Regex("^(should be at most \\d+ byte)").to(UNKNOWN),
                Regex("^(should be at most \\d+ byte)").to(UNKNOWN),

                // From Ecto.Changeset.validate_number/3
                Regex("^(must be less than \\d+)").to(UNKNOWN),

                Regex("^(must be greater than \\d+)").to(UNKNOWN),

                Regex("^(must be less than or equal to \\d+)").to(UNKNOWN),

                Regex("^(must be greater than or equal to \\d+)").to(UNKNOWN),

                Regex("^(must be equal to \\d+)").to(UNKNOWN),

                )

            return mappings
                .filter { it.key.matches(message) }
                .map { it.value }
                .firstOrNull()
                ?: UNKNOWN
        }
    }
}

package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either

sealed interface TextValidation {
    fun validate(text: String): Either<Error, String>

    sealed interface Error
}

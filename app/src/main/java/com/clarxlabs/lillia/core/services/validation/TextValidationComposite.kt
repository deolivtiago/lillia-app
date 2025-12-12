package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Right


class TextValidationComposite(val validators: List<TextValidation> = emptyList()) : TextValidation {
    override fun validate(text: String): Either<TextValidation.Error, String> =
        validators
            .map { it.validate(text) }
            .firstOrNull { it.isLeft }
            ?: Right(text)
}

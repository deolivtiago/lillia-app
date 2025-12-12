package com.clarxlabs.lillia.core.services.validation

import it.czerwinski.kotlin.util.Either
import it.czerwinski.kotlin.util.Left
import it.czerwinski.kotlin.util.Right

class TextFieldValidation(val strategy: Strategy) : TextValidation {
    class Error(val error: TextValidation.Error) : TextValidation.Error

    enum class Strategy(val validator: TextValidation) {
        REQUIRED(TextValidationComposite(listOf(LengthValidation(0, isRequired = true)))),
        EMAIL(
            TextValidationComposite(listOf(LengthValidation(isRequired = true), EmailValidation()))
        ),
        PASSWORD(
            TextValidationComposite(
                listOf(
                    LengthValidation(max = 72, isRequired = true),
                    NumbersValidation(),
                    LowerCaseValidation(),
                    UpperCaseValidation(),
                    SymbolsValidation(),
                ),
            )
        ),
        FULL_NAME(TextValidationComposite(listOf(LengthValidation(2, isRequired = true)))),
    }

    override fun validate(text: String): Either<Error, String> {
        return strategy.validator
            .validate(text)
            .fold({ Error(it).let(::Left) }, ::Right)
    }

    companion object {
        fun getErrorMessage(it: Either<TextValidation.Error, String>): String =
            it.fold(::getErrorMessage) { "" }

        fun getErrorMessage(it: TextValidation.Error): String =
            when (it) {
                is LengthValidation.Error.Required -> "é obrigatório"
                is LengthValidation.Error.TooLong -> "é muito longo"
                is LengthValidation.Error.TooShort -> "é muito curto"
                is EmailValidation.Error.InvalidFormat -> "é inválido"
                is NumbersValidation.Error.AtLeast -> "deve conter números"
                is LowerCaseValidation.Error.AtLeast -> "deve conter letras minúsculas"
                is UpperCaseValidation.Error.AtLeast -> "deve conter letras maiúsculas"
                is SymbolsValidation.Error.AtLeast -> "deve conter símbolos. Ex: ${it.permitted}"

                is Error -> "O campo ${getErrorMessage(it.error)}"
            }
    }
}

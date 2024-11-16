package uz.com.task1

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource

sealed class DemoExceptionHandler : RuntimeException() {
    abstract fun errorCode(): ErrorCodes
    open fun getAllArguments(): Array<Any?>? = null

    fun getErrorMessage(resourceBundle: ResourceBundleMessageSource): BaseMessage {
        val message = try {
            resourceBundle.getMessage(
                    errorCode().name, getAllArguments(), LocaleContextHolder.getLocale()
            )
        } catch (e: Exception) {
            e.message
        }
        return BaseMessage(errorCode().code, message)
    }
}


class UserAlreadyExistsException : DemoExceptionHandler() {
    override fun errorCode() = ErrorCodes.USER_ALREADY_EXISTS
}


class UserNotFoundExistsException:DemoExceptionHandler(){
    override fun errorCode() = ErrorCodes.USER_NOT_FOUND
}

class CategoryHasAlreadyExistsException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.CATEGORY_HAS_ALREADY_EXIST
}

class CategoryNotFoundExistsException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.CATEGORY_NOT_FOUND
}

class ProductNotFoundException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.PRODUCT_NOT_FOUND
}

class UserPaymentTransactionNotFoundException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.USER_PAYMENT_TRANSACTION_NOT_FOUND
}

class TransactionNotFoundException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.TRANSACTION_NOT_FOUND_EXCEPTION
}

class ProductHasNotEnoughException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.PRODUCT_HAS_NOT_ENOUGH_EXCEPTION
}

class UserHasNotEnoughBalance:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.USER_HAS_NOT_ENOUGH_BALANCE
}
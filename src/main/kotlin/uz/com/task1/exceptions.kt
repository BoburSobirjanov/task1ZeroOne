package uz.com.task1


sealed class DemoExceptionHandler : RuntimeException() {
    abstract fun errorCode(): ErrorCodes

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

class TransactionItemNotFoundException:DemoExceptionHandler(){
    override fun errorCode()= ErrorCodes.TRANSACTION_ITEM_NOT_FOUND_EXCEPTION
}
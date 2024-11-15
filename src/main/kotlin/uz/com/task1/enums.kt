package uz.com.task1


enum class ErrorCodes(val code: Int) {
    USER_NOT_FOUND(100),
    USER_ALREADY_EXISTS(101),
    CATEGORY_HAS_ALREADY_EXIST(201),
    CATEGORY_NOT_FOUND(200),
    PRODUCT_NOT_FOUND(300)
}



enum class UserRole {
    USER, ADMIN
}
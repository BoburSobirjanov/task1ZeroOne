package uz.com.task1

import jakarta.validation.constraints.Size
import java.math.BigDecimal


data class BaseMessage(val code: Int, val message: String?)




data class UserCreateRequest(
        var fullName: String,
        @field:Size(min = 5, max = 20) var username: String,
        var balance: BigDecimal,
        var userRole: UserRole
) {
    fun toEntity(): User {
        return User(fullName, username, balance, userRole)
    }
}

data class UserUpdateRequest(
        var fullName: String,
        var username: String?,
        var balance: BigDecimal,
        var userRole: UserRole
)

data class UserResponse(
        var id:Long,
        var fullName: String,
        var username: String,
        var balance: BigDecimal,
        var userRole: UserRole
){
    companion object {
        fun toResponse(user: User): UserResponse {
            user.run {
                return UserResponse(id!!, fullName, username, balance, userRole)
            }
        }
    }
}










data class ProductCreateRequest(
        var name: String,
        var count: Long,
        var categoryId: Long
){
    fun toEntity(category: Category): Product {
        return Product(name,count,category)
    }
}

data class ProductResponse(
        var id: Long,
        var name: String,
        var count: Long,
        var categoryName: String
){
    companion object {
        fun toResponse(product: Product): ProductResponse{
            product.run {
                return ProductResponse(id!!,name,count,categoryId.name)
            }
        }
    }
}










data class CategoryCreateRequest(
        var name: String,
        var orders: Long,
        var description: String
){
    fun toEntity():Category{
        return Category(name, orders, description)
    }
}

data class CategoryResponse(
        var id: Long,
        var name: String,
        var description: String,
        var order: Long=0L
){
    companion object {
        fun toResponse(category: Category): CategoryResponse {
            category.run {
                return CategoryResponse(id!!,name, description, orders!!)
            }
        }
    }
}












data class TransactionCreateRequest(
        var userId: Long,
        var totalAmount: BigDecimal
){
    fun toEntity(user: User):Transaction{
        return Transaction(user,totalAmount)
    }
}

data class TransactionResponse(
        var id: Long,
        var userUsername: String,
        var totalAmount: BigDecimal
){
    companion object{
        fun toResponse(transaction: Transaction):TransactionResponse{
            transaction.run {
                return TransactionResponse(id!!, userId.username,totalAmount)
            }
        }
    }
}
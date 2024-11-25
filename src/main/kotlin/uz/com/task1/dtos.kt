package uz.com.task1

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.math.BigDecimal




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
                return ProductResponse(product.id!!,product.name,product.count,product.category.name)
            }
        }
    }
}










data class CategoryCreateRequest(
        var name: String,
        var orders: Long,
        var description: String,
){
    fun toEntity():Category{
        return Category(name, orders, description)
    }
}

data class CategoryResponse(
        var id: Long,
        var name: String,
        var description: String,
        var order: Long
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
        var transactionItems: List<TransactionItemCreateRequestList>
){
    private fun calculateTotalAmount(): BigDecimal {
        return transactionItems.fold(BigDecimal.ZERO) { total, item ->
            total + item.amount.multiply(BigDecimal(item.count))
        }
    }
    fun toEntity(user: User):Transaction{
        return Transaction(user,calculateTotalAmount())
    }
}



data class TransactionItemCreateRequestList(
        var productId:Long,
        @field:Min(1) @field:Positive var count: Long,
        var amount: BigDecimal
)




data class TransactionResponse(
        var id: Long,
        var userUsername: String,
        var totalAmount: BigDecimal
){
    companion object{
        fun toResponse(transaction: Transaction):TransactionResponse{
            transaction.run {
                return TransactionResponse(id!!, user.username,totalAmount)
            }
        }
    }
}










data class UserPaymentTransactionCreateRequest(
        var userId: Long,
        var amount: BigDecimal
){
    fun toEntity(user: User):UserPaymentTransaction{
        return UserPaymentTransaction(user,amount)
    }
}

data class UserPaymentTransactionResponse(
        var  id: Long,
        var userUsername: String,
        var amount: BigDecimal
){
    companion object{
        fun toResponse(userPaymentTransaction: UserPaymentTransaction):UserPaymentTransactionResponse{
            userPaymentTransaction.run {
                return UserPaymentTransactionResponse(id!!,user.username,amount)
            }
        }
    }
}








data class TransactionItemCreateRequest(
        var productId:Long,
        @field:Min(1) @field:Positive var count: Long,
        var amount: BigDecimal,
        var transactionId:Long
){
    fun toEntity(transaction: Transaction, product: Product):TransactionItem{
        val totalAmount = amount.multiply(BigDecimal(count))
        return TransactionItem(product,count,amount,totalAmount,transaction)
    }
}






data class TransactionItemResponse(
        var id: Long,
        var count: Long,
        var amount: BigDecimal,
        var transactionId: Long
){
    companion object{
        fun toResponse(transactionItem: TransactionItem):TransactionItemResponse{
            transactionItem.run {
                return TransactionItemResponse(id!!,count,amount, transaction.id!!)
            }
        }
    }
}
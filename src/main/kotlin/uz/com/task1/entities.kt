package uz.com.task1

import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime



@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @CreationTimestamp  var createdDate: LocalDateTime? = null,
        @Column(nullable = false) @ColumnDefault(value = "false") var deleted: Boolean = false
)




@Entity(name = "users")
class User(
        @Column(nullable = false) var fullName: String,
        @Column(nullable = false, unique = true) var username: String,
        var balance: BigDecimal,
        @Enumerated(EnumType.STRING) var userRole: UserRole
):BaseEntity()






@Entity(name = "category")
class Category(
        @Column(nullable = false, unique = true) var name: String,
        var orders: Long?=null,
        var description: String
):BaseEntity()






@Entity(name = "transactions")
class Transaction(
       @ManyToOne var userId: User,
       var totalAmount: BigDecimal
):BaseEntity()






@Entity(name = "products")
class Product(
        var name: String,
        var count: Long,
        @ManyToOne var categoryId: Category
):BaseEntity()






@Entity(name = "transaction_items")
class TransactionItem(
       @ManyToOne  var productId: Product,
        var count: Long,
        var amount: BigDecimal,
        var totalAmount: BigDecimal,
       @ManyToOne  var transactionId: Transaction
):BaseEntity()






@Entity(name = "user_payment_transaction")
class UserPaymentTransaction(
        @ManyToOne var userId: User,
        var amount: BigDecimal
):BaseEntity()


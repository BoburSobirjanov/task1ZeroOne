package uz.com.task1

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
interface UserService{
    fun create(request: UserCreateRequest)
    fun getOne(id: Long): UserResponse
    fun update(id: Long, request: UserUpdateRequest)
    fun delete(id: Long)
}

@Service
class UserServiceImpl(
        private val userRepository: UserRepository):UserService{
    override fun create(request: UserCreateRequest) {
        request.run {
            val user= userRepository.findByUsernameAndDeletedFalse(username)
            if (user!=null)
                throw UserAlreadyExistsException()
            userRepository.save(this.toEntity())
        }
    }

    override fun getOne(id: Long): UserResponse {
           return userRepository.findUserByIdAndDeletedFalse(id)?.let {
              UserResponse.toResponse(it)
          } ?: throw UserNotFoundExistsException()
    }

    override fun update(id: Long, request: UserUpdateRequest) {
        val user = userRepository.findUserByIdAndDeletedFalse(id)?: throw UserNotFoundExistsException()

        request.run {
            username?.let {
               val usernameAndDeletedFalse = userRepository.findUserByIdAndUsername(id, it)
                if (usernameAndDeletedFalse!=null) throw UserAlreadyExistsException()
                user.username=it
            }
            userRole.let { user.userRole=it }
            fullName.let { user.fullName=it }
        }
        userRepository.save(user)
    }

    @Transactional
    override fun delete(id: Long) {
        val user = userRepository.findUserByIdAndDeletedFalse(id) ?: throw UserNotFoundExistsException()
        user.run {
            deleted=true
        }
        userRepository.save(user)
    }

}












@Service
interface CategoryService{
    fun create(request: CategoryCreateRequest)
    fun getOne(id: Long): CategoryResponse
    fun update(id: Long, request: CategoryCreateRequest)
    fun delete(id: Long)
}


@Service
class CategoryServiceImpl(
        private val categoryRepository: CategoryRepository
):CategoryService{

    override fun create(request: CategoryCreateRequest) {
        request.run {
            val category = categoryRepository.findCategoryByName(name)
            if (category!=null) throw CategoryHasAlreadyExistsException()
            categoryRepository.save(this.toEntity())
        }
    }

    override fun getOne(id: Long): CategoryResponse {
        return  categoryRepository.findCategoryById(id)?.let {
            CategoryResponse.toResponse(it)
        }?:  throw CategoryNotFoundExistsException()

    }

    override fun update(id: Long, request: CategoryCreateRequest) {
        val category = categoryRepository.findCategoryById(id)?: throw CategoryNotFoundExistsException()
        println(category)
        request.run {
            name.let {
                val categoryIdAndNameDeletedFalse = categoryRepository.findCategoryByIdAndName(id,it)
                if (categoryIdAndNameDeletedFalse!=null) throw CategoryHasAlreadyExistsException()
                category.name=it
            }
            description.let { category.description=it }
            orders.let { category.orders=it }
        }
        categoryRepository.save(category)
    }

    override fun delete(id: Long) {
        val category = categoryRepository.findCategoryById(id) ?: throw CategoryNotFoundExistsException()
        category.run {
            deleted=true
        }
        categoryRepository.save(category)
    }

}












@Service
interface ProductService{
    fun create(request: ProductCreateRequest)
    fun getOne(id: Long): ProductResponse
    fun update(id: Long, request: ProductCreateRequest)
    fun delete(id: Long)
}

@Service
class ProductServiceImpl(
        private val productRepository: ProductRepository,
        private val categoryRepository: CategoryRepository,
        private val entityManager: EntityManager
):ProductService{
    override fun create(request: ProductCreateRequest) {
        request.run {
           categoryRepository.findCategoryById(categoryId)?: throw CategoryNotFoundExistsException()
           val category = entityManager.getReference(Category::class.java,categoryId)
           productRepository.save(this.toEntity(category))
        }
    }

    override fun getOne(id: Long): ProductResponse {
        return productRepository.findProductById(id)?.let {
            ProductResponse.toResponse(it)
        }?: throw ProductNotFoundException()

    }

    override fun update(id: Long, request: ProductCreateRequest) {
        val product = productRepository.findProductById(id) ?: throw ProductNotFoundException()
        product.run {
            name = request.name
            count = request.count
            val category = categoryRepository.findCategoryById(request.categoryId)?: throw CategoryNotFoundExistsException()
            categoryId = category
        }
        productRepository.save(product)
    }

    override fun delete(id: Long) {
        val product = productRepository.findProductById(id)?:throw ProductNotFoundException()
        product.run {
            deleted=false
        }
        productRepository.save(product)
    }

}




@Service
interface TransactionService{
    fun create(request: TransactionCreateRequest)
    fun getOne(id: Long): TransactionResponse
    fun update(id: Long, request: TransactionCreateRequest)
    fun delete(id: Long)
}
@Service
class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository,
        private val userRepository: UserRepository,
        private val entityManager: EntityManager
):TransactionService{
    override fun create(request: TransactionCreateRequest) {
        request.run {
            userRepository.findUserByIdAndDeletedFalse(request.userId) ?: throw UserNotFoundExistsException()
            val user = entityManager.getReference(User::class.java,userId)
            transactionRepository.save(this.toEntity(user))
        }
    }

    override fun getOne(id: Long): TransactionResponse {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, request: TransactionCreateRequest) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

}







@Service
interface UserPaymentTransactionService{
    fun create(request: UserPaymentTransactionCreateRequest)
    fun getOne(id: Long): UserPaymentTransactionResponse
    fun delete(id: Long)
    fun getUsersPaymentTransactionHistory(id: Long,pageable: Pageable): Page<UserPaymentTransactionResponse>
}

@Service
class UserPaymentTransactionServiceImpl(
        private val userPaymentTransactionRepository: UserPaymentTransactionRepository,
        private val userRepository: UserRepository,
        private val entityManager: EntityManager
):UserPaymentTransactionService{
    override fun create(request: UserPaymentTransactionCreateRequest) {
        request.run {
            userRepository.findUserByIdAndDeletedFalse(request.userId)?:throw UserNotFoundExistsException()
            val user = entityManager.getReference(User::class.java,userId)
            user.balance+=request.amount
            userRepository.save(user)
            userPaymentTransactionRepository.save(this.toEntity(user))
        }
    }

    override fun getOne(id: Long): UserPaymentTransactionResponse {
        return userPaymentTransactionRepository.findUserPaymentTransactionById(id) ?.let {
            UserPaymentTransactionResponse.toResponse(it)
        }?:throw UserPaymentTransactionNotFoundException()

    }

    override fun delete(id: Long) {
        val userPaymentTransaction = userPaymentTransactionRepository.findUserPaymentTransactionById(id)?:throw UserPaymentTransactionNotFoundException()
        userPaymentTransaction.run {
            deleted=false
        }
        userPaymentTransactionRepository.save(userPaymentTransaction)
    }

    override fun getUsersPaymentTransactionHistory(id: Long,pageable: Pageable):Page<UserPaymentTransactionResponse> {
        val user = userRepository.findUserByIdAndDeletedFalse(id)?:throw UserNotFoundExistsException()
        return userPaymentTransactionRepository.findAllUserPaymentTransactionByUserId(user, pageable).map {
            UserPaymentTransactionResponse.toResponse(it)
        }
    }

}









@Service
interface TransactionItemService{
    fun create(request: TransactionItemCreateRequest)
    fun getOne(id: Long): TransactionItemResponse
    fun update(id: Long, request: TransactionCreateRequest)
    fun delete(id: Long)
}

@Service
class TransactionItemServiceImpl(
        private val transactionItemsRepository: TransactionItemsRepository,
        private val transactionRepository: TransactionRepository,
        private val productRepository: ProductRepository,
        private val userRepository: UserRepository,
        private val entityManager: EntityManager
):TransactionItemService{
    override fun create(request: TransactionItemCreateRequest) {
        request.run {
            transactionRepository.findTransactionById(request.transactionId)?:throw TransactionNotFoundException()
            productRepository.findProductById(request.productId)?:throw ProductNotFoundException()
            val transaction = entityManager.getReference(Transaction::class.java,transactionId)
            val product = entityManager.getReference(Product::class.java,productId)
            val user = transaction.userId.id?.let { userRepository.findUserByIdAndDeletedFalse(it) }
            if (user != null) {
                if (user.balance<request.amount.multiply(BigDecimal(request.count)))throw UserHasNotEnoughBalance()
            }
            user!!.balance-=request.amount.multiply(BigDecimal(request.count))
            userRepository.save(user!!)
            if (product.count<request.count)throw ProductHasNotEnoughException()
            transaction.totalAmount+=request.amount.multiply(BigDecimal(request.count))
            transactionItemsRepository.save(this.toEntity(transaction,product))
            transactionRepository.save(transaction)

        }
    }

    override fun getOne(id: Long): TransactionItemResponse {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, request: TransactionCreateRequest) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

}
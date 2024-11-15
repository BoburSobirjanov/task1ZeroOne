package uz.com.task1

import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


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
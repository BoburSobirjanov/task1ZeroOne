package uz.com.task1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository


@NoRepositoryBean
interface BaseRepository<T:BaseEntity>: JpaRepository<T,Long>, JpaSpecificationExecutor<T> {

}


@Repository
interface UserRepository:BaseRepository<User> {


    @Query("select u from users  as u where u.username= :username and u.deleted=false")
    fun findByUsernameAndDeletedFalse(username: String): User?

    @Query("select u from users as u where u.id= :id and u.deleted=false")
    fun findUserByIdAndDeletedFalse(id:Long): User?


    @Query("""select u from users as u
            where u.id != :id
            and u.username = :username
            and u.deleted = false
            """ )
    fun findUserByIdAndUsername(id:Long, username:String):User?

}




@Repository
interface ProductRepository:BaseRepository<Product> {

    @Query("select u from products as u where u.deleted=false and u.id=:id")
    fun findProductById(id: Long):Product?

}




@Repository
interface CategoryRepository:BaseRepository<Category> {

    @Query("select u from category as u where u.deleted=false and u.name= :name")
    fun findCategoryByName(name:String):Category?


    @Query("select u from category as u where u.deleted=false and u.id= :id")
    fun findCategoryById(id: Long):Category?

    @Query("select u from category as u where u.id=:id and u.name=:name and u.deleted=false")
    fun findCategoryByIdAndName(id: Long, name: String):Category?

}



@Repository
interface TransactionRepository:BaseRepository<Transaction>{

    @Query("select u from transactions as u where u.deleted=false and u.id=:id")
    fun findTransactionById(id: Long)


    @Query("select u from transactions as u where u.deleted=false and u.userId=:user")
    fun findTransactionByUserId(user: User)
}






@Repository
interface UserPaymentTransactionRepository:BaseRepository<UserPaymentTransaction>{

    @Query("select u from user_payment_transaction as u where u.id=:id")
    fun findUserPaymentTransactionById(id: Long):UserPaymentTransaction?

    @Query("select u from user_payment_transaction as u where u.deleted=false and u.userId=:user")
    fun findAllUserPaymentTransactionByUserId(user: User, pageable: Pageable):Page<UserPaymentTransaction>

}




@Repository
interface TransactionItemsRepository:BaseRepository<TransactionItem>{
    @Query("select u from transaction_items as u where u.deleted=false and u.id=:id")
    fun findTransactionItemById(id: Long)
}
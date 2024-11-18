package uz.com.task1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository


@NoRepositoryBean
interface BaseRepository<T:BaseEntity>: JpaRepository<T,Long>, JpaSpecificationExecutor<T>


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

    @Query("select u from users as u where u.deleted=false")
    fun findAllUser(pageable: Pageable):Page<User>

}




@Repository
interface ProductRepository:BaseRepository<Product> {

    @Query("select u from products as u where u.deleted=false and u.id=:id")
    fun findProductById(id: Long):Product?

    @Query("select u from products as u where u.deleted=false")
    fun findAllProduct(pageable: Pageable):Page<Product>

}




@Repository
interface CategoryRepository:BaseRepository<Category> {
    @Query("select u from category as u where u.deleted=false and u.name= :name")
    fun findCategoryByName(name:String):Category?

    @Query("select u from category as u where u.deleted=false and u.id= :id")
    fun findCategoryById(id: Long):Category?

    @Query("select u from category as u where u.id=:id and u.name=:name and u.deleted=false")
    fun findCategoryByIdAndName(id: Long, name: String):Category?

    @Query("select u from category as u where u.deleted=false")
    fun findAllCategory(pageable: Pageable):Page<Category>

    @Query("select u from category as u order by case when :sortState='asc' " +
            "then u.orders end asc, case when :sortState='desc' " +
            "then u.orders end desc")
    fun findAllSortedCategories(sortState:String,pageable: Pageable):Page<Category>

}



@Repository
interface TransactionRepository:BaseRepository<Transaction>{

    @Query("select u from transactions as u where u.deleted=false and u.id=:id")
    fun findTransactionById(id: Long):Transaction?

    @Query("select u from transactions as u where u.deleted=false and u.user=:user")
    fun findAllTransactionByUserId(user: User, pageable: Pageable):Page<Transaction>

    @Query("select u from transactions as u where u.deleted=false")
    fun findAllTransaction(pageable: Pageable):Page<Transaction>
}






@Repository
interface UserPaymentTransactionRepository:BaseRepository<UserPaymentTransaction>{

    @Query("select u from user_payment_transaction as u where u.id=:id")
    fun findUserPaymentTransactionById(id: Long):UserPaymentTransaction?

    @Query("select u from user_payment_transaction as u where u.deleted=false and u.user=:user")
    fun findAllUserPaymentTransactionByUserId(user: User, pageable: Pageable):Page<UserPaymentTransaction>

    @Query("select u from user_payment_transaction as u where u.deleted=false")
    fun findAllUserPaymentTransaction(pageable: Pageable):Page<UserPaymentTransaction>

}




@Repository
interface TransactionItemsRepository:BaseRepository<TransactionItem>{
    @Query("select u from transaction_items as u where u.deleted=false and u.id=:id")
    fun findTransactionItemById(id: Long):TransactionItem?


    @Query("""
    SELECT 
        json_build_object(
            'id', p.id,
            'name', p.name
            ) 
            AS product, ti.* FROM transactions AS tr INNER JOIN transaction_items AS ti 
        ON tr.id = ti.transaction_id AND ti.deleted = false INNER JOIN products AS p 
        ON p.id = ti.product_id AND p.deleted = false
    WHERE tr.user_id = :id AND tr.deleted = false
""", nativeQuery = true)
    fun findUsersProductHistory(id: Long, pageable: Pageable): Page<Map<String, Any>>


    @Query("select u from transaction_items as u where u.deleted=false and u.transaction=:transaction")
    fun findAllTransactionItemByTransaction(transaction: Transaction, pageable: Pageable):Page<TransactionItem>

    @Query("select u from transaction_items as u where u.deleted=false")
    fun findAllTransactionItems(pageable: Pageable):Page<TransactionItem>
}
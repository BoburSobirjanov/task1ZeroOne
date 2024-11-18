package uz.com.task1

import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/v1/user")
class UserController( val userService: UserService){


    @PostMapping("create")
    fun create(@RequestBody @Valid request: UserCreateRequest) = userService.create(request)


    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = userService.getOne(id)


    @PutMapping("{id}")
    fun update(@RequestBody request: UserUpdateRequest, @PathVariable id:Long) = userService.update(id, request)


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = userService.delete(id)


    @GetMapping("get-all")
    fun getAll(pageable: Pageable)=userService.getAll(pageable)

}









@RestController
@RequestMapping("api/v1/category")
class CategoryController(val categoryService: CategoryService
){

    @PostMapping("create")
    fun create(@RequestBody request: CategoryCreateRequest) = categoryService.create(request)


    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long)= categoryService.getOne(id)


    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody request: CategoryCreateRequest) = categoryService.update(id, request)


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long)= categoryService.delete(id)

    @GetMapping("get-all")
    fun getAll(@RequestParam(required = false) sortState: String?,pageable: Pageable): Page<CategoryResponse> {
        return if (sortState.isNullOrBlank()) {
            categoryService.getAll(pageable)
        } else {
            if (sortState.lowercase(Locale.getDefault()) != "asc" && sortState.lowercase(Locale.getDefault()) != "desc") {
                throw IllegalArgumentException("sortState must be 'asc' or 'desc' format")
            }
            categoryService.getSortedAll(sortState,pageable)
        }
}







@RestController
@RequestMapping("api/v1/product")
class ProductController(val productService: ProductService){

    @PostMapping("create")
    fun create(@RequestBody request: ProductCreateRequest)= productService.create(request)


    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long)=productService.getOne(id)


    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody request: ProductCreateRequest)=productService.update(id, request)


    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long)=productService.delete(id)


    @GetMapping("get-all")
    fun getAll(pageable: Pageable)=productService.getAll(pageable)
}







@RestController
@RequestMapping("api/v1/user-payment-transaction")
class UserPaymentTransactionController(val userPaymentTransactionService: UserPaymentTransactionService){

    @PostMapping("create")
    fun create(@RequestBody request: UserPaymentTransactionCreateRequest)=userPaymentTransactionService.create(request)

    @GetMapping("get-users-payments-history")
    fun getUsersAllPaymentTransaction(@RequestParam id: Long, pageable: Pageable)=userPaymentTransactionService.getUsersPaymentTransactionHistory(id, pageable)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long)=userPaymentTransactionService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long)=userPaymentTransactionService.delete(id)

    @GetMapping("get-all")
    fun getAll(pageable: Pageable)=userPaymentTransactionService.getAll(pageable)
}




@RestController
@RequestMapping("api/v1/transaction")
class TransactionController(val transactionService: TransactionService ){

    @PostMapping("create")
    fun create(@RequestBody request: TransactionCreateRequest)=transactionService.create(request)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long)=transactionService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long)=transactionService.delete(id)

    @GetMapping("get-all")
    fun getAll(pageable: Pageable)=transactionService.getAll(pageable)

    @GetMapping("get-user-id/{id}")
    fun getTransactionByUserId(@PathVariable id: Long, pageable: Pageable)=
            transactionService.getAllTransactionByUserId(id, pageable)
}





@RestController
@RequestMapping("api/v1/transaction-item")
class TransactionItemController(val transactionItemService: TransactionItemService){

    @PostMapping("create")
    fun create(@RequestBody request: TransactionItemCreateRequest)=transactionItemService.create(request)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long)=transactionItemService.getOne(id)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long)=transactionItemService.delete(id)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody request: TransactionItemCreateRequest)=
            transactionItemService.update(id, request)

    @GetMapping("get-all")
    fun getAll(pageable: Pageable)=transactionItemService.getAll(pageable)

    @GetMapping
    fun usersTransactionItemsHistory(@RequestParam id: Long, pageable: Pageable)=
            transactionItemService.getUsersTransactionItemsHistory(id, pageable)
  }
}
package uz.com.task1

import jakarta.validation.Valid
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler


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
}







@RestController
@RequestMapping("api/v1/user-payment-transaction")
class UserPaymentTransactionController(val userPaymentTransactionService: UserPaymentTransactionService){

    @PostMapping("create")
    fun create(@RequestBody request: UserPaymentTransactionCreateRequest)=userPaymentTransactionService.create(request)

    @GetMapping("get-users-payments-history")
    fun getUsersAllPaymentTransaction(@RequestParam id: Long, pageable: Pageable)=userPaymentTransactionService.getUsersPaymentTransactionHistory(id, pageable)

}




@RestController
@RequestMapping("api/v1/transaction")
class TransactionController(val transactionService: TransactionService ){

    @PostMapping("create")
    fun create(@RequestBody request: TransactionCreateRequest)=transactionService.create(request)
}





@RestController
@RequestMapping("api/v1/transaction-item")
class TransactionItemController(val transactionItemService: TransactionItemService){

    @PostMapping("create")
    fun create(@RequestBody request: TransactionItemCreateRequest)=transactionItemService.create(request)
}
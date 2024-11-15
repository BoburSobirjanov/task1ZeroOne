package uz.com.task1

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
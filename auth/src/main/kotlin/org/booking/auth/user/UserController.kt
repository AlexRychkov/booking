package org.booking.auth.user

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import reactor.core.publisher.Mono

@Controller
interface IUserController {

    @PostMapping("/api/v1/user/signup")
    @ResponseBody
    fun signup(@RequestBody registerUser: RegisterUser): Mono<Boolean>

    @PostMapping("/api/v1/user/signin")
    @ResponseBody
    fun signIn(@RequestBody loginUser: LoginUser): Mono<String>

    @PostMapping("/api/v1/user/disable")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun disable(@RequestBody disableUser: DisableUser): Mono<Boolean>

    @PostMapping("/api/v1/user/role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    fun role(@RequestBody changeUserRole: ChangeUserRole): Mono<Boolean>
}

@Component
open class UserController(
    private val createUser: CreateUserCommand,
    private val loginUser: LoginUserCommand,
    private val changeRole: ChangeRoleCommand,
    private val disableUser: DisableUserCommand,
) : IUserController {

    override fun signup(registerUser: RegisterUser): Mono<Boolean> = createUser(registerUser)

    override fun signIn(loginUser: LoginUser): Mono<String> = loginUser(loginUser)

    override fun role(changeUserRole: ChangeUserRole): Mono<Boolean> = changeRole(changeUserRole)

    override fun disable(disableUser: DisableUser): Mono<Boolean> = disableUser(disableUser)
}
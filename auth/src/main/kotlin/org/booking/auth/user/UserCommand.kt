package org.booking.auth.user

import org.booking.auth.util.PasswordUtil
import org.booking.common.ACommandHandler
import org.booking.jwt.ITokenService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CreateUserCommand(
    private val userRepository: IUserRepository,
) : ACommandHandler<RegisterUser, Mono<Boolean>>() {

    override fun validate(payload: RegisterUser) {
        if (!payload.isValid()) {
            throw IllegalArgumentException("Some of input fields are not correct")
        }
    }

    override fun processInternal(payload: RegisterUser): Mono<Boolean> =
        payload.let {
            User(email = it.email, name = it.name, password = PasswordUtil.encode(it.password))
        }.let { user ->
            userRepository.save(user)
                .thenReturn(true)
                .onErrorReturn(false)
        }
}

@Component
class LoginUserCommand(
    private val userRepository: IUserRepository,
    private val tokenService: ITokenService,
) : ACommandHandler<LoginUser, Mono<String>>() {

    override fun validate(payload: LoginUser) {
        if (!payload.isValid()) {
            throw IllegalArgumentException("Some of input fields are wrong")
        }
    }

    override fun processInternal(payload: LoginUser): Mono<String> =
        userRepository.findByEmail(payload.email)
            .filter { user -> PasswordUtil.matches(payload.password, user.password) }
            .switchIfEmpty(Mono.error(IllegalArgumentException("Wrong password")))
            .flatMap { user -> Mono.just(tokenService.generate(user.publicId, user.role.name)) }
}

@Component
class ChangeRoleCommand(
    private val userRepository: IUserRepository,
) : ACommandHandler<ChangeUserRole, Mono<Boolean>>() {

    override fun validate(payload: ChangeUserRole) {}

    override fun processInternal(payload: ChangeUserRole): Mono<Boolean> =
        userRepository.setRole(payload.publicId, payload.role)
            .thenReturn(true)
            .onErrorReturn(false)
}

@Component
class DisableUserCommand(
    private val userRepository: IUserRepository,
) : ACommandHandler<DisableUser, Mono<Boolean>>() {

    override fun validate(payload: DisableUser) {}

    override fun processInternal(payload: DisableUser): Mono<Boolean> =
        userRepository.setDisabled(payload.publicId)
            .thenReturn(true)
            .onErrorReturn(false)
}
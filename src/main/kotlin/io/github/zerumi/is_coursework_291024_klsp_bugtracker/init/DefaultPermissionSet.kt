package io.github.zerumi.is_coursework_291024_klsp_bugtracker.init

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.PermissionSet
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.Permissions
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.PermissionSetRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DefaultPermissionSet(
    val permissionSetRepository: PermissionSetRepository
) : CommandLineRunner {
    companion object {
        private const val DEFAULT_PERMISSIONS_NAME = "default"
        private val DEFAULT_PERMISSIONS =
            Permissions.PERMISSION_EDIT_PERMISSIONS or Permissions.PERMISSION_EDIT_PERMISSIONS

        lateinit var DEFAULT_PERMISSION_SET: PermissionSet
            private set
    }

    override fun run(vararg args: String?) {
        val defaultPermissionSet = permissionSetRepository.findByName(DEFAULT_PERMISSIONS_NAME) ?: PermissionSet(
            name = DEFAULT_PERMISSIONS_NAME,
            permissions = DEFAULT_PERMISSIONS
        )

        DEFAULT_PERMISSION_SET = permissionSetRepository.save(defaultPermissionSet)
    }
}
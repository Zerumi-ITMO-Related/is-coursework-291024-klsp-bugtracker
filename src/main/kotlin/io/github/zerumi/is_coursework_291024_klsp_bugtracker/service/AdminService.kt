package io.github.zerumi.is_coursework_291024_klsp_bugtracker.service

import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.PermissionSetInfo
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.dto.PermissionSetRequestDTO
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.PermissionSet
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.entity.User
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.PermissionSetRepository
import io.github.zerumi.is_coursework_291024_klsp_bugtracker.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val permissionSetRepository: PermissionSetRepository,
    private val userRepository: UserRepository,
) {
    fun getById(id: Long?) = permissionSetRepository.getReferenceById(requireNotNull(id))

    fun createPermissionSet(permissionSetRequestDTO: PermissionSetRequestDTO): PermissionSet {
        val permissionSet = PermissionSet(
            name = permissionSetRequestDTO.name, permissions = permissionSetRequestDTO.permissions
        )

        return permissionSetRepository.save(permissionSet)
    }

    fun updatePermissionSet(permissionSetInfo: PermissionSetInfo): PermissionSet {
        val permissionSetToUpdate = permissionSetRepository.getReferenceById(requireNotNull(permissionSetInfo.id))

        permissionSetToUpdate.name = permissionSetInfo.name
        permissionSetToUpdate.permissions = permissionSetInfo.permissions

        return permissionSetRepository.save(permissionSetToUpdate)
    }

    fun deletePermissionSet(permissionSetInfo: PermissionSetInfo) =
        permissionSetRepository.deleteById(requireNotNull(permissionSetInfo.id))

    fun linkUserToPermissionSet(userId: Long, permissionSetId: Long): User {
        val userToLinkPermissionSet = userRepository.getReferenceById(userId)
        val permissionSetToLinkUser = permissionSetRepository.getReferenceById(permissionSetId)

        userToLinkPermissionSet.permissionSet.assignedUsers.remove(userToLinkPermissionSet)
        permissionSetToLinkUser.assignedUsers.add(userToLinkPermissionSet)
        userToLinkPermissionSet.permissionSet = permissionSetToLinkUser

        return userRepository.save(userToLinkPermissionSet)
    }

    fun getPermissionSets(): List<PermissionSet> =
        permissionSetRepository.findAll()
}

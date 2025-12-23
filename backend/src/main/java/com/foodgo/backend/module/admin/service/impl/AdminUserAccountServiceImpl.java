package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.auth.dto.RegisterRequest;
import com.foodgo.backend.module.user.dto.response.UserAccountResponse;
import com.foodgo.backend.module.admin.service.AdminUserAccountService;
import com.foodgo.backend.module.user.dto.criteria.UserAccountSearchSpecification;
import com.foodgo.backend.module.user.dto.mapper.UserAccountMapper;
import com.foodgo.backend.module.user.dto.request.filter.UserAccountFilterRequest;
import com.foodgo.backend.module.user.entity.Role;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserAccountServiceImpl
    extends BaseServiceImpl<
        UserAccount, UUID, RegisterRequest, Object, UserAccountFilterRequest, UserAccountResponse>
    implements AdminUserAccountService {

  private final String entityName = EntityName.USER_ACCOUNT.getFriendlyName();

  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;
  private final UserAccountMapper userAccountMapper;

  // --- BASE IMPLEMENTATION ---

  @Override
  protected JpaRepository<UserAccount, UUID> getRepository() {
    return userAccountRepository;
  }

  @Override
  protected JpaSpecificationExecutor<UserAccount> getSpecRepository() {
    return userAccountRepository;
  }

  @Override
  protected BaseMapper<UserAccount, RegisterRequest, Object, UserAccountResponse> getMapper() {
    return userAccountMapper;
  }

  @Override
  protected String getEntityName() {
    return entityName;
  }

  @Override
  protected Specification<UserAccount> buildSpecification(UserAccountFilterRequest filter) {
    return new UserAccountSearchSpecification(filter);
  }

  // --- CUSTOM METHODS ---

  @Override
  @Transactional
  public UserAccountResponse changeUserStatus(UUID id, ChangeUserStatusRequest request) {
    UserAccount user = findByIdOrThrow(id);

    user.setActive(request.isActive());
    UserAccount savedUser = userAccountRepository.save(user);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, entityName, id));
    return userAccountMapper.toResponse(savedUser);
  }

  @Override
  @Transactional
  public UserAccountResponse assignRoles(UUID id, AssignRolesRequest request) {
    UserAccount user = findByIdOrThrow(id);

    Role newRole =
        roleRepository
            .findByName(request.roleType().getName())
            .orElseThrow(
                () -> new ResourceNotFoundException("Role not found: " + request.roleType()));

    user.setRole(newRole);
    UserAccount savedUser = userAccountRepository.save(user);

    SuccessMessageContext.setMessage("Cập nhật quyền hạn thành công cho user: " + id);
    return userAccountMapper.toResponse(savedUser);
  }
}

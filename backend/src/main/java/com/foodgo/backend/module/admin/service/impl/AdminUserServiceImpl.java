package com.foodgo.backend.module.admin.service.impl;

import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.module.admin.dto.user.AssignRolesRequest;
import com.foodgo.backend.module.admin.dto.user.ChangeUserStatusRequest;
import com.foodgo.backend.module.admin.dto.user.UserAdminResponse;
import com.foodgo.backend.module.admin.dto.user.UserFilterRequest;
import com.foodgo.backend.module.admin.service.AdminUserService;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.admin.dto.mapper.AdminUserAccountMapper;
import com.foodgo.backend.module.user.repository.RoleRepository;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
  private final String userAccountEntityName =
      EntityName.USER_ACCOUNT.getFriendlyName() + " (Admin)";

  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;

  private final AdminUserAccountMapper adminUserAccountMapper;

  public Page<UserAdminResponse> getUsers(UserFilterRequest filter, Pageable pageable) {

    // 1. Xây dựng Specification dựa trên các trường lọc
    Specification<UserAccount> spec = UserSpecification.withFilter(filter);

    // 2. Thực hiện truy vấn và phân trang
    Page<UserAccount> userPage = userAccountRepository.findAll(spec, pageable);

    // 3. Mapping nội dung (Content)
    // Sử dụng phương thức map() của Page để mapping mà không làm mất thông tin phân trang
    Page<UserAdminResponse> userAdminResponsePage =
        userPage.map(adminUserAccountMapper::toUserAdminDto);

    // 4. Đặt Message vào Context
    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_SUCCESS, userAccountEntityName));

    // 5. Trả về đối tượng Page<T> của Spring Data
    // GlobalResponseWrapper sẽ bắt đối tượng Page này và tự động bọc thành PageResponse
    return userAdminResponsePage;
  }

  @Override
  @Transactional(readOnly = true)
  public UserAdminResponse getUserById(UUID id) {
    var userAccount =
        userAccountRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy UserAccount với id = " + id));

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.FETCH_DETAIL_SUCCESS,
            userAccountEntityName,
            userAccount.getId()));

    return adminUserAccountMapper.toUserAdminDto(userAccount);
  }

  @Override
  @Transactional
  public UserAdminResponse changeUserStatus(UUID id, ChangeUserStatusRequest changeStatusRequest) {
    var updatedStatusUserAccount =
        userAccountRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy UserAccount với id = " + id));

    // Thay đổi trạng thái hoạt động
    updatedStatusUserAccount.setActive(changeStatusRequest.isActive());

    var savedUser = userAccountRepository.save(updatedStatusUserAccount);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.UPDATE_SUCCESS, userAccountEntityName, savedUser.getId()));

    return adminUserAccountMapper.toUserAdminDto(savedUser);
  }

  @Override
  @Transactional
  public UserAdminResponse assignRoles(UUID id, AssignRolesRequest assignRolesRequest) {
    // 0. Tìm User đang thao tác
    var assignRolesUserAccount =
        userAccountRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy UserAccount với id = " + id));

    // 1. Lấy Role Entity dựa trên các tên role
    var newRole =
        roleRepository
            .findByName(assignRolesRequest.roleType().getName())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Không tìm thấy Role với tên = "
                            + assignRolesRequest.roleType().getName()));

    // 2. Gán và lưu
    assignRolesUserAccount.setRole(newRole);
    var savedUser = userAccountRepository.save(assignRolesUserAccount);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.UPDATE_SUCCESS, userAccountEntityName, savedUser.getId()));

    return adminUserAccountMapper.toUserAdminDto(savedUser);
  }

  @Override
  @Transactional
  public UserAdminResponse softDeleteUser(UUID id) {
    var deletedUserAccount =
        userAccountRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy UserAccount với id = " + id));

    // Xóa mềm người dùng (Đảo ngược trạng thái)
    deletedUserAccount.setActive(!deletedUserAccount.isActive());
    var savedUser = userAccountRepository.save(deletedUserAccount);

    SuccessMessageContext.setMessage(
        String.format(
            SuccessMessageContext.SOFT_DELETE_SUCCESS, userAccountEntityName, savedUser.getId()));

    return adminUserAccountMapper.toUserAdminDto(savedUser);
  }

  public static class UserSpecification {

    public static Specification<UserAccount> withFilter(UserFilterRequest filter) {
      return (root, query, cb) -> {
        if (filter == null) {
          return cb.conjunction();
        }

        boolean isCountQuery = query.getResultType().equals(Long.class);

        if (!isCountQuery) {
          root.fetch("role", JoinType.LEFT);
          root.fetch("profile", JoinType.LEFT);
          query.distinct(true);
        }

        List<Predicate> predicates = new ArrayList<>();

        if (filter.isActive() != null) {
          predicates.add(cb.equal(root.get("isActive"), filter.isActive()));
        }

        if (StringUtils.hasText(filter.roleName())) {
          predicates.add(cb.equal(root.get("role").get("name"), filter.roleName()));
        }

        if (StringUtils.hasText(filter.searchTerm())) {
          String pattern = "%" + filter.searchTerm().toLowerCase() + "%";
          predicates.add(
              cb.or(
                  cb.like(cb.lower(root.get("profile").get("fullName")), pattern),
                  cb.like(cb.lower(root.get("email")), pattern)));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
      };
    }
  }
}

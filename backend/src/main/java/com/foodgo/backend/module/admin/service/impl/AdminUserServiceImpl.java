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
import com.foodgo.backend.module.user.mapper.UserAccountMapper;
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

  private final UserAccountMapper userAccountMapper;

  public Page<UserAdminResponse> getUsers(UserFilterRequest filter, Pageable pageable) {

    // 1. Xây dựng Specification dựa trên các trường lọc
    Specification<UserAccount> spec = UserSpecification.withFilter(filter);

    // 2. Thực hiện truy vấn và phân trang
    Page<UserAccount> userPage = userAccountRepository.findAll(spec, pageable);

    // 3. Mapping nội dung (Content)
    // Sử dụng phương thức map() của Page để mapping mà không làm mất thông tin phân trang
    Page<UserAdminResponse> userAdminResponsePage = userPage.map(userAccountMapper::toUserAdminDto);

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

    return userAccountMapper.toUserAdminDto(userAccount);
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

    return userAccountMapper.toUserAdminDto(savedUser);
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

    return userAccountMapper.toUserAdminDto(savedUser);
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

    return userAccountMapper.toUserAdminDto(savedUser);
  }

  public static class UserSpecification {

    public static Specification<UserAccount> withFilter(UserFilterRequest filter) {
      return (root, query, criteriaBuilder) -> {

        // BƯỚC 1: XỬ LÝ N+1 VÀ LAZY LOADING
        if (query.getResultType() != Long.class && query.getResultType() != long.class) {
          // Khắc phục lỗi Lazy Loading Roles
          root.fetch("role", JoinType.LEFT);
          // Khắc phục lỗi Lazy Loading Profile
          root.fetch("profile", JoinType.LEFT);
        }

        List<Predicate> predicates = new ArrayList<>();

        // 1. Lọc theo trạng thái hoạt động (isActive)
        if (filter.isActive() != null) {
          predicates.add(criteriaBuilder.equal(root.get("isActive"), filter.isActive()));
        }

        // 2. Lọc theo vai trò (roleName)
        if (StringUtils.hasText(filter.roleName())) {
          //  KHẮC PHỤC PATH: JOIN trực tiếp qua thuộc tính "role"
          predicates.add(
              criteriaBuilder.equal(
                  root.join("role", JoinType.INNER).get("name"), filter.roleName()));
        }

        // 3. Lọc theo searchTerm (Tên hoặc Email)
        if (StringUtils.hasText(filter.searchTerm())) {
          String searchPattern = "%" + filter.searchTerm().toLowerCase() + "%";

          // Giả định 'firstName' nằm trong Entity Profile (Quan hệ OneToOne)
          Predicate searchPredicate =
              criteriaBuilder.or(
                  criteriaBuilder.like(
                      criteriaBuilder.lower(root.join("profile").get("fullName")),
                      searchPattern), // Cần join Profile nếu firstName nằm trong Profile
                  criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern));
          predicates.add(searchPredicate);
        }

        if (query.getResultType() != Long.class && query.getResultType() != long.class) {
          query.distinct(true);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      };
    }
  }
}

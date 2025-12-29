package com.foodgo.backend.module.outlet.service.impl;

import com.foodgo.backend.common.base.mapper.BaseMapper;
import com.foodgo.backend.common.base.service.impl.BaseServiceImpl;
import com.foodgo.backend.common.constant.EntityName;
import com.foodgo.backend.common.constant.RoleType;
import com.foodgo.backend.common.context.SecurityContext;
import com.foodgo.backend.common.context.SuccessMessageContext;
import com.foodgo.backend.common.exception.BadRequestException;
import com.foodgo.backend.common.exception.ResourceNotFoundException;
import com.foodgo.backend.common.util.ValidationUtils;
import com.foodgo.backend.module.outlet.dto.criteria.OutletSearchSpecification;
import com.foodgo.backend.module.outlet.dto.mapper.OutletMapper;
import com.foodgo.backend.module.outlet.dto.request.filter.OutletFilterRequest;
import com.foodgo.backend.module.outlet.dto.request.create.OutletCreateRequest;
import com.foodgo.backend.module.outlet.dto.request.update.OutletUpdateRequest;
import com.foodgo.backend.module.outlet.dto.response.OutletResponse;
import com.foodgo.backend.module.outlet.entity.Outlet;
import com.foodgo.backend.module.outlet.repository.OutletRepository;
import com.foodgo.backend.module.location.repository.DistrictRepository;
import com.foodgo.backend.module.outlet.repository.OutletTypeRepository;
import com.foodgo.backend.module.outlet.service.OutletService;
import com.foodgo.backend.module.user.entity.UserAccount;
import com.foodgo.backend.module.user.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import jakarta.persistence.criteria.JoinType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutletServiceImpl
    extends BaseServiceImpl<
        Outlet, UUID, OutletCreateRequest, OutletUpdateRequest, OutletFilterRequest, OutletResponse>
    implements OutletService {

  private final OutletRepository outletRepository;
  private final OutletMapper outletMapper;
  private final UserAccountRepository userAccountRepository;
  private final DistrictRepository districtRepository;
  private final OutletTypeRepository outletTypeRepository;
  private final com.foodgo.backend.module.maps.service.GoogleMapsService googleMapsService;
  private final com.foodgo.backend.module.owner.repository.OwnerRegistrationRequestRepository ownerRegistrationRequestRepository;

  private final String outletEntityName = EntityName.OUTLET.getFriendlyName();

  // --- Abstract Methods ---
  @Override
  protected JpaRepository<Outlet, UUID> getRepository() {
    return outletRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Outlet> getSpecRepository() {
    return outletRepository;
  }

  @Override
  protected BaseMapper<Outlet, OutletCreateRequest, OutletUpdateRequest, OutletResponse>
      getMapper() {
    return outletMapper;
  }

  @Override
  protected String getEntityName() {
    return outletEntityName;
  }

  // ==================== I. HOOK METHODS (SECURITY) ====================

  @Override
  protected void ensurePermission(Outlet entity) {
    UUID currentUserId = SecurityContext.getCurrentUserId();

    // Admin bypass mọi quyền sở hữu
    if (SecurityContext.isAdmin()) {
      return;
    }

    if (!entity.getOwner().getId().equals(currentUserId)) {
      throw new AccessDeniedException(
          "Bạn không có quyền thao tác Outlet" + " id: " + entity.getId());
    }
  }

  // ==================== II. VALIDATION ====================

  @Override
  protected void validateBeforeCreate(OutletCreateRequest request) {
    // Validate email format
    if (request.email() != null && !request.email().trim().isEmpty()) {
      if (!ValidationUtils.isValidEmail(request.email())) {
        throw new BadRequestException("Định dạng email không hợp lệ.");
      }
    }

    // Validate phone format
    if (request.phoneNumber() != null && !request.phoneNumber().trim().isEmpty()) {
      if (!ValidationUtils.isValidPhone(request.phoneNumber())) {
        throw new BadRequestException("Định dạng số điện thoại không hợp lệ. Vui lòng nhập số điện thoại Việt Nam (10-11 chữ số).");
      }
    }

    // Validate capacity
    if (request.capacity() != null && request.capacity() <= 0) {
      throw new BadRequestException("Sức chứa phải lớn hơn 0.");
    }

    // Validate price range format
    if (request.priceRange() != null && !request.priceRange().trim().isEmpty()) {
      if (!ValidationUtils.isValidPriceRange(request.priceRange())) {
        throw new BadRequestException("Định dạng khoảng giá không hợp lệ. Ví dụ: 100000-200000 hoặc 100k-200k.");
      }
    }
  }

  // ==================== III. GHI ĐÈ CRUD CỐT LÕI ====================

  @Override
  @Transactional
  public OutletResponse create(OutletCreateRequest request) {
    validateBeforeCreate(request);
    // 1. Lấy Owner từ SecurityContext
    UUID ownerId = SecurityContext.getCurrentUserId();
    UserAccount owner =
        userAccountRepository
            .findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

    // [NEW] Kiểm tra xem user đã là owner chưa
    boolean isAlreadyOwner = owner.getRole().getName().equalsIgnoreCase(RoleType.ROLE_OWNER.getName());
    boolean isAdmin = owner.getRole().getName().equalsIgnoreCase(RoleType.ROLE_SYSTEM_ADMIN.getName());

    // 2. Validate FK
    if (!districtRepository.existsById(request.districtId())) {
      throw new ResourceNotFoundException("District" + " id: " + request.districtId());
    }
    if (!outletTypeRepository.existsById(request.typeId())) {
      throw new ResourceNotFoundException("OutletType" + " id: " + request.typeId());
    }

    // 3. Mapping & FK Assignment
    Outlet entity = outletMapper.toEntity(request);
    entity.setOwner(owner);
    entity.setDistrict(districtRepository.getReferenceById(request.districtId()));
    entity.setType(outletTypeRepository.getReferenceById(request.typeId()));
    
    // 3.5. Set isActive dựa trên role của user
    // Nếu chưa là owner và không phải admin, tạo outlet với isActive = false (chờ admin duyệt)
    if (!isAlreadyOwner && !isAdmin) {
      entity.setActive(false);
    } else {
      // Nếu đã là owner hoặc admin, tạo outlet active bình thường
      entity.setActive(true);
    }

    // 3.5. Auto-fill coordinates from address if not provided
    if ((entity.getLatitude() == null || entity.getLongitude() == null) && 
        request.address() != null && !request.address().trim().isEmpty()) {
      try {
        com.foodgo.backend.module.maps.dto.response.GeocodeResultResponse geocodeResult = 
            googleMapsService.getCoordinatesFromAddress(request.address());
        if ("OK".equals(geocodeResult.getStatus()) && 
            geocodeResult.getLatitude() != null && geocodeResult.getLongitude() != null) {
          entity.setLatitude(geocodeResult.getLatitude());
          entity.setLongitude(geocodeResult.getLongitude());
          log.info("Auto-filled coordinates for outlet address: {} -> ({}, {})", 
              request.address(), geocodeResult.getLatitude(), geocodeResult.getLongitude());
        } else {
          log.warn("Could not auto-fill coordinates for address: {} - {}", 
              request.address(), geocodeResult.getMessage());
        }
      } catch (Exception e) {
        // Don't fail outlet creation if geocoding fails
        log.warn("Geocoding failed for address {}: {}", request.address(), e.getMessage());
      }
    }

    // 4. Save & Success Message
    Outlet savedEntity = outletRepository.save(entity);
    afterCreate(savedEntity);

    // 5. Nếu user chưa là owner, tạo OwnerRegistrationRequest
    if (!isAlreadyOwner && !isAdmin) {
      com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest registrationRequest =
          com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest.builder()
              .user(owner)
              .outlet(savedEntity)
              .status(com.foodgo.backend.module.owner.entity.OwnerRegistrationRequest.RequestStatus.PENDING)
              .build();
      ownerRegistrationRequestRepository.save(registrationRequest);
      log.info("Created owner registration request for user {} and outlet {}", ownerId, savedEntity.getId());
      
      SuccessMessageContext.setMessage(
          "Đã tạo outlet thành công. Yêu cầu đăng ký owner của bạn đang chờ Admin duyệt.");
    } else {
      SuccessMessageContext.setMessage(
          String.format(
              SuccessMessageContext.CREATE_SUCCESS, getEntityName(), savedEntity.getId().toString()));
    }

    return outletMapper.toResponse(savedEntity);
  }

  @Override
  @Transactional
  public OutletResponse update(UUID id, OutletUpdateRequest request) {
    Outlet entity = findByIdOrThrow(id);
    ensurePermission(entity); // Check quyền trước khi update

    request
        .optionalDistrictId()
        .ifPresent(
            districtId -> {
              if (!districtRepository.existsById(districtId)) {
                throw new ResourceNotFoundException("District" + " id: " + districtId);
              }
              entity.setDistrict(districtRepository.getReferenceById(districtId));
            });

    request
        .optionalTypeId()
        .ifPresent(
            typeId -> {
              if (!outletTypeRepository.existsById(typeId)) {
                throw new ResourceNotFoundException("OutletType" + " id: " + typeId);
              }
              entity.setType(outletTypeRepository.getReferenceById(typeId));
            });

    outletMapper.updateEntity(request, entity);
    
    // Auto-fill coordinates from address if address changed and coordinates not provided
    if (request.optionalAddress().isPresent() && 
        (entity.getLatitude() == null || entity.getLongitude() == null)) {
      String newAddress = request.optionalAddress().get();
      try {
        com.foodgo.backend.module.maps.dto.response.GeocodeResultResponse geocodeResult = 
            googleMapsService.getCoordinatesFromAddress(newAddress);
        if ("OK".equals(geocodeResult.getStatus()) && 
            geocodeResult.getLatitude() != null && geocodeResult.getLongitude() != null) {
          entity.setLatitude(geocodeResult.getLatitude());
          entity.setLongitude(geocodeResult.getLongitude());
          log.info("Auto-filled coordinates for updated outlet address: {} -> ({}, {})", 
              newAddress, geocodeResult.getLatitude(), geocodeResult.getLongitude());
        } else {
          log.warn("Could not auto-fill coordinates for address: {} - {}", 
              newAddress, geocodeResult.getMessage());
        }
      } catch (Exception e) {
        // Don't fail outlet update if geocoding fails
        log.warn("Geocoding failed for address {}: {}", newAddress, e.getMessage());
      }
    }
    
    Outlet updatedEntity = getRepository().save(entity);
    afterUpdate(updatedEntity);

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.UPDATE_SUCCESS, getEntityName(), id));

    return outletMapper.toResponse(updatedEntity);
  }

  // Override getDetail to allow public access to outlet details (no owner permission check)
  // and fetch join outletImages to avoid LazyInitializationException
  @Override
  @Transactional(readOnly = true)
  public OutletResponse getDetail(UUID id) {
    Specification<Outlet> specById = (root, query, cb) -> {
      // Fetch join để tránh LazyInitializationException
      if (query != null && Long.class != query.getResultType()) {
        root.fetch("outletImages", JoinType.LEFT);
        root.fetch("district", JoinType.LEFT);
        root.fetch("type", JoinType.LEFT);
        root.fetch("owner", JoinType.LEFT);
        query.distinct(true);
      }
      return cb.equal(root.get("id"), id);
    };
    
    Outlet entity = getSpecRepository().findAll(specById).stream()
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException(
            getEntityName() + " không tìm thấy với ID: " + id));

    SuccessMessageContext.setMessage(
        String.format(SuccessMessageContext.FETCH_DETAIL_SUCCESS, getEntityName(), id));

    return outletMapper.toResponse(entity);
  }

  // ==================== III. DISTANCE-BASED METHODS ====================

  @Override
  @Transactional(readOnly = true)
  public List<com.foodgo.backend.module.outlet.dto.response.OutletWithDistanceResponse> findNearbyOutlets(
      java.math.BigDecimal latitude,
      java.math.BigDecimal longitude,
      Double radiusKm,
      Integer maxResults) {
    
    if (latitude == null || longitude == null) {
      throw new BadRequestException("Vĩ độ và kinh độ là bắt buộc");
    }

    // Default values
    double searchRadius = radiusKm != null && radiusKm > 0 ? radiusKm : 10.0; // Default 10km
    int max = maxResults != null && maxResults > 0 ? maxResults : 50; // Default 50 results

    // Get all active outlets
    Specification<Outlet> spec = (root, query, cb) -> {
      List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();
      predicates.add(cb.equal(root.get("isActive"), true));
      predicates.add(cb.equal(root.get("isDeleted"), false));
      predicates.add(cb.isNotNull(root.get("latitude")));
      predicates.add(cb.isNotNull(root.get("longitude")));
      return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
    };

    List<Outlet> allOutlets = outletRepository.findAll(spec);

    // Calculate distance for each outlet and filter by radius
    List<com.foodgo.backend.module.outlet.dto.response.OutletWithDistanceResponse> results = 
        allOutlets.stream()
            .filter(outlet -> outlet.getLatitude() != null && outlet.getLongitude() != null)
            .map(outlet -> {
              double distanceKm = com.foodgo.backend.module.outlet.util.DistanceCalculator
                  .calculateDistanceKm(latitude, longitude, outlet.getLatitude(), outlet.getLongitude());
              
              // Only include outlets within radius
              if (distanceKm <= searchRadius) {
                return buildOutletWithDistance(outlet, latitude, longitude, distanceKm);
              }
              return null;
            })
            .filter(java.util.Objects::nonNull)
            .sorted((a, b) -> a.getDistanceKm().compareTo(b.getDistanceKm())) // Sort by distance
            .limit(max)
            .collect(java.util.stream.Collectors.toList());

    log.info("Found {} outlets within {} km of ({}, {})", 
        results.size(), searchRadius, latitude, longitude);

    return results;
  }

  private com.foodgo.backend.module.outlet.dto.response.OutletWithDistanceResponse buildOutletWithDistance(
      Outlet outlet,
      java.math.BigDecimal originLat,
      java.math.BigDecimal originLon,
      double distanceKm) {
    
    // Try to get travel time from Google Maps if available
    Integer durationSeconds = null;
    String durationText = null;
    
    if (googleMapsService != null && outlet.getLatitude() != null && outlet.getLongitude() != null) {
      try {
        com.foodgo.backend.module.maps.dto.request.DistanceRequest distanceRequest = 
            new com.foodgo.backend.module.maps.dto.request.DistanceRequest();
        distanceRequest.setOriginLatitude(originLat);
        distanceRequest.setOriginLongitude(originLon);
        distanceRequest.setDestinationLatitude(outlet.getLatitude());
        distanceRequest.setDestinationLongitude(outlet.getLongitude());
        distanceRequest.setMode("driving");
        distanceRequest.setLanguage("vi");
        distanceRequest.setUnit("metric");
        
        com.foodgo.backend.module.maps.dto.response.DistanceResultResponse distanceResult = 
            googleMapsService.calculateDistance(distanceRequest);
        
        if ("OK".equals(distanceResult.getStatus())) {
          durationSeconds = distanceResult.getDurationSeconds();
          durationText = distanceResult.getDurationText();
        }
      } catch (Exception e) {
        log.debug("Could not get travel time for outlet {}: {}", outlet.getId(), e.getMessage());
      }
    }

    int distanceMeters = com.foodgo.backend.module.outlet.util.DistanceCalculator
        .calculateDistanceMeters(originLat, originLon, outlet.getLatitude(), outlet.getLongitude());
    String distanceText = com.foodgo.backend.module.outlet.util.DistanceCalculator
        .formatDistance(distanceKm);

    return com.foodgo.backend.module.outlet.dto.response.OutletWithDistanceResponse.builder()
        .id(outlet.getId())
        .name(outlet.getName())
        .description(outlet.getDescription())
        .address(outlet.getAddress())
        .email(outlet.getEmail())
        .phoneNumber(outlet.getPhoneNumber())
        .website(outlet.getWebsite())
        .latitude(outlet.getLatitude())
        .longitude(outlet.getLongitude())
        .priceRange(outlet.getPriceRange())
        .capacity(outlet.getCapacity())
        .isActive(outlet.isActive())
        .averageRating(outlet.getAverageRating())
        .totalReviews(outlet.getTotalReviews())
        .distanceKm(java.math.BigDecimal.valueOf(distanceKm).setScale(2, java.math.RoundingMode.HALF_UP))
        .distanceMeters(distanceMeters)
        .distanceText(distanceText)
        .durationSeconds(durationSeconds)
        .durationText(durationText)
        .districtId(outlet.getDistrict() != null ? outlet.getDistrict().getId() : null)
        .districtName(outlet.getDistrict() != null ? outlet.getDistrict().getName() : null)
        .outletTypeId(outlet.getType() != null ? outlet.getType().getId() : null)
        .outletTypeName(outlet.getType() != null ? outlet.getType().getName() : null)
        .build();
  }

  // ==================== IV. SPECIFICATION ====================

  @Override
  protected Specification<Outlet> buildSpecification(OutletFilterRequest filterRequest) {
    return new OutletSearchSpecification(filterRequest);
  }

  // ==================== V. SEARCH ENHANCEMENTS ====================

  @Override
  @Transactional(readOnly = true)
  public List<com.foodgo.backend.module.outlet.controller.SearchController.SearchSuggestionResponse> getSearchSuggestions(
      String query, Integer limit) {
    if (query == null || query.trim().isBlank()) {
      return List.of();
    }

    OutletFilterRequest filter = new OutletFilterRequest(
        query.trim(), // name
        null, // districtId
        null, // outletTypeId
        null, // priceRange
        null, // featureIds
        null, // latitude
        null, // longitude
        null, // maxDistanceKm
        null, // distanceMode
        null, // page
        null  // size
    );

    Pageable pageable = PageRequest.of(0, limit != null ? limit : 5);
    Specification<Outlet> spec = new OutletSearchSpecification(filter);
    Page<Outlet> outlets = outletRepository.findAll(spec, pageable);

    return outlets.getContent().stream()
        .map(outlet -> new com.foodgo.backend.module.outlet.controller.SearchController.SearchSuggestionResponse(
            outlet.getName(),
            "outlet",
            outlet.getId().toString(),
            outlet.getDistrict() != null ? outlet.getDistrict().getName() : "",
            "restaurant"
        ))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public com.foodgo.backend.module.outlet.controller.SearchController.AutocompleteResponse getAutocompleteResults(
      String query, Integer limit) {
    if (query == null || query.trim().isBlank()) {
      return new com.foodgo.backend.module.outlet.controller.SearchController.AutocompleteResponse(
          List.of(), List.of(), List.of());
    }

    int effectiveLimit = limit != null && limit > 0 ? limit : 5;

    // Search outlets
    OutletFilterRequest filter = new OutletFilterRequest(
        query.trim(), // name
        null, // districtId
        null, // outletTypeId
        null, // priceRange
        null, // featureIds
        null, // latitude
        null, // longitude
        null, // maxDistanceKm
        null, // distanceMode
        null, // page
        null  // size
    );

    Pageable pageable = PageRequest.of(0, effectiveLimit);
    Page<Outlet> outlets = outletRepository.findAll(
        new OutletSearchSpecification(filter), pageable);

    List<com.foodgo.backend.module.outlet.controller.SearchController.SearchSuggestionResponse> outletSuggestions =
        outlets.getContent().stream()
            .map(outlet -> new com.foodgo.backend.module.outlet.controller.SearchController.SearchSuggestionResponse(
                outlet.getName(),
                "outlet",
                outlet.getId().toString(),
                (outlet.getDistrict() != null ? outlet.getDistrict().getName() : "") +
                    (outlet.getAddress() != null ? " • " + outlet.getAddress() : ""),
                "restaurant"
            ))
            .toList();

    // TODO: Add menu items and categories search when needed
    return new com.foodgo.backend.module.outlet.controller.SearchController.AutocompleteResponse(
        outletSuggestions,
        List.of(), // Menu items - to be implemented
        List.of()  // Categories - to be implemented
    );
  }

  // ==================== IV. PRIVATE HELPERS (BUG FIXED) ====================

  // Note: promoteToOwnerIfNecessary method đã được xóa
  // Logic này đã được chuyển sang OwnerRegistrationRequest flow trong AdminOwnerRegistrationServiceImpl

  @Override
  public List<OutletResponse> getOwnerOutlets() {
    UUID ownerId = SecurityContext.getCurrentUserId();

    List<Outlet> outlets = outletRepository.findAllByOwnerId(ownerId);

    if (outlets.isEmpty()) {
      throw new ResourceNotFoundException("Bạn chưa tạo quán nào. Vui lòng tạo quán của bạn.");
    }

    return outlets.stream().map(outletMapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<OutletResponse> getNewestOutlets(Integer limit) {
    int maxResults = limit != null && limit > 0 ? limit : 20;
    
    Specification<Outlet> spec = (root, query, cb) -> {
      List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();
      predicates.add(cb.equal(root.get("isActive"), true));
      predicates.add(cb.equal(root.get("isDeleted"), false));
      return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
    };
    
    Pageable pageable = PageRequest.of(0, maxResults, 
        org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    
    Page<Outlet> outlets = outletRepository.findAll(spec, pageable);
    
    return outlets.getContent().stream()
        .map(outletMapper::toResponse)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<OutletResponse> getPromotedOutlets(Integer limit) {
    int maxResults = limit != null && limit > 0 ? limit : 20;
    
    // Query outlets with active advertisements
    // SELECT DISTINCT o.* FROM outlet o
    // INNER JOIN advertisement a ON o.id = a.outlet_id
    // WHERE a.is_active = true 
    //   AND a.start_date <= CURRENT_DATE 
    //   AND a.end_date >= CURRENT_DATE
    //   AND o.is_active = true 
    //   AND o.is_deleted = false
    
    java.time.LocalDate today = java.time.LocalDate.now();
    
    Specification<Outlet> spec = (root, query, cb) -> {
      List<jakarta.persistence.criteria.Predicate> predicates = new java.util.ArrayList<>();
      
      // Join với advertisement table
      jakarta.persistence.criteria.Join<com.foodgo.backend.module.outlet.entity.Outlet, com.foodgo.backend.module.advertisement.entity.Advertisement> advertisementJoin = 
          root.join("advertisements", JoinType.INNER);
      
      // Filter: isActive = true, startDate <= today, endDate >= today
      predicates.add(cb.equal(advertisementJoin.get("isActive"), true));
      predicates.add(cb.lessThanOrEqualTo(advertisementJoin.get("startDate"), today));
      predicates.add(cb.greaterThanOrEqualTo(advertisementJoin.get("endDate"), today));
      
      // Outlet filters
      predicates.add(cb.equal(root.get("isActive"), true));
      predicates.add(cb.equal(root.get("isDeleted"), false));
      
      if (query != null) {
        query.distinct(true);
      }
      
      return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
    };
    
    Pageable pageable = PageRequest.of(0, maxResults);
    
    Page<Outlet> outlets = outletRepository.findAll(spec, pageable);
    
    return outlets.getContent().stream()
        .map(outletMapper::toResponse)
        .collect(java.util.stream.Collectors.toList());
  }
}

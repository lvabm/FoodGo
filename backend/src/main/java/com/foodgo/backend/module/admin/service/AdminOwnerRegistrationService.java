package com.foodgo.backend.module.admin.service;

import com.foodgo.backend.module.owner.dto.response.OwnerRegistrationRequestResponse;

import java.util.List;
import java.util.UUID;

public interface AdminOwnerRegistrationService {
  
  List<OwnerRegistrationRequestResponse> getAllPendingRequests();
  
  OwnerRegistrationRequestResponse getRequestDetail(UUID id);
  
  OwnerRegistrationRequestResponse approveRequest(UUID id, String adminNotes);
  
  OwnerRegistrationRequestResponse rejectRequest(UUID id, String adminNotes);
  
  List<OwnerRegistrationRequestResponse> getAllRequests();
}


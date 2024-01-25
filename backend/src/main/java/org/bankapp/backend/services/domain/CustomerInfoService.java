package org.bankapp.backend.services.domain;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.CustomerInfoPreviewGetDTO;
import org.bankapp.backend.entities.domain.CustomerInfo;
import org.bankapp.backend.exceptions.CustomerNotFoundException;
import org.bankapp.backend.repostiories.domain.CustomerInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;

    public CustomerInfoPreviewGetDTO getCustomerInfoPreview(String customerId) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return CustomerInfoPreviewGetDTO.builder()
                .fullName(customerInfo.getFirstName() + " "
                        + customerInfo.getSecondName() + " "
                        + customerInfo.getSurname())
                .email(customerInfo.getEmail())
                .build();
    }

    public String getPesel(String customerId) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return customerInfo.getPesel();
    }

    public String getIdentityDocumentNumber(String customerId) {
        CustomerInfo customerInfo = customerInfoRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return customerInfo.getIdentityDocumentNumber();
    }
}

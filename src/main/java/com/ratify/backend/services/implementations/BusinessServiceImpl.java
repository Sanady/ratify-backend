package com.ratify.backend.services.implementations;

import com.ratify.backend.configs.UtilityFunctions;
import com.ratify.backend.error_handlers.exceptions.ForbiddenActionException;
import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.error_handlers.exceptions.ItemAlreadyExistsException;
import com.ratify.backend.error_handlers.exceptions.NotFoundException;
import com.ratify.backend.models.Address;
import com.ratify.backend.models.Business;
import com.ratify.backend.models.User;
import com.ratify.backend.models.enums.EBusiness;
import com.ratify.backend.payloads.requests.CreateBusinessRequest;
import com.ratify.backend.payloads.requests.SetBusinessStatusRequest;
import com.ratify.backend.payloads.responses.GetBusinessResponse;
import com.ratify.backend.payloads.responses.GetBusinessOwnerResponse;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.BusinessRepository;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.security.JwtUtils;
import com.ratify.backend.services.interfaces.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_004;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_005;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_006;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_007;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_001;

@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public BusinessServiceImpl(BusinessRepository businessRepository,
                               UserRepository userRepository,
                               JwtUtils jwtUtils) {
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<Object> createBusiness(CreateBusinessRequest request, String token) {
        String normalizedName = UtilityFunctions.normalizedName(request.getName());
        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token.substring(7)))
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(Boolean.TRUE.equals(businessRepository.existsByNormalizedName(normalizedName))) {
            throw new ItemAlreadyExistsException(ERROR_BUSINESS_001.getCode());
        }

        if(request.getAddresses().isEmpty() || request.getAddresses() == null) {
            throw new InvalidInputException(ERROR_BUSINESS_006.getCode());
        }

        List<EBusiness> businessTypeList = new ArrayList<>();
        if(request.getBusinessType() == null) {
            throw new InvalidInputException(ERROR_BUSINESS_007.getCode());
        } else {
            request.getBusinessType().forEach(type -> {
                switch (type) {
                    case "nightclub":
                        businessTypeList.add(EBusiness.NIGHTCLUB);
                        break;

                    case "bar":
                        businessTypeList.add(EBusiness.BAR);
                        break;

                    case "coffee shop":
                        businessTypeList.add(EBusiness.COFFEE_SHOP);
                        break;

                    case "restaurant":
                        businessTypeList.add(EBusiness.RESTAURANT);
                        break;

                    case "street food":
                        businessTypeList.add(EBusiness.STREET_FOOD);
                        break;

                    case "it firm":
                        businessTypeList.add(EBusiness.IT_FIRM);
                        break;

                    case "consulting":
                        businessTypeList.add(EBusiness.CONSULTING);
                        break;

                    default:
                        throw new InvalidInputException(ERROR_BUSINESS_002.getCode());
                }
            });
        }

        for(Address address : request.getAddresses()) {
            UtilityFunctions.normalizedAddress(address);
            if(businessRepository.countAddressesByNormalizedAddress(address.getNormalizedAddress()) > 0) {
                throw new ItemAlreadyExistsException(ERROR_BUSINESS_001.getCode());
            }
        }

        Business business = new Business(
                request.getName(),
                normalizedName,
                user.getNormalizedUsername(),
                businessTypeList,
                request.getAddresses(),
                new Date(),
                true
        );
        businessRepository.save(business);
        log.info("Business has been created with name {}, date {}", business.getName(), business.getCreatedAt());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(business);
    }

    @Override
    public ResponseEntity<Object> getBusiness(String name) {
        Business business = businessRepository.findByNormalizedName(name.toUpperCase())
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        User user = userRepository.findByNormalizedUsername(business.getNormalizedUsername())
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        GetBusinessOwnerResponse businessOwnerResponse = new GetBusinessOwnerResponse(user.getUsername(),
                user.getEmail());

        log.info("Get business with name {}", name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new GetBusinessResponse(
                        business.getName(),
                        business.getCreatedAt(),
                        business.getUpdatedAt(),
                        business.getBusinessType(),
                        business.getAddresses(),
                        businessOwnerResponse
                ));
    }

    @Override
    public ResponseEntity<Object> setBusinessStatus(String name, SetBusinessStatusRequest request, String token) {
        User user = userRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token.substring(7)))
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        Business business = businessRepository.findByNormalizedName(name.toUpperCase())
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        if(!Objects.equals(business.getNormalizedUsername(), user.getNormalizedUsername())) {
            throw new ForbiddenActionException(ERROR_BUSINESS_004.getCode());
        }

        if(business.getActive().equals(request.getActive())) {
            throw new ItemAlreadyExistsException(ERROR_BUSINESS_005.getCode());
        }

        business.setActive(request.getActive());
        business.setUpdatedAt(new Date());
        businessRepository.save(business);
        log.info("Business status has been changed for business {} with status {}", business.getName(),
                Boolean.TRUE.equals(request.getActive()) ? "OPEN" : "CLOSE");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Business has been set to " + (Boolean.TRUE.equals(request.getActive()) ? "active" : "inactive")));
    }

    @Override
    public ResponseEntity<Object> deleteBusiness(String name) {
        Business business = businessRepository.findByNormalizedName(name.toUpperCase())
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        businessRepository.deleteById(business.getId());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new MessageResponse("Business with name " + name + " has been deleted!"));
    }
}

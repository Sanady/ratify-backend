package com.ratify.backend.services.implementations;

import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.error_handlers.exceptions.ItemAlreadyExistsException;
import com.ratify.backend.error_handlers.exceptions.NotFoundException;
import com.ratify.backend.models.Business;
import com.ratify.backend.models.Rate;
import com.ratify.backend.models.User;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.BusinessRepository;
import com.ratify.backend.repositories.RateRepository;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.services.interfaces.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_009;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_001;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BusinessRepository businessRepository;

    @Autowired
    RateRepository rateRepository;

    @Override
    public ResponseEntity<Object> createRate(String username, String normalizedBusinessName, Integer estimate, String comment) {
        Business business = businessRepository.findByNormalizedName(normalizedBusinessName.toUpperCase())
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(normalizedBusinessName.isEmpty() || username.isEmpty())
            throw new InvalidInputException(ERROR_BUSINESS_009.getCode());
        else if(estimate < 1 || estimate > 5)
            throw new InvalidInputException(ERROR_RATE_001.getCode());
        else if(comment.length() <= 8 || comment.length() >= 1024)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(rateRepository.existsByUsernameAndBusinessNormalizedName(user.getUsername(), business.getNormalizedName()))
            throw new ItemAlreadyExistsException(ERROR_RATE_003.getCode());

        Rate rate = new Rate(
                user.getUsername(),
                business.getNormalizedName(),
                comment,
                estimate
        );

        rateRepository.save(rate);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("You have successfully rated business!"));
    }
}

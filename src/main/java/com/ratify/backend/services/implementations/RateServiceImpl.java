package com.ratify.backend.services.implementations;

import com.ratify.backend.error_handlers.exceptions.InvalidInputException;
import com.ratify.backend.error_handlers.exceptions.ItemAlreadyExistsException;
import com.ratify.backend.error_handlers.exceptions.NotFoundException;
import com.ratify.backend.models.BenefitRate;
import com.ratify.backend.models.Business;
import com.ratify.backend.models.InterviewRate;
import com.ratify.backend.models.Rate;
import com.ratify.backend.models.ReviewRate;
import com.ratify.backend.models.User;
import com.ratify.backend.models.enums.ERateType;
import com.ratify.backend.payloads.requests.BenefitRateRequest;
import com.ratify.backend.payloads.requests.InterviewRateRequest;
import com.ratify.backend.payloads.requests.ReviewRateRequest;
import com.ratify.backend.payloads.responses.MessageResponse;
import com.ratify.backend.repositories.BusinessRepository;
import com.ratify.backend.repositories.RateRepository;
import com.ratify.backend.repositories.UserRepository;
import com.ratify.backend.services.interfaces.RateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_BUSINESS_009;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_001;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_002;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_003;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_RATE_004;
import static com.ratify.backend.constants.ErrorsEnum.ERROR_USER_001;

@Service
public class RateServiceImpl implements RateService {
    private static final String SUCCESS = "You have successfully rated business!";

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final RateRepository rateRepository;

    public RateServiceImpl(UserRepository userRepository,
                           BusinessRepository businessRepository,
                           RateRepository rateRepository) {
        this.userRepository = userRepository;
        this.businessRepository = businessRepository;
        this.rateRepository = rateRepository;
    }

    @Override
    public ResponseEntity<Object> createBenefitRate(BenefitRateRequest benefitRateRequest) {
        Business business = businessRepository.findByNormalizedName(benefitRateRequest.getBusinessName().toUpperCase().replaceAll("\\s", ""))
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        User user = userRepository.findByUsername(benefitRateRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(benefitRateRequest.getBusinessName().isEmpty() || benefitRateRequest.getUsername().isEmpty())
            throw new InvalidInputException(ERROR_BUSINESS_009.getCode());
        else if(benefitRateRequest.getEstimate() < 1 || benefitRateRequest.getEstimate() > 5)
            throw new InvalidInputException(ERROR_RATE_001.getCode());
        else if(benefitRateRequest.getComment().length() <= 32 || benefitRateRequest.getComment().length() >= 1024)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(rateRepository.existsByUsernameAndBusinessNormalizedNameAndType(user.getUsername(),
                business.getNormalizedName(),
                ERateType.BENEFITS.getType()))
            throw new ItemAlreadyExistsException(ERROR_RATE_003.getCode());

        Rate rate = new Rate(user.getUsername(),
                business.getNormalizedName(),
                benefitRateRequest.getEstimate(),
                ERateType.BENEFITS.getType());

        BenefitRate benefitRate = new BenefitRate(rate,
                benefitRateRequest.getComment());

        rateRepository.save(benefitRate);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(SUCCESS));
    }

    @Override
    public ResponseEntity<Object> createInterviewRate(InterviewRateRequest interviewRateRequest) {
        Business business = businessRepository.findByNormalizedName(interviewRateRequest.getBusinessName().toUpperCase().replaceAll("\\s", ""))
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        User user = userRepository.findByUsername(interviewRateRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(interviewRateRequest.getBusinessName().isEmpty() || interviewRateRequest.getUsername().isEmpty())
            throw new InvalidInputException(ERROR_BUSINESS_009.getCode());
        else if(interviewRateRequest.getEstimate() < 1 || interviewRateRequest.getEstimate() > 5)
            throw new InvalidInputException(ERROR_RATE_001.getCode());
        else if(interviewRateRequest.getApplication().length() <= 32 || interviewRateRequest.getApplication().length() >= 2048)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(interviewRateRequest.getInterview().length() <= 32 || interviewRateRequest.getInterview().length() >= 2048)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(interviewRateRequest.getInterviewQuestions().length() <= 32 || interviewRateRequest.getInterviewQuestions().length() >= 2048)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(rateRepository.existsByUsernameAndBusinessNormalizedNameAndType(user.getUsername(),
                business.getNormalizedName(),
                ERateType.INTERVIEWS.getType()))
            throw new ItemAlreadyExistsException(ERROR_RATE_003.getCode());

        Rate rate = new Rate(user.getUsername(),
                business.getNormalizedName(),
                interviewRateRequest.getEstimate(),
                ERateType.INTERVIEWS.getType());

        InterviewRate interviewRate = new InterviewRate(rate,
                interviewRateRequest.getNoOffer(),
                interviewRateRequest.getPositiveExperience(),
                interviewRateRequest.getApplication(),
                interviewRateRequest.getInterview(),
                interviewRateRequest.getInterviewQuestions());

        rateRepository.save(interviewRate);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(SUCCESS));
    }

    @Override
    public ResponseEntity<Object> createReviewRate(ReviewRateRequest reviewRateRequest) {
        Business business = businessRepository.findByNormalizedName(reviewRateRequest.getBusinessName().toUpperCase().replaceAll("\\s", ""))
                .orElseThrow(() -> new NotFoundException(ERROR_BUSINESS_003.getCode()));

        User user = userRepository.findByUsername(reviewRateRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(ERROR_USER_001.getCode()));

        if(reviewRateRequest.getBusinessName().isEmpty() || reviewRateRequest.getUsername().isEmpty())
            throw new InvalidInputException(ERROR_BUSINESS_009.getCode());
        else if(reviewRateRequest.getEstimate() < 1 || reviewRateRequest.getEstimate() > 5)
            throw new InvalidInputException(ERROR_RATE_001.getCode());
        else if(reviewRateRequest.getPros().length() <= 16 || reviewRateRequest.getPros().length() >= 2048)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(reviewRateRequest.getCons().length() <= 16 || reviewRateRequest.getCons().length() >= 2048)
            throw new InvalidInputException(ERROR_RATE_002.getCode());
        else if(rateRepository.existsByUsernameAndBusinessNormalizedNameAndType(user.getUsername(),
                business.getNormalizedName(),
                ERateType.REVIEWS.getType()))
            throw new ItemAlreadyExistsException(ERROR_RATE_003.getCode());

        Rate rate = new Rate(user.getUsername(),
                business.getNormalizedName(),
                reviewRateRequest.getEstimate(),
                ERateType.REVIEWS.getType());

        ReviewRate reviewRate = new ReviewRate(rate,
                reviewRateRequest.getRecommended(),
                reviewRateRequest.getBusinessOutlook(),
                reviewRateRequest.getPros(),
                reviewRateRequest.getCons());

        rateRepository.save(reviewRate);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(SUCCESS));
    }

    @Override
    public ResponseEntity<Object> getAllReviewsByType(String type, int page, int size) {
        List<Object> rates;
        Page<Object> pageRates;
        Pageable pageable = PageRequest.of(page, size);

        if(Objects.equals(type, ERateType.BENEFITS.getType())) {
            pageRates = rateRepository.findAllByType(ERateType.BENEFITS.getType(), pageable);
        } else if(Objects.equals(type, ERateType.INTERVIEWS.getType())) {
            pageRates = rateRepository.findAllByType(ERateType.INTERVIEWS.getType(), pageable);
        } else if(Objects.equals(type, ERateType.REVIEWS.getType())) {
            pageRates = rateRepository.findAllByType(ERateType.REVIEWS.getType(), pageable);
        } else {
            throw new InvalidInputException(ERROR_RATE_004.getCode());
        }

        rates = pageRates.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("content", rates);
        response.put("currentPage", pageRates.getNumber());
        response.put("totalItems", pageRates.getTotalElements());
        response.put("totalPages", pageRates.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}

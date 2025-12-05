package com.liaw.dev.Conference.mapper;

import com.liaw.dev.Conference.dto.ConferenceDTO;
import com.liaw.dev.Conference.entity.Conference;
import com.liaw.dev.Conference.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConferenceMapper {

    private final UserMapper userMapper;
    private final PaymentMapper paymentMapper;

    public ConferenceDTO toDTO(Conference conference){

        return new ConferenceDTO(
                conference.getId(),
                conference.getName(),
                conference.getState(),
                conference.getCity(),
                conference.getLocal(),
                conference.getStartDate(),
                conference.getEndDate(),
                conference.getMinimumAge(),
                conference.getCode(),
                conference.getRegistrationFee(),
                paymentMapper.toPaymentDTOList(conference.getPayments()),
                userMapper.toUserResponseList(conference.getUsers()),
                conference.getHousing()
        );
    }

    public Conference toEntity(ConferenceDTO conference){
        return new Conference(
                conference.getId(),
                conference.getName(),
                conference.getState(),
                conference.getCity(),
                conference.getLocal(),
                conference.getStartDate(),
                conference.getEndDate(),
                conference.getMinimumAge(),
                conference.getCode(),
                conference.getRegistrationFee(),
                conference.getPayments(),
                conference.getUsers(),
                conference.getHousing()
        );
    }

}

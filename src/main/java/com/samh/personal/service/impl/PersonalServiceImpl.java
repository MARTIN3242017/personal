package com.samh.personal.service.impl;

import com.samh.personal.mapper.PersonaMapper;
import com.samh.personal.service.PersonalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class PersonalServiceImpl implements PersonalService {
    private PersonaMapper personaMapper;

}

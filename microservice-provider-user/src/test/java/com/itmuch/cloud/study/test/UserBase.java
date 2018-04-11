package com.itmuch.cloud.study.test;

import com.itmuch.cloud.study.controller.UserController;
import com.itmuch.cloud.study.domain.User;
import com.itmuch.cloud.study.repository.UserRepository;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public  abstract class UserBase {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserController producerController;

  @Before
  public void setup() {
    when(userRepository.getOne(anyLong())).
            thenReturn(new User());

    RestAssuredMockMvc.standaloneSetup(producerController);
  }

}
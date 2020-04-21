package com.sutpc.security.web.rest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**.
 * @Author:wusx
 * @Date: Created in 10:44 2020/4/21 0021.
 * @Description .
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void hello() {
    ResponseEntity<String> forEntity = restTemplate.getForEntity("/hello", String.class);
    Assert.assertEquals(forEntity.getStatusCodeValue(), 401);
  }

  @Test
  public void me() {
  }

}
package jpabook.jpashop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jpabook.jpashop.api.OrderApiController.OrderDto;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderApiControllerTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    //json으로 개체를 받기위해서는 엔티티 수정이 필요하여 생략.
    //엔티티가 응답값에 의존하기 떄문에
    void orderGetApiV1Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void orderGetApiV2Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v2/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrderDto> orderDtos = objectMapper.readValue(json, new TypeReference<List<OrderDto>>() {
        });

        for (OrderDto orderDto : orderDtos) {
            System.out.println(orderDto.toString());
        }

    }

    @Test
    void orderGetApiV3Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v3/orders")
                        .param("offset", "0")
                        .param("limit", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrderDto> orderDtos = objectMapper.readValue(json, new TypeReference<List<OrderDto>>() {
        });

        for (OrderDto orderDto : orderDtos) {
            System.out.println(orderDto.toString());
        }

    }

    @Test
    void orderGetApiV4Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v4/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrderQueryDto> orderDtos = objectMapper.readValue(json, new TypeReference<List<OrderQueryDto>>() {
        });

        for (OrderQueryDto orderQueryDto : orderDtos) {
            System.out.println(orderQueryDto.toString());
        }

    }

    @Test
    void orderGetApiV5Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v5/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        List<OrderQueryDto> orderDtos = objectMapper.readValue(json, new TypeReference<List<OrderQueryDto>>() {
        });

        for (OrderQueryDto orderQueryDto : orderDtos) {
            System.out.println(orderQueryDto.toString());
        }

    }




}
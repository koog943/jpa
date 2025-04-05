package jpabook.jpashop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jpabook.jpashop.api.MemberApiController.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberApiControllerTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void memberGetAPIV1Test() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        //타입을 모를경우
        //List<Member> members = objectMapper.readValue(json, new TypeReference<>(){});

        //타입을 알경우
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Member.class);
        List<Member> members = objectMapper.readValue(json, collectionType);

        assertThat(memberRepository.findAll().size()).isEqualTo(members.size());
    }

    @Test
    void memberGetAPIV2Test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v2/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        //objectMapper.readvalue()는 빈생성자를 만든후 setter를 통해 값을 주입한다. 그래서 빈 생성자가 무조건 필수로 있어야한다
        List<MemberDto> members = objectMapper.readValue(json, new TypeReference<Result<List<MemberDto>>>() {}).getData();

        assertThat(memberRepository.findAll().size()).isEqualTo(members.size());
    }

    @Test
    void memberPostAPIV1Test() throws Exception {
        int beforeSize = memberRepository.findAll().size();

        Member member = new Member();
        member.setName("member1");

        String request = objectMapper.writeValueAsString(member);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(beforeSize + 1).isEqualTo(memberRepository.findAll().size());
    }

    @Test
    void memberPostAPIV2Test() throws Exception {
        int beforeSize = memberRepository.findAll().size();

        CreateMemberRequest memberRequest = new CreateMemberRequest();
        memberRequest.setName("member1");

        String request = objectMapper.writeValueAsString(memberRequest);

        MvcResult mvcResult = mockMvc.perform(post("/api/v2/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        assertThat(beforeSize + 1).isEqualTo(memberRepository.findAll().size());
    }

    @Test
    void memberPutAPIV2Test() throws Exception {
        UpdateMemberRequest updateMemberRequest = new UpdateMemberRequest();
        updateMemberRequest.setName("updataName1");

        String request = objectMapper.writeValueAsString(updateMemberRequest);

        MvcResult mvcResult = mockMvc.perform(put("/api/v2/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        UpdateMemberResponse updateMemberResponse = objectMapper.readValue(json, UpdateMemberResponse.class);

        Member find = memberRepository.findOne(1L);
        assertThat(find.getId()).isEqualTo(updateMemberResponse.getId());
        assertThat(find.getName()).isEqualTo(updateMemberResponse.getName());
    }



}
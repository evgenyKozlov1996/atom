package ru.atom.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.atom.chat.models.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
public class ChatClientTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ChatController())
            .build();
    }

    private static final Logger log = LoggerFactory.getLogger(ChatClientTest.class);

    @Test
    public void register() throws Exception {
        mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new User("user1", "user1")))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Ignore
    @Test
    public void login() throws Exception {
        mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new User("user1", "user1")))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new User("user1", "user1")))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void viewOnline() throws Exception {
        mockMvc.perform(get("/online")).andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

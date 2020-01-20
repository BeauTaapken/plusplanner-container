package plus.planner.containerservice;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import plus.planner.containerservice.model.Part;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class PlusplannerapiApplicationTests {

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    private final Part part = new Part("1","1","test","2020","test","1");

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        mockMvc.perform(MockMvcRequestBuilders.post("/part/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(part))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void createPartCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/part/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(part))
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(200));
    }

//    @Test
//    public void readPartCorrectly() throws Exception {
//        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.get("/part/read/1")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200));
//
//        MvcResult result = action.andReturn();
//
//        String expected = "[{\"subpartid\":\"1\",\"partid\":\"1\",\"subpartname\":\"test\",\"description\":\"testdes\",\"state\":\"DONE\",\"enddate\":\"2020-10-10\"}]";
//
//        Assert.assertEquals(expected, result.getResponse().getContentAsString());
//    }

    @Test
    public void updatePartCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/part/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(part))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    public void deletePartCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/part/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }
}

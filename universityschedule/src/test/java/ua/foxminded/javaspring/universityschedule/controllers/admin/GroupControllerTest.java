package ua.foxminded.javaspring.universityschedule.controllers.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.entities.Group;
import ua.foxminded.javaspring.universityschedule.services.GroupService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
public class GroupControllerTest {

    @MockBean
    private GroupService groupService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void addGroup_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addGroup"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void addGroup_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/addGroup"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void saveGroup_shouldRedirectToHomeView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/addGroup")
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void getGroup_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/groups"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void getGroup_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/groups"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void getGroup_shouldReturnGroupsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/list/groups"))
                .andExpect(model().attributeExists("groups"));
    }

    @Test
    @WithAnonymousUser
    public void editGroup_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editGroup/{id}", 1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"TEACHER", "STUDENT"})
    public void editGroup_shouldNotAllowAccessForAuthenticatedIfNotAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/editGroup/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void editGroup_shouldReturnEditGroupView() throws Exception {
        Group group = new Group();
        when(groupService.findById(1)).thenReturn(group);
        mvc.perform(MockMvcRequestBuilders.get("/admin/editGroup/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit/group"))
                .andExpect(model().attributeExists("group"));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void postEditGroup_shouldRedirectToGroupsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/editGroup/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void deleteGroup_shouldRedirectToGroupsView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/deleteGroup/{id}", 1L)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}

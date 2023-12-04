package ua.foxminded.javaspring.universityschedule.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ua.foxminded.javaspring.universityschedule.dto.PasswordDTO;
import ua.foxminded.javaspring.universityschedule.dto.TeacherDTO;
import ua.foxminded.javaspring.universityschedule.entities.Role;
import ua.foxminded.javaspring.universityschedule.entities.Student;
import ua.foxminded.javaspring.universityschedule.entities.Teacher;
import ua.foxminded.javaspring.universityschedule.mapper.StudentMapperImpl;
import ua.foxminded.javaspring.universityschedule.mapper.TeacherMapper;
import ua.foxminded.javaspring.universityschedule.mapper.TeacherMapperImpl;
import ua.foxminded.javaspring.universityschedule.services.CourseService;
import ua.foxminded.javaspring.universityschedule.services.StudentService;
import ua.foxminded.javaspring.universityschedule.services.TeacherService;
import ua.foxminded.javaspring.universityschedule.services.UserService;
import ua.foxminded.javaspring.universityschedule.utils.CustomUserDetails;

import java.nio.CharBuffer;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@Import({TeacherMapperImpl.class, StudentMapperImpl.class})
@MockBean(StudentService.class)
@MockBean(TeacherService.class)
@MockBean(CourseService.class)
@MockBean(UserService.class)
public class ProfileControllerTest {

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void profile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void profile_shouldReturnViewProfile() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/profile")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithAnonymousUser
    public void editProfile_shouldNotAllowAccessForUnauthorizedUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                                          .with(user(new CustomUserDetails(teacher))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    public void editProfile_shouldReturnViewEditProfileStudent() throws Exception {
        Student student = new Student();
        student.addRole(Role.STUDENT);
        mvc.perform(MockMvcRequestBuilders.get("/profile/edit")
                                          .with(user(new CustomUserDetails(student))))
                .andExpect(status().isOk())
                .andExpect(view().name("/editProfile"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    public void postEditProfile_shouldRedirectToAdminProfileViewIfUserIsAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.post("/profile/edit")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/editAdminProfile"));
    }

    @Test
    public void postEditProfile_shouldRedirectToUserProfileViewIfUserNotAdmin() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        mvc.perform(MockMvcRequestBuilders.post("/profile/edit")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/editUserProfile"));
    }

    @Test
    public void editAdminProfile_shouldRedirectToEditProfileView_whenRequestParametersNotValid() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/profile/editAdminProfile")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .flashAttr("userDTO", new TeacherDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/edit"));
    }

    @Test
    public void editAdminProfile_shouldRedirectToProfileView() throws Exception {
        Teacher teacher = Teacher.builder()
                                 .firstName("test")
                                 .lastName("test")
                                 .email("test@gmail.com")
                                 .build();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/profile/editAdminProfile")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .flashAttr("userDTO", teacherMapper.teacherToDTO(teacher)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"));
    }

    @Test
    public void editUserProfile_shouldRedirectToEditProfileView_whenRequestParametersNotValid() throws Exception {
        Teacher teacher = new Teacher();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        mvc.perform(MockMvcRequestBuilders.get("/profile/editUserProfile")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .flashAttr("userDTO", new TeacherDTO()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/edit"));
    }

    @Test
    public void editUserProfile_shouldRedirectToProfileView() throws Exception {
        Teacher teacher = Teacher.builder()
                                 .firstName("test")
                                 .lastName("test")
                                 .email("test@gmail.com")
                                 .build();
        teacher.addRole(Role.TEACHER);
        teacher.addRole(Role.ADMIN);
        TeacherDTO dto = teacherMapper.teacherToDTO(teacher);
        dto.setCourses(null);
        mvc.perform(MockMvcRequestBuilders.get("/profile/editUserProfile")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .flashAttr("userDTO", dto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"));
    }

    @Test
    public void editPassword_shouldReturnEditPasswordView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/profile/editPassword")
                                          .with(user(new CustomUserDetails(new Teacher()))))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("passwordDTO"))
                .andExpect(view().name("/editPassword"));
    }

    @Test
    public void postEditPassword_shouldRedirectToEditPasswordView_whenRequestParametersNotValid() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/profile/editPassword")
                                          .with(user(new CustomUserDetails(new Teacher())))
                                          .flashAttr("passwordDTO", new PasswordDTO())
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile/editPassword"));
    }

    @Test
    public void postEditPassword_shouldRedirectToProfileView() throws Exception {
        PasswordDTO dto = new PasswordDTO();
        dto.setCurrentPassword("test".toCharArray());
        dto.setNewPassword("test".toCharArray());
        dto.setPasswordConfirmation("test".toCharArray());

        Teacher teacher = new Teacher();
        when(encoder.matches(CharBuffer.wrap(dto.getCurrentPassword()), teacher.getPassword())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.post("/profile/editPassword")
                                          .with(user(new CustomUserDetails(teacher)))
                                          .flashAttr("passwordDTO", dto)
                                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/profile"));
    }
}

package com.andr.model.servlet;

import org.junit.Test;

import com.andr.model.User;
import com.andr.model.store.HbmStore;
import com.andr.model.store.MemStore;
import com.andr.model.store.Store;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PowerMockIgnore({"org.hibernate.*", "com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(HbmStore.class)
public class UserServletTest {
    private static final Logger LOG = LoggerFactory.getLogger(
            com.andr.model.servlet.UserServletTest.class.getName());
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Store store;
    private File source;
    private User user;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletOutputStream out;
    private BufferedReader in;

    @Before
    public void init() throws IOException {
        store = MemStore.instOf();
        source = folder.newFile("source.txt");
        user = User.of("user", "login", "password", "+1234");
        store.save(user);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        out = mock(ServletOutputStream.class);
        in = new BufferedReader(new FileReader(source));
    }

    @After
    public void clear() {
        store.delete(user);
    }

    @Test
    public void whenLoginThenSuccess() throws ServletException, IOException {
        User inputUser = User.of("name", "login", "password", "38123");
        String jsonUser = new GsonBuilder().create().toJson(inputUser);
        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonUser);
        }
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("login");
        when(request.getReader()).thenReturn(in);
        when(response.getOutputStream()).thenReturn(out);
        when(request.getSession()).thenReturn(session);
        new UserServlet().doPost(request, response);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIllegalAction() throws ServletException, IOException {
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("someAction");
        when(response.getOutputStream()).thenReturn(out);
        new UserServlet().doPost(request, response);
    }

    @Test
    public void whenRegistrationThenSuccess() throws IOException, ServletException {
        User inputUser = User.of("name", "newLogin",
                "a123", "+38123");
        String jsonUser = new GsonBuilder().create().toJson(inputUser);
        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonUser);
        }
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("registration");
        when(request.getReader()).thenReturn(in);
        when(request.getSession()).thenReturn(session);
        when(response.getOutputStream()).thenReturn(out);
        new UserServlet().doPost(request, response);
        assertNotNull(store.findUserByLogin(inputUser.getLogin()));
    }

    @Test(expected = IllegalStateException.class)
    public void whenRegistrationAndLoginIsBusyThenError() throws IOException, ServletException {
        User inputUser = User.of("name",
                "login", "a123", "+38123");
        String jsonUser = new GsonBuilder().create().toJson(inputUser);
        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonUser);
        }
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("registration");
        when(request.getReader()).thenReturn(in);
        when(response.getOutputStream()).thenReturn(out);
        when(request.getSession()).thenReturn(session);
        new UserServlet().doPost(request, response);
    }

    @Test(expected = IllegalStateException.class)
    public void whenLoginAndWrongtLoginThenError() throws ServletException, IOException {
        User inputUser = User.of("name", "log", "a123", "+38123");
        String jsonUser = new GsonBuilder().create().toJson(inputUser);
        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonUser);
        }
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("login");
        when(request.getReader()).thenReturn(in);
        when(response.getOutputStream()).thenReturn(out);
        when(request.getSession()).thenReturn(session);

        new UserServlet().doPost(request, response);
    }

    @Test(expected = IllegalStateException.class)
    public void whenLoginAndWrongPasswordThenError() throws ServletException, IOException {
        User inputUser = User.of("name", "login", "a123", "+38123");
        String jsonUser = new GsonBuilder().create().toJson(inputUser);
        try (PrintWriter out = new PrintWriter(source)) {
            out.write(jsonUser);
        }
        PowerMockito.mockStatic(HbmStore.class);
        Mockito.when(HbmStore.instOf()).thenReturn(store);
        when(request.getParameter("action")).thenReturn("login");
        when(request.getReader()).thenReturn(in);
        when(response.getOutputStream()).thenReturn(out);
        when(request.getSession()).thenReturn(session);
        new UserServlet().doPost(request, response);
    }

}
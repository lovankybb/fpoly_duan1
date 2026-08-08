package com.fptpolytechnic.duan1.controller;

import com.fptpolytechnic.duan1.enums.MessageStatus;
import com.fptpolytechnic.duan1.model.Message;
import com.fptpolytechnic.duan1.repository.MessageRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet({
        "/admin/messages",
        "/messages",
        "/admin/message/detail",
        "/admin/message/delete"
})
public class MessageServlet extends HttpServlet {


    private final MessageRepository messageRepository;

    public MessageServlet() {
        this.messageRepository = new MessageRepository();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case "/admin/messages" -> responseMsgManagement(req, resp);
            case "/admin/message/detail" -> responseDetailMsg(req, resp);
            case "/admin/message/delete" -> handleDeleteMessage(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        switch (path) {
            case "/messages" -> handleAddMessage(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
        }
    }


    private void handleDeleteMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
        }

        if (!messageRepository.delete(Long.parseLong(id))) {
            resp.sendRedirect(req.getContextPath() + "/admin/messages?alertMsg=DELETE_FAILED");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/messages?alertMsg=DELETE_SUCCESS");

    }

    private void responseMsgManagement(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String offset = req.getParameter("offset");
        if (offset == null || offset.trim().isEmpty()) {
            offset = "0";
        }

        String alert = req.getParameter("alertMsg");
        if (alert != null && !alert.trim().isEmpty()) {
            req.setAttribute("alertMsg", alert);
        }

        List<Message> messages = messageRepository.findAll(Integer.parseInt(offset), 10);

        req.setAttribute("offset", offset);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher("/views/admin/msg-management.jsp").forward(req, resp);
    }

    private void handleAddMessage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sender = req.getParameter("sender");
        String email = req.getParameter("email");
        String title = req.getParameter("title");
        String content = req.getParameter("message");

        Map<String, String> errors = this.validate(sender, email, title, content);
        StringBuilder url = new StringBuilder("/contact?");
        if (!errors.isEmpty()) {
            errors.forEach((key, value) -> url.append(key).append("=").append(value).append("&"));
        }

        Message message = Message.builder()
                .sender(sender)
                .email(email)
                .status(MessageStatus.UNREAD)
                .message(content)
                .title(title)
                .build();

        if (this.messageRepository.add(message)) {
            url.append("success=true");
        }

        resp.sendRedirect(req.getContextPath() + url.toString());
    }


    private void responseDetailMsg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
        }

        Message message = messageRepository.getMessage(Long.parseLong(id));
        if (message == null) {
            resp.sendRedirect(req.getContextPath() + "/error?code=UNCATEGORIZED");
            return;
        }
        if (message.getStatus() == MessageStatus.UNREAD) {
            messageRepository.updateStatus(message.getId(), MessageStatus.READ);
        }

        System.out.println("info: created at " + message.getCreateAt());
        req.setAttribute("msg", message);
        req.getRequestDispatcher("/views/admin/msg-detail.jsp").forward(req, resp);
    }

    private Map<String, String> validate(String sender, String email, String title, String message) {
        Map<String, String> errors = new HashMap<>();
        if (sender == null || sender.trim().isEmpty()) {
            errors.put("senderErr", "true");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("emailErr", "true");
        }
        if (title == null || title.trim().isEmpty()) {
            errors.put("titleErr", "true");
        }
        if (message == null || message.trim().isEmpty()) {
            errors.put("messageErr", "true");
        }
        return errors;
    }
}

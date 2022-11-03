package com.study.ecommerce;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.CorrelationIdDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GenerateAllReportsServlet extends HttpServlet {

    private final KafkaDispatcher<String> batchDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        this.batchDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            batchDispatcher.send(
                "ECOMMERCE_SEND_MESSAGE_TO_ALL_USERS", "ECOMMERCE_USER_GENERATE_READING_REPORT",
                new CorrelationIdDto(GenerateAllReportsServlet.class.getSimpleName()), "ECOMMERCE_USER_GENERATE_READING_REPORT");

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Sent gererate report to all users");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

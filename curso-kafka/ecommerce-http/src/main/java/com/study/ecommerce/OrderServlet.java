package com.study.ecommerce;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.study.ecommerce.dto.OrderDto;
import com.study.ecommerce.kafka.dispatcher.KafkaDispatcher;
import com.study.ecommerce.kafka.dto.CorrelationIdDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OrderServlet extends HttpServlet {

    private final KafkaDispatcher<OrderDto> orderDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        this.orderDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var amount = new BigDecimal(req.getParameter("amount"));
            var email = req.getParameter("email");
            var orderDto = new OrderDto(UUID.randomUUID().toString(), amount, email);

            orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, new CorrelationIdDto(OrderServlet.class.getSimpleName()), orderDto);

            System.out.println("New order sent successfully");

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("New order sent successfully");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

package pl.pollub.cleancode.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.stream.Collectors;

// Każde wywołanie kontrolera trafia do singletona RequestHistory (metoda, ścieżka, parametry)
public class RequestHistoryInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //[Singleton]
        RequestHistory.getInstance().record(request.getMethod(), request.getRequestURI(), describeParameters(request));
        return true;
    }

    private String describeParameters(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .map(parameter -> parameter.getKey() + "=" + String.join(",", Arrays.asList(parameter.getValue())))
                .collect(Collectors.joining(", "));
    }
}

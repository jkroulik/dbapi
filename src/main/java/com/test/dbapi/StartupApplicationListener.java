package com.test.dbapi;

import com.test.dbapi.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ConnectionService connectionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        connectionService.saveDefaultConnection();
    }
}

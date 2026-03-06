package com.gts.collector.global.component;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * RSS 수집 활성화 상태를 관리하는 싱글톤 컴포넌트
 */
@Component
public class CollectionStatus {

    private final AtomicBoolean running = new AtomicBoolean(true);

    public boolean isRunning() {
        return running.get();
    }

    public void start() {
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }
}

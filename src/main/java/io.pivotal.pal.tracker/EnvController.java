package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    private final String port;
    private final String memory;
    private final String instanceIndex;
    private final String address;

    public EnvController(
            @Value("${port:NOT SET}") String port,
            @Value("${memory.limit:NOT SET}") String memory,
            @Value("${cf.instance.index:NOT SET}") String instanceIndex,
            @Value("${cf.instance.addr:NOT SET}") String address
    ) {
        this.port = port;
        this.memory = memory;
        this.instanceIndex = instanceIndex;
        this.address = address;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> environment = new HashMap<>();
        environment.put("PORT", port);
        environment.put("MEMORY_LIMIT", memory);
        environment.put("CF_INSTANCE_INDEX", instanceIndex);
        environment.put("CF_INSTANCE_ADDR", address);
        return environment;
    }
}

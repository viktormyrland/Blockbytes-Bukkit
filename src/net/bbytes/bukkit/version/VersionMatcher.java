package net.bbytes.bukkit.version;

import net.bbytes.bukkit.version.wrappers.Wrapper1_12_R1;
import net.bbytes.bukkit.version.wrappers.Wrapper1_15_R1;
import net.bbytes.bukkit.version.wrappers.Wrapper1_16_R2;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class VersionMatcher {

    private final String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    private final List<Class<? extends VersionWrapper>> versions = Arrays.asList(
            Wrapper1_12_R1.class,
            Wrapper1_15_R1.class,
            Wrapper1_16_R2.class
    );

    public VersionWrapper match() {
        try {
            return versions.stream()
                    .filter(version -> version.getSimpleName().substring(7).equals(serverVersion))
                    .findFirst().orElseThrow(() -> new RuntimeException("Invalid version"))
                    .newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }


}

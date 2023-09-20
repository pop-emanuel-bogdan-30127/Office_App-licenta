package net.officeweb.backend.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("user-photos", registry);
    }
    private void exposeDirectory(String directorName, ResourceHandlerRegistry registry) {
        Path uploadDirector = Paths.get(directorName);
        String uploadPath = uploadDirector.toFile().getAbsolutePath();

        if (directorName.startsWith("../")) directorName = directorName.replace("../", "");

        registry.addResourceHandler("/" + directorName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
    }
}

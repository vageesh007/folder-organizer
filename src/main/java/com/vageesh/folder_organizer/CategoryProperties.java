package com.vageesh.folder_organizer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "organizer")
public class CategoryProperties {
    /**  
     * Map of category name → list of extensions (with leading dot).  
     */
    private Map<String, List<String>> extMap;

    public Map<String, List<String>> getExtMap() {
        return extMap;
    }
    public void setExtMap(Map<String, List<String>> extMap) {
        this.extMap = extMap;
    }
}

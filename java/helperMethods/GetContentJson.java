package helperMethods;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GetContentJson {
    private static List categories;

    public static List readJsonFileDynamic(String filePath, String jsonPath) {

        System.out.println("jsonpath - "+jsonPath);
        try
        {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            Configuration conf = Configuration.builder()
                    .jsonProvider(new GsonJsonProvider())
                    .mappingProvider(new GsonMappingProvider())
                    .build();

            DocumentContext context = JsonPath.using(conf).parse(content);
            categories = context.read(jsonPath, List.class);//List

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return categories;
    }
}

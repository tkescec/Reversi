package hr.reversi.service;

import hr.reversi.util.AlertType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DocumentationService {
    private static final String pathName = "documentation.html";

    public static void generateDocumentation() {

        File documentationFile = new File(pathName);

        try {

            FileWriter writer = new FileWriter(documentationFile);

            writer.write("<!DOCTYPE html>");
            writer.write("<html>");
            writer.write("<head>");
            writer.write("<title>Project documentation</title>");
            writer.write("</head>");
            writer.write("<body>");
            writer.write("<h1>Project documentation</h1>");
            writer.write("<p>Class list:</p>");

            List<Path> paths = Files.walk(Paths.get("."))
                    .filter(path -> path.getFileName().toString().endsWith(".class"))
                    .collect(Collectors.toList());

            for(Path path : paths) {
                //System.out.println("Path: " + path);
                String[] tokens = path.toString().split(Pattern.quote(System.getProperty("file.separator")));

                Boolean startBuildingPath = false;

                StringBuilder sb = new StringBuilder();

                for(String token : tokens) {
                    if("classes".equals(token)) {
                        startBuildingPath = true;
                        continue;
                    }

                    if(startBuildingPath) {

                        if(token.endsWith(".class")) {
                            sb.append(token.substring(0, token.indexOf(".")));
                        }
                        else {
                            sb.append(token);
                            sb.append(".");
                        }
                    }
                    else {
                        continue;
                    }
                }

                if("module-info".equals(sb.toString())) {
                    continue;
                }

                System.out.println("Fully qualified name: " + sb.toString());

                try {
                    Class<?> clazz = Class.forName(sb.toString());

                    writer.write("<h2>" + clazz.getName() + "</h2>");

                    Constructor[] constructors = clazz.getConstructors();

                    writer.write("<h3>Constructors:</h3>");

                    for(Constructor c : constructors) {
                        writer.write("<h4>Constructor:" + c.getName() + "</h4>");
                    }

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                //System.out.println("Tokens:");
                //Arrays.stream(tokens).forEach(System.out::println);
            }

            writer.write("</body>");
            writer.write("</html>");
            writer.close();

        } catch (IOException e) {
            AlertService.showAlert(AlertType.error, "Error while generating documentation!");
        }
    }
}

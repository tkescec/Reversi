package hr.reversi.service;

import hr.reversi.util.AlertType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DocumentationService {
    private static final String pathName = "documentation.html";

    public static void generateDocumentation() {

        try (FileWriter fileWriter = new FileWriter(new File(pathName))) {

            List<Path> filesList = Files.walk(Path.of(".")).collect(Collectors.toList());

            System.out.println("Files list:");

            filesList = filesList.stream().filter(p ->
                            (p.getFileName().toString().endsWith(".class")
                                    && p.getFileName().toString().compareTo("module-info.class") != 0))
                    .collect(Collectors.toList());

            List<String> fqnList = new ArrayList<>();

            for(Path path : filesList) {
                System.out.println(path.toFile().getAbsolutePath().toString());
                StringTokenizer tokenizer = new StringTokenizer(path.toFile().getAbsolutePath().toString(), "\\");

                Boolean startJoining = false;
                String fqn = "";

                while(tokenizer.hasMoreTokens()) {
                    String newToken = tokenizer.nextToken();

                    if("classes".equals(newToken)) {
                        startJoining = true;
                        continue;
                    }

                    if(startJoining) {

                        if(newToken.contains(".class")) {
                            fqn += newToken.substring(0, newToken.lastIndexOf("."));
                        }
                        else {
                            fqn += newToken + ".";
                        }
                    }

                    System.out.println("Token: " + newToken);
                }

                System.out.println("FQN: " + fqn);

                fqnList.add(fqn);
            }

            StringBuilder classInfo = new StringBuilder();

            for(String fqn : fqnList) {
                Class clazz = Class.forName(fqn);

                classInfo.append("<section style='margin-bottom: 40px; border-bottom: solid 2px #333;'>");

                classInfo.append("<h2 style='color:#ba6250; border-bottom: dotted 1px #ba6250;'>" + clazz.getSimpleName() + "</h2>");
                classInfo.append("<h2 style='font-size:20px; margin-top:20px'>Fields:</h2>");

                Field[] fields = clazz.getDeclaredFields();

                for(Field field : fields) {
                    classInfo.append("<h3 style='color:#298c47; padding-left: 30px'>" + Modifier.toString(field.getModifiers()) + " " + field.getName() + "</h3>");
                }

                classInfo.append("<h2 style='font-size:20px; margin-top:20px'>Constructors:</h2>");
                Constructor[] constructors = clazz.getConstructors();


                for(Constructor constructor : constructors) {

                    String paramsString = "";

                    for(int i = 0; i < constructor.getParameters().length; i++) {
                        Parameter p = constructor.getParameters()[i];
                        paramsString += Modifier.toString(p.getModifiers()) + " " + p.getType().getSimpleName()
                                + " " + p.getName();

                        if(i < (constructor.getParameters().length - 1)) {
                            paramsString += ", ";
                        }
                    }

                    classInfo.append("<h3 style='color:#322a7a; padding-left: 30px'>" + Modifier.toString(constructor.getModifiers()) + " " + constructor.getDeclaringClass().getSimpleName()
                            + " (" + paramsString + ")</h3>");
                }

                classInfo.append("<h2 style='font-size:20px; margin-top:20px'>Methods:</h2>");
                Method[] methods = clazz.getDeclaredMethods();

                for(Method method : methods) {

                    String paramsString = "";

                    for(int i = 0; i < method.getParameters().length; i++) {
                        Parameter p = method.getParameters()[i];
                        paramsString += Modifier.toString(p.getModifiers()) + " " + p.getType().getSimpleName()
                                + " " + p.getName();

                        if(i < (method.getParameters().length - 1)) {
                            paramsString += ", ";
                        }
                    }

                    classInfo.append("<h3 style='color:#73216c; padding-left: 30px'>" + Modifier.toString(method.getModifiers()) + " " + method.getName()
                            + " (" + paramsString + ")</h3>");
                }

                classInfo.append("</section>");
            }

            fileWriter.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<head>")
                    .append("<title>HTML Documentation</title>")
                    .append("</head>")
                    .append("<body>")
                    .append("<h1 style='border-bottom:dashed 4px #507eba; color:#507eba; text-align: center'>Class list</h1>")
                    .append(classInfo.toString())
                    .append("</body>")
                    .append("</html>");
        } catch (IOException e) {
            AlertService.showAlert(AlertType.error, "Error while generating documentation!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

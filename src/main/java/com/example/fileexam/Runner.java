package com.example.fileexam;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Component
public class Runner implements ApplicationRunner {

    private final String NUMBER_PATH = "number.txt";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileCallSuccess();
        fileCallFail();
        fileCallToFile();
    }

    public void fileCallSuccess() throws IOException {
        Resource resource = new ClassPathResource(this.NUMBER_PATH);
        InputStream is = resource.getInputStream();

        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);

        inputStreamRead(br);
    }

    public void fileCallFail() throws IOException {
        Resource resource = new ClassPathResource(this.NUMBER_PATH);
        String path = resource.getFile().getAbsolutePath();
        FileInputStream fis = new FileInputStream(path);

        Reader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(reader);

        inputStreamRead(br);
    }

    private void inputStreamRead(BufferedReader br) throws IOException {
        String line;
        int sum = 0;

        while ((line = br.readLine()) != null) {
            sum += Integer.parseInt(line);
        }

        System.out.println(sum);
    }

    public void fileCallToFile() throws IOException {
        Resource resource = new ClassPathResource(this.NUMBER_PATH);
        InputStream is = resource.getInputStream();

        File numberFile = File.createTempFile("number", ".txt");
        try (FileOutputStream fos = new FileOutputStream(numberFile)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
        }

        fileRead(numberFile);
    }

    private void fileRead(File numberFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(numberFile));
            String line;

        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

}

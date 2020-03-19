package com.borosoft.qy.sjtj;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {

        File file = new File("D:\\sts_workplace\\borosoft-web-outside\\borosoft-dgxy\\src\\main\\java\\com\\neusoft\\portal\\creditquery\\service\\");
        List<File> files = Arrays.asList(file.listFiles());
        files.forEach(f -> {
            if(f.isFile()){
                System.out.println(f.getName() +": " + EncodingDetect.getJavaEncode(f.getAbsolutePath()));
                /*try {
                    if(StringUtils.notEqauls(EncodingDetect.getJavaEncode(f.getAbsolutePath()),"UTF-8")){
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(new FileInputStream(f.getAbsolutePath()), EncodingDetect.getJavaEncode(f.getAbsolutePath())));
                        BufferedWriter bufferedWriter = new BufferedWriter(
                                new OutputStreamWriter(new FileOutputStream("C:\\Users\\Administrator\\Desktop\\新建文件夹\\"+f.getName()), "UTF-8"));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            bufferedWriter.write(line + "\r\n");
                        }
                        bufferedWriter.close();
                        bufferedReader.close();
                        String outputFileEncode = EncodingDetect.getJavaEncode("C:\\Users\\Administrator\\Desktop\\新建文件夹\\"+f.getName());
                        System.out.println("outputFileEncode===" + outputFileEncode);
                        System.out.println("txt文件格式转换完成");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });


//        String inputFileEncode = EncodingDetect.getJavaEncode("D:\\sts_workplace\\borosoft-web-outside\\borosoft-dgxy\\src\\main\\java\\gov\\datacenter\\services\\ExecuteResult.java");
//        System.out.println("inputFileEncode===" + inputFileEncode);


    }
}

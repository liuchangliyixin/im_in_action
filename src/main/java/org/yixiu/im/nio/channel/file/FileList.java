package org.yixiu.im.nio.channel.file;

import java.io.File;
import java.io.IOException;

public class FileList {
    static final String SRC_PATH = "D:\\src";
    public static void main(String[] args) {
        try {
            listFile(SRC_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void listFile(String path) throws IOException {
        if(null == path || "".equals(path)){
            return;
        }

        File file = new File(path);
        if(file.isFile()){
            System.out.println(file.getCanonicalPath());
        }else if(file.isDirectory()){
            System.out.println(file.getCanonicalPath());
            File[] subFiles = file.listFiles();
            if(null == subFiles){
                return;
            }
            for(File subFile : subFiles){
                System.out.println(subFile.getCanonicalPath());
            }
        }else{
            System.out.println(file.getCanonicalPath());
        }
    }

    /**
     * getPath()：此文件路径方法将抽象路径名作为String返回。
     * 		   如果字符串pathname用于创建File对象，则getPath()只返回pathname参数，例如File file = new File(pathname) 构造参数pathname是怎么样，getPath()就返回怎么的字符串。
     * 		   如果URI用作参数，则它将删除协议并返回文件名。
     *
     * getAbsolutePath()：此文件路径方法返回文件的绝对路径。
     * 				   如果使用绝对路径名创建File对象，则只返回路径名。
     * 				   如果使用相对路径创建文件对象，则以系统相关的方式解析绝对路径名。
     * 				   在UNIX系统上，通过将相对路径名解析为当前用户目录，使其成为绝对路径名。
     * 				   在Microsoft Windows系统上，通过将路径名解析为路径名所指定的驱动器的当前目录（如果有），使相对路径名成为绝对路径名; 如果没有，则针对当前用户目录解析。
     *
     * getCanonicalPath()：此路径方法返回绝对唯一的标准规范路径名。
     * 				    此方法首先将此路径名转换为绝对形式，就像调用getAbsolutePath方法一样，然后以系统相关的方式将其映射到其唯一路径上。
     * 					也就是说如果路径中包含“.”或“..”等当前路径及上层路径表示法，则会从路径名中删除“.”和“..”使用真实路径代替。
     * 					另外比较重点的是 它还会解析软链接（在UNIX平台上）以及将驱动器号（在Microsoft Windows平台上），将它们转换为标准实际路径。
     *
     * 引自: https://www.jianshu.com/p/bb4412ef7c37
     */
}

package top.tanmw.generator.util;

/**
 * @author TMW
 * @since 2023/5/24 11:47
 */
public class PackageUtil {

    public static String pathToPackage(String path) {
        int indexOf = path.indexOf("src/main/java/");
        String substring = path.substring(indexOf + 14);
        return substring.replaceAll("/", ".");
    }

    public static void main(String[] args) {
        String path = "zenith-front-web/src/main/java/com/zenith/front/web/controller/st";
        String pathToPackage = PackageUtil.pathToPackage(path);
        System.out.println(pathToPackage);
    }
}

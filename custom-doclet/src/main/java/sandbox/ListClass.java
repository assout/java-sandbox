package sandbox;
import com.sun.javadoc.*;

public class ListClass {
    public static boolean start(RootDoc root) {
    	System.out.println(root.options());
        ClassDoc[] classes = root.classes();
        for (int i = 0; i < classes.length; ++i) {
            System.out.println(classes[i]);
        }
        return true;
    }
}
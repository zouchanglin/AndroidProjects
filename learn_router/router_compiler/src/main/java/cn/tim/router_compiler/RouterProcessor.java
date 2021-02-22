package cn.tim.router_compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

// 只处理自己关心的注解
@SupportedAnnotationTypes("cn.tim.router_annotation.Router")

// 支持moduleName这个参数的传递
@SupportedOptions("moduleName")

public class RouterProcessor extends AbstractProcessor{

    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Map<String, String> options = processingEnv.getOptions();
        moduleName = options.get("moduleName");

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        String codeString = "package cn.tim.routers;\n" +
                "\n" +
                "import android.app.Activity;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "\n" +
                "import cn.tim.common.IRouterLoad;\n" +
                "import cn.tim.find.FindActivity;\n" +
                "\n" +
                "public class " + moduleName + "Router implements IRouterLoad {\n" +
                "    @Override\n" +
                "    public void loadInfo(Map<String, Class<? extends Activity>> routers) {\n" +
                "        routers.put(\"/find/main\", FindActivity.class);\n" +
                "    }\n" +
                "}\n";
        // 开始构造生成Java代码
        //通过RoundEnvironment 文件工具
        Filer filer = processingEnv.getFiler();
        try {
            FileObject fileObject = filer.createSourceFile("cn.tim.routers." + moduleName +"Router");
            OutputStream outputStream = fileObject.openOutputStream();
            outputStream.write(codeString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
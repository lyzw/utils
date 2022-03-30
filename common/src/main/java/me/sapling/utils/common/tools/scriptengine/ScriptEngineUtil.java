package me.sapling.utils.common.tools.scriptengine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * { DESCRIBE HERE }
 *
 * @author zhouwei
 * @since 2021/10/15
 */
public class ScriptEngineUtil {

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> list= manager.getEngineFactories();
        list.forEach(f-> {
            System.out.println(f.getLanguageName());
        });
    }
}

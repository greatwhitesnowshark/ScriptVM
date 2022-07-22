package scripting;

/**
 * @author Smoke
 */
public class Script {

    private final String sScriptName;

    public Script(String sScriptName) {
        this.sScriptName = sScriptName;
    }

    public String GetScriptName() {
        return this.sScriptName;
    }
}

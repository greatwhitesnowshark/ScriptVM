/**
 * @author Smoke
 */
public class Config {

    public static final String[][] SCRIPTS = new String[][]
    {
            {"test.js (Give user NX cash example)", "test.js"},
            {"selectionTest.js (AskMenu example)", "selectionTest.js"},
            {"uiSelect.js (UI Type AskMenu example)", "uiSelect.js"},
            {"getmount.js (Mounts 'Shop')", "getmount.js"}
    };

    public static String[] GetSelections() {
        String[] aSelections = new String[SCRIPTS.length];
        int nIndex = 0;
        for (String[] a : SCRIPTS) {
            String sText = a[0];
            aSelections[nIndex++] = sText;
        }
        return aSelections;
    }

    public static String[] GetScripts() {
        String[] aScripts = new String[SCRIPTS.length];
        int nIndex = 0;
        for (String[] a : SCRIPTS) {
            String sScriptName = a[1];
            aScripts[nIndex++] = sScriptName;
        }
        return aScripts;
    }

    public static String GetScript(String sSelection) {
        int nSelectionIndex = 0;
        for (String s : GetSelections()) {
            if (s.equals(sSelection)) {
                break;
            }
            nSelectionIndex++;
        }
        return GetScripts()[nSelectionIndex];
    }
}

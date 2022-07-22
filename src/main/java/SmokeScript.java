import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import scripting.Script;
import scripting.ScriptError;
import scripting.ScriptSysFunc;
import user.User;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * @author Smoke
 */
public class SmokeScript {

    public static Context pContext = null; // don't touch this one

    public static void main(String[] args) {
        String sTest = (String) JOptionPane.showInputDialog(
                new JFrame(),
                "Which test script would you like to execute? \nPlease select one from the list below.",
                "SmokeScript",
                JOptionPane.PLAIN_MESSAGE,
                null,
                Config.GetSelections(),
                Config.GetScripts());

        ExecuteScriptAndWait(Config.GetScript(sTest));
    }

    public static void ExecuteScriptAndWait(String sScriptName) {
        if (!Files.exists(Paths.get("scripts/"+sScriptName))) {
            System.err.println("File does not exist - `scripts/" + sScriptName + "`");
            return;
        }
        try {
            User pUser = new User(); // fake user
            Script pScript = new Script(sScriptName);
            CompletableFuture.supplyAsync(() -> {
                try {
                    pContext = Context.newBuilder("js").allowAllAccess(true).build();
                    Value pBindings = pContext.getBindings("js");
                    pBindings.putMember("self", new ScriptSysFunc(pScript));
                    pBindings.putMember("pUser", pUser);
                    pContext.eval(Source.newBuilder("js", Paths.get("scripts/" + sScriptName).toFile()).build());
                    return true;
                } catch (PolyglotException e) {
                    ScriptError.LogError(e, false);
                    e.printStackTrace();
                } catch (IOException | CancellationException | CompletionException e) {
                    e.printStackTrace();
                } finally {
                    if (pContext != null) {
                        pContext.close();
                    }
                }
                return false;
            }).handle((pScriptToDispose, pThrowable) -> {
                if (pThrowable == null) {
                    return pScriptToDispose;
                }
                return pThrowable;
            }).whenComplete((pScriptToDispose, pThrowable) -> {
                if (pThrowable != null) {
                    pThrowable.printStackTrace();
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

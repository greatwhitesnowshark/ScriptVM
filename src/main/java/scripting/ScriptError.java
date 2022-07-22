package scripting;

import org.graalvm.polyglot.PolyglotException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Smoke
 */
public class ScriptError {

    private static final ReentrantLock pLock = new ReentrantLock();
    private final int nStartLine, nEndLine;
    private final String sSimplePath, sErrorMessage, sExpression, sStatement;
    private final StackTraceElement[] aStackTrace;
    private final Class<?> pClass;

    ScriptError(PolyglotException e, StackTraceElement... aStackTrace) {
        this.pClass = e.getCause() != null ? e.getCause().getClass() : e.getClass();
        String sFilePath = e.getSourceLocation().getSource().getPath();
        this.sSimplePath = sFilePath.substring(sFilePath.indexOf("script"));
        this.sErrorMessage = e.getLocalizedMessage().trim();
        this.sExpression = e.getSourceLocation().getCharacters().toString().trim();
        this.sStatement = e.getSourceLocation().getSource().getCharacters(e.getSourceLocation().getStartLine()).toString().trim();
        this.nStartLine = e.getSourceLocation().getStartLine();
        this.nEndLine = e.getSourceLocation().getEndLine();
        this.aStackTrace = aStackTrace;
    }

    public static void LogError(PolyglotException pException, boolean bStackTrace) {
        ScriptError pError = bStackTrace ? new ScriptError(pException, pException.getStackTrace()) : new ScriptError(pException);
        pError.LogError();
    }

    public void LogError(String... aMessage) {
        pLock.lock();
        try {
            if (aMessage != null) {
                for (String sMsg : aMessage) {
                    System.err.println(sMsg);
                }
            }
            List<String> aLogger = new LinkedList<>();
            if (pClass != null && sErrorMessage != null && !sErrorMessage.contains("Execution cancelled.")) {
                if (!sErrorMessage.isBlank()) aLogger.add(String.format("%s\n", sErrorMessage));
                if (sSimplePath != null && !sSimplePath.isBlank()) aLogger.add(String.format("%s class-type error evaluating script: `%s`", pClass.getSimpleName(), sSimplePath.trim()));
                if (nStartLine > 0 || nEndLine > 0) aLogger.add(String.format("........at %s", nEndLine > 0 && nStartLine != nEndLine ? ("lines " + nStartLine + "-" + nEndLine) : "line " + nStartLine));
                if (sStatement != null && !sStatement.isBlank() && (sExpression == null || sStatement.length() > sExpression.length() + 5)) aLogger.add(String.format("%s", sStatement.trim()));
                if (sExpression != null && !sExpression.isBlank()) aLogger.add(String.format("%s", sExpression.trim()));
                if (aStackTrace != null && aStackTrace.length > 0) {
                    for (int i = 0; i < aStackTrace.length; i++) {
                        StackTraceElement pStackTrace = aStackTrace[i++];
                        if (pStackTrace != null) aLogger.add(pStackTrace.toString());
                    }
                }
                StringBuilder pLogger = new StringBuilder("\n");
                aLogger.forEach((s) -> pLogger.append(s).append("\n"));
                System.err.print(pLogger.toString());
            }
        } finally {
            pLock.unlock();
        }
    }
}
